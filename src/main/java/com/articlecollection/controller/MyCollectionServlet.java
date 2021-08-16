package com.articlecollection.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.articlecollection.model.ArticleCollectionService;
import com.articlecollection.model.ArticleCollectionVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;

@WebServlet("/MyCollectionServlet")
public class MyCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyCollectionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action =request.getParameter("action");
		if ("myarticleCol".equals(action)) { 
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		Integer memid =memberVO.getMem_id();
		
		ArticleCollectionService artCOL = new ArticleCollectionService();
		List<ArticleCollectionVO> mycol = artCOL.getAll(memid) ;
		
		request.setAttribute("mycol", mycol);
		String url = "/front-end/MyCollection/MyCollection.jsp";
		RequestDispatcher successView = request.getRequestDispatcher(url); 
		successView.forward(request, response);
		
		}
		if("coll".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer memid =memberVO.getMem_id();
			
			
			ArticleCollectionService artCOL = new ArticleCollectionService();
			List<ArticleCollectionVO> mycol = artCOL.getAll(memid);
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(response.getWriter(), mycol);
		}
		if("colchecck".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer memid =memberVO.getMem_id();
			Integer atid = new Integer(request.getParameter("atid").trim());
			PrintWriter out = response.getWriter();
			ArticleCollectionService artCOL = new ArticleCollectionService();
			ArticleCollectionVO artVO =artCOL.checkCol(memid, atid);
			if(artVO==null) {
				out.print(0);
			}else {
				out.print(1);
			}
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
