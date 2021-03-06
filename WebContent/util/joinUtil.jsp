<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 
<%@ page import="java.util.List" %>

<%
	LimeDao dao = LimeDao.getInstance();
	Lime lime = new Lime();
	
	try{
		int pk = 8;		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String phonetel = request.getParameter("phonetel");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String c_name = request.getParameter("c_name");

		if(c_name.equals("파인인사이트")){
			pk =5;
		}else if(c_name.equals("미디어R&D")){
			pk=6;
		}else if(c_name.equals("베스티안병원")){
			pk=7;
		}
		
		lime.setMember_id(id);		
		lime.setMember_pw(pw);		
		lime.setMember_name(name);		
		lime.setCompany_pk(pk);		
		lime.setCompany_name(c_name);		
		lime.setMember_ptel(phonetel);		
		lime.setMember_tel(tel);		
		lime.setMember_email(email);		

		dao.Lime_join(lime);
		%>
			<script>
				alert("회원가입 완료");
				location.href="../index.jsp";
			</script>		
		<%
	}catch(Exception e){
	%>
		<script>
			alert("회원가입 실패");
			history.go(-1);
		</script>		
	<%
	 e.printStackTrace();	
	}
%>