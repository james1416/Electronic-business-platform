package com.bpbsreport.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BpBsReportDAO implements BpBsReportDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BPBS_REPORT (BBS_BPID,BBS_BMEMID,BBS_SMEMID,BBS_DATE,BBS_MS) VALUE(?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM BPBS_REPORT";
	private static final String GET_ONE_STMT_FOR_BUYER = "SELECT * FROM BPBS_REPORT WHERE BBS_BMEMID = ?";
	private static final String GET_ONE_STMT_FOR_SELLER = "SELECT * FROM BPBS_REPORT WHERE BBS_SMEMID = ?";
	private static final String DELETE = "DELETE FROM BPBS_REPORT WHERE BBS_ID =?";
	private static final String UPDATE = "";
	private static final String CHANGESTA = "UPDATE BPBS_REPORT SET BBS_STA = ? WHERE BBS_ID = ?";

	@Override
	public void insert(BpBsReportVO bbsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, bbsVO.getBbs_bpid());
			pstmt.setInt(2, bbsVO.getBbs_bmemid());
			pstmt.setInt(3, bbsVO.getBbs_smemid());
			pstmt.setTimestamp(4, bbsVO.getBbs_date());
			pstmt.setString(5, bbsVO.getBbs_ms());

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

	// 暫時不做太麻煩了
	@Override
	public void update(BpBsReportVO bbsVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer bbs_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, bbs_id);

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
	public List<BpBsReportVO> getAll() {
		List<BpBsReportVO> list = new ArrayList<>();
		BpBsReportVO bbsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bbsVO.setBbs_id(rs.getInt("bbs_id"));
				bbsVO.setBbs_bpid(rs.getInt("bbs_bpid"));
				bbsVO.setBbs_bmemid(rs.getInt("bbs_bmemid"));
				bbsVO.setBbs_smemid(rs.getInt("bbs_smemid"));
				bbsVO.setBbs_date(rs.getTimestamp("bbs_date"));
				bbsVO.setBbs_ms(rs.getString("bbs_ms"));
				bbsVO.setBbs_sta(rs.getInt("bbs_sta"));

				list.add(bbsVO);
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

	// 買家單一查詢
	@Override
	public BpBsReportVO findByPrimaryKey_forbmemid(Integer bbs_bmemid) {
		
		BpBsReportVO bbsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_FOR_BUYER);
			pstmt.setInt(1,bbs_bmemid);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bbsVO.setBbs_id(rs.getInt("bbs_id"));
				bbsVO.setBbs_bpid(rs.getInt("bbs_bpid"));
				bbsVO.setBbs_bmemid(rs.getInt("bbs_bmemid"));
				bbsVO.setBbs_smemid(rs.getInt("bbs_smemid"));
				bbsVO.setBbs_date(rs.getTimestamp("bbs_date"));
				bbsVO.setBbs_ms(rs.getString("bbs_ms"));
				bbsVO.setBbs_sta(rs.getInt("bbs_sta"));

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
		return bbsVO;
	}
	//賣家單一查詢
	@Override
	public BpBsReportVO findByPrimaryKey_forsmemid(Integer bbs_smemid) {
		BpBsReportVO bbsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_FOR_SELLER);
			pstmt.setInt(1, bbs_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bbsVO.setBbs_id(rs.getInt("bbs_id"));
				bbsVO.setBbs_bpid(rs.getInt("bbs_bpid"));
				bbsVO.setBbs_bmemid(rs.getInt("bbs_bmemid"));
				bbsVO.setBbs_smemid(rs.getInt("bbs_smemid"));
				bbsVO.setBbs_date(rs.getTimestamp("bbs_date"));
				bbsVO.setBbs_ms(rs.getString("bbs_ms"));
				bbsVO.setBbs_sta(rs.getInt("bbs_sta"));

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
		return bbsVO;
	}
	
	//管理員修改狀態
	@Override
	public void changeSta(Integer bbs_sta,Integer bbs_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGESTA);
			pstmt.setInt(1,bbs_sta);
			pstmt.setInt(2,bbs_id);
			pstmt.executeUpdate();

			// Handle any driver errors
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

}
