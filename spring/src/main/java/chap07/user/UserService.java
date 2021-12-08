package chap07.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

	boolean insert(UserVo vo, HttpServletRequest req); //school에있는 데이터 넘기려고 req필요
	int idcheck(String id);
	boolean login(UserVo vo, HttpSession sess); //로그인 여부를 판단
}
