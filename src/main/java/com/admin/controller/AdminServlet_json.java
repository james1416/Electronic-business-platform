package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/AdminServlet_json")
public class AdminServlet_json extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		PrintWriter out = res.getWriter();
		AdminService as = new AdminService();
		String action = req.getParameter("action");
		
		if("getall".equals(action)) {
		List<AdminVO> list  = as.getall();
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(list);
		out.print(data);
		}
		if("add".equals(action)) {
		String acc = req.getParameter("admin_acc");
		String pw = req.getParameter("admin_pw");
		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_acc(acc);
		adminVO.setAdmin_pw(pw);
//		as.add(adminVO);
		}
		if("updata".equals(action)) {
			Integer id = Integer.parseInt(req.getParameter("admin_id"));
			String acc = req.getParameter("admin_acc");
			String pw = req.getParameter("admin_pw");
			Integer sta = Integer.parseInt(req.getParameter("admin_sta"));
			System.out.println(sta);
			AdminVO admin_VO = new AdminVO();
			admin_VO.setAdmin_id(id);
			admin_VO.setAdmin_acc(acc);
			admin_VO.setAdmin_pw(pw);
			admin_VO.setAdmin_sta(sta);
//			as.updata(admin_VO);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
