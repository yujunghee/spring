package chap05;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormController {
	
	@GetMapping("/form.do")
	public String form() {
		return "form";
	}
	
	/*
	 * 파라미터를 받는 방법
	 * 1. HttpServletRequest 객체로 받기
	 * 2. @RequestParam 어노테이션으로 받기 -> 존재하지않는 값일 경우 null이 나오는게아니라 에러발생 -> required=false로하면 null
	 * 3. Command 객체로 받기 : 파라미터의 이름과 동일한 필드에 자동으로 값을 넣어줌
	 */
	
	@PostMapping("/send.do")
	public String send(HttpServletRequest req, @RequestParam("email") String email, 
			@RequestParam(value="tel", required=false) String tel,
			MemberVo vo) {
		String name = req.getParameter("name");
		req.setAttribute("msg", name+"님, 안녕하세요");
		req.setAttribute("msg2", "이메일 : "+email);
		req.setAttribute("tel", tel);
		
//		MemberVo vo = new MemberVo();
//		vo.setName(name);
//		vo.setEmail(email); -> 이렇게 일일이 setter메서드 호출할필요없이 파라미터로 command객체 받아주면 됨
		
		System.out.println(vo.getName()+" "+vo.getEmail()+" "+vo.getNo());
		if(vo.getHobby() != null) {
			for(int i=0; i<vo.getHobby().length; i++) {
				System.out.println(vo.getHobby()[i]);
			}
			for(String hobby : vo.getHobby()) {
				System.out.println(hobby);
			}
		}		
		//커맨드객체없이 취미값을 vo2의 hobby에 저장하려면?
//		MemberVo vo2 = new MemberVo();
//		vo2.setHobby(req.getParameterValues("hobby"));
//		for(int i=0; i<vo2.getHobby().length; i++) {
//			System.out.println(vo2.getHobby()[i]);
//		}
		
		return "send";
	}
	
	/*
	 * View에서 사용(주로 출력)하기 위한 값을 Controller에서 저장하는 방법 
	 * -request에 set
	 * -session에 set
	 * -model에 add
	 * -ModelAndView에 add
	 */
	
	//ModelAndView 객체 : Model + View
	@GetMapping("/test9.do")
	public ModelAndView test8() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("name","홍길동");
		mav.setViewName("test9");
		return mav;
	}
	
	
	
	
	
	
	
	
}
