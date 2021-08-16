<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/Admin-css/addAdmin.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<title>Insert title here</title>
</head>
<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />
<body>
	<div class="adminAdd">
	<form id="form">
	<table>
	<caption>新增管理員</caption>
        <tr><th>帳號</th><td><input type="text" name="adminacc" id="adminacc"></td></tr>
        <tr class="accerrortr"><td></td><td class="accerror"></td></tr>
        <tr><th>密碼</th><td><input type="password" name="adminpw" id="adminpw"></td></tr>
        <tr class="pwerrortr"><td></td><td class="pwerror"></td></tr>
    </table>
    <table class="aut">
        <caption class="auttitle">權限配置</caption>
        <tr><th class="autid">編號</th><th>選取</th><th>功能名稱</th><th>功能詳述</th></tr>
        <c:forEach var="aut" items="${AutAll.all }">
        <tr><td>${aut.aut_id}</td><td><input class="autbox" type="checkbox" name="aut" value="${aut.aut_id}"></td><td>${aut.aut_name }</td><td>${aut.aut_con }</td></tr>
        </c:forEach>
    </table>
    <input type="hidden" name="action" value="add">
    </form>
			<button id="submit" name="action" value="add" >新增</button><input id="cancel" type="button" value="返回" >
	</div>
    <script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/Admin-js/addAdmin.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
</body>
</html>