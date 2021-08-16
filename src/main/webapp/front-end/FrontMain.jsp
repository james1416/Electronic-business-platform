<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.0.js"
        integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<title>天堂鳥首頁</title>
<style>
    body {
        background-color: rgb(235, 235, 235);
    }

    * {
        box-sizing: border-box;
        max-height: 100%;
        max-width: 100%;
    }

    div.carousel-item>img {
        object-fit: cover;
        object-position: center;
        height: 300px;
        width: 100%;
    }

    a {
        text-decoration: none;
        color: cadetblue;
    }

    a:hover {
        color: darkgray;
    }
</style>
</head>
<body>
<jsp:useBean id="pd" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" />
<jsp:useBean id="bps" scope="page" class="com.bidproduct.model.BidProductService" />
<jsp:useBean id="imgSvc" scope="page" class="com.bpimage.model.BpImageService" />
<jsp:useBean id="pds" scope="page" class="com.pd.model.PdService" />
<jsp:useBean id="img" scope="page" class="com.pi.model.PiService" />
<jsp:useBean id="ns" scope="page" class="com.notice.model.NoticeService" />

 <!-- 標題 -->
    <div class="container-fluid p-0">
        <nav class="navbar fixed-top  navbar-expand-lg navbar-light p-0" style="background-color: #d3d3d3;">
            <div class="container">
                <div class="col">
                    <ul class="list-group list-group-horizontal " style="font-size:smaller;">
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/FrontMain.jsp"><i class="bi bi-house-door"></i>回首頁</a></li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/index.jsp"><i class="bi bi-card-text"></i>討論區</a></li>
                    </ul>
                </div>
                <div class="col">
                    <ul class="list-group list-group-horizontal justify-content-end font-monospace"
                        style="font-size:smaller;">
                        <c:if test="${memberVO == null }">
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/login.jsp">登入</a></li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/NewRegister.html">註冊</a></li>
                        </c:if>
                        <c:if test="${memberVO != null }">
                        <li class="list-group-item bg-transparent">您好！${memberVO.mem_name}</li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/MemberServlet?action=exit">登出</a></li>
                        </c:if>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Member/member_center.html">會員中心</a></li>
                        <li class="list-group-item bg-transparent" style="position: relative;"><a href="<%=request.getContextPath()%>/front-end/Member/checkAllNotices.html">通知</a>
                        <div style="position: absolute;top: 0px ;right: 10px;">
                        <c:if test="${memberVO == null }"></c:if>
                        <c:if test="${memberVO != null }">
                        	${ns.getcount(memberVO.mem_id) == null ? "" : ns.getcount(memberVO.mem_id) }
                        </c:if>
                        </div>
                    </ul>
                </div>
            </div>
        </nav>
        <!--分隔線 --><hr class="featurette-divider mt-4 mb-4 ">
        <!-- 搜尋列 -->
        <div class="container p-0">
            <div class="row align-items-center m-0">
                <div class="col text-center">
                    <img src="<%=request.getContextPath()%>/Img/logo.png" class="mb-2">
                    <span>天堂鳥購物平台</span>
                </div>
                <div class="col-8 p-0">
                    <nav>
                        <div class="nav nav-tabs justify-content-end" id="nav-tab" role="tablist">
                          <button class="nav-link active" style="color: rgb(92, 97, 83);" id="HOTAT">熱門文章</button>
                          <button class="nav-link" style="color: rgb(92, 97, 83);" id="NEWAT">最新文章</button>
                        </div>
                      </nav>
                      <div >
                        <div class="col" id="HOT"></div>
                        <div class="col" id="NEW" style="display: none"></div>
                    </div>
                </div>
            </div>
        </div>
        <!--旋轉木馬-->
        <div class="container p-0">
            <div id="carouselExampleCaptions" class="carousel carousel-dark slide mb-5 mt-4" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                        aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                        aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                        aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner ">
                    <div class="carousel-item active">
                        <img src="<%=request.getContextPath()%>/Img/CS01.jpg" class="img-fluid w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="<%=request.getContextPath()%>/Img/CS02.jpg" class="img-fluid w-100 " alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="<%=request.getContextPath()%>/Img/CS03.png" class="img-fluid w-100 " alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                    data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                    data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <!--各賣場連結 -->
        <div class="container p-0">
            <div class="row p-2 m-0 text-center">
                <div class="col-4">
                    <img src="<%=request.getContextPath()%>/Img/logo1.png" class="rounded-pill border-1 border-dark bg-light" width="70%"
                        height="80%">
                    <h3>競標專區</h3>
                    <p>競標專區描述</p>
                    <p><a href="<%=request.getContextPath()%>/front-end/BidProduct/listAllBid.jsp"><button type="button" class="btn btn-outline-success">逛逛去</button></a></p>
                </div>
                <div class="col-4">
                    <img src="<%=request.getContextPath()%>/Img/logo1.png" class="rounded-pill border-1 border-dark bg-light" width="70%"
                        height="80%">
                    <h3>購物商城</h3>
                    <p>競標專區描述</p>
                    <p><a href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage"><button type="button" class="btn btn-outline-success">逛逛去</button></a></p>
                </div>
                <div class="col-4">
                    <img src="<%=request.getContextPath()%>/Img/logo1.png" class="rounded-pill border-1 border-dark bg-light" width="70%"
                        height="80%">
                    <h3>預購專區</h3>
                    <p>競標專區描述</p>
                    <p><a href="<%=request.getContextPath()%>/front-end/PreProduct/listAllPrePd.jsp"><button type="button" class="btn btn-outline-success">逛逛去</button></a></p>
                </div>
            </div>
        </div>
        <!-- 熱門商品 -->
        <div class="container p-2">
            <hr class="featurette-divider mb-5 " style="margin-top: 100px;">
            <div class="row m-0">
                <div class="col-6 p-0">
                    <!--標題-->
                    <div class="row justify-content-between m-0 align-items-center"
                        style="border-radius:10px 10px 0px 0px; background-color:  #d3d3d3; border: 1px solid rgb(201, 197, 197);">
                        <div class="col-10">
                            <i class="bi bi-lightning-charge-fill"
                                style="font-size: 2rem; color: rgb(247, 244, 104);"></i>熱門商品
                        </div>
                        <div class="col"><a href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage"><button type="button" class="btn btn-outline-secondary p-1">看更多></button></a></div>
                    </div>
                    <!--商品列-->
                    <div class="col p-3 border-top-0"
                        style="border-radius:0px 0px 10px 10px;border: 1px solid rgb(201, 197, 197);">
                        <div class="row m-0">
                        
                        
                        	<c:forEach var="pd" begin="1" end="6" items="${pd.getall() }">
                        	<c:if test="${pd.p_sta != 0  }">
	                        	<div class="col-4">
	                        	
	                                <c:if test="${pis.check(pd.p_id) }">
		                            <img src="data:image/gif;base64, ${pis.get(pd.p_id)}" class="img-thumbnail" alt="..." style="height:200px">
			                        </c:if>
						            <!--          是否有沒有圖片  如果有 結束-->
						            <!--          是否有沒有圖片  如果沒有 開始-->
			                        <c:if test="${pis.check(pd.p_id) == false}">
			                            <img src="data:image/gif;base64, ${pis.showDefault()}" class="img-thumbnail" alt="..." style="height:200px">
			                        </c:if>
	                                
	                                <p class="fs-5 m-0 text-truncate"><a href="<%=request.getContextPath()%>/ProductServlet?action=selectOnePD&pid=${pd.p_id}">${pd.p_name }</a></p>
	                                <p class="text-truncate m-0">價格:${pd.p_price }</p>
	                                
	                            </div>
	                         </c:if>
                        	</c:forEach>
                            
                        </div>
                    </div>
                </div>
                <!-- 競標商品 -->
                <div class="col-6 ps-5 pe-0">
                    <div class="row m-0">
                         <!--標題-->
                        <div class="col-12 p-0">
                            <div class="row justify-content-between m-0 align-items-center"
                                style="border-radius:10px 10px 0px 0px; background-color:  #d3d3d3; border: 1px solid rgb(201, 197, 197);">
                                <div class="col-10">
                                    <i class="bi bi-lightning-charge-fill"
                                        style="font-size: 2rem; color: rgb(247, 244, 104);"></i>即將結標
                                </div>
                                <div class="col"><a href="<%=request.getContextPath()%>/front-end/BidProduct/listAllBid.jsp"><button type="button"
                                        class="btn btn-outline-secondary p-1">看更多></button></a>
                                </div>
                            </div>
                        </div>
                        <!--商品列-->
                        <div class="col-12 p-0">
                            <div class="row m-0 justify-content-around border-top-0"
                                style="border: 1px solid rgb(201, 197, 197); border-radius:0px 0px 10px 10px;">
                                
                                <c:forEach var="bidVO" begin="1" end="2" items="${bps.all}">
                                 <c:if test="${bidVO.bp_sta == 1}">
	                                <div class="col-4 ">
	                                    <div class="col-12 p-3">
	                                    	<c:if test="${imgSvc.check(bidVO.bp_id)}">
					                            <img src="data:image/gif;base64, ${imgSvc.get(bidVO.bp_id)}" class="card-img-top" alt="..." style="height:150px">
					                        </c:if>
					                        <c:if test="${imgSvc.check(bidVO.bp_id) == false}">
					                            <img src="data:image/gif;base64, ${imgSvc.showDefault()}" class="card-img-top" alt="..." style="height:150px">
					                        </c:if>
	                                    
	                                        <p class="fs-5 m-0 text-truncate"><a href="<%=request.getContextPath()%>/bid.do?action=getOne_For_Display&bp_id=${bidVO.bp_id}">${bidVO.bp_name}</a></p>
	                                        <div class="etime" id="etime${bidVO.bp_id }"><p class="text-truncate m-0">${bidVO.bp_etime }</p></div>
	                                        <p class="fs-6 m-0 text-truncate">目前最高價:${bidVO.bp_tprice}</p>
	                                    </div>
	                                </div>
	                                </c:if>
                                </c:forEach>
                                
                            </div>
                        </div>
                        <!-- 預購商品 -->
                         <!--標題-->
                        <div class="col-12 p-0 mt-2">
                            <div class="row justify-content-between m-0 align-items-center"
                                style="border-radius:10px 10px 0px 0px; background-color: #d3d3d3; border: 1px solid rgb(201, 197, 197);">
                                <div class="col-10">
                                    <i class="bi bi-lightning-charge-fill"
                                        style="font-size: 2rem; color: rgb(247, 244, 104);"></i>熱門預購
                                </div>
                                <div class="col"><a href="<%=request.getContextPath()%>/front-end/PreProduct/listAllPrePd.jsp"><button type="button"
                                        class="btn btn-outline-secondary p-1">看更多></button></a>
                                </div>
                            </div>
                        </div>
                        <!--商品列-->
                        <div class="col-12 p-0">
                            <div class="row m-0 justify-content-around p-3 border-top-0"
                                style="border: 1px solid rgb(201, 197, 197); border-radius:0px 0px 10px 10px;">
                                
                                
                                <c:forEach var="pdVO" items="${pds.all}" begin="1" end="4">
                                
                                
                                <div class="col-3">
                                    <div class="col-12 p-1">
                                    
                                    
                                    
                                        <c:if test= "${img.check(pdVO.pd_id)}" >
				                        	<img src="data:image/gif;base64, ${img.get(pdVO.pd_id)}" class="card-img-top" alt="..." style="height:120px" >
				                        </c:if>
				                       	<c:if test="${img.check(pdVO.pd_id) == false}">
				                            <img src="data:image/gif;base64, ${img.showDefaultImg()}" class="card-img-top" alt="..." style="height:120px">
				                        </c:if>
                                        <p class="fs-5 m-0 text-truncate"><a href="<%=request.getContextPath()%>/PdSelfServlet?action=selectOnePD&pd_id=${pdVO.pd_id}">${pdVO.pd_name}</a></p>
                                        <p class="text-truncate m-0">售價 :${pdVO.pd_price}</p>
                                        <p class="fs-6 m-0 text-truncate">成團數量 :${pdVO.pd_no}</p>
                                        
                                        
                                        
                                    </div>
                                </div>
                                
                                
                                </c:forEach>
                                
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 討論區 -->
        <div class="container">
            <hr class="featurette-divider mt-5 mb-3 ">
            <div class="row m-0 p-5 align-items-center">
                <div class="col text-center">
                    <img src="<%=request.getContextPath()%>/Img/logo.png" alt="" class="p-4">
                    <p class="fs-2 mb-0 mt-3">討論區</p>
                </div>
                <div class="col text-center m-0">
                    <p class="fs-3 mb-0">分享你的心得</p>
                    <p><a href="<%=request.getContextPath()%>/front-end/index.jsp"><button type="button" class="btn btn-outline-success">進入討論區</button></a></p>
                </div>
            </div>
            <hr class="featurette-divider mt-3 mb-3">
            <!-- 經營理念 -->
            <div class="row m-0 p-3 align-items-center">
                <div class="col-7 text-center ">
                    <p class="fs-1">平台經營理念</p>
                    <P>希望您可以放心的在此購買</P>
                    <P>這將是我們最大的期望</P>
                </div>
                <div class="col">
                    <img src="<%=request.getContextPath()%>/Img/logo2.png" class="img-fluid p-4" height="70%" width="70%">
                </div>
            </div>
        </div>
    </div>
    
    
    
    
    
    
    
    
    
    
<!-- 尾巴 -->
    <div class="container-fluid justify-content-center p-0 mt-3" style="background-color: #d3d3d3;
                    border-radius: 10px 10px 0px 0px;">
        <div class="row m-0 p-0">
            <div class="col-4 p-2 ms-5 mt-4 ">
                <div class="row justify-content-end align-items-end ms-5 ">
                    <div class="col-6 p-0 ms-1">
                        <img src="<%=request.getContextPath()%>/Img/logo1.png" class="img-fluid">
                    </div>
                    <div class="col p-0  translate-middle-x">
                        <img src="<%=request.getContextPath()%>/Img/logo.png" class="img-fluid">
                    </div>
                </div>
            </div>
            <div class="col-2 p-2 mt-3">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent  p-1">關於天堂鳥</li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"><small>加入我們</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"><small>天堂鳥條款</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"><small>天堂鳥商城</small></li>
                  </ul>
            </div>
            <div class="col-2 p-2 ms-5" style="margin-top:16px;">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent p-1">付款</li>
                    <li class="list-group-item  bg-transparent p-1 ps-2">
                        <img src="<%=request.getContextPath()%>/Img/VISA.png" width="30%">
                        <img src="<%=request.getContextPath()%>/Img/M2.png" width="30%" class="ms-1">
                        <img src="<%=request.getContextPath()%>/Img/M1.png" width="30%" class="ms-1">
                    </li>
                    <li class="list-group-item  bg-transparent p-1" >合作物流</li>
                    <li class="list-group-item  bg-transparent p-0 ps-2"><img src="<%=request.getContextPath()%>/Img/L1.png" width="100%"></li>
                  </ul>
            </div>
            <div class="col-2 p-2 ms-5 mt-3">
                <ul class="list-group list-group-flush bg-transparent">
                    <li class="list-group-item  bg-transparent  p-1" >客服中心</li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2"><small>聯絡客服</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2" ><small>幫助中心</small></li>
                    <li class="list-group-item  bg-transparent  p-1 ps-2" ><small>賣場Q&A</small></li>
                  </ul>
            </div>
        </div>
    </div>
<!--     尾巴結尾 -->


	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>	
<script src="${pageContext.request.contextPath}/JS/Front-end-js/Birtindex.js"></script>
<script src="/CFA101G4/JS/Forum-js/GetoneArticle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<c:if test="${memberVO.mem_sta == 0}">
<script>swal("此帳號已被停權或未認證")</script>
</c:if>

</body>
</html>