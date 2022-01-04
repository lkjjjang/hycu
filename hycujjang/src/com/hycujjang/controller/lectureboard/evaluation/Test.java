package com.hycujjang.controller.lectureboard.evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.evaluation.EvaluationDAO;
import com.hycujjang.objectPack.evaluation.EvaluationDTO;

@WebServlet("/evaluationRegisterControllerr")
public class Test extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestJson = br.readLine();
		HashMap<String, String> map = new HashMap<String, String>();
		map = getMap(requestJson);
		if (map == null) {
			response.getWriter().write(parseJson("isNull"));
		}
		
		String userID = (String) request.getSession().getAttribute("userID");
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
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		EvaluationDTO evaluationDTO = new EvaluationDTO(0, userID, lectureName, professorName, lectureYear,
				semesterDivide, lectureDivide, evaluationTitle, evaluationContent,
				totalScore, creditScore, comfortableScore, lectureScore, 0);
		int writeResult = evaluationDAO.write(evaluationDTO);
		if (writeResult == 1) {
			response.getWriter().write("[{\"resultCode\":\"ok\"}]");
		} else {
			response.getWriter().write("[{\"resultCode\":\"error\"}]");
		}
	}
	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	private HashMap<String, String> getMap(String json) {
		// 앞에서 넘겨준 데이터 형식 {id: commnetID, password: password}		
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			if (sp.length == 1) {
				return null;
			}
			result.put(sp[0], sp[1]);
		}
		return result;
	}

}
