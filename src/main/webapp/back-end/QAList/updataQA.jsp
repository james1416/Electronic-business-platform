<%@page import="com.qa_list.model.QAListVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Insert title here</title>
</head>
<body>
 <form method = "post" action = "<%=request.getContextPath()%>/QAListServlet">
<div class="modal fade" id="updata" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">編輯Q&A</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col">
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text" id="basic-addon1">Q&A編號</span>
                                                    <input  name="qal_id" value="${QAListVO.qal_id }" type="text" class="form-control" aria-label="Username"
                                                        aria-describedby="basic-addon1" readonly>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col">
                                                <div class="input-group">
                                                    <span class="input-group-text">問題</span>
                                                    <textarea name="qal_qcon" class="form-control"
                                                        aria-label="With textarea">${QAListVO.qal_qcon }</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        
                                        <div class="row mb-3">
                                            <div class="col">
                                                <div class="input-group">
                                                    <span class="input-group-text">回答</span>
                                                    <textarea name="qal_acon" class="form-control"
                                                        aria-label="With textarea">${QAListVO.qal_acon }</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        

                                        <div class="row">
                                            <div class="col">
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text" id="basic-addon1">最後更動日期</span>
                                                    <input type="text" name="qal_tsp" value="${QAListVO.qal_tsp }" class="form-control" aria-label="Username"
                                                        aria-describedby="basic-addon1" readonly>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col">
                                                <div class="input-group mb-3">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text"
                                                            for="inputGroupSelect01">狀態</label>
                                                        <select name="qal_sta" class="form-select">
                                                        	<option value="0" ${QAListVO.qal_sta == 0 ? "selected" : ""}>隱藏中</option>
            												<option value="1" ${QAListVO.qal_sta == 1 ? "selected" : ""}>顯示中</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test = "${not empty done }" >
                                <div class="row text-center">
							        	<p style="color:green">
							        	<c:forEach var="done" items="${done}">
							        		${done}
							        	</c:forEach>
							        	</p>
							    </div>
							    </c:if>
                                <c:if test = "${not empty updataErrorMsgs }">
                                
										<c:forEach var="message" items="${updataErrorMsgs}">
										<div class="row text-center">
										<p class="h5" style="color: red;">${message}</p>
										</div>
										</c:forEach>
								
								</c:if>
                                <div class="modal-footer">
                                    <button name="action" value="updata" class="btn btn-primary">修改</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>

                                </div>
                            </div>
                        </div>
                    </div>
             </form>
                    
<script>
	var myModal = new bootstrap.Modal(document.getElementById("updata"), {});
	document.onreadystatechange = function () {
	myModal.show();
	};
</script>
</body>
</html>