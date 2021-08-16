package com.articlereport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.articlereport.model.ArticleReportService;
import com.member.model.MemberVO;

@WebServlet("/ArticleReportList")
public class ArticleReportList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleReportList() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
	//此為檢舉表單資料帶入
		if("ATReport".equals(action)){
			Integer atid = new Integer(request.getParameter("atid").trim());
			ArticleService artSvc = new ArticleService();
			ArticleVO artVO = artSvc.getOneAt(atid);

			request.setAttribute("artVO",artVO);
			String url ="/front-end/Article/ArticleReport.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
		}
		//此為檢舉表單送出檢驗
		if("ATReporSubmit".equals(action)) {
			PrintWriter out = response.getWriter();
			MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			Integer atid = new Integer(request.getParameter("atid").trim());
			String repmsg = request.getParameter("text");
			
			if (repmsg  == null || repmsg .trim().length() == 0) {
				out.print(0);
				out.close();
			}
			ArticleReportService articleReportService = new ArticleReportService();
			articleReportService.addArticleRep(memid, atid, repmsg);
			out.print(1);
			out.close();
		}
		
	}

}
