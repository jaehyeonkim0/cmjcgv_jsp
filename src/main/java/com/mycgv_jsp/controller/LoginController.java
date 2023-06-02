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
	
	//DI(�������� �˾Ƽ� dependency injection���ش�(������ �����̳ʰ� ��ü�� �ʿ��� ���� ���Խ����ش�))
	@Autowired
	private MemberService memberService;
	
	//���ϰ��� �ܼ� String�̹Ƿ� DB�� ���� ���� -> Service�� �� �ʿ� X
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	//�ܼ� ���ϰ��� String�̱� ������ DB�� ���� ���� -> Service�� �� �ʿ� X
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
	
	//DB���� �ϱ� ������ Service�� ��
	@RequestMapping(value="/login_proc.do", method=RequestMethod.POST)
	public ModelAndView login_proc(MemberVo memberVo, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		/*�Ʒ� 2���� service�� �ϴ°�
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
