package com.mycgv_jsp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionAuthInterceptor extends HandlerInterceptorAdapter {
	//HandlerInterceptorAdapter is class, so use extends
	/**
	 * preHandle : Controller�� �����ϱ� ���� ����Ǵ� �޼ҵ�; 
	 * 			   executed METHODS before access to Controller
	 * */
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
								HttpServletResponse response, Object handler) throws Exception {
		//session�� ��Ʋ����ó�� �׳� ��� �Դٰ����ϴ� ���ε� �̰� Ŭ���̾�Ʈ���� ��û�� ���� �� request�� �پ �Ѿ��
		
		//Ŭ���̾�Ʈ(������)�� ��û ȭ�� - ���� ��ü ��������
		HttpSession session = request.getSession();
		
		//���ǿ�  sid(���̵�)�� �ִ��� ������ Ȯ��
		//�α��ο� ���������� �� �� ���ǿ� ���� �ִµ�, ���⼭ Ȯ���ϴ°�
		String sid = (String)session.getAttribute("sid");
		if(sid == null) { //�α��� ���Ѱ�; ������ �� �ڵ带 ���������� �־��µ�, ���� ��ü �ϳ� �����ؼ� �� üũ
			//�α����� �ȵǾ� �ִ� �����̹Ƿ� �α��������� ����
			response.sendRedirect("/mycgv_jsp/login.do"); //�ƴϸ� �ּ� �� �� ��� ��; http://localhost~
			//return type = boolean; ��û�� �������� �� ���� ������ false
			return false;
			//if���� �Ȱɷȴٴ� �Ÿ� return true �� ���� �������� �������� �� �� �ִ�(return true)
		}
		return true;
	}
}
