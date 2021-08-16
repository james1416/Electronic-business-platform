package com.messagereport.model;

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

public class MessageReportDAO implements MessageReportDAO_interface {
	private static final String GET_ALL_MSGREP=
			"SELECT MSR_ID,MSR_MSGID,MSR_MEMID,MSR_TEXT,MSR_TIME,MSR_STA,MSG_TEXT,MEM_NAME FROM MESSAGEREPORT inner join ARTICLEMESSAGE on MESSAGEREPORT.MSR_MSGID = ARTICLEMESSAGE.MSG_ID inner join MEMBER on ARTICLEMESSAGE.MSG_MEMID = MEM_ID where MSR_MEMID=?";
	private static final String INSERT_MSGREP="INSERT INTO MESSAGEREPORT(MSR_MSGID,MSR_MEMID,MSR_TIME,MSR_TEXT)VALUES(?,?,now(),?)";
	private static final String REPORT_PASS="UPDATE MESSAGEREPORT set MSR_STA=1 where MSR_ID=?";
	private static final String REPORT_REJECT="UPDATE MESSAGEREPORT set MSR_STA=2 where MSR_ID=?";
	private static final String GET_ALL=
			"SELECT MSR_ID,MSR_MSGID,MSR_MEMID,MSR_TEXT,MSR_TIME,MSR_STA,MEM_NAME,MSG_TEXT FROM MESSAGEREPORT inner join ARTICLEMESSAGE on MESSAGEREPORT.MSR_MSGID = ARTICLEMESSAGE.MSG_ID inner join MEMBER on ARTICLEMESSAGE.MSG_MEMID = MEM_ID";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//文章留言檢舉查全部
	@Override
	public List<MessageReportVO> getAll(Integer memid) {
		List<MessageReportVO>list = new LinkedList<MessageReportVO>();
		MessageReportVO messageReportVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_MSGREP);
			pstmt.setInt(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				messageReportVO = new MessageReportVO();
				messageReportVO.setMsrid(rs.getInt("MSR_ID"));
				messageReportVO.setMsgid(rs.getInt("MSR_MSGID"));
				messageReportVO.setMemid(rs.getInt("MSR_MEMID"));
				messageReportVO.setMsrtext(rs.getString("MSR_TEXT"));
				messageReportVO.setMsrtime(rs.getString("MSR_TIME"));
				messageReportVO.setMsrsta(rs.getInt("MSR_STA"));
				messageReportVO.setMemname(rs.getString("MEM_NAME"));
				messageReportVO.setMsgtext(rs.getString("MSG_TEXT"));
				list.add(messageReportVO);
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
	//文章留言檢舉新增
	@Override
	public void insert(MessageReportVO messageReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_MSGREP);
			pstmt.setInt(1, messageReportVO.getMsgid());
			pstmt.setInt(2, messageReportVO.getMemid());
			pstmt.setString(3, messageReportVO.getMsrtext());
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
	//管理員檢舉駁回
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
	//管理員檢舉通過
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
	//獲取全部留言檢舉
	@Override
	public List<MessageReportVO> admingetAll() {
		List<MessageReportVO>list = new LinkedList<MessageReportVO>();
		MessageReportVO messageReportVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				messageReportVO = new MessageReportVO();
				messageReportVO.setMsrid(rs.getInt("MSR_ID"));
				messageReportVO.setMsgid(rs.getInt("MSR_MSGID"));
				messageReportVO.setMemid(rs.getInt("MSR_MEMID"));
				messageReportVO.setMsrtext(rs.getString("MSR_TEXT"));
				messageReportVO.setMsrtime(rs.getString("MSR_TIME"));
				messageReportVO.setMsrsta(rs.getInt("MSR_STA"));
				messageReportVO.setMemname(rs.getString("MEM_NAME"));
				messageReportVO.setMsgtext(rs.getString("MSG_TEXT"));
				list.add(messageReportVO);
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
