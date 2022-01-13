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
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;

@WebServlet("/devStoryDeleteController")
public class DevStoryDeleteController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {boardID: boardID, password: pass}
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			pageBack(response, "로그인후 사용 가능합니다.");
		} 
		
		BufferedReader br = request.getReader();
		String requestData = br.readLine();	
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		
		// bbsID로 패스워드 받아와 비교후 작업
		DevBoardDAO devBoardDAO = new DevBoardDAO();
		String inputPassword = requestMap.get("password");
		String boardID = requestMap.get("boardID");
		String password = devBoardDAO.getPassword(boardID);	

		String resultCode = null;
		if (password.equals(inputPassword)) {
			if (delete(boardID)) {
				resultCode = "ok";
			} else {
				resultCode = "error";
			}
		} else {
			resultCode = "wrongPass";
		}
			
		String resultJson = getJson(resultCode);
		response.getWriter().write(resultJson);
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
	
	private boolean delete(String boardID) {
		DevBoardDAO devBoardDAO = new DevBoardDAO();
		int delResult = devBoardDAO.delete(boardID);
		if (delResult == -1) {
			return false;
		}
		return true;
	}

	private String getJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {		
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			result.put(sp[0], sp[1]);
		}

		return result;
	}
}
