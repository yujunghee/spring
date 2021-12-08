package chap07.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	UserDao dao;
	
	@Override
	@Transactional
	public boolean insert(UserVo vo, HttpServletRequest req) {
		boolean r = false;
		int cnt = dao.insert(vo);
		//school데이터 등록
		//UserVo객체에는 userno가 저장된 상태
		//학교정보를 배열로 받아오기 -> 파라미터는 무조건 String타입으로
/*		String[] school = req.getParameterValues("school"); //req객체가 배열을 맞는크기로 선언해줌 (나는 값만 넣은것)
		String[] year = req.getParameterValues("year");
		SchoolVo svo = new SchoolVo();
		svo.setUserno(vo.getUserno());
		
		for(int i=0; i<school.length; i++) {
			svo.setSchool(school[i]);
			svo.setYear(year[i]);
			cnt += dao.insertSchool(svo);
		}*/
		
		//배열 필드 사용하는 방식
		SchoolVo svo = new SchoolVo();
		svo.setUserno(vo.getUserno());
		
		for(int i=0; i<vo.getSchool().length; i++) {
			svo.setSchool(vo.getSchool()[i]);
			svo.setYear(vo.getYear()[i]);
			cnt += dao.insertSchool(svo);
		}
		
		//cnt = 배열의 길이(학교 갯수)+1(처음 insert) = 4
		if(cnt == vo.getSchool().length+1) {
			r = true;
		}
		return r;
	}

	@Override
	public int idcheck(String id) {
		return dao.idcheck(id);
	}

	@Override
	public boolean login(UserVo vo, HttpSession sess) {
		UserVo uv = dao.login(vo);
		if(uv != null) { //로그인 성공
			sess.setAttribute("loginInfo", uv);
			return true;
		} 
		return false;
	}
	
}
