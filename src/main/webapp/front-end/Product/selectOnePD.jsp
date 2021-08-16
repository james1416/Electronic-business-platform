<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script> -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.slim.min.js" integrity="sha256-u7e5khyithlIdTpu22PHhENmPcRdFiHRjhAuHcs05RI=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
	<!-- Include a polyfill for ES6 Promises (optional) for IE11 and Android browser -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">

</head>
<body>

<jsp:useBean id="cs" scope="page" class="com.category.model.CategoryService" />  	
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" /> 
<jsp:useBean id="cls" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="ts" scope="page" class="com.tracking.model.TrackingService" />
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




<!--商品詳情起始 -->
    <div class="container mt-4 mb-4">
        <div class="row border-$gray-500 border" style="border-radius: 10px; height:500px;">
            <div class="col-6">
                <div id="carouselExampleControls" class="carousel slide " data-bs-ride="carousel">
                    <div class="carousel-inner">
                    	<c:if test="${pis.check(productVO.p_id) }">
                            <c:forEach var="img" items="${pis.getall(productVO.p_id)}">
							<div id="imgbox" style="max-width: 100%; height: 500px; ">
							<img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${pis.out(img.pi_image)}">
                      		</div>
							</c:forEach>
                        </c:if>
                        <c:if test="${pis.check(productVO.p_id) == false}">
                            <div id="imgbox" style="max-width: 100%; height: 500px; ">
							<img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${pis.showDefault()}">
                      		</div>
                        </c:if>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                      <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                      <span class="carousel-control-next-icon" aria-hidden="true"></span>
                      <span class="visually-hidden">Next</span>
                    </button>
                  </div>
            </div>
            <div class="col-6">
                <div class="row">
                    <div class="col-12 border-bottom border-$gray-500"><p class="h1">${productVO.p_name}</p></div>
                    <div class="col-12 mt-3"><p class="h4">
                   	<c:forEach var="cs" items="${cs.all }">
					<c:if test="${productVO.p_cgid == cs.cg_id}">
					${cs.cg_name }
					</c:if>           
            		</c:forEach>
                    </p></div>
                </div>
                <div class="row h-50">
                    <div class="col-12"><p class="h5">${productVO.p_desc}</p></div>
                </div>
                <div class="row text-center border-top border-$gray-500">
                    <p class="h2">${productVO.p_price}<i class="bi bi-currency-dollar"></i></p>
                </div>
                <div class="row text-center">
                    <c:if test="${productVO.p_stock > 0}">
                    <p class="h5">
                   		 剩餘數量${productVO.p_stock}
                   	</p>
                   	</c:if>
                   	<c:if test="${productVO.p_stock <= 0}">
                   	<p class="h5" style="color: red;">
                   		 <i>完售</i>
                    </p>
                   	</c:if>
                    
                </div>
                <div class="row justify-content-center">
                <c:if test="${memberVO == null }">
                <c:if test="${productVO.p_stock > 0}">
                    <div class="col-6 text-end">
                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
                    </div>
                </c:if>
                <c:if test="${productVO.p_stock <= 0}">
                    <div class="col-6 text-end">
                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg disabled"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
                    </div>
                </c:if>
                </c:if>
                <c:if test="${memberVO != null }">
                	<c:if test="${cls.check(productVO.p_id, memberVO.mem_id)}">
                		<div class="col-6 text-end">
                        	<button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-cart-dash" id="delCL"></i>移除</button>
                    	</div>
	                </c:if>
	                <c:if test="${cls.check(productVO.p_id, memberVO.mem_id) == false}">
	                	<c:if test="${productVO.p_stock > 0}">
		                	<div class="col-6 text-end">
                        		<button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
                    		</div>
		                </c:if>
		                <c:if test="${productVO.p_stock <= 0}">
		                    <div class="col-6 text-end">
		                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg disabled"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
		                    </div>
		                </c:if>
	                	   
	                </c:if>
                </c:if>
                
                
                
                
                <c:if test="${memberVO == null }">
                    <div class="col">
                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart"></i>追蹤</button>
                    </div>
                </c:if>
                <c:if test="${memberVO != null }">
                	<c:if test="${ts.check(productVO.p_id, memberVO.mem_id)}">
                    <div class="col">
                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart-fill"></i>追蹤中</button>
                    </div>
                    </c:if>
                    <c:if test="${ts.check(productVO.p_id, memberVO.mem_id) == false}">
                    <div class="col">
                        <button type="button" value="${productVO.p_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart"></i>追蹤</button>
                    </div>
                    </c:if>
                </c:if>
                
                    
                    <!-- Button trigger modal -->
                    <div class="col">
					<button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
					  檢舉此商品
					</button>
					</div>
					<!-- Modal -->
					<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
					  <div class="modal-dialog modal-dialog-centered">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="staticBackdropLabel">檢舉頁面</h5>
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <form action="<%=request.getContextPath()%>/ProductReportServlet" method="post">
					      <div class="modal-body">
						        <div class="container-fluid">
								      	<div class="row">
								      		<div class="col m-auto">
								        	<div class="input-group input-group-lg m-auto">
			                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉商品編號</span>
			                                <input type="text" value="${productVO.p_id}" name="prpid" id="prpid" class="form-control" aria-label="Sizing example input"
			                                    aria-describedby="inputGroup-sizing-lg" readonly>
			                            	</div>
								        	</div>
								        </div>
								        <div class="row h-50">
								        	<div class="col h-50 m-auto">
								        		<div class="input-group input-group-lg h-50 mt-3">
			                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉內容</span>
			                                <textarea class="form-control h-50" name="prcontent" id="prcontent" aria-label="With textarea"></textarea>
			                            		</div>
								        	</div>
								        </div>
								</div>
						   </div>
						      	<div class="modal-footer">
							      	<button value="add" name="action" class="btn btn-primary">送出檢舉</button>
							        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
						      	</div>
						     </form>
					    </div>
					  </div>
					</div>
                     
                    
                    
                    
                </div>
            </div>
        </div>
    </div>
<!-- 商品詳情結尾 -->
















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
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/Product-js/selectOnePD.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
</body>
</html>