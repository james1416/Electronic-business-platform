package com.articlelike.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.articlelike.model.ArticleLikeService;
import com.articlelike.model.ArticleLikeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;

@WebServlet("/ArticleLikeServlet")
public class ArticleLikeServlet extends HttpServlet {
    public ArticleLikeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("LIKE".equals(action)) {
			Integer atid = new Integer(req.getParameter("atid"));
			PrintWriter out = res.getWriter();
			MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			int i =0;
			try {
				ArticleLikeService artlikeSvc = new ArticleLikeService();
				artlikeSvc.addlike(atid, memid);
				ArticleService artSvc = new ArticleService();
				artSvc.atLike(atid);
				i=1;
				out.print(i);
			} catch (Exception e) {
				if(i==0) {
					ArticleLikeService artlikeSvc = new ArticleLikeService();
					artlikeSvc.deleteforlike(atid, memid);
					ArticleService artSvc = new ArticleService();
					artSvc.deletelike(atid);
					out.print(i);
				}
			}
		}
		if("comparemyLike".equals(action)) {
			MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			ArticleLikeService artlikeSve = new ArticleLikeService();
			List<ArticleLikeVO> likeVO =artlikeSve.getAllmylike(memid);
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(res.getWriter(), likeVO);
		}
		if("likecheck".equals(action)) {
			MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			PrintWriter out = res.getWriter();
			Integer atid = new Integer(req.getParameter("atid").trim());
			ArticleLikeService artlikeSve = new ArticleLikeService();
			ArticleLikeVO artVO =artlikeSve.getoneLK(memid, atid);
			if(artVO==null) {
				out.print(0);
			}else {
				out.print(1);
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
