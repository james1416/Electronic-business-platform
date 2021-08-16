<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.bidproduct.model.BidProductVO"%>
<%@page import="com.bidproduct.model.BidProductService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.bidproduct.model.*"%>
<jsp:useBean id="bpcSvc" scope="page" class="com.bpclassification.model.BpClassificationService" />
<jsp:useBean id="imgSvc" scope="page" class="com.bpimage.model.BpImageService" />

<%
	BidProductService bidSvc = new BidProductService();
	List<BidProductVO> list = bidSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>

<meta charset="UTF-8">
<title>listallbid</title>


<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<style>
p.etime{
color:red;
}
</style>
<body>
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

    <!-- 網頁中間 -->

    <nav class="container-fluid">
        <div class="row mt-3 justify-content-center" id=i1>
                    <!--             商品上方的篩選欄位結尾 -->
					<c:forEach var="bidVO" items="${list}">
                    <!-- 	每個商品起始頭 (一個欄) -->
                    <div class="card" style="width: 18rem; margin-left: 5px ; margin-bottom: 5px;">
                         <c:if test="${imgSvc.check(bidVO.bp_id)}">
                            <img src="data:image/gif;base64, ${imgSvc.get(bidVO.bp_id)}" class="card-img-top" alt="...">
                        </c:if>
                        <c:if test="${imgSvc.check(bidVO.bp_id) == false}">
                            <img src="data:image/gif;base64, ${imgSvc.showDefault()}" class="card-img-top" alt="...">
                        </c:if>

                        <div class="card-body">
                            <!-- 放商品名稱 (點擊後可以導到詳細商品葉面) -->
                            <h5 class="card-title"><a href="/CFA101G4/bid.do?action=getOne_For_Display&bp_id=${bidVO.bp_id}">${bidVO.bp_name}</a></h5>
                            <!-- 放介紹 -->
                            <p class="card-text">${bidVO.bp_desc}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <!-- 放單價 -->
                            <!-- 若要改大小 -->
							<!-- 商品顯示資訊 -->
                            <li class="list-group-item"><i class="bi bi-currency-exchange"></i>目前最高價:${bidVO.bp_tprice}</li>
							<!--(倒數計時器) 時間 -->
                           <c:if test="${bidVO.bp_sta == 0}"> <li class="list-group-item"><i class="bi bi-alarm"></i>即將開始:<p class="etime" id="etime${bidVO.bp_id}">${bidVO.bp_stime}</p></li></c:if>
                           <c:if test="${bidVO.bp_sta == 1}"> <li class="list-group-item"><i class="bi bi-alarm"></i>競標剩餘時間:<p class="etime" id="etime${bidVO.bp_id}">${bidVO.bp_etime}</p></li></c:if>
                           <c:if test="${bidVO.bp_sta == 2}"> <li class="list-group-item"><i class="bi bi-alarm"></i><font color="#FF0000">競標時間已結束</font></li></c:if>
                            <!--時間 -->
							<!--狀態 -->
                           <c:if test="${bidVO.bp_sta == 0}"> <li class="list-group-item"><i class="bi bi-clipboard"></i>競標狀態:<font color="#0000CC">競標尚未開始</font></li></c:if>
                           <c:if test="${bidVO.bp_sta == 1}"> <li class="list-group-item"><i class="bi bi-clipboard"></i>競標狀態:<font color="#33FF33">競標進行中</font></li></c:if>
                           <c:if test="${bidVO.bp_sta == 2}"> <li class="list-group-item"><i class="bi bi-clipboard"></i>競標狀態:<font color="#FF0000">競標結束</font></li></c:if>
                           <!--狀態結束 -->
                        </ul>
                        <div class="card-body">
                            <!-- 放追蹤按鈕 -->
                           
<!--                             <button type="button" class="btn btn-outline-secondary"> -->
<!--                                 <i class="bi bi-suit-heart">追蹤</i> -->
<!--                             </button> -->
                        </div>
                    </div>
                    <!-- 每個商品的結尾 (一個欄) -->
					</c:forEach>	
                    
                    

                </div>

    </nav>
   
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
    
    
    
    
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/bid_order/listall.js"></script>
    </body>
</html>