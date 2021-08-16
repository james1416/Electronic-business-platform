<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="atlist" scope="page" class="com.article.model.ArticleService" />
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<!-- 	上方為sweetalert -->
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="/CFA101G4/CSS/Forum-css/NavTopbar.css" rel="stylesheet" type="text/CSS">
<link href="/CFA101G4/CSS/Forum-css/forum.css" rel="stylesheet" type="text/CSS">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<title>天堂鳥討論區</title>
</head>
<body>
	<!-- 導覽列 -->
	<div class="container-fluid mb-5" style="padding: 0">
		<nav class="navbar navbar-expand-lg navbar-light bg-dark text-white" id="topbar">
			<div class="container-fluid" style="padding: 0px 8px 0px 8px;">
				<div  id="listbtn"><i class="bi bi-arrow-down-square"></i></div>
				<!-- 左側選單 -->
				<div class="side">
					<ul class="list-group list-group-flush" style="text-align: center; margin-top: 10px;">
						<li id= "USRpic">
							<c:if test="${memberVO == null }"><img src="${pageContext.request.contextPath}/Img/logo1.png" id="USR"></c:if>
						</li>
						<li style="margin: 0px 0px 10px 0px; color: rgba(177, 207, 207, 0.747)" id="USRUID" value="${memberVO.mem_id}">
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
					<a class="navbar-brand" href="${pageContext.request.contextPath}/front-end/index.jsp" id="logo"><sub>天堂鳥</sub></a>
					<!--視窗縮小產生 -->
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse justify-content-around" id="navbarSupportedContent">
				<input class="form-control  ms-2" style="width: 90%;" id="artname" type="search" placeholder="文章搜尋" aria-label="Search" name="title">
				<button id="secbtn" class="btn btn-outline-success ms-2" type="submit" style="width: 70px;">搜尋</button>
					<ul class="navbar-nav mb-2 mb-lg-0" id="topfn">
						<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="/CFA101G4/front-end/FrontMain.jsp">
						 <i class="bi bi-house-door d-inline-block" id="house"></i></a></li>
						<li class="nav-item"><a class="nav-link" href="/CFA101G4/front-end/CartList/selectCL.jsp"><i class="bi bi-cart2"></i></a></li>
						<li class="nav-item" id="Memberfunction"><a class="nav-link" href="#"><i class="bi-person-circle"></i></a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!--至頂按鈕 -->
		<i id="GOTOP" class="bi bi-arrow-up-square position-fixed bottom-0 end-0 translate-middle" style="font-size: 2rem;display:none;color:white;"></i>
		<!-- 人頭功能塊 -->
		<div id="topfunction">
			<ul class="list-group list-group-horizontal justify-content-end" style="background-color: rgba(124, 123, 123, 0.863);">
			  <li class="list-group-item p-1" id="memcenter">會員中心</li>
			  <li class="list-group-item p-1" id="logoin"></li>
			  <li class="list-group-item p-1">通知</li>
			</ul>
		</div>
			<!-- 輪播圖-->
		<div class="container" style="padding: 0px;">
			<div id="carouselExampleFade" class="carousel slide carousel-fade"
				data-bs-ride="carousel">
				<div class="carousel-inner" style="max-height: 250px;">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/Img/logo1.png"
							class="d-block w-100" alt="...">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/Img/logo.png"
							class="d-block w-100" alt="...">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/Img/logo1.png"
							class="d-block w-100" alt="...">
					</div>
				</div>
				<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
					<span class="visually-hidden">Previous</span>
				</button>
				<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
					<span class="visually-hidden">Next</span>
				</button>4
			</div>
			<!--中間標籤區 -->
			<div class="col" style="margin-top: 5px;">
				<ul class="nav justify-content-center p-2" style="border-bottom: 1px solid darkgray; padding-bottom: 5px;" id="tablist">
					<li class="nav-item me-2 ms-4" id="allHotAt">
					<a class="nav-link active" aria-current="page" style="color: rgba(177, 207, 207, 0.808);">熱門文章</a></li>
					<li class="nav-item me-2 ms-2"  id="allAt">
					<a class="nav-link" style="color: rgba(177, 207, 207, 0.747);">最新文章</a></li>
					<li id="qa" class="nav-item ms-2" >
					<a class="nav-link" style="color: rgba(177, 207, 207, 0.815);">注意事項</a></li>
				</ul>
			</div>
			<!--讀取圖 -->
			<div class="spinner-border text-success " role="status" id="loading">
			  <span class="visually-hidden">Loading...</span>
			</div>
			<!-- 文章區  -->
			<div style="margin-top: 20px;">
				<div class="row row-cols-2 justify-content-around" style="padding: 0px; margin: 0px;" id="art">
					<c:forEach var="articleVO" items="${atlist.all}" varStatus="status" begin="0" end="20" step="2">
						<div class="card mb-3" id="articletext">
							<div class="row g-0">
								<div class="col-md-4 align-self-center" id="USRPHOTO">
									<img src="data:image/png;base64, ${pis.out(articleVO.mempic)}" class="img-fluid" alt="..." style="height:120px;border-radius: 5px;" >
									<div class="USRID"><p>${articleVO.memname}</p></div>
									<div><p id="posttime">發佈於${articleVO.postime}</p></div>
								</div>
								<div class="col-md-8 shadow p-2 mb-5 bg-body rounded" id="textbox">
									<div class="card-body">
										<h4 class="card-title">${articleVO.title}</h4>
										<p class="card-text ">${articleVO.text}</p>
									</div>
									<div class="row  mt-1" >
										<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="<%=request.getContextPath()%>/ViewmoreArticleServlet?content=${articleVO.atid}" target="_self">查看更多</a></div>
										<div class="col" >
										<button  class="btn btn-secondary" id="colbtn" value="${articleVO.atid}">收藏</button></div>
										<div class="col" ><button class="btn" id="likeart" value="${articleVO.atid}"><i class="bi-heart" >${articleVO.like}</i></button></div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div><!-- 中間區塊結尾 -->
	</div><!-- BODY結尾 -->
    <!-- 尾巴 -->
    <div class="container-fluid justify-content-center p-0 mt-3" style="background-color: rgba(41, 43, 44, 0.804);
                    border-radius: 10px 10px 0px 0px;">
        <div class="row m-0 p-0 justify-content-around">
            <div class="col-2 p-2 mt-3">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent  p-1" style="color: rgb(223, 223, 223); border-color:rgb(223, 223, 223);">關於天堂鳥</li>
                    <li class="list-group-item  bg-transparent  p-1 "style="color: rgb(223, 223, 223);"><small>加入我們</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"style="color:rgb(223, 223, 223);"><small>天堂鳥條款</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"style="color:rgb(223, 223, 223);"><small>天堂鳥商城</small></li>
                  </ul>
            </div>
            <div class="col-2 p-2 ms-5 mt-3">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent  p-1" style="color: rgb(223, 223, 223); border-color:rgb(223, 223, 223);">客服中心</li>
                    <li class="list-group-item  bg-transparent  p-1" style="color: rgb(223, 223, 223);"><small>聯絡客服</small></li>
                    <li class="list-group-item  bg-transparent  p-1" style="color: rgb(223, 223, 223);"><small>幫助中心</small></li>
                    <li class="list-group-item  bg-transparent  p-1" style="color: rgb(223, 223, 223);"><small>賣場Q&A</small></li>
                  </ul>
            </div>
                <div class="col-2 p-2 ms-5 mt-3">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent p-1" style="color: rgb(223, 223, 223); border-color:rgb(223, 223, 223);">付款</li>
                    <li class="list-group-item  bg-transparent p-1 ps-2">
                        <img src="${pageContext.request.contextPath}/Img/VISA.png" width="30%">
                        <img src="${pageContext.request.contextPath}/Img/M2.png" width="30%" class="ms-1">
                        <img src="${pageContext.request.contextPath}/Img/M1.png" width="30%" class="ms-1">
                    </li>
                  </ul>
            </div>
            <div class="col-2 p-2 ms-5 mt-3">
            <ul class="list-group list-group-flush bg-transparent">
             <li class="list-group-item  bg-transparent p-1" style="color: rgb(223, 223, 223); border-color:rgb(223, 223, 223);">合作物流</li>
                    <li class="list-group-item  bg-transparent p-0 ps-2"><img src="${pageContext.request.contextPath}/Img/L1.png" width="100%"></li>
            </ul>
            </div>
        </div>
    </div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
				crossorigin="anonymous"></script>
	
	<script src="${pageContext.request.contextPath}/JS/Forum-js/NavTopbar.js"></script>
	<script src="${pageContext.request.contextPath}/JS/Forum-js/index.js"></script>

</body>

</html>