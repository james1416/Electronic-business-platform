<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pd.model.*"%>
<%
PdVO pdVO = (PdVO) request.getAttribute("PdVO");
Integer number = (Integer) request.getAttribute("NUMBER");
Integer price = pdVO.getPd_price();
Integer total = number * price;
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>預購商品付款頁面</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<!-- Include a polyfill for ES6 Promises (optional) for IE11 and Android browser -->
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

</head>
<body>

	<jsp:useBean id="pis" scope="page" class="com.pi.model.PiService" />

	<jsp:useBean id="cgs" scope="page" class="com.pc.model.PcService" />

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
	
	



	<!-- 訂單內容的整個起始頭 -->
	<div class="container">

		<form onSubmit="{if(!allCheck){return false} ;}"
			action="<%=request.getContextPath()%>/PoServlet" method="post"
			style="margin: 0px">
			<ul class="list-group list-group-flush mt-3 mb-3">
				<!-- 一個訂單的區塊 -->


				<div class="popover-header">
					<a class="navbar-brand">商品 ${PdVO.pd_name} 的訂單明細</a>
					<button type="button" class="btn btn-secondary btn-sm mb-1"
						data-bs-toggle="collapse" data-bs-target="#showmore${id}"
						aria-expanded="false" aria-controls="collapseExample">收起資訊</button>
					<!--商品陳列開頭-->
					<div class="collapse show" id="showmore${id}">


						<li class="list-group-item w-100" id="showbody">
							<div class="row" style="margin-bottom: 10px;">
								<div class="col-5 align-self-center" style="text-align: center;">
									<div class="card mb-3 "
										style="height: 100px; border-style: none;">
										<div class="row g-0">
											<div class="col-md-4">
												<c:if test="${pis.check(PdVO.pd_id) }">
													<img class="img-fluid rounded-start"
														src="data:image/gif;base64, ${pis.get(PdVO.pd_id)}">
												</c:if>
												<c:if test="${pis.check(PdVO.pd_id) == false}">
													<img class="img-fluid rounded-start"
														src="data:image/gif;base64, ${pis.showDefaultImg()}">
												</c:if>
											</div>
											<div class="col-md-8" style="height: 100px;">
												<div class="card-body">
													<h5 class="card-title">${PdVO.pd_name}</h5>
													<p class="card-text">${PdVO.pd_desc}</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col align-self-center" style="text-align: center;">
									<div id="PDprice">價格 ${PdVO.pd_price}</div>
								</div>
								<div class="col-2 align-self-center" style="text-align: center;">
									數量
									<%=number%>
								</div>
								<div class="col align-self-center" style="text-align: center;">
									<div id="PDtotal">
										總金額
										<%=total%>
									</div>
								</div>
							</div>
						</li>




						<!--                   商品陳列結尾   	        	-->
						<li class="list-group-item">
							<div class="row" style="margin-bottom: 10px;">
								<div class="col-2" style="text-align: center;">
									<select class="form-select form-select-lg m-0"
										aria-label=".form-select-lg example" id="do" name="do">
										<option value="">請選擇物流</option>
										<option value="0">宅配</option>
									</select>
								</div>
								<div class="col-2 align-self-center">
									<select class="form-select form-select-lg m-0"
										aria-label=".form-select-lg example" name="city" id="city">
									</select>
								</div>
								<div class="col-2 align-self-center">
									<select class="form-select form-select-lg m-0"
										aria-label=".form-select-lg example" name="dist" id="dist">
									</select>
								</div>
								<div class="col-3 align-self-center">
									<input class="form-control form-control-lg" type="text"
										placeholder="請填入地址資訊" name="addr" id="addr">
								</div>
								<div class="col-1 align-self-center">
									<p class="fs-4 m-0">總金額</p>
								</div>
								<div class="col-1 align-self-center">


									<p class="fs-2 m-0"><%=total%></p>


								</div>
							</div>
							<div class="row" style="margin-bottom: 10px;">

								<div class="col-3" style="text-align: center;">
									<input class="form-control form-control-lg" type="text"
										placeholder="請輸入收件人電話" id="PO_TEL" name="PO_TEL">
								</div>
								<div class="col-3" style="text-align: center;">
									<input class="form-control form-control-lg" type="text"
										placeholder="請輸入收件人姓名" id="PO_BNAME" name="PO_BNAME">
								</div>
							</div>
						</li>
					</div>
				</div>

				<!-- 一個訂單的區塊 -->


			</ul>

			<!-- 訂單內容的整個結束點 -->




			<!-- 付款方式起始頭 -->
			<div class="container">

				<div class="row justify-content-end mb-3">
					<div class="card w-50 p-0">
						<h5 class="card-header">付款方式</h5>
						<div class="card-body">
							<div class="row">
								<div class="col">
									<select class="form-select form-select-lg m-0"
										aria-label=".form-select-lg example" id="PO_PAY" name="PO_PAY">
										<option value="">請選擇付款方式</option>
										<option value="1">錢包付款</option>
									</select> <input type="hidden" id="memele" value="${memberVO.mem_ele}">
									<input type="hidden" id="total" value="<%=total%>">
								</div>
								<div class="col">
									<p class="h3 text-end">總價格 <%=total%></p>
								</div>
							</div>


							<div class="row">
								<div class="col text-end">
									<input type="hidden" id ="PD_ID"  name="PD_ID" value="<%=pdVO.getPd_id()%>">
									<input type="hidden" id ="PD_SMEMID"  name="PD_SMEMID" value="<%=pdVO.getPd_smemid()%>"> 
									<input type="hidden" id ="PO_QTY" name="PO_QTY" value="<%=number%>">
									<input type="hidden" id="PO_PRICE" name="PO_PRICE" value="<%=total%>">
									<button type="button" value="add" name="action" id="add" style="display:none">送出訂單</button>
                        			<button type="button" id="submit" class="btn btn-secondary btn-lg">送出訂單</button>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		
	</div>
	<!-- 付款方式結束點 -->

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


	<script type="text/javascript"
		src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="/CFA101G4/JS/Front-end-js/Preproduct-js/taiwan_address_auto_change.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/JS/Front-end-js/Preproduct-js/CheckPreOrder.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
	<script>
		AddressSeleclList.Initialize("city", "dist");
	</script>
</body>
</html>