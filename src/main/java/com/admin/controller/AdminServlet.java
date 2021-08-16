package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		List<String> errorMsgs = new LinkedList<String>();
		
//		------------------- 登入開始 ------------------------
		if("login".equals(action)) {
			String adminacc= req.getParameter("adminacc");
			if (adminacc == null || adminacc.trim().length() == 0) {
				errorMsgs.add("請輸入帳號");
			}
			String adminpw = req.getParameter("adminpw");
			if(adminpw == null || adminpw.trim().length() == 0) {
				errorMsgs.add("請輸入密碼");
			}
			
			if(!errorMsgs.isEmpty()) {
				AdminVO adminVO = new AdminVO();
				adminVO.setAdmin_acc(adminacc);
				adminVO.setAdmin_pw(adminpw);
				req.setAttribute("adminVO", adminVO);
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher failview = req.getRequestDispatcher("/backLogin.jsp");
				failview.forward(req, res);
				return;
			}
//		------------ 若前面有錯就先不讓他進入資料庫消耗資源 ------------
			
//		------------ 比對資料庫若失敗就跳回 ------------
			AdminService as = new AdminService();
			AdminVO admin = as.login(adminacc, adminpw);
			if(admin == null) {
				errorMsgs.add("此帳號密碼無效，請重新輸入");
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher okview = req.
						getRequestDispatcher("/backLogin.jsp");
				okview.forward(req, res);
				return;
			}
			
//			------------- 判斷是否停權 ------------
			if(admin.getAdmin_sta() == 0) {
				errorMsgs.add("此帳號已停權");
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher okview = req.
						getRequestDispatcher("/backLogin.jsp");
				okview.forward(req, res);
				return;
			}
//			------------- 增加登入時間 ------------
			as.updateLoginlog(admin.getAdmin_id());
			HttpSession session = req.getSession();
			session.setAttribute("adminVO", admin);
			RequestDispatcher okview = req.
					getRequestDispatcher("/back-end/BackIndex.jsp");
			okview.forward(req, res);
			return;
		}
//		------------------- 登入結束 ------------------------
		
		
		
		
//		------------------ 確認帳號有無重複 -------------------
		if("checkAcc".equals(action)) {
			PrintWriter out = res.getWriter();
			String adminacc = req.getParameter("adminacc");
			AdminService as= new AdminService();
			Boolean check = as.checkAcc(adminacc);
			out.print(check);
			return;
		}
		
		
		
		
//		------------------ 查詢一個開始 ---------------------
		if("findOne".equals(action)) {
			Integer adminid = new Integer(req.getParameter("admin_id"));
			AdminService as = new AdminService();
			AdminVO adminVO = as.find(adminid);
			req.setAttribute("AdminVO", adminVO);
			boolean showupdata = true;
			req.setAttribute("showupdata", showupdata);
			RequestDispatcher okview = req.
					getRequestDispatcher("/back-end/Admin/selectAllAdmin.jsp");
			okview.forward(req, res);
			return;
		}
		
		
		
		
		
//		------------------- 修改一個 -------------------------
//		------------------- 這邊的錯誤判斷交給ajaxㄌ ------------
		if("updata".equals(action)) {
			Integer admin_id =new Integer(req.getParameter("adminid"));
			String admin_acc = req.getParameter("adminacc");
			String admin_pw = req.getParameter("adminpw");
			Integer admin_sta = new Integer(req.getParameter("adminsta"));
			AdminService as = new AdminService();
			Set<Integer> ChengeAutid = new HashSet<Integer>();
			// 若被拔除所有功能的話則會為null
			if (req.getParameterValues(req.getParameter("adminid")) != null) {
				String id[] = req.getParameterValues(req.getParameter("adminid"));
				for (int i = 0; i < id.length; i++) {
					ChengeAutid.add(Integer.parseInt(id[i]));
				}
			}
			as.updata(admin_id, admin_acc, admin_pw, admin_sta , ChengeAutid);
			RequestDispatcher successView = req.
					getRequestDispatcher("/back-end/Admin/selectAllAdmin.jsp");
			successView.forward(req, res);
			return;
		}
		
		
		
//		---------------- 新增一個 ---------------
		if("add".equals(action)) {
			String adminacc = req.getParameter("adminacc");
			String adminpw = req.getParameter("adminpw");
			
			Set<Integer> ChengeAutid = new HashSet<Integer>();

			// 若被拔除所有功能的話則會為null
			if (req.getParameterValues("aut") != null) {
				String id[] = req.getParameterValues("aut");
				for (int i = 0; i < id.length; i++) {
					ChengeAutid.add(Integer.parseInt(id[i]));
				}
			}
			AdminService as = new AdminService();
			as.add(adminacc, adminpw,ChengeAutid);
//			RequestDispatcher successView = req.
//					getRequestDispatcher("/back-end/Admin/selectAllAdmin.jsp");
//			successView.forward(req, res);
			return;
			
		}
		
		
		if("search".equals(action)) {
			Map<String, String[]>map = req.getParameterMap();
			AdminService as = new AdminService();
			List<AdminVO> list = as.search(map);
			req.setAttribute("list", list);
			RequestDispatcher successView = req.
					getRequestDispatcher("/back-end/Admin/searchAdmin.jsp");
			successView.forward(req, res);
			return;
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
