package com.cartList.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.product.model.ProductDAO;
import com.product.model.ProductVO;

public class CartListDAO implements CartListDAO_interface{
	private static DataSource ds ;
	private final String getall= "SELECT * FROM mydb.CARTLIST WHERE cl_memid = ?";
	private final String check = "SELECT * FROM mydb.CARTLIST WHERE cl_memid = ? and cl_pid = ? ";
	private final String del="DELETE FROM CARTLIST WHERE CL_MEMID=? and CL_PID =?";
	private final String delall="DELETE FROM CARTLIST WHERE CL_MEMID=?";
	private final String add="insert into CARTLIST(CL_MEMID , CL_PID)value(?,?)";
	private final String updata="UPDATE CARTLIST SET CL_PQ = ? WHERE CL_MEMID = ? and CL_PID = ?";
	
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CartListVO> getall(Integer cl_memid) {
		List<CartListVO> list = new ArrayList<CartListVO>();
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, cl_memid);
			rs  = pstmt.executeQuery();
			while(rs.next()) {
				CartListVO cartListVO = new CartListVO();
				cartListVO.setCl_memid(rs.getInt("cl_memid"));
				cartListVO.setCl_pid(rs.getInt("cl_pid"));
				cartListVO.setCl_pq(rs.getInt("cl_pq"));
				list.add(cartListVO);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
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
	public void del(Integer cl_memid, Integer cl_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(del);
			pstmt.setInt(1, cl_memid);
			pstmt.setInt(2, cl_pid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void add(Integer cl_memid, Integer cl_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, cl_memid);
			pstmt.setInt(2, cl_pid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public boolean check(Integer cl_memid, Integer cl_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		boolean Have = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(check);
			pstmt.setInt(1, cl_memid);
			pstmt.setInt(2, cl_pid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Have = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if ( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return Have;
	}

	@Override
	public Integer updata(CartListVO cartListVO,String up_or_down) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		int clpq = 0 ;
		ProductDAO pdao = new ProductDAO();
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(check);
			pstmt.setInt(1, cartListVO.getCl_memid());
			pstmt.setInt(2, cartListVO.getCl_pid());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				clpq = rs.getInt("cl_pq");
			}
			pstmt.close();
			rs.close();
			
			pstmt = con.prepareStatement(updata);
			
			
			if(pdao.findPD(cartListVO.getCl_pid()).getP_stock() < clpq) {
				clpq = 2;
				pstmt.setInt(1, clpq);
			}else {
				if("up".equals(up_or_down)) {
					clpq++;
					pstmt.setInt(1, clpq);
				}
				if("down".equals(up_or_down)) {
					clpq--;
					pstmt.setInt(1, clpq);
				}
			}
			pstmt.setInt(2, cartListVO.getCl_memid());
			pstmt.setInt(3, cartListVO.getCl_pid());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return clpq;
	}

	@Override
	public List<ProductVO> beOrder(Integer cl_memid) {
		List<CartListVO> cartList = new ArrayList<CartListVO>();
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		ProductDAO dao = new ProductDAO();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, cl_memid);
			rs  = pstmt.executeQuery();
			while(rs.next()) {
				CartListVO cartListVO = new CartListVO();
				cartListVO.setCl_memid(rs.getInt("cl_memid"));
				cartListVO.setCl_pid(rs.getInt("cl_pid"));
				cartListVO.setCl_pq(rs.getInt("cl_pq"));
				cartList.add(cartListVO);
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return dao.getCartPD(cartList);
	}

	@Override
	public void delAll(Integer cl_memid , Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(delall);
			pstmt.setInt(1, cl_memid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Integer getSum(Integer cl_memid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		Integer sum = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, cl_memid);
			rs  = pstmt.executeQuery();
			while(rs.next()) {
				sum += 1;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(	rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return sum;
	}
	
}
