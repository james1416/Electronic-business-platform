package com.Filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminVO;
import com.admin_authority.model.AdminAuthorityDAO;
import com.admin_authority.model.AdminAuthorityVO;

public class adminORG implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req	 = (HttpServletRequest) request;
		HttpServletResponse res  = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("adminVO");
		
		Integer admin_id = admin.getAdmin_id();
		List<AdminAuthorityVO> list  = new AdminAuthorityDAO().getone(admin_id);
		boolean check = false;
		for(AdminAuthorityVO a : list) {
			if(a.getAa_autid() == 3) {
				check = true;
			}
		}
		
		
		if(check) {
			chain.doFilter(request, response);
			req.getSession().setAttribute("msg", null);

		}else {
			req.getSession().setAttribute("msg", "一般商城");
			res.sendRedirect(req.getContextPath() + "/back-end/BackIndex.jsp");
		}
			
		
	}
	
}


