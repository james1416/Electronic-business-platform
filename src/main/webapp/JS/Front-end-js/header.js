
$("#searchbody").hide();

function addHover(i){
$("#point"+i).hover(function(e){
    $(e.target).css("background-color","grey");
    },function(e){
    $(e.target).css("background-color","white");
  });
} 



$("#searchbody").on("click",function(e){
	
	$("#searchinput").val($(e.target).text())
	$("#searchbody").hide()
})

$("#searchinput").on("input",function(){
	let name = $("#searchinput").val()
	if(name == ""){
		$("#searchbody").hide()
		return;
	}
	$.ajax({
			url:"/CFA101G4/ProductServlet",
			dataType:"json",
			data :{ 
				pname :name,
				pcgid : $("#pcgid").val(),
				action :"inputpoint"
			},
			success:function(data){
				$("#searchbody").html("")
				for(let i = 0 ; i < data.length ; i++){
					$("#searchbody").append(`<div class="col-12" id="point`+i+`">`+data[i]+`</div>`)
					addHover(i)
				}
				
				if($("#searchinput").val() == name && data.length != 0 ){
					$("#searchbody").show();
				}else{
					$("#searchbody").hide()
				}
			}
	
	
	
	
	})
})






$("#cart").on("click" , function(){
	window.location.href="/CFA101G4/front-end/CartList/selectCL.jsp";
})
