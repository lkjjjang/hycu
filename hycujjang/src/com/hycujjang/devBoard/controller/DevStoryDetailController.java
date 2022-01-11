package com.hycujjang.devBoard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.devBoard.objectPack.DevBoardDAO;
import com.hycujjang.devBoard.objectPack.DevBoardDTO;
import com.hycujjang.devBoard.objectPack.DevCommentDAO;
import com.hycujjang.devBoard.objectPack.DevCommentDTO;
import com.hycujjang.devBoard.objectPack.DevReplyDAO;
import com.hycujjang.devBoard.objectPack.DevReplyDTO;
import com.hycujjang.freeboard.objectPack.comment.CommentDAO;
import com.hycujjang.freeboard.objectPack.comment.CommentDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDAO;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.ViewBbsDTO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDAO;
import com.hycujjang.freeboard.objectPack.reply.ReplyDTO;

@WebServlet("/devStoryDetailController")
public class DevStoryDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardID = 0;		
		if (request.getParameter("id") != null) {
			try {
				boardID = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e){
				System.out.print("게시판 글 번호 오류");
			}			
		}
		
		DevBoardDAO devBoardDAO = new DevBoardDAO();
		DevBoardDTO devBoardDTO = new DevBoardDTO();
		
		// 글 내용 생성		
		devBoardDTO = devBoardDAO.getDevBoardDetail(boardID);
		regDateModify(devBoardDTO);

		// 댓글목록 생성
		ArrayList<DevCommentDTO> commentList = new ArrayList<DevCommentDTO>();
		commentList = getCommentList(boardID);
		
		// 대댓글목록 생성
		ArrayList<DevReplyDTO> replyList = new ArrayList<DevReplyDTO>();
		replyList = getReplyList(boardID);
		
		// 댓글목록에 대댓글 삽입
		insertReplyInComment(commentList, replyList);

		int commentCount = commentList.size() + replyList.size();
		request.setAttribute("devStoryDetail", devBoardDTO);
		request.setAttribute("comments", commentList);
		request.setAttribute("commentCount", commentCount);
		
		String devices = (String) request.getSession().getAttribute("devices");	
		if (devices.equals("mobile")) {
			request.getRequestDispatcher("devStoryMobile.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("devStory.jsp").forward(request, response);
		}
	}
	
	private ArrayList<DevReplyDTO> getReplyList(int commentID) {
		DevReplyDAO devReplyDAO = new DevReplyDAO();
		ArrayList<DevReplyDTO> list = new ArrayList<DevReplyDTO>();
		list = devReplyDAO.getList(commentID);		
		replyRegDateModify(list);
		
		return list;
	}
	
	private ArrayList<DevCommentDTO> getCommentList(int boardID) {
		DevCommentDAO devCommentDAO = new DevCommentDAO();
		ArrayList<DevCommentDTO> list = new ArrayList<DevCommentDTO>();
		list = devCommentDAO.getList(boardID);
		commentRegDateModify(list);
		
		return list;
	}
	
	private void insertReplyInComment(ArrayList<DevCommentDTO> commentList, ArrayList<DevReplyDTO> replyList) {
		for (DevCommentDTO comment: commentList) {
			for (DevReplyDTO reply: replyList) {
				if (comment.getCommentID() == reply.getCommentID()) {					
					comment.addReplyList(reply);
				}
			}
		}
	}
	
	private void regDateModify(DevBoardDTO devBoardDTO) {
		// 작성일자 '년,월,일'이 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		// db 저장형식 2022-01-06 17:53:29
		String[] dtoDate = devBoardDTO.getBoardRegDate().split(" ");
		if (dtoDate[0].equals(sf.format(nowTime))) {
			devBoardDTO.setBoardRegDate(dtoDate[1].substring(0, 5));
		} else {
			devBoardDTO.setBoardRegDate(dtoDate[0]);
		}
	}
	
	private void commentRegDateModify(ArrayList<DevCommentDTO> list) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (DevCommentDTO dto: list) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
	
	private void replyRegDateModify(ArrayList<DevReplyDTO> list) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (DevReplyDTO dto: list) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
}
