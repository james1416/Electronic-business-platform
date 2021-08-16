<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="QAsvc" scope="page" class="com.qa_list.model.QAListService" />

  <div class="container-fluid border border-$gray-500">

    
    <div class="row">
      <div class="col-2 mt-2 ">
        <div class="flex-shrink-0 p-3 bg-white border border-$gray-500" style="width: 200px; border-radius: 30px;">
          <a href="<%=request.getContextPath()%>/back-end/BackIndex.jsp" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
            <img src="<%=request.getContextPath()%>/Img/all.png" class="rounded float-start" alt="..." style="height: 150px; width: 150px;">
          </a>
          <ul class="list-unstyled ps-0">
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#member" aria-expanded="false">
                會員相關
              </button>
              <div class="collapse" id="member">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Member/selectMember.jsp">會員總覽</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#p" aria-expanded="false">
                一般商品相關
              </button>
              <div class="collapse" id="p">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Report/selectPDRP.jsp">商品檢舉審核</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#pd" aria-expanded="false">
                預購商品相關
              </button>
              <div class="collapse" id="pd">
                <ul class="list-group list-group-flush mt-2">
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#bp" aria-expanded="false">
                競標商品相關
              </button>
              <div class="collapse" id="bp">
                <ul class="list-group list-group-flush mt-2">
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#POD" aria-expanded="false">
                一般訂單相關
              </button>
              <div class="collapse" id="POD">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Report/selectODRP.jsp">訂單檢舉審核</a></li>
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Orders/selectOD.jsp">一般訂單撥 / 退款</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#BPOD" aria-expanded="false">
                預購訂單相關
              </button>
              <div class="collapse" id="BPOD">
                <ul class="list-group list-group-flush mt-2">
                 <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Orders/selectPO.jsp">預購訂單撥 / 退款</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#PDOD" aria-expanded="false">
               競標訂單相關
              </button>
              <div class="collapse" id="PDOD">
                <ul class="list-group list-group-flush mt-2">
                 <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Orders/selectBP.jsp">競標訂單撥 / 退款</a></li>
                </ul>
              </div>
            </li>
            <li class="border-top my-3"></li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#info" aria-expanded="false">
                平台資訊相關
              </button>
              <div class="collapse" id="info">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/QAList/selectALL.jsp">Q&A管理</a></li>
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/LatestNews/selectLN.jsp">最新消息管理</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#forum" aria-expanded="false">
               討論區相關
              </button>
              <div class="collapse" id="forum">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Forum/ForumAdmin.jsp">討論區檢舉</a></li>
                </ul>
              </div>
            </li>
            <li class="border-top my-3"></li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#account-collapse" aria-expanded="false">
                權限相關
              </button>
              <div class="collapse" id="account-collapse">
                <ul class="list-group list-group-flush mt-2">
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Admin/selectAllAdmin.jsp">管理員管理</a></li>
                  <li class="list-group-item border-0"><a class="nav-link link-dark p-0" href="<%=request.getContextPath()%>/back-end/Admin/addAdmin.jsp">新增員工</a></li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
      </div>


      <div class="col-10" id="PDbody">

        <div class="row m-3 p-0">
        	<div class="col	align-self-center">
        		<button type="button" id="add" class="btn btn-primary">新增Q&A</button>
        	</div>
        </div>

        <div class="row justify-content-center mt-3">
          <div class="col-1 text-center popover-header">
            編號
          </div>
          <div class="col-3 text-center popover-header">
            問題
          </div>
          <div class="col-3 text-center popover-header">
            回答
          </div>
          <div class="col-2 text-center popover-header">
            更動日期
          </div>
          <div class="col-2 text-center popover-header">
            狀態
          </div>
          <div class="col-1 text-center popover-header">
            操作
          </div>
        </div>

        <!-- 每一列的開頭 -->
<c:forEach var="QAListVO" items="${QAsvc.all}" >

        <div class="row justify-content-center border-bottom border-$gray-500 p-3">
          <div class="col-1 text-center align-self-center">
            ${QAListVO.qal_id}
          </div>
          <div class="col-3 text-center text-truncate align-self-center">
            ${QAListVO.qal_qcon}
          </div>
          <div class="col-3 text-center text-truncate align-self-center">
            ${QAListVO.qal_acon}
          </div>
          <div class="col-2 text-center align-self-center">
            ${QAListVO.qal_tsp}
          </div>
          <div class="col-2 text-center align-self-center">
            ${QAListVO.qal_sta == 0 ? "隱藏中":"顯示中"} 
          </div>
          <div class="col-1 text-center align-self-center">
            <a href="<%=request.getContextPath()%>/QAListServlet?action=findByPK&qal_id=${QAListVO.qal_id}">修改</a>
          </div>
        </div>
        
</c:forEach>
        <!-- 每一列的結尾 -->
      </div>
    </div>
  </div>




	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<c:if test="${updata!=null}">
	<c:if test="${QAListVO!=null}">
        <jsp:include page="updataQA.jsp" />
    </c:if>
</c:if>
<jsp:include page="addQA.jsp" />
<c:if test="${addVO!=null}">
	<script>
			var modal = new bootstrap.Modal(document.getElementById("addModal"), {});
			document.onreadystatechange = function () {
				modal.show();
			};
	</script>
</c:if>
<script>
	$("#add").on("click",function(){
		console.log("?")
		var modal = new bootstrap.Modal(document.getElementById("addModal"), {});
		console.log("asdsad")
		modal.show();
	})
</script>

</body>
</html>