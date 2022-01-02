package com.hycujjang.controller.freeboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.freeBBS.BbsDAO;

@WebServlet("/contentModify")
public class ContentModify extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {id: bbsId, password: password} 이렇게 넘어옴
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		String inputPass = requestMap.get("password");
		String bbsID = requestMap.get("id");
		
		BbsDAO bbsDAO = new BbsDAO();
		String pass = bbsDAO.getContentPassword(bbsID);
		
		if (pass.equals(inputPass)) {
			response.getWriter().write("[{\"resultCode\":\"ok\"}]");
		} else {
			response.getWriter().write("[{\"resultCode\":\"wrongPass\"}]");
		}
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
