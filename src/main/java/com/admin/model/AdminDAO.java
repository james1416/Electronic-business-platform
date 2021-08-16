package com.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.admin_authority.model.AdminAuthorityDAO;

public class AdminDAO implements AdminDAO_interface{
	private static final String all = "SELECT * FROM mydb.ADMIN;";
	private static final String add = "insert into mydb.ADMIN(ADMIN_ACC,ADMIN_PW)value(?,?)";
	private static final String updata = "update mydb.ADMIN set ADMIN_ACC = ? , ADMIN_PW = ? , ADMIN_STA = ? where ADMIN_ID = ?";
	private static final String findone = "select * from ADMIN where admin_id = ? ";
	private static final String login = "select * from admin where admin_acc = ? and admin_pw = ?  ";
	private static final String updataLoginLog = "update ADMIN set admin_log = ? where ADMIN_ID = ?";
	private static final String checkAcc = "select * from admin where admin_acc = ?";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<AdminVO> getall() {
		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO admin_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(all);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				admin_VO = new AdminVO();
				admin_VO.setAdmin_id(rs.getInt("ADMIN_ID"));
				admin_VO.setAdmin_acc(rs.getString("ADMIN_ACC"));
				admin_VO.setAdmin_pw(rs.getString("ADMIN_PW"));
				admin_VO.setAdmin_log(rs.getTimestamp("ADMIN_LOG"));
				admin_VO.setAdmin_sta(rs.getInt("ADMIN_STA"));
				list.add(admin_VO);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
	public void add(AdminVO adminVO , Set<Integer> ChengeAutid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer adminid = null ;
		
		try {
			//要找到主鍵的欄位
			String[] col = {"admin_id"}; 
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(add,col);//前面設置的放在這
			pstmt.setString(1, adminVO.getAdmin_acc());
			pstmt.setString(2, adminVO.getAdmin_pw());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				adminid = rs.getInt(1);
				System.out.println("剛剛新增出來的員工的編號"+ adminid);
			}else {
				System.out.println("沒找到");
			}
			rs.close();
			AdminAuthorityDAO aadao = new AdminAuthorityDAO();
			aadao.find(adminid, ChengeAutid, con);
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
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void updata(AdminVO admin_VO , Set<Integer> ChengeAutid) {
		Connection con = null ; 
		PreparedStatement pstmt = null;
		
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(updata);
				
				con.setAutoCommit(false);
				
				pstmt.setString(1, admin_VO.getAdmin_acc());
				pstmt.setString(2, admin_VO.getAdmin_pw());
				pstmt.setInt(3, admin_VO.getAdmin_sta());
				pstmt.setInt(4, admin_VO.getAdmin_id());
				pstmt.executeUpdate();
				
				AdminAuthorityDAO aadao = new AdminAuthorityDAO();
				aadao.find(admin_VO.getAdmin_id(), ChengeAutid, con);
				
				
				con.commit();
				con.setAutoCommit(true);
				System.out.println("成功跑完惹");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null ) {
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
	public AdminVO find(Integer adminid) {
		
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		AdminVO adminVO = new AdminVO();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findone);
			pstmt.setInt(1, adminid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				adminVO.setAdmin_id(rs.getInt("ADMIN_ID"));
				adminVO.setAdmin_acc(rs.getString("ADMIN_ACC"));
				adminVO.setAdmin_pw(rs.getString("ADMIN_PW"));
				adminVO.setAdmin_log(rs.getTimestamp("ADMIN_LOG"));
				adminVO.setAdmin_sta(rs.getInt("ADMIN_STA"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		
		return adminVO;
	}
	@Override
	public AdminVO login(AdminVO adminVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminVO admin = null;
		try {
			con  = ds.getConnection();
			pstmt = con.prepareStatement(login);
			pstmt.setString(1, adminVO.getAdmin_acc());
			pstmt.setString(2, adminVO.getAdmin_pw());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				admin = new AdminVO();
				admin.setAdmin_id(rs.getInt("admin_id"));
				admin.setAdmin_acc(rs.getString("admin_acc"));
				admin.setAdmin_pw(rs.getString("admin_pw"));
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				admin.setAdmin_log(ts);
				admin.setAdmin_sta(rs.getInt("admin_sta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		
		return admin;
	}
	public void updataLoginlog(Integer adminid) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updataLoginLog);
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			pstmt.setTimestamp(1, ts);
			pstmt.setInt(2, adminid);
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
	public Boolean checkAcc(String adminacc) {
		Connection con =null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		Boolean check = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(checkAcc);
			pstmt.setString(1, adminacc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		return check;
	}
	@Override
	public List<AdminVO> search(Map<String, String[]> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO adminVO = null;
		
		try {
			con = ds.getConnection();
			String finalSQL  = "select * from ADMIN "
			+ AdminSearch.get(map)
			+ " order by ADMIN_ID";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdmin_id(rs.getInt("ADMIN_ID"));
				adminVO.setAdmin_acc(rs.getString("ADMIN_ACC"));
				adminVO.setAdmin_pw(rs.getString("ADMIN_PW"));
				adminVO.setAdmin_log(rs.getTimestamp("ADMIN_LOG"));
				adminVO.setAdmin_sta(rs.getInt("ADMIN_STA"));
				list.add(adminVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
}
