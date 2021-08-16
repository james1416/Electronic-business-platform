package com.txreport.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.MemberDAO;

public class TXReportDAO implements TXReportDAO_interface{

	private String getAll = "SELECT * FROM TXREPORT order by tr_id desc limit ? , ?";
	private String getAllbySTA = "SELECT * FROM mydb.TXREPORT where tr_sta = ?  order by tr_id desc limit ? , ?";
	private String getSelf = "";
	private String Check = "SELECT * FROM mydb.TXREPORT where tr_odid = ?";
	private String add = "INSERT INTO TXREPORT (TR_ODID, TR_reporter, TR_reported, TR_CONTENT, TR_DATE ) VALUES (?,?,?,?,?)";
	private String updata = "UPDATE TXREPORT SET TR_STA = ? WHERE TR_ID = ?";
	private String getone = "SELECT * FROM mydb.TXREPORT where tr_id = ?";
	
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
	public List<TXReportVO> getSelf(Integer tr_reporter) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<TXReportVO> list = new ArrayList<TXReportVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getSelf);
			pstmt.setInt(1, tr_reporter);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TXReportVO txReportVO = new TXReportVO();
				txReportVO.setTr_id(rs.getInt("tr_id"));
				txReportVO.setTr_odid(rs.getInt("tr_odid"));
				txReportVO.setTr_reporter(rs.getInt("tr_reporter"));
				txReportVO.setTr_reported(rs.getInt("tr_reported"));
				txReportVO.setTr_content(rs.getString("tr_content"));
				txReportVO.setTr_date(rs.getTimestamp("tr_date"));
				txReportVO.setTr_sta(rs.getInt("tr_sta"));
				list.add(txReportVO);
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
	public void add(TXReportVO txReportVO) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setInt(1, txReportVO.getTr_odid());
			pstmt.setInt(2, txReportVO.getTr_reporter());
			pstmt.setInt(3, txReportVO.getTr_reported());
			pstmt.setString(4, txReportVO.getTr_content());
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
	public void updata(Integer tr_id , Integer tr_sta) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null;
		Integer mem_id = null;
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(updata);
			pstmt.setInt(1, tr_sta);
			pstmt.setInt(2, tr_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			
			if(tr_sta == 1) {
				pstmt = con.prepareStatement(getone);
				pstmt.setInt(1, tr_id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					mem_id = rs.getInt("TR_REPORTED");
				}
				
				new MemberDAO().addRP(mem_id, con);
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
	public List<TXReportVO> getAll(Integer start , Integer rows) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<TXReportVO> list = new ArrayList<TXReportVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TXReportVO txReportVO = new TXReportVO();
				txReportVO.setTr_id(rs.getInt("tr_id"));
				txReportVO.setTr_odid(rs.getInt("tr_odid"));
				txReportVO.setTr_reporter(rs.getInt("tr_reporter"));
				txReportVO.setTr_reported(rs.getInt("tr_reported"));
				txReportVO.setTr_content(rs.getString("tr_content"));
				txReportVO.setTr_date(rs.getTimestamp("tr_date"));
				txReportVO.setTr_sta(rs.getInt("tr_sta"));
				list.add(txReportVO);
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
	public List<TXReportVO> getAllbySTA(Integer tr_sta, Integer start , Integer rows) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		List<TXReportVO> list = new ArrayList<TXReportVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAllbySTA);
			pstmt.setInt(1, tr_sta);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TXReportVO txReportVO = new TXReportVO();
				txReportVO.setTr_id(rs.getInt("tr_id"));
				txReportVO.setTr_odid(rs.getInt("tr_odid"));
				txReportVO.setTr_reporter(rs.getInt("tr_reporter"));
				txReportVO.setTr_reported(rs.getInt("tr_reported"));
				txReportVO.setTr_content(rs.getString("tr_content"));
				txReportVO.setTr_date(rs.getTimestamp("tr_date"));
				txReportVO.setTr_sta(rs.getInt("tr_sta"));
				list.add(txReportVO);
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
	public TXReportVO getone(Integer tr_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		TXReportVO txReportVO = new TXReportVO();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, tr_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				txReportVO.setTr_id(rs.getInt("tr_id"));
				txReportVO.setTr_odid(rs.getInt("tr_odid"));
				txReportVO.setTr_reporter(rs.getInt("tr_reporter"));
				txReportVO.setTr_reported(rs.getInt("tr_reported"));
				txReportVO.setTr_content(rs.getString("tr_content"));
				txReportVO.setTr_date(rs.getTimestamp("tr_date"));
				txReportVO.setTr_sta(rs.getInt("tr_sta"));
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
		
		return txReportVO;
	}

	@Override
	public Boolean check(Integer tr_odid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		Boolean point = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Check);
			pstmt.setInt(1, tr_odid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				point = true;
				break;
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
		
		return point;
	}
	
}
