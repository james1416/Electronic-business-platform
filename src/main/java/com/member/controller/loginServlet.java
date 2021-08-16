package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		MemberService ms = new MemberService();
		List<MemberVO> list = ms.login(account, password);

		
		
		int flag = 0;
		if(list.isEmpty()) {
			flag = 0;
			System.out.println(flag);
			out.print(flag);
		}else {
			flag = 1;		
			System.out.println(flag);
			HttpSession session = request.getSession();
			session.setAttribute("account", account);	
			session.setAttribute("memberVO", list.get(0));
			out.print(flag);
		}
		

	}

}
