$(function(){
	
	$("#newPW").on("input", checkNewPassword);
	
	
	function checkNewPassword(){
		
        let password = $("#newPW").val();
        let reg_password = /^\w{5,15}$/;            
        let flag = reg_password.test(password);
        if(flag){
        	$("#response1").css("color", "green")
        	$("#response1").html("此密碼符合規範")
        }else{
        	$("#response1").css("color", "red")
        	$("#response1").html("密碼請輸入中英文數字5至15位！")
        }
        return flag;
    }
	
	
	function repeat(){
		var repeatPW = $("#confirmPW").val()
		var password = $("#newPW").val();
		console.log(password)
		var flag = true;
		if(repeatPW==password){
			flag=true;	
			return flag;
		}else{
			flag=false;
			return flag;
		}
	}
	
	
	
	
        $("#submit").click(function(){  
        	
        	if(repeat() && checkNewPassword()){
        		console.log(repeat())
        		console.log(checkNewPassword())
        		let password = $("#newPW").val();
        		$.ajax({
                    url:"/CFA101G4/MemberServlet",
                    type:"POST",
                    data: {
                    	"action":"updateMemPW",
                    	"mem_pw":password
                    },
                    success:function(data){
                        console.log(data);
                        swal({
                            title: '密碼修改成功！',
                            text: '下次別再忘記囉~ 為您跳轉登錄畫面唷！',
                            type: 'success', 
                            confirmButtonColor: '#d33', 
                            confirmButtonText: '確定！',
                        }).then(function(value) {
                            if (value.value) {
                            	 window.location.assign("/CFA101G4/front-end/login.jsp");
                            } 
                    })
   
                 }
              });
        	}else{
        		sweetAlert(
        				  '有誤！',
        				  '請再次確認您的密碼是否正確填寫',
        				  'error'
        				)
        	}

        });
   });