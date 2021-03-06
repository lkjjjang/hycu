package com.hycujjang.freeboard.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.hycujjang.enumCollection.ResultCode;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.upvote.VoteDAO;
import com.hycujjang.freeboard.objectPack.upvote.VoteDTO;

@WebServlet("/voteController")
public class VoteController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();
		
		// json으로 넘어온 request를 gson이 분해하고 commentDTO를 생성
		VoteDTO voteDTO = gson.fromJson(requestData, VoteDTO.class);
		
		// ok 추천성공 no 이미추천함 error 데이터베이스 오류		
		ResultCode rc = upvoteUpdate(voteDTO);		
		String resultCode = resultCodeParse(rc);
		
		// 추천갯수 받아와 json 변환
		VoteDAO voteDAO = new VoteDAO();
		String upvoteCount = voteDAO.getUpvoteCount(voteDTO) + "";
		String result = getJson(resultCode, upvoteCount);
		
		// 요청에 응답해줌
		response.getWriter().write(result);	
	}
	
	private String resultCodeParse(ResultCode rc) {
		String result = null;
		switch (rc) {
			case OK:
				result = "ok";
				break;
			case NO:
				result = "no";
				break;
			case ERROR:
				result = "error";
				break;
			default:
				System.out.print("upvote error");
				break;
		}
		return result;
	}
	
	private ResultCode upvoteUpdate(VoteDTO voteDTO) {		
		VoteDAO voteDAO = new VoteDAO();
		// true인 경우 이미 추천한 상태
		if (voteDAO.upVoteCheck(voteDTO)) {
			return ResultCode.NO;
		}
		
		int upResult = voteDAO.upvote(voteDTO);
		if (upResult == 1) {
			return ResultCode.OK;
		} else {
			return ResultCode.ERROR;
		}
		
	}
	
	private String getJson(String resultCode, String upvoteCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\",");
		sb.append("\"upvoteCount\":\"");
		sb.append(upvoteCount);
		sb.append("\"}]");
		
		return sb.toString();
	}
}
