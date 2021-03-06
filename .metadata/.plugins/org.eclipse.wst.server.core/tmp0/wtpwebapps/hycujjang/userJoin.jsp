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
					<a class="nav-link" href="freeBoradListController?pageNumber=1">자유게시판</a>
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
	
	
	<section class="container mt-3" style="max-width: 560px;">
		<div class="row">
			<div class="col-md-12">
				<img src="images/hycu1.jpg" class="img-responsive">
			</div>
		</div>
			<div class="form-group">
				<label>아이디</label>
				<div class="row">
					<div class="col-md-10">
						<input type="text" name="userID" id="id" class="form-control" onchange="idAvailableCheck()" placeholder="아이디를 입력하세요 (모든 게시글은 익명 입니다.)">
					</div>
					<div class="col-md-2">
						<button class="btn btn-primary" onclick="idCheck()">확인</button>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label>비밀번호</label>
				<input type="password" id="pass_1" name="userPassword" class="form-control" onchange="isSame()" placeholder="비밀번호를 입력하세요">
			</div>
			<div class="form-group">
				<label>비밀번호 확인</label>
				<input type="password" id="pass_2" name="ConfirmPassword" class="form-control" onchange="isSame()" placeholder="비밀번호를 입력하세요">
				<label id="same"></label>
			</div>
			<div class="form-group">
				<label>이메일</label>
				<input type="email" name="email" class="form-control" placeholder="한사대 이메일만 사용 가능합니다. (학번@hycu.ac.kr)">
			</div>
			<div style="text-align: center; row">
				<button type="submit" class="btn btn-primary" onclick="register()">회원가입</button>
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
		var idAvailable = false;
		function register() {
			var id = document.getElementById("id").value;
			var pass = document.getElementById("pass_1").value;
			var email = document.getElementsByName('email')[0].value;
			
			if (!idAvailable) {
				alert('아이디 중복체크 안되었습니다.');
				return;
			}
			
			if (!checkUserId(id) || !checkPassword(id, pass) || !checkMail(email)) {
				return;
			}
			
			var data = {'requestCode': 'register', 'id': id, 'pass': pass, 'email': email}
			emailSend(data)
		}
		
		function emailSend(data) {
			var result;
			$.ajax({
				type: "post",
				url: "userRegisterController",
				data: JSON.stringify(data),
				async: false,
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('메일이 발송 되었습니다. 잠시만 기다려 주세요');
						location.href="emailSendAction.jsp";
					} else {
						alert('시스템 오류 입니다. 처음부터 다시 진행 부탁드립니다.');
						location.href="index.jsp";
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
		
		function idTest(data) {
			var result;
			$.ajax({
				type: "post",
				url: "userRegisterController",
				data: JSON.stringify(data),
				async: false,
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(json) {
					result = json[0].resultCode
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
			return result;
		}
		
		function idCheck() {
			var id = document.getElementById("id").value;
			if (id.length == 0 || id.length == "") {
				alert('아이디 형식이 잘못되었습니다.');
				return;
			}
			
			var data = {requestCode: 'idCheck', id: id}	
			var result = idTest(data);
			if (result == 'ok') {
				alert('사용 가능합니다.');
				idAvailable = true;
			} else {
				alert('사용중인 아이디 입니다.');
			}
		}
		
		function idAvailableCheck() {
			var id = document.getElementById('id').value;
			
			if (id.length < 6 || id.length > 12) {
		        alert('아이디는 6글자 이상, 12글자 이하만 이용 가능합니다.');
			}
		}
		
		function isSame() {
			var id = document.getElementById('id').value;
			var pass1 = document.getElementById('pass_1').value;
			if (id == pass1) {
				alert('아이디와 비밀번호는 같을 수 없습니다!');
			}
			
		    if (pass1.length < 6 || pass1.length > 16) {
		        alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');
		        document.getElementById('pass_1').value=document.getElementById('pass_2').value='';
		        document.getElementById('same').innerHTML='';
		    }
		    if (document.getElementById('pass_1').value!='' && document.getElementById('pass_2').value!='') {
		        if (document.getElementById('pass_1').value==document.getElementById('pass_2').value) {
		            document.getElementById('same').innerHTML='비밀번호가 일치합니다.';
		            document.getElementById('same').style.color='blue';
		        }
		        else {
		            document.getElementById('same').innerHTML='비밀번호가 일치하지 않습니다.';
		            document.getElementById('same').style.color='red';
		        }
		    }
		}
		
		function checkExistData(value, dataName) {
	        if (value == "") {
	            alert(dataName + " 입력해주세요!");
	            return false;
	        }
	        return true;
	    }
		
		function checkUserId(id) {
	        if (!checkExistData(id, "아이디를"))
	            return false;
	 
	        var idRegExp = /^[a-zA-z0-9]{4,12}$/;
	        if (!idRegExp.test(id)) {
	            alert("아이디는 영문 대소문자와 숫자 6~12자리로 입력해야합니다!");
	            form.userId.value = "";
	            form.userId.focus();
	            return false;
	        }
	        return true;
	    }
		
		function checkPassword(id, password) {
	        if (!checkExistData(password, "비밀번호를")) {
	        	return false;
	        }
	        
	        if (password.length < 6 || password.length > 16) {
	        	 alert("비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.");
		         return false;
	        }
	 
	        if (id == password) {
	            alert("아이디와 비밀번호는 같을 수 없습니다!");
	            return false;
	        }
	        return true;
	    }
		
		function checkMail(mail) {
	        if (!checkExistData(mail, "이메일을"))
	            return false;
	 
	        var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}[.]{1}[A-Za-z]{1,3}$/;
	        if (!emailRegExp.test(mail)) {
	            alert("이메일 형식이 올바르지 않습니다!");
	            return false;
	        }
	        
	        var mailSp = mail.split('@');
	        if (mailSp[1] != 'hycu.ac.kr') {
	        	alert("한사대 이메일만 사용 가능합니다.");
	        	return false;
	        }
	        return true;
	    }
	</script>
</body>
</html>















