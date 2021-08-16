package com.cartList.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cartList.model.CartListService;
import com.member.model.MemberVO;

@WebServlet("/CartListServlet")
public class CartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String aciton = req.getParameter("action");
		PrintWriter out  = res.getWriter();
		if("add".equals(aciton)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				out.print(false);
				return;
			}
			MemberVO memberVO =  (MemberVO) session.getAttribute("memberVO");
			Integer cl_memid = memberVO.getMem_id();
			Integer cl_pid =new Integer(req.getParameter("pid"));
			CartListService cls = new CartListService();
			cls.add(cl_memid, cl_pid);
			out.print(true);
			
		}
		
		if("del".equals(aciton)) {
			
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				out.print(false);
				return;
			}
			MemberVO memberVO =  (MemberVO) session.getAttribute("memberVO");
			Integer cl_memid = memberVO.getMem_id();
			Integer cl_pid =new Integer(req.getParameter("pid"));
			CartListService cls = new CartListService();
			cls.del(cl_memid, cl_pid);
			out.print(true);
		}
		
		if("updata".equals(aciton)) {
			HttpSession session = req.getSession();
			MemberVO memberVO =  (MemberVO) session.getAttribute("memberVO");
			Integer cl_memid = memberVO.getMem_id();
			Integer cl_pid =new Integer(req.getParameter("pid"));
			String up_or_down = req.getParameter("up_or_down");
			CartListService cls = new CartListService();
			Integer data= cls.updata(cl_memid, cl_pid, up_or_down);
			out.print(data);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
