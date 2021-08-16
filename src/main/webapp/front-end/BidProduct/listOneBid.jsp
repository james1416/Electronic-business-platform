<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bidproduct.model.*"%>
<%@ page import="com.bpimage.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	BidProductVO bidVO = (BidProductVO) request.getAttribute("bidVO");
%>
<%
	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
%>

<!DOCTYPE html>


<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Document</title>
</head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Front-end-css/header.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
h3{
color:red;
}
</style>
<body>
<jsp:useBean id="bpcSvc" scope="page" class="com.bpclassification.model.BpClassificationService" />
<jsp:useBean id="bpiSvc" scope="page" class="com.bpimage.model.BpImageService" />
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



    <!--商品詳情起始 -->
    <div class="container mt-4 mb-4">
        <div class="row border-$gray-500 border" style="border-radius: 10px; height:auto;">
            <!-- 圖片內容開頭 -->
            <div class="col-6" >
                <div id="carouselExampleControls" class="carousel slide " data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <!-- 每張圖片開頭 -->
                       <c:if test="${bpiSvc.check(bidVO.bp_id) }">
                            <c:forEach var="img" items="${bpiSvc.getall(bidVO.bp_id)}">
                            <div id="imgbox" style="max-width: 100%; height: 500px; ">
                            <img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${bpiSvc.out(img.bpi_img)}">
                              </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${bpiSvc.check(bidVO.bp_id) == false}">
                            <div id="imgbox" style="max-width: 100%; height: 500px; ">
                            <img class="rounded w-100 h-100" alt="..." src=" data:image/gif;base64, ${bpiSvc.showDefault()}">
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
                        <!-- 每張圖片結尾 -->
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            
            <!-- 圖片內容結尾 -->
            <div class="col-6 h-100">
                <div class="row">
                    <div class="col-12 border-bottom border-$gray-500">
                        <!-- 商品名稱 -->
                        <p class="h1">商品名稱:${bidVO.bp_name}</p>
                    </div>
                    <div class="col-12 mt-3">
                        <!-- 商品分類 -->
                        <p class="h5">分類名稱:${bpcSvc.getOneBpc(bidVO.bp_bpcid).bpc_cgname}</p>
                    </div>
                    <div class="col-12">
                        <!-- 商品分類 -->
                        <p class="h5">競標狀態:
                        <c:if test="${bidVO.bp_sta == 0}"><td>競標尚未開始</td></c:if>
						<c:if test="${bidVO.bp_sta == 1}"><td >競標中</td></c:if>
						<c:if test="${bidVO.bp_sta == 2}"><td>競標結束</td></c:if>
                        </p>
                    </div>
                    <div class="col-12">
                        <!-- 商品分類 -->
                       <c:if test="${bidVO.bp_sta == 0}"><p class="etime">開始時間:<fmt:formatDate value="${bidVO.bp_stime}" pattern="yyyy-MM-dd HH:mm:ss"/></p></c:if>
                        <c:if test="${bidVO.bp_sta == 1}"><p class="etime">結束時間:<fmt:formatDate value="${bidVO.bp_etime}" pattern="yyyy-MM-dd HH:mm:ss"/></p></c:if>
                    </div>            
                    <div class="col-12">
                    	 <p class="h5">競標時間狀態:
                        <c:if test="${bidVO.bp_sta == 0}"><h2>即將開始於:</h2><h3 id="etimetext"></h3></c:if>
						<c:if test="${bidVO.bp_sta == 1}"><h2>競標剩餘時間:</h2><h3 id="etimetext"></h3></c:if>
						<c:if test="${bidVO.bp_sta == 2}"><h2>競標已結束</h2></c:if>
                        </p>
                    </div>
                    <div class="col-12">
                        <!-- 商品分類 -->
                        <p class="h5">出價增額:${bidVO.bp_inc}</p>
                    </div>
                    <div class="col-12">
                        <!-- 商品分類 -->
                        <p class="h5">起標金額:${bidVO.bp_sprice}</p>
                    </div>
                </div>
                <div class="row" style="height:230px;">
                    <div class="col-12">
                        <!-- 商品介紹 -->
                        <p class="h5">商品介紹:${bidVO.bp_desc}</p>
                    </div>
                </div>
                <div class="row text-center border-top border-$gray-500">
                    <!-- 商品單價 -->
					<p class="h2">目前最高出價:<i class="bi bi-currency-dollar" id="toprice">${bidVO.bp_tprice}</i></p>
                </div>
                 <div class="row">

               
                    <div class="col-6">
						<input type="text" id="bpd_bpprice" name="bpd_bpprice">
<!-- 						<input type="hidden" name="action" value="insert"> -->
<!-- 						<input type="hidden" name="bpd_bpid" >	 -->
                        <button type="submit" value="<%=bidVO.getBp_id()%>" id="bpd_bpid" class="btn btn-outline-secondary">
                                <i class="bi bi-currency-dollar">我要出價</i>
                        </button>
                    </div>

                  </div>
       			<div class="row">
                    <div class="col-12">
                       <c:if test="${not empty errorMsgs}">
						<font style="color:red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
						</c:if>
                    </div>
                </div>
                <!-- 要加橫列的話這一段 -->
               
                


                <div class="row justify-content-center text-center border-top border-$gray-500 mt-3">
                    
<!--                     <div class="col mt-3"> -->
<%--                         <button type="button" value="${bidVO.bp_id}" class="btn btn-primary btn-lg" > --%>
<!--                         <i class="bi bi-suit-heart"></i> -->
<!--                         	追蹤</button> -->
<!--                     </div> -->


                    <!-- 跳出視窗的按鈕開頭 -->
<!--                     <div class="col mt-3"> -->
<!--                         <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#staticBackdrop" > -->
<!--                             	檢舉此商品 -->
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
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                    <div class="modal-body">
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col m-auto">
                                                    <div class="input-group input-group-lg m-auto">
                                                        <span class="input-group-text"
                                                            id="inputGroup-sizing-lg">檢舉商品編號</span>
                                                        <input type="text" value="${bidVO.bp_id}"class="form-control"
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
                                        <button value="add" name="action" class="btn btn-primary" id="send">送出檢舉</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">取消</button>
                                    </div>
                            </div>
                        </div>
                    </div>
                    <!-- 跳出視窗的本體結尾 -->
					<!--顯示競標紀錄 -->
					<div class="col mt-3">
                    <button id="getbp_detail" class="btn btn-info btn-lg" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">競標紀錄</button>
                    </div>
					<div class="collapse" id="collapseExample">
					  <div class="card card-body" id="detail_body">
					   	<table class="table" id="view_detail">
						  
						  </table>
					  </div>
					  <!--顯示競標紀錄結尾 -->
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






    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/CFA101G4/JS/Front-end-js/bid_order/listOneBid.js"></script>
	<script src="/CFA101G4/JS/Front-end-js/bid_order/listone.js"></script> 
	<script src="/CFA101G4/JS/Front-end-js/bid_order/detail.js"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>

</body>

</html>