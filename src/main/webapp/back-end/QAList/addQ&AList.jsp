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
	<div class="modal fade" id="basicModal1" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h1 class="modal-title" id="myModalLabel">新增Q&A</h1>
            </div>
			<div class="modal-body">




	 <div class="login">
<%-- 	 <%=request.getAttribute("hello") %>  此處可以直接取servletㄉ東西--%>
        <h2>問題</h2>
       
        <textarea name="qcon" cols="30" rows="10" placeholder="請輸入問題" class="qatextarea" >${addVO.qal_qcon == null ? '' : addVO.qal_qcon }</textarea>
        <h2>回答</h2>
        <textarea name="acon" cols="30" rows="10" placeholder="請輸入回答" class="qatextarea">${addVO.qal_acon == null ? '' : addVO.qal_acon }</textarea>
        <c:if test="${not empty adderrorMsgs}">
			<ul>
	    		<c:forEach var="message" items="${adderrorMsgs}">
			<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="modal-footer">
        <button id="submit" name="action" value="add" class="btn btn-primary">新增</button><input  class="btn btn-default" id="cancel" type="button" value="關閉" data-dismiss="modal">
    	</div>
    	
    </div>
    		</div>
		</div>
	</div>
</div>

</form>

</body>
</html>