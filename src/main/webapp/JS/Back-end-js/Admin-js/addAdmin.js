let flag = false;
let checkAcc = false;
let checkPw = false;
$("#adminacc").on("input",function(){
                    let acc = $("#adminacc").val();   
                    let check = /^\w{8,32}$/;
                    if ( !check.test(acc)){
                        $("#adminacc").css("border","2px solid red");
                        $(".accerror").html("請輸入8~32個英文與數字的組合")
                        $(".accerror").css("color","red");
                        checkAcc = false;
                    }
                    else{
                        
                        $.ajax({
                            url:"/CFA101G4/AdminServlet",
                            data:{action : "checkAcc" ,
                            	adminacc : $("#adminacc").val()
                            },
                            success:function(data){
                            	if(data === "true"){
                            		$("#adminacc").css("border","2px solid red");
                            		$(".accerror").html("此帳號已重複")
                            		$(".accerror").css("color","red");
                            		checkAcc = false;
                            	}
                            	else{
                            		$("#adminacc").css("border","2px solid green");
                            		$(".accerror").css("color","green");
                            		$(".accerror").html("此帳號可以使用")
                            		checkAcc = true;
                            	}
                            }
                        })
                    }
              })
$("#adminpw").on("input",function(){
                    let pw = $("#adminpw").val();   
                    let check = /^\w{8,32}$/;
                    if ( !check.test(pw)){
                        $("#adminpw").css("border","2px solid red");
                        $(".pwerror").html("請輸入8~32個英文與數字的組合")
                        $(".pwerror").css("color","red");
                        checkPw = false;
                    }
                    else{
                    	$("#adminpw").css("border","2px solid green");
                		$(".pwerror").html("此密碼可以使用")
                		$(".pwerror").css("color","green");
                		checkPw = true;
                    }
              })
              
$("#submit").on("click",function(){
	if(checkAcc === true && checkPw === true){
		$.ajax({
			url:"/CFA101G4/AdminServlet",
			data:$("#form").serialize(),
			success:function(){
				window.location.href="/CFA101G4/back-end/Admin/selectAllAdmin.jsp";
			}
		
		})
	}else{
		swal(
				"錯誤囉",
				"請輸入正確的格式",
				"error"
				)
	}
})


$("#cancel").on("click",function(){
		window.location.href="/CFA101G4/back-end/Admin/selectAllAdmin.jsp";
})