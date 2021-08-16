<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
	<!-- Include a polyfill for ES6 Promises (optional) for IE11 and Android browser -->
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

<script>let allCheck = false;</script>
</head>
<body>
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<jsp:useBean id="cs" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="ns" scope="page" class="com.notice.model.NoticeService" />
<jsp:useBean id="cgs" scope="page" class="com.category.model.CategoryService" />

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
				<c:forEach var="cgs" items="${cgs.all}">
				  <option value="${cgs.cg_id }">${cgs.cg_name }</option>
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
          ${cs.getSum(memberVO.mem_id)}
          </div>
          </c:if>
<!--           購物車圖示結尾  -->
          
            <i class="bi bi-cart2 ju" style="font-size: 50px;"></i>
        </div>
      </div>
  </div>
</header>



<!-- 訂單內容的整個起始頭 -->
       <div class="container">
       
		<button type="button" data-bs-toggle="modal" id="fastbody" data-bs-target="#fast" class="btn btn-primary btn-lg sticky-top" style="left: 91%; top: 30px;">快速填寫資訊</button>       
       
       	
       
       
       
        <form onSubmit="{if(!allCheck){return false} ;}" action="<%=request.getContextPath()%>/OrdersServlet" method="post" style="margin:0px">
            <ul class="list-group list-group-flush mt-3 mb-3">
                 <!-- 一個訂單的區塊 -->
				<c:forEach var="id" items="${id}">
				<input type="hidden" value="${id}" name="foradd" id="foradd">
				<div class="popover-header">
                <a class="navbar-brand">店家 ${id} 的訂單明細</a>
                <button type="button" class="btn btn-secondary btn-sm mb-1" data-bs-toggle="collapse" data-bs-target="#showmore${id}" aria-expanded="false" aria-controls="collapseExample">收起資訊</button>
					<!--商品陳列開頭-->
					<div class="collapse show" id="showmore${id}">
                        <c:forEach var="pd" items="${list}">
                        <c:if test="${id == pd.p_memid }">
                        <li class="list-group-item w-100" id="showbody">
                        <div class="row" style="margin-bottom: 10px; ">
                            <div class="col-1 align-self-center"  style="text-align: center;"><div id="clpid" >${cls.cl_pid}<input type="hidden" name="pid" value="${cls.cl_pid}" id="clpid"></div></div>
                            <div class="col-5 align-self-center" style="text-align: center;">
                                <div class="card mb-3 " style="height:100px; border-style: none;">
                                    <div class="row g-0">
                                      <div class="col-md-4">
                                      <c:if test="${pis.check(pd.p_id) }">
										<img class="img-fluid rounded-start" src="data:image/gif;base64, ${pis.get(pd.p_id)}">
										</c:if>
										<c:if test="${pis.check(pd.p_id) == false}">
										<img class="img-fluid rounded-start" src="data:image/gif;base64, ${pis.showDefault()}">
										</c:if>
                                      </div>
                                      <div class="col-md-8" style="height:100px;">
                                        <div class="card-body">
                                          <h5 class="card-title">${pd.p_name}</h5>
                                          <div class="carddesc"><p class="card-text" >${pd.p_desc}</p></div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                            </div>
                            <div class="col align-self-center" style="text-align: center;">
                                <div id="PDprice">
                                   ${pd.p_price}
                                </div>
                            </div>
                            <div class="col-2 align-self-center" style="text-align: center;">
                                ${pd.p_stock}
                            </div>
                            <div class="col align-self-center" style="text-align: center;">
                                <div id="PDtotal">
                                    ${pd.p_price * pd.p_stock}
                                </div>
                            </div>
                        </div>
                    </li>
                    </c:if>
                    
                    </c:forEach>
                    
<!--                   商品陳列結尾   									-->
                    <li class="list-group-item">
                        <div class="row" style="margin-bottom: 10px; ">
                            <div class="col-2" style="text-align: center;">
                                <select class="form-select form-select-lg m-0" aria-label=".form-select-lg example" id="do${id}" name="do${id}">
                                    <option value="">請選擇物流</option>
                                    <option value="0">宅配</option>
                                </select>
                            </div>
                            <div class="col-2 align-self-center">
                                <select class="form-select form-select-lg m-0" aria-label=".form-select-lg example" name="city${id}" id="city${id}">
                                  </select>
                            </div>
                            <div class="col-2 align-self-center">
                                <select class="form-select form-select-lg m-0" aria-label=".form-select-lg example" name="dist${id}" id="dist${id}">
                                  </select>
                            </div>
                            <div class="col-3 align-self-center">
                                <input class="form-control form-control-lg" type="text" placeholder="請填入地址資訊"  name="add${id}" id="add${id}">
                            </div>
                            <div class="col-1 align-self-center" >
                                <p class="fs-4 m-0">總金額</p>
                            </div>
                            <div class="col-1 align-self-center" >
                            <c:forEach var="od" items="${od}">
                                <c:if test="${od.od_smemid == id }">
                                <p class="fs-2 m-0">${od.od_price}</p>
                                </c:if>
                            </c:forEach>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px; ">
                            <div class="col-4" style="text-align: center;">
                                <input class="form-control form-control-lg" type="text" placeholder="出貨備註/收件時間"  id="msg${id}" name="msg${id}" >
                            </div>
                            <div class="col-3" style="text-align: center;">
                                <input class="form-control form-control-lg" type="text" placeholder="請輸入收件人電話"  id="tel${id}" name="tel${id}">
                            </div>
                            <div class="col-3" style="text-align: center;">
                                <input class="form-control form-control-lg" type="text" placeholder="請輸入收件人姓名"  id="name${id}" name="name${id}">
                            </div>
                            </div>
                    	</li>
                    	</div>
					</div>

                <!-- 一個訂單的區塊 -->
			</c:forEach>
                
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
                    <select class="form-select form-select-lg m-0" aria-label=".form-select-lg example" id="paymhtod" name="paymhtod">
                        <option value="">請選擇付款方式</option>
                        <option value="1">錢包付款</option>
                    </select>
                    <input type="hidden" name="orders" value="${id}">
                    <input type="hidden" id="memele" value="${memberVO.mem_ele}">
                    <input type="hidden" id="total" value="${total}">
                    </div>
                    <div class="col-12">
                        <p class="h3 text-end">總價格 ${total}</p>
                    </div>
                </div>
                

                <div class="row">
                    <div class="col text-end">
                        <button type="button" class="btn btn-secondary btn-lg" name="action" value="add" id="push">送出訂單</button>
                        <button name="action" id="submit" value="add" style="display:none">送出訂單</button>
                    </div>
                </div>
            </div>
          </div>
        </div>

			
        
        </div>
        </form>
       </div>
        <!-- 付款方式結束點 -->




 
<div class="modal fade" id="fast" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">快速填寫資訊</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		          <div class="mb-3">
		        	<select class="form-select form-select-lg m-0" id="fastdo" aria-label=".form-select-lg example">
                            <option value="">請選擇物流</option>
                            <option value="0">宅配</option>
                    </select>
		          </div>
		          <div class="mb-3">
                      <select class="form-select form-select-lg m-0" aria-label=".form-select-lg example" id="fastcity">
                      </select>
		          </div>
		          <div class="mb-3">
		        	<select class="form-select form-select-lg m-0" aria-label=".form-select-lg example"  id="fastdist">
                    </select>
		          </div>
		          <div class="mb-3">
		            <label for="recipient-name" class="col-form-label" >地址:</label>
		            <input type="text" class="form-control" id="fastadd">
		          </div>
		          <div class="mb-3">
		            <label for="recipient-name" class="col-form-label">收件人:</label>
		            <input type="text" class="form-control" id="fastname">
		          </div>
		          <div class="mb-3">
		            <label for="recipient-name" class="col-form-label">電話:</label>
		            <input type="text" class="form-control" id="fasttel">
		          </div>
		          <div class="mb-3">
		            <label for="message-text" class="col-form-label">備註:</label>
		            <textarea class="form-control" id="fastmsg"></textarea>
		          </div>
		      <div class="modal-footer">
		      	<button type="button" id="okfast" class="btn btn-primary">快速填入</button>
		        <button type="button" class="btn btn-secondary" id="fastclose" data-bs-dismiss="modal">取消</button>
		      </div>
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

  
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/JS/Front-end-js/taiwan_address_auto_change.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/Orders-js/CheckOrder.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script>
</script>
</body>
</html>