<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = (String)session.getAttribute("sid");
	
	if(sid != null) {
		session.invalidate();
		out.write("<script>");
		out.write("alert('로그아웃 성공');");
		out.write("location.href='http://localhost:9000/mycgv_jsp/index.jsp'");		
		out.write("</script>");
		/* response.sendRedirect("http://localhost:9000/mycgv_jsp/index.jsp"); */
	}

%>
