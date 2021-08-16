<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="atlist" scope="page" class="com.article.model.ArticleService" />

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<link href="/CFA101G4/CSS/Forum-css/MyCollection.css" rel="stylesheet" type="text/CSS">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<title>我的收藏</title>
</head>

<body>
	<div class="container mt-5 p-1">
		<img src="/CFA101G4/Img/logo.png" class="img-thumbnail border-0 bg-transparent position-absolute top-20  translate-middle-y"
			style="height: 100px;"> <img src="/CFA101G4/Img/logo1.png"
			class="img-thumbnail border-0 bg-transparent position-absolute top-20 start-50 translate-middle-y"
			style="height: 100px;">
		<p class="fs-3 text-center mt-5" style="color:rgba(223, 223, 223, 0.712);">我的收藏</p>
			<div class="col text-end"><a class="btn btn-outline-success" href="/CFA101G4/front-end/index.jsp" role="button">回首頁</a></div>
		<div class="row m-0 mt-3 ps-2 pe-2 pt-3 pb-2 " style="background-color: white; opacity: 0.78; border-radius: 20px;">
			<div class="col-12">
				<div class="row m-0"><% int i = 1; %>
					<c:forEach var="articleVO" items="${atlist.all}" varStatus="staAt">
						<c:forEach var="myCol" items="${mycol}" varStatus="status">
							<c:if test="${myCol.atid==articleVO.atid}"><%i++; %>
								<div class="col-4 p-2 ">
									<div class="card" style="width: 100%;">
										<div class="card-body">
											<h5>文章標題</h5>
											<h5 class="card-title text-truncate">${articleVO.title}</h5>
											<h6 class="card-subtitle mb-2 text-muted">
												<small>收藏時間${myCol.time}</small></h6>
											<p class="card-text" id="coltext">${articleVO.text}</p>
											<a class="btn btn-secondary" href="<%=request.getContextPath()%>/ViewmoreArticleServlet?content=${articleVO.atid}" role="button">查看更多</a>
											<button type="button" class="btn btn-secondary" id="delcol"
												value="${articleVO.atid}">移除收藏</button>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</c:forEach>
				<% if(i==1){%>
				<div class="row text-center" style="margin-top: 20PX;">
							<div class="col p-3">
								<p class="fs-2">無收藏的文章~</p>
							</div>
						</div><% }; %>	
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<script src="/CFA101G4/JS/Forum-js/MyCollection.js"></script>
</body>
</html>