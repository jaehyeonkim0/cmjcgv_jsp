package com.mycgv_jsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.PageDao;

@Service("pageService")
public class PageServiceImpl {
	
	@Autowired
	private PageDao pageDao;
	/*
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;
	*/
	//페이징 처리는 list에만 들어간다
	public Map<String, Integer> getPageResult(String page, String serviceName) {
		
		Map<String, Integer> param = new HashMap<String, Integer>();
		
		//페이징 처리 - startCount, endCount 구하기
		int startCount = 0;
		int endCount = 0;
		int pageSize = 0;	//한페이지당 게시물 수
		int reqPage = 1;	//요청페이지; default page
		int pageCount = 1;	//전체 페이지 수
		int dbCount = 0;	//DB에서 가져온 전체 행수
		
		if(serviceName.equals("notice")) {
			//매개변수 serviceType을 noticeService 변환
			//들어올 땐 Object 타입이라 형변환 해야됨
			//noticeService = (NoticeService)serviceType;
			dbCount = pageDao.totalRowCount(serviceName);
			pageSize = 3;
		}else if(serviceName.equals("member")) {
			dbCount = pageDao.totalRowCount(serviceName);
			pageSize = 3;
		}else if(serviceName.equals("board")) {
			dbCount = pageDao.totalRowCount(serviceName);
			pageSize = 4;
		}
		
		//총 페이지 수 계산
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//요청 페이지 계산
		if(page != null){ 
			reqPage = Integer.parseInt(page);
			startCount = (reqPage-1) * pageSize+1; 
			endCount = reqPage *pageSize;
		}else{ //처음 로딩할때(page=null일 때)는 요청 페이지 없음 무조건 1페이지여서 startCount(데이터 1번부터) endCount(5번까지 보여줘라)
			startCount = 1;
			endCount = pageSize;
		}
		
		//param에 데이터 추가
		param.put("startCount", startCount);
		param.put("endCount", endCount);
		param.put("pageSize", pageSize);
		param.put("page", reqPage);
		param.put("maxSize", pageCount);
		param.put("totals", dbCount);
		
		return param;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
