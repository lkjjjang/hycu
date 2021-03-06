package com.hycujjang.lectureboard.objectPack.evaluaion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hycujjang.lectureboard.controller.LectureBoardController;
import com.hycujjang.lectureboard.objectPack.likey.LikeyDTO;
import com.hycujjang.util.DatabaseUtil;

public class EvaluationDAO {
	public int update(EvaluationDTO evaluationDTO) {
		String SQL = "UPDATE LECTURE_EVALUATION SET lectureName = ?, professorName = ?, lectureYear = ?,"
				+ "semesterDivide = ?, lectureDivide = ?, evaluationTitle = ?, evaluationContent = ?,"
				+ "totalScore = ?, creditScore = ?, comfortableScore = ?, lectureScore = ? WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, evaluationDTO.getLectureName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, evaluationDTO.getProfessorName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(3, evaluationDTO.getLectureYear());
			pstmt.setString(4, evaluationDTO.getSemesterDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, evaluationDTO.getLectureDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, evaluationDTO.getEvaluationTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(7, evaluationDTO.getEvaluationContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\\\n", "<br>"));
			pstmt.setString(8, evaluationDTO.getTotalScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(9, evaluationDTO.getCreditScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(10, evaluationDTO.getComfortableScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(11, evaluationDTO.getLectureScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(12, evaluationDTO.getEvaluationID());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // ???????????? ????
	}
	
	
	public int write(EvaluationDTO evaluationDTO) {
		//evaluationID, userID, lectureName, professorName, lectureYear, semesterDivide, lectureDivide, evaluationTitle, evaluationContent, totalScore, creditScore, comfortableScore, lectureScore, likeCount
		String SQL = "INSERT INTO LECTURE_EVALUATION VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, evaluationDTO.getUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, evaluationDTO.getLectureName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, evaluationDTO.getProfessorName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5, evaluationDTO.getSemesterDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, evaluationDTO.getLectureDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(7, evaluationDTO.getEvaluationTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(8, evaluationDTO.getEvaluationContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\\\n", "<br>"));                      
			pstmt.setString(9, evaluationDTO.getTotalScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(10, evaluationDTO.getCreditScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(11, evaluationDTO.getComfortableScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(12, evaluationDTO.getLectureScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // ???????????? ????
	}
	
	public ArrayList<EvaluationDTO> getList(String lectureDivide, String searchType, String search, int pageNumber) {
		if (lectureDivide.equals("????")) {
			lectureDivide = "";
		}
		
		ArrayList<EvaluationDTO> list = new ArrayList<>();
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int minColNum = (pageNumber - 1) * LectureBoardController.lectureListPrintCount;
		int colCount = LectureBoardController.lectureListPrintCount;
		
		try {
			if (searchType.equals("??????") || searchType.equals("")) {
				SQL = "SELECT * FROM LECTURE_EVALUATION WHERE lectureDivide "
						+ "LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) "
						+ "LIKE ? ORDER BY evaluationID DESC LIMIT "
						+ minColNum + ", " + colCount;
			} else if (searchType.equals("??????")) {
				SQL = "SELECT * FROM LECTURE_EVALUATION WHERE lectureDivide "
						+ "LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) "
						+ "LIKE ? ORDER BY likeCount DESC LIMIT "
						+ minColNum + ", " + colCount;
			}
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + lectureDivide + "%");
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				EvaluationDTO evaluation = new EvaluationDTO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getInt(14)
						);
				list.add(evaluation);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public int getListCount(String lectureDivide, String search) {
		if (lectureDivide.equals("????")) {
			lectureDivide = "";
		}
		
		String SQL = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			SQL = "SELECT COUNT(*) FROM LECTURE_EVALUATION WHERE lectureDivide "
					+ "LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) "
					+ "LIKE ? ORDER BY evaluationID";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + lectureDivide + "%");
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //???????????? ????
	}
	
	public int like(LikeyDTO likeyDTO) {
		String SQL = "UPDATE LECTURE_EVALUATION SET likeCount = likeCount + 1 WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, likeyDTO.getEvaluationID());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //???????????? ????
	}
	
	public int delete(String evaluationID) {
		String SQL = "DELETE FROM LECTURE_EVALUATION WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //???????????? ????
	}
	
	public String getUserID(String evaluationID) {
		String SQL = "SELECT userID FROM LECTURE_EVALUATION WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null; // ???????? ???? ??????
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
