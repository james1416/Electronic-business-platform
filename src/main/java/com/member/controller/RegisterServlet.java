package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberService;
import com.member.model.MemberVO;


@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=utf-8");
		if("checkAccExist".equals(action)) {
			String mem_acc = request.getParameter("account");
			response.setContentType("text/html; charset=utf-8");
			MemberService ms = new MemberService();
	
			List<MemberVO> list = ms.canThisAccBeUsed(mem_acc);
			
			System.out.println(list);
			PrintWriter out = response.getWriter();
			boolean flag = true;
			
			if(list.isEmpty()) {
				flag = true;
			}else {
				flag = false;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if(flag==false) {
				map.put("userExist", false);
				map.put("msg", "此帳戶已被使用");
			}else {
				map.put("userExist", true);
				map.put("msg", "此帳戶可使用");
				
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, map);
	}
}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=utf-8");
		if("register".equals(action)) {
			MemberService ms = new MemberService();

			MemberVO mv = new MemberVO();
			mv.setMem_acc(request.getParameter("account"));
			mv.setMem_pw(request.getParameter("password"));
			mv.setMem_name(request.getParameter("membername"));
			mv.setMem_uid(request.getParameter("UID"));
			mv.setMem_add(request.getParameter("address"));		
			mv.setMem_email(request.getParameter("email"));
			mv.setMem_tel(request.getParameter("tel"));
			//性別的String轉Integer
			Integer SEX = Integer.parseInt(request.getParameter("sex"));
			mv.setMem_sex(SEX);
			mv.setMem_bth(request.getParameter("bth"));
			//插入資料庫
			ms.insertMem(mv);
			//把此member物件存進session
//			HttpSession session = request.getSession();
//			session.setAttribute("account", request.getParameter("account"));
//			session.setAttribute("memberVO", mv);
			//準備JavaMail的發送
			String to = mv.getMem_email();
			String subject = "帳戶激活通知";
			String messageText = "<h1>你好！請點擊此<a href=\"http://35.194.147.13/CFA101G4/MemberServlet?action=confirm&account="+mv.getMem_acc()+"\">連結</a>以激活您的帳戶<br>請注意！鏈接將於30分鐘後失效！</h1>";
			sendMail sm = new sendMail(to, subject, messageText);
			Thread t1 = new Thread(sm);
			t1.start();
			t1 = null; //怕同時有人申請帳號 要讓他保持乾淨
			System.out.println("嗨我還沒好");
			response.getWriter().print("成功註冊！");
			System.out.println("嗨我好了");
		}
	}
}

