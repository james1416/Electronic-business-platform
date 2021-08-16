<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pd.model.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">
<title>修改商品資訊</title>

</head>
<body>
    
<jsp:useBean id="pis" scope="page" class="com.pi.model.PiService" />


    <!-- 網格系統開頭 -->
    <div>
        <div class="container-fluid">
            <!-- row開頭 -->
            <div class="row">

                <!-- 賣家中心左側 -->
                <div class="col-2">
                    <div class="flex-shrink-0 p-3 bg-white position-sticky top-0" style="width: 200px;">
                        <a href="/CFA101G4/front-end/Member/sellCenter.jsp"
                            class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
                            <svg class="bi me-2" width="30" height="24">
                                <use xlink:href="#bootstrap"></use>
                            </svg>
                            <span class="fs-5 fw-semibold">賣家中心</span>
                        </a>
                        <ul class="list-unstyled ps-0">
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#home-collapse" aria-expanded="false">
                                    商品管理
                                </button>
                                <div class="collapse" id="home-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/PreProduct/selectSelfPrePd.jsp">商品總覽</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="<%=request.getContextPath()%>/front-end/PreProduct/addPrePd.jsp">新增商品</a></li>
                                        <li class="list-group-item border-0">查看遭檢舉商品</li>
                                    </ul>
                                </div>
                            </li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#dashboard-collapse"
                                    aria-expanded="false">
                                    訂單管理
                                </button>
                                <div class="collapse" id="dashboard-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/OrdersServlet?action=findSOD">查看待確認訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectCheckShip.jsp">查看待出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectCancelOrder.jsp">查看已取消訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/Orders/selectAlreadyShipped.jsp">查看已出貨訂單</a></li>
                                        <li class="list-group-item border-0"><a class="nav-link link-dark p-0"  href="/CFA101G4/front-end/PreProduct/selectAlreadyShip.jsp">查看已完成訂單</a></li>
                                    </ul>
                                </div>
                            </li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="false">
                                    營收相關
                                </button>
                                <div class="collapse" id="orders-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0">查看營收</li>
                                        <li class="list-group-item border-0">A second item</li>
                                    </ul>
                                </div>
                            </li>
                            <li class="border-top my-3"></li>
                            <li class="mb-1">
                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse" data-bs-target="#account-collapse" aria-expanded="false">
                                    平台規則
                                </button>
                                <div class="collapse" id="account-collapse">
                                    <ul class="list-group list-group-flush mt-2">
                                        <li class="list-group-item border-0">查看更多</li>
                                        <li class="list-group-item border-0">A second item</li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- 賣家中心左側 -->
                </div>

                <div class="col">
                    <div class="row">
                        <div class="col-12 text-center">
                            <div class="row" id="imgbody">
                                <!-- 放預覽圖的位置開頭 -->
                            <!-- 
                            <div class="col-2 border border-$gray-500 mt-1" style="border-radius: 20px;margin-left: 5px;">
                                <div class="col text-end">
                                      <button type="button" class="btn-close" id="del" aria-label="Close"></button>
                                </div>
                                <div class="col position-relative border-0">
                                      <img src="`+result +`" class="img-thumbnail w-100 border-0" alt="...">
                                </div>
                           </div>
                         -->
                            <!-- 放預覽圖的位置結尾 -->
                            </div>
                        </div>
                    </div>
                    <div class="row m-2 border border-$gray-500 text-center" style="border-radius: 50px;">
                        <div class="col-7 text-end">
                            <p class="fs-1 p-0 m-0">商品修改</p>
                        </div>
                        <div class="col-5 mt-2">
                                <input type="file" id="i1" name="piimage" multiple="multiple" style="display: none;">
                                <input type="button" id="upimg" value="新增圖片" class="btn btn-secondary btn-lg m-0">
                        </div>
                        <div class="col-12">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品編號</span>
                                <input type="text"  name="PD_ID" id="PD_ID" value="${pdVO.pd_id}" class="form-control" aria-label="Sizing example input"
                                    aria-describedby="inputGroup-sizing-lg" readonly>
                            </div>
                        </div>
                        

                        <jsp:useBean id="pcSvc" scope="page" class="com.pc.model.PcService" />
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品名稱</span>
                                <input type="text" name="PD_NAME" id="PD_NAME" value="${pdVO.pd_name}" class="form-control" aria-label="Sizing example input"
                                    aria-describedby="inputGroup-sizing-lg">
                            </div>
                        </div>
                        
                        
                        <!--分類 -->
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品分類</span>
                                <select name="PD_PCID" id="PD_PCID" class="form-select form-select-lg" aria-label=".form-select-lg example">
                                   <c:forEach var="pcVO" items="${pcSvc.all}">
									<option value="${pcVO.pc_id}"
									${(pdVO.pd_pcid==pcVO.pc_id)?'selected':'' }>${pcVO.pc_desc}
								   </c:forEach>
                                </select>
                            </div>
                            
                        </div>


                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                                <span class="input-group-text" id="inputGroup-sizing-lg">商品介紹</span>
                                <textarea class="form-control" name="PD_DESC" id="PD_DESC" aria-label="With textarea" >${pdVO.pd_desc}</textarea>
                            </div>
                        </div>
                        <div class="col-12 mt-3">
                            <div class="input-group input-group-lg w-50 m-auto">
                            	<span class="input-group-text" id="inputGroup-sizing-lg">商品狀態</span>
                                	<select id="PD_STA" name="PD_STA" class="form-select form-select-lg" aria-label=".form-select-lg example">
                                    	<option value="0" >熱賣中
                                    	<option value="1" >下架商品(若無人購買才可以下架)

                                	</select>
                            </div>
                        </div>
                        <!-- 現有圖片預覽開頭 -->
                        <div class="row justify-content-center">
                            <div class="col-12 mt-3"><p class="fs-1">現有圖片</p></div>
                            
                            <div class="row justify-content-center">
                            <c:forEach var="img" items="${pis.getall(pdVO.pd_id)}">
                                <div class="col-2 border border-$gray-500 mt-1" style="border-radius: 20px;margin-left: 5px;">
                                    <div class="col text-end">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="del" value="${img.pi_imgid }" id="del${img.pi_imgid }">
                                            <label class="form-check-label" for="del${img.pi_pdid}">
                                            刪除
                                            </label>
                                          </div>
                                    </div>
                                    <div class="col position-relative border-0">
                                        <img src="data:image/gif;base64, ${pis.out(img.pi_desc)}" class="img-thumbnail w-100 h-75 border-0" alt="...">
                                    </div>
                                </div>
                             </c:forEach>
                            </div>

                      </div>
                        <!-- 現有圖片預覽結尾 -->
                        <div class="col-12 mt-3 mb-3">
                        	<button value="updata" name="action" id="update" style="display:none">修改</button>
                            <input type="button" value="修改"  id="submit" class="btn btn-secondary btn-lg">
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- 網格系統結尾 -->
    </div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/CFA101G4/JS/Front-end-js/Preproduct-js/update_pd_input.js"></script>
</body>
</html>