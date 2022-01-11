package com.hycujjang.service.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hycujjang.service.objectPack.user.UserDAO;
import com.hycujjang.util.SHA256;

@WebServlet("/loginController")
public class LoginController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {id: id, password: password} �Ѿ���� ����
		BufferedReader br = request.getReader();
		String requestData = br.readLine();	
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		if (requestMap == null) {
			response.getWriter().write(parseJson("nullCkeck"));
			return;
		}
		
		String userID = requestMap.get("id");
		String userPassword = SHA256.getSHA256(requestMap.get("password"));
		String resultJson = null;
		
		// ���� �α��α����κ� 
		// db�� ���̵�, ����� ��ġ�ϸ� ���ǿ� userID�� ������� 
		// ���ǿ� �����ϴ� ����̽� ������ ����
		// ������������� ���ǰ��� ������ �α��ε� ���·� ����
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(userID, userPassword);
		HttpSession session = request.getSession();
		
		if (result == 1) {
			if (!userDAO.hasEmailChecked(userID)) {
				resultJson = parseJson("unCheckedEmail");
			} else {
				session.setAttribute("userID", userID);
				resultJson = parseJson("ok");
			}
		} else if (result == 0){
			resultJson = parseJson("wrongPass");
		} else if (result == -1){
			resultJson = parseJson("wrongID");
		} else if (result == -2){
			resultJson = parseJson("wrongDB");
		}
		response.getWriter().write(resultJson);
	}

	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// �տ��� �Ѱ��� ������ ���� {id: commnetID, password: password}		
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