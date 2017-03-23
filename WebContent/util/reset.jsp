<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
session.removeAttribute("keyW");
session.removeAttribute("keyF");
session.removeAttribute("sort2");

String what = request.getParameter("what");
	if(what.equals("admin")){
		response.sendRedirect("../admin_list.jsp");	
	}else if(what.equals("lime")){
		response.sendRedirect("../Lime_list.jsp");	
	}
%>

