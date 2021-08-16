package com.productReport.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.productReport.model.ProductReportService;
import com.productReport.model.ProductReportVO;

@WebServlet("/ProductReportServlet")
public class ProductReportServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		
		if("getallbyADMIN".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<ProductReportVO> list = new ProductReportService().getallbyADMIN(start , rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		if("getallbySTA".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			Integer pr_sta = new Integer(req.getParameter("prsta"));
			System.out.println(pr_sta);
			List<ProductReportVO> list = new ProductReportService().getallbySTA(pr_sta, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		if("getone".equals(action)) {
			Integer pr_id = new Integer(req.getParameter("prid"));
			ProductReportVO productReportVO = new ProductReportService().getone(pr_id);
			String data = new ObjectMapper().writeValueAsString(productReportVO);
			res.getWriter().print(data);
			return;
		}
		
		
		if("updata".equals(action)) {
			Integer pr_id = new Integer(req.getParameter("prid"));
			Integer pr_sta = new Integer(req.getParameter("prsta"));
			new ProductReportService().updata(pr_id, pr_sta);
			return;
		}
		
		
		if("getall".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.html");
				okView.forward(req, res);
				return;
			}
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
//			selectPR
			ProductReportService prs = new ProductReportService();
			List<ProductReportVO> list = prs.getall(memberVO.getMem_id());
			System.out.println(list.size());
			for(ProductReportVO p : list) {
				System.out.println(p.getPr_id());
			}
			req.setAttribute("list", list);
			RequestDispatcher okView = req.
					getRequestDispatcher("/front-end/ProductReport/selectPR.jsp");
			okView.forward(req, res);
			return;
		}
		
		
		
		
		if("report".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.html");
				okView.forward(req, res);
				return;
			}
			String rp_pid = req.getParameter("pid");
			req.setAttribute("pid", rp_pid);
			RequestDispatcher okView = req.
					getRequestDispatcher("/front-end/ProductReport/addRP.jsp");
			okView.forward(req, res);
			return;
		}
		
		
		
		
		
		if("add".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.jsp");
				okView.forward(req, res);
				return;
			}
			Integer rp_pid = new Integer(req.getParameter("prpid"));
			String rp_content = req.getParameter("prcontent");
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer pr_memid = memberVO.getMem_id();
			ProductReportService prs = new ProductReportService();
			prs.add(pr_memid, rp_pid, rp_content);
			RequestDispatcher okView = req.
					getRequestDispatcher("/ProductServlet?action=getAllByPage");
			okView.forward(req, res);
			return;
		}
		
		
		if("del".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.html");
				okView.forward(req, res);
				return;
			}
			
			Integer pr_pid = new Integer(req.getParameter("prpid"));
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer pr_memid = memberVO.getMem_id();
			ProductReportService prs = new ProductReportService();
			prs.del(pr_memid, pr_pid);
			RequestDispatcher okView = req.
					getRequestDispatcher("/ProductReportServlet?action=getall");
			okView.forward(req, res);
			return;
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
