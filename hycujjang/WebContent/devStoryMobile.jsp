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
		.bp {
			height: auto; 
			width: 100%; 
			border-bottom:1px solid silver; 
			padding: 6px;
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
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">한양사이버대 강의평가</a>	
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="lectureBoardController?pageNumber=1">강의평가</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="freeBoardListController?pageNumber=1">자유게시판</a>
				</li>
				<li class="nav-item active">
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
	
	<div class="container">
		<c:set var="detail" value="${devStoryDetail}"></c:set>
		<div class="row">
			<div class="col-12">
				<div class="bp">
					<h1>${detail.boardTitle}</h1>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<div class="bp">
					<span style="font-weight: bold;">작성자&nbsp;</span>관리자<br>
					<span style="font-weight: bold;">작성일자 &nbsp;</span>${detail.boardRegDate}
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12 pad">${detail.boardContent}</div>
		</div>
	</div>
	
	<br>
	<br>
	<br>
	
	<div class="container" id="commentList">
		<div class="row">
			<div class="col-12">
				<h5 style="height: auto; width: 100%; border-bottom:1px solid silver; padding: 6px;">전체 댓글 ${commentCount}개</h5>
			</div>
		</div>
		
		<c:set var="commentCount" value="${commentCount}"></c:set>
		<input type="hidden" id="commentCount" value="${commentCount}">
		
		<c:forEach var="comments" varStatus="status" items="${comments}">
			<input type="hidden" id="dispID_${comments.commentID}" value="${comments.commentID}">
			<input type="hidden" id="commentWriter_${comments.commentID}" value="${comments.writeID}">
			<div class="row bg-light">
				<div class="col-6 pad" style="text-align: left;">${comments.writeID}<span style="font-size: 10px;">&nbsp;(${comments.ip})</span></div>				
				<div class="col-6 pad" style="text-align: right;">${comments.regDate} 
					<a href="#" id="commentDelete_${comments.commentID}" onclick="commentShowPop(this); return false;"><img src="images/del.png" width="15" height="15"></a>
				</div>
			</div>
			<div class="row pad">
				<div class="col" onclick="javascript:doDisplay(dispID_${comments.commentID});">
					${comments.comment}<p>
				</div>
			</div>
			<!-- 대댓글 입력창 -->
			<table class="table" id="riply_${comments.commentID}" style="display:none;">
				<tbody>
					<tr>
						<td>
							<input class="form-control" type="text" id="replyID_${comments.commentID}" name="replyID_${comments.commentID}" placeholder="아이디" maxlength="20">
						</td>
						<td>
							<input class="form-control" type="password" id="replyPassword_${comments.commentID}" name="replyPassword_${comments.commentID}" placeholder="비밀번호" maxlength="20">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea class="form-control" id="reply_text_${comments.commentID}" name="reply" placeholder="댓글을 입력하세요" maxlength="100"></textarea>
							<div id="reply_text_count_${comments.commentID}">(0 / 100)</div>
							<br>
							<div style="text-align: center;">
								<input type="hidden" id="boardID" value="${detail.boardID}">
								<c:if test="${userID == 'guest'}">
									<button class="btn btn-primary" onclick="guest()">댓글등록</button>
								</c:if>
								<c:if test="${userID != 'guest'}">
									<button class="btn btn-primary" onclick="replyRegister(replyID_${comments.commentID}, replyPassword_${comments.commentID}, reply_text_${comments.commentID}, dispID_${comments.commentID})">댓글등록</button>
								</c:if>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<c:forEach var="reply" varStatus="status" items="${comments.replyList}">
				<div class="row">
					<div class="col-6 offset-1 bg-light pad" style="text-align: left;">${reply.nickName}<span style="font-size: 10px;">&nbsp;(${reply.ip})</span></div>
					<div class="col-5 bg-light pad" style="text-align: right;">${reply.regDate}
						<a href="#" id="replyDelete_${reply.replyID}" onclick="replyShowPop(this); return false;"><img src="images/del.png" width="15" height="15"></a>
					</div>
				</div>
				<div class="row">
					<div class="col offset-1 pad">
						<span style="background-color: lavender;">@${comments.writeID}</span>&nbsp;&nbsp;${reply.replyComment}<p>
					</div>
				</div>
			</c:forEach>
		</c:forEach>
	</div>
	
	

	<br>
	<br>
	<br>
	
	
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th colspan="2"><h5>댓글 작성 하기</h5></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input class="form-control" type="text" id="writeID" name="writeID" placeholder="아이디" maxlength="20">
					</td>
					<td>
						<input class="form-control" type="password" id="password" name="password" placeholder="비밀번호" maxlength="20">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea class="form-control" id="comment" name="comment" placeholder="댓글을 입력하세요" maxlength="100"></textarea>
						<div id="comment_text_count">(0 / 100)</div>
						<br>
						<div style="text-align: center;">
							<input type="hidden" id="boardID" value="${detail.boardID}">
							<c:if test="${userID == 'guest'}">
								<button class="btn btn-primary" onclick="guest()">댓글등록</button>
							</c:if>
							<c:if test="${userID != 'guest'}">
								<button class="btn btn-primary" onclick="commentRegister()">댓글등록</button>
							</c:if>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="passPopup" style="display:none; position:absolute; width:200px; height:100px; background-color: #E0FFFF">
		<div style="text-align: center;">
			비밀번호를 입력 하세요
			<input type="password" id="contentPassword" style="width:150px; height:30px; font-size:30px;">
			<input type="hidden" id="popType">
		</div>
		<div style="text-align: center;">
			<c:if test="${userID == 'guest'}">
				<span><button class="btn btn-primary" style="font-size:15px;" onclick="guest()">확인</button></span>
			</c:if>
			<c:if test="${userID != 'guest'}">
				<span><button class="btn btn-primary" style="font-size:15px;" onclick="passPopupValid(popType, contentPassword)">확인</button></span>
			</c:if>
			<span><button class="btn btn-primary" style="font-size:15px;" onclick="popclose()">취소</button></span>
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















