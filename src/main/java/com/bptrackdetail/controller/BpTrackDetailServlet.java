package com.bptrackdetail.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;


@WebServlet("/BpTrackDetailServlet")
@MultipartConfig
public class BpTrackDetailServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("有人進追蹤SERVLET");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		MemberVO memberVO = null;
		try {
			 memberVO =(MemberVO) session.getAttribute("memberVO");
		} catch (NullPointerException e) {
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/login.jsp");
			successView.forward(req, res);
			return;
		}
		
		if("track".equals(action)){
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			
		}
	}

}
