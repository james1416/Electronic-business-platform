package com.article.controller;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.member.model.MemberVO;

@WebServlet("/MyArticleServlet")
public class MyArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyArticleServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
		Integer memid =	memberVO.getMem_id();
		ArticleService artSvc = new ArticleService();
		List<ArticleVO> mem = artSvc.getAll(memid);
		req.setAttribute("art", mem);
		String url = "/front-end/MyArticle/MyArticle.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		}
	}

