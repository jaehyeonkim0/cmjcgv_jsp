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
	//����¡ ó���� list���� ����
	public Map<String, Integer> getPageResult(String page, String serviceName) {
		
		Map<String, Integer> param = new HashMap<String, Integer>();
		
		//����¡ ó�� - startCount, endCount ���ϱ�
		int startCount = 0;
		int endCount = 0;
		int pageSize = 0;	//���������� �Խù� ��
		int reqPage = 1;	//��û������; default page
		int pageCount = 1;	//��ü ������ ��
		int dbCount = 0;	//DB���� ������ ��ü ���
		
		if(serviceName.equals("notice")) {
			//�Ű����� serviceType�� noticeService ��ȯ
			//���� �� Object Ÿ���̶� ����ȯ �ؾߵ�
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
		
		//�� ������ �� ���
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//��û ������ ���
		if(page != null){ 
			reqPage = Integer.parseInt(page);
			startCount = (reqPage-1) * pageSize+1; 
			endCount = reqPage *pageSize;
		}else{ //ó�� �ε��Ҷ�(page=null�� ��)�� ��û ������ ���� ������ 1���������� startCount(������ 1������) endCount(5������ �������)
			startCount = 1;
			endCount = pageSize;
		}
		
		//param�� ������ �߰�
		param.put("startCount", startCount);
		param.put("endCount", endCount);
		param.put("pageSize", pageSize);
		param.put("page", reqPage);
		param.put("maxSize", pageCount);
		param.put("totals", dbCount);
		
		return param;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
