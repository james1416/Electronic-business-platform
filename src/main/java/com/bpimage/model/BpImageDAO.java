package com.bpimage.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

public class BpImageDAO implements BpImageDAO_interface {
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private final String INSERT = "INSERT INTO BP_IMAGE (BPI_BPID,BPI_IMG) VALUES(?,?)";
	private final String UPDATE = "UPDATE BP_IMAGE SET BPI_IMG = ? WHERE BPI_BPID = ?";
	private final String GET_ONE_STMT = "SELECT * FROM BP_IMAGE WHERE BPI_BPID = ?";
	private final String GET_BPI_ID_STMT = "SELECT BPI_ID FROM BP_IMAGE WHERE BPI_BPID = ?";
	private final String DELETE = "DELETE FROM BP_IMAGE WHERE BPI_BPID = ?";

	@Override
	public void Insert(Integer bpi_bpid, List<Part> imgs, Connection con) {
		PreparedStatement pstmt = null;
		System.out.println(bpi_bpid);
		try {
			pstmt = con.prepareStatement(INSERT);
			for (Part img : imgs) {
				InputStream in = null;
				
				try {
					in = img.getInputStream();
					if (in.available() == 0) {
						return;
					}
						byte[] bpi_img = new byte[in.available()];
						in.read(bpi_img);
						in.close();
						pstmt.setInt(1, bpi_bpid);
						pstmt.setBytes(2, bpi_img);
						pstmt.executeUpdate();
					
				} catch (IOException e) {
					e.printStackTrace();
					con.rollback();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	@Override
	public byte[] getOne(Integer bpi_bpid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] img = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, bpi_bpid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				img = rs.getBytes("bpi_img");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return img;
	}

	@Override
	public List<BpImageVO> getAll(Integer bpi_bpid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BpImageVO> list = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, bpi_bpid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BpImageVO bpiVO = new BpImageVO();
				bpiVO.setBpi_id(rs.getInt("bpi_id"));
				bpiVO.setBpi_bpid(rs.getInt("bpi_bpid"));
				bpiVO.setBpi_img(rs.getBytes("bpi_img"));
				list.add(bpiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public boolean check(Integer bpi_bpid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean HaveImg = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, bpi_bpid);
			rs = pstmt.executeQuery();
			while (rs.next())
				HaveImg = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		return HaveImg;
	}
	public void delete(Integer bpi_bpid, Connection con) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(DELETE);
            pstmt.setInt(1, bpi_bpid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }
}