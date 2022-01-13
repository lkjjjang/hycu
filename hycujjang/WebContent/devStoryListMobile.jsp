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
		.pad {
			padding-top: 5px; 
			padding-bottom: 5px;
		}
		.hidden {
			height:100%; 
			min-height:100%; 
			overflow:hidden !important; 
			touch-action:none;
		}
	</style>
</head>
<body>
<script type="text/javascript">
	window.onload = function() { 
		if(document.location.protocol == 'http:'){
		document.location.href = document.location.href.replace('http:', 'https:');
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
   		<a class="ed link-primary text-bold title-underline" href="freeBoardListController?pageNumber=1"><span class="text-dark">제작이야기</span></a>
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
					<a href="devStoryWrite.jsp" class="btn btn-primary">글쓰기</a>
				</div>
			</c:if>
		</c:if>
		<br>
		<c:forEach var="devStory" items="${devStory}" begin="0" end="${DevListPrintCount}">	
			<c:if test="${userID != 'admin'}">	
				<div style="width: 100%; padding: 3px;"></div>
				<div class="row" onclick="location.href='devStoryDetailController?id=${devStory.boardID}';"> 
					<div class="col-12">
						<c:if test="${fn:length(devStory.boardTitle) > 25}">
							${fn:substring(devStory.boardTitle, 0, 20)}...
						</c:if>
						<c:if test="${fn:length(devStory.boardTitle) <= 25}">
							${devStory.boardTitle}
						</c:if>
						<c:if test="${devStory.cmt_count != 0}"><span style="font-size: 12px; color:#00bfff;">[${devStory.cmt_count}]</span></c:if>	
						<c:if test="${devStory.use_file != 0}"><img src="images/imageIcon.png"></c:if>
					</div>
					<div class="col-12" style="font-size: 12px; color:#c0c0c0;">
						관리자 | ${devStory.boardRegDate} 
					</div>
					<div class="col-12">
						<div style="width: 100%; border-bottom:1px solid silver; padding: 3px;"></div>
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
					<li class="page-item"><a class="page-link" href="devStoryDetailController?pageNumber=${param.pageNumber - 1}">이전</a></li>
				</c:if>
				
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(startNum + i) <= lastNum}">
						<c:if test="${param.pageNumber == (startNum + i)}">
							<li class="page-item active"><a class="page-link" href="devStoryDetailController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
						<c:if test="${param.pageNumber != (startNum + i)}">
							<li class="page-item"><a class="page-link" href="devStoryDetailController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${param.pageNumber == lastNum}">
					<li class="page-item"><a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a></li>
				</c:if>
				<c:if test="${param.pageNumber != lastNum}">
					<li class="page-item"><a class="page-link" href="devStoryDetailController?pageNumber=${param.pageNumber + 1}">다음</a></li>
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
		function passPopupValid(popType, pass) {
			var pass = pass.value;
			if (pass.length == 0) {
				alert('비밀번호를 입력 하세요');
				return;
			}
			
			var boardID = document.getElementById("boardID").value;
			// 댓글, 대댓글은 뒤에 넘버가 따라와서 정규식으로 구분함
			var commentDelete = /commentDelete/;
			var replyDelete = /replyDelete/;
			
			if (popType.value == 'contentModify') {
				goContentModify(boardID, pass);
			} else if (popType.value == 'contentDelete') {
				goContentDelete(boardID, pass);
			} else if (commentDelete.test(popType.value)) {
				goCommentDelete(popType, pass);
			} else if (replyDelete.test(popType.value)) {
				goReplyDelete(popType, pass);
			}
		}
		
		function goContentModify(boardID, pass) {
			var boardID = document.getElementById("boardID").value;
			var data = {boardID: boardID, password: pass}
			$.ajax({
				type: "post",
				url: "devStoryPassCheck",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						location.href = 'updateObjectForward?id='+ boardID +'';
					} else if (json[0].resultCode == 'wrongPass'){
						alert('비밀번호가 잘못 되었습니다.')
					} else {
						alert('데이터베이스 오류 입니다.');
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
					location.href = '/index.jsp';
				}
			});
		}
		
		function goContentDelete(boardID, pass) {
			var boardID = document.getElementById("boardID").value;
			var data = {boardID: boardID, password: pass}
			console.log(data);
			$.ajax({
				type: "post",
				url: "devStoryDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('삭제되었습니다.');
						location.href = 'devStoryListController?pageNumber=1';
					} else if (json[0].resultCode == 'wrongPass') {
						alert('비밀번호가 잘못 되었습니다.');
					} else {
						alert('데이터베이스 오류 입니다.');
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
					location.href = '/index.jsp';
				}
			});
		}
		
		function goReplyDelete(popType, pass) {
			var replyIDSp = {};
			replyIDSp = popType.value.split("_");
			var replyID = replyIDSp[1];
			var data = {replyID: replyID, password: pass};
			commentReplyAjax(data, 'devReplyDeleteController');
		}
		
		function goCommentDelete(popType, pass) {
			var commentIDSp = {};
			commentIDSp = popType.value.split("_");
			var commentID = commentIDSp[1];
			var data = {commentID: commentID, password: pass};
			commentReplyAjax(data, 'devCommentDeleteController');
		}
		
		function commentReplyAjax(data, url) {
			console.log(123);
			$.ajax({
				type: "post",
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						popclose();
						$("#commentList").load(location.href + " #commentList");
					} else if (json[0].resultCode == 'wrongPass'){
						alert('비밀번호가 잘못 되었습니다.');
					} else {
						alert('데이터베이스 오류 입니다.');
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
					location.href = '/index.jsp';
				}
			});
		}
	</script>
	<script type="text/javascript">
		// 버튼 위치 절대 경로 페이지 0,0 부터 절대값으로 좌표 가져옴
		function commentShowPop(tagID) {
			var id = tagID.id;
			var buttonLocation = $('#'+ id +'').offset();
			popup(buttonLocation, tagID);
		}
		// 버튼 위치 절대 경로 페이지 0,0 부터 절대값으로 좌표 가져옴
		function replyShowPop(tagID) {
			var id = tagID.id;
			var buttonLocation = $('#'+ id +'').offset();
			popup(buttonLocation, tagID);
		}
		// 버튼 위치 상대 경로 페이지 0,0 부터 절대값으로 좌표 가져옴
		function showPop(tagID) {
			var id = tagID.id
			var buttonLocation = $('#'+ id +'').position(); // 버튼의 위치에 레이어를 띄우고자 할 경우, 위치 정보 가져옴
			popup(buttonLocation, tagID);
		}
		
		function popup(buttonLocation, tagID) {
			var popX = buttonLocation.left - 200;
			var popY = buttonLocation.top - 100;
			var lay_pop = $('#passPopup');			
			lay_pop.css('left', (popX) + 'px'); // 레이어 위치 지정
			lay_pop.css('top', (popY) + 'px');  
			
			var popType = document.getElementById("popType");	
			popType.value = tagID.id;
			lay_pop.fadeIn();
			lay_pop.focus();
		}
		
		function popclose() {
			var lay_pop = $('#passPopup');
			var userInputPass = document.getElementById("contentPassword");
			userInputPass.value = "";
			lay_pop.fadeOut();
		}
	</script>
	<script type="text/javascript">
		function guest() {
			alert('게스트 계정은 사용할 수 없습니다.');
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#comment').bind("change keyup input", function(event) {
				$('#comment_text_count').html("("+$(this).val().length+" / 100)");
				
				if($(this).val().length > 100) {
					$(this).val($(this).val().substring(0, 100));
					$('#comment_text_count').html("(100 / 100)");
				}
			});
		});
		
		function doDisplay(id){
			var id = id.value;
			var con = document.getElementById('riply_' + id + '');
			if (con.style.display=='none') {
				con.style.display = '';
				replyWriteWindow(id);
			} else {
				con.style.display = 'none';
			}
		}
		
		function replyWriteWindow(id) {
			$("textarea[id^='reply_text_"+ id +"']").bind("change keyup input", function(event) {
				$("div[id^='reply_text_count_"+ id +"']").html("("+$(this).val().length+" / 100)");
				if($(this).val().length > 100) {
					$(this).val($(this).val().substring(0, 100));
					$("div[id^='reply_text_count_"+ id +"']").html("(100 / 100)");
				}
			});
		}
	</script>
	<script type="text/javascript">
		// 댓글
		function commentRegister() {
			var data = {
				writeID: $("#writeID").val(),
				password: $("#password").val(),
				boardID: $("#boardID").val(),
				comment: $("#comment").val()
			}
			var url = 'devCommentRegisterController';
			sendDataToServer(data, url);
			writeID: $("#writeID").val("");
			password: $("#password").val("");
			comment: $("#comment").val("");
		}
		
		// 대댓글
		function replyRegister(nick, pass, content, tagHideID) {
			var data = {
					commentID: tagHideID.value,
					boardID: $('#boardID').val(),
					nickName: nick.value,
					password: pass.value,
					replyComment: content.value
			}
			var url = 'devReplyRegisterController';
			sendDataToServer(data, url);
		}
		
		function sendDataToServer(data, url) {
			$.ajax({
				type: "post",
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'isNull') {
						alert('빈칸없이 작성 해야 합니다.');
					} else if (json[0].resultCode == 'error'){
						alert('데이터베이스 오류 입니다.');
					} else {
						$("#commentList").load(location.href + " #commentList");
					}
				},
				error: function(json) {
					alert('시스템 오류입니다.');
				}
			});
		}
	</script>
</body>
</html>















