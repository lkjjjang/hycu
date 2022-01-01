package com.hycujjang.controller.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hycujjang.objectPack.user.UserDAO;

@WebServlet("/loginController")
public class LoginController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String userPassword = null;
		UserDAO userDAO = new UserDAO();
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		if (request.getParameter("userPassword") != null) {
			userPassword = request.getParameter("userPassword");
		}
		
		if (userID == null || userPassword == null 
				|| userID.equals("") || userID.equals("")) {
			pageBack(request, response, "�Է� �� �� ������ �ֽ��ϴ�.");
		}
		
		// ���� �α��α����κ� 
		// db�� ���̵�, ����� ��ġ�ϸ� ���ǿ� userID�� ������� 
		// ������������� ���ǰ��� ������ �α��ε� ���·� ����
		int result = userDAO.login(userID, userPassword);
		HttpSession session = request.getSession();
		
		if (result == 1) {
			if (!userDAO.hasEmailChecked(userID)) {
				pageBack(request, response, "�̸��� ������ ��� �����մϴ�.");
			}
			
			session.setAttribute("userID", userID);
			response.sendRedirect("lectureBoardController");
		} else if (result == 0){
			pageBack(request, response, "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
		} else if (result == -1){
			pageBack(request, response, "�������� �ʴ� ���̵� �Դϴ�.");
		} else if (result == -2){
			pageBack(request, response, "������ ���̽� ���� �Դϴ�.");
		}
	}
	
	private void pageBack(HttpServletRequest request, HttpServletResponse response, String arlertMsg) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + arlertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
}