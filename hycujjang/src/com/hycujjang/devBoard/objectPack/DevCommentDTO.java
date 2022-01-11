package com.hycujjang.devBoard.objectPack;

import java.util.ArrayList;

import com.hycujjang.freeboard.objectPack.reply.ReplyDTO;

public class DevCommentDTO {
	private int commentID;
	private int boardID;
	private String writeID;
	private String password;
	private String comment;
	private String regDate;
	private String ip;
	private ArrayList<DevReplyDTO> replyList;
	
	public DevCommentDTO() {
	}
	public DevCommentDTO(int commentID, int boardID, String writeID, String password, String comment, String regDate,
			String ip) {
		this.commentID = commentID;
		this.boardID = boardID;
		this.writeID = writeID;
		this.password = password;
		this.comment = comment;
		this.regDate = regDate;
		this.ip = ip;
		this.replyList = new ArrayList<>();
	}
	public void addReplyList(DevReplyDTO replyList) {
		this.replyList.add(replyList);
	}
	public ArrayList<DevReplyDTO> getReplyList() {
		return replyList;
	}
	public void setReplyList(ArrayList<DevReplyDTO> replyList) {
		this.replyList = replyList;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getBoardID() {
		return boardID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public String getWriteID() {
		return writeID;
	}
	public void setWriteID(String writeID) {
		this.writeID = writeID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "DevCommentDTO [commentID=" + commentID + ", boardID=" + boardID + ", writeID=" + writeID + ", password="
				+ password + ", comment=" + comment + ", regDate=" + regDate + ", ip=" + ip + ", replyList=" + replyList
				+ "]";
	}
}
