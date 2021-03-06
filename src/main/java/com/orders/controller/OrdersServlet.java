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
			res.getWriter().write("?????????????????????");
		}
		
		
		if("checkOrders".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			System.out.println("servlet???"+mem_id);
			OrdersService os = new OrdersService();
			System.out.println("??????");
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
			System.out.println("servlet???"+mem_id);
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
				System.out.println("??????");
			}else {
				submitodid = req.getParameterValues("submitodid");
				System.out.println(submitodid.length);
			}
			if( req.getParameterValues("refundodid") == null ) {
				refundodid = new String[0];
				System.out.println("????????????");
			}else {
				refundodid = req.getParameterValues("refundodid");
				System.out.println(refundodid.length);
			}
			OrdersService os = new OrdersService();
			os.submitAll(submitodid, refundodid);
			
			res.getWriter().print("?????????/???????????????");
			return;

		}
		
		//??????????????????
		
		
		
		if("refund".equals(action)) {
			Integer od_id = new Integer(req.getParameter("od_id"));
			OrdersService os = new OrdersService();
			os.refund(od_id);
			res.getWriter().print("?????????????????????");
			return;
		}
		
		
		if("getoneOD".equals(action)) {
			Integer od_id =  new Integer(req.getParameter("odid"));
			OrdersService os = new OrdersService();
			OrderBean ob = os.getone(od_id);
			ObjectMapper om =new ObjectMapper();
			String data = om.writeValueAsString(ob);
			res.getWriter().print(data);
			System.out.println("????");
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
			res.getWriter().print("?????????????????????");
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
//			??????????????????????????????????????????
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer cl_memid = memberVO.getMem_id();
			
			CartListDAO cldao = new CartListDAO();
			
//			??????????????????????????????????????????
			List<ProductVO> list = cldao.beOrder(cl_memid);
			
			
//			System.out.println(list.size());
//			?????????????????????set?????????????????????????????????????????????????????????
			Set<Integer> smemid = new HashSet<Integer>();
			for(ProductVO p : list) {
				smemid.add(p.getP_memid());
			}
			
			
			
//			KEY ???????????? ???Value?????????ProductVO???List
			Map<Integer, List<ProductVO>> whoAndPD = new HashMap<Integer, List<ProductVO>>();
			
//			???????????? ??????????????? ???????????? ??? ????????????????????????????????????????????????????????????List??????
//			??????????????????KEY??????????????? , VALUE?????????List???Map
//			?????????????????????1??? ??? 10?????????
			
			List<OrdersVO> od = new ArrayList<OrdersVO>();
//			?????????
			Integer allTotal = 0 ;
			for(Integer i : smemid) { 
				Integer total = 0 ;
				List<ProductVO> pd = new ArrayList<ProductVO>();
				OrdersVO ordersVO = new OrdersVO();
//				?????????
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
			
			
			
//			???????????????????????????????????????????????????
//			Set<Integer> keys = whoAndPD.keySet();
//			for(Integer p : keys) {
//				System.out.println(p +"????????????????????????????"+whoAndPD.get(p).size());
//				for(int i = 0 ; i < whoAndPD.get(p).size() ; i++) {
//				System.out.println("???"+i+1+"??????????????????"+whoAndPD.get(p).get(i).getP_name());
//				}
//			}
			System.out.println("============????????????===============");

			req.setAttribute("id", smemid);
			req.setAttribute("list", list);
			req.setAttribute("od", od);
			req.setAttribute("total", allTotal);
			RequestDispatcher OKView = req.
					getRequestDispatcher("/front-end/Orders/beOrder.jsp");
			OKView.forward(req, res);
			return;
		}
		
		
		
//		??????????????????????????????
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
		
//		?????????????????????????????????
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
			
//			????????????????????????????????????
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer memid = memberVO.getMem_id(); // ??????
//			????????????????????????????????? ?????????????????????????????????
			String[] AllOrders = req.getParameterValues("foradd"); // ??????
			
			
//			????????????????????????????????????????????????????????????
			System.out.println(AllOrders.length);
//			???????????????????????????????????????????????????????????????????????????????????????????????????????????????table????????????
//			????????????????????????????????????LIST
			CartListDAO cldao = new CartListDAO();
//			Map<List<String>,List<ProductVO>> ODandPD = new HashMap<List<String>, List<ProductVO>>();
			
			Integer od_payment =  new Integer(req.getParameter("paymhtod")); // ??????
			
			
//			-------
//		        |   ????????????    |	 \  |  / 
//			-------	  \	| /  
//			  L	(??_>??) \|/    key????????????????????? ??? Value????????????????????? ProductVO ???  OrderVO ??? List
			Map<String , List<List<Object>>> KeyODandPD = new HashMap<String , List<List<Object>>>();
//			key????????????     
			
			
			List<ProductVO> list = cldao.beOrder(memid); 

			
			for(String smemid : AllOrders) {
//				??????????????????ProductVO
				List<Object> pd = new ArrayList<Object>();
//				??????????????????OrderVO
				List<Object> order = new ArrayList<Object>();
				
//				(??_>??)-b  ??????list???????????? ProductVO ???  OrderVO , ??????????????????????????????????????????Object???????????????
				List<List<Object>> ODandPD = new ArrayList<List<Object>>();
				
				Integer od_price = 0 ;
				for(ProductVO p : list) {
					if(new Integer(smemid).equals(p.getP_memid())) {
						od_price += p.getP_stock()* p.getP_price();
//						0v0
						pd.add(p);
					}
				}
				System.out.println("??????"+smemid+"??????????????????"+od_price);
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
				
				// ODandPD ?????? ????????? List ???  List
				ODandPD.add(order);
				ODandPD.add(pd);
				
				
				KeyODandPD.put(smemid, ODandPD);
			}
			System.out.println("????????????==");		
			
			
			
			
			OrdersService os = new OrdersService();
			boolean check = os.add(KeyODandPD , memid);
			
			System.out.println("??????");
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
			res.getWriter().print("???????????????????????????");
			return;
		}
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
