<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYCGV</title>
<link rel="stylesheet" href="http://localhost:9000/mycgv_jsp/css/mycgv_jsp.css">
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- content -->
	<div class="content">
		<section class="admin">
			<h1 class="title">관리자 메인</h1>
			<div>
				<article><a href="admin_notice_list.do">🛕 공지사항 관리</a></article>
				<article>🚗영화 관리</article>
				<article><a href="admin_member_list.do">👩회원 관리</a></article>
			</div>
		</section>
	</div>
	
	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>














