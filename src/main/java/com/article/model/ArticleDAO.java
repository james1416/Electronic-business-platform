package com.article.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ArticleDAO implements ArticleDAO_interface {
	//會員查全部文章
	private static final String GET_ALL_AT =
			"SELECT  AT_ID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,MEM_NAME,MEM_Pic FROM ARTICLE inner join MEMBER on ARTICLE.AT_MEMID =MEMBER.MEM_ID where AT_STA=1 order by AT_TIME desc";
	//會員新增文章
	private static final String INSERT_AT = 
			"INSERT INTO ARTICLE (AT_MEMID,AT_TITLE,AT_TIME,AT_TEXT) VALUES (?, ?, now(), ?)";
	//會員按讚
	private static final String INSERT_LK = 
			"UPDATE ARTICLE set AT_LK=AT_LK+1 where AT_ID=?";
	//會員更新文章
	private static final String USERUPDATE = 
			"UPDATE mydb.ARTICLE SET AT_TITLE =?, AT_TEXT=? WHERE AT_ID = ?";
	//查單一文章
	private static final String GET_ONE_AT = 
			"SELECT AT_ID,AT_MEMID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,MEM_NAME,MEM_Pic FROM ARTICLE inner join MEMBER on ARTICLE.AT_MEMID = MEMBER.MEM_ID where AT_STA=1 and AT_ID=?";
	//會員收回讚
	private static final String DELETE_LK = 
			"UPDATE ARTICLE set AT_LK=AT_LK-1 where AT_ID=?";
	//會員刪除文章
	private static final String DELETE_AT = 
			"UPDATE ARTICLE set AT_STA=0 where AT_ID=?";
	//管理員查全部文章
	private static final String ADMIN_GET_ALL_AT = 
			"SELECT AT_ID,AT_MEMID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,AT_STA FROM mydb.ARTICLE order by AT_TIME desc";
	//還原刪除文章
	private static final String RECOVERY_AT = 
			"UPDATE ARTICLE set AT_STA=1 where AT_ID=?";
	//我的文章
	private static final String GET_ALL_MYAT=
			"SELECT AT_ID,AT_MEMID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,AT_STA,MEM_Pic FROM ARTICLE inner join MEMBER on ARTICLE.AT_MEMID = MEMBER.MEM_ID where AT_STA=1 and AT_MEMID=? order by AT_TIME desc";
	//熱門文章
	private static final String GET_ALL_HOTAT=
			"SELECT  AT_ID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,MEM_NAME,MEM_Pic FROM ARTICLE inner join MEMBER on ARTICLE.AT_MEMID =MEMBER.MEM_ID where AT_STA=1 order by AT_LK desc";
	//首頁第一條熱門文章
	private static final String GET_ONE_HOT=
			"SELECT * FROM ARTICLE order by AT_LK desc limit 1";
	//首頁第一條最新文章
	private static final String GET_ONE_NEW=
			"SELECT * FROM ARTICLE order by AT_TIME desc limit 1";
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// 文章新增
	@Override
	public void insert(ArticleVO articleVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_AT);
			pstmt.setInt(1, articleVO.getMemid());
			pstmt.setString(2, articleVO.getTitle());
			pstmt.setString(3, articleVO.getText());

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
	// 文章修改
	@Override
	public void update(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(USERUPDATE);

			pstmt.setString(1, articleVO.getTitle());
			pstmt.setString(2, articleVO.getText());
			pstmt.setInt(3, articleVO.getAtid());
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
	// 查全部文章
	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new LinkedList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setAtid(rs.getInt("AT_ID"));
				articleVO.setTitle(rs.getString("AT_TITLE"));
				articleVO.setText(rs.getString("AT_TEXT"));
				articleVO.setPostime(rs.getString("AT_TIME"));
				articleVO.setLike(rs.getInt("AT_LK"));
				articleVO.setMemname(rs.getString("MEM_NAME"));
				articleVO.setMempic(rs.getBytes("MEM_Pic"));
				list.add(articleVO);
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
	// 文章移除
	@Override
	public void delete(Integer atid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_AT);
			pstmt.setInt(1, atid);
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
	// 標題模糊查詢
	@Override
	public List<ArticleVO> getSomeAt(String title) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;
		String sqlfina = "SELECT AT_ID,AT_TITLE,AT_TEXT,AT_TIME,AT_LK,MEM_NAME,MEM_Pic FROM ARTICLE inner join MEMBER on ARTICLE.AT_MEMID = MEMBER.MEM_ID where AT_STA =1 and AT_TITLE like'%"
				+ title + "%'" + " or " + "AT_TEXT like'%" + title + "%'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sqlfina);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setAtid(rs.getInt("AT_ID"));
				articleVO.setTitle(rs.getString("AT_TITLE"));
				articleVO.setText(rs.getString("AT_TEXT"));
				articleVO.setPostime(rs.getString("AT_TIME"));
				articleVO.setLike(rs.getInt("AT_LK"));
				articleVO.setMemname(rs.getString("MEM_NAME"));
				articleVO.setMempic(rs.getBytes("MEM_Pic"));
				list.add(articleVO);
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
	// 文章ID查詢
	@Override
	public ArticleVO getoneAt(Integer atid) {

		ArticleVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_AT);

			pstmt.setInt(1, atid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				artVO = new ArticleVO();
				artVO.setAtid(rs.getInt("AT_ID"));
				artVO.setMemid(rs.getInt("AT_MEMID"));
				artVO.setTitle(rs.getString("AT_TITLE"));
				artVO.setText(rs.getString("AT_TEXT"));
				artVO.setPostime(rs.getString("AT_TIME"));
				artVO.setLike(rs.getInt("AT_LK"));
				artVO.setMemname(rs.getString("MEM_NAME"));
				artVO.setMempic(rs.getBytes("MEM_Pic"));
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
		return artVO;
	}
	// 文章按讚
	@Override
	public void atLike(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_LK);
			pstmt.setInt(1, articleVO.getAtid());
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
	//管理員查全部文章(包含狀態)
	@Override
	public List<ArticleVO> admingetAll() {
		List<ArticleVO> list = new LinkedList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(ADMIN_GET_ALL_AT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setAtid(rs.getInt("AT_ID"));
				articleVO.setMemid(rs.getInt("AT_MEMID"));
				articleVO.setTitle(rs.getString("AT_TITLE"));
				articleVO.setText(rs.getString("AT_TEXT"));
				articleVO.setPostime(rs.getString("AT_TIME"));
				articleVO.setLike(rs.getInt("AT_LK"));
				articleVO.setStatus(rs.getInt("AT_STA"));
				list.add(articleVO);
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
	//管理員文章狀態修改
	@Override
	public void adminUpdate(Integer atid) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(RECOVERY_AT);
			pstmt.setInt(1, atid);
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
	//會員查詢自己的文章
	@Override
	public List<ArticleVO> getall(Integer memid) {
		List<ArticleVO> list = new LinkedList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_MYAT);
			pstmt.setInt(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setAtid(rs.getInt("AT_ID"));
				articleVO.setMemid(rs.getInt("AT_MEMID"));
				articleVO.setTitle(rs.getString("AT_TITLE"));
				articleVO.setText(rs.getString("AT_TEXT"));
				articleVO.setPostime(rs.getString("AT_TIME"));
				articleVO.setLike(rs.getInt("AT_LK"));
				articleVO.setStatus(rs.getInt("AT_STA"));
				articleVO.setMempic(rs.getBytes("MEM_Pic"));
				list.add(articleVO);
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
	//移除按讚
	public void deletelike(Integer atid) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_LK);
			pstmt.setInt(1, atid);
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
	//熱門文章
	@Override
	public List<ArticleVO> getHotAt() {
		List<ArticleVO> list = new LinkedList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_HOTAT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setAtid(rs.getInt("AT_ID"));
				articleVO.setTitle(rs.getString("AT_TITLE"));
				articleVO.setText(rs.getString("AT_TEXT"));
				articleVO.setPostime(rs.getString("AT_TIME"));
				articleVO.setLike(rs.getInt("AT_LK"));
				articleVO.setMemname(rs.getString("MEM_NAME"));
				articleVO.setMempic(rs.getBytes("MEM_Pic"));
				list.add(articleVO);
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
	//抓單一熱門文章
	public ArticleVO getoneHot() {
		ArticleVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_HOT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				artVO = new ArticleVO();
				artVO.setAtid(rs.getInt("AT_ID"));
				artVO.setMemid(rs.getInt("AT_MEMID"));
				artVO.setTitle(rs.getString("AT_TITLE"));
				artVO.setText(rs.getString("AT_TEXT"));
				artVO.setPostime(rs.getString("AT_TIME"));
				artVO.setLike(rs.getInt("AT_LK"));
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
		return artVO;
	}
	//抓單一最新文章
	@Override
	public ArticleVO getoneNew() {
		ArticleVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_NEW);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				artVO = new ArticleVO();
				artVO.setAtid(rs.getInt("AT_ID"));
				artVO.setMemid(rs.getInt("AT_MEMID"));
				artVO.setTitle(rs.getString("AT_TITLE"));
				artVO.setText(rs.getString("AT_TEXT"));
				artVO.setPostime(rs.getString("AT_TIME"));
				artVO.setLike(rs.getInt("AT_LK"));
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
		return artVO;
	}
}
	
	
