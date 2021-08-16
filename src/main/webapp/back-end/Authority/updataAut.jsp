<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/Authority-css/addAut.css">
<title>Insert title here</title>
</head>
<body>
	<div class="modal fade" id="updataAut" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h1 class="modal-title" id="myModalLabel">修改權限</h1>
            	</div>
            	<form method="post" action="<%=request.getContextPath()%>/AuthorityServlet">
				<div class="modal-body">
					<div class="autname">
    					權限編號<input class="autid" type="text" name="autid" value="${findAutVO.aut_id}" readonly>
					</div>
					<div class="autname">
    					權限名稱<input type="text" name="autname" value="${findAutVO.aut_name}">
					</div>
					<div class="autname">
				    	權限說明<input type="text" name="autcon" value="${findAutVO.aut_con}">
				 	</div>
				 	<c:if test="${not empty updataErrorMsgs }">
				 	<ul>
				 		<c:forEach var="msg" items="${updataErrorMsgs }">
				 				<li style="color:red">${msg}</li>
				 		</c:forEach>
				 	</ul>
				 	</c:if>
    			</div>
    			<div class="modal-footer">
    			<button id="submit" name= "action" value="updata" class="btn btn-primary">修改</button><input  class="btn btn-default" id="cancel" type="button" value="關閉" data-dismiss="modal">
    			</div>
    			</form>
    		</div>
		</div>
	</div>
	<script>
		$("#updataAut").modal({show: true});
	</script>
</body>
</html>