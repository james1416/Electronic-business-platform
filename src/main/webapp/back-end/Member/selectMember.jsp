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

<jsp:useBean id="ms" scope="page" class="com.member.model.MemberService" />


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


        <div class="row justify-content-center mt-3">
          <div class="col-2 text-center popover-header">
            編號
          </div>
          <div class="col-2 text-center popover-header">
          名字
          </div>
          <div class="col-2 text-center popover-header">
            性別
          </div>
          <div class="col-2 text-center popover-header">
            遭檢舉次數
          </div>
          <div class="col-2 text-center popover-header">
            狀態
          </div>
          <div class="col-2 text-center popover-header">
            操作
          </div>
        </div>

        <!-- 每一列的開頭 -->
<c:forEach var="ms" items="${ms.all}" >

        <div class="row justify-content-center border-bottom border-$gray-500 p-3">
          <div class="col-2 text-center align-self-center">
            ${ms.mem_id}
          </div>
          <div class="col-2 text-center text-truncate align-self-center">
            ${ms.mem_name}
          </div>
          <div class="col-2 text-center text-truncate align-self-center">
            ${ms.mem_sex}
          </div>
          <div class="col-2 text-center align-self-center">
            ${ms.mem_rp}
          </div>
          <div class="col-2 text-center align-self-center">
            ${ms.mem_sta == 0 ? "停權中":"啟用中"} 
          </div>
          <div class="col-2 text-center align-self-center">
            <!-- Button trigger modal -->
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop${ms.mem_id}">
			 	詳情/修改
			</button>
        </div>
        <!-- Modal -->
			<div class="modal fade" id="staticBackdrop${ms.mem_id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">會員詳情</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <form action="/CFA101G4/MemberServlet" method="get">
			      <div class="modal-body">
			         <div class="container">
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">編號名稱</span>
							  <input type="text" class="form-control" name="mem_id" value="${ms.mem_id}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">會員名稱</span>
							  <input type="text" class="form-control" value="${ms.mem_name}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">身分證</span>
							  <input type="text" class="form-control" value="${ms.mem_uid}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">生日</span>
							  <input type="text" class="form-control" value="${ms.mem_bth}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">性別</span>
							  <input type="text" class="form-control" value="${ms.mem_sex}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">信箱</span>
							  <input type="text" class="form-control" value="${ms.mem_email}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">電話</span>
							  <input type="text" class="form-control" value="${ms.mem_tel}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">帳號</span>
							  <input type="text" class="form-control" value="${ms.mem_acc}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
			        	<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">密碼</span>
							  <input type="password" class="form-control" value="${ms.mem_pw}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
			        	<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">被檢舉次數</span>
							  <input type="text" class="form-control" value="${ms.mem_rp}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
						<div class="col-12">
							<div class="input-group mb-3">
							  <span class="input-group-text" id="basic-addon1">評分</span>
							  <input type="text" class="form-control" value="${ms.mem_point}" aria-label="Username" aria-describedby="basic-addon1" readonly>
							</div>
						</div>
			        	<div class="col-12">
							<select name="mem_sta" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
							  <option value="0" ${ms.mem_sta == 0 ? "selected" : ""}>停權中</option>
							  <option value="1" ${ms.mem_sta == 1? "selected" : ""}>啟用中</option>
							</select>
						</div>
			        </div>
			      </div>
			     
			      <div class="modal-footer">
			        <button name="action" value="changeStaTo0" class="btn btn-primary">修改</button>
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
			      </div>
			       </form>
			    </div>
			  </div>
			</div>
          </div>
        
</c:forEach>
        <!-- 每一列的結尾 -->
      </div>
    </div>
  </div>




	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>