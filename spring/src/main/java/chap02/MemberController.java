package chap02;

/*
 * Controller가 하는 역할
 * 클라이언트의 요청을 받아서, 해당하는 로직을 처리(Service), 화면을 응답(View)하게 함 
 */

public class MemberController {

	MemberService service;
	
	//MemberService 객체를 주입받음
	public void setService(MemberService service) {
		this.service = service;
	}
	
	// 등록폼에서 저장버튼을 클릭하면 전송되는 위치(라고 가정) -> 이 메서드가 호출됨
	public String insert(String name) {
		
		if(service.insert(name) > 0) {
			//등록 성공시 처리
		} else {
			//등록 실패시 처리
		}
		return "응답";
	}
}
