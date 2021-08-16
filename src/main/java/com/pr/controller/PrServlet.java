package com.pr.controller;

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
import com.pr.model.PrService;
import com.txreport.model.TXReportService;
import com.txreport.model.TXReportVO;


@WebServlet("/PrServlet")
public class PrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		String action = req.getParameter("action");
		
		//檢舉買家
		if("reportBuyer".equals(action)){
			HttpSession session = req.getSession();
			MemberVO memberVO= (MemberVO) session.getAttribute("memberVO");
			// smemid=檢舉人
			Integer pr_smemid = memberVO.getMem_id();
			Integer pr_poid = new Integer(req.getParameter("PR_POID"));
			// bmemid=被檢舉人
			Integer pr_bmemid = new Integer(req.getParameter("PR_BMEMID"));
			String pr_desc = req.getParameter("PR_DESC");
			PrService prSvc = new PrService();
			prSvc.add(pr_poid, pr_bmemid, pr_smemid, pr_desc);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/PreProduct/selectCancelOrder.jsp");
			OKView.forward(req, res);
			return;
		}
		
		
		if("getAll".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			PrService prSvc = new PrService();

//			String data = new ObjectMapper().writeValueAsString();
//			res.getWriter().print(data);
			return;
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
