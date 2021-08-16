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
<jsp:useBean id="pd" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="t" scope="page" class="com.tracking.model.TrackingService" />
<jsp:useBean id="pis" scope="page" class="com.productImg.model.ProductImgService" />
<jsp:useBean id="cls" scope="page" class="com.cartList.model.CartListService" />
<jsp:useBean id="cs" scope="page" class="com.category.model.CategoryService" />
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













 <!-- 網頁中間 -->

 <nav class="container-fluid">
        <div class="row mt-3 justify-content-center" id="i1">

<!-- 	每個商品起始頭 -->
        <c:forEach var="pd" items="${pb.list }">
         <div class="card" style="width: 18rem; margin-left: 5px ; margin-bottom: 5px;">
			<!--          是否有沒有圖片  如果有 開頭-->
                        <c:if test="${pis.check(pd.p_id) }">
                            <img src="data:image/gif;base64, ${pis.get(pd.p_id)}" class="img-thumbnail" alt="..." style="height:230px">
                        </c:if>
            <!--          是否有沒有圖片  如果有 結束-->
            <!--          是否有沒有圖片  如果沒有 開始-->
                        <c:if test="${pis.check(pd.p_id) == false}">
                            <img src="data:image/gif;base64, ${pis.showDefault()}" class="img-thumbnail" alt="..." style="height:230px">
                        </c:if>
            <!--          是否有沒有圖片  如果沒有 結束-->
                        <div class="card-body">
                        <h5 class="card-title"><a href="<%=request.getContextPath()%>/ProductServlet?action=selectOnePD&pid=${pd.p_id}">${pd.p_name }</a></h5>
                        <div class="carddesc"><p class="card-text">${pd.p_desc }</p></div>
                        </div>
                        <ul class="list-group list-group-flush">
                        <li class="list-group-item">${pd.p_price }</li>
                        <li class="list-group-item">
<!--                       確認數量開頭-->
                        <c:if test="${pd.p_stock > 0 }">
                      	  數量${pd.p_stock }
                      	</c:if>
                      	<c:if test="${pd.p_stock <= 0 }">
                      	<p class="h6 p-0 m-0" style="color: red;">
                      	完售
                      	</p>
                      	</c:if>
<!--                       	確認數量結尾-->
                        </li>
                        </ul>
                        <div class="card-body">
<!--                         購物車按鈕    -->
                        	<c:if test="${pd.p_stock <= 0 }">
                        	<button type="button" class="btn btn-outline-secondary disabled" id="addCL" value="${pd.p_id}"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
                        	</c:if>
                        	<c:if test="${pd.p_stock > 0 }">

                        	<c:if test="${memberVO == null }">
                        	<button type="button" class="btn btn-outline-secondary" id="addCL" value="${pd.p_id}"><i class="bi bi-cart-plus" id="addCL"></i>加入</button>
                        	</c:if>
                        	<c:if test="${memberVO != null }">
           		            	<c:if test="${cls.check(memberVO.mem_id, pd.p_id) }">
           		            		<button type="button" class="btn btn-outline-secondary" id="delCL" value="${pd.p_id}"><i class="bi bi-cart-dash" id="delCL"></i>移除</button>
            	            	</c:if>
            	            	<c:if test="${cls.check(memberVO.mem_id, pd.p_id) == false}">
            	            		<button type="button" class="btn btn-outline-secondary" id="addCL" value="${pd.p_id}"><i class="bi bi-cart-plus" id="addCL" ></i>加入</button>
            	            	</c:if>
                        	</c:if>
                        	</c:if>
<!--                         購物車按鈕    -->
                        
                        
                        
<!--                         		追蹤按鈕 -->
                            	<c:if test="${memberVO == null }">
                                <button type="button" class="btn btn-outline-secondary" value="${pd.p_id}" id="tadd"><i class="bi bi-suit-heart"></i>追蹤</button>
                                </c:if>
                                <!--          有登入的話    -->
                                <c:if test="${memberVO != null }">
                                <!--             判斷是否以追蹤 若會的話則回傳ture  -->
                                	<c:if test="${t.check(pd.p_id,memberVO.mem_id)}" >
                                		<button type="button" class="btn btn-outline-secondary" value="${pd.p_id}" id="tdel"><i class="bi bi-suit-heart-fill"></i>追蹤中</button>
                                	</c:if>
                                <!--判斷是否以追蹤 若會的話則回傳false 值得一提的是一定要 == false 不然內容不會執行導致其他是空的  -->
                                	<c:if test="${t.check(pd.p_id,memberVO.mem_id) == false}" >
                                		<button type="button" class="btn btn-outline-secondary" value="${pd.p_id}" id="tadd"><i class="bi bi-suit-heart"></i>追蹤</button>
                                	</c:if>
                            	</c:if>
<!--                             	追蹤按鈕 	-->
                        	
                        </div>
                    </div>
        </c:forEach>
        </div>

    </nav>

<!-- 分頁 -->
<div class="col" >
<nav aria-label="Page navigation example" style="margin : 0px auto">
  <ul class="pagination" style="justify-content:center">
  <c:if test="${pb.currentPage == 1 }">
  	<li class="page-item disabled">
      <a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pb.currentPage -1}&rows=20" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
  </c:if>
  <c:if test="${pb.currentPage != 1 }">
    <li class="page-item">
      <a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pb.currentPage -1}&rows=20" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
  </c:if>
  
  
  <c:if test="${pb.totalPage <= 10 }">
	    <c:forEach begin="1" end="${pb.totalPage}" var="pg">
	    	 <c:if test="${pb.currentPage == pg}">
	    	 	  <li class="page-item active"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    	 <c:if test="${pb.currentPage != pg}">
	    	 	  <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    </c:forEach>
    </c:if>
    
    
    
    <c:if test="${pb.currentPage > 5 && pb.totalPage > 10 && (pb.totalPage - pb.currentPage) >= 5 }">
	    <c:forEach begin="${pb.currentPage - 5}" end="${pb.currentPage + 5}" var="pg">
	    	 <c:if test="${pb.currentPage == pg}">
	    	 	  <li class="page-item active"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    	 <c:if test="${pb.currentPage != pg}">
	    	 	  <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    </c:forEach>
    </c:if>
    
    
  	<c:if test="${pb.currentPage <= 5 && pb.totalPage >= 10 }">
	    <c:forEach begin="1" end="10" var="pg">
	    	 <c:if test="${pb.currentPage == pg}">
	    	 	  <li class="page-item active"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    	 <c:if test="${pb.currentPage != pg}">
	    	 	  <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
	    	 </c:if>
	    </c:forEach>
    </c:if>
    
    
    
    
    
    
    <c:if test="${ (pb.totalPage - pb.currentPage) < 5 && pb.totalPage >10}">
    <c:forEach begin="${pb.currentPage - 5}" end="${pb.totalPage}" var="pg">
    	 <c:if test="${pb.currentPage == pg}">
    	 	  <li class="page-item active"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
    	 </c:if>
    	 <c:if test="${pb.currentPage != pg}">
    	 	  <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
    	 </c:if>
    </c:forEach>
    </c:if>
    
    
    
    
  	<c:if test="${pb.currentPage > 5 && (pb.totalPage - pb.currentPage) >= 5 } ">
    <c:forEach begin="${pb.currentPage-5}" end="${pb.currentPage+5}" var="pg">
    	 <c:if test="${pb.currentPage == pg}">
    	 	  <li class="page-item active"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
    	 </c:if>
    	 <c:if test="${pb.currentPage != pg}">
    	 	  <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pg}&rows=20">${pg}</a></li>
    	 </c:if>
    </c:forEach>
    </c:if>
    
    
    
    
	<c:if test="${pb.currentPage == pb.totalPage }">
    <li class="page-item disabled">
      <a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pb.currentPage +1 }&rows=20" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
	</c:if>
	<c:if test="${pb.currentPage != pb.totalPage }">
    <li class="page-item">
      <a class="page-link" href="<%=request.getContextPath()%>/ProductServlet?action=getAllByPage&currentPage=${pb.currentPage +1 }&rows=20" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
	</c:if>
  </ul>
</nav>
  總頁數${pb.totalPage }總筆數${pb.totalCount }
</div>
<!-- 分頁 -->
 
 
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
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/Product-js/selectAllPD.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/header.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>

</body>
</html>