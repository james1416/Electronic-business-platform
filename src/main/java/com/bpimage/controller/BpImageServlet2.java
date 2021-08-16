package com.bpimage.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.cj.exceptions.UnableToConnectException;



@WebServlet("/BpImageServlet2")
public class BpImageServlet2 extends HttpServlet {
	Connection con = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();
		
		
		try {
			
			Integer bpi_bpid = new Integer(req.getParameter("bpi_bpid"));
			Integer bpi_id = new Integer(req.getParameter("bpi_id"));
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT BPI_IMG FROM BP_IMAGE WHERE BPI_BPID ="+bpi_bpid+"AND BPI_ID ="+bpi_id);
			if(rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("bpi_img"));
				byte[] buf = new byte[4*1024];
				int len;
				while((len = in.read(buf)) != -1) {
					out.write(buf,0,len);
				}
				in.close();
			}else {
				InputStream in = getServletContext().getResourceAsStream("/NoData/none2.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/NoData/null.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close(); 
		}
	}


	public void init() throws ServletException{
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G4");
			con = ds.getConnection();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	public void destroy() {
		try {
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
