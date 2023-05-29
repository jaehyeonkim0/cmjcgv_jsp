package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.vo.NoticeVo;

public interface NoticeService {
	
	ArrayList<NoticeVo> getSelect(int startCount, int endCount);
	int getInsert(NoticeVo noticeVo);
	NoticeVo getSelect(String bid);
	int getUpdate(NoticeVo noticeVo);
	int getDelete(String bid);
	void getUpdateNhits(String bid);
	int getTotalRowCount();

}
