package com.hycujjang.lectureboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.lectureboard.objectPack.evaluaion.EvaluationDAO;
import com.hycujjang.lectureboard.objectPack.likey.LikeyDAO;
import com.hycujjang.lectureboard.objectPack.likey.LikeyDTO;

@WebServlet("/likecontroller")
public class LikeController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {id: id} 넘어오는 형태
		BufferedReader br = request.getReader();
		String requestJson = br.readLine();
		HashMap<String, String> map = new HashMap<String, String>();
		map = getMap(requestJson);
		if (map == null) {
			response.getWriter().write(parseJson("isNull"));
		}
		
		String userID = (String) request.getSession().getAttribute("userID");
		int evaluationID = Integer.parseInt(map.get("id"));
		LikeyDTO likeyDTO = new LikeyDTO(userID, evaluationID);
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		LikeyDAO likeyDAO = new LikeyDAO();
		String result = null;
		// true:추천 누른글, false:안누름
		boolean likeCheck = likeyDAO.likeCheck(likeyDTO);
		
		if (!likeCheck) {
			// 추천테이블에 추천 생성
			int likeResult = likeyDAO.like(likeyDTO);
			// 강의테이블에 추천 증가
			int evaluationResult = evaluationDAO.like(likeyDTO);
			
			if (likeResult == 1 && evaluationResult == 1) {
				result = parseJson("ok");
			} else {
				result = parseJson("error");
			}
		} else {
			result = parseJson("hasLike");
		}	
		response.getWriter().write(result);
	}
	
	private HashMap<String, String> getMap(String json) {	
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			if (sp.length == 1) {
				return null;
			}
			// 스플릿 하면서 value 가 없을떈 생성자체가 안되니 null체크 안해도 됨
			result.put(sp[0], sp[1]);
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
