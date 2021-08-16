package com.electronicwallet.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.electronicwallet.model.ElectronicWalletService;
import com.electronicwallet.model.ElectronicWalletVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latest_news.model.Latest_newsService;
import com.latest_news.model.Latest_newsVO;
import com.member.model.MemberVO;
import com.pageBean.PageBean;


@WebServlet("/ElectronicwalletServlet")
public class ElectronicwalletServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=utf-8");
		
		if("checkMyWalletbyPage".equals(action)) {
			System.out.println(action);
			String currentPageStr = request.getParameter("currentPage");
			String pageSizeStr = request.getParameter("pageSize");
			HttpSession session = request.getSession();
			MemberVO mv = (MemberVO)session.getAttribute("memberVO");
			Integer ele_memid = mv.getMem_id();
			int currentPage = 0; //當前頁碼，如果不傳則默認當前為第一頁
			if(currentPageStr!=null && currentPageStr.length()>0) {
				currentPage = new Integer(currentPageStr);
			}else {
				currentPage = 1;
			}
			
			int pageSize = 0; //每一頁顯示條數，默認每一頁顯示五條
			if(pageSizeStr!=null && pageSizeStr.length()>0) {
				pageSize = new Integer(pageSizeStr);
			}else {
				pageSize = 5;
			}
			
			//調用service查詢pageBean對象
			System.out.println("嗨~");
			ElectronicWalletService ews = new ElectronicWalletService();
			PageBean<ElectronicWalletVO> pageBean = ews.pageQuery(ele_memid, currentPage, pageSize);
			String data = new ObjectMapper().writeValueAsString(pageBean);
			response.getWriter().print(data);
			
		}
		if("getOneLog".equals(action)) {
			System.out.println("HI");
			Integer ele_id = new Integer(request.getParameter("ele_id"));
			HttpSession session = request.getSession();
			MemberVO mv = (MemberVO)session.getAttribute("memberVO");
			Integer ele_memid = mv.getMem_id();
			ElectronicWalletService ews = new ElectronicWalletService();
			ElectronicWalletVO ewvo = ews.getOneLog(ele_memid, ele_id);
			String data = new ObjectMapper().writeValueAsString(ewvo);
			response.getWriter().println(data);
		}
		if("storeMoney".equals(action)) {
			System.out.println("hello");
			String cardNumber = request.getParameter("cardNumber");
			String expiryMM = request.getParameter("expityMonth");
			String expiryYY = request.getParameter("expityYear");
			String cvCode = request.getParameter("cvCode");
			String card = "1111111111111111";
			String MM = "11";
			String YY = "11";
			String cv = "111";
			System.out.println(cardNumber);
			if(card.equals(cardNumber) && MM.equals(expiryMM) && YY.equals(expiryYY) && cv.equals(cvCode)) {
				System.out.println("二階段");
				Integer ele_mon = new Integer(request.getParameter("ele_mon"));
				String ele_rec = "您以信用卡儲值了"+ele_mon+"元";
				HttpSession session = request.getSession();
				MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
				Integer ele_memid = memberVO.getMem_id();
				Long Date = new Date().getTime();
				String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date);
				Timestamp ele_time = java.sql.Timestamp.valueOf(nowdate);
				ElectronicWalletService ews = new ElectronicWalletService();
				ews.insertNewPayment(ele_memid, ele_time, ele_rec, ele_mon);
				response.getWriter().print("1");
			}
			else{
				System.out.println("您的信用卡資訊有誤！");
				response.getWriter().print("0");
			}
		}
	}

}
