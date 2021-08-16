<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="atmem" scope="page" class="com.member.model.MemberService" />
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>					
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link href="/CFA101G4/CSS/Forum-css/PostArticle.css" rel="stylesheet" type="text/CSS">
<link rel="icon" href="/CFA101G4/Img/logo1.ico" type="image/x-icon">
<!--TinyMCE帶入 -->
	<script src="https://cdn.tiny.cloud/1/rjegu8kq0f1otjksqumxm0nziyn77gmtn90kxlpqofa53m5v/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<title>天堂鳥討論區</title>
</head>
<body>
    <div class="container" style="margin:50px 0px 80px 100px;">
        <div class="card w-75 p-0" style="height: 810px; margin-left:150px; background-color: white; opacity: 0.88; border-radius: 10px;">
            <div class="card-header ">
                <p class="fs-5 mb-0 text-center">我要發文</p>
            </div>
            <div class="card mb-0" style="max-width: 100%;">
                <div class="row g-0">
                    <div class="col">
                        <div class="card-body pb-0">
                            <div class="row mt-1 mb-1 ms-0 me-0 p-1 align-items-center" style="border: 1px solid rgb(175, 175, 175); border-radius: 10px;">
                                <div class="col-2 text-center" style="border-right: 1px solid rgb(182, 182, 182);">
                                    <h5 class="card-title mb-0 ">文章標題</h5>
                                </div>
                                <div class="col">
                                <input type="text" class="form-control border-0" aria-describedby="basic-addon1" id="atTitle">
                                </div>              
                            </div>
                            <label class="ms-2" id="Titlewarn"></label>
                            <textarea id="mytext" rows="5" cols="30" class="w-100" style="max-height: 550px;"></textarea>
                                <label id="Textwarn"></label>
                            <p class="card-text m-1"><small class="text-muted"></small></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer text-muted text-center">
                <button type="button" class="btn btn-secondary m-1" id="cancel">取消修改</button>
                <button type="button" class="btn btn-secondary m-1" id="Submit" disabled="disabled">提交修改</button>
            </div>
        </div>
    </div>

    	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
		<script src="/CFA101G4/JS/Forum-js/PostArticle.js"></script>
</body>
</html>