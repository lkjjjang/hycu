package com.hycujjang.lectureboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.lectureboard.objectPack.evaluaion.EvaluationDAO;
import com.hycujjang.lectureboard.objectPack.evaluaion.EvaluationDTO;

@WebServlet("/evaluationRegisterController")
public class EvaluationRegisterController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestJson = br.readLine();
		HashMap<String, String> map = new HashMap<String, String>();
		map = getMap(requestJson);

		if (map == null) {
			response.getWriter().write(parseJson("isNull"));
			return;
		}
		
		String userID = (String) request.getSession().getAttribute("userID");
		int evaluationID = Integer.parseInt(map.get("evaluationID"));
		String lectureName = map.get("lectureName");
		String professorName = map.get("professorName");
		int lectureYear = Integer.parseInt(map.get("lectureYear"));
		String semesterDivide = map.get("semesterDivide");
		String lectureDivide = map.get("lectureDivide");
		String evaluationTitle = map.get("evaluationTitle");
		String evaluationContent = map.get("evaluationContent");
		String totalScore = map.get("totalScore");
		String creditScore = map.get("creditScore");
		String comfortableScore = map.get("comfortableScore");
		String lectureScore = map.get("lectureScore");

		boolean isRegister = true;
		if (evaluationID != 0) {
			isRegister = false;
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		EvaluationDTO evaluationDTO = new EvaluationDTO(evaluationID, userID, lectureName, professorName, lectureYear,
				semesterDivide, lectureDivide, evaluationTitle, evaluationContent,
				totalScore, creditScore, comfortableScore, lectureScore, 0);
		
		if (isRegister) {
			int writeResult = evaluationDAO.write(evaluationDTO);
			if (writeResult == 1) {
				response.getWriter().write("[{\"resultCode\":\"ok\"}]");
			} else {
				response.getWriter().write("[{\"resultCode\":\"error\"}]");
			}
		} else {
			int updateResult = evaluationDAO.update(evaluationDTO);
			if (updateResult == 1) {
				response.getWriter().write("[{\"resultCode\":\"ok\"}]");
			} else {
				response.getWriter().write("[{\"resultCode\":\"error\"}]");
			}
		}
	}
	
	private HashMap<String, String> getMap(String json) {	
		StringBuilder key = new StringBuilder(json.length() / 2);
        StringBuilder value = new StringBuilder(json.length() /2 );
        HashMap<String, String> result = new HashMap<String, String>();
        char[] chars = json.toCharArray();
        int startInx = 2;
        // {"evaluationID":"17","lectureName":"웹서비스와애플리케이션기초"}
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
	
	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
}
