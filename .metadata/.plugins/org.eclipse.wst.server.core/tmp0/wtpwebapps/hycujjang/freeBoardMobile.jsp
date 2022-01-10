<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
	<style>
		.b{
			border: solid 1px black;
		}
	</style>
</head>
<body>
<script>
	window.onload = function() { 
		if(document.location.protocol == 'http:'){
		//document.location.href = document.location.href.replace('http:', 'https:');
		}
	}
</script>
	<nav class="navbar navbar-expand-md navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">한양사이버대 강의평가</a>	
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="lectureBoardController?pageNumber=1">강의평가</a>
				</li>
				<li class="nav-item active">
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
	<h1 style="padding: 6px;">
   		<a class="ed link-primary text-bold title-underline" href="freeBoardListController?pageNumber=1"><span class="text-dark">자유게시판</span></a>
	</h1>
		
	<div class="container">
		<c:if test="${userID == 'admin'}">
			<div id="checkList" style="text-align: right;">
				<button class="btn btn-primary" id="selectAll" onclick="select(this)">전체선택</button>
				<button class="btn btn-danger" id="deleteAll" onclick="deleteAll()">선택삭제</button>
			</div>	
		</c:if>
		<c:if test="${userID != 'admin'}">
			<c:if test="${userID == 'guest'}">
				<div style="text-align: right;">
					<button onclick="guest()" class="btn btn-primary">글쓰기</button>
				</div>
			</c:if>
			<c:if test="${userID != 'guest'}">
				<div style="text-align: right;">
					<a href="write.jsp" class="btn btn-primary">글쓰기</a>
				</div>
			</c:if>
		</c:if>
		<br>
		<!--bbsID, nickName, bbsDate, bbsTitle, bbsHit, cmt_count, used_file --> 
		<c:forEach var="freeBBS" items="${freeBBS}" begin="0" end="${freeBBSListPrintCount}">	
			<c:if test="${userID != 'admin'}">	
				<div style="width: 100%; padding: 3px;"></div>
				<div class="row" onclick="location.href='freeBoardDetailController?id=${freeBBS.bbsID}';"> 
					<div class="col-12">
						<c:if test="${fn:length(freeBBS.bbsTitle) > 25}">
							${fn:substring(freeBBS.bbsTitle, 0, 20)}...
						</c:if>
						<c:if test="${fn:length(freeBBS.bbsTitle) <= 25}">
							${freeBBS.bbsTitle}
						</c:if>
						<c:if test="${freeBBS.cmt_count != 0}"><span style="font-size: 12px; color:#00bfff;">[${freeBBS.cmt_count}]</span></c:if>	
						<c:if test="${freeBBS.used_file != 0}"><img src="images/imageIcon.png"></c:if>
					</div>
					<div class="col-12" style="font-size: 12px; color:#c0c0c0;">
						${freeBBS.nickName} | ${freeBBS.bbsDate} | 조회수 ${freeBBS.bbsHit}
					</div>
					<div class="col-12">
						<div style="width: 100%; border-bottom:1px solid silver; padding: 3px;"></div>
					</div>
				</div>
			</c:if>
			
			<!-- 관리계정으로 삭제하기 위한 조치 -->
			<c:if test="${userID == 'admin'}">
				<div class="row" style="height: auto; width: 100%; border-bottom:1px solid silver; padding: 6px;"> 
					<div class="col-12">
						<input type="checkbox" name="delCheck_id" id="delCheck_id" value="${freeBBS.bbsID}">
						<a class="text-dark" href="freeBoardDetailController?id=${freeBBS.bbsID}">
						<c:if test="${fn:length(freeBBS.bbsTitle) > 25}">
							${fn:substring(freeBBS.bbsTitle, 0, 20)}...
						</c:if>
						<c:if test="${fn:length(freeBBS.bbsTitle) <= 25}">
							${freeBBS.bbsTitle}
						</c:if>
						<c:if test="${freeBBS.cmt_count != 0}"><span style="font-size: 12px; color:#00bfff;">[${freeBBS.cmt_count}]</span></c:if>	
						<c:if test="${freeBBS.used_file != 0}"><img src="images/imageIcon.png"></c:if>
						</a>
					</div>
					<div class="col-12" style="font-size: 12px; color:#c0c0c0;">
						${freeBBS.nickName} | ${freeBBS.bbsDate} | 조회수 ${freeBBS.bbsHit}
					</div>
				</div>
			</c:if>
			
		</c:forEach>		
		<div>
			<c:set var="page" value="${(param.pageNumber == null) ? 1 : param.pageNumber}"></c:set>
			<c:set var="startNum" value="${page - (page - 1) % 5}"></c:set>
			<c:set var="lastNum" value="${listCount}"></c:set>
			
			<ul class="pagination justify-content-center mt-3">
				<c:if test="${param.pageNumber == 1}">
					<li class="page-item"><a class="page-link" onclick="alert('이전 페이지가 없습니다.');">이전</a></li>
				</c:if>
				<c:if test="${param.pageNumber != 1}">
					<li class="page-item"><a class="page-link" href="freeBoardListController?pageNumber=${param.pageNumber - 1}">이전</a></li>
				</c:if>
				
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(startNum + i) <= lastNum}">
						<c:if test="${param.pageNumber == (startNum + i)}">
							<li class="page-item active"><a class="page-link" href="freeBoardListController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
						<c:if test="${param.pageNumber != (startNum + i)}">
							<li class="page-item"><a class="page-link" href="freeBoardListController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${param.pageNumber == lastNum}">
					<li class="page-item"><a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a></li>
				</c:if>
				<c:if test="${param.pageNumber != lastNum}">
					<li class="page-item"><a class="page-link" href="freeBoardListController?pageNumber=${param.pageNumber + 1}">다음</a></li>
				</c:if>
			</ul>
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
			alert('게스트 계정은 글작성이 불가능 합니다.');
		}
	</script>
	<script type="text/javascript">
		function deleteAll() {
			var checkList = document.getElementsByName('delCheck_id');
			var checkedList = new Array();
			var count = 0;
			for (var i = 0; i < checkList.length; i++) {
				if (checkList[i].checked == true) {
					count++;
					checkedList[i] = checkList[i].value;
				}
			}
			
			if (count == 0) {
				alert('선택사항이 없습니다.');
				return;
			}
			
			var data = new Array();
			for (var i = 0; i < checkedList.length; i++) {
				if (checkedList[i] != null) {
					var dataTemp = new Object();
					dataTemp.id = checkedList[i];
					data.push(dataTemp);
				}
			}
			
			$.ajax({
				type: "post",
				url: "freeBoardDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('삭제 되었습니다.');
					} else {
						alert('데이터베이스 오류 입니다.');
					}
					location.reload();
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
	</script>
	<script type="text/javascript">
		function select(tagID) {
			var id = tagID.id;
			var checkList = document.getElementsByName('delCheck_id');
			for (var i = 0; i < checkList.length; i++) {
				if (id == 'selectAll') {
					checkList[i].checked = true;
				} else {
					checkList[i].checked = false;
				}
			}
			
			if (id == 'selectAll') {
				changeId('cancel', '선택해제');
			} else {
				changeId('selectAll', '전체선택');
			}
		}
		
		function changeId(id, text)  {
			  const btnElement = document.getElementById('checkList');
			  btnElement.innerHTML = '<button class="btn btn-primary" id="' + id + '" onclick="select(this)">'+ text +'</button>' +
			  							'<button class="btn btn-danger" id="deleteAll" onclick="deleteAll()">선택삭제</button>';
		}
	</script>
	<script type="text/javascript">
		function select(tagID) {
			var id = tagID.id;
			var checkList = document.getElementsByName('delCheck_id');
			for (var i = 0; i < checkList.length; i++) {
				if (id == 'selectAll') {
					checkList[i].checked = true;
				} else {
					checkList[i].checked = false;
				}
			}
			
			if (id == 'selectAll') {
				changeId('cancel', '선택해제');
			} else {
				changeId('selectAll', '전체선택');
			}
		}
		
		function changeId(id, text)  {
			  const btnElement = document.getElementById('checkList');
			  btnElement.innerHTML = '<button class="btn btn-primary" id="' + id + '" onclick="select(this)">'+ text +'</button>' +
			  							'<button class="btn btn-danger" id="deleteAll" onclick="deleteAll()">선택삭제</button>';
		}
	</script>
	<script type="text/javascript">
		function deleteAll() {
			var checkList = document.getElementsByName('delCheck_id');
			var checkedList = new Array();
			var count = 0;
			for (var i = 0; i < checkList.length; i++) {
				if (checkList[i].checked == true) {
					count++;
					checkedList[i] = checkList[i].value;
				}
			}
			
			if (count == 0) {
				alert('선택사항이 없습니다.');
				return;
			}
			
			var data = new Array();
			for (var i = 0; i < checkedList.length; i++) {
				if (checkedList[i] != null) {
					var dataTemp = new Object();
					dataTemp.id = checkedList[i];
					data.push(dataTemp);
				}
			}
			
			$.ajax({
				type: "post",
				url: "freeBoardDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('삭제 되었습니다.');
					} else {
						alert('데이터베이스 오류 입니다.');
					}
					location.reload();
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
	</script>
</body>
</html>















