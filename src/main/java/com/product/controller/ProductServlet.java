package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.Bean.PageBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 10 * 10 * 1024 * 1024)
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		// ajax 拿所有自己上架的商品分頁
		if("getSelfPDbyPage".equals(action)) {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer mem_id = memberVO.getMem_id();
			Integer start = new Integer(req.getParameter("start"));
			Integer rows = new Integer(req.getParameter("rows"));
			
			
			ProductService ps = new ProductService();
			List<ProductVO> list = ps.findMyPDbyPage(mem_id, start, rows);
			
			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(list);
			res.getWriter().print(data);
			return;
		}
		
		
		if("inputpoint".equals(action)) {
			String input = req.getParameter("pname");
			if(req.getParameter("pname") == null || req.getParameter("pname").trim().length() == 0) {
				input =" ";
			}
			Integer p_cgid = new Integer(req.getParameter("pcgid"));
			ProductService ps = new ProductService();
			Set<String> set = ps.inputPoint(input, p_cgid);
			String data = new ObjectMapper().writeValueAsString(set);
			res.getWriter().print(data);
			return;
		}
		
		
		
		
		if("search".equals(action)){
			String currentPage = req.getParameter("scurrentPage");
			String rows = req.getParameter("srows");
			
			String p_name = req.getParameter("pname");
			Integer p_cgid = new Integer(req.getParameter("pcgid"));
			
			if( req.getParameter("pname") == null ||  req.getParameter("pname").trim().length() ==  0 ) {
				p_name = "";
			}
			if(req.getParameter("scurrentPage") == null || "".equals(req.getParameter("scurrentPage"))) {
				currentPage = "1";
			}
			if(req.getParameter("srows") == null || "".equals(req.getParameter("srows"))) {
				rows = "20";
			}
			ProductService ps = new ProductService();
			PageBean<ProductVO> pb= ps.searchMyPDbyPage(p_name, p_cgid, currentPage, rows);
			req.setAttribute("spb", pb);
			req.setAttribute("pname", p_name);
			req.setAttribute("pcgid", p_cgid);
			RequestDispatcher okview = req.
					getRequestDispatcher("/front-end/Product/searchPDbyPage.jsp");
				okview.forward(req, res);
			return;

		}
		
		
		
		if("getAllByPage".equals(action)){
			String currentPage = req.getParameter("currentPage");
			String rows = req.getParameter("rows");
			
			if(req.getParameter("currentPage") == null || "".equals(req.getParameter("currentPage"))) {
				currentPage = "1";
			}
			if(req.getParameter("rows") == null || "".equals(req.getParameter("rows"))) {
				rows = "20";
			}
			
			ProductService ps = new ProductService();
			PageBean<ProductVO> pb= ps.getAllByPage(currentPage , rows);
			req.setAttribute("pb", pb);
			RequestDispatcher okview = req.
					getRequestDispatcher("/front-end/Product/selectPDbyPagejsp.jsp");
				okview.forward(req, res);
			return;

		}
		
		
		if("getSelf".equals(action)) {
//			叫他出來
			HttpSession session = req.getSession();
//			從session拿出你前面登入時 給的東西 然後轉型為MemberVO 存入 memberVO
//			這樣子的話你就可以使用memberVO.getxxx 方法，拿到該會員的資料
			if((MemberVO) session.getAttribute("memberVO") == null) {
				RequestDispatcher successView = req.
						getRequestDispatcher("/front-end/login.jsp");
				successView.forward(req, res);
				return;
			}
			MemberVO memberVO= (MemberVO) session.getAttribute("memberVO");
//			這邊我是要查這個會員自己新增的商品，所以我先取出他的編號存入p_memid
			Integer p_memid= memberVO.getMem_id();
//			測試用
			System.out.println(memberVO.getMem_id());
			System.out.println("我到這了");
//			接著我去創建我的service  // 註 如果沒有這一層，也可以直接new你的DAO
			ProductService ps = new ProductService();
//			呼叫裡面的getSelf方法拿到我創的商品 存到list內 ，因為我們前面設定的回傳為list
			List<ProductVO> list = ps.getSelf(p_memid);
//			接著可以放入我們的req內了，
			req.setAttribute("list", list);
//			接著下面做轉交，把我們的東西傳給下一個網站，就可以呼叫出來了！
			RequestDispatcher successView = req.
					getRequestDispatcher("/front-end/Product/selectSelfPD.jsp");
			successView.forward(req, res);
			return ;
		}
		
		if("findPD".equals(action)) {
			Integer p_id= new Integer(req.getParameter("pid"));
			ProductService ps = new ProductService();
			ProductVO productVO = ps.findPD(p_id);
			req.setAttribute("productVO", productVO);
			RequestDispatcher okView = req.
					getRequestDispatcher("/front-end/Product/findPD.jsp");
			okView.forward(req, res);
			return;
		}
		
		
		if("findOnePDforAJAX".equals(action)) {
			Integer p_id= new Integer(req.getParameter("pid"));
			ProductService ps = new ProductService();
			ProductVO productVO = ps.findPD(p_id);
			String data = new ObjectMapper().writeValueAsString(productVO);
			res.getWriter().print(data);
			return;
		}
		
		
		
		if("findPDforAJAX".equals(action)) {
			Integer p_id= new Integer(req.getParameter("pid"));
			ProductService ps = new ProductService();
			Integer data = ps.findPDforAJAX(p_id);
			PrintWriter out  = res.getWriter();
			out.print(data);
			return;

		}
		
		
		if("selectOnePD".equals(action)) {
			Integer p_id= new Integer(req.getParameter("pid"));
			ProductService ps = new ProductService();
			ProductVO productVO = ps.findPD(p_id);
			req.setAttribute("productVO", productVO);
			RequestDispatcher okView = req.
					getRequestDispatcher("/front-end/Product/selectOnePD.jsp");
			okView.forward(req, res);
			return;
		}
		
		
		
		
		if("updata".equals(action)) {
			List<Part>list = (List<Part>) req.getParts();
			String needDel[];
			if(req.getParameterValues("del") ==null) {
				 needDel = new String[0];
			}else {
				 needDel = req.getParameterValues("del");
			}
			System.out.println("安安");
			Integer p_cgid = new Integer(req.getParameter("pcgid").trim());
			System.out.println("1");
			Integer p_id = new Integer(req.getParameter("pid").trim());
			System.out.println("2");
			String p_name = req.getParameter("pname").trim();
			System.out.println("3");
			Integer p_price = new Integer(req.getParameter("pprice").trim());
			System.out.println("4");
			Integer p_stock = new Integer(req.getParameter("pstock").trim());
			System.out.println("5");
			String p_desc = req.getParameter("pdesc").trim();
			System.out.println("6");
			Integer p_sta = new Integer(req.getParameter("psta").trim());
			System.out.println("7");
			
			ProductService ps = new ProductService();
			ps.updataPD(p_id, p_name, p_price, p_stock , p_cgid , p_desc , p_sta , list , needDel);
			
			
			RequestDispatcher okView = req.
					getRequestDispatcher("/ProductServlet?action=getSelf");
			okView.forward(req, res);
			return;
		}
		
		if("add".equals(action)) {
			// req.getParrs 方法 會拿到一個陣列 內容物型別為part
			List<Part> list = (List<Part>) req.getParts();
			
			HttpSession session = req.getSession();
			
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			
			Integer p_memid = memberVO.getMem_id();
			
			Integer p_cgid = new Integer(req.getParameter("pcgid").trim());
			String p_name = req.getParameter("pname".trim());
			Integer p_price = new Integer(req.getParameter("pprice").trim());
			Integer p_stock = new Integer(req.getParameter("pstock").trim());
			String p_desc = req.getParameter("pdesc").trim();
			ProductService ps = new ProductService();
			
			// Part.getName 他可以拿到我們Part的name
			// 可以再去做後續的處理
				
			ps.addPD(p_memid,p_cgid, p_name, p_price, p_stock, p_desc , list);
			RequestDispatcher okView = req.
					getRequestDispatcher("/ProductServlet?action=getSelf");
			okView.forward(req, res);
			return;
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
