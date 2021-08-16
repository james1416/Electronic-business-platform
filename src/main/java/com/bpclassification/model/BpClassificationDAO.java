package com.bpclassification.model;

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



public class BpClassificationDAO implements BpClassificationDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO BP_CLASSIFICATION (BPC_CGNAME) VALUES(?);";
	private static final String GET_ALL_STMT = "SELECT BPC_ID,BPC_CGNAME FROM BP_CLASSIFICATION";
	private static final String GET_ONE_STMT = "SELECT BPC_ID,BPC_CGNAME FROM BP_CLASSIFICATION WHERE BPC_ID = ?";
	private static final String DELETE = "DELETE FROM BP_CLASSIFICATION WHERE BPC_ID = ?";
	private static final String UPDATE = "UPDATE BP_CLASSIFICATION SET BPC_CGNAME = ? WHERE BPC_ID = ?";
	
	
	@Override
	public void insert(BpClassificationVO bpcVO) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, bpcVO.getBpc_cgname());
			pstmt.executeUpdate();
				
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured ."+se.getMessage());
		}finally {
			if(pstmt != null) {
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
	public void update(BpClassificationVO bpcVO) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(UPDATE);
			
			pstmt.setString(1, bpcVO.getBpc_cgname());
			pstmt.setInt(2, bpcVO.getBpc_id());
			pstmt.executeUpdate();
				
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured ."+se.getMessage());
		}finally {
			if(pstmt != null) {
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
	public void delete(Integer bpc_id) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(DELETE);
			
			pstmt.setInt(1, bpc_id);
			pstmt.executeUpdate();
				
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured ."+se.getMessage());
		}finally {
			if(pstmt != null) {
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
	public BpClassificationVO findByPrimaryKey(Integer bpc_id) {
		BpClassificationVO bpcVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, bpc_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bpcVO = new BpClassificationVO();
				bpcVO.setBpc_id(rs.getInt("bpc_id"));
				bpcVO.setBpc_cgname(rs.getString("bpc_cgname"));
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A data base error occured ."+se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
			
		}
		return bpcVO;
	}

	@Override
	public List<BpClassificationVO> getAll() {
		List<BpClassificationVO> list = new ArrayList<>();
		BpClassificationVO bpcVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bpcVO = new BpClassificationVO();
				bpcVO.setBpc_id(rs.getInt("bpc_id"));
				bpcVO.setBpc_cgname(rs.getString("bpc_cgname"));
				list.add(bpcVO);
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
	
}
