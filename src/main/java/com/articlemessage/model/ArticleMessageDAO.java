package com.articlemessage.model;

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


public class ArticleMessageDAO implements ArticleMessageDAO_interface {
	//會員全部留言(不包含隱藏留言)
	private static final String GET_ALL_ATMS = 
			"SELECT MSG_ID,MSG_ATID,MSG_MEMID,MSG_TEXT,MSG_TIME,MEM_NAME,MEM_Pic FROM ARTICLEMESSAGE inner join MEMBER on ARTICLEMESSAGE.MSG_MEMID = MEMBER.MEM_ID where MSG_STA=1 order by MSG_TIME";
	//會員單一留言資訊
	private static final String GET_ONE_ATMS =
			"SELECT MSG_ID,MSG_ATID,MSG_MEMID,MSG_TEXT,MSG_TIME,MEM_NAME FROM ARTICLEMESSAGE inner join MEMBER on ARTICLEMESSAGE.MSG_MEMID = MEMBER.MEM_ID where MSG_ID=? and MSG_STA =1";
	//會員新增留言
	private static final String INSERT_ATMS="INSERT INTO ARTICLEMESSAGE(MSG_ATID,MSG_MEMID,MSG_TIME,MSG_TEXT)values( ?, ?,now() ,?)";
	//會員刪除留言
	private static final String DELETE_MSG="update ARTICLEMESSAGE set MSG_STA=0 where MSG_ID=?";
	
	private static final String UPDATE_MSG=
			"update ARTICLEMESSAGE set MSG_TEXT=? where MSG_ID=? and MSG_MEMID=?";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//會員新增留言
	@Override
	public void insertAtmessage(ArticleMessageVO articleMessageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ATMS);
			pstmt.setInt(1, articleMessageVO.getAtid());
			pstmt.setInt(2, articleMessageVO.getMemid());
			pstmt.setString(3, articleMessageVO.getMsgtext());
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
	public void deleteAtmessage(ArticleMessageVO articleMessageVO) {
		// TODO Auto-generated method stub
		
	}
	//會員全部留言(不包含隱藏留言)
	@Override
	public List<ArticleMessageVO> getAll() {
		
		List<ArticleMessageVO> list = new LinkedList<ArticleMessageVO>();
		ArticleMessageVO articleMessageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ATMS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleMessageVO = new ArticleMessageVO();
				articleMessageVO.setMsgid(rs.getInt("MSG_ID"));
				articleMessageVO.setAtid(rs.getInt("MSG_ATID"));
				articleMessageVO.setMemid(rs.getInt("MSG_MEMID"));
				articleMessageVO.setMsgtext(rs.getString("MSG_TEXT"));
				articleMessageVO.setMsgtime(rs.getString("MSG_TIME"));
				articleMessageVO.setMemname(rs.getString("MEM_NAME"));
				articleMessageVO.setMempic(rs.getBytes("MEM_Pic"));
				list.add(articleMessageVO);
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

	//會員單一留言資訊
	@Override
	public ArticleMessageVO findByPrimaryKey(Integer msgid) {
		ArticleMessageVO articleMessageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ATMS);
			pstmt.setInt(1, msgid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				articleMessageVO = new ArticleMessageVO();
				articleMessageVO.setMsgid(rs.getInt("MSG_ID"));
				articleMessageVO.setAtid(rs.getInt("MSG_ATID"));
				articleMessageVO.setMemid(rs.getInt("MSG_MEMID"));
				articleMessageVO.setMsgtext(rs.getString("MSG_TEXT"));
				articleMessageVO.setMsgtime(rs.getString("MSG_TIME"));
				articleMessageVO.setMemname(rs.getString("MEM_NAME"));
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
		return articleMessageVO;
	}

	//刪除留言
	@Override
	public void delete(Integer msgid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_MSG);
			pstmt.setInt(1, msgid);
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

	//修改留言
	@Override
	public void update(ArticleMessageVO articleMessageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MSG);
			pstmt.setString(1, articleMessageVO.getMsgtext());
			pstmt.setInt(2, articleMessageVO.getMsgid());
			pstmt.setInt(3, articleMessageVO.getMemid());
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
