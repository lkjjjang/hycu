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
<script>
	window.onload = function() { 
		if(document.location.protocol == 'http:'){
		document.location.href = document.location.href.replace('http:', 'https:');
		}
	}
</script>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="lectureBoardController?pageNumber=1">한양사이버대 강의평가</a>	
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
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
						<a class="dropdown-item" href="index.jsp">로그인</a>
						<a class="dropdown-item" href="userJoin.jsp">회원가입</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	<img src="images/hycu1.jpg" style="display: block; margin: 0 auto;">
	<section class="container mt-3" style="max-width: 560px;">
		<form method="post" action="loginController">
			<div class="form-group">
				<label>아이디</label>
				<input type="text" name="userID" class="form-control">
			</div>
			<div class="form-group">
				<label>비빌번호</label>
				<input type="password" name="userPassword" class="form-control">
			</div>
			<div style="text-align: center;">
				<input type="submit" class="btn btn-primary" value="로그인" />
				<a href="userJoin.jsp"><input type="button" class="btn btn-primary" value="회원가입" /></a>
			</div>
		</form>		
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
</body>
</html>
