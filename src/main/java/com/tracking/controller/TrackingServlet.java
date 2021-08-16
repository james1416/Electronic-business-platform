package com.tracking.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;
import com.tracking.model.TrackingService;
import com.tracking.model.TrackingVO;


@WebServlet("/TrackingServlet")
public class TrackingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		if("add".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				out.print(false);
				return;
			}
			Integer tk_pid= new Integer(req.getParameter("pid"));
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer tk_memid = memberVO.getMem_id();
			TrackingService ts = new TrackingService();
			ts.add(tk_pid, tk_memid);
			out.print(true);
			return;
		}
		
		
		if("del".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				out.print(false);
				return;
			}
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer tk_memid = memberVO.getMem_id();
			Integer tk_pid = new Integer(req.getParameter("pid"));
			TrackingService ts = new TrackingService();
			ts.del(tk_pid, tk_memid);
			out.print(true);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
