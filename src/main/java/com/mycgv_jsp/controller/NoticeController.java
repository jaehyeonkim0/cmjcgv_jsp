package com.mycgv_jsp.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.NoticeService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private PageServiceImpl pageServiceImpl;
	
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
	public ModelAndView notice_list(String page) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Integer> param = pageServiceImpl.getPageResult(page, "notice");
		
		ArrayList<NoticeVo> list = noticeService.getSelect(param.get("startCount"), param.get("endCount"));
		
		mav.addObject("list", list);
		mav.setViewName("/notice/notice_list");
		mav.addObject("totals", param.get("totals")); //
		mav.addObject("pageSize", param.get("pageSize"));
		mav.addObject("maxSize", param.get("maxSize"));
		mav.addObject("page", param.get("page"));
		
		return mav;
	}

	@RequestMapping(value="/notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		
		ModelAndView mav = new ModelAndView();
		NoticeVo noticeVo = noticeService.getSelect(nid);
		
		mav.addObject("noticeVo", noticeVo);
		mav.setViewName("/notice/notice_content");
		if(noticeVo != null) {
			noticeService.getUpdateNhits(nid);
		}
		return mav;
	}
	
}
