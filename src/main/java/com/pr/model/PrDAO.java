package com.pr.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PrDAO implements PrDAO_interface {
	
	private String getAll="SELECT * FROM prereport";

	private String getSelf = 
			"";
	private String add = 
			"INSERT INTO  PREREPORT (PR_POID , PR_BMEMID, PR_SMEMID,PR_DESC,PR_DATE ) VALUES(?,?,?,?,?)";
	private String update="";
	
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
	public List<PrVO> getSelf(Integer pr_bmemid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(PrVO prVO) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, prVO.getPr_poid());
			pstmt.setInt(2, prVO.getPr_bmemid());
			pstmt.setInt(3, prVO.getPr_smemid());
			pstmt.setString(4, prVO.getPr_desc());
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			pstmt.setTimestamp(5, ts);
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
	public void update(Integer pr_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PrVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null ; 
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
