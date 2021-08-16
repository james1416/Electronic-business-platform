package com.ppdr.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class PpdrDAO implements PpdrDAO_interface{
	
	private final String getall="SELECT * FROM PREPDREPORT where ppdr_memid = ?";
	
	private final String add="INSERT INTO PREPDREPORT (ppdr_memid, ppdr_pid, ppdr_content, ppdr_date) VALUES (?, ?, ?, ?)";
	
	private static DataSource ds ;
	
	static {
		try {
			InitialContext ctx= new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<PpdrVO> getall(Integer ppdr_memid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		List<PpdrVO> list = new ArrayList<PpdrVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, ppdr_memid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PpdrVO ppdrVO = new PpdrVO();
				ppdrVO.setPpdr_id(rs.getInt("ppdr_id"));
				ppdrVO.setPpdr_memid(rs.getInt("ppdr_memid"));
				ppdrVO.setPpdr_pid(rs.getInt("ppdr_pid"));
				ppdrVO.setPpdr_content(rs.getString("ppdr_content"));
				ppdrVO.setPpdr_date(rs.getTimestamp("ppdr_date"));
				ppdrVO.setPpdr_sta(rs.getInt("ppdr_sta"));
				list.add(ppdrVO);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null ) {
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
	public void add(Integer ppdr_memid, Integer ppdr_pid, String ppdr_content) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, ppdr_memid);
			pstmt.setInt(2, ppdr_pid);
			pstmt.setString(3, ppdr_content);
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			pstmt.setTimestamp(4, ts);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	

}
