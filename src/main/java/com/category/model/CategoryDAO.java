package com.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CategoryDAO implements CategoryDAO_interface{
	
	private final String getall="SELECT * FROM mydb.CATEGORY";
	private static DataSource ds ;
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds =(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<CategoryVO> getall() {
		Connection con = null ;
		PreparedStatement pstmt = null ; 
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			CategoryVO categoryVO = new CategoryVO();
			categoryVO.setCg_id(rs.getInt("cg_id"));
			categoryVO.setCg_name(rs.getString("cg_name"));
			list.add(categoryVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(pstmt != null) {
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
		
		
		return list;
	}
	
	
}
