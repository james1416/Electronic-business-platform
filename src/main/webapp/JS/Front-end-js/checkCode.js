$(function(){
		$("#changeImg").on("click", function(){
			let date = new Date().getTime();
			console.log(date);
			$("#img").prop("src", "/CFA101G4/CheckCodesServlet?"+date);
		})
	
        $("#submit").click(function(){  
        	let code = $("#floatingInput").val();
            $.ajax({
                url:"/CFA101G4/MemberServlet",
                type:"GET",
                data: {
                	"action":"checkCode",
                	"code":code
                },
                success:function(data){
                	console.log(data);
                	if(data==1){
                		window.location.assign("/CFA101G4/front-end/sendEmail.html")
                	}else{
                		$("#response").html("您的驗證碼輸入錯誤！請重新輸入！")
                	}
                }
            });
           
        });
   });