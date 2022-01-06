package com.hycujjang.controller.freeboard.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.comment.CommentDAO;
import com.hycujjang.enumCollection.ResultCode;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.reply.ReplyDAO;

@WebServlet("/replyDeleteController")
public class ReplyDeleteController extends HttpServlet {
	private ReplyDAO replyDAO = new ReplyDAO();
	private int bbsID;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// {replyID: replyID, password: pass}
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		
		// replyID로 패스워드 받아와 비교후 작업
		String inputPassword = requestMap.get("password");
		String replyID = requestMap.get("replyID");
		String replyPassword = this.replyDAO.getPassword(replyID);
		this.bbsID = this.replyDAO.getBbsID(Integer.parseInt(replyID));
				
		if (replyPassword.equals(inputPassword)) {
			if (delete(replyID) == ResultCode.ERROR) {
				response.getWriter().write(parseJson("error"));
				return;
			}
		} else {
			response.getWriter().write(parseJson("wrongPass"));
			return;
		}
		
		// 게시글 commentCount 수정
		int totalCommentCount = getCommentCount();
		setBbsCommentCount(totalCommentCount);
		response.getWriter().write(parseJson("ok"));
	}
	
	private void setBbsCommentCount(int count) {
		BbsDAO bbsDAO = new BbsDAO();
		bbsDAO.setCommentCountUpdate(count, this.bbsID);
	}
	
	private int getCommentCount() {
		CommentDAO commentDAO = new CommentDAO();
		int commentCount = commentDAO.getCommentCount(this.bbsID);
		int replyCount = this.replyDAO.getReplyCount(this.bbsID);
		
		return commentCount + replyCount;
	}
	
	private ResultCode delete(String commentID) {
		int reply = this.replyDAO.delete(commentID);
		
		if (reply == -1) {
			return ResultCode.ERROR;
		}
		
		return ResultCode.OK;
	}

	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// 앞에서 넘겨준 데이터 형식 {id: commnetID, password: password}		
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			result.put(sp[0], sp[1]);
		}

		return result;
	}
}
