package com.pt.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pd.model.PdVO;


public class PtDAO implements PtDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}



	private static final String INSERT_MEM_TRK=
			"INSERT INTO PRETRACKING (pt_memid,pt_pdid) VALUES(?, ?)";
	private static final String GET_MEM__TRK=
			"SELECT pt_pdid, pt_memid FROM PRETRACKING WHERE pt_memid = ? and pt_pdid =? ";
	private static final String DELETE = 
			"DELETE FROM PRETRACKING where pt_memid = ? and pt_pdid = ?";
	
	
	//判斷是否收藏商品
	public PtVO findByMemAndTrk(Integer pt_memid,Integer pt_pdid) {
			PtVO ptVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEM__TRK);

				pstmt.setInt(1, pt_memid);
				pstmt.setInt(2, pt_pdid);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					ptVO = new PtVO();
					ptVO.setPt_pdid(rs.getInt("pt_pdid"));
					ptVO.setPt_memid(rs.getInt("pt_memid"));

				}

				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
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
			return ptVO;
	}

	@Override
	//會員新增追蹤商品
	public void insertTrk(PtVO ptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_MEM_TRK);

			pstmt.setInt(1, ptVO.getPt_memid());
			pstmt.setInt(2, ptVO.getPt_pdid());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	//會員取消商品追蹤
	public void deleteTrk(Integer pt_memid,Integer pt_pdid) {


		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, pt_memid);
			pstmt.setInt(2, pt_pdid);

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

	@Override
	//會員查詢所有追蹤商品
	public List<PtVO> findTrkByMem(Integer pt_memid) {
		List<PtVO> list = new ArrayList<PtVO>();
		PtVO ptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM__TRK);

			pstmt.setInt(1, pt_memid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				ptVO = new PtVO();
				ptVO.setPt_memid(rs.getInt("pt_memid"));
				ptVO.setPt_pdid(rs.getInt("pt_pdid"));
				
				list.add(ptVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return list ;

		
	}
}
