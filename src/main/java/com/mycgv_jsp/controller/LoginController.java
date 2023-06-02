package com.mycgv_jsp.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class LoginController {
	
	//DI(스프링이 알아서 dependency injection해준다(스프링 컨테이너가 객체를 필요한 곳에 주입시켜준다))
	@Autowired
	private MemberService memberService;
	
	//리턴값이 단순 String이므로 DB와 연동 안함 -> Service로 뺄 필요 X
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	//단순 리턴값이 String이기 때문에 DB와 연동 안함 -> Service로 뺄 필요 X
	@RequestMapping(value="/login_fail.do", method=RequestMethod.GET)
	public String login_fail() {
		return "/login/login_fail";
	}
	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		String sid = (String)session.getAttribute("sid");
		if(sid != null) {
			session.invalidate();
		}
		
		return "redirect:/index.do";
	}
	
	//DB연동 하기 때문에 Service로 뺌
	@RequestMapping(value="/login_proc.do", method=RequestMethod.POST)
	public ModelAndView login_proc(MemberVo memberVo, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		/*아래 2줄이 service가 하는것
		MemberDao memberDao = new MemberDao();
		int result = memberDao.loginCheck(memberVo);
		*/
		/* MemberServiceImpl memberService = new MemberServiceImpl(); */
		int result = memberService.getLoginResult(memberVo);
		
		if(result==1) {
			session.setAttribute("sid", memberVo.getId());
			mav.addObject("login_result", "ok");
			mav.setViewName("redirect:/index.do");
			//session.setAttribute("uid", memberVo.getId());
		}else {
			mav.setViewName("redirect:/login_fail.do");
		}
		
		return mav;
	}

}
