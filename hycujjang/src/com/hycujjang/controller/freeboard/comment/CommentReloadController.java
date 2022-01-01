package com.hycujjang.controller.freeboard.comment;

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

import com.hycujjang.objectPack.comment.CommentDAO;
import com.hycujjang.objectPack.comment.CommentDTO;
import com.hycujjang.objectPack.reply.ReplyDAO;
import com.hycujjang.objectPack.reply.ReplyDTO;

@WebServlet("/commentReloadController")
public class CommentReloadController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();

		// json���� �Ѿ�� request�� gson�� �����ϰ� commentDTO�� ����
		// gson���� ����� �ִ� ��ü�� json�� Ű���� Ŭ������ ��������� ��ġ�ؾ���
		CommentDTO commentDTO = gson.fromJson(requestData, CommentDTO.class);
		
		// ��۸�� ������
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = commentDAO.getList(commentDTO.getBbsID());
		commentRegDateModify(commentList);
		
		// ���۸�� ����
		ReplyDAO replyDAO = new ReplyDAO();
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = replyDAO.getList(commentDTO.getBbsID());		
		replyRegDateModify(replyList);		
		
		// ��۸�Ͽ� ���� ����
		for (CommentDTO comment: commentList) {
			for (ReplyDTO reply: replyList) {
				if (comment.getCommentID() == reply.getCommentID()) {					
					comment.addReplyList(reply);
				}
			}
		}

		// dto��ü�� json���� ��ȯ
		String jsonResult = gson.toJson(commentList);
		
		// ��û�� ��������
		response.getWriter().write(jsonResult);
	}
	
	private void commentRegDateModify(ArrayList<CommentDTO> comments) {
		// �ۼ����� '����'�� �ٸ��� ��,��,�� ��� ������ ��,�� ���
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (CommentDTO dto: comments) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
	
	private void replyRegDateModify(ArrayList<ReplyDTO> list) {
		// �ۼ����� '����'�� �ٸ��� ��,��,�� ��� ������ ��,�� ���
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (ReplyDTO dto: list) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
}
