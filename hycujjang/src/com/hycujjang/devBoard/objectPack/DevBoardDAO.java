package com.hycujjang.devBoard.objectPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hycujjang.freeboard.controller.FreeBoardListController;
import com.hycujjang.freeboard.objectPack.freeBBS.BbsDTO;
import com.hycujjang.freeboard.objectPack.freeBBS.ViewBbsDTO;
import com.hycujjang.util.DatabaseUtil;

public class DevBoardDAO {
	
	public int getBoardID(DevBoardDTO devBoardDTO) {	
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {		
			// boardID, boardTitle, boardPassword, boardContent, boardRegDate
			SQL = "SELECT boardID FROM DEVSTORY_BOARD WHERE boardTitle = ? AND boardPassword = ? ORDER BY boardID DESC LIMIT 1";
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, devBoardDTO.getBoardTitle());
			pstmt.setString(2, devBoardDTO.getBoardPassword());
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
	
	public String getPassword(String boardID) {
		String SQL = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			SQL = "SELECT boardPassword FROM DEVSTORY_BOARD WHERE boardID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardID);
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
	
	public int contentUpdate(DevBoardDTO devBoardDTO) {
		String SQL = "UPDATE DEVSTORY_BOARD SET boardTitle = ?, boardContent = ? WHERE boardID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, devBoardDTO.getBoardTitle());
			pstmt.setString(2, devBoardDTO.getBoardContent());
			pstmt.setInt(3, devBoardDTO.getBoardID());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int write(DevBoardDTO devBoardDTO) {
		//boardID(auto), boardTitle, boardPassword, boardContent, boardRegDate
		String SQL = "INSERT INTO DEVSTORY_BOARD VALUES (NULL, ?, ?, ?, NOW())";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, devBoardDTO.getBoardTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, devBoardDTO.getBoardPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, devBoardDTO.getBoardContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<ViewDevBoardDTO> getList(int pageNumber) {		
		ArrayList<ViewDevBoardDTO> list = new ArrayList<>();
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int minColNum = (pageNumber - 1) * FreeBoardListController.freeBBSListPrintCount;
		int colCount = FreeBoardListController.freeBBSListPrintCount;
		try {
			//boardID, boardTitle, boardPassword, boardRegDate, cmt_count, use_file
			SQL = "SELECT * FROM VIEW_DEVSTORY_BOARD_LIST ORDER BY boardID DESC LIMIT " + minColNum + ", " + colCount;
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ViewDevBoardDTO bbs = new ViewDevBoardDTO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)
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
	
	public DevBoardDTO getDevBoardDetail(int boardID) {		
		DevBoardDTO result = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {	
			//boardID, boardTitle, boardPassword, boardContent, boardRegDate
			SQL = "SELECT * FROM DEVSTORY_BOARD WHERE boardID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = new DevBoardDTO(
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
	
	public int delete(String[] boardIDs) {
		String param = "";
		for(int i = 0; i < boardIDs.length; i++) {
			param += boardIDs[i];
			if (i < boardIDs.length - 1) {
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
	
	public int delete(String boardID) {
		String SQL = "DELETE FROM DEVSTORY_BOARD WHERE boardID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(boardID));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //데이터베이스 오류
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
