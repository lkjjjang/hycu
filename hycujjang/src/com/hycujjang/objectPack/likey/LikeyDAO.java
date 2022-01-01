package com.hycujjang.objectPack.likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hycujjang.util.DatabaseUtil;

public class LikeyDAO {
	public boolean likeCheck(String userID, String evaluationID) {
		String SQL = "SELECT * FROM LECTURE_LIKEY WHERE userID = ? AND evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, evaluationID);			
			rs = pstmt.executeQuery();
		
			return rs.next() ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}		
		return false;
	}
	
	public int like(String userID, String evaluationID) {
		String SQL = "INSERT INTO LECTURE_LIKEY VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, evaluationID);
			// executeUpdate ���༺���� ������Ʈ�� ������ ��ȯ
			// �̻�Ȳ���� 1���� �߰� �ϱ� ������ 1�� ��ȯ
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; // ��õ�ߺ�����
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
