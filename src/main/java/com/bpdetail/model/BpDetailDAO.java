package com.bpdetail.model;

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

public class BpDetailDAO implements BpDetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO BP_DETAIL(BPD_BPID,BPD_BMEMID,BPD_BPPRICE,BPD_BPDATE) VALUES(?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM BP_DETAIL";
	private static final String GET_ONE_STMT_BPID = "SELECT * FROM BP_DETAIL WHERE BPD_BPID = ? ORDER BY BPD_ID DESC";
	private static final String GET_ONE_STMT_BMEMID = "SELECT * FROM BP_DETAIL WHERE BPD_BMEMID = ? ORDER BY BPD_ID DESC LIMIT ?,?";
	private static final String GET_TOP_PRICE = "SELECT * FROM BP_DETAIL where bpd_bpid = ? order by BPD_BPPRICE DESC limit 0 , 1";
	@Override
	public void insert(BpDetailVO bpdVO,Connection con) {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, bpdVO.getBpd_bpid());
			pstmt.setInt(2, bpdVO.getBpd_bmemid());
			pstmt.setInt(3, bpdVO.getBpd_bpprice());
			pstmt.setTimestamp(4, bpdVO.getBpd_bpdate());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				
			}
		}
	}

	@Override
	public void update(BpDetailVO bpdVO) {
		// 暫時用不到

	}

	@Override
	public void delete(Integer bpd_id) {
		// 暫時用不到

	}

	@Override
	public List<BpDetailVO> findByPrimaryKeyfromBmember(Integer bpd_bmemid,Integer start, Integer rows) {
		List<BpDetailVO> list = new ArrayList<BpDetailVO>();
		BpDetailVO bpdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BMEMID);
			pstmt.setInt(1, bpd_bmemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bpdVO = new BpDetailVO();
				bpdVO.setBpd_id(rs.getInt("BPD_ID"));
				bpdVO.setBpd_bpid(rs.getInt("BPD_BPID"));
				bpdVO.setBpd_bmemid(rs.getInt("BPD_BMEMID"));
				bpdVO.setBpd_bpprice(rs.getInt("BPD_BPPRICE"));
				bpdVO.setBpd_bpdate(rs.getTimestamp("BPD_BPDATE"));
				list.add(bpdVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public List<BpDetailVO> findByPrimaryKeyfromBPID(Integer bpd_bpid) {
		List<BpDetailVO> list = new ArrayList<BpDetailVO>();
		BpDetailVO bpdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BPID);
			pstmt.setInt(1, bpd_bpid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bpdVO = new BpDetailVO();
				bpdVO.setBpd_id(rs.getInt("BPD_ID"));
				bpdVO.setBpd_bpid(rs.getInt("BPD_BPID"));
				bpdVO.setBpd_bmemid(rs.getInt("BPD_BMEMID"));
				bpdVO.setBpd_bpprice(rs.getInt("BPD_BPPRICE"));
				bpdVO.setBpd_bpdate(rs.getTimestamp("BPD_BPDATE"));
				list.add(bpdVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<BpDetailVO> getAll() {
		List<BpDetailVO> list = new ArrayList<BpDetailVO>();
		BpDetailVO bpdVO = new BpDetailVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bpdVO = new BpDetailVO();
				bpdVO.setBpd_id(rs.getInt("BPD_ID"));
				bpdVO.setBpd_bpid(rs.getInt("BPD_BPID"));
				bpdVO.setBpd_bmemid(rs.getInt("BPD_BMEMID"));
				bpdVO.setBpd_bpprice(rs.getInt("BPD_BPPRICE"));
				bpdVO.setBpd_bpdate(rs.getTimestamp("BPD_BPDATE"));
				list.add(bpdVO);

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public BpDetailVO getTopPrice(Integer bpd_bpid) {
		BpDetailVO bpdVO = new BpDetailVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TOP_PRICE);
			pstmt.setInt(1, bpd_bpid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bpdVO = new BpDetailVO();
				bpdVO.setBpd_id(rs.getInt("BPD_ID"));
				bpdVO.setBpd_bpid(rs.getInt("BPD_BPID"));
				bpdVO.setBpd_bmemid(rs.getInt("BPD_BMEMID"));
				bpdVO.setBpd_bpprice(rs.getInt("BPD_BPPRICE"));
				bpdVO.setBpd_bpdate(rs.getTimestamp("BPD_BPDATE"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}finally {
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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return bpdVO;
	}

}
