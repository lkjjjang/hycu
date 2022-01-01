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
			pageBack(response, "�Է��� �� �� ������ �ֽ��ϴ�.");
		}
		
		// �б� �̸��� �˻�
		String[] mailCheck = userEmail.split("@");
		if (!mailCheck[1].equals("hycu.ac.kr")) {
			pageBack(response, "�ѻ�� �̸��ϸ� �̿� ���� �մϴ�.");
		}
		
		UserDAO user = new UserDAO();
		UserDTO userDTO = new UserDTO(userID, userPassword, userEmail, SHA256.getSHA256(userEmail), false);
		
		if (user.hasID(userDTO)) {
			pageBack(response, "�̹� �����ϴ� ���̵� �Դϴ�.");
		}
		
		EmailSend mail = new EmailSend(userDTO);
		int sendResult = mail.send();
		if (sendResult == 1) {
			user.join(userDTO);
			pageBack(response, "���Ϲ߼� ����", "emailSendAction.jsp");
		} else {
			pageBack(response, "�̸��� �߼ۿ� ���� �߽��ϴ�. �ٽ� �õ��� �ּ���");
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