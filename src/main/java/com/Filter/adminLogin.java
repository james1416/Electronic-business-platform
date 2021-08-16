package com.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminVO;
public class adminLogin implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req	 = (HttpServletRequest) request;
		HttpServletResponse res  = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("adminVO");
		if(admin == null) {
			session.setAttribute("location", req.getRequestURI());
			System.out.println(req.getRequestURI());
			System.out.println("你要登入");
			res.sendRedirect(req.getContextPath() + "/backLogin.jsp");
			return;
		}else if(admin.getAdmin_sta() == 0){
			System.out.println("被停權");
			res.sendRedirect(req.getContextPath() + "/backLogin.jsp");
			return;
		}else {
			chain.doFilter(request, response);
		}
		
	}
	

}
