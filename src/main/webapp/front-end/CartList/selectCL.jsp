<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<jsp:useBean id="cls" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="ps" scope="page" class="com.product.model.ProductService" /> 
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
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
						<div class="col-12 position-absolute" id="searchbody" style="display:hidden;border: grey 1px solid ; z-index:20;background-color:white;width: 98%;border-radius: 5px;">
                    	</div>
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
        
        <div class="col-1 text-center" style="position:relative;" id="cart">
          
<!--           購物車圖示開頭  -->
          <c:if test="${memberVO ==null }">
          </c:if>
          <c:if test="${memberVO !=null }">
          <div id="cart-sum" style="border: 1px black solid; border-radius: 30px; width: 35px; height: 25px;background: black; color: white;font-size: 20px; position: absolute;
          top:5px ; right: 22px; line-height: 25px;">
          ${cls.getSum(memberVO.mem_id)}
          </div>
          </c:if>
<!--           購物車圖示結尾  -->
          
            <i class="bi bi-cart2 ju" style="font-size: 50px;"></i>
        </div>
      </div>
  </div>
</header>








<!--  ------------購物車本體----------------- -->

	<form action="<%=request.getContextPath()%>/OrdersServlet" method="post">

        <div class="container" id="CLbody">
            <ul class="list-group list-group-flush">
            <c:forEach var="cls" items="${cls.getall(memberVO.mem_id)}">
             	
                <li class="list-group-item">
                    <div class="row" style="margin-bottom: 10px; ">
                        <div class="col-1 align-self-center"  style="text-align: center;"><div id="clpid" >${cls.cl_pid}<input type="hidden" name="pid" value="${cls.cl_pid}" id="clpid"></div></div>
                        <div class="col-5 align-self-center" style="text-align: center;">
                            <div class="card mb-3 " style="height:120px; border-style: none;">
                                <div class="row h-100 mt-2">
                                  <div class="col-md-4 h-100">
									<c:if test="${pis.check(cls.cl_pid) }">
									<img class="img-fluid rounded-start w-100 h-100" src="data:image/gif;base64, ${pis.get(cls.cl_pid)}">
									</c:if>
									<c:if test="${pis.check(cls.cl_pid) == false}">
									<img class="img-fluid rounded-start w-100 h-100" src="data:image/gif;base64, ${pis.showDefault()}">
									</c:if>
                                  </div>
                                  <div class="col-md-8" style="height:100px;">
                                    <div class="card-body">
                                      <h5 class="card-title">${ps.findPD(cls.cl_pid).p_name }</h5>
                                      <div class="carddesc"><p class="card-text" >${ps.findPD(cls.cl_pid).p_desc }</p></div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="PDprice">
                        		<c:if test="${ps.findPD(cls.cl_pid).p_stock >= cls.cl_pq }">
                            	${ps.findPD(cls.cl_pid).p_price }
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock < cls.cl_pq && ps.findPD(cls.cl_pid).p_stock != 0 }">
                            	${ps.findPD(cls.cl_pid).p_price }
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock == 0 }">
							   	0
							   	</c:if>
                        	</div>
                        </div>
                        <div class="col-2 align-self-center" style="text-align: center;">
                            	<div class="input-group">
                            	<c:if test="${ps.findPD(cls.cl_pid).p_stock >= cls.cl_pq }">
                            	<input type="button" class="btn btn-outline-secondary" value="-" id="down" style="width: 40px ; height: 30px; line-height: 10px; ">
							  	<input type="text" id="clpq" name="clpq" class="form-control" aria-label="Text input with segmented dropdown button" style="width: 40px ; height: 30px; text-align: center;" readonly value="${cls.cl_pq }">
							   	<input type="button" value="+" id="up" class="btn btn-outline-secondary" style="width: 40px ; height: 30px; line-height: 10px; ">
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock < cls.cl_pq && ps.findPD(cls.cl_pid).p_stock != 0 }">
                            	<input type="button" class="btn btn-outline-secondary" value="-" id="down" style="width: 40px ; height: 30px; line-height: 10px; ">
							  	<input type="text" id="clpq" name="clpq" class="form-control" aria-label="Text input with segmented dropdown button" style="width: 40px ; height: 30px; text-align: center;" readonly value="1">
							   	<input type="button" value="+" id="up" class="btn btn-outline-secondary" style="width: 40px ; height: 30px; line-height: 10px; ">
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock == 0 }">
							   	<p class="h5" style="color:red">此商品已完售！</p>
							   	<input type="hidden" class="btn btn-outline-secondary" value="-" id="down" style="width: 40px ; height: 30px; line-height: 10px; ">
							    <input type="hidden" id="clpq" name="clpq" class="form-control" aria-label="Text input with segmented dropdown button" style="width: 40px ; height: 30px; text-align: center;" readonly value="0">
							   	<input type="hidden" value="+" id="up" class="btn btn-outline-secondary" style="width: 40px ; height: 30px; line-height: 10px; ">
							   	</c:if>
                            </div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="PDtotal">
                        		<c:if test="${ps.findPD(cls.cl_pid).p_stock >= cls.cl_pq }">
                        		${ps.findPD(cls.cl_pid).p_price * cls.cl_pq }
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock < cls.cl_pq && ps.findPD(cls.cl_pid).p_stock != 0 }">
                        		${ps.findPD(cls.cl_pid).p_price * 1 }
							   	</c:if>
							   	<c:if test="${ps.findPD(cls.cl_pid).p_stock == 0 }">
							   	0
							   	</c:if>
                        	</div>
                        </div>
                        <div class="col align-self-center" style="text-align: center;">
                        	<div id="del">刪除</div>
                        </div>	
                    </div>
                </li>
                </c:forEach>
                <li style="list-style:none">
                <div style="margin: 20px auto; margin-left: 90%;">總價格<span id="alltotal" style="font-size:25px"></span>
                <button id="accounts" type="button" class="btn btn-secondary btn-lg" style="margin-top:20px">結帳去</button>
                <button name="action" id="submit" value="check" style="display:none"></button></div>
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




<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/CartList-js/selectCL.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>