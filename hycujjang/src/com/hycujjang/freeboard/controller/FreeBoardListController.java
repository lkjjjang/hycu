package com.hycujjang.freeboard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.ViewBbsDTO;

@WebServlet("/freeBoardListController")
public class FreeBoardListController extends HttpServlet {
	
	public static int freeBBSListPrintCount = 20;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		if (userID == null) {
			// 로그인 안됨
			pageBack(response, "로그인후 사용 가능합니다.");
		} else {
			// 로그인 됨
			// 게시글 생성
			ArrayList<ViewBbsDTO> bbsList = new ArrayList<ViewBbsDTO>();
			BbsDAO bbsDAO = new BbsDAO();
			bbsList = bbsDAO.getList(pageNumber);
			regDateModify(bbsList);
			
			// 페이징처리를 위해 게시글 갯수 파악
			int listCount = getListCount(bbsList.size());			
			request.setAttribute("freeBBS", bbsList);
			request.setAttribute("listCount", listCount);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("freeBBSListPrintCount", FreeBoardListController.freeBBSListPrintCount);
			
			String devices = (String) request.getSession().getAttribute("devices");	
			if (devices.equals("mobile")) {
				request.getRequestDispatcher("freeBoardMobile.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("freeBoard.jsp").forward(request, response);
			}
		}
	}
	
	private void regDateModify(ArrayList<ViewBbsDTO> list) {
		// 작성일자 '년,월,일'이 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		// db 저장형식 2022-01-06 17:53:29
		for (ViewBbsDTO dto: list) {
			String[] dtoDate = dto.getBbsDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setBbsDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setBbsDate(dtoDate[0]);
			}
		}
	}
	
	private int getListCount(int listCount) {
		int result = listCount / FreeBoardListController.freeBBSListPrintCount;
		
		if (listCount % FreeBoardListController.freeBBSListPrintCount > 0) {
			result += 1;
		}
		return result;
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
