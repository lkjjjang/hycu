package com.hycujjang.devBoard.objectPack;

public class DevBoardDTO {
	private int boardID;
	private String boardTitle;
	private String boardPassword;
	private String boardContent;
	private String boardRegDate;
	public DevBoardDTO() {
	}
	public DevBoardDTO(String boardTitle, String boardPassword, String boardContent) {
		this.boardTitle = boardTitle;
		this.boardPassword = boardPassword;
		this.boardContent = boardContent;
	}
	
	public DevBoardDTO(int boardID, String boardTitle, String boardPassword, String boardContent, String boardRegDate) {
		this.boardID = boardID;
		this.boardTitle = boardTitle;
		this.boardPassword = boardPassword;
		this.boardContent = boardContent;
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
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardRegDate() {
		return boardRegDate;
	}
	public void setBoardRegDate(String boardRegDate) {
		this.boardRegDate = boardRegDate;
	}
	@Override
	public String toString() {
		return "DevBoardDTO [boardID=" + boardID + ", boardTitle=" + boardTitle + ", boardPassword=" + boardPassword
				+ ", boardContent=" + boardContent + ", boardRegDate=" + boardRegDate + "]";
	}
	
	
	
}
