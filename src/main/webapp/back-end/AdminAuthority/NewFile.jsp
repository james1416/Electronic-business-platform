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
			<img src="<%=request.getContextPath()%>/Img/英文去背.png" alt=""> <span
				class="listname">權限總覽</span> <span class="hello">您好！管理員！</span>
		</div>
		<jsp:useBean id="AdminAll" scope="page" class="com.admin.model.AdminService" />
		<jsp:useBean id="AdminAutAll" scope="page" class="com.admin_authority.model.AdminAuthorityService" />
		<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />
		<div class="search">
			<button type="submit" id="add">配置權限</button>
			<button type="submit" id="add">新增權限</button>
			
		</div>
		<div class="mainbody">

				<form method = "post" action = "<%=request.getContextPath()%>/AdminAuthorityServlet">
					<input type="checkbox" name="sa" value="1">1
					<input type="checkbox" name="sa" value="2">2
					<input type="checkbox" name="sa" value="3">3
					<input type="checkbox" name="sa" value="4">4
					<button id="submit">新增</button>
				</form>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/LeftList.js"></script>
	<script>
		$("#submit").click(function(){
			alert("安安")
		})
	</script>
</body>
</html>