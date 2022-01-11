package com.hycujjang.devBoard.objectPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hycujjang.freeboard.objectPack.comment.CommentDTO;
import com.hycujjang.util.DatabaseUtil;

public class DevCommentDAO {
	
	public int delete(String commentID) {	
		String SQL = "DELETE FROM DEVSTORY_COMMENT WHERE commentID = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, commentID);
			// 쿼리 성공시 성공한 row를 반환 여기서에선 1
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public String getPassword(String commentID) {	
		String SQL = "SELECT password FROM DEVSTORY_COMMENT WHERE commentID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(commentID));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}		
		return result;
	}
	
	public int write(DevCommentDTO devCommentDTO) {
		// commentID(auto), boardID, writeID, password, comment, regDate, ip
		String SQL = "INSERT INTO DEVSTORY_COMMENT(boardID, writeID, password, comment, regDate, ip) VALUES(?, ?, ?, ?, NOW(), ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, devCommentDTO.getBoardID());
			pstmt.setString(2, devCommentDTO.getWriteID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, devCommentDTO.getPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, devCommentDTO.getComment().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, devCommentDTO.getIp());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<DevCommentDTO> getList(int bbsID) {		
		ArrayList<DevCommentDTO> list = new ArrayList<>();
		String SQL = "SELECT * FROM DEVSTORY_COMMENT WHERE boardID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			// commentID, boardID, writeID, password, comment, regDate, ip
			while (rs.next()) {
				DevCommentDTO comments = new DevCommentDTO(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
						);
				list.add(comments);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null;
	}
	
	private void instanseClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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
