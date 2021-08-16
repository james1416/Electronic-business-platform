package com.po.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.orders.model.OrdersService;
import com.pd.model.PdService;
import com.pd.model.PdVO;
import com.po.model.PoAndPdBean;
import com.po.model.PoService;
import com.po.model.PoVO;

@MultipartConfig
@WebServlet("/PoServlet")

public class PoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public PoServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");	

		
		if("getSelfOrdersAll".equals(action)) {
			MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
			Integer po_bmemid = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<PoAndPdBean> list = new PoService().getSelfBuyOrdersAll(po_bmemid, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
//		拿取相同狀態的全部訂單
		if("getSelfOrders".equals(action)) {
			MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
			Integer po_bmemid = memberVO.getMem_id();
			Integer po_sta = new Integer(req.getParameter("posta"));
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<PoAndPdBean> list = new PoService().getSelfBuyOrdersSTA(po_bmemid, po_sta, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		if("getone".equals(action)) {
			Integer po_id = new Integer(req.getParameter("poid"));
			PoService ps = new PoService();
			String data = new ObjectMapper().writeValueAsString(ps.getone(po_id));
			res.getWriter().print(data);
			return;
		}
		
		if("submitAll".equals(action)) {
			
			String submitodid[];
			String refundodid[];
			if(req.getParameterValues("submitodid")== null) {
				submitodid = new String[0];
			}else {
				submitodid = req.getParameterValues("submitodid");
			}
			if( req.getParameterValues("refundodid") == null ) {
				refundodid = new String[0];
			}else {
				refundodid = req.getParameterValues("refundodid");
			}
			PoService ps = new PoService();
			ps.submitAll(submitodid, refundodid);
			res.getWriter().print("訂單撥/退款成功！");
			return;
		}
		
		
		if("refund".equals(action)) {
			Integer po_id = new Integer(req.getParameter("poid"));
			PoService ps = new PoService();
			ps.refund(po_id);
			return;
		}
		
		
		if("POdone".equals(action)) {
			Integer po_id = new Integer(req.getParameter("poid"));
			PoService ps = new PoService();
			ps.ODdone(po_id);
			return;
		}
		
		
		
		
		if("adminGetOD".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			Integer po_sta = new Integer(req.getParameter("posta"));
			PoService ps = new PoService();
			List<PoVO> list = ps.adminGetOD(po_sta , start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		
		
		
		
		
		
		
		//  跳轉購買頁面      由listOnePd.jsp發出請求
		if("go_order".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			if( memberVO == null) {
		 		RequestDispatcher successView =req.getRequestDispatcher("/front-end/login.jsp");
		 		successView.forward(req, res);
		 		return;
		 	}
			PdVO pdVO = new PdVO();
			pdVO.setPd_name(req.getParameter("PD_NAME"));			
			pdVO.setPd_id(new Integer (req.getParameter("PD_ID")));
			pdVO.setPd_price(new Integer(req.getParameter("PD_PRICE")));
			pdVO.setPd_desc(req.getParameter("PD_DESC"));
			pdVO.setPd_smemid(new Integer (req.getParameter("PD_SMEMID")));
			req.setAttribute("PdVO", pdVO);
			
			//抓取數量
			Integer number  = new Integer(req.getParameter("NUMBER"));
			req.setAttribute("NUMBER",number);
			
			//跳轉至訂單頁面
			String url = "/front-end/PreProduct/CheckPreOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		
		
		//新增一筆訂單  由 preorder.jsp 發出請求
		if("addOrder".equals(action)) {
			System.out.println("進來新增訂單");
			HttpSession session =req.getSession();
			MemberVO memberVO =(MemberVO) session.getAttribute("memberVO");
			
			Integer po_bmemid = memberVO.getMem_id();
			
			PoVO poVO = new PoVO();
			poVO.setPo_bmemid(po_bmemid);
			poVO.setPo_pdid(new Integer(req.getParameter("PD_ID")));
			poVO.setPo_smemid(new Integer(req.getParameter("PD_SMEMID")));
			poVO.setPo_qty(new Integer(req.getParameter("PO_QTY")));
			poVO.setPo_price(new Integer(req.getParameter("PO_PRICE")));
			poVO.setPo_tel(req.getParameter("PO_TEL"));
			poVO.setPo_bname(req.getParameter("PO_BNAME"));
			//串接地址
			String city = req.getParameter("city");
			String dist = req.getParameter("dist");
			String addr = req.getParameter("addr");
			StringBuffer sb = new StringBuffer();
			sb.append(city);
			sb.append(dist);
			sb.append(addr);
			String po_home = sb.toString();
			poVO.setPo_home(po_home);
			poVO.setPo_pay(new Integer(req.getParameter("PO_PAY")));
			PoService poSvc = new PoService();
			poSvc.add(poVO);
			res.getWriter().print("OK");
			return;
		}
		
		//取得自己購買的訂單
		if("getBuyOrder".equals(action)) {
			HttpSession session = req.getSession();
			
			if((MemberVO) session.getAttribute("memberVO") == null) {
		 		RequestDispatcher successView =req.getRequestDispatcher("/front-end/PreProduct/frontLogin.jsp");
		 		successView.forward(req, res);
			}	
			
			//取得會員編號 
		 	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");		 	
		 	Integer po_bmemid = (Integer) memberVO.getMem_id();
		 	
		 	//查詢會員購買的訂單
		 	PoService poSvc = new PoService();
		 	List<PoVO> list = poSvc.allBorder(po_bmemid);
		 	
		 	
		 	
		 	//跳轉到購買的訂單頁面	 	
		 	req.setAttribute("list", list);
		 	String url = "front-end/listSelfBuyOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);  
			successView.forward(req, res);
			return ;
		 	
		}
		//取得自己販售的訂單
		if("getSellOrder".equals(action)) {
			HttpSession session = req.getSession();
			
			if((MemberVO) session.getAttribute("memberVO") == null) {
		 		RequestDispatcher successView =req.getRequestDispatcher("/front-end/frontLogin.jsp");
		 		successView.forward(req, res);
			}	
			
			//取得會員編號 
		 	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");		 	
		 	Integer po_smemid = (Integer) memberVO.getMem_id();
		 	
		 	//查詢會員販賣的訂單
		 	PoService poSvc = new PoService();
		 	List<PoVO> list = poSvc.allBorder(po_smemid);
		 	
		 	//跳轉到購買的訂單頁面	 	
		 	req.setAttribute("list", list);
		 	String url = "front-end/PreProduct/selectCheckOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);  
			successView.forward(req, res);
			return ;
		 	
		}
		
		
		//取得單一商品的 銷售數量
		if ("getOnePdSum".equals(action)) { 
			
		 	HttpSession session = req.getSession();
		 	MemberVO memberVO =(MemberVO) session.getAttribute("memberVO");

			/***************************1.接收請求參數****************************************/

			Integer po_pdid = new Integer (req.getParameter("pdid"));
						
			PoService poSvc = new PoService();
			Integer sum = poSvc.getSum(po_pdid);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)************/

			res.getWriter().print(sum);
			return;


		}
		
		//更改訂單狀態
		if("updateOrderSta".equals(action)) {
			System.out.println("進來更改訂單狀態");
			Integer po_id = new Integer(req.getParameter("PO_ID"));
			Integer po_sta = new Integer(req.getParameter("PO_STA"));
			System.out.println("訂單編號:"+po_id);
			System.out.println("訂單狀態" +po_sta);
			PoService poSvc = new PoService();
			poSvc.updateOrder(po_id, po_sta);
			res.getWriter().print("s");
			return;
		}
		
		//買家評論訂單
				if("submitComment".equals(action)) {
					String po_comment = req.getParameter("msg");
					Integer po_point = new Integer (req.getParameter("rating"));
					Integer po_id = new Integer(req.getParameter("po_id"));
					Integer po_sta = new Integer (req.getParameter("po_sta"));
					Integer po_iscom = new Integer (req.getParameter("po_iscom"));
						
					
					PoService poSvc = new PoService();
					poSvc.SetComment(po_comment, po_point, po_sta, po_id , po_iscom);
					res.getWriter().print("成功更新訂單評論!");
				}
				
				//買家更新訂單狀態
				if("updateBOrderSta".equals(action)) {
					System.out.println("買家進來更改訂單狀態");
					System.out.println("測試");
					Integer po_id = new Integer(req.getParameter("PO_ID"));
					Integer po_sta = new Integer(req.getParameter("PO_STA"));
					Integer po_goldflow = new Integer(req.getParameter("PO_GOLDFLOW"));
				
					PoService poSvc = new PoService();
					poSvc.updateBOrder(po_id, po_sta , po_goldflow);
					res.getWriter().print("s");
					return;
				}
		
	}
		



	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
