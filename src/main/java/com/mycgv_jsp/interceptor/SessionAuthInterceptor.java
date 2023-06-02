package com.mycgv_jsp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionAuthInterceptor extends HandlerInterceptorAdapter {
	//HandlerInterceptorAdapter is class, so use extends
	/**
	 * preHandle : Controller에 접근하기 전에 수행되는 메소드; 
	 * 			   executed METHODS before access to Controller
	 * */
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
								HttpServletResponse response, Object handler) throws Exception {
		//session은 셔틀버스처럼 그냥 계속 왔다갔다하는 놈인데 이게 클라이언트한테 요청이 들어올 때 request에 붙어서 넘어옴
		
		//클라이언트(브라우저)의 요청 화인 - 세션 객체 가져오기
		HttpSession session = request.getSession();
		
		//세션에  sid(아이디)가 있는지 없는지 확인
		//로그인에 성공했으면 이 때 세션에 값을 넣는데, 여기서 확인하는것
		String sid = (String)session.getAttribute("sid");
		if(sid == null) { //로그인 안한것; 원래는 이 코드를 페이지마다 넣었는데, 이젠 객체 하나 생성해서 다 체크
			//로그인이 안되어 있는 상태이므로 로그인폼으로 전송
			response.sendRedirect("/mycgv_jsp/login.do"); //아니면 주소 싹 다 적어도 됨; http://localhost~
			//return type = boolean; 요청한 페이지로 못 가기 때문에 false
			return false;
			//if문에 안걸렸다는 거면 return true 로 내가 가려고한 페이지에 갈 수 있다(return true)
		}
		return true;
	}
}
