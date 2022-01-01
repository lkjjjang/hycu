package com.hycujjang.controller.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hycujjang.objectPack.user.UserDAO;
import com.hycujjang.objectPack.user.UserDTO;
import com.hycujjang.util.SHA256;

@WebServlet("/userRegisterController")
public class UserRegisterController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String userPassword = null;
		String userEmail = null;
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		if (request.getParameter("userPassword") != null) {
			userPassword = request.getParameter("userPassword");
		}
		
		if (request.getParameter("userEmail") != null) {
			userEmail = request.getParameter("userEmail");
		}
		
		if (userID.equals("") || userPassword.equals("") || userEmail.equals("") 
				|| userID == null || userPassword == null || userEmail == null) {
			pageBack(response, "입력이 안 된 사항이 있습니다.");
		}
		
		// 학교 이메일 검사
		String[] mailCheck = userEmail.split("@");
		if (!mailCheck[1].equals("hycu.ac.kr")) {
			pageBack(response, "한사대 이메일만 이용 가능 합니다.");
		}
		
		UserDAO user = new UserDAO();
		UserDTO userDTO = new UserDTO(userID, userPassword, userEmail, SHA256.getSHA256(userEmail), false);
		
		if (user.hasID(userDTO)) {
			pageBack(response, "이미 존재하는 아이디 입니다.");
		}
		
		EmailSend mail = new EmailSend(userDTO);
		int sendResult = mail.send();
		if (sendResult == 1) {
			user.join(userDTO);
			pageBack(response, "메일발송 성공", "emailSendAction.jsp");
		} else {
			pageBack(response, "이메일 발송에 실패 했습니다. 다시 시도해 주세요");
		}	
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg, String url) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = '" + url + "'");
		script.println("</script>");
		script.close();
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
	
	
	
	
	
	
	
	
	
	
}
