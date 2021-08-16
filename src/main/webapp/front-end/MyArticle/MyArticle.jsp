<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc="
	crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<!-- 以下為匯入css -->
<link href="/CFA101G4/CSS/Forum-css/NavTopbar.css" rel="stylesheet" type="text/CSS">
<link href="/CFA101G4/CSS/Forum-css/MyArticle.css" rel="stylesheet" type="text/CSS">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">

<title>我的文章</title>
</head>
<body  >
			<nav class="navbar navbar-expand-lg navbar-light bg-dark text-white" id="topbar">
			<div class="container-fluid" style="padding: 0px 8px 0px 8px;">
				<div  id="listbtn"><i class="bi bi-arrow-down-square"></i></div>
				<!-- 左側選單 -->
				<div class="side">
					<ul class="list-group list-group-flush" style="text-align: center; margin-top: 10px;">
						<li id= "USRpic">
							<c:if test="${memberVO == null }"><img src="${pageContext.request.contextPath}/Img/logo1.png" id="USR"></c:if>
						</li>
						<li style="margin: 0px 0px 10px 0px; color: rgba(177, 207, 207, 0.747)" id="USRUID">
							<c:if test="${memberVO == null }"><p>未登入</p></c:if> 
						</li>
						<li class="list-group-item" id="postmyarticle">我要發文</li>
						<li class="list-group-item" id="myarticle">
						<a href="<%=request.getContextPath()%>/MyArticleServlet">我的文章</a></li>
						<li class="list-group-item" id="mycolarticle">
						<a href="<%=request.getContextPath()%>/MyCollectionServlet?action=myarticleCol">收藏的文章</a></li>
						<li class="list-group-item" id="myrep">
						<a href="<%=request.getContextPath()%>/ArticlereportServlet">檢舉查看</a></li>
					</ul>
				</div>
				<a href="/CFA101G4/front-end/index.jsp"><img src="${pageContext.request.contextPath}/Img/logo.png" alt="" id="logophot"></a> 
					<a class="navbar-brand" href="#" id="logo"><sub>天堂鳥</sub></a>
					<!--視窗縮小產生 -->
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse justify-content-around  ms-2" id="navbarSupportedContent">
				<form class="d-flex " METHOD="post" ACTION="<%=request.getContextPath()%>/article/ArticleServlet.do?action=getSomeAtB"
								style="width: 90%;">
				<input class="form-control me-1" id="artname" type="search" placeholder="文章搜尋" aria-label="Search" name="title">
				<button id="secbtn" class="btn btn-outline-success ms-1" type="submit" style="width: 70px;">搜尋</button>
				</form>
					<ul class="navbar-nav mb-2 mb-lg-0" id="topfn">
						<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="/CFA101G4/front-end/Member/FrontMain.jsp">
						 <i class="bi bi-house-door d-inline-block" id="house"></i></a></li>
						<li class="nav-item"><a class="nav-link" href="/CFA101G4/front-end/CartList/selectCL.jsp"><i class="bi bi-cart2"></i></a></li>
						<li class="nav-item" id="Memberfunction"><a class="nav-link" href="#"><i class="bi-person-circle"></i></a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- 人頭功能塊 -->
		<div id="topfunction">
			<ul class="list-group list-group-horizontal justify-content-end" style="background-color: rgba(124, 123, 123, 0.863);">
			  <li class="list-group-item p-1" id="memcenter">會員中心</li>
			  <li class="list-group-item p-1" id="logoin"></li>
			</ul>
		</div>
<!--  文章列表區 -->
    <div class="container p-2" style="margin-top: 100px;background-color: white; opacity: 0.88; border-radius: 20px;">
        <div class="col-12 text-center">
            <p class="fs-3 mb-2">我的文章</p><span id="total"></span>
        </div>
<!--BODY區 -->
  <div>
	<c:forEach var="articleVO" items="${art}" varStatus="artVO" >
        <div class="card mb-3" style="background-color:${artVO.index % 2 == 1 ? 'rgb(216, 216, 216)' : 'rgb(255, 255, 255)'}" id="allmyart">
            <div class="row g-0">
                <div class="col-md-1 align-self-center">
                    <img src="data:image/png;base64, ${pis.out(articleVO.mempic)}" class="img-fluid ms-1" alt="..." style="height:120px; border-radius: 10px;" >
                </div>
                <div class="col-md-11">
                    <div class="card-body">
                    <input type="text" class="border-0 bg-transparent fs-4 w-100"  value="${articleVO.title}" readonly style="outline-style: none;">
                    <p class="mb-2"> </p>
                        <p class="border-0 bg-transparent p-1" id="myArticleText" >${articleVO.text}</p>
                         <div class="row mt-1">
                          <div class="col-5 p-1"><p class="card-text"><small class="text-muted">發文時間${articleVO.postime}</small></p></div>
                         </div>
                    </div>
                </div>
            </div>
            <div class="row ">
                <div class="col align-self-center">
                    <ul class="list-group list-group-horizontal ">
                        <li class="list-group-item p-1"><button type="button" class="btn btn-danger">
                            <i class="bi-heart-fill m-1"></i>${articleVO.like}</button></li>
                        <li class="list-group-item p-1">
                        	<button type="button" class="btn btn-outline-dark" style="padding: 6px 16px 6px 16px;">
                            <i class="bi-chat-left-text m-1"></i>
                            </button>
                          </li>
                    </ul>
                </div>
                <div class="col p-3 text-end align-self-center">
                        <button type="button"  value="${articleVO.atid}" class="btn btn-light dropdown-toggle"  data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi-exclamation-octagon"></i>
                        </button>
                          <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/CFA101G4/article/ArticleServlet.do?action=USR_For_Update&atid=${articleVO.atid}" target="_parent">編輯文章</a></li>
                            <li><button type="button" class="dropdown-item" value="${articleVO.atid}" id="del">刪除文章</button></li>
                          </ul>
                	</div>
        		</div>
        	</div>
        </c:forEach>
       </div>
    </div>
    	<!--至頂按鈕 -->
		<i id="GOTOP" class="bi bi-arrow-up-square position-fixed bottom-0 end-0 translate-middle" style="font-size: 2rem;display:none;color:white;"></i>
    	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
		<script src="/CFA101G4/JS/Forum-js/MyArticle.js"></script>
		<script src="${pageContext.request.contextPath}/JS/Forum-js/NavTopbar.js"></script>
</body>
</html>