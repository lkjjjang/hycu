package com.hycujjang.devBoard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.devBoard.objectPack.DevBoardDAO;
import com.hycujjang.devBoard.objectPack.DevBoardDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.ViewBbsDTO;

@WebServlet("/updateObjectForward")
public class UpdateObjectForward extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("id");
		if (str == null || str.equals("")) {
			pageBack(response, "잘못된 접근 입니다.");
		}
		
		DevBoardDAO devBoardDAO = new DevBoardDAO();
		int boardID = Integer.parseInt(str);
		DevBoardDTO devBoardDTO = new DevBoardDTO();
		devBoardDTO = devBoardDAO.getDevBoardDetail(boardID);
		
		request.setAttribute("content", devBoardDTO);
		request.getRequestDispatcher("devStoryUpdate.jsp").forward(request, response);
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
