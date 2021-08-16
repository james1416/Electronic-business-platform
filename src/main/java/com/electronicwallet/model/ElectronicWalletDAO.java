package com.electronicwallet.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.*;

import com.member.model.MemberDAO;



public class ElectronicWalletDAO implements ElectronicWalletDAO_interface{
	public static DataSource ds=null;
	
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ElectronicWalletVO> getAll(Integer ele_memid) {
		String getAll = "select * from mydb.electronicwallet where ele_memid = ? order by ele_time desc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ElectronicWalletVO ewVO = null;
		List<ElectronicWalletVO> list = new ArrayList<ElectronicWalletVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			pstmt.setInt(1, ele_memid);
			rs = pstmt.executeQuery();	
			while(rs.next()) {
				ewVO = new ElectronicWalletVO();
				ewVO.setEle_id(rs.getInt("ele_id"));
				ewVO.setEle_memid(rs.getInt("ele_memid"));
				ewVO.setEle_time(rs.getTimestamp("ele_time"));
				ewVO.setEle_rec(rs.getString("ele_rec"));
				ewVO.setEle_mon(rs.getInt("ele_mon"));
				list.add(ewVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void insertNewPayment(Integer ele_memid, Timestamp ele_time, String ele_rec, Integer ele_mon) {
		String insertNewPayment= "insert into electronicwallet (ele_memid, ele_time, ele_rec, ele_mon) values(?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(insertNewPayment);
			pstmt.setInt(1, ele_memid);
			pstmt.setTimestamp(2, ele_time);
			pstmt.setString(3, ele_rec);
			pstmt.setInt(4, ele_mon);
			pstmt.executeUpdate();
			
			MemberDAO dao = new MemberDAO();
			dao.updateEleWal(ele_memid, ele_mon, con);
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
	public void refund(Integer od_bmemid, Timestamp ele_time, Integer od_price, Connection con) {
		String walUpdatedByTranOnSmem = "insert into electronicwallet (ele_memid, ele_time, ele_rec, ele_mon) values(?, ?, ?, ?)";
		String ele_rec = "您的訂單於"+ele_time+"已退款金額為"+od_price+"元";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(walUpdatedByTranOnSmem);
			pstmt.setInt(1, od_bmemid);
			pstmt.setTimestamp(2, ele_time);
			pstmt.setString(3, ele_rec);
			pstmt.setInt(4, od_price);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-eleWalSmem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
	public void walUpdatedByTranOnSmem(Integer od_smemid, Timestamp ele_time, Integer od_price, Connection con) {
		String walUpdatedByTranOnSmem = "insert into electronicwallet (ele_memid, ele_time, ele_rec, ele_mon) values(?, ?, ?, ?)";
		String ele_rec = "您的訂單於"+ele_time+"已入帳	金額為"+od_price+"元";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(walUpdatedByTranOnSmem);
			pstmt.setInt(1, od_smemid);
			pstmt.setTimestamp(2, ele_time);
			pstmt.setString(3, ele_rec);
			pstmt.setInt(4, od_price);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-eleWalSmem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
	public void walUpdatedByTranOnBmem(Integer od_bmemid, Timestamp ele_time, Integer od_price, Connection con) {
		String walUpdatedByTranOnBmem="insert into electronicwallet (ele_memid, ele_time, ele_rec, ele_mon) values(?, ?, ?, ?)";
		String ele_rec="您於"+ele_time+"所消費的訂單金額為"+od_price+"元";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(walUpdatedByTranOnBmem);
			pstmt.setInt(1, od_bmemid);
			pstmt.setTimestamp(2, ele_time);
			pstmt.setString(3, ele_rec);
			pstmt.setInt(4, od_price);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-eleWalBmem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	public Integer findTotalCount(Integer ele_memid) {
		String findTotalCount = "select count(*) from mydb.electronicwallet WHERE ele_memid=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer totalCount = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findTotalCount);
			pstmt.setInt(1, ele_memid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				totalCount = rs.getInt("count(*)");
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
			+ se.getMessage());
	}finally {
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
		return totalCount;
}
	
	@Override
	public List<ElectronicWalletVO> findByPage(Integer ele_memid, Integer start, Integer pageSize) {
		String findByPage = "select * from mydb.electronicwallet WHERE ele_memid=? ORDER BY ele_time DESC limit ?, ?";
		System.out.println("hi from dao");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		ElectronicWalletVO ewv = null;
		List<ElectronicWalletVO> list = new ArrayList<ElectronicWalletVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findByPage);
			pstmt.setInt(1, ele_memid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ewv = new ElectronicWalletVO();
				ewv.setEle_id(rs.getInt("ele_id"));
				ewv.setEle_memid(rs.getInt("ele_memid"));
				ewv.setEle_time(rs.getTimestamp("ele_time"));
				ewv.setEle_rec(rs.getString("ele_rec"));
				ewv.setEle_mon(rs.getInt("ele_mon"));
				list.add(ewv);
			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
			+ se.getMessage());
	}finally {
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
	public ElectronicWalletVO getOneLog(Integer ele_memid, Integer ele_id) {
		String getOneLog = "SELECT*FROM electronicwallet WHERE ele_memid=? AND ele_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		ElectronicWalletVO ewv = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getOneLog);
			pstmt.setInt(1, ele_memid);
			pstmt.setInt(2, ele_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ewv = new ElectronicWalletVO();
				ewv.setEle_id(rs.getInt("ele_id"));
				ewv.setEle_memid(rs.getInt("ele_memid"));
				ewv.setEle_time(rs.getTimestamp("ele_time"));
				ewv.setEle_rec(rs.getString("ele_rec"));
				ewv.setEle_mon(rs.getInt("ele_mon"));
			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
			+ se.getMessage());
	}finally {
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
		return ewv;
	}
}
