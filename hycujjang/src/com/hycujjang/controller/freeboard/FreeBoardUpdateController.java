package com.hycujjang.controller.freeboard;

import java.io.File;
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

import com.hycujjang.objectPack.file.FileDAO;
import com.hycujjang.objectPack.file.FileDTO;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.freeBBS.BbsDTO;
import com.hycujjang.util.FileUtils;

@WebServlet("/freeBoardUpdateController")
public class FreeBoardUpdateController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			pageBack(response, "�α����� ��� �����մϴ�.");
		}
		
		String password = request.getParameter("password");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int bbsID = Integer.parseInt(request.getParameter("bbsID"));
		// �� ������ html�±װ� �Բ� �Ѿ�� null�� �ɼ� ����
		if (isWhiteSpace(password) || isWhiteSpace(title)) {
			pageBack(response, "�Է� �� �� ������ �ֽ��ϴ�.");
			return;
		}
		
		String[] contentSplit = content.split(" ");			
		String directory = this.getServletContext().getRealPath("/upload/") + getTodayDate();
		String tempDir = this.getServletContext().getRealPath("/tempImg/") + userID;
		File folder = new File(directory);				
		
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		FileUtils fileUtils = new FileUtils();
		// �������� ���� ���� ����Ʈ
		ArrayList<String> usedFileList = fileUtils.getUsedFileList(contentSplit, userID);	
		// �����̵��� �̵��� ���� ����Ʈ
		ArrayList<String> reNameList = fileUtils.moveFile(directory, tempDir, usedFileList);	
		// �ӽ������� �Ǿ� �ִ� �̹��� ��θ� ���� ��η� �ٲ���
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			content = fileUtils.getModifyedContent(contentSplit, usedFileList, reNameList, userID);
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO(bbsID, title, password, content);
		int result = bbsDAO.contentUpdate(bbsDTO);
		int imageUpdateResult = 0;
		int useImageUpdateResult = 0;
		
		// �̹������� �߰�
		FileDAO fileDAO = new FileDAO();	
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			useImageUpdateResult = bbsDAO.updateUseImage(bbsID);
			
			for (int i = 0; i < usedFileList.size(); i++) {
				int fileID = fileDAO.getFileID(userID, usedFileList.get(i));
				FileDTO fileDTO = new FileDTO(fileID, bbsID, usedFileList.get(i), reNameList.get(i));
				imageUpdateResult = fileDAO.updateFileName(fileDTO);
			}
		}
		
		if (result == -1 || imageUpdateResult == -1 || useImageUpdateResult == -1) {
			bbsDAO.delete(bbsDTO.getBbsID() + "");			
			pageBack(response, "�ۼ��� �� ��Ͽ� ���� �߽��ϴ�.");
		} else {
			//���������� ��� �Ǿ����ϴ�.
			pageBack(response, "���������� ��� �Ǿ����ϴ�.", "freeBoardController?pageNumber=1");
		}
	}
	
	private String getTodayDate() {
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(nowTime).toString();
	}
	
	private boolean isWhiteSpace(String param) {
		if (param.equals("") || param == null) {
			return true;
		}		
		
		if (param.trim().length() == 0) {
			return true;
		}
		
		return false;
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
		script.println("history.back()");
		script.println("</script>");
		script.close();
	}
}