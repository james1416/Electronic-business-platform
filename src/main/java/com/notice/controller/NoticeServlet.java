package com.notice.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		response.setContentType("text/html; charset=utf-8");

		
		
		
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = request.getParameter("requestURL");
			
			try {
			String id = request.getParameter("mem_id");
			int mem_id = Integer.parseInt(id);
			NoticeService ns = new NoticeService();
			ns.deleteOneNotice(mem_id);
			
//			List<NoticeVO> list = ns.getAllNotice();
			
//			request.setAttribute("list", list);
			
			String url = requestURL;
			RequestDispatcher successView = request.getRequestDispatcher(url);
			
			successView.forward(request, response);
//			System.out.println(request.getServletPath());  拿到/notice
//			System.out.println(request.getContextPath());  拿到/CFA101G4
			System.out.println(request.getRequestURI());
			}catch (Exception e) {
				
			}
		}
		
		
		if("checkAllNotices".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer nt_memid = memberVO.getMem_id();
			NoticeService ns = new NoticeService();
			List<NoticeVO> allNotice = ns.getAllNotice(nt_memid);
			String data = new ObjectMapper().writeValueAsString(allNotice);
			response.getWriter().write(data);
		}
		//0804 10:42新增
		if("changeViewTo1".equals(action)) {
			Integer nt_id = new Integer(request.getParameter("nt_id"));
			System.out.println(nt_id);
			NoticeService ns = new NoticeService();
			ns.changeViewTo1(nt_id);
			response.getWriter().print("success");
		}
		
		if("checkNoticebyMemid".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer nt_memid = memberVO.getMem_id();
			NoticeService ns = new NoticeService();
			List<NoticeVO> list= ns.getAllNotice(nt_memid);
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			response.getWriter().print(data);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
