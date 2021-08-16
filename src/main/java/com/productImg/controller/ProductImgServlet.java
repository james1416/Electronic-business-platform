package com.productImg.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productImg.model.ProductImgService;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 10 * 10 * 1024 * 1024)
@WebServlet("/ProductImgServlet")
public class ProductImgServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		
		String action = req.getParameter("action");
		if("getonepic".equals(action)) {
			String pid = req.getParameter("pi_pid");
			Integer pi_pid = Integer.parseInt(pid);
			ProductImgService pis = new ProductImgService();
			String data = pis.get(pi_pid);
			ObjectMapper mapper = new ObjectMapper();
			res.getWriter().write(mapper.writeValueAsString(data));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//----------- 以下為讀圖片的殘骸 ----------------		
//		String action = req.getParameter("action");
//		if("add".equals(action)) {
//			System.out.println(req.getParameter("pid"));
//			Integer pi_pid= new Integer(req.getParameter("pid"));
//			
//			
//			System.out.println("進來了");
//			List<Part> imgs = (List<Part>) req.getParts();
//			System.out.println(imgs);
//			System.out.println(imgs.size());
//			if(imgs.size() == 1) {
//				System.out.println("沒放圖片");
//				return;
//			}
//			
//			for(int i = 0 ; i < imgs.size() ; i++) {
//				if(i == imgs.size()-1 || i == imgs.size()-2){
//					System.out.println("結束");
//					return;
//					}
//				InputStream in = imgs.get(i).getInputStream();
//				if(in.available() == 0) {
//					System.out.println("沒東西上傳");
//					return;
//				}
//				byte[] img = new byte[in.available()];
//				in.read(img);
//				in.close();
//				ProductImgService pis = new ProductImgService();
//				pis.insert(pi_pid, img);
//			}
//			
			
			
			
			
//			for(Part p : a) {
//				InputStream in= p.getInputStream();
//				byte[] img = new byte[in.available()];
//				in.read(img);
//				in.close();
//				ProductImgService pis = new ProductImgService();
//				pis.insert(3, img);
//			}
			
			
			
			
			
//			
//			for(int i = 0 ; i < piimage.size() ; i++) {
//				Part q= piimage.iterator().next();
//				InputStream in = q.getInputStream();
//				byte[] img = new byte[in.available()];
//				System.out.println("這張圖片大小為:"  + in.available());
//				in.read(img);
//				in.close();
//				ProductImgService pis = new ProductImgService();
//				pis.insert(1, img);
//			}
//			Integer pi_pid= new Integer(req.getParameter("pid"));
//			System.out.println(pi_pid);
//			InputStream in = piimage.getInputStream();
//			System.out.println(in);
//			byte[] pi_image = new byte[in.available()];
//			in.read(pi_image);
//			in.close();
//			ProductImgService pis = new ProductImgService();
//			pis.insert(9, pi_image);
//			System.out.println("成功了嗎?");
//		}
		
		
		
//		if("get".equals(action)) {
//			
//			ProductImgService pis = new ProductImgService();
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
