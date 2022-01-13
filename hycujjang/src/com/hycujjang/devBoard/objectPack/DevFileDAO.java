package com.hycujjang.devBoard.objectPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hycujjang.freeboard.objectPack.file.FileDTO;
import com.hycujjang.util.DatabaseUtil;

public class DevFileDAO {
	public int delete(String bbsID) {
		String SQL = "DELETE FROM FREE_BBS_FILE WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			// executeUpdate 실행성공시 업데이트한 갯수를 반환
			// 이상황에선 1개만 추가 하기 때문에 1을 반환
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; // 추천중복오류
	}
	
	public int deleteAll(String boardID) {
		String SQL = "DELETE FROM DEVSTORY_FILE WHERE boardID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardID);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int updateFileName(DevFileDTO devFileDTO) {
		String SQL = "UPDATE DEVSTORY_FILE SET boardID = ?, fileLastName = ?, regDate = NOW() WHERE fileID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, devFileDTO.getBoardID());
			pstmt.setString(2, devFileDTO.getFileLastName());
			pstmt.setInt(3, devFileDTO.getFileID());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int uploadTempFileName(DevFileDTO devFileDTO) {
		String SQL = "INSERT INTO DEVSTORY_FILE (userID, fileRealName, fileTempName) VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, devFileDTO.getUserID());
			pstmt.setString(2, devFileDTO.getFileRealName());
			pstmt.setString(3, devFileDTO.getFileTempName());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
	}
	
	public int getFileID(String userID, String fileTempName) {
		String SQL = "SELECT fileID FROM DEVSTORY_FILE WHERE userID = ? AND fileTempName = ? ORDER BY fileID DESC LIMIT 1";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, fileTempName);
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
		
		return -1; // 데이터베이스 오류
	}
	public int upload(FileDTO fileDTO) {
		String SQL = "INSERT INTO (bbsID, userID, fileRealName, fileNewName) VALUES (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, fileDTO.getBbsID());
			pstmt.setString(2, fileDTO.getUserID());
			pstmt.setString(3, fileDTO.getFileRealName());
			pstmt.setString(4, fileDTO.getFileTempName());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
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
