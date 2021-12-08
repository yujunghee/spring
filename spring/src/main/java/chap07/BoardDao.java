package chap07;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {
	
	@Autowired //자동주입
	SqlSessionTemplate sqlSessionTemplate; 
	
	public int count(BoardVo vo) {
		return sqlSessionTemplate.selectOne("board.count",vo);
	}
	
	public List<BoardVo> selectList(BoardVo vo){ //Map으로 받도록 String->Map으로 수정함->boardVo로 수정
		return sqlSessionTemplate.selectList("board.selectList",vo); //여기서 board는 mapper의 namespace
	}
	
	public int insert(BoardVo vo) {
		//return sqlSessionTemplate.insert("board.insert",vo); //앞에는 xml의 namespace+id, 뒤에는 파라미터
		int r = -1; //0개가 insert될수도있음
		try {
			r = sqlSessionTemplate.insert("board.insert",vo);
		}catch(Exception e) {
			r=0;
		}
		return r;
	}
	
	//메서드 생성 -> sqlSessionTemplate.selectOne("board.selectOne",PK)
	public BoardVo selectOne(int boardno) {
		return sqlSessionTemplate.selectOne("board.selectOne",boardno);
	}
	
	public BoardVo2 selectOne2(int boardno) {
		return sqlSessionTemplate.selectOne("board.selectOne2",boardno);
	}
	
	public int update(BoardVo vo) {
		return sqlSessionTemplate.update("board.update",vo);
	}
	
	public int delete(BoardVo vo) { //파라미터 굳이 int로 안넣고 vo로 넣어도 ok
		return sqlSessionTemplate.delete("board.delete",vo.getBoardno());
	}
}
