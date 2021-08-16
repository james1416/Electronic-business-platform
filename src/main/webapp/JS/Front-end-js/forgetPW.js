$(function(){
        $("#submit").click(function(){  
        	let mem_email = $("#floatingInput").val();
            $.ajax({
                url:"/CFA101G4/MemberServlet",
                type:"POST",
                data: {
                	"action":"checkEmail",
                	"email":mem_email
                },
                success:function(data){
                    console.log(data);
                  if(data==1){
                	  console.log(data); 
                	  sessionStorage.setItem("mem_email", mem_email);
                	  window.location.assign("/CFA101G4/front-end/checkCode.html");
                  }else{
                	  $("#response").html("您輸入的Email可能有誤！")
                  }
                }
            });
           
        });
   });