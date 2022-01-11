package com.hycujjang.service.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hycujjang.enumCollection.UserRegisterEnum;
import com.hycujjang.service.controller.email.EmailSend;
import com.hycujjang.service.objectPack.user.UserDAO;
import com.hycujjang.service.objectPack.user.UserDTO;
import com.hycujjang.util.SHA256;

@WebServlet("/userRegisterController")
public class UserRegisterController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// key은 두 종류로 넘어옴 (idCheck, userRegister) 
		// idCehck {requestCode: idCheck, id: userID}		
		// register {requestCode: register, id: userID, pass: pass, email: email}		
		BufferedReader br = request.getReader();
		String requestJson = br.readLine();
		HashMap<String, String> requestMap = jsonParse(requestJson);
		
		String result = null;
		if (requestMap.get("requestCode").equals("idCheck")) {
			// 중복이면 false
			if (idCheck(requestMap)) {
				result = getResultJson("ok");
			} else {
				result = getResultJson("error");
			}
			response.getWriter().write(result);
			return;
		} 
		
		String userID = requestMap.get("id");
		String pass = SHA256.getSHA256(requestMap.get("pass"));
		String email = requestMap.get("email");
		
		UserDAO user = new UserDAO();
		UserDTO userDTO = new UserDTO(userID, pass, email, SHA256.getSHA256(email), false);	
		EmailSend mail = new EmailSend(email);
		int sendResult = mail.send();
		
		if (sendResult == 1) {
			user.join(userDTO);
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);
			result = getResultJson("ok");
		} else {
			result = getResultJson("error");
		}	
		response.getWriter().write(result);
	}
	
	private boolean idCheck(HashMap<String, String> map) {
		String id = map.get("id");
		UserDAO userDAO = new UserDAO();
		return userDAO.hasID(id);
	}
	
	private String getResultJson(String msg) {
		return "[{\"resultCode\":\""+ msg +"\"}]";
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// 앞에서 넘겨준 데이터 형식 {"id":"1111111","pass":"11111111","email":"2021100346@hycu.ac.kr"}
		HashMap<String, String> result = new HashMap<String, String>();
		System.out.println(json);
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
