let email = sessionStorage.getItem("mem_email");
$("#email").html(email);


            $.ajax({
                url:"/CFA101G4/MemberServlet",
                type:"POST",
                data: {
                	"action":"sendEmailForNewPW",
                	"email":email
                },
                success:function(data){
                	console.log(data);
                	$("#submit").on("click", function(){
                		window.location.assign("/CFA101G4/front-end/login.jsp");
                	})
                }
            });
           
       
