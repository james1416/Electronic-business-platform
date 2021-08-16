package com.articlereport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.articlereport.model.ArticleReportService;
import com.articlereport.model.ArticleReportVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/AdminReportList")
public class AdminReportList extends HttpServlet {
    public AdminReportList() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		if("admingetAll".equals(action)) {
			ArticleReportService ArtRP = new ArticleReportService();
			List<ArticleReportVO> AtReport = ArtRP.getAll();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(response.getWriter(), AtReport);
		}
		if("5".equals(action)) {
			
		}
		if("PASS".equals(action)) {
			Integer repid = new Integer(request.getParameter("repid"));
			ArticleReportService artRP = new ArticleReportService();
			artRP.reportPass(repid);
			out.print(1);
			out.close();
		}
		if("REJECT".equals(action)) {
			Integer repid = new Integer(request.getParameter("repid"));
			ArticleReportService artRP = new ArticleReportService();
			artRP.reportReturn(repid);
			out.print(1);
			out.close();
			
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
