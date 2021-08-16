package com.po.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.electronicwallet.model.ElectronicWalletDAO;
import com.member.model.MemberDAO;
import com.notice.model.NoticeDAO;
import com.orders.model.OrdersDAO;
import com.orders.model.OrdersVO;
import com.pd.model.PdDAO;
import com.pd.model.PdVO;


import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class PoDAO implements PoDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 新增預購商品
	private  final String ADD_ORDER = "INSERT INTO PREORDER (po_bmemid , po_smemid , po_pdid ,po_date , po_ship , po_qty , po_price , po_pay ,po_sta , po_comment ,po_tel,po_bname,po_home) VALUES (?, ?, ?, ? , ?, ?, ?, ?, ?,?, ?, ?, ?)";
	// 計算單一商品的預購總數量 用以判斷是否達標
	private final String COUNT_ORDER = "SELECT SUM(PO_QTY)  AS SUM FROM PREORDER WHERE PO_PDID = ?";
	//不管GOLDFLOW的更改狀態指令
	private final String NO_GOLDFLOW = "UPDATE PREORDER SET po_sta=? where po_id = ?";
	// 更新預購商品的訂購狀態
	private final String UPDATE_ORDER = "UPDATE PREORDER SET po_sta=?, po_goldflow = ? where po_id = ?";
	// 拿到會員自己"購買"預購的訂單
	private final String GET_SELF_BORDER = "SELECT * FROM PREORDER WHERE po_bmemid = ?";
	// 拿到會員自己"販售"預購的訂單
	private final String GET_SELF_SORDER = "SELECT * FROM PREORDER WHERE po_smemid =?";
	// 查詢此商品是否有人下訂單
	private final String HAVE_BUY_PD = "SELECT * FROM PREORDER WHERE PO_PDID = ?";
	// 商品成團後 更改所有購買此商品的訂單狀態
	private final String Change_PD_ALL_STA = "UPDATE PREORDER SET po_sta = ? WHERE po_pdid=?";

	// 拿自己買的並且已經成立的訂單
	private final String getSelfOrdersAll = "SELECT * FROM mydb.PREORDER where PO_BMEMID = ? and po_sta > 1 order by po_id desc limit ? , ? ";
	
	// 拿自己買的並且已經成立某個狀態的訂單
	private final String getSelfOrdersSTA = "SELECT * FROM mydb.PREORDER where PO_BMEMID = ? and po_sta = ? order by po_id desc limit ? , ? ";

	// 取得購買此商品的所有訂單
	private static final String GET_ALL_PD_ORDER = "SELECT * FROM PREORDER WHERE po_pdid=?";
	
	// 買家評論訂單
	private static final String SET_COMMENT= "UPDATE PREORDER SET po_comment=? , po_point =? , po_sta = ? ,po_iscom = ? WHERE po_id =?";
		
	
	
	@Override
	public void addorder(PoVO poVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer po_price = null ;
		Integer po_bmemid = null;
		Integer po_id = null;
		
		try {
			String col[] = {"po_id"};
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_ORDER,col);
			con.setAutoCommit(false);

			pstmt.setInt(1, poVO.getPo_bmemid());
			po_bmemid = poVO.getPo_bmemid();
			pstmt.setInt(2, poVO.getPo_smemid());
			pstmt.setInt(3, poVO.getPo_pdid());

			// 拿取當下時間給訂單成立時間
			Long date = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			java.sql.Timestamp ts = Timestamp.valueOf(now);

			pstmt.setTimestamp(4, ts);
			pstmt.setTimestamp(5, poVO.getPo_ship());
			pstmt.setInt(6, poVO.getPo_qty());
			pstmt.setInt(7, poVO.getPo_price());
			po_price = poVO.getPo_price();
			pstmt.setInt(8, poVO.getPo_pay());
			pstmt.setInt(9, 0);
			pstmt.setString(10, poVO.getPo_comment());
//			pstmt.setInt(11, poVO.getPo_point());
			pstmt.setString(11, poVO.getPo_tel());
			pstmt.setString(12, poVO.getPo_bname());
			pstmt.setString(13, poVO.getPo_home());
			
			pstmt.executeUpdate();
			
			
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				po_id = rs.getInt(1);
			} else {
				System.out.println("沒找到主鍵");
			}
			
			MemberDAO mdao = new MemberDAO();
			mdao.minusWalletByOrders(po_price, po_bmemid, con);
			
			ElectronicWalletDAO edao = new ElectronicWalletDAO();
			edao.walUpdatedByTranOnBmem(po_bmemid, ts, po_price, con);
			
			NoticeDAO ndao = new NoticeDAO();
			
			ndao.StoB_PRE(0, poVO.getPo_bmemid(), po_id, con);
			
			con.commit();
			con.setAutoCommit(true);
			// Handle any SQL errors
		} catch (SQLException se) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Integer count(Integer po_pdid) {

		Integer count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(COUNT_ORDER);

			pstmt.setInt(1, po_pdid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("sum");

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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

	// 更新訂單狀態
	@Override
	public void updateOrder(Integer po_id, Integer po_sta) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer po_bmemid = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(NO_GOLDFLOW);
			pstmt.setInt(1, po_sta);
			pstmt.setInt(2, po_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement("SELECT * FROM mydb.PREORDER where po_id = ?");
			pstmt.setInt(1, po_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				po_bmemid = rs.getInt("po_bmemid");
			}

			NoticeDAO ndao = new NoticeDAO();
			ndao.StoB_PRE(po_sta, po_bmemid, po_id, con);
			
			
			
			con.commit();
			con.setAutoCommit(true);
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	// 拿取會員自己的全部訂單
	@Override
	public List<PoVO> getbuyall(Integer po_bmemid) {

		List<PoVO> list = new ArrayList<PoVO>();
		PoVO poVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SELF_BORDER);

			pstmt.setInt(1, po_bmemid);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				list.add(poVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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

	@Override
	public List<PoVO> getsellall(Integer po_smemid) {
		List<PoVO> list = new ArrayList<PoVO>();
		PoVO poVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SELF_SORDER);

			pstmt.setInt(1, po_smemid);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				list.add(poVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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

	// 確認此商品是否有人下訂單
	@Override
	public boolean haveBuy(Integer po_pdid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean HaveOrder = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(HAVE_BUY_PD);
			pstmt.setInt(1, po_pdid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				HaveOrder = true;
				if (HaveOrder)
					break;
			}
		} catch (SQLException e) {
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return HaveOrder;
	}

	// 更改訂購此商品的全部訂單狀態
	@Override
	public void updateAllSta(Integer po_sta, Integer po_pdid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Change_PD_ALL_STA);
			pstmt.setInt(1, po_sta);
			pstmt.setInt(2, po_pdid);

			pstmt.executeUpdate();
			
			
			
			NoticeDAO ndao = new NoticeDAO();
			List<PoVO> list = getbuyOrder(po_pdid);
			//取出 他的 買家會員編號 訂單編號
						
			for (PoVO listOrder : list) {
				Integer po_bmemid = listOrder.getPo_bmemid();
				Integer po_id = listOrder.getPo_id();
				ndao.StoB_PRE(po_sta, po_bmemid , po_id , con);
			}
			
			
			ndao.BtoS_PRE(po_sta, list.get(0).getPo_smemid(), list.get(0).getPo_pdid(), con);
			
			con.commit();
			con.setAutoCommit(true);
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public List<PoVO> adminGetOD(Integer po_sta , Integer start , Integer rows) {
		
		String adminGetOD = "SELECT * FROM PREORDER WHERE PO_GOLDFLOW = 1 or PO_GOLDFLOW = 2 or PO_GOLDFLOW = 3 or PO_GOLDFLOW = 4 order by po_id DESC LIMIT ? , ?";
		String adminGetone = "SELECT * FROM PREORDER WHERE PO_GOLDFLOW = ? order by po_id DESC LIMIT ? , ?";
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<PoVO> list = new ArrayList<PoVO>();
		PoVO poVO = null;

		try {
			con = ds.getConnection();
			if(po_sta == 0) {
				pstmt = con.prepareStatement(adminGetOD);
				pstmt.setInt(1, start);
				pstmt.setInt(2, rows);
			}else {
				pstmt = con.prepareStatement(adminGetone);
				pstmt.setInt(1, po_sta);
				pstmt.setInt(2, start);
				pstmt.setInt(3, rows);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				list.add(poVO);
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
	public void ODdone(Integer po_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		PoVO poVO = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("SELECT * FROM mydb.PREORDER where po_id = ?");
			pstmt.setInt(1, po_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
			}
			rs.close();
			pstmt.close();
			
			
			pstmt = con.prepareStatement("UPDATE PREORDER SET PO_GOLDFLOW = 3 WHERE PO_ID = ?");
			pstmt.setInt(1, po_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			Long date = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			java.sql.Timestamp ele_time = Timestamp.valueOf(now);
			MemberDAO dao = new MemberDAO();
			dao.addWalletByOrders(poVO.getPo_price(), poVO.getPo_smemid(), con);
			
			ElectronicWalletDAO dao2 = new ElectronicWalletDAO();
			dao2.walUpdatedByTranOnSmem(poVO.getPo_smemid(), ele_time, poVO.getPo_price(), con);
			
			NoticeDAO dao4 = new NoticeDAO();
			dao4.BtoS_PRE(10, poVO.getPo_smemid(), poVO.getPo_id(), con);
			
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
		
		
	}

	@Override
	public void refund(Integer po_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		PoVO poVO = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("SELECT * FROM mydb.PREORDER where po_id = ?");
			pstmt.setInt(1, po_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
			}
			rs.close();
			pstmt.close();
			
			
			pstmt = con.prepareStatement("UPDATE PREORDER SET PO_GOLDFLOW = 4 WHERE PO_ID = ?");
			pstmt.setInt(1, po_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			Long date = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			java.sql.Timestamp ele_time = Timestamp.valueOf(now);
			MemberDAO dao = new MemberDAO();
			dao.addWalletByOrders(poVO.getPo_price(), poVO.getPo_bmemid(), con);
			
			ElectronicWalletDAO dao2 = new ElectronicWalletDAO();
			dao2.refund(poVO.getPo_bmemid(), ele_time, poVO.getPo_price(), con);
			
			NoticeDAO dao4 = new NoticeDAO();
			dao4.StoB_PRE(7, poVO.getPo_bmemid(), poVO.getPo_id(), con);
			
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
		
	}

	@Override
	public PoVO getone(Integer po_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		PoVO poVO = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("SELECT * FROM mydb.PREORDER where po_id = ?");
			pstmt.setInt(1, po_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
			}
				
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
		return poVO;
	}

	@Override
	public List<PoAndPdBean> getSelfBuyOrdersAll(Integer po_bmemid, Integer start, Integer rows) {

		List<PoAndPdBean> list = new ArrayList<PoAndPdBean>();
		PoVO poVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(getSelfOrdersAll);

			pstmt.setInt(1, po_bmemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PoAndPdBean poAndPdBean = new PoAndPdBean();
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				PdVO pdVO= new PdDAO().findByPk(rs.getInt("po_pdid"));
				
				poAndPdBean.setPdVO(pdVO);
				poAndPdBean.setPoVO(poVO);
				
				list.add(poAndPdBean);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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

	@Override
	public List<PoAndPdBean> getSelfBuyOrdersSTA(Integer po_bmemid, Integer po_sta, Integer start, Integer rows) {

		List<PoAndPdBean> list = new ArrayList<PoAndPdBean>();
		PoVO poVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(getSelfOrdersSTA);

			pstmt.setInt(1, po_bmemid);
			pstmt.setInt(2, po_sta);
			pstmt.setInt(3, start);
			pstmt.setInt(4, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PoAndPdBean poAndPdBean = new PoAndPdBean();
				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				PdVO pdVO = new PdDAO().findByPk(rs.getInt("po_pdid"));
				
				poAndPdBean.setPdVO(pdVO);
				poAndPdBean.setPoVO(poVO);
				
				list.add(poAndPdBean);
			}
			

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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

	
	public List<PoVO> getbuyOrder(Integer po_pdid) {
		List<PoVO> list = new ArrayList<PoVO>();
		PoVO poVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PD_ORDER);

			pstmt.setInt(1, po_pdid);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				poVO = new PoVO();
				poVO.setPo_bmemid(rs.getInt("po_bmemid"));
				poVO.setPo_comment(rs.getString("po_comment"));
				poVO.setPo_date(rs.getTimestamp("po_date"));
				poVO.setPo_id(rs.getInt("po_id"));
				poVO.setPo_pay(rs.getInt("po_pay"));
				poVO.setPo_pdid(rs.getInt("po_pdid"));
				poVO.setPo_point(rs.getInt("po_point"));
				poVO.setPo_price(rs.getInt("po_price"));
				poVO.setPo_qty(rs.getInt("po_qty"));
				poVO.setPo_ship(rs.getTimestamp("po_ship"));
				poVO.setPo_smemid(rs.getInt("po_smemid"));
				poVO.setPo_sta(rs.getInt("po_sta"));
				poVO.setPo_bname(rs.getString("po_bname"));
				poVO.setPo_tel(rs.getString("po_tel"));
				poVO.setPo_home(rs.getString("po_home"));
				poVO.setPo_goldflow(rs.getInt("po_goldflow"));
				poVO.setPo_iscom(rs.getInt("po_iscom"));
				list.add(poVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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
	
	
	//買家評論訂單
	@Override
	public void setComment(String po_comment, Integer po_point, Integer po_sta, Integer po_id, Integer po_iscom)  {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SET_COMMENT);
			pstmt.setString(1, po_comment);
			pstmt.setInt(2, po_point);
			pstmt.setInt(3, po_sta);
			pstmt.setInt(4, po_iscom);
			pstmt.setInt(5, po_id);
			
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
	// 更新訂單狀態 (買家給賣家發通知)
	@Override
	public void updateBOrder(Integer po_id, Integer po_sta , Integer po_goldflow) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer po_smemid = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_ORDER);
			pstmt.setInt(1, po_sta);
			pstmt.setInt(2, po_goldflow);
			pstmt.setInt(3, po_id);
			System.out.println(po_sta);
			System.out.println(po_goldflow);
			System.out.println(po_id);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("過了");
			pstmt = con.prepareStatement("SELECT * FROM mydb.PREORDER where po_id = ?");
			pstmt.setInt(1, po_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				po_smemid = rs.getInt("po_smemid");
			}

			NoticeDAO ndao = new NoticeDAO();
			ndao.BtoS_PRE(po_sta, po_smemid, po_id, con);
			
			
			
			con.commit();
			con.setAutoCommit(true);
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
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
	
}
