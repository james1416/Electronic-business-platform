package com.pd.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.pd.model.PdService;
import com.pd.model.PdVO;
import com.po.model.PoService;






@WebServlet("/PdSelfServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 10 * 10 * 1024 * 1024)
public class PdSelfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
				
		
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 來自FrontMain.jsp的請求 拿到會員自己上架的商品
		if ("getSelf".equals(action)) { 
				System.out.println("進來抓資料");
			 	HttpSession session = req.getSession();
			 	if((MemberVO) session.getAttribute("memberVO") == null) {
			 		RequestDispatcher successView =req.getRequestDispatcher("/front-end/login.jsp");
			 		successView.forward(req, res);
			 		return;
			 	}
			 	MemberVO memberVO =(MemberVO) session.getAttribute("memberVO");

				/***************************1.接收請求參數****************************************/

				Integer pd_smemid = memberVO.getMem_id();
				Integer start = new Integer(req.getParameter("start"));
				Integer rows = new Integer(req.getParameter("rows"));
				
				PdService pdSvc = new PdService();
				List<PdVO> list = pdSvc.findMyPDbyPage(pd_smemid, start, rows);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
			
				ObjectMapper om = new ObjectMapper();
				String data = om.writeValueAsString(list);
				res.getWriter().print(data);
				return;


		}
		
		if("findPD".equals(action)) {       //來自listSelfAllPd.jsp 的修改請求
			Integer pd_id = new Integer(req.getParameter("pd_id"));
			PdService pdSvc = new PdService();
			PdVO pdVO = pdSvc.getOnePd(pd_id);
			req.setAttribute("pdVO", pdVO);
			String url = "/front-end/PreProduct/updatePrePd.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		
		
		if("selectOnePD".equals(action)) {
			Integer pd_id= new Integer(req.getParameter("pd_id"));
			PdService pdSvc = new PdService();
			PdVO pdVO = pdSvc.getOnePd(pd_id);
			req.setAttribute("pdVO", pdVO);
			RequestDispatcher successView = req.
					getRequestDispatcher("/front-end/PreProduct/listOnePrePd.jsp");
			successView.forward(req, res);
			return;
		}
	
		if("update".equals(action)) {
			Integer po_pdid = new Integer(req.getParameter("PD_ID"));
			PoService poSvc = new PoService();
			boolean checkOrder = poSvc.checkOrder(po_pdid);

			// 查看是否有人下訂此商品 如果有的話不能修改商品資訊
			if (checkOrder) {
				System.out.println("已經有人購買了 無法修改商品資訊");
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/PreProduct/listAllPrePd.jsp");
				successView.forward(req, res);
				return;
			}

			else {
				List<Part> list = (List<Part>) req.getParts();
				String needDel[];
				if (req.getParameterValues("del") == null) {
					needDel = new String[0];
				} else {
					needDel = req.getParameterValues("del");
				}

				Integer pd_smemid = new Integer(req.getParameter("PD_SMEMID"));
				Integer pd_id = new Integer(req.getParameter("PD_ID"));
				Integer pd_pcid = new Integer(req.getParameter("PD_PCID"));
				String pd_name = req.getParameter("PD_NAME");
				Integer pd_price = new Integer(req.getParameter("PD_PRICE"));
				Integer pd_no = new Integer(req.getParameter("PD_NO"));
				String pd_desc = req.getParameter("PD_DESC");
				Timestamp pd_sdate = java.sql.Timestamp.valueOf(req.getParameter("PD_SDATE"));
				Timestamp pd_edate = java.sql.Timestamp.valueOf(req.getParameter("PD_EDATE"));
				Timestamp pd_spdate = java.sql.Timestamp.valueOf(req.getParameter("PD_SPDATE"));
				Integer pd_sta = new Integer(req.getParameter("PD_STA"));

				PdService pdSvc = new PdService();

				pdSvc.updatePd(pd_smemid, pd_pcid, pd_name, pd_price, pd_no, pd_desc, pd_sdate, pd_edate, pd_spdate,
						pd_sta, pd_id, list, needDel);

				RequestDispatcher okView = req.getRequestDispatcher("/PdSelfServlet?action=getSelf");
				okView.forward(req, res);
				return;
			}
			
		}
		
		
		if("add".contentEquals(action)) {
			
			// req.getParts 方法 會拿到一個陣列 內容物型別為part
			List<Part> list = (List<Part>) req.getParts();
			
			HttpSession session = req.getSession();
			
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			
			Integer pd_smemid = memberVO.getMem_id();
			
			
			Integer pd_pcid = new Integer (req.getParameter("PD_PCID"));
			String pd_name =  req.getParameter("PD_NAME");
			Integer pd_price = new Integer(req.getParameter("PD_PRICE"));
			Integer pd_no = new Integer(req.getParameter("PD_NO"));
			String pd_desc = req.getParameter("PD_DESC");
			Timestamp pd_sdate = java.sql.Timestamp.valueOf(req.getParameter("PD_SDATE"));
			Timestamp pd_edate = java.sql.Timestamp.valueOf(req.getParameter("PD_EDATE"));
			Timestamp pd_spdate = java.sql.Timestamp.valueOf(req.getParameter("PD_SPDATE"));
			
			//讓商品預設狀態為未上架 新增完商品後再去做判斷
			Integer pd_sta = 0;
			
			
			// Part.getName 他可以拿到我們Part的name
			PdService pdSvc = new PdService();
			pdSvc.addPd(pd_smemid, pd_pcid, pd_name, pd_price, pd_no, pd_desc, pd_sdate, pd_edate, pd_spdate, pd_sta, list);
			RequestDispatcher okView = req.
					getRequestDispatcher("/ProductServlet?action=getSelf");
			okView.forward(req, res);
			return;
		}
		
		if ("offPd".equals(action)) {
			System.out.println("進來下架");

			Integer pd_id = new Integer(req.getParameter("PD_ID"));
			PoService poSvc = new PoService();

			boolean checkOrder = poSvc.checkOrder(pd_id);
			
			if (checkOrder) { // 已經有人購買無法下架
				boolean data = false;
				res.getWriter().print(data);
				return;
			} else {

				Integer pd_sta = new Integer(req.getParameter("PD_STA"));

				PdService pdSvc = new PdService();
				pdSvc.offPd(pd_id, pd_sta);
				System.out.println("成功下架商品");

				boolean data = true ;
				res.getWriter().print(data);
				return;

			}
		}
		
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
			doGet(req, res);
	}
}
