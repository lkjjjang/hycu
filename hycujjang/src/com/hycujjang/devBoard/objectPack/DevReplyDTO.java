package com.hycujjang.devBoard.objectPack;

public class DevReplyDTO {
	private int replyID;
	private int commentID;
	private int boardID;
	private String nickName;
	private String password;
	private String replyComment;
	private String regDate;
	private String ip;
	
	public DevReplyDTO() {
		
	}
	public DevReplyDTO(int replyID, int commentID, int boardID, String nickName, String password, String replyComment,
			String regDate, String ip) {
		this.replyID = replyID;
		this.commentID = commentID;
		this.boardID = boardID;
		this.nickName = nickName;
		this.password = password;
		this.replyComment = replyComment;
		this.regDate = regDate;
		this.ip = ip;
	}
	public int getReplyID() {
		return replyID;
	}
	public void setReplyID(int replyID) {
		this.replyID = replyID;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReplyComment() {
		return replyComment;
	}
	public void setReplyComment(String replyComment) {
		this.replyComment = replyComment;
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
		return "DevReplyDTO [replyID=" + replyID + ", commentID=" + commentID + ", boardID=" + boardID + ", nickName="
				+ nickName + ", password=" + password + ", replyComment=" + replyComment + ", regDate=" + regDate
				+ ", ip=" + ip + "]";
	}
}
