package com.hycujjang.devBoard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.devBoard.objectPack.DevCommentDAO;
import com.hycujjang.devBoard.objectPack.DevReplyDAO;
import com.hycujjang.enumCollection.ResultCode;

@WebServlet("/devReplyDeleteController")
public class DevReplyDeleteController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {replyID: replyID, password: pass} �Ѿ���� ����
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		DevReplyDAO commentDAO = new DevReplyDAO();
		
		String inputPassword = requestMap.get("password");
		String replyID = requestMap.get("replyID");
		String replyPassword = commentDAO.getPassword(replyID);
		
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
	
	private ResultCode delete(String replyID) {
		DevReplyDAO devReplyDAO = new DevReplyDAO();
		int result = devReplyDAO.delete(replyID);
		
		if (result == -1) {
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
		// �տ��� �Ѱ��� ������ ���� {commnetID: commnetID, password: password}		
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
