<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
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
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="lectureBoardController?pageNumber=1">한양사이버대 강의평가</a>	
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
				<li class="nav-item">
					<a class="nav-link" href="devStoryListController?pageNumber=1">제작이야기</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">						
						<a class="dropdown-item" href="index.jsp">로그인</a>
						<a class="dropdown-item" href="userJoin.jsp">회원가입</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	
	<section class="container mt-3" style="max-width: 560px;">
		<div class="row">
			<div class="col-12" style="text-align: center;">
				<img src="images/hycu1.jpg" class="img-fluid">
			</div>
		</div>
		
		<div class="form-group">
			<label>아이디</label>
			<input type="text" name="userID" id="id" class="form-control">
		</div>
		<div class="form-group">
			<label>비밀번호</label>
			<input type="password" name="userPassword" id="password" class="form-control">
		</div>
		<div style="text-align: center;">
			<button class="btn btn-primary" onclick="login()">로그인</button>
			<a href="userJoin.jsp"><input type="button" class="btn btn-primary" value="회원가입" /></a>
		</div>	
	</section>

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
		window.onload = function() {
			if(document.location.protocol == 'http:'){
			document.location.href = document.location.href.replace('http:', 'https:');
			}
			mobileCheck();
		};
		
		function mobileCheck() {
			var device = 'pc';
			var uAgent = navigator.userAgent.toLowerCase(); 
			var mobilePhones = new Array('iphone', 'ipod', 'ipad', 'android', 'blackberry', 'windows ce','nokia', 'webos', 'opera mini', 'sonyericsson', 'opera mobi', 'iemobile'); 
			for (var i = 0; i < mobilePhones.length; i++){ 
				if (uAgent.indexOf(mobilePhones[i]) != -1){ 
					device = 'mobile';
				} 
			}
			var data = {device: device}
			$.ajax({
				type: "post",
				url: "deviceUpdate",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
			});
		}
	</script>
	<script type="text/javascript">
		function login() {
			var devices = mobileCheck();
			var id = document.getElementById('id').value;
			var password = document.getElementById('password').value;
			var data = {id: id, password: password, devices: devices}
			$.ajax({
				type: "post",
				url: "loginController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(json) {
					var result = json[0].resultCode;
					if (result == 'ok') {
						location.href = 'lectureBoardController?pageNumber=1';
						return;
					}
					
					if (result == 'unCheckedEmail') {
						alert('이메일 인증 후 사용 가능합니다.')
					} else if (result == 'wrongPass') {
						alert('비밀번호가 틀렸습니다.')
					} else if (result == 'wrongID') {
						alert('존재하지 않는 아이디 입니다.')
					} else if (result == 'wrongDB') {
						alert('데이터 베이스 오류 입니다.')
					} else {
						alert('입력 안 된 사항이 있습니다.')
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















