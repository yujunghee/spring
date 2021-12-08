package chap06;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import chap05.MemberVo;

/*
 * RestAPI Controller
 */

@RestController
public class ApiController {
	
	@Autowired
	BoardService service;
	
	/*
	 * 파라미터를 받는 4번째 방법
	 * @PathVariable
	 * /api/list/1/자바 -> page=1, searchWord=자바
	 * /api/list/2/파이썬 -> page=2, serchWord=파이썬	
	 */  
	
	@GetMapping("/api/list/{page}/{searchWord}")
	public List<BoardVo> list(@PathVariable int page, @PathVariable String searchWord){
		System.out.println("page : "+page);
		System.out.println("searchWord : "+searchWord);
		List<BoardVo> list = service.selectList();
		return list;
	}
}
