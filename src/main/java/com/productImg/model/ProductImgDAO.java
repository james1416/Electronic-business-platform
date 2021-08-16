package com.productImg.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;


public class ProductImgDAO implements ProductImgDAO_interface{
	private final String addPIMG="INSERT INTO PRODUCTIMG (PI_PID , PI_IMAGE) VALUES (?,?)";
	private final String getone="select * from PRODUCTIMG where pi_pid = ?";
	private final String getall="select * from PRODUCTIMG where pi_pid = ?";
	private final String del="DELETE FROM PRODUCTIMG WHERE pi_id = ?";
	static DataSource ds = null ; 
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
	public void addPIMG(Integer p_id, List<Part> imgs , Connection con) {
		//最後要關閉
		PreparedStatement pstmt = null ;
		System.out.print(p_id);
		try {
			pstmt = con.prepareStatement(addPIMG);
			for(Part img : imgs) {
				InputStream in = null;
				try {
					in = img.getInputStream();
					if(in.available() == 0) {
						return;
					}
					byte[] pi_image = new byte[in.available()];
					in.read(pi_image);
					in.close();
					pstmt.setInt(1, p_id);
					pstmt.setBytes(2, pi_image);
					pstmt.executeUpdate();
					
				} catch (IOException e) {
					if(con != null) {
						con.rollback();
					}
					e.printStackTrace();
				} finally {
					if(in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println("新增玩了");
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			//不要關到con
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
	public byte[] getone(Integer pi_pid) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ; 
		byte[] img = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, pi_pid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				img = rs.getBytes("PI_image");
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
	
	
	
	public List<ProductImgVO> getall(Integer pi_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null;
		List<ProductImgVO>list =  new ArrayList<ProductImgVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getall);
			pstmt.setInt(1, pi_pid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductImgVO productImgVO= new ProductImgVO();
				productImgVO.setPi_id(rs.getInt("pi_id"));
				productImgVO.setPi_pid(rs.getInt("pi_pid"));
				productImgVO.setPi_image(rs.getBytes("pi_image"));
				list.add(productImgVO);
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
	public void del(Integer pi_id[] , Connection con) {
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(del);
			for(int i = 0 ; i < pi_id.length ; i++) {
				pstmt.setInt(1, pi_id[i]);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
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
	public boolean check(Integer pi_pid) {
		Connection con = null;
		PreparedStatement pstmt = null ; 
		ResultSet rs = null;
		boolean HaveIMG = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getone);
			pstmt.setInt(1, pi_pid);
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

}
