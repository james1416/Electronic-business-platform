$(function(){
        $("#submit").click(function(){  
            $.ajax({
                url:"/CFA101G4/loginServlet",
                type:"POST",
                data: $("#form").serialize(),
                success:function(data){
                    console.log(data);
                  if(data==true){
                	  location.href="/CFA101G4/front-end/FrontMain.jsp";               	
                  }else{
                	  $("#response").html("您的帳號或密碼可能有誤！")
                  }
                }
            });
           
        });
   });