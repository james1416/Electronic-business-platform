<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
 <form method = "post" action = "<%=request.getContextPath()%>/QAListServlet">
	<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">新增Q&A</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="container">
                                       
                                        <div class="row mb-3">
                                            <div class="col">
                                                <div class="input-group">
                                                    <span class="input-group-text">問題</span>
                                                    <textarea name="qcon" class="form-control"
                                                        aria-label="With textarea">${addVO.qal_qcon == null ? '' : addVO.qal_qcon }</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col">
                                                <div  class="input-group">
                                                    <span class="input-group-text">回答</span>
                                                    <textarea  name="acon" class="form-control"
                                                        aria-label="With textarea">${addVO.qal_acon == null ? '' : addVO.qal_acon }</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${not empty adderrorMsgs}">
                                            <c:forEach var="message" items="${adderrorMsgs}">
                                            <div class="row text-center">
											<p class="h5" style="color: red;">${message}</p>
                                        	</div>
                                            </c:forEach>
                                        </c:if>
                                       
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button name="action" value="add" class="btn btn-primary">新增</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>

                                </div>
                            </div>
                        </div>
                    </div>
</form>

</body>
</html>