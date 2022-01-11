package com.hycujjang.freeboard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hycujjang.freeboard.objectPack.comment.CommentDAO;
import com.hycujjang.freeboard.objectPack.comment.CommentDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDAO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDTO;

@WebServlet("/commentRegisterController")
public class CommentRegisterController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();

		// json���� �Ѿ�� request�� gson�� �����ϰ� commentDTO�� ����
		CommentDTO commentDTO = gson.fromJson(requestData, CommentDTO.class);
		// �Է°� �˻�
		if (commentDTO.getWriteID().equals("") || commentDTO.getPassword().equals("") || commentDTO.getComment().equals("") ||
				commentDTO.getWriteID() == null || commentDTO.getPassword() == null || commentDTO.getComment() == null) {
			response.getWriter().write(parseJson("isNull"));
			return;
		}

		// ������ ����
		String test = getClientIP(request);
		String[] ipNums = test.split("\\.");
		if (ipNums.length == 4) {
			commentDTO.setIp(ipNums[2] + "." + ipNums[3]);
		} else {
			commentDTO.setIp("0.0");
		}
		
		// ������̺� ����
		CommentDAO commentDAO = new CommentDAO();
		int result = commentDAO.write(commentDTO);
		if (result != 1) {
			response.getWriter().write(parseJson("error"));
			return;
		}
		
		// ��û�� ��������
		response.getWriter().write(parseJson("ok"));
	}
	
	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Procy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
