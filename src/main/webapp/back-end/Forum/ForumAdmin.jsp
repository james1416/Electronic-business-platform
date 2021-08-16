<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc="
    crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<!-- 	上方為sweetalert -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
  <link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
  <title>討論區管理</title>

</head>
<style>
body{
	background-image: url(/CFA101G4/Img/bg01.jpg);
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
	background-size: cover;
}
  tr#ss>td {
    padding: 0px;
  }

  tbody>tr:hover {
    background-color: rgba(179, 178, 178, 0.877);
  }
table{
table-layout:fixed;word-break:break-all;
}
td#dd{
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden; 
	text-overflow: ellipsis;
	}
td#ss{
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden; 
	text-overflow: ellipsis;
	}
</style>

<body>
  <div class="container mt-5">
    <h3 style="color:rgb(139, 139, 139)">討論區檢舉管理</h3>
    <div class="row">
      <div class="col-2"><input type="text" class="form-control" placeholder="輸入關鍵字" aria-label="Username"
          aria-describedby="basic-addon1" id="searchUSR"></div>
      <div class="col-2">
        <select class="form-select" aria-label="Default select example" id="checkfn">
          <option value="0"selected>請選擇</option>
          <option value="待審核">待審核</option>
          <option value="審核已通過">通過</option>
          <option value="未通過">未通過</option>
        </select>
      </div>
      <div class="col-2" id="searchtotal">		
      </div>
      <div class="col" id="total">
        
      </div>
      <div class="col">
        <button type="button" class="btn btn-outline-secondary">回管理首頁</button>
      </div>
      <div class="col text-end">
       <button id="nowpage" type="button" class="btn btn-secondary">第1頁</button>
      </div>
    </div>
  </div>


  <div class="container mt-2 mb-3"  style="border: 1px solid black; border-radius: 10px; background-color: white; opacity: 0.88">
    <table class="table text-center table-sm" id="mytable">
      <thead>
        <tr>
          <th scope="col" id="member">被檢舉人</th>
          <th scope="col">被檢舉原因</th>
          <th scope="col">檢舉時間</th>
          <th scope="col">審核狀態</th>
          <th scope="col">審核處理</th>
        </tr>
      </thead>
      <tbody id="tbody">
      </tbody>
    </table>
    <div class="text-center mb-2">
    	<div class="pagination-container">
    		<nav aria-label="Page navigation example">
		      <ul class="pagination justify-content-center position-absolute bottom-0 start-50 translate-middle-x" id="pagination">
		      </ul>
		    </nav>
		  </div>
    </div>
  </div>  

  <div class="modal fade" id="detailed" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">檢舉詳情</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body p-1" >
        		檢舉原因描述
				<div class="col ps-2" id="reptext" style="text-indent:1em"></div>
				
				<div class="col p-2 bg-secondary" id="repattext">
				</div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
	
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
    crossorigin="anonymous"></script>
  <script src="${pageContext.request.contextPath}/JS/Back-end-js/Forum-js/ForumAdmin.js"></script>
</body>


</html>