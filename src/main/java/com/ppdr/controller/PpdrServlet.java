package com.ppdr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;
import com.ppdr.model.PpdrService;
import com.ppdr.model.PpdrVO;


@WebServlet("/PpdrServlet")
public class PpdrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		
		
		//取得自己被檢舉的商品
		if("getAllReport".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.html");
				okView.forward(req, res);
				return;
			}
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
//			selectPR
			PpdrService ppdrSvc = new PpdrService();
			List<PpdrVO> list = ppdrSvc.getall(memberVO.getMem_id());
	
			req.setAttribute("list", list);
			RequestDispatcher okView = req.
					getRequestDispatcher("/front-end/PreProduct/selectReportPrePd.jsp");
			okView.forward(req, res);
			return;
		}
		
		
	}	
		
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		doGet(req, res);
	}

}
