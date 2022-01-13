package com.hycujjang.freeboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;

@WebServlet("/contentPassCheck")
public class ContentPassCheck extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {bbsID: bbsID, password: password} 이렇게 넘어옴
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = getMap(requestData);
		String inputPass = requestMap.get("password");
		String bbsID = requestMap.get("bbsID");
		
		BbsDAO bbsDAO = new BbsDAO();
		
		String pass = bbsDAO.getContentPassword(bbsID);
		if (pass.equals(inputPass)) {
			response.getWriter().write("[{\"resultCode\":\"ok\"}]");
		} else {
			response.getWriter().write("[{\"resultCode\":\"wrongPass\"}]");
		}
	}
	
	private HashMap<String, String> getMap(String json) {	
		StringBuilder key = new StringBuilder(json.length() / 2);
        StringBuilder value = new StringBuilder(json.length() /2 );
        HashMap<String, String> result = new HashMap<String, String>();
        char[] chars = json.toCharArray();
        int startInx = 2;
        
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == '"' && chars[i] == ',' && chars[i + 1] == '"') {
                boolean isValue = false;
                for (int j = startInx; j < i - 2; j++) {
                    if (!isValue && chars[j] != '"') {
                        key.append(chars[j]);
                    }

                    if (chars[j] == '"') {
                        isValue = true;
                        j += 2;
                    }

                    if (isValue) {
                        value.append(chars[j + 1]);
                    }
                }
                
                String resultKey = key.toString();
                String resultValue = value.toString();
                if (resultValue.equals("") || resultValue == null) {
                	return null;
                }
                
                result.put(resultKey, resultValue);
                startInx = i + 2;
                key.setLength(0);
                value.setLength(0);
            }
            
            if (i == chars.length - 1) {
                boolean isValue = false;
                for (int j = startInx; j < i - 2; j++) {
                    if (!isValue && chars[j] != '"') {
                        key.append(chars[j]);
                    }

                    if (chars[j] == '"') {
                        isValue = true;
                        j += 2;
                    }

                    if (isValue) {
                        value.append(chars[j + 1]);
                    }
                }
                
                String resultKey = key.toString();
                String resultValue = value.toString();
                if (resultValue.equals("") || resultValue == null) {
                	return null;
                }
                
                result.put(resultKey, resultValue);
                startInx = i + 2;
                key.setLength(0);
                value.setLength(0);
            }
        }
        return result;
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
