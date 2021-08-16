package com.bpdetail.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bidproduct.model.BidProductService;
import com.bidproduct.model.BidProductVO;
import com.bpdetail.model.BpDetailService;
import com.bpdetail.model.BpDetailVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberService;
import com.member.model.MemberVO;



@WebServlet("/BpDetail")
@MultipartConfig
public class BpDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			//跳轉用
			Integer bp_id = new Integer(req.getParameter("bpd_bpid"));
			try {
			MemberVO memberVO ;
			HttpSession session = req.getSession();
			memberVO =(MemberVO)session.getAttribute("memberVO");
			
			//驗證是否登入
			if(memberVO == null) {
				res.getWriter().print("login");
				return;
			}			
			//抓到買家會員ID
			Integer bpd_bmemid = memberVO.getMem_id();
			System.out.println(bpd_bmemid);
			//抓到錢包餘額
			Integer mem_ele = new MemberService().checkEle(bpd_bmemid);
			System.out.println("會員錢包:"+mem_ele);
			//抓到商品編號
			Integer bpd_bpid = new Integer(req.getParameter("bpd_bpid"));
			System.out.println(bpd_bpid);
			//抓到投標金額
			//投標金額為輸入不能留空
			Integer bpd_bpprice= null;
			try {
				bpd_bpprice = new Integer(req.getParameter("bpd_bpprice"));
			} catch (NumberFormatException e) {
				errorMsgs.add("下標金額請勿留空或輸入非數值");
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			
			//有人進行投標動作 new 一個系統當前時間
			Timestamp bpd_bpdate ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			//simpledateformat format資料會將資料格式變為String 需要 使用valueof再轉為時間戳
			//timestamp 會多一個微秒
			bpd_bpdate = Timestamp.valueOf(sdf.format(date));System.out.println(bpd_bpprice);
			System.out.println(bpd_bpdate);
			//從資料庫撈出資料做比對
			//這邊我有兩個想法就是在前端做好input標籤 目前最高金額值但可能資料會是舊的所以選擇效能差的方式較安全
			BidProductService bidSvc = new BidProductService();
			BidProductVO bidVO = bidSvc.getOneBidProduct(bpd_bpid);
			
			//做6判斷 1.判斷商品是否處於競標中  2.金額是否有大於起標金額 3.出價金額是否有大於上一位出價者 4.出價增額是否有達到條件 5.錢包餘額是否有大於等於自己喊的價格 6.會員不能出價自己商品
			 
			//3.有點繁瑣這邊應該能用JS或前多相關方法來做資料驗證但還是先做
			//其中一項如果為false 都進行錯誤訊息新增並跳轉到原本頁面
			//1.判斷狀態是否開始或結束
			Integer bp_sta = bidVO.getBp_sta();
			System.out.println(bp_sta);
			if(bp_sta == 0||bp_sta ==2) {
				errorMsgs.add("目前此競標商品已過競標時間或還未開始競標");
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			
			//會員不能下標屬於自己的商品(先註解)測試麻煩
//			Integer bp_smemid = bidVO.getBp_smemid();
//			if(bpd_bmemid == bp_smemid) {
//				errorMsgs.add("不允許自己下標自己的商品");
//			}
//			if (!errorMsgs.isEmpty()) {
//				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
//				res.getWriter().print(data);
//				return;
//			}
			//出價要高於起拍價格
			Integer bp_sprice = bidVO.getBp_sprice();
			if(bpd_bpprice < bp_sprice) {
				errorMsgs.add("出價請高於起拍價格");
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			//出價要大於上一位買家
			Integer bp_tprice = bidVO.getBp_tprice();
			if(bpd_bpprice <= bp_tprice) {
				errorMsgs.add("出價金額請大於上一位買家");
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			//加價要等漁獲大於出價增額
			Integer bp_inc = bidVO.getBp_inc();
			if(bp_tprice == 0) {
				if((bpd_bpprice - bp_sprice) < bp_inc) {
					errorMsgs.add("出價請等於或大於出價增額1");
				}
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			//加價要等漁獲大於出價增額
			if(bp_tprice != 0) {
				if((bpd_bpprice-bp_tprice) < bp_inc) {
				errorMsgs.add("出價請等於或大於出價增額2");
				}	
			}
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			//4.
			if(mem_ele < bpd_bpprice) {
				errorMsgs.add("錢包餘額不足請會員充值後在進行競標");
			}

			//跳轉到錢包儲值頁面
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("");
//				failureView.forward(req, res);
//				return;
//			}
			/*******總錯誤處理檢查*******/
			if (!errorMsgs.isEmpty()) {
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}
			//確認後 才將值改改變
			bp_tprice = bpd_bpprice;
			
			System.out.println("準備加入VO");
			System.out.println(bp_tprice);
			BpDetailVO bpdVO = new BpDetailVO();
			bpdVO.setBpd_bpid(bpd_bpid);
			bpdVO.setBpd_bmemid(bpd_bmemid);
			bpdVO.setBpd_bpprice(bpd_bpprice);
			bpdVO.setBpd_bpdate(bpd_bpdate);
			//確認無誤開始新增競標紀錄
			//資料
			bidSvc.updatebp_tprice(bp_tprice, bpd_bpid,bpdVO);
			System.out.println("資料新增修改成功");
			//成功跳轉
			BidProductVO bidVO2 = bidSvc.getOneBidProduct(bp_id);
			res.getWriter().print("OK");
			return;
			
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("發生預期外錯誤請重新操作");
				String data = new ObjectMapper().writeValueAsString(errorMsgs.get(0));
				res.getWriter().print(data);
				return;
			}	
		}
		
		/**********************得到單筆商品資料的全部出價明細********************************/
		
		if("getbp_detail".equals(action)) {
			Integer bpd_bpid =new Integer(req.getParameter("bp_id"));
			
			BpDetailService bpdSvc = new BpDetailService();
			List<BpDetailVO> list = bpdSvc.getOnebpdfromBpid(bpd_bpid);
			ObjectMapper mapper= new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print(data);
			
		}
		
		/**********************買家找到自己的出價紀錄********************************/
		if("get_my_detail".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer bpd_bmemid = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));

			BpDetailService bpdSvc = new BpDetailService();
			List<BpDetailVO> list = bpdSvc.getOnebpdfromBpbmemid(bpd_bmemid, start, rows);
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
