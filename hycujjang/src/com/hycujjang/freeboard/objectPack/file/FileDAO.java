package com.hycujjang.freeboard.objectPack.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hycujjang.util.DatabaseUtil;

public class FileDAO {
	/*
	public int delete(String bbsID) {
		String SQL = "DELETE FROM FREE_BBS_FILE WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			// executeUpdate ���༺���� ������Ʈ�� ������ ��ȯ
			// �̻�Ȳ���� 1���� �߰� �ϱ� ������ 1�� ��ȯ
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; // ��õ�ߺ�����
	}*/
	
	public int updateFileName(FileDTO fileDTO) {
		String SQL = "UPDATE FREE_BBS_FILE SET bbsID = ?, fileLastName = ?, regDate = NOW() WHERE fileID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, fileDTO.getBbsID());
			pstmt.setString(2, fileDTO.getFileLastName());
			pstmt.setInt(3, fileDTO.getFileID());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // �����ͺ��̽� ����
	}
	
	public int uploadTempFileName(FileDTO fileDTO) {
		String SQL = "INSERT INTO FREE_BBS_FILE (userID, fileRealName, fileTempName) VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileDTO.getUserID());
			pstmt.setString(2, fileDTO.getFileRealName());
			pstmt.setString(3, fileDTO.getFileTempName());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // �����ͺ��̽� ����
	}
	
	public int getFileID(String userID, String fileTempName) {
		String SQL = "SELECT fileID FROM FREE_BBS_FILE WHERE userID = ? AND fileTempName = ? ORDER BY fileID DESC LIMIT 1";
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
		
		return -1; // �����ͺ��̽� ����
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
		
		return -1; // �����ͺ��̽� ����
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
