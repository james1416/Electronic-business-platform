<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pd.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
PdVO pdVO = (PdVO) request.getAttribute("PdVO");
Integer number = (Integer) request.getAttribute("NUMBER");
Integer price = pdVO.getPd_price();
Integer total = number * price;
%>




<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/CFA101G4_40/CSS/Front-end/CheckOrder.css">
<meta charset="UTF-8">
<title>填寫訂單</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 1000px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}

img {
	width: 200px;
	heigth: 200px
}
</style>
</head>
<body>
	<FORM METHOD="post" action="<%=request.getContextPath()%>/PoServlet">


		<table>
			<tr>
				<th>預購商品編號</th>
				<th>賣家會員編號</th>
				<th>預購商品名稱</th>
				<th>預購商品價格</th>
				<th>商品描述</th>
				<th>購買數量</th>
				<th>總金額</th>
			</tr>
			<tr>
				<td><%=pdVO.getPd_id()%></td>
				<td><%=pdVO.getPd_smemid()%></td>
				<td><%=pdVO.getPd_name()%></td>
				<td><%=pdVO.getPd_price()%></td>
				<td><%=pdVO.getPd_desc()%></td>
				<td><%=number%></td>
				<td><%=total%></td>
			</tr>
		</table>
		<div>

			<div>
				<h1>收件人資訊</h1>
				收件人<input type="text" name="PO_BNAME"> 電話<input type="text"
					name="PO_TEL"> 收件地址<input type="text" name="PO_HOME">
			</div>
		</div>
		<div>
			<h1>您的錢包餘額 :${memberVO.mem_ele}</h1>
		</div>
		<input type="hidden" name="PD_ID" value="<%=pdVO.getPd_id()%>">
		<input type="hidden" name="PD_SMEMID" value="<%=pdVO.getPd_smemid()%>">
		<input type="hidden" name="PO_QTY" value="<%=number%>"> 
		<input type = "hidden" name="PO_PAY" id="PO_PAY" value="1">		
		<input type="hidden" name="PO_PRICE" value="<%=total%>">
		<button name="action" value="addOrder">送出訂單</button>
	</FORM>

</body>
</html>