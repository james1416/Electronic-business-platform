<%@page import="com.admin.model.AdminVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/Admin-css/selectAllAdmin.css">
<title>Document</title>
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
				class="listname">管理員總覽</span> <span class="hello">您好！ ${admin.admin_acc} ！</span>
		</div>
		<div class="search">
		<form method="post" action="<%=request.getContextPath()%>/AdminServlet">
<!-- 			<select name="" id=""></select> -->
			<input name="admin_id" placeholder="輸入編號搜尋">
			<input name="admin_acc" placeholder="輸入帳號搜尋">
			<input name="admin_pw" placeholder="輸入密碼搜尋">
			<button type="submit" name="action" value="search" class="sebt">搜尋</button>
		</form>	
			<button type="submit" id="add">新增員工</button>
		</div>
		<div class="mainbody">
			<table id="table">
				<tr class="maintr">
					<th>管理員編號</th>
					<th>管理員帳號</th>
					<th>管理員密碼</th>
					<th>最後登入時間</th>
					<th>管理員狀態</th>
					<th>管理</th>
				</tr>
				<c:forEach var="admin" items="${list }">
				 <tr>
                	<td>${admin.admin_id}</td>
                	<td>${admin.admin_acc}</td>
                	<td>${admin.admin_pw}</td>
                	<td>
                	<fmt:formatDate value="${admin.admin_log }" pattern="yyyy-MM-dd HH:mm:ss"/>
                	<c:if test="${admin.admin_log == null }">
                	尚未登入過
                	</c:if>
                	</td>
                	<td>${admin.admin_sta == 0 ? "禁用" : "啟用"}</td>
                	<td>
                	<form  method="post"  action="<%=request.getContextPath()%>/AdminServlet">
                	<input type="hidden" name="admin_id" value="${admin.admin_id}">
                	<a href="<%=request.getContextPath()%>/AdminServlet?action=findOne&admin_id=${admin.admin_id}">修改</a>
<!--                 	<button name="action" value="findOne" class="up">修改</button> -->
                	</form>
                	</td>
                </tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	
<c:if test="${showupdata !=null}">
               <jsp:include page="findAndUpdateAdmin.jsp" />
</c:if>


<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/LeftList.js"></script>
	<script>
	    document.getElementById("add").addEventListener("click", function(){
	    	window.location.href="<%=request.getContextPath()%>/back-end/Admin/addAdmin.jsp"
	    	
	    })
	</script>
</body>
</html>