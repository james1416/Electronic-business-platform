package com.qa_list.controller;

import java.io.IOException;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qa_list.model.QAListService;
import com.qa_list.model.QAListVO;



@WebServlet("/QAListServlet")
public class QAListServlet extends HttpServlet {
       
    public QAListServlet() {
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); //取得jsp內 name = action 的 value
		System.out.println(action); //印出取得的值
//		req.setAttribute("hello", "hello"); 測試用\
		
//	-----------------新增一筆資料----------------------
		if("add".equals(action)) {
			//創建一個如果有錯誤訊息的list,只要發生錯誤就會將錯誤訊息加進此list
			List<String> errorMsgs = new LinkedList<String>(); 
			req.setAttribute("adderrorMsgs", errorMsgs);
			//取得於JSP中,name為"qcon"的 value 也就是問題內容
			String qcon = req.getParameter("qcon");
			//取得於JSP中,name為"acon"的 value 也就是回答內容
			String acon = req.getParameter("acon");
			//錯誤判斷 ，當 問題內容 為 空(null) 或者 問題內容 去除頭跟尾的空白之後，長度為0時
			if( qcon ==null || qcon.trim().length() == 0 ) {
				// 新增一個錯誤訊息，並且加進 錯誤訊息list 裡面
				errorMsgs.add("問題請勿空白");
			}
			//錯誤判斷 ，當 回答內容 為空(null) 或者 問題內容 去除頭跟尾的空白之後，長度為0時
			if( acon == null || acon.trim().length() == 0 ) {
				// 新增一個錯誤訊息，並且加進 錯誤訊息list 裡面
				errorMsgs.add("回答請勿空白");
			}
			// 判斷 若 我們的錯誤訊息list ， 長度不是空的話將會執行
			// list.isEmpty()此指令可以判斷list內是否有東西，如果為空印出來會是[]
			if(!errorMsgs.isEmpty()) {
				//以下主要是讓使用者送出時，若有錯誤則會保存內容，不會變成又要重新輸入一次的情況發生
				//創建一個qaListVO
				QAListVO qaListVO = new QAListVO();
				//設置 問題內容 至這個vo
				qaListVO.setQal_qcon(qcon);
				//設置 答案內容 至這個vo
				qaListVO.setQal_acon(acon);
				//把這個VO 設置到我們的req內
				req.setAttribute("addVO", qaListVO);
				//轉交到我們指定的地方
				RequestDispatcher failureView = req.
						getRequestDispatcher("/back-end/QAList/selectALL.jsp");
				//帶上我們的req,res過去
				failureView.forward(req, res);
				//以下為中止程式，不加的話會往下跑
				return;
			}
			
			//以下為 若沒有任何錯誤時會執行到的程式碼
			//這邊我設置了一個結束時會有的list
			//記錄用
			Date date = new Date();
			//new出一個service
			QAListService qals = new QAListService();
			//呼叫service內的add方法 並傳入參數
			qals.add(qcon ,acon, date);
			RequestDispatcher failureView = req.
					getRequestDispatcher("/back-end/QAList/selectALL.jsp");
			//帶上他的req跟res
			failureView.forward(req, res);
			return;
		}
		
		
		
		
//		-----------------查詢一筆資料----------------------		
		
		if("findByPK".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer qalid = new Integer(req.getParameter("qal_id"));
				QAListService qaListService = new QAListService();
				QAListVO qaListVO = qaListService.findByPKList(qalid);
				System.out.println(qaListVO);
				req.setAttribute("QAListVO", qaListVO);
				boolean openModal=true;
				req.setAttribute("updata",openModal);
				String url = "/back-end/QAList/selectALL.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
			}catch (Exception e) {
				errorMsgs.add("無法取得資料:"+e.getMessage());
				RequestDispatcher successView = req.
						getRequestDispatcher("/back-end/QAList/selectALL.jsp");
				successView.forward(req, res);
				return;
			}
			
		}
		
//		-----------------修改一筆資料--------------------
		
		if("updata".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("updataErrorMsgs", errorMsgs);
			String qcon = req.getParameter("qal_qcon");
			if (qcon == null || qcon.trim().length() == 0) {
				errorMsgs.add("問題請勿為空");
			}
			String acon = req.getParameter("qal_acon");
			if (acon == null || acon.trim().length() == 0) {
				errorMsgs.add("回答請勿為空");
			}
			Integer sta =new Integer(req.getParameter("qal_sta"));
			System.out.println("狀態號碼"+sta);
			if( sta != 0 && sta != 1) {
				errorMsgs.add("狀態錯誤");
			}
			Integer id =new Integer(req.getParameter("qal_id"));
			if(!errorMsgs.isEmpty()) {
				java.sql.Date date =java.sql.Date.valueOf(req.getParameter("qal_tsp").trim()) ;
				QAListVO qaListVO = new QAListVO();
				qaListVO.setQal_qcon(qcon);
				qaListVO.setQal_acon(acon);
				qaListVO.setQal_sta(sta);
				qaListVO.setQal_id(id);
				qaListVO.setQal_tsp(date);
				req.setAttribute("QAListVO", qaListVO);
				boolean openModal=true;
				req.setAttribute("updata",openModal);
				RequestDispatcher failureView =req.
						getRequestDispatcher("/back-end/QAList/selectALL.jsp");
				failureView.forward(req, res);
				return;
				}
			
			Date date = new Date();
			QAListService qals = new QAListService();
			qals.updata(qcon, acon, sta , date , id);
			List<String> done = new LinkedList<String>();
			req.setAttribute("done", done);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time = sdf.format(date);
			done.add("於"+ time +"成功修改了這一筆資料！");
			RequestDispatcher successView = req.
					getRequestDispatcher("/QAListServlet?action=findByPK&qal_id="+id);
			successView.forward(req, res);
			return;
			}
		}	
		
//		String url ="/back-end/QAList/addQAList.jsp"; 
//		RequestDispatcher successView = req.getRequestDispatcher(url); //轉交
//		successView.forward(req, res);
//		------------上面為 forword ------
//		req.setAttribute("action", "insert");
//		RequestDispatcher successView = req.getRequestDispatcher("/back-end/QAList/selectAllQAList.jsp");
//		successView.forward(req, res);
//		return;
		
		
		
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
