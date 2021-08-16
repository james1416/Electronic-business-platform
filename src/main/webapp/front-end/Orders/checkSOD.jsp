<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<jsp:useBean id="ps" scope="page" class="com.product.model.ProductService" />  
<jsp:useBean id="cls" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="os" scope="page" class="com.orders.model.OrdersService" />
<jsp:useBean id="ols" scope="page" class="com.ordersList.model.OrdersListService" />
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
                        	${ns.getcount(memberVO.mem_id) == 0 ? "" : ns.getcount(memberVO.mem_id) }
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



 <!-- 網格系統開頭 -->
    <div>
        <div class="container-fluid">
            <!-- row開頭 -->
            <div class="row">

                <!-- 賣家中心左側 -->
                <div class="col-2">
                    <div class="flex-shrink-0 p-3 bg-white position-sticky top-0">
                        <a href="<%=request.getContextPath()%>/front-end/Member/sellCenter.jsp"
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
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/ProductServlet?action=getSelf">商品總覽</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Product/addPD.jsp">新增商品</a></li>
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
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/OrdersServlet?action=findSOD">查看待確認訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectNeedSMOD.jsp">查看待出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectCancelOD.jsp">查看已取消訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectAlreadyShipped.jsp">查看已出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectDoneOD.jsp">查看已完成訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectReturnOD.jsp">查看需退貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/Orders/selectAlreadyFunded.jsp">查看已撥款訂單</a></li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                 <!-- 賣家中心左側 -->



                <!-- 訂單的開頭預設起始 -->
                <div class="col">
                    <div class="row justify-content-center mt-2">
                        <div class="col text-end ">
                        	<button type="button" value="allupdataOD" id="allAccept" name="action" class="btn btn-primary m-0">一鍵接受訂單</button>
                            <button type="button" value="allupdataOD" id="allCancel" name="action" class="btn btn-danger m-0">一鍵取消訂單</button>
                        </div>
                    </div>

                    <div class="row justify-content-center p-3 mt-0">
                        <div class="col-2 text-center popover-header">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="checkbox">
                                <label class="form-check-label" for="checkbox">
                                   	 訂單編號
                                </label>
                            </div>
                        </div>
                        <div class="col-1 text-center popover-header">
                           	 買家編號
                        </div>
                        <div class="col-2 text-center popover-header">
                            	下單時間
                        </div>
                        <div class="col-2 text-center popover-header">
                            	訂單總額
                        </div>
                        <div class="col-3 text-center popover-header">
                           	 操作
                        </div>
                        <div class="col-2 text-center popover-header">
                            更多
                        </div>
                    </div>
                    <!-- 訂單的開頭預設結束點 -->
                    <!-- 一張訂單的起始點 -->
                    <c:forEach var="od" items="${os.mySOrders(memberVO.mem_id) }">
                    <c:if test="${od.od_sta == 0 }">
                    <div class="row justify-content-center p-3">
                        <div class="col-2 text-center align-self-center">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="odid" value="${od.od_id }" id="odidcheckbox">
                                <label class="form-check-label" for="flexCheckDefault">
                                    ${od.od_id }
                                </label>
                            </div>
                        </div>
                        <div class="col-1 text-center align-self-center">
                            ${od.od_bmemid }
                        </div>
                        <div class="col-2 text-center align-self-center">
                            ${od.od_date }
                        </div>
                        <div class="col-2 text-center align-self-center">
                            ${od.od_price }
                        </div>
                        <div class="col-3 text-center align-self-center">
                        
                            <button type="button" id="accept" value="${od.od_id }" class="btn btn-primary m-0">接受訂單</button>
                            
                            <button type="button" id="cancel" value="${od.od_id }" class="btn btn-danger m-0">取消訂單</button>
                            
                        </div>
                        <div class="col-2 text-center align-self-center">
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                            data-bs-target="#bmemid${od.od_id }">查看訂單詳情</button>
                            <!-- Modal -->
                            <div class="modal fade" id="bmemid${od.od_id }" data-bs-backdrop="static"
                                data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
                                aria-hidden="true">
                                <div class="modal-dialog modal-xl">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="staticBackdropLabel">買家 ${od.od_bmemid } 的訂單明細</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="container-fluid">
                                                
                                                <div class="row">
                                                    <div class="col-2 text-center popover-header">
                                                       	 商品編號
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	商品縮圖
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	商品名稱
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                       	 單價
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	下單數量
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                      	  總額
                                                    </div>
                                                </div>
                                                <!-- 訂單內明細商品的起始點 -->
                                                <c:forEach var="odls" items="${ols.getall(od.od_id)}">
                                                <div class="row">
                                                    <div class="col-2 text-cente align-self-center">
                                                        ${odls.ol_pid }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        <c:if test="${pis.check(odls.ol_pid) }">
                            								<img src="data:image/gif;base64, ${pis.get(odls.ol_pid)}" class="img-thumbnail" alt="...">
                        								</c:if>
                        								<c:if test="${pis.check(odls.ol_pid) == false}">
                            								<img src="data:image/gif;base64, ${pis.showDefault()}" class="img-thumbnail" alt="...">
                        								</c:if>
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                       	${ps.findPD(odls.ol_pid).p_name }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${odls.ol_price }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${odls.ol_pq }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${odls.ol_pq * odls.ol_price }
                                                    </div>
                                                </div>
                                                </c:forEach>
                                                <!-- 訂單內明細商品的結束點 -->
                                                <div class="row">
                                                	<div class="col align-self-center">
                                                        <p class="h5">配送地址</p>
                                                    </div>
                                                    <div class="col align-self-center">
                                                        <p class="h4">${od.od_shipinfo }</p>
                                                    </div>
                                                    <div class="col text-end">
                                                      	  訂單總金額
                                                        <p class="h2">${od.od_price }</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" id="modelaccept" value="${od.od_id }" class="btn btn-primary">接受訂單</button>
                                            <button type="button" id="modelcancel" value="${od.od_id }"  class="btn btn-danger">取消訂單</button>
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">關閉</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 彈跳視窗結束點 -->
                        </div>
                    </div>
                    </c:if>
                    </c:forEach>
                     <!-- 一張訂單的結束點 -->
                </div>

            </div>

        </div>

        
    </div>
<!-- 網格系統結尾 -->

















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



<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/Orders-js/CheckSOD.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>