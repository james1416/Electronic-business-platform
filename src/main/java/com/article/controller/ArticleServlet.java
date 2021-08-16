package com.article.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;

public class ArticleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		//發文請求
		if ("insert".equals(action)) { 
			PrintWriter out = res.getWriter();
			try {
				MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
				Integer memid =	memberVO.getMem_id();
				String title = req.getParameter("title");
				String text = req.getParameter("text");
				if(title.trim().isEmpty()||text.trim().isEmpty()) {
					out.print("0");
					out.close();
					return;
				}
				ArticleService artSvc = new ArticleService();
				artSvc.addArt(memid, title, text);
				out.print("1");
				out.close();
			} catch (Exception e) {
				out.print("0");
				out.close();
			}
		}
		if ("getSomeAt".equals(action)||("getSomeAtB".equals(action))) { 
				String title = req.getParameter("title");
				ArticleService serch = new ArticleService();
				List<ArticleVO> serchend =serch.getSomeAt(title);
				
				if("getSomeAt".equals(action)) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.writeValue(res.getWriter(), serchend);
				}
				if("getSomeAtB".equals(action)) {
					if(serchend == null) {
						res.getWriter().print("<script>Swal.fire({ icon: 'question',text: '查無相關文章', }); </script>");
						String url="/front-end/SearchPage.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
					}
						
					
					req.setAttribute("SomeB",serchend);
					String url="/front-end/SearchPage.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}
					
		}
			//編輯文章資料帶入
		if ("USR_For_Update".equals(action)) { 
			try {
				Integer atid = new Integer(req.getParameter("atid"));
				ArticleService artSvc = new ArticleService();
				ArticleVO artVO = artSvc.getOneAt(atid);
				req.setAttribute("artVO",artVO);
				String url ="/front-end/MyArticle/RenewMyArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.getMessage();
			}
		}
		//編輯文章資料寫入
		if ("update".equals(action)) { 
			PrintWriter out = res.getWriter();

			try {
				Integer atid = new Integer(req.getParameter("atid").trim());
				String title = req.getParameter("title");
				if (title == null || title.trim().length() == 0) {
					out.print(0);
				}
				String text = req.getParameter("text");
				if (text == null || text.trim().length() == 0) {
					out.print(0);
				}

				ArticleService artSvc = new ArticleService();
				artSvc.updateArt(atid, title, text);
				out.print(1);

			} catch (Exception e) {
					e.getMessage();
			}
		}
		//刪除文章
		if ("delete_fot_At".equals(action)) {
				PrintWriter out = res.getWriter();
				Integer atid = new Integer(req.getParameter("atid").trim());
				ArticleService artSvc = new ArticleService();
				artSvc.deleteArticle(atid);
				out.print(1);
				out.close();
		}
		//單一熱門文章
		if("getonehot".equals(action)) {
			ArticleService artSvc = new ArticleService();
			ArticleVO artVO = artSvc.getoneHot();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(res.getWriter(), artVO);
		}
		if("getonenew".equals(action)) {
			ArticleService artSvc = new ArticleService();
			ArticleVO artVO = artSvc.getoneNew();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(res.getWriter(), artVO);
		}
	
//		最新文章
		if("allArticle".equals(action)) {
			ArticleService artSvc = new ArticleService();
			List<ArticleVO> allAT=artSvc.getAll();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(res.getWriter(), allAT);
		}
//		熱門文章
		if("allHotArticle".equals(action)) {
			ArticleService artSvc = new ArticleService();
			List<ArticleVO> allHotAt=artSvc.getHotAT();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(res.getWriter(), allHotAt);
		}
	}
}
