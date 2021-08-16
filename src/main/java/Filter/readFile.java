package Filter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



//@WebServlet("/fileUpload")
public class readFile extends HttpServlet {
	static Connection con;
	static {
    	try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/David");
			con = ds.getConnection();
		} catch (NamingException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
//		ServletOutputStream sos = response.getOutputStream();
		PrintWriter out = response.getWriter();
		String pic = "Select * from jdbcsample.club where id = ?";
		
		
		try {
			PreparedStatement pstmt = con.prepareStatement(pic);
			String id = request.getParameter("id");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				InputStream is = rs.getBinaryStream("pic");
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buf = new byte[bis.available()];

				bis.read(buf);
				bis.close();
				
				String data = Base64.getEncoder().encodeToString(buf);
				
				System.out.println(data);
				out.println(data);
				
//				while((length = bis.read(buf))!=-1) {
//					sos.write(buf, 0, length);	
//				}
//				bis.close();
			}else {
				out.println("讀取失敗");
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
