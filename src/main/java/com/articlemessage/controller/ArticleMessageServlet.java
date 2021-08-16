package com.articlemessage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.articlemessage.model.ArticleMessageService;
import com.member.model.MemberVO;

@WebServlet("/ArticlemessageServlet")
public class ArticleMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleMessageServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if ("Reply".equals(action)) {
			PrintWriter out = response.getWriter();
			try {
				MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
				Integer memid =	memberVO.getMem_id();
				Integer atid = new Integer(request.getParameter("atid"));
				String text = request.getParameter("text");
		
				if(text.trim().isEmpty()) {
					out.print(0);
					out.close();
					return;
				}
				ArticleMessageService articleMessageService = new ArticleMessageService();
				articleMessageService.addArtMsg(atid, memid, text);
				out.print(1);
				out.close();
			} catch (Exception e) {
					e.getMessage();
			}
		
			
		}
		if("dalete".equals(action)) {
			PrintWriter out = response.getWriter();
			Integer msgid = new Integer(request.getParameter("msgid").trim());
			ArticleMessageService articleMessageService = new ArticleMessageService();
			articleMessageService.deleteMsg(msgid);
			out.print(1);
		}
		if("updateMsg".equals(action)) {
			PrintWriter out = response.getWriter();
			Integer msgid = new Integer(request.getParameter("msgid").trim());
			MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			String msgtext = request.getParameter("msgtext");
			ArticleMessageService articleMessageService = new ArticleMessageService();
			articleMessageService.updateMsg(memid, msgid, msgtext);
			out.print(1);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
