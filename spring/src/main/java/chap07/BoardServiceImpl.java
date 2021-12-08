package chap07;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao boardDao;
	
	@Override
	public int count(BoardVo vo) {
		return boardDao.count(vo);
	}
	
	@Override
	public List<BoardVo> selectList(BoardVo vo) { //String searchWord->Map으로 수정(Type도 같이 넘기기위해)
//		Map map = new HashMap();
//		map.put("searchType", searchType);
//		map.put("searchWord", searchWord);
		return boardDao.selectList(vo);
	}
	
	@Override
	public int insert(BoardVo vo) {
		return boardDao.insert(vo);
	}
	
	@Override
	public BoardVo selectOne(int boardno) {
		return boardDao.selectOne(boardno);
	}
	
	@Override
	public int update(BoardVo vo) {
		return boardDao.update(vo);
	}
	
	@Override
	public int delete(BoardVo vo) {
		return boardDao.delete(vo);
	}
	
	@Override
	public BoardVo2 selectOne2(int boardno) {
		return boardDao.selectOne2(boardno);
	}

}
