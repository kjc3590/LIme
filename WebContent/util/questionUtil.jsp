<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.lang.ProcessBuilder.Redirect"%>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 

<!-- 파일업로드 및 예약정보 DB--------------------------------------------------------------------------------------  -->
<%
	/* 파일 업로드 할때는 절대경로로 넣어주어야 함 */
	String realFolder = "";		//웹어플리케이션상의 절대경로
	LimeDao dao = LimeDao.getInstance();
	Lime lime = new Lime();
	
	//파일 업로드 경로(upload란 이름의 폴더 만들어 놓기)
	//현재 상대경로임
	String saveFolder = "/upload";
	//인코딩 타입
	String encType = "utf-8";
	//최대 업로드 사이즈 지정(5MB)
	int maxSize = 5*1024*1024;
	
	ServletContext context = getServletContext();
	//현재 upload 페이지의 웹어플리케이션상의 절대경로 구함
	realFolder = context.getRealPath(saveFolder);

	try {
		MultipartRequest multi = new MultipartRequest(request,
													realFolder,	//upload 절대경로
													maxSize,	//최대 업로드 사이즈
													encType,	//인코딩 처리
													new DefaultFileRenamePolicy());	//이름 겹치면 이름 바꿔줌
		String id  = (String)session.getAttribute("id");
		String company_name = multi.getParameter("company_name");
		String tel = multi.getParameter("tel");
		String member_name = multi.getParameter("member_name");
		String ptel = multi.getParameter("ptel");
		String email = multi.getParameter("email");
		String division = multi.getParameter("division");
		String content = multi.getParameter("content");
		String manager = null;
		
		/* 업무별 담당자 
		
		if(division.equals("원무")){
			manager = "담군";
		}else if(division.equals("진료/지원")){
			manager = "다군";
		}else if(division.equals("진료/간호")){
			manager = "비군";
		}else if(division.equals("EMR")){
			manager = "오군";
		}else{
			manager= "";
		} */

		//파일 정보 처리
		//서버에 저장된 파일명 구하기
		String filename = multi.getFilesystemName("uploadFile");
		System.out.println(filename);
		
		String path = realFolder +"\\"+ filename;
		
		lime.setMember_id(id);
		lime.setCompany_name(company_name);
		lime.setMember_tel(tel);
		lime.setMember_name(member_name);
		lime.setMember_ptel(ptel);
		lime.setMember_email(email);
		lime.setQuestion_division(division);
		lime.setQuestion_content(content);
		lime.setQuestion_filename(filename);
		lime.setQuestion_attach(path);
		lime.setQuestion_manager(manager);
		
		dao.resv(lime);
		dao.work_resv();
	
%>
		<script>
			alert('접수 완료');
			location.href="../Lime_list.jsp";
		/* forward page ="index_dbO.jsp"; */
		</script>
<%
	}catch(IOException e) {
%>
		<script>
			alert('접수 실패');
			location.href="../Lime_list.jsp";
			</script>
		<%
		e.printStackTrace();
	} catch(Exception e) {
		%>
		<script>
			alert('접수 실패');
			location.href="../Lime_list.jsp";
			</script>
		<%
			e.printStackTrace();
		}
		%>