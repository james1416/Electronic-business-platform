<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/AdminAuthority-css/selectAllAdminAuthority.css">
</head>
<body>
	<div class="leftList">
		<img src="<%=request.getContextPath()%>/Img/全部去背.png" alt="">
		<ul class="list">
			<li class="memberlist">會員總覽</li>
			<li class="orderlist">訂單總覽</li>
			<li class="pdlist">商品檢舉管理</li>
			<li class="rplist">會員檢舉管理</li>
			<li class="conlist">討論區檢舉管理</li>
			<li class="newlist">最新消息管理</li>
			<li class="qalist">Q&A管理</li>
			<li class="aclist">討論區公告管理</li>
			<li class="srlist">平台報表</li>
			<li class="adminlist">管理員總覽</li>
			<li class="autlist">權限管理</li>
		</ul>
	</div>
	<div class="main" id="main">
		<div class="maintop">
			<img src="<%=request.getContextPath()%>/Img/Enlogo.png" alt=""> <span
				class="listname">權限總覽</span> <span class="hello">您好！ ${admin.admin_acc}！</span>
		</div>
		<jsp:useBean id="AdminAll" scope="page" class="com.admin.model.AdminService" />
		<jsp:useBean id="AdminAutAll" scope="page" class="com.admin_authority.model.AdminAuthorityService" />
		<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />
		<div class="search">
			<button type="submit" id="add">配置權限</button>
			<button type="submit" id="add">新增權限</button>
			
		</div>
		
		<div class="mainbody">
		
			<table id="table">
				<tr class="maintr">
					<th>管理員編號</th>
					<th>管理員帳號</th>
					<th>權限配置</th>
				</tr>
			
			<c:forEach var="admin" items="${AdminAll.all}" >
				<tr>
					<td>${admin.admin_id }</td>
					<td>${admin.admin_acc }</td>
					<td><div class="checkdiv">
					<%int i = 1; %>
					<form  method = "post" action = "<%=request.getContextPath()%>/AdminAuthorityServlet">
					<input type="hidden" name="action" value="${admin.admin_id }">
					
					<c:forEach var="Aut" items="${AutAll.all }" >
					
								<input type="checkbox" value="${Aut.aut_id}" name="${admin.admin_id }" 
								
								<c:forEach var="AdAut" items="${AdminAutAll.getone(admin.admin_id)}">
									${AdAut.aa_autid == Aut.aut_id ? "checked" : "" }
								</c:forEach>
								>
								${Aut.aut_id }:${Aut.aut_name }
								<%if(i == 5){
									out.print("<br>");
								};
								i++;
								%>
					</c:forEach>
					<button class="update">修改</button>
					</form>
					</div></td>
				</tr>
			</c:forEach>
			</table>
			
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/LeftList.js"></script>
</body>
</html>