<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 
<%@ page import="java.io.IOException" %>

<%
	LimeDao dao = LimeDao.getInstance();
	Lime lime = new Lime();
	request.setCharacterEncoding("UTF-8");
	

	
	

	try{
		String trans_ko = request.getParameter("trans_ko");
		String manager = (String)session.getAttribute("name");
		
		
		
		System.out.println("trans_ko: " + trans_ko);
		
		
 		lime.setTranslation_korean(trans_ko);
 		lime.setTranslation_manager_korean(manager);
 	
 		
 	
 		
		if(trans_ko!=""){
			dao.Translation_insert(lime);
%>
		<script>
			alert('등록 완료');
			location.href="../admin_translation.jsp";
		</script>
<%
		}else {
			%>
			<script>
				alert('필수 항목을 입력해주세요');
				location.href="../admin_translation.jsp";
			</script>
	<%
		}
 		
	}catch(Exception e){
%>
		<script>
			alert('필수 항목을 입력해주세요');
			history.go(-1);
		</script>
<%
		e.printStackTrace();
	}
	
%>