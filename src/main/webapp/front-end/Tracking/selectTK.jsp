<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

<title>Insert title here</title>
</head>
<body>
<c:if test = "${memberVO == null }">
	<c:redirect url="/front-end/login.jsp">
	</c:redirect>
</c:if>
<jsp:useBean id="ts" scope="page" class="com.tracking.model.TrackingService" />
<jsp:useBean id="ps" scope="page" class="com.product.model.ProductService" /> 
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<jsp:useBean id="cls" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="ns" scope="page" class="com.notice.model.NoticeService" />



<header>
  <div class="container-fluid border border-$gray-500">
  <!-- ---------------------- 上面為網格系統結尾 -->

		<nav class="navbar fixed-top  navbar-expand-lg navbar-light p-0" style="background-color: #d3d3d3;">
            <div class="container">
                <div class="col">
                    <ul class="list-group list-group-horizontal" style="font-size:smaller;">
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/FrontMain.jsp"><i class="bi bi-house-door"></i>回首頁</a></li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/index.jsp"><i class="bi bi-card-text"></i>討論區</a></li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Member/sellCenter.jsp">賣家中心</a></li>
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
                        </c:if>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Member/member_center.html">會員中心</a></li>
                        <li class="list-group-item bg-transparent" style="position: relative;"><a href="<%=request.getContextPath()%>/front-end/Member/checkAllNotices.html">通知</a>
                        <div style="position: absolute;top: 0px ;right: 10px;">
                        <c:if test="${memberVO == null }"></c:if>
                        <c:if test="${memberVO != null }">
                        	${ns.getcount(memberVO.mem_id) == null ? "" : ns.getcount(memberVO.mem_id) }
                        </c:if>
                        </div>
                        </li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Member/checkOrdersStatus.html">購買訂單</a></li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Tracking/selectTK.jsp">追蹤清單</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!--分隔線 --><hr class="featurette-divider mt-4 mb-4 ">
<!-- ---------------------- 以上為右邊 -->
	</div>
</header>















<!--  ------------購物車本體----------------- -->

	<form action="/CFA101G4/OrdersServlet" method="post">

        <div class="container" id="CLbody">
            <ul class="list-group list-group-flush">
            <li class="list-group mt-3">
            	<div class="col-12 text-center">
            	<p class="h3">追蹤清單</p>
            	</div>
             </li>
            <li class="list-group-item">
                    <div class="row" style="margin-bottom: 10px; ">
                        <div class="col-1 align-self-center"  style="text-align: center;"><div>商品編號</div></div>
                        <div class="col-5 align-self-center" style="text-align: center;">
                            <div class="card" style=" border-style: none;">
                                <div class="row">
                                  <div class="col-md-4 align-self-center">商品縮圖</div>
                                  <div class="col-md-8 align-self-center">
                                    <div class="card-body align-self-center">商品名稱/介紹</div>
                                  </div>
                                </div>
                              </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="PDprice">
                        	商品單價
                        	</div>
                        </div>
                        <div class="col-2 align-self-center" style="text-align: center;">
                            <div class="input-group">
                          	  加入追蹤日期
                            </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div>操作</div>
                        </div>	
                    </div>
                </li>
            <c:forEach var="ts" items="${ts.findSelf(memberVO.mem_id)}">
             	
                <li class="list-group-item">
                    <div class="row" style="margin-bottom: 10px; ">
                        <div class="col-1 align-self-center"  style="text-align: center;"><div id="clpid" >${ps.findPD(ts.tk_pid).p_id }<input type="hidden" name="pid" value="${ts.tk_pid}" id="clpid"></div></div>
                        <div class="col-5 align-self-center" style="text-align: center;">
                            <div class="card mb-3 " style="height:120px; border-style: none;">
                                <div class="row h-100 mt-2">
                                  <div class="col-md-4 h-100">
									<c:if test="${pis.check(ts.tk_pid) }">
									<img class="img-fluid rounded-start w-100 h-100" src="data:image/gif;base64, ${pis.get(ts.tk_pid)}">
									</c:if>
									<c:if test="${pis.check(ts.tk_pid) == false}">
									<img class="img-fluid rounded-start w-100 h-100" src="data:image/gif;base64, ${pis.showDefault()}">
									</c:if>
                                  </div>
                                  <div class="col-md-8" style="height:100px;">
                                    <div class="card-body">
                                      <h5 class="card-title"><a href="/CFA101G4/ProductServlet?action=selectOnePD&pid=${ts.tk_pid}">${ps.findPD(ts.tk_pid).p_name }</a></h5>
                                      <p class="card-text" >${ps.findPD(ts.tk_pid).p_desc }</p>
                                    </div>
                                  </div>
                                </div>
                              </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="PDprice">
                            	${ps.findPD(ts.tk_pid).p_price }
                        	</div>
                        </div>
                        <div class="col-2 align-self-center" style="text-align: center;">
                            <div class="input-group">
                            <fmt:formatDate value="${ts.tk_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="del">刪除</div>
                        </div>	
                    </div>
                </li>
                </c:forEach>
                <li style="list-style:none">
                </li>
            </ul>
        </div>
        </form>
<!--  ------------購物車本體----------------- -->




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




<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/header.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/Tracking-js/selectTK.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>