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

@WebServlet("/evaluationDeleteController")
public class EvaluationDeleteController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // {id: id} �Ѿ���� ����
		BufferedReader br = request.getReader();
		String requestJson = br.readLine();
		HashMap<String, String> map = new HashMap<String, String>();
		map = getMap(requestJson);
		if (map == null) {
			response.getWriter().write(parseJson("isNull"));
		}
		
		String id = map.get("id");
		
		String result = null;
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		// ��õ���̺������� �����ؾ���
		if (evaluationDAO.delete(id) == 1) {
			result = parseJson("ok");
		} else {
			result = parseJson("error");
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
			// ���ø� �ϸ鼭 value �� ������ ������ü�� �ȵǴ� nullüũ ���ص� ��
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