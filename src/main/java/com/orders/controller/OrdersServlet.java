package com.orders.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cartList.model.CartListDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.memberorders.model.MemberordersVO;
import com.orders.model.OrderBean;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.product.model.ProductVO;

@MultipartConfig
@WebServlet("/OrdersServlet")
public class OrdersServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");	
		System.out.println(action);
		
		
		if("returned".equals(action)) {
			Integer od_sta = new Integer(req.getParameter("od_sta"));
			Integer od_id = new Integer(req.getParameter("od_id"));
			new OrdersService().returned(od_id, od_sta);
			return;
		}
		
		
		
		if("ChangeOrderSta".equals(action)) {
			Integer od_sta = new Integer(req.getParameter("od_sta"));
			Integer od_id = new Integer(req.getParameter("od_id"));
			Integer od_goldflow ;
			if(req.getParameter("odgoldflow") == null) {
				od_goldflow = 1;
			}else {
				od_goldflow = new Integer(req.getParameter("odgoldflow"));
			}
			System.out.println(od_sta);
			OrdersService os = new OrdersService();
			os.changeOD(od_sta, od_id , od_goldflow);
			res.getWriter().write("成功取消訂單！");
		}
		
		
		if("checkOrders".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			System.out.println("servlet端"+mem_id);
			OrdersService os = new OrdersService();
			System.out.println("測試");
			Map<Integer, List<MemberordersVO>> map = os.checkOrders(mem_id);
			System.out.println(map);
			String data = new ObjectMapper().writeValueAsString(map);
			res.getWriter().print(data);
			return;
		}
		
		
		if("checkOrdersByOd_sta".equals(action)) {
			System.out.println("chekOrders");
			String sta = req.getParameter("od_sta");
			Integer od_sta = Integer.parseInt(sta);
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			System.out.println("servlet端"+mem_id);
			OrdersService os = new OrdersService();
			Map<Integer, List<MemberordersVO>> map = os.listOrdersByMem_idandOd_sta(mem_id, od_sta);
			String data = new ObjectMapper().writeValueAsString(map);
			res.getWriter().print(data);
			return;
		}
		
		if("getSelfOrders".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			List<OrderBean> list = new OrdersService().getSelfOrders(mem_id);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		
		if("getAllbySTA".equals(action)) {
			OrdersService os = new OrdersService();
			Integer od_sta= new Integer(req.getParameter("odsta"));
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<OrdersVO> list = os.getAllbySTA(od_sta, start, rows);
			String data = new ObjectMapper().writeValueAsString(list);
			System.out.println(data);
			res.getWriter().print(data);
			return;
		}
		
		
		
		
		if("submitAll".equals(action)) {
			String submitodid[];
			String refundodid[];
			if(req.getParameterValues("submitodid")== null) {
				submitodid = new String[0];
				System.out.println("沒有");
			}else {
				submitodid = req.getParameterValues("submitodid");
				System.out.println(submitodid.length);
			}
			if( req.getParameterValues("refundodid") == null ) {
				refundodid = new String[0];
				System.out.println("取消沒有");
			}else {
				refundodid = req.getParameterValues("refundodid");
				System.out.println(refundodid.length);
			}
			OrdersService os = new OrdersService();
			os.submitAll(submitodid, refundodid);
			
			res.getWriter().print("訂單撥/退款成功！");
			return;

		}
		
		//查看所有訂單
		
		
		
		if("refund".equals(action)) {
			Integer od_id = new Integer(req.getParameter("od_id"));
			OrdersService os = new OrdersService();
			os.refund(od_id);
			res.getWriter().print("訂單退款成功！");
			return;
		}
		
		
		if("getoneOD".equals(action)) {
			Integer od_id =  new Integer(req.getParameter("odid"));
			OrdersService os = new OrdersService();
			OrderBean ob = os.getone(od_id);
			ObjectMapper om =new ObjectMapper();
			String data = om.writeValueAsString(ob);
			res.getWriter().print(data);
			System.out.println("嗯?");
			return;
		}
		
		
		
		if("getLv7Orders".equals(action)) {
			OrdersService os = new OrdersService();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			List<OrdersVO> list = os.getLv7Orders(start , rows);
			String data = new ObjectMapper().writeValueAsString(list);
			res.getWriter().print(data);
			return;

		}
		
		if("appropriation".equals(action)) {
			String Id = req.getParameter("od_id");
			System.out.println(Id);
			Integer od_id = new Integer(Id);
			OrdersService os = new OrdersService();
			os.appropriation(od_id);
			res.getWriter().print("訂單撥款成功！");
			return;

		}
		
		
		
		if("allupdataOD".equals(action)) {
			String od_id[] = req.getParameterValues("odid");
			Integer od_sta = new Integer(req.getParameter("odsta"));
			Integer od_goldflow ;
			if(req.getParameter("odgoldflow") == null) {
				od_goldflow = 1;
			}else {
				od_goldflow = new Integer(req.getParameter("odgoldflow"));
			}
			System.out.println(od_goldflow);
			OrdersService os = new OrdersService();
			os.changeAllOD(od_sta, od_id , od_goldflow);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/checkSOD.jsp");
			OKView.forward(req, res);
			return;
		}
		
		
		if("updataOD".equals(action)) {
			
			Integer od_sta = new Integer(req.getParameter("odsta"));
			Integer od_id = new Integer(req.getParameter("odid"));
			Integer od_goldflow ;

			if(req.getParameter("odgoldflow") == null) {
				od_goldflow = 1;
			}else {
				od_goldflow = new Integer(req.getParameter("odgoldflow"));
			}
			System.out.println(od_goldflow);
			OrdersService os = new OrdersService();
			os.changeOD(od_sta, od_id , od_goldflow);
			res.getWriter().print("s");
			return;
		}
		
		
		
		
		
		if("check".equals(action)) {
//			下面三行再拿登入該會員的編號
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer cl_memid = memberVO.getMem_id();
			
			CartListDAO cldao = new CartListDAO();
			
//			叫出此會員購物車內所有的商品
			List<ProductVO> list = cldao.beOrder(cl_memid);
			
			
//			System.out.println(list.size());
//			將賣家編號塞入set可取得不重複的賣家會員編號以利之後過濾
			Set<Integer> smemid = new HashSet<Integer>();
			for(ProductVO p : list) {
				smemid.add(p.getP_memid());
			}
			
			
			
//			KEY 賣家編號 ，Value是裝著ProductVO的List
			Map<Integer, List<ProductVO>> whoAndPD = new HashMap<Integer, List<ProductVO>>();
			
//			第一層為 賣家的編號 第二層為 若 賣家編號等於商品的賣家編號的話，則會加入List裡面
//			最後將會取得KEY為賣家編號 , VALUE為商品List的Map
//			類似賣家編號為1的 有 10個商品
			
			List<OrdersVO> od = new ArrayList<OrdersVO>();
//			第一層
			Integer allTotal = 0 ;
			for(Integer i : smemid) { 
				Integer total = 0 ;
				List<ProductVO> pd = new ArrayList<ProductVO>();
				OrdersVO ordersVO = new OrdersVO();
//				第二層
				for(ProductVO p : list) {
					
					
					if(i == p.getP_memid()) {
						
						total += p.getP_stock() * p.getP_price();
						pd.add(p);
					}
				}
				whoAndPD.put(i, pd);
				allTotal += total;
				ordersVO.setOd_price(total);
				ordersVO.setOd_smemid(i);
				od.add(ordersVO);
			}
			
			
			
//			下方可以看出，哪個會員有哪個商品。
//			Set<Integer> keys = whoAndPD.keySet();
//			for(Integer p : keys) {
//				System.out.println(p +"的訂單商品有多少個?"+whoAndPD.get(p).size());
//				for(int i = 0 ; i < whoAndPD.get(p).size() ; i++) {
//				System.out.println("第"+i+1+"的商品名稱為"+whoAndPD.get(p).get(i).getP_name());
//				}
//			}
			System.out.println("============要轉跳了===============");

			req.setAttribute("id", smemid);
			req.setAttribute("list", list);
			req.setAttribute("od", od);
			req.setAttribute("total", allTotal);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/beOrder.jsp");
			OKView.forward(req, res);
			return;
		}
		
		
		
//		查詢自己買東西的訂單
		if("findBOD".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.jsp");
				okView.forward(req, res);
				return;
			}
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer od_bmemid = memberVO.getMem_id();
			OrdersService os = new OrdersService();
			List<OrdersVO> list = os.myBOrders(od_bmemid);
			req.setAttribute("list", list);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/selectMYBOrders.jsp");
			OKView.forward(req, res);
			return;
		}
		
//		查詢自己販賣的商品訂單
		if("findSOD".equals(action)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("memberVO") == null) {
				RequestDispatcher okView = req.
						getRequestDispatcher("/front-end/login.jsp");
				okView.forward(req, res);
				return;
			}
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer od_smemid = memberVO.getMem_id();
			OrdersService os = new OrdersService();
			List<OrdersVO> list = os.mySOrders(od_smemid);
			
			req.setAttribute("list", list);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/checkSOD.jsp");
			OKView.forward(req, res);
			return;
		}
		
		
		
		if("add".equals(action)) {
			
//			拿到登入該會員的會員編號
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer memid = memberVO.getMem_id(); // 這個
//			這邊用於拿到要成立訂單 訂單包含的賣家為那些人
			String[] AllOrders = req.getParameterValues("foradd"); // 這個
			
			
//			這邊就可以看出有幾個訂單，就會跑幾次迴圈
			System.out.println(AllOrders.length);
//			因為訂單主要是要讓使用者填寫資料，商品數量等等都不會改變，所以再從購物車的table內拿資料
//			再依據資料找到商品並存進LIST
			CartListDAO cldao = new CartListDAO();
//			Map<List<String>,List<ProductVO>> ODandPD = new HashMap<List<String>, List<ProductVO>>();
			
			Integer od_payment =  new Integer(req.getParameter("paymhtod")); // 這個
			
			
//			-------
//		        |   好長的香    |	 \  |  / 
//			-------	  \	| /  
//			  L	(ˊ_>ˋ) \|/    key值會放賣家編號 ， Value則是放一個裝著 ProductVO 跟  OrderVO 的 List
			Map<String , List<List<Object>>> KeyODandPD = new HashMap<String , List<List<Object>>>();
//			key賣家編號     
			
			
			List<ProductVO> list = cldao.beOrder(memid); 

			
			for(String smemid : AllOrders) {
//				內容物其實是ProductVO
				List<Object> pd = new ArrayList<Object>();
//				內容物其實是OrderVO
				List<Object> order = new ArrayList<Object>();
				
//				(ˊ_>ˋ)-b  這個list裡面會放 ProductVO 跟  OrderVO , 為了讓他們可以存在一起要使用Object來做為型別
				List<List<Object>> ODandPD = new ArrayList<List<Object>>();
				
				Integer od_price = 0 ;
				for(ProductVO p : list) {
					if(new Integer(smemid).equals(p.getP_memid())) {
						od_price += p.getP_stock()* p.getP_price();
//						0v0
						pd.add(p);
					}
				}
				System.out.println("賣家"+smemid+"本訂單金額為"+od_price);
				Integer od_smemid = new Integer(smemid);
				Integer od_bmemid = memid;
				String od_notes = req.getParameter("msg"+smemid);
				Integer od_shipping = new Integer(req.getParameter("do"+smemid));
				String od_name =  req.getParameter("name"+smemid);
				String od_tel = req.getParameter("tel"+smemid);
				
				String city = req.getParameter("city"+smemid);
				String dist = req.getParameter("dist"+smemid);
				String add = req.getParameter("add"+smemid);
				StringBuffer sb = new StringBuffer();
				sb.append(city);
				sb.append(dist);
				sb.append(add);
				String od_shipinfo = sb.toString();
				System.out.println(od_shipinfo);
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOd_bmemid(od_bmemid);
				ordersVO.setOd_smemid(od_smemid);
				ordersVO.setOd_price(od_price);
				ordersVO.setOd_shipping(od_shipping);
				ordersVO.setOd_shipinfo(od_shipinfo);
				ordersVO.setOd_payment(od_payment);
				ordersVO.setOd_notes(od_notes);
				ordersVO.setOd_tel(od_tel);
				ordersVO.setOd_name(od_name);
				System.out.println(pd.size());
				order.add(ordersVO);
				
				// ODandPD 是個 只能塞 List 的  List
				ODandPD.add(order);
				ODandPD.add(pd);
				
				
				KeyODandPD.put(smemid, ODandPD);
			}
			System.out.println("我成功了==");		
			
			
			
			
			OrdersService os = new OrdersService();
			boolean check = os.add(KeyODandPD , memid);
			
			System.out.println("結束");
			if(check) {
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Member/checkOrdersStatus.jsp");
			OKView.forward(req, res);
			}else {
				RequestDispatcher OKView = req.
						getRequestDispatcher("/front-end/CartList/selectCL.jsp");
				OKView.forward(req, res);
			}
			return;
		}
		
		
		
		if("submitComment".equals(action)) {
			String od_comment = req.getParameter("msg");
			Integer od_point = new Integer(req.getParameter("rating"));
			Integer od_id = new Integer(req.getParameter("od_id"));
			Integer od_rating = new Integer(req.getParameter("od_rating"));
			System.out.println(od_id);
			System.out.println(od_comment);
			OrdersService os = new OrdersService();
			os.updateComment(od_comment, od_point, od_rating, od_id);
			res.getWriter().print("成功更新訂單評論！");
			return;
		}
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
