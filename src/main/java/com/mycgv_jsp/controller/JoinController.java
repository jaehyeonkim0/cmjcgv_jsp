package com.mycgv_jsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class JoinController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "/join/join";
	}
	
	@RequestMapping(value="/join_proc.do", method=RequestMethod.POST)
	public ModelAndView join_proc(MemberVo memberVo) {
		
		ModelAndView mav = new ModelAndView();
		/*
		MemberDao memberDao = new MemberDao();
		int result = memberDao.insert(memberVo);
		*/
		//MemberServiceImpl memberService = new MemberServiceImpl();
		int result = memberService.getJoinResult(memberVo);
		
		if(result==1) {
			mav.addObject("join_result", "ok");
			mav.setViewName("/login/login");
		}else {
			
		}
		
		return mav;
	}
	//ResponseBody가 있으면 viewResolver한테 안 가고 js로 감
	@RequestMapping(value="/id_check.do", method=RequestMethod.GET)
	@ResponseBody
	public String id_check(String id) {
		/*
		MemberDao memberDao = new MemberDao();
		int result = memberDao.idCheck(id);
		*/
		
		//MemberServiceImpl memberService = new MemberServiceImpl();
		return memberService.getIdCheckResult(id);
	}

}
