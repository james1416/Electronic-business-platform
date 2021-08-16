package com.article.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article.model.ArticleService;
import com.article.model.ArticleVO;

@WebServlet("/ViewmoreArticleServlet")
public class ViewmoreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ViewmoreArticleServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Integer atid = new Integer(request.getParameter("content"));
		ArticleService artSvc = new ArticleService();
		ArticleVO artVO=artSvc.getOneAt(atid);
		request.setAttribute("artVO", artVO);
		String url ="/front-end/Article/Viewmore.jsp";
		RequestDispatcher successView = request.getRequestDispatcher(url);
		successView.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
