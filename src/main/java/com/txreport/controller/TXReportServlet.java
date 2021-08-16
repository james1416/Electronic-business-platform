package com.txreport.controller;

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
import com.txreport.model.TXReportService;
import com.txreport.model.TXReportVO;

@WebServlet("/TXReportServlet")
public class TXReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if("getAllbySTA".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			Integer tr_sta = new Integer(req.getParameter("trsta"));
			TXReportService rs = new TXReportService();
			List<TXReportVO> list = rs.getAllbySTA(tr_sta, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		
		if("getAll".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			TXReportService rs = new TXReportService();
			List<TXReportVO> list = rs.getAll(start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		if("reportBuy".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO= (MemberVO) session.getAttribute("memberVO");
			Integer tr_reporter = memberVO.getMem_id();
			Integer tr_odid = new Integer(req.getParameter("trodid"));
			Integer tr_reported = new Integer(req.getParameter("trbmemid"));
			String tr_content = req.getParameter("trcontent");
			TXReportService txrs =  new TXReportService();
			txrs.add(tr_odid, tr_reporter, tr_reported , tr_content);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/selectCancelOD.jsp");
			OKView.forward(req, res);
			return;
			
		}
		
		if("audit".equals(action)) {
			Integer tr_id = new Integer(req.getParameter("trid"));
			Integer tr_sta = new Integer(req.getParameter("trsta"));
			TXReportService txrs =  new TXReportService();
			txrs.updata(tr_id, tr_sta);
			return;
		}
		
		
		if("getone".equals(action)) {
			Integer tr_id = new Integer(req.getParameter("trid"));
			TXReportService txrs =  new TXReportService();
			TXReportVO txReportVO = txrs.getone(tr_id);
			String data = new ObjectMapper().writeValueAsString(txReportVO);
			res.getWriter().print(data);
			return;
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
