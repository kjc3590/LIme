<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
				<div class="topP row no-margin">
					<div class="col-md-6  nopadding">
						<div class="col-md-3 nopadding">
							<div class="nav-logo">
								<a href="index.jsp">
									<img src="assets/images/logo.png">
								</a>							
							</div>

						</div>
						<div class="col-md-7">
							<div class="logo-content">
								<p class="nav-logotitle">라임 유지보수 페이지</p>
							</div>
						</div>
						<div class="col-md-2"></div>
					</div>
					<div class="col-md-6 nopadding">
							<div class="nav-logotitle-right">
								<div class="col-md-3">
									<a href="index.jsp" class="nav-title">홈</a>
								</div>
		<%
			if(session.getAttribute("id")==null){
		%>

								<div class="col-md-3">
									<a href="" class="nav-title" onclick="nologin()">문의하기</a>
								</div>
								<div class="col-md-3">
									<a href="Lime-Edu_nd.jsp" class="nav-title">교육안내</a>
								</div>
		<%
		}else{
			if(session.getAttribute("type").equals("user") || session.getAttribute("type").equals("userO")){  
		%>
								<div class="col-md-3">
									<a href="Lime_list.jsp" class="nav-title">문의하기</a>
								</div>
								<div class="col-md-3">
									<a href="Lime-Edu_nd.jsp" class="nav-title">교육안내</a>
								</div>
		
		<%		
			}else if (session.getAttribute("type").equals("super")){
				 %>		
								<div class="col-md-3">
									<a href="admin_list.jsp" class="nav-title">문의/답변</a>
								</div>
								<div class="col-md-3">
									<a href="join_accept.jsp" class="nav-title">회원관리</a>
								</div>
		<% 
			}
		}
 		%>
								<div class="col-md-3">
									<a href="Lime-notice_nd.jsp" class="nav-title">고객센터</a>
								</div>
							</div>
					</div>
						<!-- TRIM 생성  -->
					</div>
				<script>
					function nologin(){
						alert("로그인을 하신 후 사용할 수 있습니다.");
						location.href="index.jsp";
					}
				</script>
