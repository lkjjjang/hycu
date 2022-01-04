package com.hycujjang.controller.email;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hycujjang.objectPack.user.UserDAO;
import com.hycujjang.util.SHA256;

@WebServlet("/emailCheckAction")
public class EmailCheckController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("userID") == null) {
			pageBack(response, "잘못된 접근 입니다.");
		}
		
		UserDAO userDAO = new UserDAO();	
		String userID = (String) session.getAttribute("userID");
		String code = request.getParameter("code");
		String userEmail = userDAO.getUserEmail(userID);
		
		if (userEmail == null) {
			userDAO.delete(userID);
			session.removeAttribute("userID");
			pageBack(response, "데이터베이스 오류 입니다.");
		}
		
		if (SHA256.getSHA256(userEmail).equals(code)) {
			userDAO.setUserEmailChecked(userID);
			pageBack(response, "인증에 성공했습니다.", "index.jsp");
		} else {
			userDAO.delete(userID);
			session.removeAttribute("userID");
			pageBack(response, "유효하지 않은 코드 입니다.");
		}
	}
	
	private void pageBack(HttpServletResponse response, String arlertMsg, String url) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + arlertMsg + "');");
		script.println("location.href = '" + url + "'");
		script.println("</script>");
		script.close();
	}
	
	private void pageBack(HttpServletResponse response, String arlertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + arlertMsg + "');");
		script.println("location.href = 'userJoin.jsp'");
		script.println("</script>");
		script.close();
	}

}
