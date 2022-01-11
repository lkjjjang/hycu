package com.hycujjang.devBoard.objectPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hycujjang.util.DatabaseUtil;

public class DevReplyDAO {
	
	public String getPassword(String replyID) {	
		String SQL = "SELECT password FROM DEVSTORY_REPLY WHERE replyID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(replyID));
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
	
	public int delete(String replyID) {	
		String SQL = "DELETE FROM DEVSTORY_REPLY WHERE replyID = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, replyID);
			// 쿼리 성공시 성공한 row를 반환 여기서에선 1
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int write(DevReplyDTO devReplyDTO) {
		// replyID(auto), commentID, boardID, nickName, password, replyComment, regDate, ip
		String SQL = "INSERT INTO DEVSTORY_REPLY(commentID, boardID, nickName, password, replyComment, regDate, ip)"
				+ " VALUES(?, ?, ?, ?, ?, NOW(), ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, devReplyDTO.getCommentID());
			pstmt.setInt(2, devReplyDTO.getBoardID());
			pstmt.setString(3, devReplyDTO.getNickName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, devReplyDTO.getPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, devReplyDTO.getReplyComment().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, devReplyDTO.getIp());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<DevReplyDTO> getList(int bbsID) {		
		ArrayList<DevReplyDTO> list = new ArrayList<>();
		String SQL = "SELECT * FROM DEVSTORY_REPLY WHERE boardID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			//replyID, commentID, boardID, nickName, password, replyComment, regDate, ip
			while (rs.next()) {
				DevReplyDTO comments = new DevReplyDTO(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8)
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
