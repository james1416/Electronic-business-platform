<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pd.model.*"%>
<!DOCTYPE html>
<html lang="en">

<%
PdService pdSvc = new PdService();
List<PdVO> list = pdSvc.getall();
pageContext.setAttribute("list", list);
%>
	<jsp:useBean id="pd" scope="page" class="com.pd.model.PdService" />
	<jsp:useBean id="t" scope="page" class="com.pt.model.PtService" />
	<jsp:useBean id="img" scope="page" class="com.pi.model.PiService" />
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>預購商品首頁</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	
<!-- 	上方連結的CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

</head>
<body>



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
</div>
</header>









    <!-- 網頁中間 -->

    <nav class="container-fluid">
        <div class="row mt-3 justify-content-center" id=i1>
			<c:forEach var="pdVO" items="${pd.all}" >
				<c:if test="${pdVO.pd_sta != 3}">
                    <!-- 	每個商品起始頭 (一個欄) -->
                    <div class="card" style="width: 18rem; margin-left: 5px ; margin-bottom: 5px;">
                        <!-- 放圖片 (單張) -->
                         
                        <c:if test= "${img.check(pdVO.pd_id)}" >
                        	<img src="data:image/gif;base64, ${img.get(pdVO.pd_id)}" class="card-img-top" alt="..." style="height:230px" >
                        </c:if>
                       	<c:if test="${img.check(pdVO.pd_id) == false}">
                            <img src="data:image/gif;base64, ${img.showDefaultImg()}" class="card-img-top" alt="..." style="height:230px">
                        </c:if>
                        <div class="card-body">
                            <!-- 放商品名稱 (點擊後可以導到詳細商品葉面) -->
                            <h5 class="card-title"><a href="/CFA101G4/PdSelfServlet?action=selectOnePD&pd_id=${pdVO.pd_id}"">${pdVO.pd_name}</a></h5>
                            <!-- 放介紹 -->
                            <div class="carddesc"><p class="card-text">${pdVO.pd_desc}</p></div>
                        </div>
                        <ul class="list-group list-group-flush">
                            <!-- 放單價 -->
                            <!-- 若要改大小 -->

                            <li class="list-group-item">售價 :${pdVO.pd_price}</li>
                            <!-- 放成團數量 -->
                            <li class="list-group-item">成團數量 :${pdVO.pd_no}</li>
                        </ul>
<!--                         <div class="card-body"> -->
<!--                             放追蹤按鈕 -->

<!--                             <button type="button" class="btn btn-outline-secondary"> -->
<!--                                 <i class="bi bi-suit-heart">追蹤</i> -->
<!--                             </button> -->
<!--                         </div> -->
                    </div>
                    </c:if>
                </c:forEach>    
                    <!-- 每個商品的結尾 (一個欄) -->

                    
                    

                </div>

    </nav>
    
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



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="/CFA101G4/JS/Front-end-js/Preproduct-js/listAllPrePd.js"></script>
</body>
</html>