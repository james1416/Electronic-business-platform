package com.tracking.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class TrackingDAO implements TrackingDAO_interface{
	
	private final String findSelf = "SELECT * FROM mydb.TRACKING where tk_memid = ?";
	private final String check = "SELECT * FROM mydb.TRACKING where tk_pid = ? and tk_memid = ? ";
	private final String add = "INSERT INTO TRACKING(tk_pid , tk_memid , tk_date)VALUE(?,?,?)";
	private final String del = "DELETE FROM TRACKING WHERE tk_pid = ? and tk_memid = ?";
	static DataSource ds =null;
	
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
}
	@Override
	public List<TrackingVO> findSelf(Integer tk_memid) {
		Connection con = null ; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null ; 
		List<TrackingVO> list = new ArrayList<TrackingVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findSelf);
			pstmt.setInt(1, tk_memid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TrackingVO trackingVO= new TrackingVO();
				trackingVO.setTk_pid(rs.getInt("tk_pid"));
				trackingVO.setTk_memid(rs.getInt("tk_memid"));
				trackingVO.setTk_date(rs.getTimestamp("tk_date"));
				list.add(trackingVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public void add(TrackingVO trackingVO) {
		Connection con = null ; 
		PreparedStatement pstmt = null; 
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, trackingVO.getTk_pid());
			pstmt.setInt(2, trackingVO.getTk_memid());
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			pstmt.setTimestamp(3, ts);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void del(TrackingVO trackingVO) {
		Connection con = null ; 
		PreparedStatement pstmt = null; 
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(del);
			pstmt.setInt(1, trackingVO.getTk_pid());
			pstmt.setInt(2, trackingVO.getTk_memid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Boolean check(TrackingVO trackingVO) {
		Boolean tracking= false;
		Connection con = null ; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null ; 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(check);
			pstmt.setInt(1, trackingVO.getTk_pid());
			pstmt.setInt(2, trackingVO.getTk_memid());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				tracking = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return tracking;
	}

}
