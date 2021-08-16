package com.orders.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cartList.model.CartListDAO;
import com.electronicwallet.model.ElectronicWalletDAO;
import com.electronicwallet.model.ElectronicWalletService;
import com.member.model.MemberDAO;
import com.member.model.MemberVO;
import com.memberorders.model.MemberordersVO;
import com.memberorders.model.ordersVO;
import com.notice.model.NoticeDAO;
import com.ordersList.model.OrdersListDAO;
import com.ordersList.model.OrdersListVO;
import com.product.model.ProductDAO;
import com.product.model.ProductVO;
import com.productImg.model.ProductImgDAO;
import com.productImg.model.ProductImgService;
import com.productImg.model.ProductImgVO;


public class OrdersDAO implements OrdersDAO_interface{
	
	private final String add = "INSERT INTO ORDERS (OD_BMEMID, OD_SMEMID, OD_PRICE, OD_DATE, OD_SHIPPING, OD_SHIPINFO, OD_PAYMENT, OD_NOTES, OD_NAME, OD_TEL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String changeOD = "UPDATE orders SET OD_STA = ?, OD_GOLDFLOW = ? WHERE OD_ID = ?";
	private final String getone = "SELECT * FROM ORDERS where od_id = ?";
	private final String selectMyBOrders="SELECT * FROM ORDERS where od_bmemid = ?";
	private final String selectMySOrders="SELECT * FROM ORDERS where od_smemid = ?";
	private final String getCount = "SELECT count(*) FROM ORDERS where od_smemid = ? and od_sta = ?";
	private static DataSource ds ;
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds =(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized public boolean add(Map<String, List<List<Object>>> KeyODandPD ,Integer memid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		OrdersListDAO oldao = new OrdersListDAO();
		Integer od_price = 0 ;
		Integer od_bmemid = 0;
		Integer od_smemid = 0;
		Timestamp ts = null ;
		NoticeDAO nado = new NoticeDAO();
		Boolean checkPoint = true;
		List<ProductVO> needCheck = new ArrayList<ProductVO>();

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			String col[] = {"od_id"};
			Integer odid = null;
			
			Set<String> keys = KeyODandPD.keySet();
			for(String key : keys) {
				for(int i = 0 ; i < KeyODandPD.get(key).size() ; i++ ) {
					for(Object obj : KeyODandPD.get(key).get(i)) {
						if(obj instanceof ProductVO) {
							ProductVO productVO = (ProductVO) obj;
							needCheck.add(productVO);
							oldao.add(productVO, con, odid);
							
						}
						if(obj instanceof OrdersVO) {
							pstmt = con.prepareStatement(add, col);
							pstmt.setInt(1, ((OrdersVO)obj).getOd_bmemid());
							od_bmemid = ((OrdersVO)obj).getOd_bmemid();
							pstmt.setInt(2, ((OrdersVO)obj).getOd_smemid());
							od_smemid = ((OrdersVO)obj).getOd_smemid();
							pstmt.setInt(3, ((OrdersVO)obj).getOd_price());
							od_price += ((OrdersVO)obj).getOd_price();
							nado.add(0, od_smemid, memid, con);
							nado.toB_org(0, memid, memid, con);
							Date date = new Date();
							ts = new Timestamp(date.getTime());
							pstmt.setTimestamp(4, ts);
							pstmt.setInt(5, ((OrdersVO)obj).getOd_shipping());
							pstmt.setString(6, ((OrdersVO)obj).getOd_shipinfo());
							pstmt.setInt(7, ((OrdersVO)obj).getOd_payment());
							pstmt.setString(8, ((OrdersVO)obj).getOd_notes());
							pstmt.setString(9, ((OrdersVO)obj).getOd_name());
							pstmt.setString(10, ((OrdersVO)obj).getOd_tel());
							pstmt.executeUpdate();
							rs = pstmt.getGeneratedKeys();
							
							while(rs.next()) {
								odid = rs.getInt(1);
							}
							rs.close();
							pstmt.close();
							
						}
					}
				}
				
			}
			

			MemberDAO mdao = new MemberDAO();
			mdao.minusWalletByOrders(od_price, od_bmemid, con);
			ElectronicWalletDAO edao = new ElectronicWalletDAO();
			edao.walUpdatedByTranOnBmem(od_bmemid, ts, od_price, con);
			
			CartListDAO cldao = new CartListDAO();
			cldao.delAll(memid, con);
			
			
			ProductDAO pdao = new ProductDAO();
			
			for(ProductVO p : needCheck) {
				if(p.getP_stock() > pdao.findPD(p.getP_id()).getP_stock()) {
					checkPoint = false;
				}
			}
			
			
			if(checkPoint) {
				con.commit();
				con.setAutoCommit(true);
			}else {
				con.rollback();
				con.setAutoCommit(true);
			}
			
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
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
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return checkPoint;
	}

	@Override
	public List<OrdersVO> mySOrders(Integer od_smemid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ;
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(selectMySOrders);
			pstmt.setInt(1, od_smemid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_name(rs.getString("od_name"));
				ordersVO.setOd_tel(rs.getString("od_tel"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				list.add(ordersVO);
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

	@Override
	public List<OrdersVO> myBOrders(Integer od_bmemid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ;
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(selectMyBOrders);
			pstmt.setInt(1, od_bmemid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_name(rs.getString("od_name"));
				ordersVO.setOd_tel(rs.getString("od_tel"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				list.add(ordersVO);
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

	@Override
	public void changeOD(Integer od_sta , Integer od_id , Integer od_goldflow) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		Integer b_memid = null;
		Integer s_memid = null;
		
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(changeOD);
			pstmt.setInt(1, od_sta);
			pstmt.setInt(2, od_goldflow);
			pstmt.setInt(3, od_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, od_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				b_memid = rs.getInt("od_bmemid");
				s_memid = rs.getInt("od_smemid");
				
			}
			
			
			NoticeDAO ndao = new NoticeDAO();
			ndao.add(od_sta, s_memid, od_id, con);
			ndao.toB_org(od_sta, b_memid, od_id, con);
			
			con.commit();
			con.setAutoCommit(true);
			
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
			if (pstmt != null) {
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
	}

	@Override
	public List<OrdersVO> getAllOrders() {
		String getAllOrders = "select * from orders";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAllOrders);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				list.add(ordersVO);
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
	public List<OrdersVO> getLv7Orders(Integer start ,Integer rows) {
		String getLv7Only = "SELECT * FROM orders WHERE  od_sta = 7 or od_sta = 8 order by od_id DESC LIMIT ?,? ";
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getLv7Only);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				list.add(ordersVO);
			}
		}catch (SQLException se) {
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
	public void refund(Integer od_id) {
		String getLv8_OD = "SELECT * FROM orders WHERE od_sta=8 AND od_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		OrdersVO ordersVO = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(getLv8_OD);
			pstmt.setInt(1, od_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
			}
			
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ele_time = Timestamp.valueOf(now);
		
		MemberDAO dao = new MemberDAO();
		dao.addWalletByOrders(ordersVO.getOd_price(), ordersVO.getOd_bmemid(), con);
		
		ElectronicWalletDAO dao2 = new ElectronicWalletDAO();
		dao2.refund(ordersVO.getOd_bmemid(), ele_time, ordersVO.getOd_price(), con);
		
		OrdersDAO dao3 = new OrdersDAO();
		dao3.changeODstaBe6(od_id, con);
		
		NoticeDAO dao4 = new NoticeDAO();
		dao4.toB_org(6, ordersVO.getOd_bmemid(), od_id, con);
		
		con.commit();	
		con.setAutoCommit(true);
		}catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-�-orders");
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
	public void updateTradersWalletByOrdersPK(Integer od_id) {
		String getLv7OrdersByPK = "SELECT * FROM orders WHERE od_sta=7 AND od_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		OrdersVO ordersVO = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(getLv7OrdersByPK);
			pstmt.setInt(1, od_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
			}
			
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ele_time = Timestamp.valueOf(now);
		MemberDAO dao = new MemberDAO();
		dao.addWalletByOrders(ordersVO.getOd_price(), ordersVO.getOd_smemid(), con);
		ElectronicWalletDAO dao2 = new ElectronicWalletDAO();
		dao2.walUpdatedByTranOnSmem(ordersVO.getOd_smemid(), ele_time, ordersVO.getOd_price(), con);
		OrdersDAO dao3 = new OrdersDAO();
		dao3.hideOrdersAfterAppropriation(ordersVO.getOd_id(), con);
		
		NoticeDAO dao4 = new NoticeDAO();
		dao4.add(9, ordersVO.getOd_smemid(), od_id, con);
		
		con.commit();	
		con.setAutoCommit(true);
		}catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-�-orders");
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
	public void changeODstaBe6(Integer od_id, Connection con) {
		String hideOrders = "UPDATE orders SET od_goldflow = 4 WHERE od_id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(hideOrders);
			pstmt.setInt(1, od_id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-�-hideOrders");
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
	public void hideOrdersAfterAppropriation(Integer od_id, Connection con) {
		String hideOrders = "UPDATE orders SET od_goldflow = 3 WHERE od_id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(hideOrders);
			pstmt.setInt(1, od_id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-�-hideOrders");
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
	public Integer getCount(Integer mem_id, Integer od_sta) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		Integer count = 0 ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getCount);
			pstmt.setInt(1, mem_id);
			pstmt.setInt(2, od_sta);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return count;
	}

	@Override
	public OrderBean getone(Integer od_id) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		OrdersVO ordersVO = null;
		OrderBean ob = new OrderBean();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, od_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				ob.setOrdersVO(ordersVO);
			}
			
		OrdersListDAO oldao = new OrdersListDAO();
		List<OrdersListVO> ollist = oldao.getall(od_id);
		
		List<ProductVO> plist = new ArrayList<ProductVO>();
		ProductDAO pdao = new ProductDAO();
		for(OrdersListVO ol : ollist) {
			plist.add(pdao.findPD(ol.getOl_pid()));
		}
		
		List<forODimg> imglist = new ArrayList<forODimg>();
		
		ProductImgService pis = new ProductImgService();
		for(ProductVO p : plist) {
			forODimg fod =  new forODimg();
			if(pis.check(p.getP_id())) {
				fod.setPdimg(pis.get(p.getP_id()));
			}else {
				fod.setPdimg(pis.showDefault());
			}
			fod.setPdid(p.getP_id());
			imglist.add(fod);
			
		}
		
		ob.setOlist(ollist);
		ob.setPlist(plist);
		ob.setImgList(imglist);
		
		
		}catch (SQLException se) {
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
		
		return ob;
	}

	@Override
	public List<OrdersVO> getAllbySTA(Integer od_sta, Integer start ,Integer rows) {
		String getAllOrders = "SELECT * FROM ORDERS where od_goldflow = ? order by od_id DESC LIMIT ?,? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAllOrders);
			pstmt.setInt(1, od_sta);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				list.add(ordersVO);
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

// for 購物車
	public Map<Integer, List<MemberordersVO>> listAllOrdersByMem_id(Integer od_bmemid){
		String join = "SELECT od_id, od_smemid, od_price, ol_price, od_sta, ol_pid, ol_pq, p_id, p_name, p_desc, mem_acc FROM orders e INNER JOIN orderslist ON od_id = ol_odid INNER JOIN product ON  ol_pid =  p_id INNER JOIN MEMBER ON od_smemid = mem_id WHERE od_bmemid = ? order by od_id desc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberordersVO> list = new ArrayList<MemberordersVO>();
		
		Map<Integer, List<MemberordersVO>> map = new HashMap<Integer, List<MemberordersVO>>();
		Set<Integer> set = new HashSet<Integer>(); //處理訂單編號
		OrdersVO ordersVO = null;
		OrdersListVO ordersListVO = null;
		ProductVO productVO = null;
		MemberVO memberVO = null;
		MemberordersVO memberordersVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(join);
			pstmt.setInt(1, od_bmemid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersListVO = new OrdersListVO();
				ordersListVO.setOl_pid(rs.getInt("ol_pid"));
				ordersListVO.setOl_pq(rs.getInt("ol_pq"));
				ordersListVO.setOl_price(rs.getInt("ol_price"));
				productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_desc(rs.getString("p_desc"));
				memberVO = new MemberVO();
				memberVO.setMem_acc(rs.getString("mem_acc"));
				memberordersVO = new MemberordersVO();
				memberordersVO.setOrdersListVO(ordersListVO);
				memberordersVO.setOrdersVO(ordersVO);
				memberordersVO.setProductVO(productVO);
				memberordersVO.setMemberVO(memberVO);
				
				list.add(memberordersVO);
				set.add(rs.getInt("od_id"));
			}
			
			
				for(Integer od_id:set) {
					map.put(od_id, new ArrayList<MemberordersVO>());
				}
				
				for(MemberordersVO moVO:list) {
					Integer od_id = moVO.getOrdersVO().getOd_id();
					map.get(od_id).add(moVO);
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
		return map;

	}

	@Override
	public List<OrderBean> getSelfOrders(Integer mem_id) {
		String getSlefOrders= "SELECT * FROM mydb.ORDERS where od_bmemid = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderBean> odList = new ArrayList<OrderBean>();
		OrdersListDAO oldao = new OrdersListDAO();
		ProductDAO pdao = new ProductDAO();
		ProductImgService pis = new ProductImgService();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getSlefOrders);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderBean ob = new OrderBean();
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_bmemid(rs.getInt("od_bmemid"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_date(rs.getTimestamp("od_date"));
				ordersVO.setOd_shipping(rs.getInt("od_shipping"));
				ordersVO.setOd_shipinfo(rs.getString("od_shipinfo"));
				ordersVO.setOd_payment(rs.getInt("od_payment"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_notes(rs.getString("od_notes"));
				ordersVO.setOd_comment(rs.getString("od_comment"));
				ordersVO.setOd_point(rs.getInt("od_point"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersVO.setOd_goldflow(rs.getInt("od_goldflow"));
				ob.setOrdersVO(ordersVO);
				List<OrdersListVO> ollist= oldao.getall(rs.getInt("od_id"));
				List<ProductVO> plist = new ArrayList<ProductVO>();
				List<forODimg> imglist = new ArrayList<forODimg>();
				for(OrdersListVO ol : ollist) {
					
					ProductVO productVO = new ProductVO();
					productVO = pdao.findPD(ol.getOl_pid());
					plist.add(productVO);
					
					forODimg fod = new forODimg();
					if(pis.check(ol.getOl_pid())) {
						fod.setPdimg(pis.get(ol.getOl_pid()));
					}else {
						fod.setPdimg(pis.showDefault());
					}
					fod.setPdid(ol.getOl_pid());
					imglist.add(fod);
				}
				ob.setOlist(ollist);
				ob.setPlist(plist);
				ob.setImgList(imglist);
				odList.add(ob);
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

		return odList;
	}

	
	
	
	
	@Override
	public Map<Integer, List<MemberordersVO>> listOrdersByMem_idandOd_sta(Integer od_bmemid, Integer od_sta) {
		String listAllLv2OrdersByMem_id="SELECT od_id, od_smemid, od_price, ol_price, od_sta, od_rating, ol_pid, ol_pq, p_id, p_name, p_desc, mem_acc FROM orders e INNER JOIN orderslist ON od_id = ol_odid INNER JOIN product ON  ol_pid =  p_id INNER JOIN MEMBER ON od_smemid = mem_id WHERE od_bmemid = ? and od_sta= ? order by od_id desc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberordersVO> list = new ArrayList<MemberordersVO>();
		OrdersVO ordersVO = null;
		OrdersListVO ordersListVO = null;
		ProductVO productVO = null;
		MemberVO memberVO = null;
		MemberordersVO memberordersVO = null;
		Set<Integer> set = new HashSet<Integer>();
		Map<Integer, List<MemberordersVO>> map = new HashMap<Integer, List<MemberordersVO>>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(listAllLv2OrdersByMem_id);
			pstmt.setInt(1, od_bmemid);
			pstmt.setInt(2, od_sta);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOd_id(rs.getInt("od_id"));
				ordersVO.setOd_smemid(rs.getInt("od_smemid"));
				ordersVO.setOd_price(rs.getInt("od_price"));
				ordersVO.setOd_sta(rs.getInt("od_sta"));
				ordersVO.setOd_rating(rs.getInt("od_rating"));
				ordersListVO = new OrdersListVO();
				ordersListVO.setOl_price(rs.getInt("ol_price"));
				ordersListVO.setOl_pid(rs.getInt("ol_pid"));
				ordersListVO.setOl_pq(rs.getInt("ol_pq"));
				productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_desc(rs.getString("p_desc"));
				memberVO = new MemberVO();
				memberVO.setMem_acc(rs.getString("mem_acc"));
				memberordersVO = new MemberordersVO();
				memberordersVO.setOrdersVO(ordersVO);
				memberordersVO.setOrdersListVO(ordersListVO);
				memberordersVO.setProductVO(productVO);
				memberordersVO.setMemberVO(memberVO);
				set.add(rs.getInt("od_id"));
				
				list.add(memberordersVO);
			}
			
			for(Integer od_id : set) {
				map.put(od_id, new ArrayList<MemberordersVO>());
			}
			for(MemberordersVO moVO :list) {
				Integer od_id = moVO.getOrdersVO().getOd_id();
				map.get(od_id).add(moVO);
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
		return map;

	}

	@Override
	public void updateCommentByOdid(String od_comment, Integer od_point, Integer od_rating, Integer od_id) {
		String updateComment="UPDATE orders SET od_comment= ?, od_point=?, od_rating=? WHERE od_id = ? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updateComment);
			pstmt.setString(1, od_comment);
			pstmt.setInt(2, od_point);
			pstmt.setInt(3, od_rating);
			pstmt.setInt(4, od_id);
			
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
	public void returned(Integer od_id, Integer od_sta) {
		String changODandGoldflow = "UPDATE orders SET OD_STA = ?, OD_GOLDFLOW = 2 WHERE OD_ID = ?";
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		Integer b_memid = null;
		
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(changODandGoldflow);
			pstmt.setInt(1, od_sta);
			pstmt.setInt(2, od_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, od_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				b_memid = rs.getInt("od_bmemid");
				
			}
			
			
			NoticeDAO ndao = new NoticeDAO();
			if(od_sta == 8) {
				//成功退貨
				ndao.toB_org(11, b_memid, od_id, con);
				
			}else {
				//不接受退貨
				ndao.toB_org(12, b_memid, od_id, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
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
			if (pstmt != null) {
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
		
	}
}


