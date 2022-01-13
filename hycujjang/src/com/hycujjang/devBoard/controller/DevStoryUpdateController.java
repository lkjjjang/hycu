package com.hycujjang.devBoard.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.devBoard.objectPack.DevBoardDAO;
import com.hycujjang.devBoard.objectPack.DevBoardDTO;
import com.hycujjang.devBoard.objectPack.DevFileDAO;
import com.hycujjang.devBoard.objectPack.DevFileDTO;
import com.hycujjang.freeboard.objectPack.file.FileDAO;
import com.hycujjang.freeboard.objectPack.file.FileDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.util.FileUtils;

@WebServlet("/devStoryUpdateController")
public class DevStoryUpdateController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {boardID: boardID, password: pass} 이렇게 넘어옴
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			pageBack(response, "로그인후 사용 가능합니다.");
		}
		
		String password = request.getParameter("password");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		// 글 내용은 html태그가 함께 넘어와 null이 될수 없음
		if (isWhiteSpace(password) || isWhiteSpace(title)) {
			pageBack(response, "입력 안 된 사항이 있습니다.");
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
		// 본문에서 사용된 파일 리스트
		ArrayList<String> usedFileList = fileUtils.getUsedFileList(contentSplit, userID);	
		// 파일이동후 이동한 파일 리스트
		ArrayList<String> reNameList = fileUtils.moveFile(directory, tempDir, usedFileList);	
		// 임시폴더로 되어 있는 이미지 경로를 정식 경로로 바꿔줌
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			content = fileUtils.getModifyedContent(contentSplit, usedFileList, reNameList, userID);
		}
		
		String modifydeContent = changeImgStyleByWidth(content);
		DevBoardDAO devBoardDAO = new DevBoardDAO();
		DevBoardDTO devBoardDTO = new DevBoardDTO(boardID, title, password, modifydeContent, null);
		int result = devBoardDAO.contentUpdate(devBoardDTO);
		int imageUpdateResult = 0;
		
		// 이미지정보 추가
		DevFileDAO devFileDAO = new DevFileDAO();	
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			
			for (int i = 0; i < usedFileList.size(); i++) {
				int fileID = devFileDAO.getFileID(userID, usedFileList.get(i));
				DevFileDTO devFileDTO = new DevFileDTO(fileID, boardID, usedFileList.get(i), reNameList.get(i));
				imageUpdateResult = devFileDAO.updateFileName(devFileDTO);
			}
		}
		
		if (result == -1 || imageUpdateResult == -1) {
			devBoardDAO.delete(devBoardDTO.getBoardID() + "");			
			pageBack(response, "작성한 글 등록에 실패 했습니다.");
		} else {
			//정상적으로 등록 되었습니다.
			pageBack(response, "정상적으로 수정 되었습니다.", "devStoryListController?pageNumber=1");
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
