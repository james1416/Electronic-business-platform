package com.pd.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import com.pi.model.PiDAO;

public class PdDAO implements PdDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PREPRODUCT (pd_smemid,pd_pcid,pd_name,pd_price,pd_no,pd_desc,pd_sdate,pd_edate,pd_spdate,pd_sta) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// 會員修改商品資訊
	private static final String UPDATE = "UPDATE PREPRODUCT set pd_smemid=?, pd_pcid=?, pd_name=? ,pd_price=?, pd_no=?, pd_desc=?, pd_sdate=?, pd_edate=?, pd_spdate=?, pd_sta=? where pd_id=?";
	private static final String GET_ONE_PD = "SELECT pd_id,pd_smemid,pd_pcid,pd_name,pd_price,pd_no,pd_desc,pd_sdate,pd_edate,pd_spdate,pd_sta FROM PREPRODUCT WHERE pd_id=? ";
	private static final String GET_ALL_PD = "SELECT pd_id,pd_smemid,pd_pcid,pd_name,pd_price,pd_no,pd_desc,pd_sdate,pd_edate,pd_spdate,pd_sta FROM PREPRODUCT";
	private static final String GET_PDID = "SELECT pd_id FROM PREPRODUCT WHERE  (pd_smemid=? AND pd_name=? AND pd_desc=? AND pd_sdate=?) ";
	private static final String GET_SELF_ALL_PD = "SELECT * FROM PREPRODUCT WHERE pd_smemid = ?";
	// 更改商品狀態
	private static final String UPDATE_STA = "UPDATE PREPRODUCT SET pd_sta=? WHERE pd_id=?";

	private static final String GET_PD_NO = "SELECT pd_no FROM PREPRODUCT WHERE pd_id=?";
	
	private static final String FIND_MY_PD_PAGE =
			"SELECT * FROM PREPRODUCT WHERE pd_smemid =? limit ? , ?";
	private static final String OFF_PD=
			"UPDATE PREPRODUCT set pd_sta = ? where pd_id= ?";
	
	@Override
	// 新增一個商品
	public Integer addpd(PdVO pdVO, List<Part> imgs) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer pd_id = null;
		try {
			String[] col = { "pd_id" };

			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(INSERT_STMT, col);
			pstmt.setInt(1, pdVO.getPd_smemid());
			pstmt.setInt(2, pdVO.getPd_pcid());
			pstmt.setString(3, pdVO.getPd_name());
			pstmt.setInt(4, pdVO.getPd_price());
			pstmt.setInt(5, pdVO.getPd_no());
			pstmt.setString(6, pdVO.getPd_desc());
			pstmt.setTimestamp(7, pdVO.getPd_sdate());
			pstmt.setTimestamp(8, pdVO.getPd_edate());
			pstmt.setTimestamp(9, pdVO.getPd_spdate());
			pstmt.setInt(10, pdVO.getPd_sta());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				pd_id = rs.getInt(1);
			} else {
				System.out.println("沒找到主鍵");
			}
			rs.close();
			System.out.println("主見"+pd_id);
			PiDAO pidao = new PiDAO();
			pidao.addPIMG(pd_id, imgs, con);

			con.commit();
			con.setAutoCommit(true);
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
		return pd_id;
	}

	@Override
	public void update(PdVO pdVO, List<Part> imgs, Integer[] pi_imgid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, pdVO.getPd_smemid());
			pstmt.setInt(2, pdVO.getPd_pcid());
			pstmt.setString(3, pdVO.getPd_name());
			pstmt.setInt(4, pdVO.getPd_price());
			pstmt.setInt(5, pdVO.getPd_no());
			pstmt.setString(6, pdVO.getPd_desc());
			pstmt.setTimestamp(7, pdVO.getPd_sdate());
			pstmt.setTimestamp(8, pdVO.getPd_edate());
			pstmt.setTimestamp(9, pdVO.getPd_spdate());
			pstmt.setInt(10, pdVO.getPd_sta());
			pstmt.setInt(11, pdVO.getPd_id());
			pstmt.executeUpdate();

			Integer pd_id = pdVO.getPd_id();
			PiDAO pidao = new PiDAO();
			pidao.del(pi_imgid, con);
			pidao.addPIMG(pd_id, imgs, con);

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
	// 利用商品編號查詢商品
	public PdVO findByPk(Integer pd_id) {

		PdVO pdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PD);

			pstmt.setInt(1, pd_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				pdVO = new PdVO();
				pdVO.setPd_id(rs.getInt("pd_id"));
				pdVO.setPd_smemid(rs.getInt("pd_smemid"));
				pdVO.setPd_pcid(rs.getInt("pd_pcid"));
				pdVO.setPd_name(rs.getString("pd_name"));
				pdVO.setPd_price(rs.getInt("pd_price"));
				pdVO.setPd_no(rs.getInt("pd_no"));
				pdVO.setPd_desc(rs.getString("pd_desc"));
				pdVO.setPd_sdate(rs.getTimestamp("pd_sdate"));
				pdVO.setPd_edate(rs.getTimestamp("pd_edate"));
				pdVO.setPd_spdate(rs.getTimestamp("pd_spdate"));
				pdVO.setPd_sta(rs.getInt("pd_sta"));
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
		return pdVO;
	}

	@Override
	// 顯示所有商品
	public List<PdVO> getall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PdVO> list = new ArrayList<PdVO>();
		PdVO pdVO = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PD);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				pdVO = new PdVO();

				pdVO.setPd_id(rs.getInt("pd_id"));
				pdVO.setPd_smemid(rs.getInt("pd_smemid"));
				pdVO.setPd_pcid(rs.getInt("pd_pcid"));
				pdVO.setPd_name(rs.getString("pd_name"));
				pdVO.setPd_price(rs.getInt("pd_price"));
				pdVO.setPd_no(rs.getInt("pd_no"));
				pdVO.setPd_desc(rs.getString("pd_desc"));
				pdVO.setPd_sdate(rs.getTimestamp("pd_sdate"));
				pdVO.setPd_edate(rs.getTimestamp("pd_edate"));
				pdVO.setPd_spdate(rs.getTimestamp("pd_spdate"));
				pdVO.setPd_sta(rs.getInt("pd_sta"));

				list.add(pdVO);

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
		return list;
	}

	@Override
	// 查詢商品編號 為了給圖片
	public PdVO findPdid(Integer pd_smemid, String pd_name, String pd_desc, Timestamp pd_sdate) {

		PdVO pdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PDID);

			pstmt.setInt(1, pd_smemid);
			pstmt.setString(2, pd_name);
			pstmt.setString(3, pd_desc);
			pstmt.setTimestamp(4, pd_sdate);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				pdVO = new PdVO();
				pdVO.setPd_id(rs.getInt("pd_id"));
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
		return pdVO;

	}

	@Override
	// 拿到會員個人商品
	public List<PdVO> getSelfAll(Integer pd_smemid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PdVO pdVO = null;
		List<PdVO> list = new ArrayList<PdVO>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SELF_ALL_PD);
			pstmt.setInt(1, pd_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				pdVO = new PdVO();

				pdVO.setPd_id(rs.getInt("pd_id"));
				pdVO.setPd_smemid(rs.getInt("pd_smemid"));
				pdVO.setPd_pcid(rs.getInt("pd_pcid"));
				pdVO.setPd_name(rs.getString("pd_name"));
				pdVO.setPd_price(rs.getInt("pd_price"));
				pdVO.setPd_no(rs.getInt("pd_no"));
				pdVO.setPd_desc(rs.getString("pd_desc"));
				pdVO.setPd_sdate(rs.getTimestamp("pd_sdate"));
				pdVO.setPd_edate(rs.getTimestamp("pd_edate"));
				pdVO.setPd_spdate(rs.getTimestamp("pd_spdate"));
				pdVO.setPd_sta(rs.getInt("pd_sta"));

				list.add(pdVO);

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
		return list;
	}

	@Override
	public void update_sta(Integer pd_sta, Integer pd_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_STA);
			pstmt.setInt(1, pd_sta);
			pstmt.setInt(2, pd_id);

			pstmt.executeUpdate();
			System.out.println("更改成功");

			
			
			
			
			
			
			
			
			
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
	public Integer findNumber(Integer pd_id) {

		Integer pd_no = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PD_NO);

			pstmt.setInt(1, pd_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				pd_no = (rs.getInt("pd_no"));
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
		return pd_no;
	}
	// Find SelfPd By AJAX 
	@Override
	public List<PdVO> findMyPDbyPage(Integer pd_smemid, Integer start, Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<PdVO> list = new ArrayList<PdVO>();
		PdVO pdVO = new PdVO();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_MY_PD_PAGE);
			pstmt.setInt(1, pd_smemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				pdVO = new PdVO();

				pdVO.setPd_id(rs.getInt("pd_id"));
				pdVO.setPd_smemid(rs.getInt("pd_smemid"));
				pdVO.setPd_pcid(rs.getInt("pd_pcid"));
				pdVO.setPd_name(rs.getString("pd_name"));
				pdVO.setPd_price(rs.getInt("pd_price"));
				pdVO.setPd_no(rs.getInt("pd_no"));
				pdVO.setPd_desc(rs.getString("pd_desc"));
				pdVO.setPd_sdate(rs.getTimestamp("pd_sdate"));
				pdVO.setPd_edate(rs.getTimestamp("pd_edate"));
				pdVO.setPd_spdate(rs.getTimestamp("pd_spdate"));
				pdVO.setPd_sta(rs.getInt("pd_sta"));

				list.add(pdVO);
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
	public void offPd(Integer pd_id, Integer pd_sta) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			
			con = ds.getConnection();

			pstmt = con.prepareStatement(OFF_PD);
			pstmt.setInt(1, pd_sta);
			pstmt.setInt(2, pd_id);

			pstmt.executeUpdate();

			

			
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
	
}
