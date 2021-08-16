<%@page import="com.admin.model.AdminVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<title>Document</title>
</head>
<body>






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
          <div class="col-1 text-center popover-header">
            編號
          </div>
          <div class="col-2 text-center popover-header">
            帳號
          </div>
          <div class="col-2 text-center popover-header">
            密碼
          </div>
          <div class="col-3 text-center popover-header">
            最後登入時間
          </div>
          <div class="col-2 text-center popover-header">
            管理員狀態
          </div>
          <div class="col-2 text-center popover-header">
            管理
          </div>
        </div>
<jsp:useBean id="AdminAll" scope="page" class="com.admin.model.AdminService" />
<jsp:useBean id="AdminAutAll" scope="page" class="com.admin_authority.model.AdminAuthorityService" />  	
<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />
<c:forEach var="admin" items="${AdminAll.all}">
        <!-- 每一列的開頭 -->
        <div class="row justify-content-center border-bottom border-$gray-500 p-3">
          <div class="col-1 text-center align-self-center">
            ${admin.admin_id}
          </div>
          <div class="col-2 text-center align-self-center">
            ${admin.admin_acc}
          </div>
          <div class="col-2 text-center align-self-center">
            ${admin.admin_pw}
          </div>
          <div class="col-3 text-center align-self-center">
          <fmt:formatDate value="${admin.admin_log }" pattern="yyyy-MM-dd HH:mm:ss"/>
          <c:if test="${admin.admin_log == null }">
            	尚未登入過
          </c:if>
          </div>
          <div class="col-2 text-center align-self-center">
           ${admin.admin_sta == 0 ? "禁用" : "啟用"}
          </div>
          <div class="col-2 text-center align-self-center">
            <!-- Button trigger modal -->
            <button type="button" value="${admin.admin_id }"  class="btn btn-primary m-0" data-bs-toggle="modal" data-bs-target="#updata${admin.admin_id }">
              	修改
            </button>
          </div> 
          <!-- Modal -->
          <form id="form${admin.admin_id }" style="margin:0px">
          <div class="modal fade" id="updata${admin.admin_id }" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
            aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="staticBackdropLabel">管理員資料修改</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="updatabody">
                  <div class="row mb-4">
                    <div class="col">
                      <div class="container-fluid">
                        <div class="input-group ">
                          <span class="input-group-text" id="inputGroup-sizing-default">編號</span>
                          <input type="text" name="adminid" value="${admin.admin_id }" class="form-control" aria-label="Sizing example input"
                            aria-describedby="inputGroup-sizing-default" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  

                  <div class="row">
                    <div class="col">
                      <div class="container-fluid">
                        <div class="input-group">
                          <span class="input-group-text" id="inputGroup-sizing-default">帳號</span>
                          <input type="text" class="form-control" id="adminacc${admin.admin_id }" value="${admin.admin_acc }" name="adminacc" aria-label="Sizing example input"
                            aria-describedby="inputGroup-sizing-default">
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row p-0 m-0 mb-4" >
                    <div class="col text-center m-0 p-0" id="accerror${admin.admin_id }">
                    </div>
                  </div>

                  <div class="row mb-4">
                    <div class="col">
                      <div class="container-fluid">
                        <div class="input-group">
                          <span class="input-group-text" id="inputGroup-sizing-default">密碼</span>
                          <input id="adminpw${admin.admin_id }" name="adminpw" value="${admin.admin_pw }" type="text" class="form-control" aria-label="Sizing example input"
                            aria-describedby="inputGroup-sizing-default">
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <div class="row p-0 m-0 mb-4" >
                    <div class="col text-center m-0 p-0" id="pwerror${admin.admin_id }">
                    </div>
                  </div>

                  <div class="row mb-4">
                    <div class="col">
                      <div class="container-fluid">
                        <div class="input-group">
                          <span class="input-group-text">最後登入時間</span>
                          
                          	<c:if test="${admin.admin_log != null }">
                          	<input type="text" name="adminlog" class="form-control" aria-label="Sizing example input"
                            aria-describedby="inputGroup-sizing-default" value="<fmt:formatDate value="${admin.admin_log }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
				            </c:if>
				            <c:if test="${admin.admin_log == null }">
				            <input type="text" name="adminlog"  value="尚未登入過" class="form-control" aria-label="Sizing example input"
                            aria-describedby="inputGroup-sizing-default" readonly>
				            </c:if>
                          

                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row mb-4 border-$gray-500 border-bottom">
                    <div class="col mb-3">
                      <div class="container-fluid">
                        <div class="input-group ">
                          <span class="input-group-text" >狀態</span>
                          
                          <select name="adminsta" class="form-select" aria-label="Default select example">
                            <option value="0" ${admin.admin_sta == 0 ? "selected" : ""}>禁用</option>
        					<option value="1" ${admin.admin_sta == 1 ? "selected" : ""}>啟用</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>






                  <div class="row">
                    <div class="col text-center"><p class="h3">權限配置</p></div>
                  </div>

                  
                  <div class="row border-bottom border-$gray-500">
                    <div class="col-2 text-center ">編號</div>
                    <div class="col-2 text-center">選取</div>
                    <div class="col-3 text-center">權限名稱</div>
                    <div class="col-5 text-center">權限說明</div>
                  </div>
        	<c:forEach var="Aut" items="${AutAll.all }" >
                  <div class="row mt-2 row border-bottom border-$gray-500">
                    <div class="col-2 text-center pb-2">
					${Aut.aut_id }
                    </div>
                    <div class="col-2 text-center pb-2">
                       <input type="checkbox"  class="form-check-input" class="autbox" value="${Aut.aut_id}" name="${admin.admin_id }" 
						
						<c:forEach var="AdAut" items="${AdminAutAll.getone(admin.admin_id)}">
						${AdAut.aa_autid == Aut.aut_id ? "checked" : "" }
						</c:forEach>
						>
                    
                    
                    
                    </div>
                    <div class="col-3 text-center pb-2">
						${Aut.aut_name }
                    </div>
                    <div class="col-5 text-center pb-2">
						${Aut.aut_con }                    
					</div>
                  </div>
			</c:forEach>


                </div>
                <div class="modal-footer">
                  <input type="hidden" name="action" value="updata">
                  <button type="button" id="submit${admin.admin_id }" value="${admin.admin_id }" class="btn btn-primary">修改</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                </div>
              </div>
            </div>
          </div>
           <!-- Modal -->
          </form>
          
          
          
        </div>
        <!-- 每一列的結尾 -->
</c:forEach>
      </div>
    </div>
  </div>



<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/Admin-js/findAndUpdateAdmin.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>