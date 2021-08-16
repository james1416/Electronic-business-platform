package com.articlecollection.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.articlecollection.model.ArticleCollectionService;
import com.articlecollection.model.ArticleCollectionVO;
import com.member.model.MemberVO;

@WebServlet("/ArticlecollectionServlet")
public class ArticleCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArticleCollectionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
			//收藏文章
		if ("COLLECTION".equals(action)) {
			Integer atid = new Integer(request.getParameter("atid"));
			MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			int i =0;
			try {
				ArticleCollectionService addcol = new ArticleCollectionService();
				addcol.addCol(atid, memid);
				i=1;
				out.print(i);
				//如果已收藏將會移除收藏
			} catch (Exception e) {
				if(i==0) {
					ArticleCollectionService artColSvc = new ArticleCollectionService();
					artColSvc.deleteforCol(atid, memid);
					out.print(i);
				}
			}
		}
	}
}
