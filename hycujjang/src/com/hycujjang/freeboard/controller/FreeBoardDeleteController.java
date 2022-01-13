package com.hycujjang.freeboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.freeboard.objectPack.comment.CommentDAO;
import com.hycujjang.freeboard.objectPack.file.FileDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDAO;
import com.hycujjang.freeboard.objectPack.upvote.VoteDAO;

@WebServlet("/freeBoardDeleteController")
public class FreeBoardDeleteController extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 일반 유저 {bbsID: bbsID, password: password}
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			pageBack(response, "로그인후 사용 가능합니다.");
		} 
		BufferedReader br = request.getReader();
		String requestData = br.readLine();	
		
		if (userID.equals("admin")) {
			deleteAll(requestData);
			String resultJson = getJson("ok");
			response.getWriter().write(resultJson);
		} else {
			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap = jsonParse(requestData);
			
			// bbsID로 패스워드 받아와 비교후 작업
			BbsDAO BbsDAO = new BbsDAO();
			String inputPassword = requestMap.get("password");
			String bbsID = requestMap.get("bbsID");
			String bbsPassword = BbsDAO.getContentPassword(bbsID);	

			String resultCode = null;
			if (bbsPassword.equals(inputPassword)) {
				if (isDeleteAll(bbsID)) {
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
	}
	
	private void deleteAll(String jsonStr) {
		String[] idList = jsonStr.replace(":", "").replace("i", "").replace("d", "").replace("\"", "").replace("{", "").replace("}", "").replace("[", "").replace("]", "").split(",");
		BbsDAO bbsDAO = new BbsDAO();	
		bbsDAO.deleteAll(idList);
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
	
	private boolean isDeleteAll(String bbsID) {
		BbsDAO BbsDAO = new BbsDAO();
		int bbs = BbsDAO.delete(bbsID);
		if (bbs == -1) {
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
		// 앞에서 넘겨준 데이터 형식 {id: bbsID, password: password}		
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
