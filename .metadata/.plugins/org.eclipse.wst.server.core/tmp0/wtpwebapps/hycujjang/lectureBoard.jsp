<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>한양사이버대 강의평가</title>
	<!-- 부트스트랩 CSS 추가하기 -->
	<link rel="stylesheet" 
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
			integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" 
			crossorigin="anonymous">
	<!-- 커스텀 CSS 추가하기 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
<script>
	window.onload = function() { 
		if(document.location.protocol == 'http:'){
		document.location.href = document.location.href.replace('http:', 'https:');
		}
	}
</script>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">한양사이버대 강의평가</a>	
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="lectureBoardController?pageNumber=1">강의평가</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="freeBoardListController?pageNumber=1">자유게시판</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="devStoryListController?pageNumber=1">제작이야기</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" href="index.jsp">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="logout.jsp">로그아웃</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	
	<section class="container">
		<form method="get" action="lectureBoardController" class="form-inline mt-3">
			<select name="lectureDivide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="전공" ${(param.lectureDivide == "전공")? "selected" : ""}>전공</option>
				<option value="교양" ${(param.lectureDivide == "교양")? "selected" : ""}>교양</option>
				<option value="기타" ${(param.lectureDivide == "기타")? "selected" : ""}>기타</option>
			</select>
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순">최신순</option>
				<option value="추천순" ${(param.searchType == "추천순")? "selected" : ""}>추천순</option>
			</select>
			<input type="text" name="search" value="${param.search}" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<c:if test="${userID == 'guest'}">
				<a class="btn btn-primary mx-1 mt-2" onclick="guest()">등록하기</a>
				<a class="btn btn-danger mx-1 mt-2" onclick="guest()">신고</a>
			</c:if>
			<c:if test="${userID != 'guest'}">
				<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a>
				<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
			</c:if>
		</form> 
		
		<!-- 게시글 출력 -->
		<c:forEach var="evaluationList" varStatus="status" items="${evaluationList}" begin="0" end="${lectureListPrintCount}">		
			<div class="card bg-light mt-3">
				<div class="card-header bg-light">
					<div class="row">
						<div class="col-8 text-left">${evaluationList.lectureName}&nbsp;<small>${evaluationList.professorName}</small></div>
						<div class="col-4 text-right">
							종합<span style="color: red;">${evaluationList.totalScore}</span>
						</div>
					</div>
				</div>
				<div class="card-body">
					<h5 class="card-title">
						${evaluationList.evaluationTitle}&nbsp;<small>(${evaluationList.lectureYear}년 ${evaluationList.semesterDivide})</small>
					</h5>
					<p class="card-text">${evaluationList.evaluationContent}</p>
					<div class="row">
						<div class="col-md-9 text-left">
							성적<span style="color: red;">${evaluationList.creditScore}</span>
							널널<span style="color: red;">${evaluationList.comfortableScore}</span>
							강의<span style="color: red;">${evaluationList.lectureScore}</span>
							<span style="color: green;">(추천: ${evaluationList.likeCount})</span>
						</div>
						<div class="col-md-3 text-right">
							<c:if test="${userID != 'guest'}">
								<a onclick="like(${evaluationList.evaluationID}); return false;" href="#">추천</a>
							</c:if>
							<c:if test="${userID == 'guest'}">
								<a onclick="guest(); return false;" href="#">추천</a>
							</c:if>
							<c:set var="sessionID" value="${userID}"></c:set>
							<c:set var="writerID" value="${evaluationList.userID}"></c:set>
								<input type="hidden" name="lectureName" id="lectureName_${evaluationList.evaluationID}" value="${evaluationList.lectureName}">
								<input type="hidden" name="professorName" id="professorName_${evaluationList.evaluationID}" value="${evaluationList.professorName}">
								<input type="hidden" name="totalScore" id="totalScore_${evaluationList.evaluationID}" value="${evaluationList.totalScore}">
								<input type="hidden" name="evaluationTitle" id="evaluationTitle_${evaluationList.evaluationID}" value="${evaluationList.evaluationTitle}">
								<input type="hidden" name="lectureYear" id="lectureYear_${evaluationList.evaluationID}" value="${evaluationList.lectureYear}">
								<input type="hidden" name="semesterDivide" id="semesterDivide_${evaluationList.evaluationID}" value="${evaluationList.semesterDivide}">
								<input type="hidden" name="evaluationContent" id="evaluationContent_${evaluationList.evaluationID}" value="${evaluationList.evaluationContent}">
								<input type="hidden" name="evaluationID" id="evaluationID_${evaluationList.evaluationID}" value="${evaluationList.evaluationID}">
								<input type="hidden" name="creditScore" id="creditScore_${evaluationList.evaluationID}" value="${evaluationList.creditScore}">
								<input type="hidden" name="comfortableScore" id="comfortableScore_${evaluationList.evaluationID}" value="${evaluationList.comfortableScore}">
								<input type="hidden" name="lectureScore" id="lectureScore_${evaluationList.evaluationID}" value="${evaluationList.lectureScore}">
								<input type="hidden" name="lectureDivide" id="lectureDivide_${evaluationList.evaluationID}" value="${evaluationList.lectureDivide}">
							<c:if test="${sessionID == writerID}">
								<a onclick="cardDelete(${evaluationList.evaluationID}); return false;" href="#">삭제</a>
								<a onclick="modify(${evaluationList.evaluationID})" href="#">수정</a>
							</c:if>
							<c:if test="${sessionID == 'admin'}">
								<a onclick="cardDelete(${evaluationList.evaluationID}); return false;" href="#">삭제</a>
								<a onclick="modify(${evaluationList.evaluationID})" href="#">수정</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>	
		</c:forEach>
		
		
		<!-- 페이지번호 출력 -->
		<!-- 파라미터로 들어오는 페이지 번호를 이용해 startNum값을 구함 -->
		<div>
			<c:set var="page" value="${(param.pageNumber == null) ? 1 : param.pageNumber}"></c:set>
			<c:set var="startNum" value="${page - (page - 1) % 5}"></c:set>
			<c:set var="lastNum" value="${listCount}"></c:set>
			
			<ul class="pagination justify-content-center mt-3">
				<c:if test="${param.pageNumber == 1}">
					<li class="page-item"><a class="page-link" onclick="alert('이전 페이지가 없습니다.');">이전</a></li>
				</c:if>
				<c:if test="${param.pageNumber != 1}">
					<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber - 1}">이전</a></li>
				</c:if>
				
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(startNum + i) <= lastNum}">
						<c:if test="${param.pageNumber == (startNum + i)}">
							<li class="page-item active"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
						<c:if test="${param.pageNumber != (startNum + i)}">
							<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${param.pageNumber == lastNum}">
					<li class="page-item"><a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a></li>
				</c:if>
				<c:if test="${param.pageNumber != lastNum}">
					<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>		
	</section>	
	
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">평가 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-row">
						<div class="form-group col-sm-6">
							<label>강의명</label>
							<input type="text" name="lectureName" id="lectureName" class="form-control" maxlength="20">
						</div>
						<div class="form-group col-sm-6">
							<label>교수명</label>
							<input type="text" name="professorName" id="professorName" class="form-control" maxlength="20">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-sm-4">
							<label>수강 연도</label>
							<select name="lectureYear" id="lectureYear" class="form-control">
								<option value="2017">2017</option>
								<option value="2018">2018</option>
								<option value="2019">2019</option>
								<option value="2020">2020</option>
								<option value="2021">2021</option>
								<option value="2022" selected>2022</option>
								<option value="2023">2023</option>
								<option value="2023">2024</option>
								<option value="2023">2025</option>
								<option value="2023">2026</option>
							</select>
						</div>
						<div class="form-group col-sm-4">
							<label>수강 학기</label>
							<select name="semesterDivide" id="semesterDivide" class="form-control">
								<option value="1학기" selected>1학기</option>
								<option value="여름학기">여름학기</option>
								<option value="2학기">2학기</option>
								<option value="겨울학기">겨울학기</option>
							</select>
						</div>
						<div class="form-group col-sm-4">
							<label>강의 구분</label>
							<select name="lectureDivide" id="lectureDivide" class="form-control">
								<option value="전공" selected>전공</option>
								<option value="교양">교양</option>
								<option value="기타">기타</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label>제목</label>
						<input type="text" name="evaluationTitle" id="evaluationTitle" class="form-control" maxlength="30">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea name="evaluationContent" id="evaluationContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
					</div>
					<div class="form-row">
						<div class="form-group col-sm-3">
							<label>종합</label>
							<select name="totalScore" id="totalScore" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>성적</label>
							<select name="creditScore" id="creditScore" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>널널</label>
							<select name="comfortableScore" id="comfortableScore" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>강의</label>
							<select name="lectureScore" id="lectureScore" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
						<button onclick="register()" class="btn btn-primary">등록하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">평가 수정</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-row">
						<div class="form-group col-sm-6">
							<label>강의명</label>
							<input type="text" name="lectureName1" id="lectureName1" class="form-control" maxlength="20">
						</div>
						<div class="form-group col-sm-6">
							<label>교수명</label>
							<input type="text" name="professorName1" id="professorName1" class="form-control" maxlength="20">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-sm-4">
							<label>수강 연도</label>
							<select name="lectureYear1" id="lectureYear1" class="form-control">
								<option value="2017">2017</option>
								<option value="2018">2018</option>
								<option value="2019">2019</option>
								<option value="2020">2020</option>
								<option value="2021">2021</option>
								<option value="2022">2022</option>
								<option value="2023">2023</option>
								<option value="2023">2024</option>
								<option value="2023">2025</option>
								<option value="2023">2026</option>
							</select>
						</div>
						<div class="form-group col-sm-4">
							<label>수강 학기</label>
							<select name="semesterDivide1" id="semesterDivide1" class="form-control">
								<option value="1학기" selected>1학기</option>
								<option value="여름학기">여름학기</option>
								<option value="2학기">2학기</option>
								<option value="겨울학기">겨울학기</option>
							</select>
						</div>
						<div class="form-group col-sm-4">
							<label>강의 구분</label>
							<select name="lectureDivide1" id="lectureDivide1" class="form-control">
								<option value="전공" selected>전공</option>
								<option value="교양">교양</option>
								<option value="기타">기타</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label>제목</label>
						<input type="text" name="evaluationTitle1" id="evaluationTitle1" class="form-control" maxlength="30">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea name="evaluationContent1" id="evaluationContent1" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
					</div>
					<div class="form-row">
						<div class="form-group col-sm-3">
							<label>종합</label>
							<select name="totalScore1" id="totalScore1" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>성적</label>
							<select name="creditScore1" id="creditScore1" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>널널</label>
							<select name="comfortableScore1" id="comfortableScore1" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="form-group col-sm-3">
							<label>강의</label>
							<select name="lectureScore1" id="lectureScore1" class="form-control">
								<option value="A" selected>A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="F">F</option>
							</select>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-sm-6">
							<input type="hidden" name="evaluationID1" id="evaluationID1" class="form-control" maxlength="20">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
						<button onclick="updateReg()" class="btn btn-primary">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">신고하기</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="reportController" method="post"><div class="form-group">
							<label>신고 제목</label>
							<input type="text" name="reportTitle" class="form-control" maxlength="30">
						</div>
						<div class="form-group">
							<label>신고 내용</label>
							<textarea name="reportContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">신고하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2021이기주All Rights Reserved.
	</footer>
		
	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" 
			integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" 
			crossorigin="anonymous">
	</script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" 
			integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" 
			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function guest() {
			alert('게스트 계정은 사용 할 수 없습니다.');
		}
	</script>
	<script type="text/javascript">
		function modify(id) {
			var evaluationID = id;
			var evaluationContent1 = document.getElementById('evaluationContent_'+ id +'').value;
			var lectureName1 = document.getElementById('lectureName_'+ id +'').value;	
			var professorName1 = document.getElementById('professorName_'+ id +'').value;	
			var totalScore1 = document.getElementById('totalScore_'+ id +'').value;	
			var evaluationTitle1 = document.getElementById('evaluationTitle_'+ id +'').value;	
			var lectureYear1 = document.getElementById('lectureYear_'+ id +'').value;	
			var semesterDivide1 = document.getElementById('semesterDivide_'+ id +'').value;	
			var creditScore1 = document.getElementById('creditScore_'+ id +'').value;	
			var comfortableScore1 = document.getElementById('comfortableScore_'+ id +'').value;	
			var lectureScore1 = document.getElementById('lectureScore_'+ id +'').value;	
			var lectureDivide1 = document.getElementById('lectureDivide_'+ id +'').value;
			
			$(".modal-body #evaluationID1").val(evaluationID);
			$(".modal-body #lectureName1").val(lectureName1);
			$(".modal-body #professorName1").val(professorName1);
			$(".modal-body #totalScore1").val(totalScore1);
			$(".modal-body #evaluationTitle1").val(evaluationTitle1);
			$(".modal-body #lectureYear1").val(lectureYear1);
			$(".modal-body #semesterDivide1").val(semesterDivide1);
			$(".modal-body #creditScore1").val(creditScore1);
			$(".modal-body #comfortableScore1").val(comfortableScore1);
			$(".modal-body #lectureScore1").val(lectureScore1);
			$(".modal-body #lectureDivide1").val(lectureDivide1);
			$(".modal-body #evaluationContent1").val(evaluationContent1.replaceAll('<br>', '\n'));
			$('#updateModal').modal('show');
		}
		
		function updateReg() {	
			var evaluationID1 = document.getElementById("evaluationID1").value;	
			var lectureName1 = document.getElementById("lectureName1").value;	
			var professorName1 = document.getElementById("professorName1").value;	
			var totalScore1 = document.getElementById("totalScore1").value;	
			var evaluationTitle1 = document.getElementById("evaluationTitle1").value;	
			var lectureYear1 = document.getElementById("lectureYear1").value;	
			var semesterDivide1 = document.getElementById("semesterDivide1").value;	
			var creditScore1 = document.getElementById("creditScore1").value;	
			var comfortableScore1 = document.getElementById("comfortableScore1").value;	
			var lectureScore1 = document.getElementById("lectureScore1").value;	
			var lectureDivide1 = document.getElementById("lectureDivide1").value;	
			var evaluationContent1 = document.getElementById("evaluationContent1").value;	
			
			var data = {
					evaluationID: evaluationID1,
					lectureName: lectureName1,
					professorName: professorName1,
					totalScore: totalScore1,
					evaluationTitle: evaluationTitle1,
					lectureYear: lectureYear1,
					semesterDivide: semesterDivide1,
					creditScore: creditScore1,
					comfortableScore: comfortableScore1,
					lectureScore: lectureScore1,
					lectureDivide: lectureDivide1,
					evaluationContent: evaluationContent1
				}
			console.log(data);
			$.ajax({
				type: "post",
				url: "evaluationUpdateController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						location.reload();
					} else if (json[0].resultCode == 'isNull'){
						alert('입력하지 않은 사항이 있습니다.')
					} else {
						alert('데이터 베이스 오류 입니다.');
						location.reload();
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
		
		
	</script>
	<script type="text/javascript">
		function cardDelete(id) {
			if (confirm("삭제하시겠습니까?")) {
				console.log(id);
				var data = {id: id}
				$.ajax({
					type: "post",
					url: "evaluationDeleteController",
					data: JSON.stringify(data),
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					
					success: function(json) {
						if (json[0].resultCode == 'ok') {
							location.reload();
						} else {
							alert('데이터 베이스 오류 입니다.');
							location.reload();
						}
					},
					error: function(json) {
						alert('시스템 오류 입니다.')
					}
				});
			}
		}
	</script>
	<script type="text/javascript">
		function like(id) {
			var data = {id: id}
			$.ajax({
				type: "post",
				url: "likecontroller",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('추천 하였습니다.')
						location.reload();
					} else if (json[0].resultCode == 'hasLike'){
						alert('이미 추천을 누른 글 입니다.')
					} else {
						alert('데이터 베이스 오류 입니다.');
						location.reload();
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
	</script>
	<script type="text/javascript">
		function register() {
			var lectureName = document.getElementById("lectureName").value;	
			var professorName = document.getElementById("professorName").value;	
			var totalScore = document.getElementById("totalScore").value;	
			var evaluationTitle = document.getElementById("evaluationTitle").value;	
			var lectureYear = document.getElementById("lectureYear").value;	
			var semesterDivide = document.getElementById("semesterDivide").value;	
			var creditScore = document.getElementById("creditScore").value;	
			var comfortableScore = document.getElementById("comfortableScore").value;	
			var lectureScore = document.getElementById("lectureScore").value;	
			var lectureDivide = document.getElementById("lectureDivide").value;
			var evaluationContent = document.getElementById("evaluationContent").value;	
			
			var data = {
					evaluationID: "register", // 모두 문자열로 보내야함
					lectureName: lectureName,
					professorName: professorName,
					totalScore: totalScore,
					evaluationTitle: evaluationTitle,
					lectureYear: lectureYear,
					semesterDivide: semesterDivide,
					creditScore: creditScore,
					comfortableScore: comfortableScore,
					lectureScore: lectureScore,
					lectureDivide: lectureDivide,
					evaluationContent: evaluationContent,
				}
			$.ajax({
				type: "post",
				url: "evaluationRegisterController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						location.reload();
					} else if (json[0].resultCode == 'isNull'){
						alert('입력하지 않은 사항이 있습니다.')
					} else {
						alert('데이터 베이스 오류 입니다.');
						location.reload();
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
	</script>	
</body>
</html>



