package chap04;

import org.springframework.stereotype.Component;

/*
 * XXDao는 데이터 처리를 위한 클래스
 * Dao(Date Access Object) : 데이터에 접근하기 위한 객체
 * ex) insert, select, update, delete ....
 */
@Component
public class MemberDao {
	
	// db에 회원을 등록하는 메서드
	public int insert(String name) {
		//db에 저장
		System.out.println(name+"저장!!!");
		return 1; //성공시 1을 리턴
	}
}
