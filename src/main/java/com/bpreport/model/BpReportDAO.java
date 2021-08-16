package com.bpreport.model;

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


public class BpReportDAO implements BpReportDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BP_REPORT (BPR_MEMID,BPR_BPID,BPR_CONTENT,BPR_DATE) VALUE(?,?,?,?);";
	private static final String GET_ALL_STMT = "SELECT *FROM BP_REPORT";
	private static final String GET_ONE_STMT = "SELECT *FROM BP_REPORT WHERE BPR_MEMID = ?";
	private static final String DELETE = "DELETE FROM BP_REPORT WHERE BPR_ID = ?"; // 應該不需要??
	private static final String UPDATE = ""; // 應該不需要
	private static final String CHANGESTA = "UPDATE BP_REPORT SET BPR_STA =? WHERE BPR_ID = ?";

	@Override
	public void insert(BpReportVO bprVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, bprVO.getBpr_memid());
			pstmt.setInt(2, bprVO.getBpr_bpid());
			pstmt.setString(3, bprVO.getBpr_content());
			pstmt.setTimestamp(4, bprVO.getBpr_date());

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
	public void updatesta(Integer bpr_sta, Integer bpr_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGESTA);
			pstmt.setInt(1, bpr_sta);
			pstmt.setInt(2, bpr_id);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void delete(Integer bpr_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, bpr_id);

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
	public BpReportVO findByPrimaryKey(Integer bpr_memid) {
		BpReportVO bprVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bprVO = new BpReportVO();
				bprVO.setBpr_id(rs.getInt("bpr_id"));
				bprVO.setBpr_memid(rs.getInt("bpr_memid"));
				bprVO.setBpr_bpid(rs.getInt("bpr_bpid"));
				bprVO.setBpr_content(rs.getString("bpr_content"));
				bprVO.setBpr_date(rs.getTimestamp("bpr_date"));
				bprVO.setBpr_sta(rs.getInt("bpr_sta"));
			}
		} catch (SQLException se) {
			// TODO: handle exception
		}
		return null;
	}

	// 拿到全部檢舉內容的方法
	@Override
	public List<BpReportVO> geAll() {
		List<BpReportVO> list = new ArrayList<>();
		BpReportVO bprVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bprVO = new BpReportVO();
				bprVO.setBpr_id(rs.getInt("bpr_id"));
				bprVO.setBpr_memid(rs.getInt("bpr_memid"));
				bprVO.setBpr_bpid(rs.getInt("bpr_bpid"));
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
	public void changesta(Integer bpr_id, Integer bpr_sta) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGESTA);
			pstmt.setInt(1, bpr_sta);
			pstmt.setInt(2, bpr_id);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
