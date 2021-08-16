package com.bpclassification.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bpclassification.model.BpClassificationService;
import com.bpclassification.model.BpClassificationVO;

@WebServlet("/BpClassificationServlet")
public class BpClassificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String str = req.getParameter("bpc_id");
				if(str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入類別名稱");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}
				Integer bpc_id = null;
				try {
					bpc_id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("類別名稱格式錯誤");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}
				BpClassificationService bpcSvc = new BpClassificationService();
				BpClassificationVO bpcVO = bpcSvc.getOneBpc(bpc_id);
				
				if(bpcVO == null) {
					errorMsgs.add("查無相關資料");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("bpcVO", bpcVO);
				String url = "";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, res);
			}
			
			
		}
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer bpc_id = new Integer("bpc_id");
				BpClassificationService bpcSvc = new BpClassificationService();
				BpClassificationVO bpcVO = bpcSvc.getOneBpc(bpc_id);
				req.setAttribute("bpcVO", bpcVO);
				String url  ="";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("無法取修改的資料"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, res);
			}
		}
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer bpc_id = new Integer(req.getParameter("bpc_id").trim());
				String bpc_cgname = req.getParameter("bpc_cgname");
				String bpc_cgnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if(bpc_cgname == null || bpc_cgname.trim().length() == 0) {
					errorMsgs.add("類別名稱請勿空白");	
				}else if(!bpc_cgname.trim().matches(bpc_cgnameReg)){
					errorMsgs.add("類別名稱:只能是中、英文字母、和....長度2-10");
				}
				BpClassificationVO bpcVO = new BpClassificationVO();
				bpcVO.setBpc_cgname(bpc_cgnameReg);
				bpcVO.setBpc_id(bpc_id);
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}
				
				BpClassificationService bpcSvc = new BpClassificationService();
				bpcVO = bpcSvc.updateBpc(bpc_id, bpc_cgnameReg);
				req.setAttribute("bpcVO", bpcVO);
				String url = "";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, res);
			}

		}
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String bpc_cgname = req.getParameter("bpc_cgname");
				String bpc_cgnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if(bpc_cgname == null || bpc_cgname.trim().length() == 0) {
					errorMsgs.add("類別名稱請勿空白");	
				}else if(!bpc_cgname.trim().matches(bpc_cgnameReg)){
					errorMsgs.add("類別名稱:只能是中、英文字母、和....長度2-10");
				}
				
				BpClassificationVO bpcVO = new BpClassificationVO();
				bpcVO.setBpc_cgname(bpc_cgnameReg);
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}
				
				BpClassificationService bpcSvc = new BpClassificationService();
				bpcVO = bpcSvc.addBpc(bpc_cgnameReg);
				req.setAttribute("bpcVO", bpcVO);
				String url = "";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer bpc_id = new Integer(req.getParameter("bpc_id"));
				BpClassificationService bpcSvc = new BpClassificationService();
				bpcSvc.deleteBpc(bpc_id);
				
				String url = "";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("");
				failureView.forward(req, res);
			}
		}
		
	}

}
