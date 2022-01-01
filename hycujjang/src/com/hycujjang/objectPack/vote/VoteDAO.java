package com.hycujjang.objectPack.vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hycujjang.util.DatabaseUtil;

public class VoteDAO {	
	public int deleteAll(String[] bbsID) {
		String param = "";
		for(int i = 0; i < bbsID.length; i++) {
			param += bbsID[i];
			if (i < bbsID.length - 1) {
				param += ",";
			}
		}
		
		String SQL = "DELETE FROM FREE_BBS_UPVOTE WHERE bbsID IN (" + param + ")";
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
		return -1; //�����ͺ��̽� ����
	}
	
	public int delete(String bbsID) {
		String SQL = "DELETE FROM FREE_BBS_UPVOTE WHERE bbsID = ?";
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
	}
	
	public int getUpvoteCount(VoteDTO voteDTO) {
		String SQL = "SELECT COUNT(*) FROM FREE_BBS_UPVOTE WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, voteDTO.getBbsID());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = rs.getInt(1);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return result;
	}
	
	public int upvote(VoteDTO voteDTO) {
		String SQL = "INSERT INTO FREE_BBS_UPVOTE VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, voteDTO.getUserID());
			pstmt.setString(2, voteDTO.getBbsID());
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
	
	public boolean upVoteCheck(VoteDTO voteDTO) {
		String SQL = "SELECT * FROM FREE_BBS_UPVOTE WHERE userID = ? AND bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, voteDTO.getUserID());
			pstmt.setString(2, voteDTO.getBbsID());
			rs = pstmt.executeQuery();
			// rs.next() ������������ ���� rs���� ���� ����� ������� true
			result = rs.next();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return result;
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
