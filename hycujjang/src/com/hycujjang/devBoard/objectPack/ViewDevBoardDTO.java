package com.hycujjang.devBoard.objectPack;

public class ViewDevBoardDTO {
	//boardID, boardTitle, boardPassword, boardRegDate, cmt_count, use_file
	private int boardID;
	private String boardTitle;
	private String boardPassword;
	private String boardRegDate;
	private String cmt_count;
	private String use_file;
	public ViewDevBoardDTO() {
		
	}
	public ViewDevBoardDTO(int boardID, String boardTitle, String boardPassword, String boardRegDate, String cmt_count,
			String use_file) {
		this.boardID = boardID;
		this.boardTitle = boardTitle;
		this.boardPassword = boardPassword;
		this.boardRegDate = boardRegDate;
		this.cmt_count = cmt_count;
		this.use_file = use_file;
	}
	
	public String getBoardRegDate() {
		return boardRegDate;
	}
	public void setBoardRegDate(String boardRegDate) {
		this.boardRegDate = boardRegDate;
	}
	public int getBoardID() {
		return boardID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardPassword() {
		return boardPassword;
	}
	public void setBoardPassword(String boardPassword) {
		this.boardPassword = boardPassword;
	}
	public String getCmt_count() {
		return cmt_count;
	}
	public void setCmt_count(String cmt_count) {
		this.cmt_count = cmt_count;
	}
	public String getUse_file() {
		return use_file;
	}
	public void setUse_file(String use_file) {
		this.use_file = use_file;
	}
	@Override
	public String toString() {
		return "ViewDevBoardListDTO [boardID=" + boardID + ", boardTitle=" + boardTitle + ", boardPassword="
				+ boardPassword + ", cmt_count=" + cmt_count + ", use_file=" + use_file + "]";
	}
	
	
}
