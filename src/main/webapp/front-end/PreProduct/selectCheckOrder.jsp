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
<title>達標訂單確認管理</title>
</head>



<!-- 通知的service -->
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
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/PreProduct/selectSelfPrePd.jsp">我的預購賣場</a></li>
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
                        <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Member/checkPRordersAll.jsp">購買訂單</a></li>
<%--                         <li class="list-group-item bg-transparent"><a href="<%=request.getContextPath()%>/front-end/Tracking/selectTK.jsp">追蹤清單</a></li> --%>
                    </ul>
                </div>
            </div>
        </nav>
        <!--分隔線 --><hr class="featurette-divider mt-4 mb-4 ">
<!-- ---------------------- 以上為右邊 -->

</header>




<body>
<jsp:useBean id="pis" scope="page" class="com.pi.model.PiService" /> 
<jsp:useBean id="ps" scope="page" class="com.pd.model.PdService" />  
<jsp:useBean id="os" scope="page" class="com.po.model.PoService" />
<jsp:useBean id="cls" scope="page" class="com.pc.model.PcService"/>
<!-- 網格系統開頭 -->
    <div>
        <div class="container-fluid">
            <!-- row開頭 -->
            <div class="row">

                <!-- 賣家中心左側 -->
                <div class="col-2">
                    <div class="flex-shrink-0 p-3 bg-white position-sticky top-0">
                        <a href="/CFA101G4/front-end/Member/sellCenter.jsp"
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
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/front-end/PreProduct/selectSelfPrePd.jsp">商品總覽</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/PreProduct/addPrePd.jsp">新增商品</a></li>
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
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/PoServlet?action=getSellOrder">查看待確認訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectCheckShip.jsp">查看待出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectCancelOrder.jsp">查看已取消訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectAlreadyShip.jsp">查看已出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectFinishOrder.jsp">查看已完成訂單</a></li>
                                   		<li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectReturnOrder.jsp">查看被退貨訂單</a></li>
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
                </div>
                 <!-- 賣家中心左側 -->



                <!-- 訂單的開頭預設起始 -->
                <div class="col">
                    <div class="row justify-content-center mt-2">
                        <div class="col text-end ">
                        	<button type="button" value="allupdataOD" id="allAccept" name="action" class="btn btn-primary m-0">一鍵接受訂單</button>
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
                    
					<!--狀態為預購達標等待確認的訂單 -->
                    <c:forEach var="od" items="${os.allSorder(memberVO.mem_id) }">
                    <c:if test="${od.po_sta == 2 }">
                    <div class="row justify-content-center p-3">
                        <div class="col-2 text-center align-self-center">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="odid" value="${od.po_id}" id="odidcheckbox">
                                <label class="form-check-label" for="flexCheckDefault">
                                    ${od.po_id }
                                </label>
                            </div>
                        </div>
                        <div class="col-1 text-center align-self-center">
                            ${od.po_bmemid }
                        </div>
                        <div class="col-2 text-center align-self-center">
                            ${od.po_date }
                        </div>
                        <div class="col-2 text-center align-self-center">
                            ${od.po_price }
                        </div>
                        <div class="col-3 text-center align-self-center">
                        
                            <button type="button" id="accept" value="${od.po_id}" class="btn btn-primary m-0">接受訂單</button>
                            
                            <button type="button" id="cancel" value="${od.po_id}" class="btn btn-danger m-0">取消訂單</button>
                            
                        </div>
                        <div class="col-2 text-center align-self-center">
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                            data-bs-target="#bmemid${od.po_id}">查看訂單詳情</button>
                            <!-- Modal -->
                            
                            <div class="modal fade" id="bmemid${od.po_id }" data-bs-backdrop="static"
                                data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
                                aria-hidden="true">
                                <div class="modal-dialog modal-xl">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="staticBackdropLabel">買家 ${od.po_bmemid } 的訂單明細</h5>
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
                                                        商品名稱
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                       	買家名稱
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        買家電話                                                        	
                                                    </div>
                                                     <div class="col-2 text-center popover-header">
                                                      	數量
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                      	金額
                                                    </div>
                                                </div>
                                                <!-- 訂單內明細商品的起始點 -->
                                                
                                                <div class="row">
                                                    <div class="col-2 text-cente align-self-center">
                                                        ${od.po_id }
                                                    </div>

                                                    <div class="col-2 text-center align-self-center">
                                                       	${ps.getOnePd(od.po_pdid).pd_name }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${od.po_bname }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${od.po_tel }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${od.po_qty }
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                        ${od.po_price }
                                                    </div>
                                                </div>
                                               
                                                <!-- 訂單內明細商品的結束點 -->
                                                <div class="row">
                                                	<div class="col align-self-center">
                                                        <p class="h5">配送地址</p>
                                                    </div>
                                                    <div class="col align-self-center">
                                                        <p class="h4">${od.po_home}</p>
                                                    </div>
                                                    <div class="col text-end">
                                                      	  訂單總金額
                                                        <p class="h2">${od.po_price}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
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


<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/Preproduct-js/selectCheckOrder.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>