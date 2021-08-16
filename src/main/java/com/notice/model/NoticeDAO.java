package com.notice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.ResultSet;

public class NoticeDAO implements NoticeDAO_interface{

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
	public List<NoticeVO> getall(Integer nt_memid) {
		String getAll = "SELECT * FROM notice WHERE nt_memid=? ORDER BY nt_time desc";
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getAll);
			pstmt.setInt(1, nt_memid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeVO nv = new NoticeVO();
				nv.setNt_id(rs.getInt("nt_id"));
				nv.setNt_memid(rs.getInt("nt_memid"));
				nv.setNt_text(rs.getString("nt_text"));		
				nv.setNt_time(rs.getDate("nt_time"));
				nv.setNt_view(rs.getInt("nt_view"));
				list.add(nv);				
			}
			System.out.println(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
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

	@Override
	public int deleteNotice(Integer mem_id) {
		String deleteNotice = "delete from mydb.NOTICE where nt_memid = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Taipei", "David", "123456");
			pstmt = con.prepareStatement(deleteNotice);
			pstmt.setInt(1, mem_id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
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
		return count;
	}

	@Override
//	發給賣家
	public void add(Integer sta, Integer notifyOther, Integer SelforODID , Connection con) {
		String addone = "INSERT INTO NOTICE (NT_MEMID, NT_TEXT, NT_TIME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		String forNotifyOther ="";
		
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ts = Timestamp.valueOf(now);
		
		switch (sta) {
		case 0:
			forNotifyOther ="您於"+now+"收到了一筆新的訂單！快去審核吧！";
			break;
		case 2:
			forNotifyOther ="您已於"+now+"受理訂單編號"+SelforODID+"請盡速為買家出貨。";
			break;
		case 3:
			forNotifyOther ="您的訂單編號"+SelforODID+"於"+now+"出貨囉！";
			break;
		case 4:
			forNotifyOther = "您有一筆訂單須辦理退貨，請前往審核。";
			break;
//		case 5:
//		
//			break;
		case 6:
			forNotifyOther ="您的訂單編號"+SelforODID+"於"+now+"退款完畢";
//			break;
		case 7:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"已完成。";
			break;
		case 8:
			forNotifyOther ="您的訂單編號"+SelforODID+"於"+now+"遭到取消囉！";
			break;
		case 9:
			forNotifyOther ="您的訂單編號"+SelforODID+"於"+now+"成功撥款囉！";
//			break;
//		case 10:
//			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"已評價完成。";
//			break;
		}
		
		try {
			
				pstmt = con.prepareStatement(addone);
				pstmt.setInt(1, notifyOther);
				pstmt.setString(2, forNotifyOther);
				pstmt.setTimestamp(3, ts);
				pstmt.executeUpdate();
			
		} catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void StoB_PRE(Integer sta, Integer notifyOther, Integer SelforODID, Connection con) {
		String addone = "INSERT INTO NOTICE (NT_MEMID, NT_TEXT, NT_TIME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		String forNotifyOther ="";
		
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ts = Timestamp.valueOf(now);
		
		switch (sta) {
		case 0:
			forNotifyOther= "訂單編號"+SelforODID+"下訂成功，請等待預購結束日。";
			break;
		case 1:
			forNotifyOther= "訂單編號"+SelforODID+"此次預購數量不足，成團失敗。";
			break;	
			
		case 2:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"成功出團 , 請等待賣家確認訂單";
			break;
		case 3:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"已成立，請耐心等待賣家出貨！";
			break;
		case 4:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"出貨囉，請留意宅配的聯絡！";
			break;
		case 5:
		
			break;
		case 6:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"成功退貨，請等待撥款!";
			break;
		case 7:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"成功退款！";
			break;
		case 8:
			
			break;
		case 9:
			forNotifyOther = "訂單編號"+SelforODID+"於"+ now+"被取消了！";
			break;
		case 10:
			break;
		}
		
		try {
				pstmt = con.prepareStatement(addone);
				pstmt.setInt(1, notifyOther);
				pstmt.setString(2, forNotifyOther);
				pstmt.setTimestamp(3, ts);
				pstmt.executeUpdate();
		} catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
		

	@Override
	public void BtoS_PRE(Integer sta, Integer notifyOther, Integer SelforODID, Connection con) {
		String addone = "INSERT INTO NOTICE (NT_MEMID, NT_TEXT, NT_TIME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		String forNotifyOther ="";
		
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ts = Timestamp.valueOf(now);
		
		switch (sta) {
		case 0:
			forNotifyOther ="您於"+now+"收到了一筆新的預購訂單！";
			break;
		case 1:
			forNotifyOther= "商品編號"+SelforODID+"此次預購數量不足，成團失敗。";
			break;
		case 2:
			forNotifyOther= "商品編號"+SelforODID+"此次預購成團成功，請前往審核訂單。";
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			forNotifyOther= "商品編號"+SelforODID+"買家已收貨，請前往查看完成訂單。";
			break;
		case 6:
			forNotifyOther= "商品編號"+SelforODID+"此筆訂單被退貨，請前往查看。";	
			break;
		case 7:
			
			break;
		case 8:
			break;
		case 9:
			forNotifyOther= "商品編號"+SelforODID+"此筆訂單被取消，請前往查看。";
			break;
		case 10:
			forNotifyOther= "訂單編號"+SelforODID+"成功撥款。";
			break;
		}
		
		try {
				pstmt = con.prepareStatement(addone);
				pstmt.setInt(1, notifyOther);
				pstmt.setString(2, forNotifyOther);
				pstmt.setTimestamp(3, ts);
				pstmt.executeUpdate();
		} catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void toB_BP(Integer sta, Integer notifyOther, Integer SelforODID, Connection con) {
		String addone = "INSERT INTO NOTICE (NT_MEMID, NT_TEXT, NT_TIME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		String forNotifyOther ="";
		
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ts = Timestamp.valueOf(now);
		
		switch (sta) {
		case 0:
			break;
		case 1:
			forNotifyOther = "您的競標商品"+ SelforODID +"於"+now+"成功成立並且扣款，請填寫收件資訊。";
			break;
		case 2:
			
			break;
		case 3:
			forNotifyOther = "您的競標商品"+ SelforODID +"於"+now+"已出貨，請留意宅配通知。";
			break;
		case 4:
			
			break;
		case 5:
		
			break;
		case 6:
			break;
		case 7:
			forNotifyOther = "您的競標商品"+ SelforODID +"於"+now+"成功退款！";
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		}
		
		try {
				pstmt = con.prepareStatement(addone);
				pstmt.setInt(1, notifyOther);
				pstmt.setString(2, forNotifyOther);
				pstmt.setTimestamp(3, ts);
				pstmt.executeUpdate();
		} catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
		

	@Override
	public void toS_BP(Integer sta, Integer notifyOther, Integer SelforODID, Connection con) {
		String addone = "INSERT INTO NOTICE (NT_MEMID, NT_TEXT, NT_TIME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = null;
		String forNotifyOther ="";
		
		Long date = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		java.sql.Timestamp ts = Timestamp.valueOf(now);
		
		switch (sta) {
		case 0:
			break;
		case 1:
			forNotifyOther = "您的競標商品"+ SelforODID+"商品於"+now+"成功成立，請靜待買家填寫收件資訊。";
			break;
		case 2:
			forNotifyOther = "您的競標商品"+ SelforODID+"收件資訊已填寫！請盡快出貨唷！";
			break;
		case 3:
			break;
		case 4:
			forNotifyOther = "您的競標商品"+ SelforODID+"商品於"+now+"成功完成訂單！請等待撥款！";
			break;
		case 5:
			forNotifyOther = "您的競標商品"+ SelforODID+"商品於"+now+"成功完成撥款";
			break;
		case 6:
			break;
		case 7:
			
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			forNotifyOther= "訂單編號"+SelforODID+"成功撥款。";
			break;
		}
		
		try {
				pstmt = con.prepareStatement(addone);
				pstmt.setInt(1, notifyOther);
				pstmt.setString(2, forNotifyOther);
				pstmt.setTimestamp(3, ts);
				pstmt.executeUpdate();
		} catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void toB_org(Integer sta, Integer notifyOther, Integer SelforODID, Connection con) {
		String toB_org = "insert into notice (NT_MEMID, NT_TEXT, NT_TIME) values(?, ?, ?)";
		PreparedStatement pstmt = null ;
		Long time = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(time);
		Timestamp tsp = Timestamp.valueOf(now);
		
		
		String forNotifyOther ="";
		
		switch (sta) {
		case 0:
			forNotifyOther = "您的訂單已成立，請等待賣家審核。";
			break;
		case 2:
			forNotifyOther = "賣家已於"+now+"確認您的訂單，即將為您出貨。";
			break;
		case 3:
			forNotifyOther = "您的訂單編號為"+SelforODID+"，賣家於"+now+"已為您出貨。";
			break;
		case 4:
			forNotifyOther = "您的訂單編號"+SelforODID+"取消申請受理中。";
			break;
		case 5:
			break;
		case 6:
			forNotifyOther = "訂單編號"+SelforODID+"，於"+now+"退款完成。";
			break;
		case 7:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"已完成。";
			break;
		case 8:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"已取消。";
			break;
//		case 9:
//			
//			break;
		case 10:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"已評價完成。";
			break;
			// 退貨成功
		case 11:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"成功通過退貨審核，訂單已取消。";
			break;
			//退貨失敗
		case 12:
			forNotifyOther = "訂單編號"+SelforODID+"於"+now+"賣家不接受退貨申請。";
			break;
			
			
			
			
		}
		
		try {
			pstmt = con.prepareStatement(toB_org);
			pstmt.setInt(1, notifyOther);
			pstmt.setString(2, forNotifyOther);
			pstmt.setTimestamp(3, tsp);
			pstmt.executeUpdate();
		}  catch (SQLException e) {
			if( con != null ) {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public Integer getcount(Integer mem_id) {
		String getCount  = "SELECT count(*) FROM mydb.notice where nt_memid = ? and nt_view = 0";
		Connection con = null ; 
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		Integer count = null ;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getCount);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count(*)");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		return count;
	}
	
	@Override
	public void changeViewTo1(Integer nt_id) {
		String changeViewTo1 = "UPDATE notice SET nt_view = 1 WHERE nt_id = ? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(changeViewTo1);
			pstmt.setInt(1, nt_id);
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
		

}