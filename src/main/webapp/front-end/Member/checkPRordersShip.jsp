<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>	
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<title>待確認訂單</title>
<style>
        /* div{
            border: 1px solid black;            
        } */
        div.row, div.container, div.col, div.col-11{
            padding-left: 0px;
            padding-right: 0px;
        }
        div.row{
            margin-left: 0px;
            margin-right: 0px;
        }
        body{
            background-color: #ebebeb;
        }
        a{
            text-decoration: none;
        }
        ul{
            margin-bottom: 0px;
            margin-top: 10px;
        }
        img{
        	height:80px;
        	width:80px;
        }
    </style>
</head>


<!-- 通知的service -->
<jsp:useBean id="ns" scope="page" class="com.notice.model.NoticeService" />




<body>




 <div class="container-fluid">
        <div class="row  h-100">
          <div class="col-2" style="padding-right: 0px;">
              <div class="col2">
                   <div class="row" style="height: 100px;">
                       <div class="col-5">
                        
                       </div>
                   </div>
                   <div class="col" style="margin-left: 40px;">
                       <div class="col" id="collapseExample" data-bs-target="#collapseExample">
                            <a href="/CFA101G4/front-end/Member/member_center.html" id="myAccount">我的帳戶</a>
                       </div>
                       
                   </div>
                   
                   <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/front-end/Member/checkAllNotices.html" id="notice">通知總覽</a>
                        </div>
                        
                   </div>
                   <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/front-end/Member/storeMoney.html">錢包儲值</a>
                        </div>
                   </div>
                    <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/front-end/Member/checkOrdersStatus.jsp">我的一般購物</a>
                        </div>
                    </div>
                    <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/front-end/Member/checkPRordersAll.jsp">我的預購</a>
                        </div>
                    </div>
                    <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/bid.do?action=get_win_bid">我的競標</a>
                        </div> 
                   </div>
                   <div class="col" style="margin-left: 40px; margin-top: 10px;">
                        <div class="col">
                            <a href="/CFA101G4/front-end/FrontMain.jsp">回首頁</a>
                        </div> 
                   </div>
              </div>
          </div> 
          <div class="col-10">
              
	              <div class="col shadow-sm p-3 mb-5 bg-white rounded" style="background-color: white; margin-top: 15px; margin-bottom: 15px!important;">
	                <ul class="nav justify-content-around">
	                    <li class="nav-item">
	                      <a class="nav-link active" aria-current="page" href="/CFA101G4/front-end/Member/checkPRordersAll.jsp">全部</a>
	                    </li>
	                    
	                    <li class="nav-item">
	                      <a class="nav-link" href="/CFA101G4/front-end/Member/checkPRordersShip.jsp">待出貨</a>
	                    </li>
	                    <li class="nav-item">
	                      <a class="nav-link" href="/CFA101G4/front-end/Member/checkPRordersRecipt.jsp" tabindex="-1" aria-disabled="true">待收貨</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="/CFA101G4/front-end/Member/checkPRordersFinish.jsp" tabindex="-1" aria-disabled="true">完成</a>
	                      </li>
	                      <li class="nav-item">
	                        <a class="nav-link" href="/CFA101G4/front-end/Member/checkPRordersCancel.jsp" tabindex="-1" aria-disabled="true">已取消</a>
	                      </li>
	                      <li class="nav-item">
	                        <a class="nav-link" href="/CFA101G4/front-end/Member/checkPRordersReturn.jsp" tabindex="-1" aria-disabled="true">退貨訂單</a>
	                      </li>
	                  </ul>
	            </div>
	            <div class="col p-2" style="margin-bottom: 15px!important;">
	                <div class="input-group">
	                    <span class="input-group-text" id="basic-addon1"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
	                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
	                      </svg></span>
	                    <input type="text"  style="background-color: #d3d3d3" class="form-control" placeholder="您可以透過賣家名稱、訂單編號或商品名稱搜尋" aria-label="Username" aria-describedby="basic-addon1">
	                </div>
	            </div>
	           <div id="ajaxchange"> 
		           
            	</div>
            
        </div>
    </div> 
  </div>












<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/Preproduct-js/orders-js/checkPRordersShip.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

