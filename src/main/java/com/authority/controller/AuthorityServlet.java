package com.authority.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.authority.model.AuthorityService;
import com.authority.model.AuthorityVO;

@WebServlet("/AuthorityServlet")
public class AuthorityServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		List<String> errorMsgs =  new LinkedList<String>();
		
		
//		-----------------  查詢一個  -----------------		
		if("findone".equals(action)) {
			Integer aut_id=new Integer(req.getParameter("autid"));
			AuthorityService as = new AuthorityService();
			AuthorityVO authorityVO = as.findone(aut_id);
			req.setAttribute("findAutVO", authorityVO);
			boolean openFind = true;
			req.setAttribute("openFind", openFind);
			RequestDispatcher okView = req.
					getRequestDispatcher("/back-end/Authority/selectAllAuthority.jsp");
			okView.forward(req, res);
			
		}
		
//		-----------------  新增  -----------------

		if("add".equals(action)) {
			String aut_name = req.getParameter("autname");
			String aut_con = req.getParameter("autcon");
			if(aut_name == null || aut_name.trim().length() == 0) {
				errorMsgs.add("權限名稱請勿為空");
			}
			if(aut_con == null || aut_con.trim().length() == 0 ) {
				errorMsgs.add("權限說明請勿為空");
			}
			if(!errorMsgs.isEmpty()) {
				
				AuthorityVO authorityVO= new AuthorityVO();
				authorityVO.setAut_name(aut_name);
				authorityVO.setAut_con(aut_con);
				req.setAttribute("AuthorityVO", authorityVO);
				req.setAttribute("addErrorMsgs", errorMsgs);
				RequestDispatcher failView = req.
						getRequestDispatcher("/back-end/Authority/selectAllAuthority.jsp");
				failView.forward(req, res);
				return;
			}
			AuthorityService as = new AuthorityService();
			as.add(aut_name, aut_con);
			RequestDispatcher okView = req.
					getRequestDispatcher("/back-end/Authority/selectAllAuthority.jsp");
			okView.forward(req, res);
		}
		
		
		
//		-----------------  修改 -----------------
		if("updata".equals(action)) {
			Integer aut_id= new Integer(req.getParameter("autid"));
			String aut_name = req.getParameter("autname");
			String aut_con = req.getParameter("autcon");
			if(aut_name == null || aut_name.trim().length() == 0) {
				errorMsgs.add("權限名稱請勿為空");
			}
			if(aut_con == null || aut_con.trim().length() == 0 ) {
				errorMsgs.add("權限說明請勿為空");
			}
			if(!errorMsgs.isEmpty()) {
				AuthorityVO authorityVO= new AuthorityVO();
				authorityVO.setAut_name(aut_name);
				authorityVO.setAut_con(aut_con);
				authorityVO.setAut_id(aut_id);
				boolean openFind = true;
				req.setAttribute("openFind", openFind);
				req.setAttribute("findAutVO", authorityVO);
				req.setAttribute("updataErrorMsgs", errorMsgs);
				RequestDispatcher failView = req.
						getRequestDispatcher("/back-end/Authority/selectAllAuthority.jsp");
				failView.forward(req, res);
				return;
			}
			AuthorityService as = new AuthorityService();
			as.updata(aut_id, aut_name, aut_con);
			RequestDispatcher okView = req.
					getRequestDispatcher("/back-end/Authority/selectAllAuthority.jsp");
			okView.forward(req, res);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
