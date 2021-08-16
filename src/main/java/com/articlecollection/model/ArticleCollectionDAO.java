package com.articlecollection.model;

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



public class ArticleCollectionDAO implements ArticleCollection_interface {
	private static final String GET_ALL_MYCOL = "SELECT * FROM mydb.ARTICLECOLLECTION where COL_MEMID=?";
	private static final String DELETE_MYCOL = "DELETE FROM mydb.ARTICLECOLLECTION WHERE COL_ATID =? and COL_MEMID =?";
	private static final String INSERT_COL="INSERT INTO ARTICLECOLLECTION(COL_ATID,COL_MEMID,COL_TIME)VALUES(?, ?, now())";
	private static final String GET_ALL="SELECT * FROM ARTICLECOLLECTION";
	private static final String GET_ONE="SELECT COL_ATID,COL_MEMID FROM ARTICLECOLLECTION where COL_ATID=? and COL_MEMID=?";
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
	public void insert(ArticleCollectionVO articleCollectionVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_COL);
			pstmt.setInt(1, articleCollectionVO.getAtid());
			pstmt.setInt(2, articleCollectionVO.getMemid());
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
		
	//會員查看自己收藏的文章
	@Override
	public List<ArticleCollectionVO> getAll(Integer memid) {
		List<ArticleCollectionVO> list = new LinkedList<ArticleCollectionVO>();
		ArticleCollectionVO articleCollectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_MYCOL);
			pstmt.setInt(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleCollectionVO = new ArticleCollectionVO();
				articleCollectionVO.setAtid(rs.getInt("COL_ATID"));
				articleCollectionVO.setMemid(rs.getInt("COL_MEMID"));
				articleCollectionVO.setTime(rs.getString("COL_TIME"));
				list.add(articleCollectionVO);
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

	@Override
	public ArticleCollectionVO findMemByPK(Integer memid, Integer atid) {
		ArticleCollectionVO artColVO = null;
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
				artColVO = new ArticleCollectionVO();
				artColVO.setAtid(rs.getInt("COL_ATID"));
				artColVO.setMemid(rs.getInt("COL_MEMID"));
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
		return artColVO;
	}
	
	//會員移除收藏的文章
	@Override
	public void delete(Integer atid, Integer memid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_MYCOL);
			pstmt.setInt(1, atid);
			pstmt.setInt(2, memid);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<ArticleCollectionVO> getAll() {
		List<ArticleCollectionVO> list = new LinkedList<ArticleCollectionVO>();
		ArticleCollectionVO articleCollectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleCollectionVO = new ArticleCollectionVO();
				articleCollectionVO.setAtid(rs.getInt("COL_ATID"));
				articleCollectionVO.setMemid(rs.getInt("COL_MEMID"));
				list.add(articleCollectionVO);
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
}
