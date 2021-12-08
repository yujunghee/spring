package chap02;

/*
 * XXService 클래스는 로직을 담당하는 클래스
 * 컨트롤러가 특정 기능을 실행하기 위해 호출함 (호출이 되어질것을 생각하고 만들기)
 */

public class MemberService {

	MemberDao dao; //dao객체를 주입받을 필드를 선언
	
	//주입방법(1. setter메서드 2. 생성자 3. 자동주입)
	//setter메서드
	public MemberService setDao(MemberDao dao) {
		this.dao = dao;
		return this;
	}

	/* 회원등록 기능을 수행하는 메서드 
	 * db처리는 MemberDao에서
	 * 여기서는 XXDao의 어떤 메서드를 호출, 비지니스 로직을 수행함
	 */
	
	public int insert(String name) {
		int r = dao.insert(name);
		return r;
	}
}
