<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 
<%@ page import="java.io.IOException" %>

<%
	LimeDao dao = LimeDao.getInstance();
	Lime lime = new Lime();
	
	try{
		String reply_idS = request.getParameter("reply_id");
		int reply_id = Integer.parseInt(reply_idS);
		String member_id = (String)session.getAttribute("id");
		String member_name = (String)session.getAttribute("name");
		String manager = request.getParameter("manager");
		String keyword = request.getParameter("keyword");
		String question_progress = request.getParameter("question_progress");
		String question_division = request.getParameter("division");
		String reply_priority = request.getParameter("reply_priority");
		String reply_method = request.getParameter("reply_method");
		String detailDivision = request.getParameter("detailDivision");
		String reply_term = request.getParameter("term");
		String reply_day = request.getParameter("reply_day");
		String reply_content = request.getParameter("reply_content");
		String progress_ago = request.getParameter("progress_ago");
		
		System.out.println("------------------------------------------------");
		
		System.out.println(question_progress);
		System.out.println(question_division);
		
		System.out.println("------------------------------------------------");
		
 		lime.setMember_id(member_id);
 		lime.setMember_name(member_name);
		lime.setReply_id(reply_id);
 		lime.setQuestion_manager(manager);
		lime.setReply_keyword(keyword);
		lime.setQuestion_progress(question_progress);
		lime.setQuestion_division(question_division);
		lime.setReply_priority(reply_priority);
		lime.setReply_method(reply_method);
		lime.setDetailDivision(detailDivision);
		lime.setReply_term(reply_term);
		lime.setReply_day(reply_day);
		lime.setReply_content(reply_content);	
		lime.setprogress_ago(progress_ago);
		
		dao.work_update(lime);
%>
		<script>
			alert('수정 완료');
			location.href="../admin_list.jsp";
		</script>
<%
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