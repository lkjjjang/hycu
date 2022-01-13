package com.hycujjang.devBoard.objectPack;

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
	
	public DevFileDTO(String userID, String fileRealName, String fileTempName) {
		this.userID = userID;
		this.fileTempName = fileTempName;
		this.fileRealName = fileRealName;
	}
	
	public DevFileDTO(int fileID, int boardID, String fileTempName, String fileLastName) {
		this.boardID = boardID;
		this.fileID = fileID;
		this.fileTempName = fileTempName;
		this.fileLastName = fileLastName;
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
