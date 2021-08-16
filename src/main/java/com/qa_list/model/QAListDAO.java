package com.qa_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class QAListDAO implements QAListDAO_interface{
	private final String all ="SELECT * FROM QA_LIST;";
	private final String add = "INSERT INTO QA_LIST(QAL_QCON , QAL_ACON , QAL_TSP)VALUE(?,?,?)";
	private final String findByPK= "SELECT * FROM QA_LIST where QAL_ID = ?";
	private final String updata = "UPDATE QA_LIST SET QAL_QCON = ?, QAL_ACON = ? , QAL_TSP = ?,QAL_STA = ? WHERE QAL_ID = ?";
	private static DataSource ds = null;
	static {
			try {
				Context ctx = new javax.naming.InitialContext();
				ds =(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
	}
	
	public List<QAListVO> getall() {
		List<QAListVO>list = new ArrayList<QAListVO>();
		QAListVO qaListVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(all);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				qaListVO = new QAListVO();
				qaListVO.setQal_id(rs.getInt("qal_id"));
				qaListVO.setQal_qcon(rs.getString("qal_qcon"));
				qaListVO.setQal_acon(rs.getString("qal_acon"));
				qaListVO.setQal_sta(rs.getInt("qal_sta"));
				///轉成日期
				qaListVO.setQal_tsp(rs.getDate("qal_tsp"));
				list.add(qaListVO);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
		return list;
	}

	@Override
	public void add(QAListVO qaListVO) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(add);
			pstmt.setString(1, qaListVO.getQal_qcon());
			pstmt.setString(2, qaListVO.getQal_acon());
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time = sdf.format(qaListVO.getQal_tsp());
			pstmt.setString(3, time);
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
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	@Override
	public QAListVO findQaListVOByPK(Integer QAL_ID) {
		Connection con = null ;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QAListVO qaListVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareCall(findByPK);
			pstmt.setInt(1, QAL_ID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				qaListVO = new QAListVO();
				qaListVO.setQal_id(rs.getInt("qal_id"));
				qaListVO.setQal_qcon(rs.getString("qal_qcon"));
				qaListVO.setQal_acon(rs.getString("qal_acon"));
				qaListVO.setQal_sta(rs.getInt("qal_sta"));
				qaListVO.setQal_tsp(rs.getDate("qal_tsp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
		return qaListVO;
	}

	@Override
	public void updata(QAListVO qaListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareCall(updata);
			pstmt.setString(1, qaListVO.getQal_qcon());
			pstmt.setString(2, qaListVO.getQal_acon());
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time = sdf.format(qaListVO.getQal_tsp());
			pstmt.setString(3, time);
			pstmt.setInt(4, qaListVO.getQal_sta());
			pstmt.setInt(5, qaListVO.getQal_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!= null) {
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
	

}
