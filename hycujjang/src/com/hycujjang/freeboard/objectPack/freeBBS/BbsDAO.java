package com.hycujjang.freeboard.objectPack.freeBBS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hycujjang.freeboard.controller.FreeBoardListController;
import com.hycujjang.util.DatabaseUtil;

public class BbsDAO {
	public BbsDTO getFreeBoardModifyDetail(int bbsID) {		
		BbsDTO result = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {		
			SQL = "SELECT bbsID, nickName, password, bbsTitle, bbsContente FROM FREE_BBS WHERE bbsID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = new BbsDTO(
						rs.getInt(1),
						rs.getString(2).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(3).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(4).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(5).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n")
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public ViewBbsDTO getFreeBoardDetail(int bbsID) {		
		ViewBbsDTO result = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {		
			SQL = "SELECT * FROM VIEW_FREE_BBS_DETAIL WHERE bbsID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = new ViewBbsDTO(
						rs.getInt(1),
						rs.getString(2).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(3).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(4).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(5).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getInt(6)
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public int hitUpdate(int bbsID) {
		String SQL = "UPDATE FREE_BBS SET bbsHit = bbsHit + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int contentUpdate(BbsDTO bbsDTO) {
		String SQL = "UPDATE FREE_BBS SET bbsTitle = ?, password = ?, bbsContente = ? WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsDTO.getBbsTitle());
			pstmt.setString(2, bbsDTO.getPassword());
			pstmt.setString(3, bbsDTO.getBbsContente());
			pstmt.setInt(4, bbsDTO.getBbsID());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int write(BbsDTO bbsDTO) {
		String SQL = "INSERT INTO FREE_BBS VALUES (NULL, ?, ?, NOW(), ?, ?, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsDTO.getNickName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, bbsDTO.getPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, bbsDTO.getBbsTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, bbsDTO.getBbsContente().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int getBbsID(BbsDTO bbsDTO) {	
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {		
			SQL = "SELECT bbsID FROM FREE_BBS WHERE nickName = ? AND password = ? AND bbsTitle = ? ORDER BY bbsID DESC LIMIT 1";
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsDTO.getNickName());
			pstmt.setString(2, bbsDTO.getPassword());
			pstmt.setString(3, bbsDTO.getBbsTitle());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1;
	}
	
	public ArrayList<ViewBbsDTO> getList(int pageNumber) {		
		ArrayList<ViewBbsDTO> list = new ArrayList<>();
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int minColNum = (pageNumber - 1) * FreeBoardListController.freeBBSListPrintCount;
		int colCount = FreeBoardListController.freeBBSListPrintCount;
		try {		
			SQL = "SELECT bbsID, nickName, bbsDate, bbsTitle, bbsHit, cmt_count, used_file "
					+ "FROM VIEW_FREE_BBS_LIST ORDER BY bbsID DESC LIMIT " + minColNum + ", " + colCount;
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ViewBbsDTO bbs = new ViewBbsDTO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getInt(7)
						);
				list.add(bbs);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public String getContentPassword(String bbsID) {
		String SQL = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			SQL = "SELECT password FROM FREE_BBS WHERE bbsID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return result; //-1일 경우 데이터베이스 오류
	}
	
	public int delete(String bbsID) {
		String SQL = "DELETE FROM FREE_BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(bbsID));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //데이터베이스 오류
	}
	
	public int deleteAll(String[] bbsID) {
		String param = "";
		for(int i = 0; i < bbsID.length; i++) {
			param += bbsID[i];
			if (i < bbsID.length - 1) {
				param += ",";
			}
		}
		
		String SQL = "DELETE FROM FREE_BBS WHERE bbsID IN (" + param + ")";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //데이터베이스 오류
	}
	
	public String getUserID(String bbsID) {
		String SQL = "SELECT userID FROM FREE_BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(bbsID));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null; // 존재하지 않는 아이디
	}
	
	private static void instanseClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
