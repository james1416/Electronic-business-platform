package com.pi.model;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;


public class PiDAO implements PiDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_IMG = "INSERT INTO PREIMAGE (pi_pdid,pi_desc) VALUES (?,?)";
	private static final String GET_ONE_PDIMG= "SELECT *  FROM PREIMAGE WHERE pi_pdid = ?"; 
	private static final String GET_ALL="SELECT * FROM PREIMAGE WHERE pi_pdid = ?";
	private static final String DELETE="DELETE FROM PREIMAGE WHERE pi_imgid= ?";
	@Override
	public void addPIMG(Integer pd_id, List<Part> imgs, Connection con) {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_IMG);
			for (Part img : imgs) {
				InputStream in = null;
				System.out.println("到DAO曾"+img.getName());
				try {

					in = img.getInputStream();
					System.out.println(in.available());
					if (in.available() == 0) {
						System.out.println("怎進來了");
						return;
					} 
					byte[] pi_desc = new byte[in.available()];
					in.read(pi_desc);
					in.close();
					System.out.println("讀完圖片");
					pstmt.setInt(1, pd_id);
					System.out.println(pd_id);
					pstmt.setBytes(2, pi_desc);
					System.out.println(pi_desc);
					pstmt.executeUpdate();
					System.out.println("送出新增");
				} catch (IOException e) {
					if(con != null) {
						con.rollback();
					}
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			System.out.println("新增成功");
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			// 記得不要關 con回到商品新增時再關閉
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
	public boolean check(Integer pd_id) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null;
		boolean HaveIMG = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PDIMG);
			pstmt.setInt(1, pd_id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				HaveIMG = true;
				if(HaveIMG)
					break;
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
		return HaveIMG;	
	}




	@Override
	public List<PiVO> getall(Integer pi_pdid) {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PiVO> list = new ArrayList<PiVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setInt(1,pi_pdid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PiVO piVO = new PiVO();
				piVO.setPi_pdid(rs.getInt("pi_pdid"));
				piVO.setPi_imgid(rs.getInt("pi_imgid"));
				piVO.setPi_desc(rs.getBytes("pi_desc"));
				list.add(piVO);
				System.out.println("拿到");
			}
		}catch (SQLException e) {
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
	public void del(Integer pi_imgid[] , Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			for(int i = 0 ; i < pi_imgid.length ; i++) {
				pstmt.setInt(1, pi_imgid[i]);
				pstmt.executeUpdate();
			}
		
		}catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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


	@Override
	public byte[] getone(Integer pi_pdid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ; 
		byte[] img = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PDIMG);
			pstmt.setInt(1, pi_pdid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				img = rs.getBytes("pi_desc");
				break;
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
		
		return img;
	}
	
	



}
