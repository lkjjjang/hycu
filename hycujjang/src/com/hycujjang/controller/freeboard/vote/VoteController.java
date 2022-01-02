package com.hycujjang.controller.freeboard.vote;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.hycujjang.enumCollection.ResultCode;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.vote.VoteDAO;
import com.hycujjang.objectPack.vote.VoteDTO;

@WebServlet("/voteController")
public class VoteController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();
		
		// json���� �Ѿ�� request�� gson�� �����ϰ� commentDTO�� ����
		VoteDTO voteDTO = gson.fromJson(requestData, VoteDTO.class);
		
		// ok ��õ���� no �̹���õ�� error �����ͺ��̽� ����		
		ResultCode rc = upvoteUpdate(voteDTO);		
		String resultCode = resultCodeParse(rc);
		
		// ��õ���� �޾ƿ� json ��ȯ
		VoteDAO voteDAO = new VoteDAO();
		String upvoteCount = voteDAO.getUpvoteCount(voteDTO) + "";
		String result = getJson(resultCode, upvoteCount);
		
		// ��û�� ��������
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
		// true�� ��� �̹� ��õ�� ����
		if (voteDAO.upVoteCheck(voteDTO)) {
			return ResultCode.NO;
		}
		
		// ��õ�� FREE_BBS ���̺� ��õ�� ����
		int upResult = voteDAO.upvote(voteDTO);
		if (upResult == 1) {
			BbsDAO bbsDAO = new BbsDAO();
			int bbsID = Integer.parseInt(voteDTO.getBbsID());
			bbsDAO.update(bbsID);
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
