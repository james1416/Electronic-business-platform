<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="atMS" scope="page" class="com.articlemessage.model.ArticleMessageService" />
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<link href="/CFA101G4/CSS/Forum-css/MyReport.css" rel="stylesheet" type="text/CSS">
<title>天堂鳥討論區</title>
<style>
#GOTOP:hover{
	cursor: pointer;
	color: blanchedalmond;
}
</style>
</head>
<body>
<!--至頂按鈕 -->
		<i id="GOTOP" class="bi bi-arrow-up-square position-fixed bottom-0 end-0 translate-middle" style="font-size: 2rem;display:none;color:white;"></i>
	<div class="container p-4 bg-light mt-4" style="border-radius:20px;">
		<h3 class="text-center">我的檢舉</h3>
		<div class="text-end mb-1">
		<a type="button" class="btn btn-outline-success" href="${pageContext.request.contextPath}/front-end/index.jsp" role="button">回討論區首頁</a>
		<button type="button" class="btn btn-outline-secondary" id="hideall">全部收起</button></div>
        <div class="row text-center p-2 border-bottom bg-secondary"  style="border-radius:20px;">
            <div class="col">被檢舉人</div>
            <div class="col">檢舉時間</div>
            <div class="col">審核狀態</div>
            <div class="col"></div>
        </div>
 	<c:forEach var="articleRep" items="${AtReport}" varStatus="status">
        <div class="row p-1 border-bottom justify-content-center">
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">${articleRep.memname}</p>
            </div>
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">${articleRep.reptime}</p>
            </div>
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">
					<c:if test="${articleRep.status== 0}"> 待審核 </c:if>
					<c:if test="${articleRep.status== 1}"> 審核通過 </c:if>
					<c:if test="${articleRep.status== 2}"> 審核未通過</c:if>	
				</p>
            </div>
            <div class="col text-center">
                <button class="btn btn-secondary mt-2" type="button" data-bs-toggle="collapse" data-bs-target="#e${status.count}"
                    aria-expanded="false" aria-controls="collapseExample" id="Ud" >查看詳情
                </button>
            </div>
            <div class="collapse w-100 mb-1" id="e${status.count}" >
                <div class="card mt-2" style="border-radius: 40x;">
                    <div class="card-header">檢舉內容--文章 </div>
                    <div class="card-body">
                        	<h5 class="card-title">文章標題<br>${articleRep.attitle}</h5>
                        	<hr>
                        <h5 class="card-title">檢舉原因描述</h5>
                        <p class="card-text">${articleRep.text}</p>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
<!--         留言檢舉區 -->
        <c:forEach var="MsReport" items="${MsReport}" varStatus="sta">
       <div class="row p-1 border-bottom justify-content-center">
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">${MsReport.memname}</p>
            </div>
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">${MsReport.msrtime}</p>
            </div>
            <div class="col text-center">
                <p class="fs-6 mt-3 mb-0 p-1">
                
					<c:if test="${MsReport.msrsta== 0}"> 待審核 </c:if>
					<c:if test="${MsReport.msrsta== 1}"> 審核通過 </c:if>
					<c:if test="${MsReport.msrsta== 2}"> 審核未通過</c:if>	
				</p>
            </div>
            <div class="col text-center">
                <button class="btn btn-secondary mt-2" type="button" data-bs-toggle="collapse" data-bs-target="#a${sta.count}"
                    aria-expanded="false" aria-controls="collapseExample" >查看詳情
                </button>
            </div>
            <div class="collapse w-100 mb-1" id="a${sta.count}"  >
                <div class="card mt-2" style="border-radius: 40x;">
                    <div class="card-header">被檢舉內容--留言 </div>
                    <div class="card-body">
                        <p class="card-text">${MsReport.msgtext}</p>
                        <hr>
                        <h5 class="card-title">檢舉原因描述</h5>
                        <p class="card-text">${MsReport.msrtext}</p>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/JS/Forum-js/MyReport.js"></script>
</body>
</html>