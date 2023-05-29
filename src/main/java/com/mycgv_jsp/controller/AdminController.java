package com.mycgv_jsp.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.service.NoticeService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	//DI할 때는 따로 autowired 붙여야됨
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private PageServiceImpl pageServiceImpl;
	
	@RequestMapping(value="admin_index.do", method=RequestMethod.GET)
	public String admin_index() {
		return "/admin/admin_index";
	}
	
	@RequestMapping(value="/admin_notice_list.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_list(String page) {
		
		ModelAndView mav = new ModelAndView();
		Map<String, Integer> param = pageServiceImpl.getPageResult(page, "notice");
		
		ArrayList<NoticeVo> list = noticeService.getSelect(param.get("startCount"), param.get("endCount"));
		
		mav.addObject("list", list);
		mav.setViewName("/admin/notice/admin_notice_list");
		mav.addObject("totals", param.get("totals"));
		mav.addObject("pageSize", param.get("pageSize"));
		System.out.println(param.get("pageSize"));
		mav.addObject("maxSize", param.get("maxSize"));
		mav.addObject("page", param.get("page"));
		
		return mav;
	}
	
	@RequestMapping(value="/admin_notice_write.do", method=RequestMethod.GET)
	public String admin_notice_write() {
		
		return "/admin/notice/admin_notice_write";
	}
	
	@RequestMapping(value="/admin_notice_write_proc.do", method=RequestMethod.POST)
	public String admin_notice_write_proc(NoticeVo noticeVo) {
		String viewName = "";
		int result = noticeService.getInsert(noticeVo);
		
		if(result==1)
			viewName = "redirect:/admin_notice_list.do";
		
		return viewName;
	}
	
	@RequestMapping(value="/admin_notice_content.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_content(String nid) {
		
		ModelAndView mav = new ModelAndView();
		NoticeVo noticeVo = noticeService.getSelect(nid);
		
		mav.addObject("noticeVo", noticeVo);
		mav.setViewName("/admin/notice/admin_notice_content");
		
		return mav;
	}
	
	@RequestMapping(value="/admin_notice_update.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_update(String nid) {
		
		ModelAndView mav = new ModelAndView();
		
		NoticeVo noticeVo = noticeService.getSelect(nid);
		
		mav.addObject("noticeVo", noticeVo);
		mav.setViewName("/admin/notice/admin_notice_update");
		
		return mav;
	}
	
	@RequestMapping(value="/admin_notice_update_proc.do", method=RequestMethod.POST)
	public ModelAndView admin_notice_update_proc(NoticeVo noticeVo) {
		
		ModelAndView mav = new ModelAndView();
		int result = noticeService.getUpdate(noticeVo);
		
		if(result==1)
			mav.setViewName("redirect:/admin_notice_list.do");
		
		return mav;
	}
	
	@RequestMapping(value="/admin_notice_delete.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_delete(String nid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("nid", nid);
		mav.setViewName("/admin/notice/admin_notice_delete");
		
		return mav;
		
	}
	
	@RequestMapping(value="/admin_notice_delete_proc.do", method=RequestMethod.POST)
	public String admin_notice_delete_proc(String nid) {
		String viewName="";
		int result = noticeService.getDelete(nid);
		
		if(result==1)
			viewName = "redirect:/admin_notice_list.do";
		
		return viewName;
	}
	
	
	//회원관리 admin_member_list.do
	@RequestMapping(value="/admin_member_list.do", method=RequestMethod.GET)
	public ModelAndView admin_member_list(String page) {
		ModelAndView mav = new ModelAndView();
		Map<String, Integer> param = pageServiceImpl.getPageResult(page, "member");
		
		//ArrayList<MemberVo> list = memberDao.select(startCount, endCount);
		ArrayList<MemberVo> list = memberService.getList(param.get("startCount"), param.get("endCount"));
		
		mav.addObject("list", list);
		mav.setViewName("/admin/member/admin_member_list");
		mav.addObject("totals", param.get("totals"));
		mav.addObject("pageSize", param.get("pageSize"));
		mav.addObject("maxSize", param.get("maxSize"));
		mav.addObject("page", param.get("page"));
		
		return mav;
		
		
	}

}
