package com.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import com.cartList.model.CartListVO;
import com.productImg.model.ProductImgDAO;

public class ProductDAO implements ProductDAO_interface{
	private final String all="SELECT * FROM PRODUCT order by P_id desc";
	private final String findTotalCount = "SELECT count(*) FROM PRODUCT where p_sta = 1 ";
	private final String getSelf="SELECT * FROM mydb.PRODUCT where p_memid = ?";
	private final String findByPage="SELECT * FROM PRODUCT where p_sta = 1 limit ? , ?";
	private final String findSelfPDbyPage = "SELECT * FROM PRODUCT where p_memid = ? limit ? , ?";
	private final String getPD="SELECT * FROM mydb.PRODUCT where p_id = ?";
	private final String updataPD = "update mydb.PRODUCT set  P_CGID = ? ,P_NAME = ? , P_PRICE = ? ,P_STOCK = ? ,P_DESC = ? , P_STA = ? where P_ID = ?";
	private final String PDSell = "UPDATE PRODUCT SET P_STOCK = ?, P_SL = ? WHERE P_ID = ?";
	private final String add = "insert into mydb.PRODUCT(P_CGID, P_NAME , P_PRICE , P_STOCK , P_DESC ,P_MEMID )value(?,?,?,?,?,?)";
	private final String updataSTA = "UPDATE PRODUCT SET P_STA = ? WHERE P_ID = ? ";

	
	
	private static DataSource ds = null;
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<ProductVO> getall() {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<ProductVO> list = new ArrayList<ProductVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(all);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ProductVO productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
				list.add(productVO);
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
	public List<ProductVO> getSelf(Integer p_memid) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<ProductVO> list = new ArrayList<ProductVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getSelf);
			pstmt.setInt(1, p_memid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ProductVO productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
				list.add(productVO);
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
	public ProductVO findPD(Integer p_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null;
		ResultSet rs = null ; 
		ProductVO productVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getPD);
			pstmt.setInt(1, p_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
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
		
		return productVO;	
		
		
	}

	@Override
	public void updataPD(ProductVO productVO , List<Part> imgs, Integer[] pi_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(updataPD);
			pstmt.setInt(1, productVO.getP_cgid());
			pstmt.setString(2, productVO.getP_name());
			pstmt.setInt(3, productVO.getP_price());
			pstmt.setInt(4, productVO.getP_stock());
			pstmt.setString(5, productVO.getP_desc());
			pstmt.setInt(6, productVO.getP_sta());
			pstmt.setInt(7, productVO.getP_id());
			pstmt.executeUpdate();
			Integer p_id = productVO.getP_id();
			ProductImgDAO pidao = new ProductImgDAO();
			pidao.del(pi_id, con);
			pidao.addPIMG(p_id, imgs, con);
			
			
			con.commit();
			con.setAutoCommit(true);
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
	public void addPD(ProductVO productVO , List<Part> imgs) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		Integer p_id = null;
		try {
			// 主要欄位
			String[] col = {"p_id"}; //塞的是 AI 出來的 PK
			
			con = ds.getConnection();
			// 把自動送出關掉
			con.setAutoCommit(false);
			// 下完sql語句後 要在, 放上 你上面的那個col
			pstmt = con.prepareStatement(add , col);
			pstmt.setInt(1, productVO.getP_cgid());
			pstmt.setString(2, productVO.getP_name());
			pstmt.setInt(3, productVO.getP_price());
			pstmt.setInt(4, productVO.getP_stock());
			pstmt.setString(5, productVO.getP_desc());
			pstmt.setInt(6, productVO.getP_memid());
			pstmt.executeUpdate();
			
			
			// 可以查詢我們那一個新生成的PK
			rs = pstmt.getGeneratedKeys();
			
			
			if(rs.next()) {
				p_id = rs.getInt(1);
			}else {
				System.out.println("沒找到主鍵");
			}
			rs.close();
			ProductImgDAO pidao = new ProductImgDAO();
			pidao.addPIMG(p_id, imgs , con);
			
			
			
			con.commit();
			con.setAutoCommit(true);
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
	}

	@Override
	public Integer findPDforAJAX(Integer p_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ; 
		Integer p_stock = 0 ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getPD);
			pstmt.setInt(1, p_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				p_stock = rs.getInt("p_stock");
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
		
		
		return p_stock;
	}

	@Override
	public List<ProductVO> getCartPD(List<CartListVO> cartList) {
		List<ProductVO> productVOs = new ArrayList<ProductVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ; 
		
		try {
			con = ds.getConnection();
			for (int i = 0; i < cartList.size(); i++) {
				pstmt = con.prepareStatement(getPD);
				pstmt.setInt(1, cartList.get(i).getCl_pid());
				rs = pstmt.executeQuery();
					while(rs.next()) {
						ProductVO productVO = new ProductVO();
						productVO.setP_id(rs.getInt("p_id"));
						productVO.setP_memid(rs.getInt("p_memid"));
						productVO.setP_cgid(rs.getInt("p_cgid"));
						productVO.setP_name(rs.getString("p_name"));
						productVO.setP_price(rs.getInt("p_price"));
						productVO.setP_stock(cartList.get(i).getCl_pq());
						productVO.setP_sl(rs.getInt("p_sl"));
						productVO.setP_desc(rs.getString("p_desc"));
						productVO.setP_sta(rs.getInt("p_sta"));
						productVOs.add(productVO);
					}
			
			
			
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
		return productVOs;
	}

	@Override
	public void PDSell(Integer p_id, Integer p_stock, Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer stock = null ;
		Integer sl = null;
		try {
			pstmt = con.prepareStatement(getPD);
			pstmt.setInt(1, p_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				stock = rs.getInt("p_stock");
				sl = rs.getInt("p_sl");
			}
			pstmt.close();
			pstmt = con.prepareStatement(PDSell);
			pstmt.setInt(1, stock-p_stock);
			pstmt.setInt(2, sl+p_stock);
			pstmt.setInt(3, p_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
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
		}
	}

	@Override
	public Integer findTotalCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet  rs = null;
		Integer count = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findTotalCount);
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
		
		return count;
	}

	@Override
	public List<ProductVO> findByPage(Integer start, Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<ProductVO> list = new ArrayList<ProductVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findByPage);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ProductVO productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
				list.add(productVO);
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
	public List<ProductVO> findMyPDbyPage(Integer mem_id, Integer start, Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<ProductVO> list = new ArrayList<ProductVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findSelfPDbyPage);
			pstmt.setInt(1, mem_id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ProductVO productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
				list.add(productVO);
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
	public void updataSTA(Integer p_id, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(updataSTA);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, p_id);
			pstmt.executeUpdate();
			
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
		
		}
	}

	@Override
	public List<ProductVO> searchMyPDbyPage(String search, Integer p_cgid, Integer start, Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<ProductVO> list = new ArrayList<ProductVO>();
		String sql = "";
		
		
		
		try {
			con = ds.getConnection();
			
			if(p_cgid == 0 ) {
				sql = "SELECT * FROM mydb.PRODUCT Where p_name LIKE '%"+search+"%' and p_sta = 1 limit ?,?";
			}else {
				sql = "SELECT * FROM mydb.PRODUCT Where p_name LIKE '%"+search+"%' and p_cgid = ?  and p_sta = 1 limit ?,?";
			}
			
			
			
			pstmt = con.prepareStatement(sql);
			
			if(p_cgid == 0 ) {
				pstmt.setInt(1, start);
				pstmt.setInt(2, rows);
			}else {
				pstmt.setInt(1, p_cgid);
				pstmt.setInt(2, start);
				pstmt.setInt(3, rows);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				ProductVO productVO = new ProductVO();
				productVO.setP_id(rs.getInt("p_id"));
				productVO.setP_memid(rs.getInt("p_memid"));
				productVO.setP_cgid(rs.getInt("p_cgid"));
				productVO.setP_name(rs.getString("p_name"));
				productVO.setP_price(rs.getInt("p_price"));
				productVO.setP_stock(rs.getInt("p_stock"));
				productVO.setP_sl(rs.getInt("p_sl"));
				productVO.setP_desc(rs.getString("p_desc"));
				productVO.setP_sta(rs.getInt("p_sta"));
				list.add(productVO);
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
	public Integer searchCount(String search, Integer p_cgid) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		String sql = "";
		Integer count = 0;
		
		
		
		try {
			con = ds.getConnection();
			
			if(p_cgid == 0 ) {
				sql = "SELECT count(*) FROM mydb.PRODUCT Where p_name LIKE '%"+search+"%'";
			}else {
				sql = "SELECT count(*) FROM mydb.PRODUCT Where p_name LIKE '%"+search+"%' and p_cgid = ? ";
			}
			
			
			
			pstmt = con.prepareStatement(sql);
			
			if(p_cgid != 0 ) {
				pstmt.setInt(1, p_cgid);
			}else {
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				count = rs.getInt("count(*)");
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
		
		return count;
	}

	@Override
	public Set<String> inputPoint(String input, Integer p_cgid) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		String sql = "";
		Set<String> set = new HashSet<String>();
		
		try {
			con = ds.getConnection();
			
			if(p_cgid == 0 ) {
				sql = "SELECT * FROM PRODUCT Where p_name LIKE '%"+input+"%'";
			}else {
				sql = "SELECT * FROM PRODUCT Where p_name LIKE '%"+input+"%' and p_cgid = ? ";
			}
			
			
			
			pstmt = con.prepareStatement(sql);
			
			if(p_cgid != 0 ) {
				pstmt.setInt(1, p_cgid);
			}else {
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				set.add(rs.getString("p_name")) ;
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
		
		return set;
	}
}
