<%@page import="com.qa_list.model.QAListVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h1 class="modal-title" id="myModalLabel">編輯Q&A</h1>
            </div>
			<div class="modal-body">

	<div class="login">
        <form method = "post" action = "<%=request.getContextPath()%>/QAListServlet">
        <div class="qalid"><p>Q&A編號<input type="text" name="qal_id" readonly value="${QAListVO.qal_id }" class="qaid"></p></div>
        <h2>問題內容</h2>
        <textarea name="qal_qcon" cols="30" rows="10" class="qatextarea">${QAListVO.qal_qcon }</textarea>
        <h2>回答內容</h2>
        <textarea name="qal_acon" cols="30" rows="10" class="qatextarea">${QAListVO.qal_acon }</textarea>
        
        <table>
        <tr class="qaltsp"><th><p>最後更動日期為</p></th><td><input type="text" class="qaltspinput" name="qal_tsp" readonly value="${QAListVO.qal_tsp }"></td></tr>
        <tr class="qalsta"><th><p>狀態</p></th><td><select name="qal_sta">
            <option value="0" ${QAListVO.qal_sta == 0 ? "selected" : ""}>隱藏中</option>
            <option value="1" ${QAListVO.qal_sta == 1 ? "selected" : ""}>顯示中</option>
        </select></td></tr>
        </table>
        <c:if test = "${not empty done }" >
        	<p style="color:green">
        	<c:forEach var="done" items="${done}">
        		${done}
        	</c:forEach>
        	</p>
        </c:if>
		<c:if test = "${not empty updataErrorMsgs }">
			<ul>
			<c:forEach var="message" items="${updataErrorMsgs}">
				<li style="color:red">${message}</li> 
			</c:forEach>
			</ul>
		</c:if>
		<div class="modal-footer">
        <button id="submit" name= "action" value="updata" class="btn btn-primary">修改</button><input  class="btn btn-default" id="cancel" type="button" value="關閉" data-dismiss="modal">
    	</div></form>
    </div>
    		</div>
		</div>
	</div>
</div>
        <script>
    		 $("#basicModal").modal({show: true});
        </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/Q&AList-js/findByPKQ&AList.js"></script>
</body>
</html>