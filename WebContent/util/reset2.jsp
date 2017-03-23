<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<script>
			alert("검색 결과2323가 없습니다.");
		</script>
<%
session.removeAttribute("keyW");
session.removeAttribute("keyF");
session.removeAttribute("sort2");

		response.sendRedirect("../admin_list.jsp");	
	
%>

