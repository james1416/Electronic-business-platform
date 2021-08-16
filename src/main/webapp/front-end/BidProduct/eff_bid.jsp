<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



<jsp:useBean id="bpcSvc" scope="page" class="com.bpclassification.model.BpClassificationService" />

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
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/BidProduct/get_my_bidproduct.jsp">競標賣家中心</a></li>
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
                        	${ns.getcount(memberVO.mem_id) == 0 ? "" : ns.getcount(memberVO.mem_id) }
                        </c:if>
                        </div>
                        </li>
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/bid.do?action=get_win_bid">購買訂單</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        分隔線<hr class="featurette-divider mt-4 mb-4 ">
<!-- ---------------------- 以上為右邊 -->
<!-- 以下為下方圖片跟引所欄位 -->
      <div class="row mt-4 pb-3">
        <div class="col-1 align-self-center w-25">
          <img src="<%=request.getContextPath()%>/Img/Enlogo.png" class="img-thumbnail border-0" alt="...">
        </div>
        <div class="col-7 p-0 align-self-center">
        <div class="row">
        <div class="input-group">
<form action="<%=request.getContextPath()%>/ProductServlet" method="post" style="display : flex ;width: 100%;">
        <div class="col-3">
			<select name="pcgid" id="pcgid" class="form-select" aria-label="Default select example" style="z-index: 10;">
				<option value="0" selected>選擇分類</option>
				<c:forEach var="cs" items="${cs.all}">
				  <option value="${cs.cg_id }">${cs.cg_name }</option>
				 </c:forEach>
			</select>
		</div>
		<div class="col-9">
		        <div class="row">
					<div class="col-9 position-relative" style="padding-right: 0px;">
						<input type="text" name="pname" id="searchinput" class="form-control" aria-label="Text input with dropdown button">
					</div>
					<div class="col-3 p-0">
						<button class="btn btn-outline-secondary" name="action" value="search">搜尋</button>
					</div>
				</div>
				
		</div>
</form>
	          </div>
	        </div>
        </div>
        
      </div>
  </div>
</header>




    <!-- 網格系統開頭 -->
    <div>
        <div class="container-fluid">
            <!-- row開頭 -->
            <div class="row">

                <!-- 賣家中心左側 -->
                <div class="col-2">
                    <div class="flex-shrink-0 p-3 bg-white position-sticky top-0">
                        <a href="/CFA101G4/front-end/BidProduct/get_my_bidproduct.jsp"
                            class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
                            <svg class="bi me-2" width="30" height="24">
                                <use xlink:href="#bootstrap"></use>
                            </svg>
                            <span class="fs-5 fw-semibold">賣家中心</span>
                        </a>
                        <ul class="list-unstyled ps-0">
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#home-collapse" aria-expanded="false">
                                    商品管理
                                </button>
                                <div class="collapse" id="home-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/bid.do?action=getmybid$bp">競標商品總覽</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/addBid.jsp">新增競標商品</a></li>
                                        <li class="list-group-item border-0">查看遭檢舉商品</li>
                                    </ul>
                                </div>
                            </li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#dashboard-collapse"
                                    aria-expanded="false">
                                    訂單管理
                                </button>
                                <div class="collapse" id="dashboard-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/OrdersServlet?action=findSOD">查看待確認競標訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/Orders/selectNeedSMOD.jsp">查看待出貨競標訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/Orders/selectCancelOD.jsp">查看已取消競標訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/Orders/selectAlreadyShipped.jsp">查看已出貨競標訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/Orders/selectDoneOD.jsp">查看已完成競標訂單</a></li>
                                    </ul>
                                </div>
                            </li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="false">
                                    營收相關
                                </button>
                                <div class="collapse" id="orders-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0">查看營收</li>
                                        <li class="list-group-item border-0">測試</li>
                                    </ul>
                                </div>
                            </li>
                            <li class="border-top my-3"></li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#account-collapse" aria-expanded="false">
                                    平台規則
                                </button>
                                <div class="collapse" id="account-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0">查看更多</li>
                                        <li class="list-group-item border-0">測試</li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- 賣家中心左側 -->
                </div>

                <div class="col" id="PDbody">
                    <div class="row justify-content-center mt-3 p-3">
                    <div class="col-1 text-center popover-header">
                            競標商品編號
                        </div>
                        <div class="col-1 text-center popover-header">
                            競標商品名稱
                        </div>
                        <div class="col-1 text-center popover-header">
                            競標結束時間
                        </div>
                        <div class="col-1 text-center popover-header">
                            商品起標價格
                        </div>
                        <div class="col-1 text-center popover-header">
                            商品出價增額
                        </div>
                        <div class="col-1 text-center popover-header">
                            買家會員編號
                        </div>
                        <div class="col-1 text-center popover-header">
                            競標成交金額
                        </div>
                        <div class="col-1 text-center popover-header">
                            商品狀態
                        </div>
                        <div class="col-2 text-center popover-header">
           	競標商品訂單狀態
                        </div>
                        <div class="col-1 text-center popover-header">
          	出貨
                        </div>
                        <div class="col-1 text-center popover-header">
          	取消競標訂單
                        </div>
                    </div>
                    
                    
				<div id="moremsg" class="row mt-3 mb-3">
					<div class="col-12 text-center">往下拉載入更多資料</div>
				</div>
                </div>
            </div>
        </div>
        <!-- 網格系統結尾 -->
    </div>













































<footer>
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
</footer>


<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/bid_order/effbid.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>