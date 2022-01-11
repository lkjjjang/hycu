package com.hycujjang.freeboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.enumCollection.ResultCode;
import com.hycujjang.freeboard.objectPack.comment.CommentDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDAO;

@WebServlet("/replyDeleteController")
public class ReplyDeleteController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {replyID: replyID, password: pass}
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		ReplyDAO replyDAO = new ReplyDAO();
		
		String inputPassword = requestMap.get("password");
		String replyID = requestMap.get("replyID");
		String replyPassword = replyDAO.getPassword(replyID);
				
		if (replyPassword.equals(inputPassword)) {
			if (delete(replyID) == ResultCode.OK) {
				response.getWriter().write(parseJson("ok"));
			} else {
				response.getWriter().write(parseJson("error"));
			}
		} else {
			response.getWriter().write(parseJson("wrongPass"));
		}
	}
	
	private ResultCode delete(String commentID) {
		ReplyDAO replyDAO = new ReplyDAO();
		int reply = replyDAO.delete(commentID);
		
		if (reply == -1) {
			return ResultCode.ERROR;
		}
		
		return ResultCode.OK;
	}

	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// 앞에서 넘겨준 데이터 형식 {id: commnetID, password: password}		
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
