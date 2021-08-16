package com.bidproduct.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.bidproduct.model.BidProductService;
import com.bidproduct.model.BidProductVO;
import com.bpimage.model.BpImageService;
import com.bpimage.model.BpImageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.po.model.PoService;

@WebServlet("/bid.do")
@MultipartConfig
public class BidProductServlet extends HttpServlet {

	private ObjectMapper objectMapper;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html;charset=UTF-8");
		System.out.println(action);
		
				//抓到及時最高價格AJAX
		        if("tprice".equals(action)) {
		            Integer bp_id = new Integer(req.getParameter("bp_id"));
		            BidProductService bidSvc = new BidProductService();
		            BidProductVO bidVO = new BidProductVO();
		            bidVO = bidSvc.getOneBidProduct(bp_id);
		            String data = new ObjectMapper().writeValueAsString(bidVO.getBp_tprice());
		            res.getWriter().print(data);
		            return;
		
		        }
		
			if("getSelfBuyOrdersAll".equals(action)) {
				MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
				Integer bp_bmemid = memberVO.getMem_id();
				Integer start = new Integer(req.getParameter("start"));
				Integer rows = new Integer(req.getParameter("rows"));
				List<BidProductVO> list = new BidProductService().getSelfBuyOrdersAll(bp_bmemid, start, rows);
				String data = new ObjectMapper().writeValueAsString(list);
				res.getWriter().print(data);
				return;
				
			}
			
			
			if("getSelfBuyOrdersSTA".equals(action)) {
				MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
				Integer bp_bmemid = memberVO.getMem_id();
				Integer bp_shsta = new Integer(req.getParameter("bpshsta"));
				Integer start = new Integer(req.getParameter("start"));
				Integer rows = new Integer(req.getParameter("rows"));
				List<BidProductVO> list = new BidProductService().getSelfBuyOrdersSTA(bp_bmemid, bp_shsta, start, rows);
				String data = new ObjectMapper().writeValueAsString(list);
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
				new BidProductService().submitAll(submitodid, refundodid);
				res.getWriter().print("訂單撥/退款成功！");
				return;
		}
		
		
		if("bpdone".equals(action)) {
			Integer bp_id = new Integer(req.getParameter("bpid"));
			new BidProductService().BPdone(bp_id);
			return;
		}
		
		
		if("refund".equals(action)) {
			Integer bp_id = new Integer(req.getParameter("bpid"));
			new BidProductService().refund(bp_id);
			return;
		}
		
		
		if("admingetbp".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<BidProductVO> list= new BidProductService().adminGetBP(start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
			
		}
		
		if("admingetone".equals(action)) {
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			Integer bp_shsta = new Integer(req.getParameter("bpshsta"));
			List<BidProductVO> list= new BidProductService().adminGetOne(bp_shsta, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		
		
		if("test".equals(action)) {
			BidProductService bidSvc = new BidProductService();
			 List<BidProductVO> list = bidSvc.getAll();
			ObjectMapper mapper= new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			
		}
		

		// 單一主鍵查詢
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			// --------接收請求參數錯誤處理------------
			try {
				String str = req.getParameter("bp_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入競標商品編號");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/BidProduct/index.jsp");
					failureView.forward(req, res);
					return;
				}

				Integer bp_id = null;
				try {
					bp_id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("競標商品編號格式不正確");
				}
				// 再次確認陣列有無錯誤訊息
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/BidProduct/index.jsp");
					failureView.forward(req, res);
					return;
				}

				BidProductService bidSvc = new BidProductService();
				BidProductVO bidVO = bidSvc.getOneBidProduct(bp_id);
				if (bidVO == null) {
					errorMsgs.add("查無相關資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/BidProduct/index.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("bidVO", bidVO);
				String url = "/front-end/BidProduct/listOneBid.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/BidProduct/index.jsp");
				failureView.forward(req, res);
			}
		}

		// 單一主鍵獲得更改資料

		if ("getOne_For_Update".equals(action)) {
				Integer bp_id = new Integer(req.getParameter("bp_id"));
				BidProductService bidSvc = new BidProductService();
				BidProductVO bidVO = bidSvc.getOneBidProduct(bp_id);

				req.setAttribute("bidVO", bidVO);
				String url = "/front-end/BidProduct/updata_Bid.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
		}
		
		// 更新商品資訊
		if ("updata".equals(action)) {
			List<Part> list =(List<Part>)req.getParts();
			System.out.println("進入到修改");
			String needDel[];
			if(req.getParameterValues("del") == null) {
				needDel = new String[0];
			}else {
				needDel =req.getParameterValues("del");
			}
			System.out.println(needDel);
			for(int i = 0;i<needDel.length;i++) {
				System.out.println(needDel[i]);
			}
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bp_smemid = memberVO.getMem_id();
			System.out.println(bp_smemid);
			//商品編號
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			//類別ID
			Integer bp_bpcid = new Integer(req.getParameter("bp_bpcid"));
			//商品名稱
			String bp_name = req.getParameter("bp_name");
			//競標開始時間
			Timestamp bp_stime = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d1 = null;
			try {
				d1 = sdf.parse(req.getParameter("bp_stime"));
			} catch (java.text.ParseException e) {

				e.printStackTrace();
			}
			bp_stime = new java.sql.Timestamp(d1.getTime()); 
			//競標結束時間
			Timestamp bp_etime = null;
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d2 = null;
			try {
				d2 = sdf.parse(req.getParameter("bp_etime"));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			bp_etime = new java.sql.Timestamp(d2.getTime());
			//起拍價格
			Integer bp_sprice = new Integer(req.getParameter("bp_sprice"));	
			//出價增額
			Integer bp_inc = new Integer(req.getParameter("bp_inc"));
			//值購價格
			Integer bp_imdt = new Integer(req.getParameter("bp_imdt"));
			//運費
			Integer bp_ship = new Integer(req.getParameter("bp_ship"));
			//商品說明
			String bp_desc = req.getParameter("bp_desc");
			System.out.println("到service前");
			BidProductService bidSvc = new BidProductService();
			bidSvc.updataBid(bp_bpcid, bp_name, bp_stime, bp_etime, bp_sprice, bp_inc, bp_imdt, bp_ship, bp_desc, bp_id, needDel, list);
			RequestDispatcher successView = req.getRequestDispatcher("");
			successView.forward(req, res);
			
		}

		// 新增商品
		if ("add".equals(action)) {
			//驗證登入
			HttpSession session = req.getSession();
			if((MemberVO)session.getAttribute("memberVO")==null) {
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/login.jsp");
				successView.forward(req, res);
				return;
			}
			List<Part> list = (List<Part>) req.getParts();
			System.out.println(list);
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			//會員ID
			Integer bp_smemid = memberVO.getMem_id();
			//類別ID
			Integer bp_bpcid = new Integer(req.getParameter("bpc_id"));
			//商品名稱
			String bp_name = req.getParameter("bp_name");
			//競標開始時間
			Timestamp bp_stime = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d1 = null;
			try {
				d1 = sdf.parse(req.getParameter("bp_stime"));
			} catch (java.text.ParseException e) {

				e.printStackTrace();
			}
			bp_stime = new java.sql.Timestamp(d1.getTime()); 
			//競標結束時間
			Timestamp bp_etime = null;
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d2 = null;
			try {
				d2 = sdf.parse(req.getParameter("bp_etime"));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			bp_etime = new java.sql.Timestamp(d2.getTime());
			//起拍價格
			Integer bp_sprice = new Integer(req.getParameter("bp_sprice"));	
			//出價增額
			Integer bp_inc = new Integer(req.getParameter("bp_inc"));

			//商品說明
			String bp_desc = req.getParameter("bp_desc");
			
			BidProductService bidSvc = new BidProductService();
			bidSvc.addBid(bp_smemid, bp_bpcid, bp_name, bp_stime, bp_etime, bp_sprice, bp_inc, bp_desc,list);
			
			System.out.println("圖片及商品資料新增OK");
			
				String url = "/front-end/BidProduct/get_my_bidproduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
		}
		
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer bp_id = new Integer(req.getParameter("bp_id"));
				System.out.println(bp_id);
				// 較複雜 這邊可能要先得到資料庫有無下標人才允許目前還未做到下標紀錄table 7/4
				// 暫且跳過
				BidProductService bidSvc = new BidProductService();
				bidSvc.deleteBidProduct(bp_id);
				
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/BidProduct/get_my_bidproduct.jsp");
				failureView.forward(req, res);
			}
			return;
		}
		
		//會員搜尋自己上架商品(AJAX版)
		if("getmybid".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bp_smemid = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));

			BidProductService bidSvc = new BidProductService();
			List<BidProductVO> list = bidSvc.findmybidbypage(bp_smemid, start, rows);
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			return;
		}
		
		//會員搜尋自己得標的商品
		if("get_win_bid".equals(action)) {
			MemberVO memberVO ;
			HttpSession session = req.getSession();
			memberVO =(MemberVO)session.getAttribute("memberVO");
			//驗證是否登入
			if(memberVO == null) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/login.jsp");
				failureView.forward(req, res);
			}
			Integer bp_bmemid = memberVO.getMem_id();
			BidProductService bidSvc = new BidProductService();
			List<BidProductVO> list = bidSvc.getwinbid(bp_bmemid);
			req.setAttribute("list", list);
			String url = "/front-end/BidProduct/get_win_bidnew.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		//跳轉到基本資料填寫
		if("fill".equals(action)) {
			MemberVO memberVO ;
			HttpSession session = req.getSession();
			memberVO =(MemberVO)session.getAttribute("memberVO");
			//驗證是否登入
			if(memberVO == null) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/login.jsp");
				failureView.forward(req, res);
			}
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			BidProductService bidSvc = new BidProductService();
			BidProductVO bidVO = bidSvc.getOneBidProduct(bp_id);
			req.setAttribute("bidVO", bidVO);
			String url = "/front-end/BidProduct/fillnew.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		//新增商品收件資訊
		if("receive".equals(action)) {
			System.out.println("receive in");
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=UTF-8");
			MemberVO memberVO ;
			HttpSession session = req.getSession();
			memberVO =(MemberVO)session.getAttribute("memberVO");
			//驗證是否登入
			if(memberVO == null) {
				res.getWriter().print("login");
				return;
			}
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			Integer bp_bmemid = memberVO.getMem_id();
			String bp_bname = req.getParameter("bp_bname");
			String bp_add = req.getParameter("bp_add");
			String bp_tel = req.getParameter("bp_tel");
			Integer bp_shsta = new Integer(req.getParameter("bp_shsta"));
			
			BidProductService bidSvc = new BidProductService();
			bidSvc.InsertOrUpdateReceive(bp_bname, bp_add, bp_tel,bp_id,bp_shsta);
//			List<BidProductVO> list = bidSvc.getwinbid(bp_bmemid);
//			req.setAttribute("list", list);
//			String url = "/front-end/BidProduct/get_win_bidnew.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);
//			successView.forward(req, res);
		}
		
		//獲得有效競標的商品
		if("geteffbid".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bp_smemid = memberVO.getMem_id();

			BidProductService bidSvc = new BidProductService();
			List<BidProductVO> list = bidSvc.effbid(bp_smemid);
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			return;
		}
		
		//更改訂單狀態
		if("updateshsta".equals(action)) {
			System.out.println("inside");
			Integer bp_shsta = new Integer(req.getParameter("bp_shsta"));
			System.out.println(bp_shsta);
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			System.out.println(bp_id);
			BidProductService bidSvc = new BidProductService();
			bidSvc.changeshsta(bp_id, bp_shsta);
			res.getWriter().print("s");
			return;
		}
		//買家獲得完成訂單
		if("completebidb".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bp_bmemid = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));

			BidProductService bidSvc = new BidProductService();
			List<BidProductVO> list = bidSvc.completebidb(bp_bmemid, start, rows);
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			return;
		}
		//賣家獲得完成的訂單
		if("completebids".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bp_smemid = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));

			BidProductService bidSvc = new BidProductService();
			List<BidProductVO> list = bidSvc.completebids(bp_smemid, start, rows);
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			return;
		}
		
		//買家新增評論
		if("submitComment".equals(action)) {
			Integer bp_id = new Integer(req.getParameter("bp_id"));
			String bp_comment = req.getParameter("msg");
			Integer bp_point = new Integer(req.getParameter("rating"));
			Integer bp_comsta = new Integer(req.getParameter("bp_comsta"));
			BidProductService bidSvc = new BidProductService();
			bidSvc.addcom(bp_id, bp_comment, bp_point, bp_comsta);
		}
	}
}
