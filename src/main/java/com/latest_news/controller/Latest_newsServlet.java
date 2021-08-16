package com.latest_news.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.latest_news.model.Latest_newsService;
import com.latest_news.model.Latest_newsVO;
import com.pageBean.PageBean;


@WebServlet("/Latest_newsServlet")
public class Latest_newsServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("action");
		
		if("getall".equals(action)){
			Integer start = new Integer(request.getParameter("start"));
			Integer rows = new Integer(request.getParameter("rows"));
			
			List<Latest_newsVO> list= new Latest_newsService().getAll(start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			response.getWriter().print(data);
			return;
			
		}
		if("update".equals(action)) {
			Integer ln_id = new Integer(request.getParameter("lnid"));
			String ln_con = request.getParameter("lncon");
			Integer ln_sta = new Integer(request.getParameter("lnsta"));
			
			Latest_newsService lns = new Latest_newsService();
			lns.updatelnVO(ln_con, ln_id,ln_sta);
			RequestDispatcher successView = request.
					getRequestDispatcher("back-end/LatestNews/selectLN.jsp");
			successView.forward(request, response);
			
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		String action = request.getParameter("action");
		
		response.setContentType("text/html;charset=utf-8");
		if("add".equals(action)) {
			String ln_con = request.getParameter("lncon");
			Latest_newsService lns = new Latest_newsService();
			lns.addLN(ln_con);
			response.getWriter().println("新增成功！");
			RequestDispatcher successView = request.
					getRequestDispatcher("back-end/LatestNews/selectLN.jsp");
			successView.forward(request, response);
			return;
			
			
			
			
		}
		if("checkLNbyPage".equals(action)) {
			System.out.println(action);
			String currentPageStr = request.getParameter("currentPage");
			String pageSizeStr = request.getParameter("pageSize");
			
			int currentPage = 0; //當前頁碼，如果不傳則默認當前為第一頁
			if(currentPageStr!=null && currentPageStr.length()>0) {
				currentPage = new Integer(currentPageStr);
			}else {
				currentPage = 1;
			}
			
			int pageSize = 0; //每一頁顯示條數，默認每一頁顯示五條
			if(pageSizeStr!=null && pageSizeStr.length()>0) {
				pageSize = new Integer(pageSizeStr);
			}else {
				pageSize = 5;
			}
			
			//調用service查詢pageBean對象
			System.out.println("嗨~");
			Latest_newsService lns = new Latest_newsService();
			PageBean<Latest_newsVO> pageBean = lns.pageQuery(currentPage, pageSize);
			String data = new ObjectMapper().writeValueAsString(pageBean);
			response.getWriter().print(data);
			
		}
	}
	

}
