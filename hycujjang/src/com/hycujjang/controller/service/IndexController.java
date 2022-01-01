package com.hycujjang.controller.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.user.UserDAO;

@WebServlet("/index.jsp")
public class IndexController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID != null) {
			// �̸��� ���� üũ
			UserDAO userDAO = new UserDAO();
			if (!userDAO.hasEmailChecked(userID)) {
				pageBack(response, "�̸��� ���� �� ��� �����մϴ�.");
			}			
			response.sendRedirect("lectureBoardController");
		} else {
			response.sendRedirect("index2.jsp");
		}
	}
	
	private void pageBack(HttpServletResponse response, String arlertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + arlertMsg + "');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
	}
}