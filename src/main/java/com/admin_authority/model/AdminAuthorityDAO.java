package com.admin_authority.model;

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
import javax.sql.DataSource;

import com.admin.model.AdminVO;
import com.authority.model.AuthorityVO;

public class AdminAuthorityDAO implements AdminAuthorityDAO_interface {
	private final String one = "select * from ADMIN_AUTHORITY aa join ADMIN a on aa.AA_ADMINID = a.ADMIN_ID join AUTHORITY au on au.AUT_ID = aa.AA_AUTID where a.ADMIN_ID = ?";
	private final String all = "select * from ADMIN_AUTHORITY";
	private final String getone = "select * from ADMIN_AUTHORITY where AA_ADMINID = ? ";
	private final String add = "insert into ADMIN_AUTHORITY(AA_ADMINID , AA_AUTID)value(?,?)";
	private final String del = "delete from ADMIN_AUTHORITY  where aa_adminid=? and aa_autid=?";
	private static DataSource ds = null;
	static {
		try {
			InitialContext ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public List<AdminAuthorityVO> getall() {

		List<AdminAuthorityVO> list = new ArrayList<AdminAuthorityVO>();
		AdminAuthorityVO adminAuthorityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(all);
			rs = pstmt.executeQuery();
			while (rs.next()) {
//				AdminVO adminVO = new AdminVO();
//				AuthorityVO authorityVO = new AuthorityVO();
				adminAuthorityVO = new AdminAuthorityVO();
				adminAuthorityVO.setAa_adminid(rs.getInt("aa_adminid"));
				adminAuthorityVO.setAa_autid(rs.getInt("aa_autid"));
//				adminVO.setAdmin_id(rs.getInt("admin_id"));
//				adminVO.setAdmin_acc(rs.getString("admin_acc"));
//				adminAuthorityVO.setAdminVO(adminVO);
//				authorityVO.setAut_id(rs.getInt("aut_id"));
//				authorityVO.setAut_name(rs.getString("aut_name"));
//				authorityVO.setAut_con(rs.getString("aut_con"));
//				adminAuthorityVO.setAuthorityVO(authorityVO);
				list.add(adminAuthorityVO);
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
	public List<AdminAuthorityVO> getone(Integer adminid) {
		List<AdminAuthorityVO> list = new ArrayList<AdminAuthorityVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(one);
			pstmt.setInt(1, adminid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AdminAuthorityVO adminAuthorityVO = new AdminAuthorityVO();
				adminAuthorityVO = new AdminAuthorityVO();
				adminAuthorityVO.setAa_adminid(rs.getInt("aa_adminid"));
				adminAuthorityVO.setAa_autid(rs.getInt("aa_autid"));
//				AdminVO adminVO = new AdminVO();
//				AuthorityVO authorityVO = new AuthorityVO();
//				adminVO.setAdmin_id(rs.getInt("admin_id"));
//				adminVO.setAdmin_acc(rs.getString("admin_acc"));
//				adminAuthorityVO.setAdminVO(adminVO);
//				authorityVO.setAut_id(rs.getInt("aut_id"));
//				authorityVO.setAut_name(rs.getString("aut_name"));
//				authorityVO.setAut_con(rs.getString("aut_con"));
//				adminAuthorityVO.setAuthorityVO(authorityVO);
				list.add(adminAuthorityVO);
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
	public void find(Integer adminid, Set<Integer> ChengeAutid, Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, adminid);
			rs = pstmt.executeQuery();
			
			Set<Integer> HaveAutID = new HashSet<Integer>();
			while (rs.next()) {
				HaveAutID.add(rs.getInt("AA_AUTID"));
			}
			
			add(adminid,ChengeAutid,HaveAutID,con);
			del(adminid,ChengeAutid,HaveAutID,con);
			

		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("在aadao的find被拒絕拉");
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
		}

	}

	public void add(Integer adminid, Set<Integer> ChengeAutid, Set<Integer> HaveAutID, Connection con) {
		PreparedStatement pstmt = null;

		try {
			con.prepareStatement(add);
			Set<Integer> check = new HashSet<Integer>();
			check.clear();
			check.addAll(ChengeAutid);
			check.removeAll(HaveAutID);
			pstmt = con.prepareStatement(add);
			for (int i = 0; i < check.size(); i++) {
				pstmt.setInt(1, adminid);
				pstmt.setInt(2, (Integer) check.toArray()[i]);
				pstmt.executeUpdate();
			}
			System.out.println("本身沒有！但更改過後會\"有\"的功能為" + check);
			System.out.println("也就是說" + check + "必須新增");
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
					System.out.println("在aadao的add被拒絕拉");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void del(Integer adminid, Set<Integer> ChengeAutid, Set<Integer> HaveAutID, Connection con) {
		PreparedStatement pstmt = null;

		try {
			con.prepareStatement(del);
			Set<Integer> check = new HashSet<Integer>();
			
			pstmt = con.prepareStatement(add);
			
			check.clear();
			check.addAll(HaveAutID);
			check.removeAll(ChengeAutid);
			System.out.println("本身有！但更改過後卻不會 \"擁有\" 的功能為" + check);
			System.out.println("也就是說" + check + "必須移除");
			pstmt = con.prepareStatement(del);
			for (int i = 0; i < check.size(); i++) {
				pstmt.setInt(1, adminid);
				pstmt.setInt(2, (Integer) check.toArray()[i]);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
					System.out.println("在aadao的del被拒絕拉");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
