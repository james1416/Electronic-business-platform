	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<title>Insert title here</title>
</head>
<body>


  <div class="container-fluid">

    
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


      <div class="col-10" id="LNbody">

        <div class="row m-2 p-0">
        	<div class="col align-self-center">
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">新增消息</button>
        </div>

        <div class="row justify-content-center mt-3">
          <div class="col-1 text-center popover-header">
           	 編號
          </div>
          <div class="col-7 text-center popover-header">
    		消息內文
          </div>
          <div class="col-2 text-center popover-header">
           	 新增時間
          </div>
          <div class="col-1 text-center popover-header">
			狀態
          </div>
          <div class="col-1 text-center popover-header">
			操作
          </div>
        </div>

        <!-- 每一列的開頭 -->

        
        <!-- 每一列的結尾 -->
        <div id="moremsg" class="row mt-3 mb-3">
				<div class="col-12 text-center">往下拉載入更多資料</div>
		</div>
      </div>
    </div>
  </div>
  
</div>

<form method="post" action="<%=request.getContextPath()%>/Latest_newsServlet">
			<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">新增消息</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			       <div class="row mb-3">
                      <div class="col">
                           <div  class="input-group">
                               <span class="input-group-text">內容</span>
                               <textarea  id="newText" name="lncon" class="form-control"
                                                        aria-label="With textarea"></textarea>
                   			</div>
                       </div>
                    </div>
			      </div>
			      <div class="modal-footer">
			      	<button type="button" name="action" id="submit" value="add" class="btn btn-primary">新增</button>
			      	<button name="action" id="go" value="add" class="btn btn-primary" style="display:none">新增</button>
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
			      </div>
			    </div>
			  </div>
			</div>
</form>


<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/LatestNews-js/selectLN.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>