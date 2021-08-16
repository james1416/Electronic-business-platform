package com.productReport.model;

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

import com.member.model.MemberDAO;
import com.product.model.ProductDAO;

public class ProductReportDAO implements ProductReportDAO_interface {
	private final String getallbyADMIN="SELECT * FROM PRODUCTREPORT order by pr_id desc limit ? , ?";
	private final String getallbySTA = "SELECT * FROM PRODUCTREPORT where pr_sta = ? order by pr_id desc limit ? , ?";
	private final String getall="SELECT * FROM PRODUCTREPORT where pr_memid = ?";
	private final String getone="SELECT * FROM PRODUCTREPORT where pr_id = ?";
	private final String del="DELETE FROM PRODUCTREPORT WHERE pr_memid = ? and pr_pid =  ?";
	private final String add="INSERT INTO PRODUCTREPORT (PR_MEMID, PR_PID, PR_CONTENT, PR_DATE) VALUES (?, ?, ?, ?)";
	private final String updata = "UPDATE PRODUCTREPORT SET PR_STA = ? WHERE PR_ID = ? ";
	
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
	public List<ProductReportVO> getall(Integer pr_memid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		List<ProductReportVO> list = new ArrayList<ProductReportVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, pr_memid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductReportVO productReportVO = new ProductReportVO();
				productReportVO.setPr_id(rs.getInt("pr_id"));
				productReportVO.setPr_memid(rs.getInt("pr_memid"));
				productReportVO.setPr_pid(rs.getInt("pr_pid"));
				productReportVO.setPr_content(rs.getString("pr_content"));
				productReportVO.setPr_date(rs.getTimestamp("pr_date"));
				productReportVO.setPr_sta(rs.getInt("pr_sta"));
				list.add(productReportVO);
				
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
	public void add(Integer pr_memid, Integer pr_pid , String pr_content) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, pr_memid);
			pstmt.setInt(2, pr_pid);
			pstmt.setString(3, pr_content);
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

	@Override
	public void del(Integer pr_memid, Integer pr_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(del);
			pstmt.setInt(1, pr_memid);
			pstmt.setInt(2, pr_pid);
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

	@Override
	public List<ProductReportVO> getallbyADMIN(Integer start , Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		List<ProductReportVO> list = new ArrayList<ProductReportVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getallbyADMIN);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductReportVO productReportVO = new ProductReportVO();
				productReportVO.setPr_id(rs.getInt("pr_id"));
				productReportVO.setPr_memid(rs.getInt("pr_memid"));
				productReportVO.setPr_pid(rs.getInt("pr_pid"));
				productReportVO.setPr_content(rs.getString("pr_content"));
				productReportVO.setPr_date(rs.getTimestamp("pr_date"));
				productReportVO.setPr_sta(rs.getInt("pr_sta"));
				list.add(productReportVO);
				
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
	public List<ProductReportVO> getallbySTA(Integer pr_sta, Integer start, Integer rows) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		List<ProductReportVO> list = new ArrayList<ProductReportVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getallbySTA);
			pstmt.setInt(1, pr_sta);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductReportVO productReportVO = new ProductReportVO();
				productReportVO.setPr_id(rs.getInt("pr_id"));
				productReportVO.setPr_memid(rs.getInt("pr_memid"));
				productReportVO.setPr_pid(rs.getInt("pr_pid"));
				productReportVO.setPr_content(rs.getString("pr_content"));
				productReportVO.setPr_date(rs.getTimestamp("pr_date"));
				productReportVO.setPr_sta(rs.getInt("pr_sta"));
				list.add(productReportVO);
				
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
	public void updata(Integer pr_id, Integer pr_sta) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		Integer p_id = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(updata);
			pstmt.setInt(1, pr_sta);
			pstmt.setInt(2, pr_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			if(pr_sta == 1) {
				pstmt = con.prepareStatement(getone);
				pstmt.setInt(1, pr_id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					p_id = rs.getInt("pr_pid");
				}
				new ProductDAO().updataSTA(p_id, con);
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
	public ProductReportVO getone(Integer pr_id) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		ProductReportVO productReportVO = new ProductReportVO();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, pr_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productReportVO.setPr_id(rs.getInt("pr_id"));
				productReportVO.setPr_memid(rs.getInt("pr_memid"));
				productReportVO.setPr_pid(rs.getInt("pr_pid"));
				productReportVO.setPr_content(rs.getString("pr_content"));
				productReportVO.setPr_date(rs.getTimestamp("pr_date"));
				productReportVO.setPr_sta(rs.getInt("pr_sta"));
				
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
		
		return productReportVO;
	}

}
