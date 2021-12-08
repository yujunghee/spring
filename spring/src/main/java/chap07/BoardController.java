package chap07;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import chap07.user.UserVo;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService; // 왜 이렇게(impl빼고) 쓸까? -> 자동형변환(상속관계,부모니까)
	
	@GetMapping({"/board/index.do","/user/mypage.do"})
	public String index(Model model, HttpServletRequest req, BoardVo vo) { //req에 set하기위해 model사용
	
		System.out.println(req.getRequestURI());
		System.out.println(req.getServletPath());
		
		String view = "board/index";
		if("/user/mypage.do".equals(req.getServletPath())) { //마이페이지로 접속한 경우
			vo.setUserno(((UserVo)req.getSession().getAttribute("loginInfo")).getUserno());
			view = "user/mypage";
		}
		
		int totCount = boardService.count(vo); //총갯수
		int totPage = totCount / 10; //총 페이지수
		if(totCount % 10 > 0) totPage++;
		
		int startIdx = (vo.getPage()-1) * 10;
		vo.setStartIdx(startIdx);
				
		List<BoardVo> list = boardService.selectList(vo);
		
		model.addAttribute("list",list);
		model.addAttribute("totPage",totPage);
		
		return view;
		
	}
	
	@RequestMapping("/board/write.do")
	public String write() {
		return "board/write";
	}

	@PostMapping("/board/insert.do")
	public String insert(BoardVo vo, HttpServletRequest req, MultipartFile file, HttpSession sess) { //Command객체
		
		vo.setUserno(((UserVo)sess.getAttribute("loginInfo")).getUserno()); //userno가져오기
		/* 풀어쓰면 요렇게 3줄
		UserVo uv = (UserVo)sess.getAttribute("loginInfo");
		int userno = uv.getUserno();
		vo.setUserno(userno);
		*/
		
		//파일 저장
		if(!file.isEmpty()) { //사용자가 파일을 첨부했다면
			try {
				String path = req.getRealPath("/upload/");
				String filename = file.getOriginalFilename();
				file.transferTo(new File(path+filename)); //경로에 파일을 저장
				vo.setFilename(filename);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		int r = boardService.insert(vo);
		System.out.println(r);
		
		//void로하고 서블릿사용 or jsp파일만들기(result.jsp)
		
		//alert 정상적으로 등록되었습니다 띄우고 -> index.do로 이동
		if(r > 0) {
			req.setAttribute("msg", "정상적으로 등록되었습니다.");
			req.setAttribute("url", "index.do");
		} else {
			req.setAttribute("msg", "등록 오류"); //r=0일때 오류안뜨게 try-catch해주기(dao에서)
			req.setAttribute("url", "write.do");
		}
		return "include/result";
	}
	
	@GetMapping("/board/detail.do")
	public String detail(Model model, @RequestParam int boardno) { //출력해야하니까 model필요
		model.addAttribute("data",boardService.selectOne(boardno));
		return "board/detail";
	}
	@GetMapping("/board/detail2.do") //resultMap쓰는 예시
	public String detail2(Model model, @RequestParam int boardno) { //출력해야하니까 model필요
		model.addAttribute("data",boardService.selectOne2(boardno));
		return "board/detail2";
	}
	
	@GetMapping("/board/edit.do")
	public String edit(Model model, @RequestParam int boardno) {
		model.addAttribute("data",boardService.selectOne(boardno));
		return "board/edit";
	}
	
	@PostMapping("/board/update.do")
	public String update(Model model, BoardVo vo) {
		int r = boardService.update(vo);
		if(r > 0) {
			model.addAttribute("msg","정상적으로 수정되었습니다.");
			model.addAttribute("url", "detail.do?boardno="+vo.getBoardno()); //성공했을때 상세페이지로 이동
		} else {
			model.addAttribute("msg","수정 오류");
			model.addAttribute("url", "edit.do?boardno="+vo.getBoardno()); //실패했을때->boardno=0이되는이유:edit.jsp에 없어서->hidden으로 선언
		}
		return "include/result";
	}
	
	@GetMapping("/board/delete.do")
	public String delete(Model model, BoardVo vo) {
		int r = boardService.delete(vo);
		if(r > 0) {
			model.addAttribute("msg","정상적으로 삭제되었습니다.");
			model.addAttribute("url", "index.do"); //성공했을때 목록으로 이동(나중에->첫페이지이동이아니라 보던페이지로 가게)
		} else {
			model.addAttribute("msg","삭제 오류");
			model.addAttribute("url", "detail.do?boardno="+vo.getBoardno()); //실패했을때 상세페이지로 이동
		}
		return "include/result";
	}
}
