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
<jsp:useBean id="cs" scope="page" class="com.category.model.CategoryService" />  
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
        <div class="container-fluid" id="body">
            <!-- row開頭 -->
            <div class="row">

                <!-- 賣家中心左側 -->
                <div class="col-2">
                    <div class="flex-shrink-0 p-3 bg-white position-sticky top-0" style="width: 200px;">
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
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="false">
                                    營收相關
                                </button>
                                <div class="collapse" id="orders-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0">查看營收</li>
                                        <li class="list-group-item border-0">A second item</li>
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
                                        <li class="list-group-item border-0">A second item</li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- 賣家中心左側 -->
                </div>

                <div class="col">
                    <div class="row">
                        <div class="col-12 text-center">
                            <div class="row" id="imgbody">
                                <!-- 放預覽圖的位置開頭 -->
                                <!-- 放預覽圖的位置結尾 -->
                            </div>
                        </div>
                    </div>
                    <div class="row m-2 border border-$gray-500 text-center" style="border-radius: 50px;">
                        <div class="col-7 text-end">
                            <p class="fs-1 p-0 m-0">商品新增</p>
                        </div>
                        <div class="col-5 mt-2">
                                <input type="file" id="i1" name="piimage" multiple="multiple" style="display: none;">
                                <input type="button" id="upimg" value="新增圖片" class="btn btn-secondary btn-lg m-0">
                        </div>
                        <div class="col-12">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品名稱</span>
                                <input type="text" name="pname" id="pname" class="form-control" aria-label="Sizing example input"
                                    aria-describedby="inputGroup-sizing-lg">
                            </div>
                        </div>
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品分類</span>
                                <select name="pcgid" id="pcgid" class="form-select form-select-lg" aria-label=".form-select-lg example">
                                    <option value="" selected>請選擇分類</option>
                                    <c:forEach var="cs" items="${cs.all }">
                                    <option value="${cs.cg_id}">${cs.cg_name}
                                    </c:forEach>
                                </select>
                            </div>
                            
                        </div>
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品價格</span>
                                <input type="text" name="pprice" id="pprice" class="form-control" aria-label="Sizing example input"
                                    aria-describedby="inputGroup-sizing-lg">
                            </div>
                        </div>
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span  class="input-group-text" id="inputGroup-sizing-lg">商品數量</span>
                                <input type="text" name="pstock" id="pstock" class="form-control" aria-label="Sizing example input"
                                    aria-describedby="inputGroup-sizing-lg">
                            </div>
                        </div>
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品介紹</span>
                                <textarea class="form-control" name="pdesc" id="pdesc" aria-label="With textarea"></textarea>
                            </div>
                        </div>
                        <div class="col-12 mt-3 mb-3">
                            <button value="add" name="action" id="add" style="display:none">新增</button>
                            <button type="button" id="submit" class="btn btn-secondary btn-lg">新增</button>
                        </div>

                    </div>
                </div>
                
            </div>
            
        </div>
        <!-- 網格系統結尾 -->
        
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




<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/Product-js/addPD.js"></script>
</body>
</html>