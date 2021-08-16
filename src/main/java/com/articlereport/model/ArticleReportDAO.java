package com.articlereport.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ArticleReportDAO implements ArticleReportDAO_interface{
	private static final String GET_ALL_ATREP =
			"SELECT REP_ID,REP_ATID,REP_TEXT,REP_TIME,REP_STA,AT_TITLE,AT_TEXT,MEM_NAME,AT_STA FROM ARTICLEREPORT inner join ARTICLE on ARTICLEREPORT.REP_ATID = ARTICLE.AT_ID inner join MEMBER on ARTICLE.AT_MEMID = MEM_ID where REP_MEMID=?";
	private static final String INSERT_ATREP="INSERT INTO ARTICLEREPORT(REP_MEMID,REP_ATID,REP_TIME,REP_TEXT)VALUES(?,?,now(),?)";
	private static final String GET_ALL=
			"SELECT REP_ID,REP_ATID,REP_MEMID,REP_TEXT,REP_TIME,REP_STA,AT_TITLE,AT_TEXT,MEM_NAME FROM ARTICLEREPORT inner join ARTICLE on ARTICLEREPORT.REP_ATID = ARTICLE.AT_ID inner join MEMBER on ARTICLE.AT_MEMID = MEMBER.MEM_ID;";
	private static final String REPORT_PASS=
			"UPDATE ARTICLEREPORT set REP_STA=1 where REP_ID=?";
	private static final String REPORT_REJECT=
			"UPDATE ARTICLEREPORT set REP_STA=2 where REP_ID=?";
	private static DataSource ds = null;
	
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//會員文章檢舉總覽
	@Override
	public List<ArticleReportVO> getAll(Integer memid) {

		List<ArticleReportVO> list = new LinkedList<ArticleReportVO>();
		ArticleReportVO articleReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ATREP);
			pstmt.setInt(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleReportVO = new ArticleReportVO();
				articleReportVO.setRepid(rs.getInt("REP_ID"));
				articleReportVO.setAtid(rs.getInt("REP_ATID"));
				articleReportVO.setText(rs.getString("REP_TEXT"));
				articleReportVO.setReptime(rs.getString("REP_TIME"));
				articleReportVO.setStatus(rs.getInt("REP_STA"));
				articleReportVO.setAttitle(rs.getString("AT_TITLE"));
				articleReportVO.setAttext(rs.getString("AT_TEXT"));
				articleReportVO.setMemname(rs.getString("MEM_NAME"));
				articleReportVO.setAtsta(rs.getInt("AT_STA"));
				list.add(articleReportVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	//會員檢舉文章
	@Override
	public void insert(ArticleReportVO articleReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ATREP);
			pstmt.setInt(1, articleReportVO.getMemid());
			pstmt.setInt(2, articleReportVO.getAtid());
			pstmt.setString(3, articleReportVO.getText());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	//管理員獲取檢舉案件
	@Override
	public List<ArticleReportVO> getAll() {
		List<ArticleReportVO> list = new LinkedList<ArticleReportVO>();
		ArticleReportVO articleReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleReportVO = new ArticleReportVO();
				articleReportVO.setRepid(rs.getInt("REP_ID"));
				articleReportVO.setAtid(rs.getInt("REP_ATID"));
				articleReportVO.setMemid(rs.getInt("REP_MEMID"));
				articleReportVO.setText(rs.getString("REP_TEXT"));
				articleReportVO.setReptime(rs.getString("REP_TIME"));
				articleReportVO.setStatus(rs.getInt("REP_STA"));
				articleReportVO.setAttitle(rs.getString("AT_TITLE"));
				articleReportVO.setAttext(rs.getString("AT_TEXT"));
				articleReportVO.setMemname(rs.getString("MEM_NAME"));
				list.add(articleReportVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	//管理員審核通過
	@Override
	public void reportPass(Integer repid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(REPORT_PASS);
			pstmt.setInt(1, repid);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	//管理員審核駁回
	@Override
	public void reportReturn(Integer repid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(REPORT_REJECT);
			pstmt.setInt(1, repid);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}
