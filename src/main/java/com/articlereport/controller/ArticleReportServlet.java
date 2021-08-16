package com.articlereport.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.articlereport.model.ArticleReportService;
import com.articlereport.model.ArticleReportVO;
import com.member.model.MemberVO;
import com.messagereport.model.MessageReportService;
import com.messagereport.model.MessageReportVO;

@WebServlet("/ArticlereportServlet")
public class ArticleReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		Integer memid =	memberVO.getMem_id();
		//查全部文章檢舉
		ArticleReportService atRSvc = new ArticleReportService();
		List<ArticleReportVO> AtReport = atRSvc.getAll(memid);
		//查全部回復檢舉
		MessageReportService msRSvc = new MessageReportService();
		List<MessageReportVO> MsReport = msRSvc.getAll(memid);
		
		
		request.setAttribute("MsReport",MsReport);
		request.setAttribute("AtReport", AtReport);
		String url = "/front-end/MyReport/MyReport.jsp";
		RequestDispatcher successView = request.getRequestDispatcher(url); 
		successView.forward(request, response);

	}
}
