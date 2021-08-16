package Filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



//@WebServlet("/uploadFile")
public class uploadFile extends HttpServlet {
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
		String data = request.getParameter("data");
		String id = request.getParameter("id");
		int ID = Integer.parseInt(id);
		System.out.println(id);
		System.out.println(data);
		String insertPic = "UPDATE mydb.MEMBER SET MEM_PIC = ? WHERE MEM_ID = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(insertPic);
			pstmt.setString(1, data);
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();
			}catch (Exception e) {
				
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
