package com.hycujjang.controller.freeboard;

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

import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.freeBBS.BbsDTO;
import com.hycujjang.objectPack.freeBBS.ViewBbsDTO;

@WebServlet("/freeBoardListController")
public class FreeBoardListController extends HttpServlet {
	
	public static int freeBBSListPrintCount = 20;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (request.getParameter("pageNumber") == null) {
			pageBack(response, "�߸��� ���� �Դϴ�.");
		}
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		if (userID == null) {
			// �α��� �ȵ�
			pageBack(response, "�α����� ��� �����մϴ�.");
		} else {
			// �α��� ��
			// �Խñ� ����
			ArrayList<ViewBbsDTO> bbsList = new ArrayList<ViewBbsDTO>();
			BbsDAO bbsDAO = new BbsDAO();
			bbsList = bbsDAO.getList(pageNumber);
			regDateModify(bbsList);
			
			// ����¡ó���� ���� �Խñ� ���� �ľ�
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
		// �ۼ����� '��,��,��'�� �ٸ��� ��,��,�� ��� ������ ��,�� ���
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		// db �������� 2022-01-06 17:53:29
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