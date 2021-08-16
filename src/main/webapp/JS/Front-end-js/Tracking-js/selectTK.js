function init() {
	
	
	$("#CLbody").on("click",function(e){
		
		 
		if(e.target.getAttribute("id") === "del"){
			let i = $(e.target).parent().parent().parent().index();
			console.log($(e.target).parent().parent().parent())
			console.log(i)
			console.log($("div[id='clpid']:eq("+(i-2)+")").text())
			$.ajax({
		        url:"/CFA101G4/TrackingServlet",
		        dataType:"json",
		        data:{
		        	pid : $("div[id='clpid']:eq("+(i-2)+")").text(),
		        	action : "del",
		        },
		        success:function(data){
		        	$(e.target).parent().parent().parent().remove();
		        }
		    })
		}
		
		
	
	})
	
	
	
	
}
window.onload = init;