package com.admin_authority.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin_authority.model.AdminAuthorityService;
import com.admin_authority.model.AdminAuthorityVO;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/AdminAuthorityServlet")
public class AdminAuthorityServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		// 從隱藏的物件拿到該管理員ID
		String s = req.getParameter("action");
		System.out.println(s);
		
		
		//可能用不到了 因為已經整合進去單一裡面
		
		
		if("getone".equals(s)) {
			PrintWriter out = res.getWriter();
			System.out.println(s);
			Integer adminid = Integer.parseInt(req.getParameter("adminid"));
			System.out.println(adminid);
			AdminAuthorityService as = new AdminAuthorityService();
			List<AdminAuthorityVO> list = as.getone(adminid);
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			out.println(data);
			return;
		}
	
		
		Integer adminid = new Integer(s);

		Set<Integer> ChengeAutid = new HashSet<Integer>();

		// 若被拔除所有功能的話則會為null
		if (req.getParameterValues(s) != null) {
			String id[] = req.getParameterValues(s);
			for (int i = 0; i < id.length; i++) {
				ChengeAutid.add(Integer.parseInt(id[i]));
			}
		}
		AdminAuthorityService aas = new AdminAuthorityService();
		aas.add(adminid, ChengeAutid);
		RequestDispatcher successView = req.
				getRequestDispatcher("/back-end/AdminAuthority/selectAllAdminAuthority.jsp");
		successView.forward(req, res);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
