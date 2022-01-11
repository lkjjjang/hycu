package com.hycujjang.devBoard.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hycujjang.devBoard.objectPack.DevCommentDAO;
import com.hycujjang.devBoard.objectPack.DevCommentDTO;
import com.hycujjang.devBoard.objectPack.DevReplyDAO;
import com.hycujjang.devBoard.objectPack.DevReplyDTO;

@WebServlet("/devReplyRegisterController")
public class DevReplyRegisterController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		// {writeID: writeID, password: password, boardID: boardID, comment: comment}
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();

		// json으로 넘어온 request를 gson이 분해하고 commentDTO를 생성
		DevReplyDTO devCommentDTO = gson.fromJson(requestData, DevReplyDTO.class);
		// 입력값 검사
		if (devCommentDTO.getNickName().equals("") || devCommentDTO.getPassword().equals("") || devCommentDTO.getReplyComment().equals("") ||
				devCommentDTO.getNickName() == null || devCommentDTO.getPassword() == null || devCommentDTO.getReplyComment() == null) {
			response.getWriter().write(parseJson("isNull"));
			return;
		}

		// 아이피 생성
		String test = getClientIP(request);
		String[] ipNums = test.split("\\.");
		if (ipNums.length == 4) {
			devCommentDTO.setIp(ipNums[2] + "." + ipNums[3]);
		} else {
			devCommentDTO.setIp("0.0");
		}
		
		// 댓글테이블에 저장
		DevReplyDAO devCommentDAO = new DevReplyDAO();
		int result = devCommentDAO.write(devCommentDTO);
		if (result != 1) {
			response.getWriter().write(parseJson("error"));
			return;
		}
		
		// 요청에 응답해줌
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
