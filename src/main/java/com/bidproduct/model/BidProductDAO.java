package com.bidproduct.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;


import com.notice.model.NoticeDAO;
import com.bpdetail.model.BpDetailDAO;
import com.bpdetail.model.BpDetailVO;
import com.bpimage.model.BpImageDAO;
import com.electronicwallet.model.ElectronicWalletDAO;
import com.member.model.MemberDAO;

public class BidProductDAO implements BidProductDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private final String INSERT_STMT = "INSERT INTO BID_PRODUCT(BP_SMEMID,BP_BPCID,BP_NAME,BP_STIME,BP_ETIME,BP_SPRICE,BP_INC,BP_DESC) VALUES(?,?,?,?,?,?,?,?)";
	private final String GET_ALL_STMT = "SELECT * FROM BID_PRODUCT";
	private final String GET_ONE_STMT_BY_BIDNO = "SELECT * FROM BID_PRODUCT WHERE BP_ID = ?";
	private final String CHANGE_BP_STATUS = "UPDATE BID_PRODUCT SET BP_STA = ? WHERE BP_ID = ?";
	private final String CHANGE_BP_SHSTA = "UPDATE BID_PRODUCT SET BP_SHSTA = ? WHERE BP_ID =?";
	private final String UPDATE = "UPDATE BID_PRODUCT SET BP_BPCID = ?, BP_NAME = ?,BP_STIME = ?,BP_ETIME = ?,BP_SPRICE = ?, BP_INC = ?, BP_IMDT = ?, BP_SHIP = ?, BP_DESC = ? WHERE BP_ID = ?";
	private final String DELETE = "DELETE FROM BID_PRODUCT WHERE BP_ID =?";
	private final String GET_BP_ID = "SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ? AND BP_BPCID = ? AND BP_NAME = ? AND BP_STIME = ? AND BP_ETIME = ? AND BP_SPRICE = ? AND BP_INC = ? AND BP_IMDT = ? AND BP_SHIP = ? AND BP_DESC = ? ";
	private final String GET_MY_BID = "SElECT * FROM BID_PRODUCT WHERE BP_SMEMID = ?";
	private final String UPDATE_BP_TPRICE = "UPDATE BID_PRODUCT SET BP_TPRICE = ? WHERE BP_ID = ?";
	private final String UPDATE_BP_BMEMID = "UPDATE BID_PRODUCT SET BP_BMEMID = ?, BP_TO = ? ,BP_SHSTA = ? WHERE BP_ID = ?";
	private final String GET_BP_WIN_BID ="SELECT * FROM BID_PRODUCT WHERE BP_BMEMID = ?";
	private final String RECEIVE ="UPDATE BID_PRODUCT SET BP_BNAME =?, BP_ADD =?,BP_TEL = ?,BP_SHSTA=? WHERE BP_ID=?";
	private final String FINDMYBIDBYPAGE = "SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ?  LIMIT ?,?";
	private final String EFFBID = "SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ? AND BP_STA = 2 AND BP_TO > 0 AND BP_SHSTA = 2";
	private final String CHANGESHSTA = "UPDATE BID_PRODUCT SET BP_SHSTA = ? WHERE BP_ID = ?";
	private final String TOBECONFIRMED ="SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ? AND BP_STA = 2 AND BP_TO > 0 AND BP_SHSTA = 3";
	private final String COMPLETEBIDB = "SELECT * FROM BID_PRODUCT WHERE BP_BMEMID = ? AND BP_SHSTA = 4 LIMIT ?,?";
	private final String COMPLETEBIDS = "SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ? AND BP_SHSTA = 4 LIMIT ?,?";

	private final String adminGetBP = "SELECT * FROM BID_PRODUCT where bp_shsta = 4 or bp_shsta = 5 or bp_shsta = 6 or bp_shsta = 7 order by bp_id desc limit ? , ?";
	private final String adminGetOne = "SELECT * FROM BID_PRODUCT where bp_shsta = ? order by bp_id desc limit ?,?";
	//拿自己買到的全部訂單
	private final String getSlefBuyOrderAll ="SELECT * FROM BID_PRODUCT where BP_BMEMID = ? and BP_SHSTA > 0 order by bp_id desc limit ?,?";
	
	private final String getSlefBuyOrderSTA ="SELECT * FROM BID_PRODUCT where BP_BMEMID = ? and BP_SHSTA = ? order by bp_id desc limit ?,?";
	private final String CANCEL = "SELECT * FROM BID_PRODUCT WHERE BP_SMEMID = ? AND(BP_SHSTA = 5 OR BP_SHSTA = 6 OR BP_SHSTA = 7)";
	private final String ADDCOM = "UPDATE BID_PRODUCT SET BP_COMMENT = ?, BP_POINT = ? ,BP_COMSTA = ? WHERE BP_ID = ?";

	
	public void insert(BidProductVO bidVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, bidVO.getBp_smemid()); // 這邊不知道是不是透過session 來抓到會員的NO 可能要問
			pstmt.setInt(2, bidVO.getBp_bpcid());
			pstmt.setString(3, bidVO.getBp_name());
			pstmt.setTimestamp(4, bidVO.getBp_stime());
			pstmt.setTimestamp(5, bidVO.getBp_etime());
			pstmt.setInt(6, bidVO.getBp_sprice());
			pstmt.setInt(7, bidVO.getBp_inc());
			pstmt.setInt(8, bidVO.getBp_imdt());
			// 非強制輸入資料如果留空會造成數值null會造成不成功
			pstmt.setInt(9, bidVO.getBp_ship());
			pstmt.setString(10, bidVO.getBp_desc());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void updata(BidProductVO bidVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, bidVO.getBp_bpcid());
			pstmt.setString(2, bidVO.getBp_name());
			pstmt.setTimestamp(3, bidVO.getBp_stime());
			pstmt.setTimestamp(4, bidVO.getBp_etime());
			pstmt.setInt(5, bidVO.getBp_sprice());
			pstmt.setInt(6, bidVO.getBp_inc());
			pstmt.setInt(7, bidVO.getBp_imdt());
			pstmt.setInt(8, bidVO.getBp_ship());
			pstmt.setString(9, bidVO.getBp_desc());
			pstmt.setInt(10, bidVO.getBp_id());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void changesta(Integer bp_sta, Integer bp_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGE_BP_STATUS);

			pstmt.setInt(1, bp_sta);
			pstmt.setInt(2, bp_id);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
	}

	@Override
	public void delete(Integer bp_id) {
		  Connection con = null;
	        PreparedStatement pstmt = null;
	        try {

	            con = ds.getConnection();
	            con.setAutoCommit(false);
	            //先刪除圖片
	            BpImageDAO bpidao = new BpImageDAO();
	            bpidao.delete(bp_id, con);
	            //在刪除資料
	            pstmt = con.prepareStatement(DELETE);
	            pstmt.setInt(1, bp_id);
	            pstmt.executeUpdate();
	            con.commit();
	            con.setAutoCommit(true);
	        } catch (SQLException se) {
	            throw new RuntimeException("A database error occured." + se.getMessage());
	        } finally {
	            if (pstmt != null) {
	                try {
	                    pstmt.close();
	                } catch (SQLException se) {
	                    se.printStackTrace();
	                }
	            }
	            if (con != null) {
	                try {
	                    con.close();
	                } catch (SQLException se) {
	                    se.printStackTrace();
	                }
	            }

	        }
	}

	@Override
	public List<BidProductVO> getAll() {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BidProductVO findByPrimaryKey(Integer bp_id) {
		BidProductVO BidProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
			pstmt.setInt(1, bp_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return BidProductVO;
	}

	@Override
	public List<BidProductVO> getAllBystatus(Integer bp_sta) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("BP_STA") == bp_sta) {
					BidProductVO = new BidProductVO();
					BidProductVO.setBp_id(rs.getInt("BP_ID"));
					BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
					BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
					BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
					BidProductVO.setBp_name(rs.getString("BP_NAME"));
					BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
					BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
					BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
					BidProductVO.setBp_inc(rs.getInt("BP_INC"));
					BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
					BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
					BidProductVO.setBp_desc(rs.getString("BP_DESC"));
					BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
					BidProductVO.setBp_sta(rs.getInt("BP_STA"));
					BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
					BidProductVO.setBp_to(rs.getInt("BP_TO"));
					BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
					BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
					BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
					BidProductVO.setBp_point(rs.getInt("BP_POINT"));
					BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

					list.add(BidProductVO);
				}
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public BidProductVO getBp_id(BidProductVO bidVO) {
		BidProductVO BidProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BP_ID);
			pstmt.setInt(1, bidVO.getBp_smemid());
			pstmt.setInt(2, bidVO.getBp_bpcid());
			pstmt.setString(3, bidVO.getBp_name());
			pstmt.setTimestamp(4, bidVO.getBp_stime());
			pstmt.setTimestamp(5, bidVO.getBp_etime());
			pstmt.setInt(6, bidVO.getBp_sprice());
			pstmt.setInt(7, bidVO.getBp_inc());
			pstmt.setInt(8, bidVO.getBp_imdt());
			pstmt.setInt(9, bidVO.getBp_ship());
			pstmt.setString(10, bidVO.getBp_desc());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return BidProductVO;
	}

	@Override
	public List<BidProductVO> getMyBid(Integer bp_smemid) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MY_BID);
			pstmt.setInt(1, bp_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;

	}

	@Override
	public Integer addBid(BidProductVO bidVO, List<Part> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer bp_id = null;
		try {
			String[] col = { "bp_id" };
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT, col);
			pstmt.setInt(1, bidVO.getBp_smemid());
			pstmt.setInt(2, bidVO.getBp_bpcid());
			pstmt.setString(3, bidVO.getBp_name());
			pstmt.setTimestamp(4, bidVO.getBp_stime());
			pstmt.setTimestamp(5, bidVO.getBp_etime());
			pstmt.setInt(6, bidVO.getBp_sprice());
			pstmt.setInt(7, bidVO.getBp_inc());
			pstmt.setString(8, bidVO.getBp_desc());
			pstmt.executeUpdate();
			System.out.println("商品資料新增成功");
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				bp_id = rs.getInt(1);
			} else {
				System.out.println("沒找到PK");
			}
			rs.close();
			BpImageDAO bpidao = new BpImageDAO();
			bpidao.Insert(bp_id, list, con);
			
			
			
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return bp_id;
	}

//	@Override
//	public void updataBid(BidProductVO bidVO, List<Part> list, Integer[] bpi_id) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ds.getConnection();
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(UPDATE);
//			pstmt.setInt(1, bidVO.getBp_bpcid());
//			pstmt.setString(2, bidVO.getBp_name());
//			pstmt.setTimestamp(3, bidVO.getBp_stime());
//			pstmt.setTimestamp(4, bidVO.getBp_etime());
//			pstmt.setInt(5, bidVO.getBp_sprice());
//			pstmt.setInt(6, bidVO.getBp_inc());
//			pstmt.setInt(7, bidVO.getBp_imdt());
//			pstmt.setInt(8, bidVO.getBp_ship());
//			pstmt.setString(9, bidVO.getBp_desc());
//			pstmt.setInt(10, bidVO.getBp_id());
//			pstmt.executeUpdate();
//			
//			Integer bpi_bpid = bidVO.getBp_id();
//			BpImageDAO bpidao = new BpImageDAO();
////			bpidao.(bpi_id, con);
//			bpidao.Insert(bpi_bpid, list, con);
//			
//			con.commit();
//			con.setAutoCommit(true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//			
//		}
//
//	}

	@Override
	public void Updateprice(Integer bp_tprice,Integer bp_id,BpDetailVO bpdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_BP_TPRICE);
			pstmt.setInt(1, bp_tprice);
			pstmt.setInt(2, bp_id);
			pstmt.executeUpdate();
			//開始另一項交易
			BpDetailDAO bpddao = new BpDetailDAO();
			bpddao.insert(bpdVO, con);
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			se.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		
		}
	}

	@Override
	public void UpdateBp_bmemid(Integer bp_bmemid,Integer bp_to,Integer bp_id,Integer bp_shsta) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer bp_smemid = null;
		try {
			
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_BP_BMEMID);
			pstmt.setInt(1, bp_bmemid);
			pstmt.setInt(2, bp_to);	
			pstmt.setInt(3,bp_shsta);
			pstmt.setInt(4, bp_id);
			pstmt.executeUpdate();
			pstmt.close();
			if(bp_to != null) {
				
				pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
				pstmt.setInt(1, bp_id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					bp_smemid = rs.getInt("BP_SMEMID");
				}
			
				new MemberDAO().minusWalletByOrders(bp_to, bp_bmemid, con);
				Long date = new Date().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = sdf.format(date);
				java.sql.Timestamp ts = Timestamp.valueOf(now);
				new ElectronicWalletDAO().walUpdatedByTranOnBmem(bp_bmemid, ts, bp_to, con);
				
				new NoticeDAO().toB_BP(1, bp_bmemid, bp_id, con);
				new NoticeDAO().toS_BP(1, bp_smemid, bp_id, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (Exception se) {
			se.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		
		}
	}

	@Override
	public List<BidProductVO> getWinbid(Integer bp_bmemid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BidProductVO> list = new ArrayList<BidProductVO>();
		BidProductVO BidProductVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BP_WIN_BID);
			pstmt.setInt(1, bp_bmemid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public void InsertOrUpdateReceive(String bp_bname, String bp_add, String bp_tel,Integer bp_id,Integer bp_shsta) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		Integer bp_smemid = null ;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(RECEIVE);
			pstmt.setString(1, bp_bname);
			pstmt.setString(2, bp_add);
			pstmt.setString(3, bp_tel);
			pstmt.setInt(4, bp_shsta);
			pstmt.setInt(5, bp_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
			pstmt.setInt(1, bp_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bp_smemid = rs.getInt("BP_SMEMID");
			}
			new NoticeDAO().toS_BP(2, bp_smemid, bp_id, con);
			
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured." + se.getMessage());
		}finally {
			if( rs != null ) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		
		}
	}

	@Override
	public List<BidProductVO> findMyBidByPage(Integer bp_smemid, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDMYBIDBYPAGE);
			pstmt.setInt(1, bp_smemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> effbid(Integer bp_smemid) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(EFFBID);
			pstmt.setInt(1, bp_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_bname(rs.getString("BP_BNAME"));
				BidProductVO.setBp_add(rs.getString("BP_ADD"));
				BidProductVO.setBp_tel(rs.getString("BP_TEL"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));


				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public void changeshsta(Integer bp_id, Integer bp_shsta ) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ; 
		Integer bp_bmemid = null ;
		Integer bp_smemid = null ;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(CHANGESHSTA);
			pstmt.setInt(1, bp_shsta);
			pstmt.setInt(2, bp_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
			pstmt.setInt(1, bp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bp_bmemid = rs.getInt("BP_BMEMID");
				bp_smemid = rs.getInt("BP_SMEMID");
			}
			if(bp_shsta == 3) {
				new NoticeDAO().toB_BP(bp_shsta, bp_bmemid, bp_id, con);
			}
			if(bp_shsta == 4) {
				new NoticeDAO().toS_BP(bp_shsta, bp_smemid, bp_id, con);
			}
			
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if( con != null ) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured." + se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		
		}
		
	}

	@Override
	public List<BidProductVO> tobeconfirmed(Integer bp_smemid) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(TOBECONFIRMED);
			pstmt.setInt(1, bp_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_bname(rs.getString("BP_BNAME"));
				BidProductVO.setBp_add(rs.getString("BP_ADD"));
				BidProductVO.setBp_tel(rs.getString("BP_TEL"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));


				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
		
	}

	@Override
	public List<BidProductVO> completebidb(Integer bp_bmemid, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COMPLETEBIDB);
			pstmt.setInt(1, bp_bmemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> completebids(Integer bp_smemid, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COMPLETEBIDS);
			pstmt.setInt(1, bp_smemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> adminGetBP(Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(adminGetBP);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> adminGetOneBP(Integer bp_shsta, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(adminGetOne);
			pstmt.setInt(1, bp_shsta);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public void BPdone(Integer bp_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		BidProductVO BidProductVO = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(CHANGESHSTA);
			pstmt.setInt(1, 6);
			pstmt.setInt(2, bp_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
			pstmt.setInt(1, bp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

			}
		
			new MemberDAO().addWalletByOrders(BidProductVO.getBp_to(), BidProductVO.getBp_smemid(), con);
			
			Long date = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			java.sql.Timestamp ts = Timestamp.valueOf(now);
			
			new ElectronicWalletDAO().walUpdatedByTranOnSmem(BidProductVO.getBp_smemid(), ts, BidProductVO.getBp_to(), con);
			
			new NoticeDAO().toS_BP(5, BidProductVO.getBp_smemid(), BidProductVO.getBp_id(), con);
			
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		
				
		
	}

	@Override
	public void refund(Integer bp_id) {
		Connection con = null ; 
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		BidProductVO BidProductVO = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(CHANGESHSTA);
			pstmt.setInt(1, 7);
			pstmt.setInt(2, bp_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_BIDNO);
			pstmt.setInt(1, bp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

			}
		
			new MemberDAO().addWalletByOrders(BidProductVO.getBp_to(), BidProductVO.getBp_bmemid(), con);
			
			Long date = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			java.sql.Timestamp ts = Timestamp.valueOf(now);
			
			new ElectronicWalletDAO().refund(BidProductVO.getBp_bmemid(), ts, BidProductVO.getBp_to(), con);
			
			new NoticeDAO().toB_BP(7, BidProductVO.getBp_smemid(), BidProductVO.getBp_id(), con);
			
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		
	}

	@Override
	public List<BidProductVO> getSelfBuyOrdersAll(Integer bp_bmemid, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getSlefBuyOrderAll);
			pstmt.setInt(1, bp_bmemid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<BidProductVO> getSelfBuyOrdersSTA(Integer bp_bmemid, Integer bp_shsta, Integer start, Integer rows) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getSlefBuyOrderSTA);
			pstmt.setInt(1, bp_bmemid);
			pstmt.setInt(2, bp_shsta);
			pstmt.setInt(3, start);
			pstmt.setInt(4, rows);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public void addcom(Integer bp_id, String bp_comment, Integer bp_point, Integer bp_comsta) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADDCOM);
			pstmt.setString(1, bp_comment);
			pstmt.setInt(2, bp_point);
			pstmt.setInt(3, bp_comsta);
			pstmt.setInt(4, bp_id);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		}finally {			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		
	}

	@Override
	public List<BidProductVO> cancelbids(Integer bp_smemid) {
		List<BidProductVO> list = new ArrayList<>();
		BidProductVO BidProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CANCEL);
			pstmt.setInt(1, bp_smemid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BidProductVO = new BidProductVO();
				BidProductVO.setBp_id(rs.getInt("BP_ID"));
				BidProductVO.setBp_bmemid(rs.getInt("BP_BMEMID"));
				BidProductVO.setBp_smemid(rs.getInt("BP_SMEMID"));
				BidProductVO.setBp_bpcid(rs.getInt("BP_BPCID"));
				BidProductVO.setBp_name(rs.getString("BP_NAME"));
				BidProductVO.setBp_stime(rs.getTimestamp("BP_STIME"));
				BidProductVO.setBp_etime(rs.getTimestamp("BP_ETIME"));
				BidProductVO.setBp_sprice(rs.getInt("BP_SPRICE"));
				BidProductVO.setBp_inc(rs.getInt("BP_INC"));
				BidProductVO.setBp_imdt(rs.getInt("BP_IMDT"));
				BidProductVO.setBp_ship(rs.getInt("BP_SHIP"));
				BidProductVO.setBp_desc(rs.getString("BP_DESC"));
				BidProductVO.setBp_tprice(rs.getInt("BP_TPRICE"));
				BidProductVO.setBp_sta(rs.getInt("BP_STA"));
				BidProductVO.setBp_beend(rs.getTimestamp("BP_BEEND"));
				BidProductVO.setBp_to(rs.getInt("BP_TO"));
				BidProductVO.setBp_paymth(rs.getInt("BP_PAYMTH"));
				BidProductVO.setBp_shsta(rs.getInt("BP_SHSTA"));
				BidProductVO.setBp_comment(rs.getString("BP_COMMENT"));
				BidProductVO.setBp_point(rs.getInt("BP_POINT"));
				BidProductVO.setBp_point(rs.getInt("BP_COMSTA"));

				list.add(BidProductVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public void updataBid(BidProductVO bidVO, List<Part> list, Integer[] bpi_id) {
		
	}
}
