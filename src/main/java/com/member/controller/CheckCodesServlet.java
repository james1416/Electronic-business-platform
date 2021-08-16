package com.member.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@WebServlet("/CheckCodesServlet")
public class CheckCodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width = 150;
		int height = 75;
		int red = 0, green=0, blue=0;
		Random ran = new Random();
		//創建一個驗證碼圖片對象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//美化圖片

		Graphics graphics = image.getGraphics(); //畫筆對象

		red = ran.nextInt(255);
		green = ran.nextInt(255);
		blue = ran.nextInt(255);
		
		graphics.setColor(new Color(red, green, blue)); //設置畫筆顏色
		graphics.fillRect(0, 0, width, height); //填充顏色
		
		graphics.setColor(Color.BLUE); //設置畫筆顏色
		graphics.drawRect(0, 0, width-1, height-1); //畫邊框
		
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<4;i++) {
			
			graphics.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			
			int index = ran.nextInt(str.length());
			//拿到字符
			char ch = str.charAt(index);
			sb.append(ch);
			//寫驗證碼
			graphics.drawString(ch+"", width/5*(i+1), height/2);		 
		}
		String checkCode_session = sb.toString();
		
		graphics.setColor(Color.GREEN); //設置畫筆顏色
		
		//畫十條線
		for(int i =0; i<10;i++) {
			red= ran.nextInt(255);
			green=ran.nextInt(255);
			blue=ran.nextInt(255);
			graphics.setColor(new Color(red, green, blue));
			//線的座標
			int x1 = ran.nextInt(width);
			int x2 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int y2 = ran.nextInt(height);
			
			graphics.drawLine(x1, y1, x2, y2);
		}
		HttpSession session = request.getSession();
		
		
		session.setAttribute("checkCode", checkCode_session);
		
		//圖片輸出到頁面展示
		ImageIO.write(image, "jpg", response.getOutputStream());

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
