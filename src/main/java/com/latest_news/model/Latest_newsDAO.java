package com.latest_news.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Latest_newsDAO implements Latest_newsDAO_interface{
	
private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//done
	@Override
	public List<Latest_newsVO> getAllLN(Integer start , Integer rows) {
		String getAll = "select * from mydb.latest_news order by ln_tsp limit ? , ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		List<Latest_newsVO> list = new ArrayList<Latest_newsVO>();
		ResultSet rs = null;
		Latest_newsVO lnVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			lnVO = new Latest_newsVO();
			lnVO.setLn_id(rs.getInt(1));
			lnVO.setLn_con(rs.getString(2));
			lnVO.setLn_tsp(rs.getTimestamp(3));
			lnVO.setLn_sta(rs.getInt(4));
				list.add(lnVO);
			}	
		} catch (SQLException e) {
			
			e.printStackTrace();
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
	public void deleteLN(Integer ln_id) {
		String deleteOne = "delete from mydb.latest_news where ln_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(deleteOne);
			pstmt.setInt(1, ln_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
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
	public void updateLN(Latest_newsVO latest_newsVO) {
		String update = "update mydb.latest_news set ln_con = ?, ln_sta = ? where ln_id = ?";
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(update);
			pstmt.setString(1, latest_newsVO.getLn_con());
//			long date = new Date().getTime();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String now = sdf.format(date);
//			java.sql.Timestamp tsp = java.sql.Timestamp.valueOf(now);
			pstmt.setInt(2, latest_newsVO.getLn_sta());
			pstmt.setInt(3, latest_newsVO.getLn_id());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void addLN(String ln_con) {
		String addLN= "Insert into mydb.latest_news (ln_con, ln_tsp) values(?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(addLN);
			pstmt.setString(1, ln_con);
			Long date = new Date().getTime();
			Timestamp ln_tsp = new Timestamp(date);
			pstmt.setTimestamp(2, ln_tsp);
			pstmt.executeUpdate();
			
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
	}

	@Override
	public List<Latest_newsVO> listOneLN(Integer ln_id) {
		String listOneLN = "SELECT*FROM mydb.latest_news WHERE ln_id = ? ORDER BY ln_tsp";
		Connection con = null;
		PreparedStatement pstmt = null;
		List<Latest_newsVO> list = new ArrayList<Latest_newsVO>();
		ResultSet rs = null;
		Latest_newsVO lnVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(listOneLN);
			pstmt.setInt(1, ln_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				lnVO = new Latest_newsVO();
				lnVO.setLn_id(rs.getInt("ln_id"));
				lnVO.setLn_con(rs.getString("ln_con"));
				lnVO.setLn_tsp(rs.getTimestamp("ln_TSP"));
				lnVO.setLn_sta(rs.getInt("ln_sta"));
				list.add(lnVO);
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
	public Integer findTotalCount() {
		String findTotalCount = "select count(*) from mydb.latest_news";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer totalCount = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findTotalCount);
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
	public List<Latest_newsVO> findByPage(Integer start, Integer pageSize) {
		String findByPage = "select * from mydb.latest_news ORDER BY ln_tsp DESC limit ?, ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		Latest_newsVO ln = null;
		List<Latest_newsVO> list = new ArrayList<Latest_newsVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findByPage);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ln = new Latest_newsVO();
				ln.setLn_id(rs.getInt("ln_id"));
				ln.setLn_con(rs.getString("ln_con"));
				ln.setLn_tsp(rs.getTimestamp("ln_tsp"));
				ln.setLn_sta(rs.getInt("ln_sta"));
				list.add(ln);
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
}
