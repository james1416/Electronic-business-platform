package com.pc.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.pd.model.PdVO;


public class PcDAO implements PcDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ONE_CLS =
			"SELECT pc_desc FROM PRECLASSIFICATION WHERE pc_id=?";
	private static final String GET_ALL_CLS =
			"SELECT * FROM PRECLASSIFICATION";
	
	
	public PcVO findByPc(Integer pc_id) {
		PcVO pcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CLS);

			pstmt.setInt(1, pc_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				pcVO = new PcVO();
				pcVO.setPc_desc(rs.getString("pc_desc"));

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
		return pcVO;
	}


	@Override
	public List<PcVO> getall() {
		List<PcVO> list = new ArrayList<PcVO>();
		PcVO pcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			con=ds.getConnection();
			pstmt= con.prepareStatement(GET_ALL_CLS);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				pcVO = new PcVO();
			
				pcVO.setPc_id(rs.getInt("pc_id"));
				pcVO.setPc_desc(rs.getString("pc_desc"));

			
				list.add(pcVO);
			
			}
		}catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
		return list;
	}

}
