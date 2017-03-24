<%@page import="oracle.net.aso.p"%>
<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*" %> 
<%@ page import="kr.guestbook.domain.*" %> 
<%@ page import="java.io.IOException" %>
<%-- <input type="hidden" value="<%=resv.getTranslation_id()%>" name ="real_id">  --%>

<%
/* 		
 */


	request.setCharacterEncoding("UTF-8");
	LimeDao dao = LimeDao.getInstance();
	Lime lime = new Lime();
	Lime lime_ago = new Lime();
	 
	try{
		String trans_ch = null;
		String trans_eng = null;
		String trans_bch = null;
		String trans_beng = null;

		String trans_m_c = null;
		String trans_m_e = null;

		String trans_b_m_c = null;
		String trans_b_m_e = null;
		
		String manager = (String)session.getAttribute("name");
		
		String iS = request.getParameter("ii");
		int ii = Integer.parseInt(iS);
		
		System.out.println ("iiUtil : " + ii);
		
		for(int i=1; i<=ii; i++ ){
		System.out.println ("i : " + i);
		
		
			String idd = "id" +i;
			trans_bch = "trans_ch" +i;
			trans_beng = "trans_eng" +i;
			trans_b_m_c = "trans_m_c" +i;
			trans_b_m_e = "trans_m_e" +i;
			
			String idS = request.getParameter(""+idd+"");
			int id = Integer.parseInt(idS);
			lime.setTranslation_id(id);

	 		lime_ago = dao.translate_ago(id);
	 		String chinese_ago = lime_ago.getTranslation_chinese();
	 		String english_ago = lime_ago.getTranslation_english();
	 		
	 		
	 		//ago 아이디넣기
	 		
		
			trans_ch = request.getParameter(""+trans_bch+"");
			trans_eng = request.getParameter(""+trans_beng+"");
			
			//예전 매니저찾기
			trans_m_c = request.getParameter(""+trans_b_m_c+"");
			trans_m_e = request.getParameter(""+trans_b_m_e+"");
	 		
	 		if(trans_ch==""&&trans_eng==""){
	 			trans_ch ="";
	 			trans_eng="";
	 			trans_m_c = "";
	 			trans_m_e = "";
	 		}else if (trans_ch!=""&&trans_eng==""){
	 			if(trans_ch.equals(chinese_ago)){
	 			trans_eng="";
	 			trans_m_e = "";
	 			}else{
	 				trans_eng="";
		 			trans_m_c = manager;
		 			trans_m_e = "";
	 			}
	 			
	 		}else if (trans_ch=="" && trans_eng!=""){
	 			if(trans_eng.equals(english_ago)){
		 			trans_ch ="";
		 			trans_m_c = "";
	 			}else{
		 			trans_m_e = manager;
		 			trans_ch ="";
		 			trans_m_c = "";
	 			}
	 			
	 		}else if(trans_ch!="" && trans_eng!=""){
	 			
	 			if(trans_m_c==""){
	 				trans_m_c = manager;
	 				System.out.println("결과1 : " + trans_m_c);
	 			}
	 			if(trans_m_e==""){
	 				trans_m_e = manager;
	 				System.out.println("결과2 : " + trans_m_e);
	 			}
	 			
	 			if(trans_eng.equals(english_ago)){
	 			}else{
		 			trans_m_e = manager;
	 			}
	 			
	 			if(trans_ch.equals(chinese_ago)){
		 		}else{
			 		trans_m_c = manager;
		 		} 
	 		}
			
	 		lime.setTranslation_chinese(trans_ch);
	 		lime.setTranslation_english(trans_eng);
	 		lime.setTranslation_manager_chinese(trans_m_c);
			lime.setTranslation_manager_english(trans_m_e);
	 		
	//모두 있을떄 -> manager1의 값을 받아올 수 있다면 받아오고 없다면 써주고,.		
			
			dao.Translation_update(lime);
		}
		
%>
		<script>
			alert('수정 완료');
			location.href="../Lime_translation.jsp";
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