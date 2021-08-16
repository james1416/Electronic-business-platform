<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.5.0.js"
        integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="icon" href="<%=request.getContextPath()%>/Img/all.png" type="image/x-icon">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/Front-end-css/signin.css">
    <title>會員登入</title>



    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
  </head>
  <body class="text-center">
    
<main class="form-signin">
  <form id="form">
    <img class="mb-4 img-thumbnail bg-transparent border-0 pe-3" src="<%=request.getContextPath()%>/Img/all.png" alt="" >
    <h1 class="h3 mb-3 fw-normal text-success">會員登入</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" placeholder="Account" name="account">
      <label for="floatingInput">帳號</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
      <label for="floatingPassword">密碼</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <!-- 記住我看你要不要加 -->
        <!-- <input type="checkbox" value="remember-me" class="p-2"> 記住我 -->
        <a href="<%=request.getContextPath()%>/front-end/forgetPW.html" class="text-secondary p-2">忘記密碼</a>
        <a href="<%=request.getContextPath()%>/front-end/NewRegister.html" class="text-secondary p-2">註冊會員</a>
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-outline-dark" type="button" id="submit">登入</button>
    <p class="mt-2 text-danger" id="response"></p>
    <p class="mt-4 mb-3 text-muted">&copy; TIBAME-2021</p>
  </form>
</main>

<script type="text/javascript" src="<%=request.getContextPath()%>/JS/Front-end-js/login.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
crossorigin="anonymous"></script>
  </body>
</html>
    