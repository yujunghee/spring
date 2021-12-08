package chap07;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	// preHandle : 컨트롤러 실행 전 (많이 사용)
	// postHandle : 컨트롤러 실행 후
	// afterCompletion : 뷰 실행 후
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		// 세션객체를 생성해서 -> 로그인 세션이 존재하는지 여부를 체크
		// 존재여부에 따라서 처리
		HttpSession sess = req.getSession();
		if(sess.getAttribute("loginInfo") == null) { //로그인이 안된 상태
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능합니다.');");
			out.println("location.href='/spring/user/login.do';");
			out.println("</script>");
			return false;
		}else { //로그인 된 상태
			return true;  //가던 길 가라~
		}
	}
}
