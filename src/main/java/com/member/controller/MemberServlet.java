package com.member.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.tribes.membership.McastService;

import com.electronicwallet.model.ElectronicWalletService;
import com.electronicwallet.model.ElectronicWalletVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latest_news.model.Latest_newsService;
import com.latest_news.model.Latest_newsVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		response.setContentType("text/html; charset=utf-8"); 
		
		
		// 查看會員錢包餘額ajax
		
		if("confirm".equals(action)) {
            System.out.println("helloFromRegister");
            String mem_acc = request.getParameter("account");
            System.out.println(mem_acc);
            MemberService ms = new MemberService();
            ms.changeStaTo1(mem_acc);
            response.sendRedirect("/CFA101G4/front-end/confirm_success.html");
        }
		
		if("checkEle".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO= (MemberVO) session.getAttribute("memberVO");
			Integer mem_id= memberVO.getMem_id();
			MemberService ms = new MemberService();
			Integer mem_ele = ms.checkEle(mem_id);
			response.getWriter().print(mem_ele);
			return;
		}
		
		if("findoneMem".equals(action)) {
			MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
			Integer memid =	memberVO.getMem_id();
			MemberVO memVO = new MemberService().getOnee(memid);
			ObjectMapper mapper = new ObjectMapper();
			 mapper.writeValue(response.getWriter(), memVO);
		}
		
		//會員中心左側登入後自動抓取個人身分資料
		if("findSingleMem".equals(action)) {
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			HttpSession session = request.getSession(); //到session中尋找
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			List<MemberVO> list =  new MemberService().getOne(memberVO.getMem_id());
			MemberVO member = list.get(0);
			ObjectMapper mapper = new ObjectMapper();	
			String data = mapper.writeValueAsString(member);
			PrintWriter out = response.getWriter();
			out.println(data);
			return;
		}
		//列出所有站內通知
		if("getAllNotice".equals(action)) {
			HttpSession session = request.getSession(); //到session中尋找
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			NoticeService ns = new NoticeService();
			List<NoticeVO> list = ns.getAllNotice(memberVO.getMem_id());
			new ObjectMapper().writeValue(response.getWriter(), list);
			return;
		}
		//新增圖片時及時讀取至左上方icon
		if("readIcon".equals(action)) {	
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();
			String mem_acc = (String) request.getSession().getAttribute("account");			
			byte[] icon = new MemberService().getIconBlob(mem_acc);
			String data = Base64.getEncoder().encodeToString(icon);
			out.println(data);
			return;
		}
		//會員登出
		if("exit".equals(action)) {
			HttpSession session = request.getSession();
			session.setAttribute("memberVO", null);
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/front-end/FrontMain.jsp");
			return;
		}
		//拿到所有交易紀錄
		if("getAllLog".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO mv = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = mv.getMem_id();
			ElectronicWalletService ews = new ElectronicWalletService();
			List<ElectronicWalletVO> list = ews.getAll(mem_id);
			String data = new ObjectMapper().writeValueAsString(list);
			System.out.println("安安"+data);
			response.getWriter().println(data);
			return;
		}
		//列出一筆最新消息
		if("listOneLN".equals(action)){
			String data = request.getParameter("ln_id");
			Integer ln_id= new Integer(data);
			Latest_newsService lns = new Latest_newsService();
			List<Latest_newsVO> list = lns.getOneLN(ln_id);
			String oneLN = new ObjectMapper().writeValueAsString(list);
			response.getWriter().println(oneLN);
			return;
		}
		//為沒有頭像的會員新增一個預設圖片
		if("insertDefaultIconIntoDB".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO mv = (MemberVO)session.getAttribute("memberVO");
			String mem_acc = mv.getMem_acc();			
			InputStream is = new FileInputStream("C:/jQuery/workspace/CFA101G4/WebContent/Img/CSS_pic/uploadPic.png");
			byte[] mem_pic = new byte[is.available()];
			is.read(mem_pic);
			is.close();
			MemberService ms = new MemberService();
			ms.insertDefaultIcon(mem_pic, mem_acc);
			return;
		}
		//新增一筆交易紀錄以及會員錢包儲值
		if("insertNewPayment".equals(action)) {
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
			return;
		}
		if("checkPWExist".equals(action)) {
			String mem_pw = request.getParameter("mem_pw");	
			MemberService ms = new MemberService();
			MemberVO memberVO = (MemberVO)request.getSession().getAttribute("memberVO");
			String mem_acc = memberVO.getMem_acc();
			List<MemberVO> list = ms.checkMemPW(mem_acc, mem_pw);
			System.out.println(list);
			PrintWriter out = response.getWriter();
			boolean flag = true;
			
			if(list.isEmpty()) {
				flag = false;
			}else {
				flag = true;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if(flag==true) {
				map.put("userExist", true);
				map.put("msg", "密碼正確");
			}else {
				map.put("userExist", false);
				map.put("msg", "密碼錯誤");
				
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, map);
			return;
			}
			if("getAllMembers".equals(action)) {
				MemberService ms = new MemberService();
				List<MemberVO> all = ms.getAll();
				ObjectMapper mapper = new ObjectMapper();
				String data = mapper.writeValueAsString(all);
				response.getWriter().write(data);
				return;
			}
			
			
			if("changeStaTo0".equals(action)){
				Integer mem_id = new Integer(request.getParameter("mem_id"));
				Integer mem_sta = new Integer(request.getParameter("mem_sta"));
				MemberService ms = new MemberService();
				ms.changeStaTo0(mem_sta ,mem_id);
				RequestDispatcher ok = request.getRequestDispatcher("/back-end/Member/selectMember.jsp");
				ok.forward(request, response);
				return;
			}
			
			if("checkCode".equals(action)) {
				String code = request.getParameter("code");
				HttpSession session = request.getSession();
				String checkCode = (String)session.getAttribute("checkCode");
				System.out.println(checkCode);
				if(code.equals(checkCode)) {
					response.getWriter().write("1");
				}else {
					response.getWriter().write("0");
				}
				return;
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String action = request.getParameter("action");
		response.setContentType("text/html; charset=utf-8"); 
		
		if("imgto64".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO= (MemberVO) session.getAttribute("memberVO");
			List<MemberVO> list =  new MemberService().getOne(memberVO.getMem_id());
			MemberVO member = list.get(0);
			String data = Base64.getEncoder().encodeToString(member.getMem_pic());
			response.getWriter().print(data);
			return;
		}
		
		if("changeMemberStatus".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			String mem_acc = memberVO.getMem_acc();
			System.out.println("嗨"+mem_acc);
			MemberService ms = new MemberService();
			ms.changeMemberStatus(mem_acc);
			response.getWriter().print("更改會員狀態成功！");
			return;
		}
		
		if("uploadPic".equals(action)) {
			System.out.println("我到這了");
			Part part = request.getPart("upfile");	
			String mem_acc = (String) request.getSession().getAttribute("account");
			InputStream in = part.getInputStream();
			byte[] mem_pic = new byte[in.available()];
			in.read(mem_pic);
			in.close();
			MemberService ms = new MemberService();
			ms.uploadIconBlob(mem_pic, mem_acc);
			return;
		}
		
		if("updateMem".equals(action)) {
			response.setContentType("text/html;charset=utf-8");
			String mem_acc = request.getParameter("account");
			String mem_name = request.getParameter("name");
			String mem_email = request.getParameter("email");
			String mem_tel = request.getParameter("tel");
			String mem_gender = request.getParameter("gender");
			String mem_bth = request.getParameter("bth");		
			Integer mem_sex = new Integer(mem_gender);
			
			MemberVO mv = new MemberVO();
			mv.setMem_acc(mem_acc);
			mv.setMem_name(mem_name);
			mv.setMem_email(mem_email);
			mv.setMem_tel(mem_tel);
			mv.setMem_sex(mem_sex);
			mv.setMem_bth(mem_bth);
			
			MemberService ms = new MemberService();
			ms.updateMem(mv);
			
			response.getWriter().println("成功更新資料！");
			return;
		}
		if("getDefaultIcon".equals(action)) {

//				System.out.println("hello");
				InputStream is = new FileInputStream("C:/CFA101G4_workspace/CFA101G4/WebContent/Img/CSS_pic/uploadPic.png");
				byte[] mem_pic = new byte[is.available()];
				is.read(mem_pic);
				is.close();
//				System.out.println(mem_pic);
				String data = Base64.getEncoder().encodeToString(mem_pic);
				response.getWriter().println(data);
				return;
		}
		if("updateMemPW".equals(action)) {
			String mem_pw = request.getParameter("mem_pw");
			System.out.println(mem_pw);
			MemberVO memberVO = (MemberVO)request.getSession().getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			new MemberService().updateMemPW(mem_id, mem_pw);
			response.getWriter().println("成功更新密碼！");
			return;
		}
		
		
		if("checkEmail".equals(action)) {
			String mem_email = request.getParameter("email");
			System.out.println(mem_email);
			MemberService ms = new MemberService();
			List<MemberVO> list = ms.checkEmail(mem_email);
			
			System.out.println(list);
			if(list.isEmpty()) {
				response.getWriter().write("0");
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("memberVO", list.get(0));
				response.getWriter().write("1");
			}
			return;
		}
		
		
		if("sendEmailForNewPW".equals(action)) {
			String to = request.getParameter("email");
			System.out.println(to);	
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			String mem_acc = memberVO.getMem_acc();
			String subject = "重設您的天堂鳥購物密碼";
			String messageText="<h3>"+mem_acc+"您好，我們已經收到您重設密碼的申請。請點擊<a href=\"http://35.194.147.13/CFA101G4/front-end/setNewPW.html\">連結</a>，將會為您的帳戶重新設置新密碼。或是複製以下連結至您的瀏覽器開啟：<br>http://35.194.147.13/CFA101G4/front-end/setNewPW.html<br>如果您沒有申請此重設密碼的需求，請立刻聯絡我們的天堂鳥客服團隊</h3>";
			sendMail sm = new sendMail(to, subject, messageText);
			Thread t1 = new Thread(sm);
			t1.start();
			response.getWriter().write("更改密碼信已寄出！");
			return;
		}
		
		
		if("getMem_ele".equals(action)) {
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			MemberService ms = new MemberService();
			List<MemberVO> list = ms.getOne(mem_id);
			Integer ele = list.get(0).getMem_ele();
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(ele);
			System.out.println(data);
			response.getWriter().write(data);
			return;
		}
	}

}
