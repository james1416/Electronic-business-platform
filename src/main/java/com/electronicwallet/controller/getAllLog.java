package com.electronicwallet.controller;
import com.electronicwallet.model.ElectronicWalletService;
import com.electronicwallet.model.ElectronicWalletVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/getAllLog")
public class getAllLog extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		MemberVO mv = (MemberVO)session.getAttribute("memberVO");
		Integer mem_id = mv.getMem_id();
		ElectronicWalletService ews = new ElectronicWalletService();
		List<ElectronicWalletVO> list = ews.getAll(mem_id);
		String data = new ObjectMapper().writeValueAsString(list);
		response.getWriter().println(data);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
