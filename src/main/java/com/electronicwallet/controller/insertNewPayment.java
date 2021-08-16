package com.electronicwallet.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.electronicwallet.model.ElectronicWalletService;
import com.member.model.MemberVO;


@WebServlet("/insertNewPayment")
public class insertNewPayment extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		String ele_rec = request.getParameter("ele_rec");
		Integer ele_mon = new Integer(request.getParameter("ele_mon"));
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		Integer ele_memid = memberVO.getMem_id();
		Long Date = new Date().getTime();
		String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date);
		Timestamp ele_time = java.sql.Timestamp.valueOf(nowdate);
		ElectronicWalletService ews = new ElectronicWalletService();
		ews.insertNewPayment(ele_memid, ele_time, ele_rec, ele_mon);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
