package com.hycujjang.controller.devStory.objectPack;

public class DevFileDTO {
	private int boardID;
	private int fileID;
	private String userID;
	private String fileRealName;
	private String fileTempName;
	private String fileLastName;
	private String regDate;
	public DevFileDTO() {
		
	}
	public DevFileDTO(int boardID, int fileID, String userID, String fileRealName, String fileTempName,
			String fileLastName, String regDate) {
		super();
		this.boardID = boardID;
		this.fileID = fileID;
		this.userID = userID;
		this.fileRealName = fileRealName;
		this.fileTempName = fileTempName;
		this.fileLastName = fileLastName;
		this.regDate = regDate;
	}
	public int getBoardID() {
		return boardID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileTempName() {
		return fileTempName;
	}
	public void setFileTempName(String fileTempName) {
		this.fileTempName = fileTempName;
	}
	public String getFileLastName() {
		return fileLastName;
	}
	public void setFileLastName(String fileLastName) {
		this.fileLastName = fileLastName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "DevFileDTO [boardID=" + boardID + ", fileID=" + fileID + ", userID=" + userID + ", fileRealName="
				+ fileRealName + ", fileTempName=" + fileTempName + ", fileLastName=" + fileLastName + ", regDate="
				+ regDate + "]";
	}
	
	
}
