package com.hycujjang.controller.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deviceUpdate")
public class DeviceUpdate extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {device: device} 앞에서 넘겨준 형태
		BufferedReader br = request.getReader();
		String requestData = br.readLine();	
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		String device = requestMap.get("device");
		
		HttpSession session = request.getSession();
		session.setAttribute("devices", device);
	}

	private HashMap<String, String> jsonParse(String json) {	
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
