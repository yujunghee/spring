package chap07.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import chap07.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/user/regist.do")
	public String regist() {
		return "user/regist";
	}

	@PostMapping("/user/regist.do")
	public String regist(Model model, UserVo vo, HttpServletRequest req) {
		//System.out.println("등록 전 userno : "+vo.getUserno()); //초기값 0이 출력됨
		log.info("등록 전 userno : "+vo.getUserno()); //->어노테이션사용함
		boolean r = service.insert(vo, req);
		
		System.out.println("등록 후 userno : "+vo.getUserno()); //last_insert된 userno가 출력됨
		if(r) {
			model.addAttribute("msg","정상적으로 가입되었습니다.");
			model.addAttribute("url","/spring/board/index.do");
		} else {
			model.addAttribute("msg","가입오류");
			model.addAttribute("url","regist.do");
		}
		return "include/result";
	}
	
	@GetMapping("/user/idcheck.do")
	public String idcheck(Model model, @RequestParam String id) {
		int r = service.idcheck(id);
		model.addAttribute("ret",r); //r은 0또는 1(count)
		return "include/return";
	}
	
	@RequestMapping("/user/login.do") //required를 false로줘서 처음에 쿠키가없어도 에러안나게 처리해줌
	public String login(@CookieValue(value="cookieId", required=false) Cookie cookie, UserVo vo) { 
		if(cookie != null) { // = 이전에 사용자가 아이디 저장을 체크하고 로그인 한적이 있다
			vo.setId(cookie.getValue());
			//vo.setCheckId("check");
		}
			
		return "user/login";
	}
	
	@RequestMapping("/user/loginProcess.do")
	public String loginProcess(UserVo vo, HttpSession sess, Model model, HttpServletResponse res) {
		if(service.login(vo, sess)) { //로그인성공했을때
			// 쿠키 객체 생성
			Cookie cookie = new Cookie("cookieId",vo.getId());
			if("check".equals(vo.getCheckId())) { //사용자가 아이디저장에 체크했을 때
				cookie.setMaxAge(60*60*24*30); //30일 동안 보관
			}else {
				cookie.setMaxAge(0); //아이디저장에 체크를 안했으면 쿠키 날려버리기(삭제하는것은 없기때문에 유효시간을 0으로 세팅)
			}
			res.addCookie(cookie); //response객체에 쿠키 추가
			return "redirect:/board/index.do";
		}else {
			model.addAttribute("msg","아이디 비밀번호가 올바르지 않습니다.");
			model.addAttribute("url", "login.do");
		}
		return "include/result";
	}
	
	@RequestMapping("/user/logout.do")
	public String logout(Model model, HttpSession sess) {
		sess.invalidate();
		//sess.removeAttribute("loginInfo");
		model.addAttribute("msg","로그아웃되었습니다.");
		model.addAttribute("url","/spring/board/index.do");
		return "include/result";
	}
	
/*
	@RequestMapping("/user/mypage.do")
	public String mypage(Model model, HttpSession sess, BoardVo vo) {
		
		vo.setUserno(((UserVo)sess.getAttribute("loginInfo")).getUserno());
		
		int totCount = boardService.count(vo); //총갯수
		int totPage = totCount / 10; //총 페이지수
		if(totCount % 10 > 0) totPage++;
	
		int startIdx = (vo.getPage()-1) * 10;
		vo.setStartIdx(startIdx);
				
		List<BoardVo> list = boardService.selectList(vo);
		model.addAttribute("list",list);
		model.addAttribute("totPage",totPage);
		return "user/mypage";
	}
*/
}
