<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pd.model.*"%>
<%@ page import="com.po.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<% 	
	PdVO pdVO = (PdVO) request.getAttribute("pdVO");
 	Integer po_pdid = pdVO.getPd_id();
	PoService poSvc = new PoService();
	Integer sum = poSvc.getSum(po_pdid);
%>

<jsp:useBean id="pcSvc" scope="page" class="com.pc.model.PcService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">
<title>商品頁面</title>

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
                
                    </ul>
                </div>
            </div>
        </nav>
        <!--分隔線 --><hr class="featurette-divider mt-4 mb-4 ">
<!-- ---------------------- 以上為右邊 -->

</header>





<body>

    <!--商品詳情起始 -->
    <div class="container mt-4 mb-4">
        <div class="row border-$gray-500 border">
            <!-- 圖片內容開頭 -->
            <div class="col-6" style="border-radius: 10px; height:500px;">
                <div id="carouselExampleControls" class="carousel slide " data-bs-ride="carousel">
                    <div class="carousel-inner">
                       
                       
                        <!-- 每張圖片開頭 -->
                        
             <jsp:useBean id="piSvc" scope="page" class="com.pi.model.PiService" />            
                        
						<c:if test="${piSvc.check(pdVO.pd_id) }">
                            <c:forEach var="img" items="${piSvc.getall(pdVO.pd_id)}">
                            <div id="imgbox" style="max-width: 100%; height: 500px; ">
                            <img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${piSvc.out(img.pi_desc)}">
                              </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${piSvc.check(pdVO.pd_id) == false}">
                            <div id="imgbox" style="max-width: 100%; height: 500px; ">
                            <img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${piSvc.showDefaultImg()}">
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
            <!-- 圖片內容結尾 -->
            <div class="col-6 h-100">
                <div class="row">
                    <div class="col-12 border-bottom border-$gray-500">
                        <!-- 商品名稱 -->
                        <p class="h1 text-center">${pdVO.pd_name}</p>
                    </div>
                    <div class="col-12 mt-3">
                        <!-- 商品狀態 -->
                        <p class="h4">
            				<c:if test="${pdVO.pd_sta ==0 }">
								<td>尚未開賣</td>				
							</c:if>
							<c:if test="${pdVO.pd_sta ==1 }">
								<td>熱賣中</td>				
							</c:if>
							<c:if test="${pdVO.pd_sta ==2 }">
								<td>已結束拍賣</td>				
							</c:if>
							<c:if test="${pdVO.pd_sta ==3 }">
								<td>商品已下架</td>				
							</c:if>
                        </p>
                    </div>
                </div>
                <div class="row" style="height: 230px;">
                    <div class="col-12">
                        <!-- 商品介紹 -->
                        <p class="h5">${pdVO.pd_desc}</p>
                    </div>
                </div>
                <div class="row  border-top border-$gray-500">
                    <!-- 商品單價 -->
                    <p class="h3"><i class="bi bi-currency-dollar"></i>商品售價:${pdVO.pd_price}</p>
                </div>
               
               
              
                <div class="row ">
                <!-- 成團人數 -->
                        <p class="h5">
                            成團人數 : ${pdVO.pd_no}
                        </p>
                </div>
               
                               <div class="row ">
                <!-- 已參加人數 -->

                        <p class="h5">
                            已經參加人數:<%=sum %>	
                        </p>
                </div>
               
               
				<!--截止日期 -->
                <div class="row ">
                   
                        <p class="h5">
                            截止日期: <fmt:formatDate value="${pdVO.pd_edate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </p>
                </div>
				

<FORM METHOD="post" action="/CFA101G4/PoServlet">				
                <!-- 要加橫列的話這一段 -->
                <div class="row ">
                    <div class="col-12">
                      數量 	<input type="number"  min="1" name="NUMBER" value="1">
                    </div>
                </div>
                



                <div class="row justify-content-center">
                    
                    
					<div class="col">
						<c:if test="${pdVO.pd_sta == 0  }">
						<button   class="btn btn-success btn-lg" disabled>
                        <i class="bi bi-suit-heart"></i>無法購買
                        </button>				
						</c:if>
						
						<c:if test="${pdVO.pd_sta == 1  }">
						<button   class="btn btn-success btn-lg">
                        <i class="bi bi-suit-heart"></i>點擊購買
                        </button>		
						</c:if>
     					<c:if test="${pdVO.pd_sta == 2  }">
						<button   class="btn btn-success btn-lg" disabled>
                        <i class="bi bi-suit-heart"></i>無法購買
                        </button>				
						</c:if>
						<c:if test="${pdVO.pd_sta == 3  }">
						<button   class="btn btn-success btn-lg" disabled>
                        <i class="bi bi-suit-heart"></i>無法購買
                        </button>				
						</c:if>
     					
                         <!--將商品資訊傳到訂單頁面 -->
        <!--商品名稱 -->
		<input type="hidden" name="PD_NAME" value="${pdVO.pd_name}">
		<!--商品編號 -->
		<input type="hidden" name="PD_ID" value="${pdVO.pd_id}">
		<!--商品價格 -->
		<input type="hidden" name="PD_PRICE" value="${pdVO.pd_price}">
		<!--商品描述 -->
		<input type="hidden" name="PD_DESC" value="${pdVO.pd_desc}">
		<!--賣家會員編號 -->
		<input type="hidden" name="PD_SMEMID" value="${pdVO.pd_smemid}">
		<input type="hidden" name="action" value="go_order">

                    </div>
 
 </FORM>                   
      				
					
<%--                       <c:if test="${memberVO == null }"> --%>
<!--                     <div class="col"> -->
<%--                         <button type="button" value="${pdVO.pd_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart"></i>追蹤</button> --%>
<!--                     </div> -->
<%--                 </c:if> --%>
<%--                 <c:if test="${memberVO != null }"> --%>
<%--                 	<c:if test="${ts.check(productVO.p_id, memberVO.mem_id)}"> --%>
<!--                     <div class="col"> -->
<%--                         <button type="button" value="${pdVO.pd_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart-fill"></i>追蹤中</button> --%>
<!--                     </div> -->
<%--                     </c:if> --%>
<%--                     <c:if test="${ts.check(productVO.p_id, memberVO.mem_id) == false}"> --%>
<!--                     <div class="col"> -->
<%--                         <button type="button" value="${pdVO.pd_id}" class="btn btn-primary btn-lg"><i class="bi bi-suit-heart"></i>追蹤</button> --%>
<!--                     </div> -->
<%--                     </c:if> --%>
<%--                 </c:if> --%>




                    <!-- 跳出視窗的按鈕開頭 -->
<!--                     <div class="col"> -->
<!--                         <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal" -->
<!--                             data-bs-target="#staticBackdrop"> -->
<!--                             檢舉此商品 -->
<!--                         </button> -->
<!--                     </div> -->
                     <!-- 跳出視窗的按鈕結尾 -->

                    <!-- 跳出視窗的本體開頭 -->
                    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="staticBackdropLabel">檢舉頁面</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                    <div class="modal-body">
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col m-auto">
                                                    <div class="input-group input-group-lg m-auto">
                                                        <span class="input-group-text"
                                                            id="inputGroup-sizing-lg">檢舉商品編號</span>
                                                        <input type="text" value="${pdVO.pd_id}"class="form-control"
                                                            aria-label="Sizing example input"
                                                            aria-describedby="inputGroup-sizing-lg" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row h-50">
                                                <div class="col h-50 m-auto">
                                                    <div class="input-group input-group-lg h-50 mt-3">
                                                        <span class="input-group-text"
                                                            id="inputGroup-sizing-lg">檢舉內容</span>
                                                        <textarea class="form-control h-50" name="prcontent"
                                                            id="prcontent" aria-label="With textarea"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button value="add" name="action" class="btn btn-primary">送出檢舉</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">取消</button>
                                    </div>
                            </div>
                        </div>
                    </div>
                    <!-- 跳出視窗的本體結尾 -->

                </div>
            </div>
        </div>
    </div>
    <!-- 商品詳情結尾 -->


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
<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/Preproduct-js/listOnePrePd.js"></script>

</body>

</html>