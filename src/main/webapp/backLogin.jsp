<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
 <link rel="stylesheet" type="text/css" href="/CFA101G4/CSS/Back-end-css/BackLogin.css">
</head>
<body>
	<form method="post" action="/CFA101G4/AdminServlet">
	 <div class="login">
        <h1>管理員登入</h1>
        <img src="/CFA101G4/Img/all.png" >
        <h2>帳號</h2>
        <input type="text" placeholder="請輸入管理員帳號" id="acc" name="adminacc" value="${adminVO.admin_acc == null ?  '' : adminVO.admin_acc}">
        <h2>密碼</h2>
        <input type="password" placeholder="請輸入管理員密碼" id="pw" name="adminpw" value="${adminVO.admin_pw == null ?  '' : adminVO.admin_pw}">
        <c:if test= "${not empty errorMsgs }">
        	<ul>
        		<c:forEach var="msg" items="${errorMsgs }">
        	<li style="color:red">${ msg }</li>
        		</c:forEach>
        		
        	</ul>
        </c:if>
        <button id="submit" name="action" value="login">登入</button>
    </div>
    </form>
</body>
</html>