$("button").on("click",function(){
	console.log($(this).val())
	let who = $(this).val()
	let flag = false;
	let checkAcc = true;
	let checkPw = true;
	
	$("#adminacc"+who).on("input",function(){
	                    let acc = $("#adminacc"+who).val();   
	                    let check = /^\w{8,32}$/;
	                    if ( !check.test(acc)){
	                        $("#adminacc"+who).css("border","2px solid red");
	                        $("#accerror"+who).html("請輸入8~32個英文與數字的組合")
	                        $("#accerror"+who).css("color","red");
	                        checkAcc = false;
	                    }
	                    else{
	                        
	                        $.ajax({
	                            url:"/CFA101G4/AdminServlet",
	                            data:{action : "checkAcc" ,
	                            	adminacc : $("#adminacc"+who).val()
	                            },
	                            success:function(data){
	                            	if(data === "true"){
	                            		$("#adminacc"+who).css("border","2px solid red");
	                            		$("#accerror"+who).html("此帳號已重複")
	                            		$("#accerror"+who).css("color","red");
	                            		checkAcc = false;
	                            	}
	                            	else{
	                            		$("#adminacc"+who).css("border","2px solid green");
	                            		$("#accerror"+who).css("color","green");
	                            		$("#accerror"+who).html("此帳號可以使用")
	                            		checkAcc = true;
	                            	}
	                            }
	                        })
	                    }
	              })
	$("#adminpw"+who).on("input",function(){
	                    let pw = $("#adminpw"+who).val();   
	                    let check = /^\w{8,32}$/;
	                    if ( !check.test(pw)){
	                        $("#adminpw"+who).css("border","2px solid red");
	                        $("#pwerror"+who).html("請輸入8~32個英文與數字的組合")
	                        $("#pwerror"+who).css("color","red");
	                        checkPw = false;
	                    }
	                    else{
	                    	$("#adminpw"+who).css("border","2px solid green");
	                		$("#pwerror"+who).html("此密碼可以使用")
	                		$("#pwerror"+who).css("color","green");
	                		checkPw = true;
	                    }
	              })
	 
	$("#submit"+who).on("click",function(){
		
		let acc = $("#adminacc"+who).val();   
        let acccheck = /^\w{8,32}$/;
		
		
        let pw = $("#adminpw"+who).val();   
        let pwcheck = /^\w{8,32}$/;
        
        
        let lastacc = false
        let lastpw = false
        
        if(acccheck.test(acc)){
        	lastacc = true;
        }
        if(pwcheck.test(pw)){
        	lastpw = true;
        }
        
		if(lastacc === true && lastpw === true){
			$.ajax({
				url:"/CFA101G4/AdminServlet",
				data: $("#form"+who).serialize(),
				success:function(){
		             window.location.href="/CFA101G4/back-end/Admin/selectAllAdmin.jsp";
				}
			})
		}else{
			swal("請輸入正確的格式");
		}
	})
	
	
})