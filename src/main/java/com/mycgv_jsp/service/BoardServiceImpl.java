package com.mycgv_jsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ArrayList<BoardVo> getSelect(int startCount, int endCount) {
		ArrayList<BoardVo> rlist = new ArrayList<BoardVo>();
		List<Object> list = boardDao.select(startCount, endCount);
		for(Object obj : list) {
			BoardVo boardVo = (BoardVo)obj;
			rlist.add(boardVo);
		}
		
		return rlist;
	}

	@Override
	public int getInsert(BoardVo boardVo) {
		return boardDao.insert(boardVo);
	}

	@Override
	public BoardVo getSelect(String bid) {
		return boardDao.select(bid);
	}

	@Override
	public int getUpdate(BoardVo boardVo) {
		return boardDao.update(boardVo);
	}

	@Override
	public int getDelete(String bid) {
		return boardDao.delete(bid);
	}

	@Override
	public void getUpdateHits(String bid) {
		boardDao.updateHits(bid);
		
	}

}
