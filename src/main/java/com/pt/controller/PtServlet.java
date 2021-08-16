package com.pt.controller;

import java.io.IOException;
import java.security.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;
import com.pd.model.PdService;
import com.pd.model.PdVO;
import com.pt.model.PtService;
import com.pt.model.PtVO;




@WebServlet("/PtServlet")
@MultipartConfig
public class PtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
				
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		if ("isTracking".equals(action)) { // 來自FrontMain.jsp的請求 判斷是否收藏商品
				
				
            
				/***************************1.接收請求參數****************************************/
				Integer pt_pdid = null;
				pt_pdid = new Integer(req.getParameter("PT_PDID"));
				
				HttpSession session = req.getSession();
				MemberVO memberVO =(MemberVO) session.getAttribute("memberVO");
				
				if(memberVO != null) {
				Integer pt_memid = memberVO.getMem_id();
				/***************************2.開始查詢資料****************************************/	
				PtService ptSvc = new PtService();
				boolean isTrack = ptSvc.isTracking(pt_memid , pt_pdid); //判斷是否收藏
					
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				res.getWriter().print(isTrack);
				
				
				// 沒有登入會員 重導回登入介面  (未成功)
				}else {
					RequestDispatcher okView = req.
							getRequestDispatcher("/front-end/frontLogin.jsp");
					okView.forward(req, res);
					return;
				}
				

		}
		//新增商品追蹤
	  if("addTracking".equals(action)) {  
		  	 System.out.println("進來新增追蹤");
		 	 HttpSession session = req.getSession();
		 	 MemberVO memberVO =(MemberVO) session.getAttribute("memberVO");
		 	/***************************1.接收請求參數****************************************/
			 Integer pt_pdid = new Integer(req.getParameter("PT_PDID"));
			 Integer pt_memid = memberVO.getMem_id();
			 /***************************2.開始新增資料****************************************/
			 PtService ptSvc = new PtService();
			 ptSvc.addPt(pt_memid, pt_pdid);
			 /***************************3.新增完成,準備轉交(Send the Success view)***********/
//			String url = "/front-end/listAllTrk.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPd.jsp
//			successView.forward(req, res);	
	  }
	 if("getSelfTrk".equals(action)) {
		 HttpSession session = req.getSession();
		 //判斷是否有登入會員
		 if(session.getAttribute("memberVO") ==null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/frontLogin.jsp");
				okView.forward(req, res);
				return;
		 }
		 MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		 PtService ptSvc = new PtService();
		 List<PtVO> list= ptSvc.getOneMEM(memberVO.getMem_id());
		 req.setAttribute("list", list);
		 RequestDispatcher okView = req.getRequestDispatcher("");
		 okView.forward(req, res);
	 }
	}	
}