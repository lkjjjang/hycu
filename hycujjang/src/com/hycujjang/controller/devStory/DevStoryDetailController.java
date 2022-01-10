package com.hycujjang.controller.devStory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.comment.CommentDAO;
import com.hycujjang.objectPack.comment.CommentDTO;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.freeBBS.BbsDTO;
import com.hycujjang.objectPack.freeBBS.ViewBbsDTO;
import com.hycujjang.objectPack.reply.ReplyDAO;
import com.hycujjang.objectPack.reply.ReplyDTO;

@WebServlet("/devStoryDetailController")
public class DevStoryDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bbsID = 0;		
		if (request.getParameter("id") != null) {
			try {
				bbsID = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e){
				System.out.print("�Խ��� �� ��ȣ ����");
			}			
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		ViewBbsDTO bbsDTO = new ViewBbsDTO();
		
		// ��ȸ�� ����
		bbsDAO.hitUpdate(bbsID);
		
		// �� ���� ����		
		bbsDTO = bbsDAO.getFreeBoardDetail(bbsID);
		regDateModify(bbsDTO);

		// ��۸�� ����
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = getCommentList(bbsID);
		
		// ���۸�� ����
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = getReplyList(bbsID);
		
		// ��۸�Ͽ� ���� ����
		insertReplyInComment(commentList, replyList);

		int commentCount = commentList.size() + replyList.size();
		request.setAttribute("freeBoardDetail", bbsDTO);
		request.setAttribute("comments", commentList);
		request.setAttribute("commentCount", commentCount);
		
		String devices = (String) request.getSession().getAttribute("devices");	
		if (devices.equals("mobile")) {
			request.getRequestDispatcher("viewMobile.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("view.jsp").forward(request, response);
		}
	}
	
	private ArrayList<ReplyDTO> getReplyList(int bbsID) {
		ReplyDAO replyDAO = new ReplyDAO();
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = replyDAO.getList(bbsID);		
		replyRegDateModify(replyList);
		
		return replyList;
	}
	
	private ArrayList<CommentDTO> getCommentList(int bbsID) {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = commentDAO.getList(bbsID);
		commentRegDateModify(commentList);
		
		return commentList;
	}
	
	private void insertReplyInComment(ArrayList<CommentDTO> commentList, ArrayList<ReplyDTO> replyList) {
		for (CommentDTO comment: commentList) {
			for (ReplyDTO reply: replyList) {
				if (comment.getCommentID() == reply.getCommentID()) {					
					comment.addReplyList(reply);
				}
			}
		}
	}
	
	private void regDateModify(ViewBbsDTO bbsDTO) {
		// �ۼ����� '��,��,��'�� �ٸ��� ��,��,�� ��� ������ ��,�� ���
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		// db �������� 2022-01-06 17:53:29
		String[] dtoDate = bbsDTO.getBbsDate().split(" ");
		if (dtoDate[0].equals(sf.format(nowTime))) {
			bbsDTO.setBbsDate(dtoDate[1].substring(0, 5));
		} else {
			bbsDTO.setBbsDate(dtoDate[0]);
		}
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