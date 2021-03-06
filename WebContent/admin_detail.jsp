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
<!-- 부트스트랩 -->
<script src="assets/js/jquery-3.1.1.min.js"></script>
<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/css/style.css" rel="stylesheet">
<link href="assets/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="vendors/material-icons/material-design-iconic-font.min.css"
	rel="stylesheet">
<script src="vendors/fileinput/fileinput.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>

<script>
	function searchCheck(){
        //검색
        if(document.frm1.manager.value ==""){
            alert("작업담당자를 입력하세요.");
            return false;
        }else if(document.frm1.division.value==""){
            alert("업무구분을 입력하세요.");
            return false;
        }else if(document.frm1.question_progress.value==""){
            alert("작업진행도를 입력하세요.");
            return false;
        }else{
        	return true;
        }
    }
</script>

<%
	List<Lime> resvList = null;
	List<Lime> workList = null;
	LimeDao dao = LimeDao.getInstance();
	String Question_idS = request.getParameter("Question_id");
	int Question_id = Integer.parseInt(Question_idS);

	resvList = dao.workListDetail(Question_id);
	workList = dao.replyDetail(Question_id);
	
	String question_attach = null;
	String question_filename = null;
%>
</head>

<body>
	<section id="content">
		<div class="container">

			<div class="card">
				<%@ include file="/include/header.jsp"%>
				<div class="card-header">
					<h2 class="line">요청목록</h2>

				</div>
				<div class="card-body card-padding">

					<%
						for (Lime lime : resvList) {
							
							question_attach = lime.getQuestion_attach();
							question_filename = lime.getQuestion_filename();
					%>
					<form class="form-horizontal" action="./util/work_insert.jsp"
						name="frm1" onsubmit="return searchCheck();">
						<div class="row">
							<div class="col-md-4">
								<div class="coco col-md-5">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>업체명</p>
											</div>

										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>유선번호</p>
											</div>
										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>첨부파일</p>
											</div>
										</div>
									</div>

								</div>
								<div class="down col-md-7">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													name="request_id" readonly="readonly"
													value="<%=lime.getCompany_name()%>">
											</div>

										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													name="prog_name" readonly="readonly"
													value="<%=lime.getMember_tel()%>">
											</div>

										</div>
									</div>
									
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control" name="prog_name" readonly="readonly" value="<%=question_filename%>" 
													onclick="location.href='http://www.naver.com' " >
											</div>
										</div>
									</div>
									
								</div>
							</div>
							<div class="col-md-4">
								<div class="coco col-md-5">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>요청자명</p>
											</div>
										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>핸드폰번호</p>
											</div>

										</div>
									</div>
								</div>
								<div class="down col-md-7">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control" name="b_name"
													readonly="readonly" value="<%=lime.getMember_name()%>">
											</div>

										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control" name="tel"
													value="<%=lime.getMember_ptel()%>" readonly="readonly">
											</div>

										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="coco col-md-5">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>이메일</p>
											</div>
										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>업무구분</p>
											</div>

										</div>
									</div>
								</div>
								<div class="down col-md-7">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control" name="code"
													readonly="readonly" value="<%=lime.getMember_email()%>">
											</div>

										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													name="phonetel" readonly="readonly"
													value="<%=lime.getQuestion_division()%>">
											</div>

										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-12">
									<div class="form-control3">
										<!--  <p>내용(차트번호,환자성명,내원일)과 발생되는 화면의 스크린샷을 첨부해주세요.</p> -->
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-12">
									<div class="col-md-12">
										<div class="form-group">
											<div class="fg-line">
												<textarea class="leftm form-control" rows="8" name="content"
													readonly="readonly"><%=lime.getQuestion_content()%></textarea>
											</div>
										</div>
									</div>
								</div>
							</div>
							<%
								}
								for (Lime lime : workList) {
									String manager = (String)session.getAttribute("name");
									String division = lime.getQuestion_division();
									String question_progress = lime.getQuestion_progress();
									String member_name = lime.getMember_name();
									String detailDivision = lime.getDetailDivision();
									String reply_day= lime.getReply_day();
									String reply_keyword = lime.getReply_keyword();
									String reply_priority = lime.getReply_priority();
									String reply_method = lime.getReply_method();
									String reply_term = lime.getReply_term();
									String reply_content = lime.getReply_content();

							%>
							<div class="col-md-4">
								<div class="coco2 col-md-5">

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p class="ne">작업담당자</p>
											</div>

										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>우선순위</p>
											</div>
										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>키워드</p>
											</div>
										</div>
									</div>

								</div>
								<div class="col-md-7">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													name="manager" value="<%=manager %>">
											</div>

										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<select id="" class="chang form-control"
													name="reply_priority">
													<option value="" selected="selected">우선순위</option>
													<option value="긴급"
														<%="긴급".equals(reply_priority) ? "selected" : ""%>>긴급</option>
													<option value="높음"
														<%="높음".equals(reply_priority) ? "selected" : ""%>>높음</option>
													<option value="보통"
														<%="보통".equals(reply_priority) ? "selected" : ""%>>보통</option>
													<option value="낮음"
														<%="낮음".equals(reply_priority) ? "selected" : ""%>>낮음</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													name="keyword" value="<%=reply_keyword %>">
											</div>
										</div>
									</div>


								</div>
							</div>


							<div class="col-md-4">
								<div class="coco2 col-md-5">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p class="ne">업무구분</p>
											</div>

										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>세부구분</p>
											</div>
										</div>
									</div>
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>작업방법</p>
											</div>

										</div>
									</div>
								</div>
								<div class="col-md-7">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<select id="" class="chang form-control" name="division">
													<option value="" selected="selected">업무구분</option>
													<option value="EMR"
														<%="EMR".equals(division) ? "selected" : ""%>>EMR</option>
													<option value="CDSS"
														<%="CDSS".equals(division) ? "selected" : ""%>>CDSS</option>
													<option value="LIS"
														<%="LIS".equals(division) ? "selected" : ""%>>LIS</option>
													<option value="SMMC"
														<%="SMMC".equals(division) ? "selected" : ""%>>SMMC</option>
													<option value="기타"
														<%="기타".equals(division) ? "selected" : ""%>>기타</option>
												</select>
											</div>

										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<select id="" class="chang form-control"
													name="detailDivision">
													<option value="" selected="selected">세부구분</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													value="<%=reply_method %>" name="reply_method">
											</div>
										</div>
									</div>


								</div>
							</div>

							<div class="col-md-4">

								<div class="coco2 col-md-5">
									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p class="ne">작업진행도</p>
											</div>

										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>공수기간</p>
											</div>

										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control1">
												<p>완료예정일시</p>
											</div>
										</div>
									</div>
								</div>

								<div class="col-md-7">

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<select id="" class="chang form-control"
													name="question_progress">
													<option value="">작업진행도</option>
													<option value="접수"
														<%="접수".equals(question_progress) ? "selected" : ""%>>접수</option>
													<option value="회신요청"
														<%="회신요청".equals(question_progress) ? "selected" : ""%>>회신요청</option>
													<option value="개발중"
														<%="개발중".equals(question_progress) ? "selected" : ""%>>개발중</option>
													<option value="작업완료"
														<%="작업완료".equals(question_progress) ? "selected" : ""%>>작업완료</option>
													<option value="작업보류"
														<%="작업보류".equals(question_progress) ? "selected" : ""%>>작업보류</option>
													<option value="삭제"
														<%="삭제".equals(question_progress) ? "selected" : ""%>>삭제</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<select id="" class="chang form-control" name="term">
													<option value="" selected="selected">일</option>
													<option value="1">1일</option>
													<option value="2">2일</option>
													<option value="3">3일</option>
													<option value="4">4일</option>
													<option value="5">5일</option>
													<option value="6">6일</option>
													<option value="7">7일</option>
													<option value="8">8일</option>
													<option value="9">9일</option>
													<option value="10일">10일</option>
													<option value="11일">11일</option>
													<option value="12일">12일</option>
													<option value="13">13일</option>
													<option value="14">14일</option>
													<option value="15">15일</option>
													<option value="16">16일</option>
													<option value="17">17일</option>
													<option value="18">18일</option>
													<option value="19">19일</option>
													<option value="20">20일</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form_group">
										<div class="fg-line">
											<div class="form-control">
												<input type="text" class="chang2 form-control"
													value="완료예정일시" name="reply_day">
											</div>
										</div>
									</div>

								</div>
							</div>

							<div class="col-md-12">
								<div class="col-md-12">
									<div class="form-control3">
										<p>작업내용</p>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-12">
									<div class="col-md-12">
										<div class="form-group">
											<div class="fg-line">
												<textarea class="leftm form-control" rows="8"
													name="reply_content"><%=lime.getReply_content()%></textarea>
											</div>
										</div>
									</div>
								</div>
							</div>

							<input type="hidden" value="<%=lime.getReply_id() %>" name="reply_id">
							<input type="hidden" value="<%=question_progress %>" name="progress_ago">

							<div class="col-md-12">
								<div class="col-md-10"></div>
								<div class="col-md-2">
									<div class="col-md-6">
										<div class="form-group">
											<%
						if(question_progress.isEmpty()){
						%>
											<input type="submit" value="등록" class="btn btn-two m-r-10">
											<%
						}else{
						%>
											<input type="submit" value="수정" class="btn btn-two m-r-10">
											<%	
						}
						%>
										</div>
									</div>
									
									<div class="col-md-6">
										<div class="form-group">
											<a href="admin_list.jsp"> 
												<input class="btn btn-two m-r-10" value="취소" type="button">
											</a>
										</div>
									</div>
									
								</div>
							</div>
							<%
								}
							%>

						</div>
					</form>
				</div>
			</div>
			<%@ include file="include/footer2.jsp"%>
		</div>
	</section>
</body>
</html>