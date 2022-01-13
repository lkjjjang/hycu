package com.hycujjang.freeboard.controller;

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

import com.hycujjang.freeboard.objectPack.file.FileDAO;
import com.hycujjang.freeboard.objectPack.file.FileDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.util.FileUtils;

@WebServlet("/freeBoardRegister")
public class FreeBoardRegister extends HttpServlet{
	private String backURL = "freeBoardListController?pageNumber=1";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickName = null;
		String password = null;
		String title = null;
		String content = null;
		String userID = null;
		
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		if (userID == null) {
			pageBack(response, "�α����� ��� �����մϴ�.");
		}
		
		if (request.getParameter("nickName") != null) {
			nickName = request.getParameter("nickName");
		}
		
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		}
		
		if (request.getParameter("title") != null) {
			title = request.getParameter("title");
		}
		
		if (request.getParameter("content") != null) {
			content = request.getParameter("content");
		}
		
		// �� ������ html�±װ� �Բ� �Ѿ�� null�� �ɼ� ����
		if (isWhiteSpace(nickName) || isWhiteSpace(password) || isWhiteSpace(title)) {
			pageBack(response, "�Է� �� �� ������ �ֽ��ϴ�.");
			return;
		}
		System.out.println("content : " + content);
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
		System.out.println("usedFileList-----------------");
		for (String s: usedFileList) {
			System.out.println(s);
		}
		// �̵��� ���� ����Ʈ
		ArrayList<String> reNameList = fileUtils.moveFile(directory, tempDir, usedFileList);
		System.out.println("reNameList-----------------");
		for (String s: reNameList) {
			System.out.println(s);
		}
		// �ӽ������� �Ǿ� �ִ� �̹��� ��θ� ���� ��η� �ٲ���
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			content = fileUtils.getModifyedContent(contentSplit, usedFileList, reNameList, userID);
			System.out.println("getModifyedContent-----------------");
			System.out.println(content);
		}
		
		// �̹��� ���ε�� ������ ������ ���� ������ px�� �����Ǿ� �־� 
		// ������������ �̹��� ������� �ȵǴ� ���󶧹��� px ��  % �� ����
		String modifydeContent = changeImgStyleByWidth(content);
		System.out.println("modifydeContent-----------------");
		System.out.println(modifydeContent);
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO(0, nickName, password, "NOW()", title, modifydeContent, 0, 0, 0, 0);
		int result = bbsDAO.write(bbsDTO);
		int imageUpdateResult = 0;
		
		// �̹������� �߰�
		FileDAO fileDAO = new FileDAO();	
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			int bbsID = bbsDAO.getBbsID(bbsDTO);
			
			for (int i = 0; i < usedFileList.size(); i++) {
				int fileID = fileDAO.getFileID(userID, usedFileList.get(i));
				FileDTO fileDTO = new FileDTO(fileID, bbsID, usedFileList.get(i), reNameList.get(i));
				imageUpdateResult = fileDAO.updateFileName(fileDTO);
			}
		}
		
		if (result == -1 || imageUpdateResult == -1) {
			bbsDAO.delete(bbsDTO.getBbsID() + "");			
			pageBack(response, "�ۼ��� �� ��Ͽ� ���� �߽��ϴ�.");
		} else {
			//���������� ��� �Ǿ����ϴ�.
			pageBack(response, "���������� ��� �Ǿ����ϴ�.", backURL);
		}
	}
	
	private String changeImgStyleByWidth(String contents) {
		 int sbCapacity = contents.length();
	        StringBuilder sb = new StringBuilder(sbCapacity * 2);
	        String targetStr = "style=\"width:";
	        char[] targetCh = targetStr.toCharArray();
	        char[] contentsChars = contents.toCharArray();

	        for (int i = 0; i < contentsChars.length; i++) {
	            if (contentsChars[i] == 's') {
	                boolean isImgTag = false;
	                for (int j = 0; j < targetCh.length; j++) {
	                    if (contentsChars[i] != targetCh[j]) {
	                        isImgTag = false;
	                        sb.append(contentsChars[i]);
	                        break;
	                    } else {
	                        isImgTag = true;
	                        sb.append(contentsChars[i++]);
	                    }
	                }

	                if (isImgTag) {
	                    sb.append(contentsChars[i]);
	                }

	                boolean inputSizePx = false;
	                int imgSizeDigitMin = i;
	                int imgSizeDigitMax = 0;

	                for (int k = 1; k <= 6; k++) {
	                    int searchLimit = 10;
	                    if (i + searchLimit > sbCapacity) {
	                        break;
	                    }

	                    if (contentsChars[i + k] == 'p') {
	                        imgSizeDigitMax = i + k;
	                        if (contentsChars[i + k + 1] == 'x') {
	                            inputSizePx = true;
	                        }
	                    }
	                }
	                char[] size = {'1', '0', '0', '%', ';', '"'};
	                if (inputSizePx) {
	                    int digit = imgSizeDigitMax - imgSizeDigitMin - 1;
	                    sb.append(size);
	                    i += digit + 4;
	                }
	            } else {
	                sb.append(contentsChars[i]);
	            }
	        }
	        return sb.toString();
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
