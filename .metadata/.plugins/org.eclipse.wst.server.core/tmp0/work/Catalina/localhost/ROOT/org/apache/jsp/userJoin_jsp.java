/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.73
 * Generated at: 2022-01-03 05:19:57 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.PrintWriter;

public final class userJoin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.io.PrintWriter");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("	<meta charset=\"UTF-8\">\r\n");
      out.write("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n");
      out.write("	<title>한양사이버대 강의평가</title>\r\n");
      out.write("	<!-- 부트스트랩 CSS 추가하기 -->\r\n");
      out.write("	<link rel=\"stylesheet\" \r\n");
      out.write("			href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" \r\n");
      out.write("			integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" \r\n");
      out.write("			crossorigin=\"anonymous\">\r\n");
      out.write("	<!-- 커스텀 CSS 추가하기 -->\r\n");
      out.write("	<link rel=\"stylesheet\" href=\"./css/custom.css\">\r\n");
      out.write("	\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\r\n");
      out.write("		<a class=\"navbar-brand\" href=\"index.jsp\">한양사이버대 강의평가</a>	\r\n");
      out.write("		<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbar\">\r\n");
      out.write("			<span class=\"navbar-toggler-icon\"></span>\r\n");
      out.write("		</button>\r\n");
      out.write("		<div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n");
      out.write("			<ul class=\"navbar-nav mr-auto\">\r\n");
      out.write("				<li class=\"nav-item\">\r\n");
      out.write("					<a class=\"nav-link\" href=\"lectureBoardController?pageNumber=1\">강의평가</a>\r\n");
      out.write("				</li>\r\n");
      out.write("				<li class=\"nav-item\">\r\n");
      out.write("					<a class=\"nav-link\" href=\"freeBoradController?pageNumber=1\">자유게시판</a>\r\n");
      out.write("				</li>\r\n");
      out.write("				<li class=\"nav-item dropdown\">\r\n");
      out.write("					<a class=\"nav-link dropdown-toggle\" id=\"dropdown\" data-toggle=\"dropdown\" href=\"index.jsp\">\r\n");
      out.write("						회원관리\r\n");
      out.write("					</a>\r\n");
      out.write("					<div class=\"dropdown-menu\" aria-labelledby=\"dropdown\">						\r\n");
      out.write("						<a class=\"dropdown-item\" href=\"index.jsp\">로그인</a>\r\n");
      out.write("						<a class=\"dropdown-item\" href=\"userJoin.jsp\">회원가입</a>\r\n");
      out.write("					</div>\r\n");
      out.write("				</li>\r\n");
      out.write("			</ul>\r\n");
      out.write("		</div>	\r\n");
      out.write("	</nav>\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	<section class=\"container mt-3\" style=\"max-width: 560px;\">\r\n");
      out.write("		<div class=\"row\">\r\n");
      out.write("			<div class=\"col-md-12\">\r\n");
      out.write("				<img src=\"images/hycu1.jpg\" class=\"img-responsive\">\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("			<div class=\"form-group\">\r\n");
      out.write("				<label>아이디</label>\r\n");
      out.write("				<div class=\"row\">\r\n");
      out.write("					<div class=\"col-md-10\">\r\n");
      out.write("						<input type=\"text\" name=\"userID\" id=\"id\" class=\"form-control\" onchange=\"idAvailableCheck()\" placeholder=\"아이디를 입력하세요 (모든 게시글은 익명 입니다.)\">\r\n");
      out.write("					</div>\r\n");
      out.write("					<div class=\"col-md-2\">\r\n");
      out.write("						<button class=\"btn btn-primary\" onclick=\"idCheck()\">확인</button>\r\n");
      out.write("					</div>\r\n");
      out.write("				</div>\r\n");
      out.write("			</div>\r\n");
      out.write("			<div class=\"form-group\">\r\n");
      out.write("				<label>비밀번호</label>\r\n");
      out.write("				<input type=\"password\" id=\"pass_1\" name=\"userPassword\" class=\"form-control\" onchange=\"isSame()\" placeholder=\"비밀번호를 입력하세요\">\r\n");
      out.write("			</div>\r\n");
      out.write("			<div class=\"form-group\">\r\n");
      out.write("				<label>비밀번호 확인</label>\r\n");
      out.write("				<input type=\"password\" id=\"pass_2\" name=\"ConfirmPassword\" class=\"form-control\" onchange=\"isSame()\" placeholder=\"비밀번호를 입력하세요\">\r\n");
      out.write("				<label id=\"same\"></label>\r\n");
      out.write("			</div>\r\n");
      out.write("			<div class=\"form-group\">\r\n");
      out.write("				<label>이메일</label>\r\n");
      out.write("				<input type=\"email\" name=\"email\" class=\"form-control\" placeholder=\"한사대 이메일만 사용 가능합니다. (학번@hycu.ac.kr)\">\r\n");
      out.write("			</div>\r\n");
      out.write("			<div style=\"text-align: center; row\">\r\n");
      out.write("				<button type=\"submit\" class=\"btn btn-primary\" onclick=\"register()\">회원가입</button>\r\n");
      out.write("			</div>\r\n");
      out.write("	</section>\r\n");
      out.write("	\r\n");
      out.write("	<footer class=\"bg-dark mt-4 p-5 text-center\" style=\"color: #FFFFFF;\">\r\n");
      out.write("		Copyright &copy; 2021이기주All Rights Reserved.\r\n");
      out.write("	</footer>\r\n");
      out.write("	<script src=\"https://code.jquery.com/jquery-3.6.0.js\"\r\n");
      out.write("  			integrity=\"sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=\"\r\n");
      out.write("  			crossorigin=\"anonymous\">\r\n");
      out.write("	</script>\r\n");
      out.write("	<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" \r\n");
      out.write("			integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" \r\n");
      out.write("			crossorigin=\"anonymous\">\r\n");
      out.write("	</script>\r\n");
      out.write("	<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" \r\n");
      out.write("			integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" \r\n");
      out.write("			crossorigin=\"anonymous\">\r\n");
      out.write("	</script>\r\n");
      out.write("	<script type=\"text/javascript\">\r\n");
      out.write("		var idAvailable = false;\r\n");
      out.write("		function register() {\r\n");
      out.write("			var id = document.getElementById(\"id\").value;\r\n");
      out.write("			var pass = document.getElementById(\"pass_1\").value;\r\n");
      out.write("			var email = document.getElementsByName('email')[0].value;\r\n");
      out.write("			\r\n");
      out.write("			if (!idAvailable) {\r\n");
      out.write("				alert('아이디 중복체크 안되었습니다.');\r\n");
      out.write("				return;\r\n");
      out.write("			}\r\n");
      out.write("			\r\n");
      out.write("			if (!checkUserId(id) || !checkPassword(id, pass) || !checkMail(email)) {\r\n");
      out.write("				return;\r\n");
      out.write("			}\r\n");
      out.write("			\r\n");
      out.write("			var data = {'requestCode': 'register', 'id': id, 'pass': pass, 'email': email}\r\n");
      out.write("			emailSend(data)\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function emailSend(data) {\r\n");
      out.write("			var result;\r\n");
      out.write("			$.ajax({\r\n");
      out.write("				type: \"post\",\r\n");
      out.write("				url: \"userRegisterController\",\r\n");
      out.write("				data: JSON.stringify(data),\r\n");
      out.write("				async: false,\r\n");
      out.write("				contentType: \"application/json; charset=utf-8\",\r\n");
      out.write("				dataType: \"json\",\r\n");
      out.write("				success: function(json) {\r\n");
      out.write("					if (json[0].resultCode == 'ok') {\r\n");
      out.write("						alert('메일이 발송 되었습니다. 잠시만 기다려 주세요');\r\n");
      out.write("						location.href=\"emailSendAction.jsp\";\r\n");
      out.write("					} else {\r\n");
      out.write("						alert('시스템 오류 입니다. 처음부터 다시 진행 부탁드립니다.');\r\n");
      out.write("						location.href=\"index.jsp\";\r\n");
      out.write("					}\r\n");
      out.write("				},\r\n");
      out.write("				error: function(json) {\r\n");
      out.write("					alert('시스템 오류 입니다.')\r\n");
      out.write("				}\r\n");
      out.write("			});\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function idTest(data) {\r\n");
      out.write("			var result;\r\n");
      out.write("			$.ajax({\r\n");
      out.write("				type: \"post\",\r\n");
      out.write("				url: \"userRegisterController\",\r\n");
      out.write("				data: JSON.stringify(data),\r\n");
      out.write("				async: false,\r\n");
      out.write("				contentType: \"application/json; charset=utf-8\",\r\n");
      out.write("				dataType: \"json\",\r\n");
      out.write("				success: function(json) {\r\n");
      out.write("					result = json[0].resultCode\r\n");
      out.write("				},\r\n");
      out.write("				error: function(json) {\r\n");
      out.write("					alert('시스템 오류 입니다.')\r\n");
      out.write("				}\r\n");
      out.write("			});\r\n");
      out.write("			return result;\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function idCheck() {\r\n");
      out.write("			var id = document.getElementById(\"id\").value;\r\n");
      out.write("			if (id.length == 0 || id.length == \"\") {\r\n");
      out.write("				alert('아이디 형식이 잘못되었습니다.');\r\n");
      out.write("				return;\r\n");
      out.write("			}\r\n");
      out.write("			\r\n");
      out.write("			var data = {requestCode: 'idCheck', id: id}	\r\n");
      out.write("			var result = idTest(data);\r\n");
      out.write("			if (result == 'ok') {\r\n");
      out.write("				alert('사용 가능합니다.');\r\n");
      out.write("				idAvailable = true;\r\n");
      out.write("			} else {\r\n");
      out.write("				alert('사용중인 아이디 입니다.');\r\n");
      out.write("			}\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function idAvailableCheck() {\r\n");
      out.write("			var id = document.getElementById('id').value;\r\n");
      out.write("			if (id.length < 6 || id.length > 12) {\r\n");
      out.write("		        alert('아이디는 6글자 이상, 12글자 이하만 이용 가능합니다.');\r\n");
      out.write("			}\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function isSame() {\r\n");
      out.write("			var id = document.getElementById('id').value;\r\n");
      out.write("			var pass1 = document.getElementById('pass_1').value;\r\n");
      out.write("			if (id == pass1) {\r\n");
      out.write("				alert('아이디와 비밀번호는 같을 수 없습니다!');\r\n");
      out.write("			}\r\n");
      out.write("			\r\n");
      out.write("		    if (pass1.length < 6 || pass1.length > 16) {\r\n");
      out.write("		        alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');\r\n");
      out.write("		        document.getElementById('pass_1').value=document.getElementById('pass_2').value='';\r\n");
      out.write("		        document.getElementById('same').innerHTML='';\r\n");
      out.write("		    }\r\n");
      out.write("		    if (document.getElementById('pass_1').value!='' && document.getElementById('pass_2').value!='') {\r\n");
      out.write("		        if (document.getElementById('pass_1').value==document.getElementById('pass_2').value) {\r\n");
      out.write("		            document.getElementById('same').innerHTML='비밀번호가 일치합니다.';\r\n");
      out.write("		            document.getElementById('same').style.color='blue';\r\n");
      out.write("		        }\r\n");
      out.write("		        else {\r\n");
      out.write("		            document.getElementById('same').innerHTML='비밀번호가 일치하지 않습니다.';\r\n");
      out.write("		            document.getElementById('same').style.color='red';\r\n");
      out.write("		        }\r\n");
      out.write("		    }\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function checkExistData(value, dataName) {\r\n");
      out.write("	        if (value == \"\") {\r\n");
      out.write("	            alert(dataName + \" 입력해주세요!\");\r\n");
      out.write("	            return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        return true;\r\n");
      out.write("	    }\r\n");
      out.write("		\r\n");
      out.write("		function checkUserId(id) {\r\n");
      out.write("	        if (!checkExistData(id, \"아이디를\"))\r\n");
      out.write("	            return false;\r\n");
      out.write("	 \r\n");
      out.write("	        var idRegExp = /^[a-zA-z0-9]{4,12}$/;\r\n");
      out.write("	        if (!idRegExp.test(id)) {\r\n");
      out.write("	            alert(\"아이디는 영문 대소문자와 숫자 6~12자리로 입력해야합니다!\");\r\n");
      out.write("	            form.userId.value = \"\";\r\n");
      out.write("	            form.userId.focus();\r\n");
      out.write("	            return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        return true;\r\n");
      out.write("	    }\r\n");
      out.write("		\r\n");
      out.write("		function checkPassword(id, password) {\r\n");
      out.write("	        if (!checkExistData(password, \"비밀번호를\")) {\r\n");
      out.write("	        	return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        \r\n");
      out.write("	        if (password.length < 6 || password.length > 16) {\r\n");
      out.write("	        	 alert(\"비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.\");\r\n");
      out.write("		         return false;\r\n");
      out.write("	        }\r\n");
      out.write("	 \r\n");
      out.write("	        if (id == password) {\r\n");
      out.write("	            alert(\"아이디와 비밀번호는 같을 수 없습니다!\");\r\n");
      out.write("	            return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        return true;\r\n");
      out.write("	    }\r\n");
      out.write("		\r\n");
      out.write("		function checkMail(mail) {\r\n");
      out.write("	        if (!checkExistData(mail, \"이메일을\"))\r\n");
      out.write("	            return false;\r\n");
      out.write("	 \r\n");
      out.write("	        var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}[.]{1}[A-Za-z]{1,3}$/;\r\n");
      out.write("	        if (!emailRegExp.test(mail)) {\r\n");
      out.write("	            alert(\"이메일 형식이 올바르지 않습니다!\");\r\n");
      out.write("	            return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        \r\n");
      out.write("	        var mailSp = mail.split('@');\r\n");
      out.write("	        if (mailSp[1] != 'hycu.ac.kr') {\r\n");
      out.write("	        	alert(\"한사대 이메일만 사용 가능합니다.\");\r\n");
      out.write("	        	return false;\r\n");
      out.write("	        }\r\n");
      out.write("	        return true;\r\n");
      out.write("	    }\r\n");
      out.write("	</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
