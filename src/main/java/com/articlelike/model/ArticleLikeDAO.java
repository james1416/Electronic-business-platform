package com.articlelike.model;

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

import com.article.model.ArticleVO;
import com.articlecollection.model.ArticleCollectionVO;

public class ArticleLikeDAO implements ArticleLikeDAO_interface {
	private static final String GET_ALL_LIKE ="SELECT * FROM mydb.ARTICLELIKE;";
	private static final String INSERT_ATLIKE ="INSERT INTO ARTICLELIKE(LK_ATID,LK_MEMID,LK_TIME)VALUES( ?, ?,now())";
	private static final String DELETE_ATLIKE ="DELETE FROM ARTICLELIKE WHERE (LK_ATID = ?) and (LK_MEMID = ?)";
	private static final String GET_ALL_MYLK="SELECT * FROM ARTICLELIKE where LK_MEMID=?";
	private static final String GET_ONE="SELECT LK_ATID,LK_MEMID FROM ARTICLELIKE where LK_ATID=? and LK_MEMID=?";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ArticleLikeVO> getall() {
		
		List<ArticleLikeVO> list = new LinkedList<ArticleLikeVO>();
		ArticleLikeVO atLKVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LIKE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				atLKVO = new ArticleLikeVO();
				atLKVO.setAtid(rs.getInt("LK_ATID"));
				atLKVO.setMemid(rs.getInt("LK_MEMID"));
				atLKVO.setLktime(rs.getString("LK_TIME"));
				list.add(atLKVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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



	@Override
	public void insert(ArticleLikeVO articleLikeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ATLIKE);
			pstmt.setInt(1, articleLikeVO.getAtid());
			pstmt.setInt(2, articleLikeVO.getMemid());
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



	@Override
	public void delete(Integer atid, Integer memid) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ATLIKE);
			pstmt.setInt(1, atid);
			pstmt.setInt(2, memid);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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



	@Override
	public List<ArticleLikeVO> getAllmylike(Integer memid) {
		List<ArticleLikeVO> list = new LinkedList<ArticleLikeVO>();
		ArticleLikeVO atLKVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_MYLK);
			pstmt.setInt(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				atLKVO = new ArticleLikeVO();
				atLKVO.setAtid(rs.getInt("LK_ATID"));
				atLKVO.setMemid(rs.getInt("LK_MEMID"));
				atLKVO.setLktime(rs.getString("LK_TIME"));
				list.add(atLKVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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



	
	@Override
	public ArticleLikeVO getonelike(Integer memid, Integer atid) {
		ArticleLikeVO artlike = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, atid);
			pstmt.setInt(2, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				artlike = new ArticleLikeVO();
				artlike.setAtid(rs.getInt("LK_ATID"));
				artlike.setMemid(rs.getInt("LK_MEMID"));
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
		return artlike;
	}

}
