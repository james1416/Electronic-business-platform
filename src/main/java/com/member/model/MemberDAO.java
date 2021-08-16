package com.member.model;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class MemberDAO implements MemberDAO_interface{
	//共享一個DS即可
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//重構完畢
	@Override 
	public void insert(MemberVO memberVO) {
		String insert = "INSERT INTO `MEMBER` (MEM_NAME, MEM_UID, MEM_BTH, MEM_SEX, MEM_EMAIL, MEM_TEL, MEM_ADD, MEM_ACC, MEM_PW , MEM_PIC) "
											+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";
			Connection con = null;
			PreparedStatement pstmt = null;
			InputStream is;
			byte[] mem_pic = null;
			try {
				is = new FileInputStream("C:/CFA101G4_workspace/CFA101G4/WebContent/Img/CSS_pic/uploadPic.png");
				try {
					mem_pic = new byte[is.available()];
					is.read(mem_pic);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(insert);		
				pstmt.setString(1, memberVO.getMem_name());
				pstmt.setString(2, memberVO.getMem_uid());
				pstmt.setString(3, memberVO.getMem_bth());
				pstmt.setInt(4, memberVO.getMem_sex());
				pstmt.setString(5, memberVO.getMem_email());
				pstmt.setString(6, memberVO.getMem_tel());
				pstmt.setString(7, memberVO.getMem_add());
				pstmt.setString(8, memberVO.getMem_acc());
				pstmt.setString(9, memberVO.getMem_pw());
				pstmt.setBytes(10, mem_pic);
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
	@Override
	public void update(MemberVO memberVO) {
		String update = "UPDATE `MEMBER` SET MEM_NAME = ?, MEM_EMAIL = ?, MEM_TEL = ?, MEM_SEX = ?, MEM_BTH = ? WHERE MEM_ACC = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(update);
			pstmt.setString(1, memberVO.getMem_name());
			pstmt.setString(2, memberVO.getMem_email());
			pstmt.setString(3, memberVO.getMem_tel());
			pstmt.setInt(4, memberVO.getMem_sex());
			pstmt.setString(5, memberVO.getMem_bth());
			pstmt.setString(6, memberVO.getMem_acc());
			
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
	
	//重構完畢
	@Override
	public List<MemberVO> findMemByPK(Integer mem_id) {
		String getOne = "SELECT * FROM `MEMBER` WHERE MEM_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO mv = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getOne);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mv = new MemberVO();
				mv.setMem_id(rs.getInt("MEM_ID"));
				mv.setMem_name(rs.getString("MEM_NAME"));
				mv.setMem_uid(rs.getString("MEM_UID"));
				mv.setMem_bth(rs.getString("MEM_BTH"));
				mv.setMem_sex(rs.getInt("MEM_SEX"));
				mv.setMem_email(rs.getString("MEM_EMAIL"));
				mv.setMem_tel(rs.getString("MEM_TEL"));
				mv.setMem_add(rs.getString("MEM_ADD"));
				mv.setMem_acc(rs.getString("MEM_ACC"));
				mv.setMem_pw(rs.getString("MEM_PW"));
				mv.setMem_sta(rs.getInt("MEM_STA"));
				mv.setMem_ele(rs.getInt("MEM_ELE"));
				mv.setMem_rp(rs.getInt("MEM_RP"));
				mv.setMem_point(rs.getDouble("MEM_POINT"));
				mv.setMem_pic(rs.getBytes("MEM_PIC"));
				list.add(mv);
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
	//重構完畢
	@Override
	public List<MemberVO> getAll() {
		String getAll = "SELECT * FROM `MEMBER`";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO mv = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mv = new MemberVO();
				mv.setMem_id(rs.getInt("mem_id"));
				mv.setMem_name(rs.getString("mem_name"));
				mv.setMem_uid(rs.getString("mem_uid"));
				mv.setMem_bth(rs.getString("mem_bth"));
				mv.setMem_sex(rs.getInt("mem_sex"));
				mv.setMem_email(rs.getString("mem_email"));
				mv.setMem_tel(rs.getString("mem_tel"));
				mv.setMem_add(rs.getString("mem_add"));
				mv.setMem_acc(rs.getString("mem_acc"));
				mv.setMem_pw(rs.getString("mem_pw"));
				mv.setMem_sta(rs.getInt("mem_sta"));
				mv.setMem_ele(rs.getInt("mem_ele"));
				mv.setMem_rp(rs.getInt("mem_rp"));
				mv.setMem_point(rs.getDouble("mem_point"));
				mv.setMem_pic(rs.getBytes("mem_pic"));
				list.add(mv);
			}
		} catch (SQLException se) {
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
	//重構完畢
	@Override
	public List<MemberVO> checkMember(String mem_acc, String mem_pw) {		
		String check = "SELECT * FROM `MEMBER` WHERE mem_acc = ? && mem_pw = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO mv = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(check);
			pstmt.setString(1, mem_acc);
			pstmt.setString(2, mem_pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mv = new MemberVO();
				mv.setMem_id(rs.getInt("mem_id"));
				mv.setMem_name(rs.getString("mem_name"));
				mv.setMem_uid(rs.getString("mem_uid"));
				mv.setMem_bth(rs.getString("mem_bth"));
				mv.setMem_sex(rs.getInt("mem_sex"));
				mv.setMem_email(rs.getString("mem_email"));
				mv.setMem_tel(rs.getString("mem_tel"));
				mv.setMem_add(rs.getString("mem_add"));
				mv.setMem_acc(rs.getString("mem_acc"));
				mv.setMem_pw(rs.getString("mem_pw"));
				mv.setMem_sta(rs.getInt("mem_sta"));
				mv.setMem_ele(rs.getInt("mem_ele"));
				mv.setMem_rp(rs.getInt("mem_rp"));
				mv.setMem_point(rs.getDouble("mem_point"));
				mv.setMem_pic(rs.getBytes("mem_pic"));
				System.out.println(rs.getString("mem_acc"));
				list.add(mv);
			}
		} catch (SQLException se) {
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
	//重構完畢
	@Override
	public List<MemberVO> checkMemAcc(String mem_acc) {
		String checkAcc = "SELECT * FROM mydb.Member where mem_acc = ?";
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO mv = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(checkAcc);
			pstmt.setString(1, mem_acc);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				mv = new MemberVO();
				mv.setMem_acc(rs.getString(9));
				list.add(mv);
			}
		} catch (SQLException se) {			
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
	
	
	//重構完畢 (BLOB圖片上傳)
	@Override
	public void uploadIconBlob(byte[] mem_pic, String mem_acc) {
		String uploadBlob = "UPDATE `MEMBER` SET mem_pic = ? WHERE mem_acc = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(uploadBlob);
			pstmt.setBytes(1, mem_pic);
			pstmt.setString(2, mem_acc);
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
	//BASE64讀取
	@Override
	public byte[] getIconBlob(String mem_acc) {
		String getIconBlob = "select mem_pic from member where mem_acc = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] pic = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getIconBlob);
			pstmt.setString(1, mem_acc);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InputStream in = rs.getBinaryStream(1);
				BufferedInputStream bis = new BufferedInputStream(in);
				try {
					pic = new byte[bis.available()];
					bis.read(pic);
					bis.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
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
		return pic;
	}
	@Override
	public void InsertDefaultIcon(byte[] mem_pic, String mem_acc) {
		String InsertDI = "UPDATE `MEMBER` SET mem_pic = ? WHERE mem_acc = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(InsertDI);
			pstmt.setBytes(1, mem_pic);
			pstmt.setString(2, mem_acc);
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
	@Override
	public void updateEleWal(Integer ele_memid, Integer ele_mon, Connection con) {
		String updateEleWal = "UPDATE `MEMBER` SET mem_ele = mem_ele+ ? WHERE mem_id=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(updateEleWal);
			pstmt.setInt(1, ele_mon);
			pstmt.setInt(2, ele_memid);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}
	}
	@Override
	public void updateMemPW(Integer mem_id, String mem_pw) {
		String updateMemPW = "UPDATE `MEMBER` SET mem_pw = ? WHERE mem_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updateMemPW);
			pstmt.setString(1, mem_pw);
			pstmt.setInt(2, mem_id);
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
	@Override
	public List<MemberVO> checkMemPW(String mem_acc, String mem_pw) {
		String checkMemPW = "SELECT * FROM `MEMBER` WHERE mem_acc= ? AND mem_pw = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO mv = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(checkMemPW);
			pstmt.setString(1, mem_acc);
			pstmt.setString(2, mem_pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mv = new MemberVO();
				mv.setMem_acc(rs.getString("mem_acc"));
				mv.setMem_pw(rs.getString("mem_pw"));
				list.add(mv);
			}
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
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void addWalletByOrders(Integer od_price, Integer od_smemid, Connection con) {
		String addWalletByOrders = "UPDATE `MEMBER` SET mem_ele = mem_ele + ? WHERE mem_id = ? ";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(addWalletByOrders);
			pstmt.setInt(1, od_price);
			pstmt.setInt(2, od_smemid);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-smem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}
	@Override
	public void minusWalletByOrders(Integer od_price, Integer od_bmemid, Connection con) {
		String minusWalletByOrders="UPDATE `MEMBER` SET mem_ele = mem_ele - ? WHERE mem_id = ? ";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(minusWalletByOrders);
			pstmt.setInt(1, od_price);
			pstmt.setInt(2, od_bmemid);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-bmem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	@Override
	public Integer checkEle(Integer mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ; 
		Integer mem_ele = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM `MEMBER` where mem_id = ?");
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mem_ele = rs.getInt("mem_ele");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if ( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
		return mem_ele;
	}
	@Override
	public void addRP(Integer mem_id , Connection con) {
		PreparedStatement pstmt = null ; 
		
		try {
			pstmt = con.prepareStatement("UPDATE `MEMBER` SET mem_rp = mem_rp + 1 WHERE mem_id = ?");
			pstmt.setInt(1, mem_id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			if ( pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	public void changeMemberStatus(String mem_acc) {
		String changeMemberStatus ="UPDATE `MEMBER` SET mem_sta=1 WHERE mem_sta=0 AND mem_acc=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(changeMemberStatus);
			pstmt.setString(1, mem_acc);
			pstmt.executeUpdate();
	}catch (SQLException se) {
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
	
	
	@Override
	public void changeStaTo0(Integer mem_sta,Integer mem_id) {
		String changeStaTo0="update member set mem_sta = ? where mem_id= ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(changeStaTo0);
			pstmt.setInt(1, mem_sta);
			pstmt.setInt(2, mem_id);
			pstmt.executeUpdate();
		}  catch (SQLException se) {
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
	@Override
	public List<MemberVO> checkEmail(String mem_email) {
		String checkEmail = "SELECT * FROM `MEMBER` WHERE mem_email=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO memberVO = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(checkEmail);
			pstmt.setString(1, mem_email);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getInt("mem_id"));
				memberVO.setMem_acc(rs.getString("mem_acc"));
				list.add(memberVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if ( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	  public void changeStaTo1(String mem_acc) {
	        String changeStaTo1="UPDATE member SET mem_sta=1 WHERE mem_acc = ? ";
	        Connection con = null;
	        PreparedStatement pstmt = null;

	        try {
	            con = ds.getConnection();
	            pstmt=con.prepareStatement(changeStaTo1);
	            pstmt.setString(1, mem_acc);
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

	  @Override
		public MemberVO getOne(Integer memid) {
			String getOne = "SELECT * FROM `MEMBER` WHERE MEM_ID = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MemberVO mv = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(getOne);
				pstmt.setInt(1, memid);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					mv = new MemberVO();
					mv.setMem_id(rs.getInt("MEM_ID"));
					mv.setMem_name(rs.getString("MEM_NAME"));
					mv.setMem_uid(rs.getString("MEM_UID"));
					mv.setMem_bth(rs.getString("MEM_BTH"));
					mv.setMem_sex(rs.getInt("MEM_SEX"));
					mv.setMem_email(rs.getString("MEM_EMAIL"));
					mv.setMem_tel(rs.getString("MEM_TEL"));
					mv.setMem_add(rs.getString("MEM_ADD"));
					mv.setMem_acc(rs.getString("MEM_ACC"));
					mv.setMem_pw(rs.getString("MEM_PW"));
					mv.setMem_sta(rs.getInt("MEM_STA"));
					mv.setMem_ele(rs.getInt("MEM_ELE"));
					mv.setMem_rp(rs.getInt("MEM_RP"));
					mv.setMem_point(rs.getDouble("MEM_POINT"));
					mv.setMem_pic(rs.getBytes("MEM_PIC"));
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
			return mv;
		}
}
