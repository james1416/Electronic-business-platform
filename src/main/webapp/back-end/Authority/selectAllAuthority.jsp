<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/Authority-css/selectAllAuthority.css">
<title>Insert title here</title>
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
	<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />
	<div class="main" id="main">
		<div class="maintop">
			<img src="<%=request.getContextPath()%>/Img/Enlogo.png" alt=""> <span
				class="listname">權限總覽</span> <span class="hello">您好！ ${admin.admin_acc} ！</span>
		</div>
		<div class="search">
			<button type="submit" id="add">新增權限</button>
		</div>
		<div class="mainbody">
			<table id="table">
				<tr class="maintr">
					<th>權限編號</th>
					<th>權限名稱</th>
					<th>權限內容</th>
					<th>管理</th>
				</tr>
				<c:forEach var="aut" items="${AutAll.all}">
				<tr>
					<td>${aut.aut_id}</td>
					<td>${aut.aut_name}</td>
					<td>${aut.aut_con}</td>
					<td><a href="<%=request.getContextPath()%>/AuthorityServlet?action=findone&autid=${aut.aut_id}">修改</a></td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
<c:if test="${findAutVO != null}">
	<c:if test="${openFind!=null}">
               <jsp:include page="updataAut.jsp" />
	</c:if>
</c:if>



<jsp:include page="addAut.jsp" />
<c:if test="${addErrorMsgs != null }">
	<script>
		$("#addAut").modal({show: true});
	</script>
</c:if>
	<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/LeftList.js"></script>
	<script>
	document.getElementById("add").addEventListener("click",function(){
		$("#addAut").modal({show: true});
    })
	</script>
</body>
</html>