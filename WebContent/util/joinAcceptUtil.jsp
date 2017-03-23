<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*"%>
<%@ page import="kr.guestbook.domain.*"%>
<%
	String id = request.getParameter("id");
	LimeDao dao = LimeDao.getInstance();
	dao.join_accept(id);
%>
	<script>
		alert("승인 완료");
		location.href="../join_accept.jsp";
	</script>