package com.ordersList.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.product.model.ProductDAO;
import com.product.model.ProductVO;

public class OrdersListDAO implements OrdersListDAO_interface{
	
	private final String add = "INSERT INTO ORDERSLIST (OL_ODID, OL_PID , OL_PQ , OL_PRICE ) VALUES (?, ?, ?, ?)";
	private final String getall = "SELECT * FROM ORDERSLIST where ol_odid = ?";
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
	public void add(ProductVO productVO , Connection con, Integer odid) {
		PreparedStatement pstmt = null ;
		
		try {
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, odid);
			pstmt.setInt(2 , productVO.getP_id());
			pstmt.setInt(3, productVO.getP_stock());
			pstmt.setInt(4, productVO.getP_price());
			pstmt.executeUpdate();
			ProductDAO pdao = new ProductDAO();
			pdao.PDSell(productVO.getP_id(), productVO.getP_stock(), con);
		} catch (SQLException e) {
			if( con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	@Override
	public List<OrdersListVO> getall(Integer ol_odid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ;
		List<OrdersListVO> list = new ArrayList<OrdersListVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, ol_odid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrdersListVO ordersListVO = new OrdersListVO();
				ordersListVO.setOl_odid(rs.getInt("ol_odid"));
				ordersListVO.setOl_pid(rs.getInt("ol_pid"));
				ordersListVO.setOl_pq(rs.getInt("ol_pq"));
				ordersListVO.setOl_price(rs.getInt("ol_price"));
				list.add(ordersListVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}

}
