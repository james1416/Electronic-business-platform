package com.authority.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class AuthorityDAO implements AuthorityDAO_interface{
	private static final String all = "SELECT * FROM mydb.AUTHORITY";
	private static final String findone = "SELECT * FROM mydb.AUTHORITY where AUT_ID = ?";
	private static final String add = "insert into AUTHORITY(AUT_NAME,AUT_CON)value(?,?)";
	private static final String updata = "update AUTHORITY set AUT_NAME = ? ,AUT_CON = ? where  AUT_ID = ?";
	private static DataSource ds = null; 
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds =(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<AuthorityVO> getall() {
		List<AuthorityVO> list = new ArrayList<AuthorityVO>();
		AuthorityVO authority_VO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(all);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				authority_VO = new AuthorityVO();
				authority_VO.setAut_id(rs.getInt("aut_id"));
				authority_VO.setAut_name(rs.getString("aut_name"));
				authority_VO.setAut_con(rs.getString("aut_con"));
				list.add(authority_VO);
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

	@Override
	public void add(AuthorityVO authorityVO) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setString(1, authorityVO.getAut_name());
			pstmt.setString(2, authorityVO.getAut_con());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	@Override
	public void updata(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null ; 

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);
			pstmt.setString(1, authorityVO.getAut_name());
			pstmt.setString(2, authorityVO.getAut_con());
			pstmt.setInt(3, authorityVO.getAut_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	@Override
	public AuthorityVO findone(Integer aut_id) {
		Connection con = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		AuthorityVO authorityVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(findone);
			pstmt.setInt(1, aut_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				authorityVO = new AuthorityVO();
				authorityVO.setAut_id(rs.getInt("AUT_ID"));
				authorityVO.setAut_name(rs.getString("AUT_NAME"));
				authorityVO.setAut_con(rs.getString("AUT_CON"));
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
		return authorityVO;
	}
}

