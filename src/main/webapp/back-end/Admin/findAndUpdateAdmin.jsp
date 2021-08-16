<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Back-end-css/Admin-css/findAndUpdataAdmin.css">
<title>Insert title here</title>
</head>
<jsp:useBean id="AdminAutAll" scope="page" class="com.admin_authority.model.AdminAuthorityService" />  	
<jsp:useBean id="AutAll" scope="page" class="com.authority.model.AuthorityService" />					
<body>
<div class="modal fade" id="updataMain" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
				
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel">編輯管理員帳號</h3>
            </div>
			
			<div class="modal-body">
	<form id="form">
		<table>
        <tr><th>編號</th><td><input type="text" id="adminid" name="adminid" value="${AdminVO.admin_id }" readonly></td></tr>
        <tr><th>帳號</th><td><input type="text" id="adminacc" name="adminacc" value="${AdminVO.admin_acc }"></td></tr>
        <tr class="accerrortr"><td></td><td class="accerror"></td></tr>
        <tr><th>密碼</th><td><input type="text" id="adminpw" name="adminpw" value="${AdminVO.admin_pw }"></td></tr>
        <tr class="pwerrortr"><td></td><td class="pwerror"></td></tr>
        <tr><th>最後登入時間
        </th><td>
        	<c:if test="${AdminVO.admin_log != null }">
        	<input type="text" name="adminlog" id="adminlog" value="<fmt:formatDate value="${AdminVO.admin_log }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
            </c:if>
            <c:if test="${AdminVO.admin_log == null }">
            <input type="text" name="adminlog" id="adminlog" value="尚未登入過" readonly/>
            </c:if>
        </td></tr>
        <tr><th>狀態</th>
        <td>
        <select name="adminsta">
        <option value="0" ${AdminVO.admin_sta == 0 ? "selected" : ""}>禁用</option>
        <option value="1" ${AdminVO.admin_sta == 1 ? "selected" : ""}>啟用</option>
        </select>
        </td>
        </tr>
        </table>
        <table>
        <caption>權限配置</caption>
        <tr>
        	<th>編號</th>
        	<th>選取</th>
        	<th>權限名稱</th>
        	<th>權限說明</th>
        </tr>
        <c:forEach var="Aut" items="${AutAll.all }" >
					<tr>
					<td>
					${Aut.aut_id }
					</td>
					<td>
						<input type="checkbox" class="autbox" value="${Aut.aut_id}" name="${AdminVO.admin_id }" 
						
						<c:forEach var="AdAut" items="${AdminAutAll.getone(AdminVO.admin_id)}">
						${AdAut.aa_autid == Aut.aut_id ? "checked" : "" }
						</c:forEach>
						>
					</td>
					<td>
						${Aut.aut_name }
					</td>
					<td>
						${Aut.aut_con }
					</td>
					</tr>		
		</c:forEach>
    	</table>
    	<input type="hidden" name="action" value="updata">
    	</form>
    	<div class="modal-footer">
        <button id="submit" name="action" value="updata" class="btn btn-primary">修改</button><input  class="btn btn-default" id="cancel" type="button" value="關閉" data-dismiss="modal">
        </div>
    		</div>
		</div>
	</div>
</div>
<script>
    		 $("#updataMain").modal({show: true});
</script>
    
<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Back-end-js/Admin-js/findAndUpdateAdmin.js"></script>
</body>
</html>