package com.hycujjang.objectPack.freeBBS;

public class ViewBbsDTO {
	private int bbsID;
	private String nickName;
	private String bbsDate;
	private String bbsTitle;
	private String bbsContente;
	private int bbsHit;
	private int upvote_count;
	private int cmt_count;
	private int used_file;
	
	public ViewBbsDTO() {
		
	}
	
	public ViewBbsDTO(int bbsID, String bbsTitle, String bbsContente, String nickName, String bbsDate,
			int upvote_count) {
		this.bbsID = bbsID;
		this.nickName = nickName;
		this.bbsDate = bbsDate;
		this.bbsTitle = bbsTitle;
		this.bbsContente = bbsContente;
		this.upvote_count = upvote_count;
	}
	
	public ViewBbsDTO(int bbsID, String nickName, String bbsDate, String bbsTitle, int bbsHit, int cmt_count,
			int used_file) {
		this.bbsID = bbsID;
		this.nickName = nickName;
		this.bbsDate = bbsDate;
		this.bbsTitle = bbsTitle;
		this.bbsHit = bbsHit;
		this.cmt_count = cmt_count;
		this.used_file = used_file;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBbsDate() {
		return bbsDate;
	}

	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}

	public String getBbsTitle() {
		return bbsTitle;
	}

	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}

	public int getBbsHit() {
		return bbsHit;
	}

	public void setBbsHit(int bbsHit) {
		this.bbsHit = bbsHit;
	}

	public int getCmt_count() {
		return cmt_count;
	}

	public void setCmt_count(int cmt_count) {
		this.cmt_count = cmt_count;
	}

	public int getUsed_file() {
		return used_file;
	}

	public void setUsed_file(int used_file) {
		this.used_file = used_file;
	}

	public String getBbsContente() {
		return bbsContente;
	}
	
	public void setBbsContente(String bbsContente) {
		this.bbsContente = bbsContente;
	}
	
	public int getUpvote_count() {
		return upvote_count;
	}
	
	public void setUpvote_count(int upvote_count) {
		this.upvote_count = upvote_count;
	}
	
	@Override
	public String toString() {
		return "ViewBbsDTO [bbsID=" + bbsID + ", nickName=" + nickName + ", bbsDate=" + bbsDate + ", bbsTitle="
				+ bbsTitle + ", bbsContente=" + bbsContente + ", bbsHit=" + bbsHit + ", upvote_count=" + upvote_count
				+ ", cmt_count=" + cmt_count + ", used_file=" + used_file + "]";
	}
}
