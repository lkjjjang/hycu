package com.hycujjang.controller.freeboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.freeBBS.BbsDTO;

@WebServlet("/contentModifyController")
public class ContentModifyController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("id");
		if (str == null || str.equals("")) {
			pageBack(response, "잘못된 접근 입니다.");
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		int bbsID = Integer.parseInt(str);
		BbsDTO bbsDTO = new BbsDTO();
		bbsDTO = bbsDAO.getFreeBoardDetail(bbsID);
		
		request.setAttribute("content", bbsDTO);
		request.getRequestDispatcher("contentModify.jsp").forward(request, response);
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
