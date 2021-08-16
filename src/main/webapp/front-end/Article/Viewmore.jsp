<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="atmem" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="atMSSvc" scope="page" class="com.articlemessage.model.ArticleMessageService" />
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" />
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.5.0.js"integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc="
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<!-- FB--SDK -->
<script async defer crossorigin="anonymous" src="https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v11.0" nonce="HhVUmARJ"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="/CFA101G4/CSS/Forum-css/NavTopbar.css" rel="stylesheet" type="text/CSS">
<link href="/CFA101G4/CSS/Forum-css/Viewmore.css" rel="stylesheet" type="text/CSS">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<title>天堂鳥討論區</title>
</head>
<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-dark text-white" id="topbar">
			<div class="container-fluid" style="padding: 0px 8px 0px 8px;">
				<div  id="listbtn"><i class="bi bi-arrow-down-square"></i></div>
				<!-- 左側選單 -->
				<div class="side">
					<ul class="list-group list-group-flush" style="text-align: center; margin-top: 10px;">
						<li id= "USRpic">
							<c:if test="${memberVO == null }"><img src="${pageContext.request.contextPath}/Img/logo1.png" id="USR"></c:if>
						</li>
						<li style="margin: 0px 0px 10px 0px; color: rgba(177, 207, 207, 0.747)" value="${memberVO.mem_id}" id="USRUID">
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
				<form class="d-flex" METHOD="post" ACTION="<%=request.getContextPath()%>/article/ArticleServlet.do?action=getSomeAtB"
						style="width: 90%;">
				<input class="form-control me-1 " id="artname" type="search" placeholder="文章搜尋" aria-label="Search" name="title">
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
	<!--BODY區 -->
	<div class="container p-2" style="margin-top: 80px;">
		<div class="row">
			<!-- 左側文章區 -->
			<div class="col-11"
				style="background-color: white; opacity: 0.88; padding-right: 15px; border-radius: 10px;">
				<div class="col-12 text-end bg-secondary mb-2 mt-2" style="border-bottom: 1px solid rgb(199, 199, 199); 
				margin-top: 5px; border-radius:0px 10px 10px 0px;">
					<img id="bird" src="${pageContext.request.contextPath}/Img/logo1.png" class="img-fluid p-2" width="12%">
				</div>
				<!-- 文章區 -->
				<div class="row"
					style="border-bottom: 1px solid rgb(199, 199, 199);">
					<div class="col-2 p-1">
						<div class="col ">
							<div class="p-2">
								<img src="data:image/png;base64, ${pis.out(artVO.mempic)}" class="img-fluid w-100" alt="" 
								style="border-radius: 10px; height:120px;max-height:120px;">
							</div>
							<ul class="list-group text-center">
								<li class="list-group-item p-1 border-0" value="${artVO.memid}" id="postname" style="color: cadetblue;">${artVO.memname}
									</li>
								<li class="list-group-item p-1 border-0">樓主</li>
							</ul>
						</div>
					</div>
						<div class="col-10">
							<div class="col-12 fs-3">
								<div class="col" id="tiele">
									<p>${artVO.title}</p>
								</div>
							</div>
						<div class="row"
							style="border-bottom: 1px solid rgb(199, 199, 199);">
							<div class="col align-self-center" style="padding-top: 10px;">
								<nav class="nav ">
									<p class="fs-6 mb-0"
										id="atPosttime">發布於${artVO.postime}</p>
									<p class="fs-6" style="margin: 0px 5px 0px 5px;"></p>
								</nav>
							</div>
							<div class="col">
								<ul class="nav justify-content-end p-1">
									<li class="nav-item ">
										<button type="button" class="btn btn-danger" id="likeArticle" value="${artVO.atid}"
											style="margin-right: 20px;">
											<i class="bi bi-heart">${artVO.like}</i>
										</button>
									</li>
									<li class="nav-item">
										<button type="button" class="btn btn-outline-secondary" id="collection" value="${artVO.atid}"
											style="margin-right: 20px;">收藏</button>
									</li>
									<li class="nav-item">
										<button type="button" class="btn btn-outline-secondary"
											style="margin-right: 20px;" value="${artVO.atid}" id="Reply">我要回覆</button>
									</li>
									<li class="nav-item">
										<button type="button" class="btn btn-light dropdown-toggle" id="atReport" data-bs-toggle="dropdown" aria-expanded="false">
											<i class="bi-exclamation-octagon"></i>
										</button>
										  <ul class="dropdown-menu" id="dropfuntion">
    										<li><a class="dropdown-item" id="reparticle" 
    										href="<%=request.getContextPath()%>/ArticleReportList?action=ATReport&atid=${artVO.atid}" target="_blank">檢舉文章</a></li>
										  </ul>
									</li>
								</ul>
							</div>
						</div>
						<div class="col-12" style="height: 400px;" id="articleText">${artVO.text}</div>
						<div class="col">
						<div class="row mb-0 p-1 align-items-center text-center">
							 <div class="col-1 me-2 pb-2" id="tagfb"></div>
  							 </div>
						</div>
					</div>
				</div>
				<!-- 文章區END -->
				<!-- 留言區 -->	<% int i = 1; %>
<!-- 				<nav class="navbar fixed-bottom navbar-light bg-transparent" id="cccc" style="display: none;"> -->
<!-- 					  <div class="container-fluid justify-content-center"> -->
<!-- 					    <button type="button" class="btn btn-success m-1" id="submit">確定修改</button> -->
<!-- 						<button type="button" class="btn btn-danger m-1" id="Cancel">取消</button> -->
<!-- 						<label class="mb-0" id="hint"></label> -->
<!-- 					  </div> -->
<!-- 					</nav> -->
				<c:forEach var="atMSG" items="${atMSSvc.all}" varStatus="status">
					<c:if test="${artVO.atid==atMSG.atid}">
						<div class="row" style="margin-top: 30PX;" id="das">
							<div class="col-2 p-1">
								<div class="col">
									<div class="p-2">
										<img src="data:image/png;base64, ${pis.out(atMSG.mempic)}"
											class="img-fluid w-100" alt="" style="border-radius: 10px; height:120px;max-height:120px; ">
									</div>
									<ul class="list-group text-center">
										<li class="list-group-item p-1 border-0" style="color: cadetblue;" id="msgname" value="${atMSG.msgid}" ss="${atMSG.memid}">
												${atMSG.memname}
										</li>
										<li class="list-group-item p-1 border-0">樓層<%= i++ %></li>
									</ul>
								</div>
							</div>
							
							<div class="col-10">
								<div class="col-11">
									<div class="input" id="msgcontent" style="height: 200px;outline: none; border: 1px solid rgb(199, 199, 199); border-radius: 10px;">
									<textarea class="form-control border-0" placeholder="留言..." id="mymsg" style="height: 100%;resize : none; outline: none;" readonly>${atMSG.msgtext}</textarea>
									</div>
								</div>
									<div class="row justify-content-between p-1">
										<div class="col" style="border-right: 1px solid rgb(199, 199, 199);">留言時間${atMSG.msgtime}</div>
										<div class="col text-center">
											<button class="btn btn-light dropdown-toggle" id="Msgrep" data-bs-toggle="dropdown" aria-expanded="false"
											style="padding: 3px 15px 3px 15px; border-radius: 10%;" value="${atMSG.msgid}" >
												<i class="bi-exclamation-octagon"></i>
											</button>
											<span id="texs"></span>
												<ul class="dropdown-menu" id="msgbox">
	    										<li><a class="dropdown-item" id="repArticleMsg" 
	    										href="<%=request.getContextPath()%>/MessageReportServlet?action=atMessageRep&msgid=${atMSG.msgid}" target="_blank">檢舉留言</a></li>
											  </ul>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					<% if(i==1){%>
				<div class="row text-center" style="margin-top: 30PX;">
							<div class="col p-3">
								<p class="fs-2">還沒有人留言~</p>
							</div>
						</div>
			<% }; %>	
				<!-- 留言插入區 -->
			</div>
			<!-- 右側留空欄 -->
			<div class="col m-3"></div>
		</div>
		<!-- 尾巴 -->
		<!-- 文章回復彈跳視窗 -->
		<div class="modal fade" id="ReplyModal" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-xl" style="margin:65px 93px 20px 150px">
				<div class="modal-content" style="background-color: white; opacity: 0.95;">
					<div class="modal-header p-2 ">
						<h5 class="modal-title " id="exampleModalLabel">文章回覆</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body" id="Replybox">
						<div class="card mb-3" style="max-width: 100%;">
							<div class="row g-0">
								<div class="col-md-4">
									<img src="data:image/png;base64, ${pis.out(artVO.mempic)}"
								class="img-fluid" alt=""  style="border-radius: 20px; height: 120px; padding: 2px 10px 2px 10px;">
								</div>
								<div class="col-md-8">
									<div class="card-body">
										<h5 class="card-title" id="ReplyTitle"></h5>
										<p class="card-text" id="articleCard"></p>
										<p class="card-text">
										<small class="text-muted" id="Reptime"></small>
										</p>
									</div>
								</div>
							</div>
						</div>
						<div class="form-floating">
							<textarea class="form-control" placeholder="Leave a comment here"
								id="RepMsg" style="height: 100px"></textarea>
							<label for="RepMsg">我的回覆</label>
							<p class="fs-6" id="Repwarn"></p>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" id="RepSubmit">確定送出</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 文章回復彈跳視窗END -->
	</div>
	<!--至頂按鈕 -->
		<i id="GOTOP" class="bi bi-arrow-up-square position-fixed bottom-0 end-0 translate-middle" style="font-size: 2rem;display:none;color:white"></i>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/JS/Forum-js/NavTopbar.js"></script>
	<script src="${pageContext.request.contextPath}/JS/Forum-js/Viewmore.js"></script>
	
</body>
</html>