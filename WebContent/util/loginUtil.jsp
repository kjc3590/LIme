<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 
<%@ page import="java.io.IOException"%>
	<%
		String id = request.getParameter("id");
		String userpw = request.getParameter("pw");
		
		LimeDao dao = LimeDao.getInstance();
		
		try{
		Lime login = dao.Login(id);
		
		String c_name = login.getCompany_name();
		String name = login.getMember_name();
		String type = login.getMember_type();
		
		System.out.println(type);
		
		boolean loginCheck = false;
		
		if(login != null){
			loginCheck = login.isCheckedPasswd(userpw);
		}
		if(type.equals("wait")){
		%>
		<script>
			 alert('회원승인 대기중 입니다.');
				 history.go(-1);
		</script>
		<%
		}else if(loginCheck){
			session.setAttribute("id", id);
			System.out.println(session.getAttribute("id"));
			session.setAttribute("c_name", c_name);
			session.setAttribute("name", name);
			session.setAttribute("type", type);
			response.sendRedirect("../index.jsp");
		}else{
	%>
		<script>
			 alert('아이디 또는 비밀번호가 불일치합니다.');
		 		history.go(-1);
		</script>
	<%			
		}
		}catch(Exception e){
			%>
			<script>
				 alert('아이디가 존재하지 않습니다.');
			 		history.go(-1);
			</script>
		<%	
			e.printStackTrace();
		}
	%>