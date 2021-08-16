package com.messagereport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.articlemessage.model.ArticleMessageService;
import com.articlemessage.model.ArticleMessageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.messagereport.model.MessageReportService;
import com.messagereport.model.MessageReportVO;

@WebServlet("/MessageReportServlet")
public class MessageReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MessageReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//文章留言檢舉資料帶入
		if("atMessageRep".equals(action)) {
			Integer msgid = new Integer(request.getParameter("msgid").trim());
			ArticleMessageService artMsSvc = new ArticleMessageService();
			ArticleMessageVO artMSVO = artMsSvc.findmessagePK(msgid);
			request.setAttribute("artMSVO", artMSVO);
			String url ="/front-end/Article/MessageReport.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		//文章留言檢舉送出
		if("atMessageRepSubmit".equals(action)){
			PrintWriter out = response.getWriter();
			MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			Integer msgid = new Integer(request.getParameter("msgid").trim());
			String msReptext = request.getParameter("text");
			if(msReptext ==null || msReptext.trim().isEmpty()) {
				out.print(0);
				out.close();
				return;
			}
			MessageReportService messageRepSvc = new  MessageReportService();
			messageRepSvc.addMessageRep(msgid, memid, msReptext);
			out.print(1);
			out.close();
		}
		if("admingetAll".equals(action)) {
			MessageReportService msgRP = new MessageReportService();
			List<MessageReportVO> msgRpVO = msgRP.admingetAll();
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(response.getWriter(), msgRpVO);
		}
		if("PASS".equals(action)) {
			PrintWriter out = response.getWriter();
			MessageReportService msgSvc = new MessageReportService();
			Integer repid = new Integer(request.getParameter("repid").trim());
			msgSvc.reportPass(repid);
			out.print(1);
			out.close();
		}
		if("REJECT".equals(action)) {
			PrintWriter out = response.getWriter();
			MessageReportService msgSvc = new MessageReportService();
			Integer repid = new Integer(request.getParameter("repid").trim());
			msgSvc.reportReject(repid);
			out.print(1);
			out.close();
		}
	}
}
