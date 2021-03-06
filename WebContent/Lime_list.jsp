<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.guestbook.dao.*"%>
<%@ page import="kr.guestbook.domain.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.IOException"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>Lime 유지보수 사이트</title>

<!-- 부트스트랩 -->
<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/css/style.css" rel="stylesheet">
<link href="assets/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="vendors/material-icons/material-design-iconic-font.min.css" rel="stylesheet">
<script src="assets/js/jquery-3.1.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script>
	function searchCheck(frm){
        //검색
        if(frm.keyWord.value ==""){
            alert("검색 단어를 입력하세요.");
            frm.keyWord.focus();
            return;
        }
        frm.submit();      
    }
    </script>
<%
    request.setCharacterEncoding("UTF-8");

  //한 화면에 몇 개의 글(행, 레코드)을 보여줄지 지정
  	int pageSize = 10;

  	//선택한 페이지 번호 반환
  	String pageNum = request.getParameter("pageNum");
  	// 초기 page 설정
  	if(pageNum == null){
  		pageNum = "1";
  	}
  	int currentPage = Integer.parseInt(pageNum);
  	int pageN  = Integer.parseInt(pageNum) -1 ;
    int count = 0;
	int cnt = 1;
	
	List<Lime> resvList = null;
	Lime lime = new Lime();
	LimeDao dao = LimeDao.getInstance();
	
	String keyField = "";
	String keyWord = "";
	String keyW = (String)session.getAttribute("keyW");
	String keyF = (String)session.getAttribute("keyF");
	
	// keyField session
	if(request.getParameter("keyField")!=null){
		keyField = request.getParameter("keyField");
		session.setAttribute("keyF",keyField);
		keyF = (String)session.getAttribute("keyF");
		lime.setKeyField(keyF);
	}else{
		keyField = null;
		lime.setKeyField(keyF);
	}
	
	// keyword session 
	if(request.getParameter("keyWord")!=null){
		keyWord = request.getParameter("keyWord");
		session.setAttribute("keyW",keyWord);
		keyW = (String)session.getAttribute("keyW");
		lime.setKeyWord(keyW);

	}else{
		keyWord = null;
		lime.setKeyWord(keyW);
	}

%>
</head>
<body>
<div id="wrap">
	
		<div class="container" >

			<div class="card">
			
				<%@ include file="/include/header.jsp"%>
				
				<div class="card-header">
					<h2 class="line"><a href="Lime_list.jsp">고객문의</a></h2>
				</div>

				<div class="card-body">
					<div class="col-md-4">
						<form name="serach" method="post" class="form1">
							<select class="choice" name="keyField">
				                <option value="All"> ----선택----</option>
				                <option value="a.question_id">접수번호</option>
				                <option value="a.member_name">요청자<option>  
				                <option value="a.question_progress">작업진행도</option>  
				                <option value="a.question_day">등록시간</option>
				                <option value="a.request_id">요청내용</option>
							</select> 
							<input type="text" name="keyWord" class="text" /> 
							<input class="btn-Lime btn" type="button" value="검색" onclick="searchCheck(form)" />
						</form>
					</div>
					
					<div class="hoho col-md-6">
						<form name="serach" action="./util/reset.jsp">
							<input type="hidden" value="lime" name ="what"> 
							<input class="btn-Lime btn" type="submit" value="초기화">
						</form>
					</div>
					
					<div class="col-md-2">
						<div class="totoyo">
							<a href="Lime_write.jsp">
							<span class="btn-Lime btn">
								글쓰기
							</span>
							</a>
						</div>
					</div>

				</div>
				<div class="col-md-12">
					<div class="table-responsive">
						<table id="data-table-selection" class="table table-condensed">
							<thead>
								<tr class="tr-color">
									<th class="table-sort">접수번호</th>
									<th class="table-sort">요청자</th>
									<th class="table-sort">작업진행도</th>
									<th class="table-sort">요청 시간</th>
									<th class="table-sort">접수 시간</th>
									<th class="table-sort">업무 구분</th>
									<th class="table-sort">작업담당자</th>
									<th class="table-sort">요청내용</th>
								</tr>
							</thead>
							<tbody>
			<%
			try{
				pageN = pageN * pageSize;
			  	count = dao.getCount(lime);
				if(count > 0){
			  		resvList = dao.resvList(lime, pageN, pageSize);
			  	}
				for(Lime resv : resvList){
						String progress = resv.getQuestion_progress();
						String content = resv.getQuestion_content();
						if(content.length()>15){
							content = content.substring(0,15) + "...";
						}
						
						if(progress.isEmpty()){
				%>
				  <tr class="warning"  style="cursor: pointer; " onclick="location.href='Lime_detail.jsp?q_id=<%=resv.getQuestion_id()%>'">
				  	<td><%=resv.getQuestion_id()%></td>
				  	<td><%=resv.getMember_name() %></td>
				  	<td><%=resv.getQuestion_progress() %></td>
				  	<td><%=resv.getQuestion_day()%></td>
				  	<td><%=resv.getReply_day()%></td>
				  	<td><%=resv.getQuestion_division()%></td>
				  	<td><%=resv.getQuestion_manager()%></td>
				  	<td><%=content %></td>
				 </tr>
				<%
						}else{
				%>
				  <tr class="success"  style="cursor: pointer; " onclick="location.href='Lime_detail_reply.jsp?q_id=<%=resv.getQuestion_id()%>'">
				  	<td><%=resv.getQuestion_id()%></td>
				  	<td><%=resv.getMember_name() %></td>
				  	<td><%=resv.getQuestion_progress() %></td>
				  	<td><%=resv.getQuestion_day()%></td>
				  	<td><%=resv.getReply_day()%></td>
				  	<td><%=resv.getQuestion_division()%></td>
				  	<td><%=resv.getQuestion_manager()%></td>
				  	<td><%=content %></td>
				 </tr>
				<%
						}
					}
				%>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-md-12">
					<nav>
						<div class="yoho">
			<%
					//한 화면에 몇 개의 페이지 번호가 보여지는지 지정
					int pageBlock = 5;
			        //총 페이지 수
			        int pageCount = (count - 1)/pageSize + 1;
			        int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
			        int endPage = startPage + pageBlock - 1;
			        
			        if(endPage > pageCount) endPage = pageCount;
			 %>
			<nav>
			  <ul class="pagination">
			<%
			        if(startPage > pageBlock){
			%>
			    <li>
			      <a href="Lime_list.jsp?pageNum=<%=startPage -1 %>" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			<%        	
			      }else{
			%>
			    <li class="disabled">
			      <a href="#" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			<%
			      }

			for(int i=startPage;i<=endPage;i++){
			        if(i==currentPage){//페이지번호와 현재 페이지 번호일치
			%>
						<li class="active"><a href="#"><%= i %> <span class="sr-only">(current)</span></a></li>
						
			<%        		
			        	}else{//페이지번호와 현재 페이지 번호 불일치
			        		
			%>
						<li><a href="Lime_list.jsp?pageNum=<%=i%>"><%=i%> <span class="sr-only">(current)</span></a></li>
						
			<%        		
			        	}
			}
			        if(endPage < pageCount){
			%>
				<li>
				<a href="Lime_list.jsp?pageNum=<%=startPage + pageBlock%>" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			    </a>
			    </li>
			  </ul>
			</nav>
			<%
			      }
			}catch (Exception e) {
				session.removeAttribute("keyW");
				session.removeAttribute("keyF");
				response.getWriter().print("<script>alert('검색결과가 없습니다.')</script>");	
				response.getWriter().print("<script>location.href='Lime_list.jsp'</script>   ");	
			}
		%>
						</div>
					</nav>
				</div>
		<%@ include file="/include/footer2.jsp"%>
			</div>
		</div>
	</div>
</body>
</html>