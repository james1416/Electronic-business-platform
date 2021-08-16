function init() {
	let bp_bname = false;
	let bp_add = false ; 
	let bp_tel = false;

	let onlynum = /^\d+$/;
	let number = /^09\d{8}$/;
	
	$("#bp_bname").css("border-color" , "red");
	
	$("#bp_add").css("border-color" , "red");
	
	$("#bp_tel").css("border-color" , "red");
	
	$("#bp_bname").on("input",function(){
		if($("#bp_bname").val() != ""){
			$("#bp_bname").css("border-color" , "green");
			bp_bname = true ; 
		}else{
			$("#bp_name").css("border-color" , "red");
			bp_bname = false
		}
	})
	
	$("#bp_add").on("input",function(){
		if($("#bp_add").val() != ""){
			$("#bp_add").css("border-color" , "green");
			bp_add = true ; 
		}else{
			$("#bp_name").css("border-color" , "red");
			bp_add = false
		}
	})
	
	$("#bp_tel").on("input",function(){
		if(number.test($("#bp_tel").val())){
			$("#bp_tel").css("border-color" , "green");
			bp_tel = true ;
		}
		else{
			$("#bp_tel").css("border-color" , "red");
			bp_tel = false
		}
	})
	
	
	
	$("#add").on("click",function(){
		var formData = new FormData();
		
       
        
        formData.append("bp_id",$("#bp_id").val())
        formData.append("bp_shsta",$("#bp_shsta").val())
        formData.append("bp_bname",$("#bp_bname").val())
        formData.append("bp_add",$("#bp_add").val())
        formData.append("bp_tel",$("#bp_tel").val())
        formData.append("action","receive")
		alert("OK!")
		
		$.ajax({
			url: "/CFA101G4/bid.do",
			type:"post",
			async: true,
		    contentType: false,
		    processData: false,
			data:formData,
			success:function(data){
				 console.log(data)
				 if(data === "login"){
					 swal(
							 "請先登入會員",
							 data,
							 "error"
					 ).then(function(value) {
					        if (value.value) {
					        	location.href = "/CFA101G4/front-end/login.jsp"
									 return 
					        }
						})
				 }else{
					 location.href="/CFA101G4/bid.do?action=get_win_bid"
					 } 
				 }
				
		})

	})
	
	
	
	
	$("#submit").on("click",function () {
		if(bp_bname && bp_add && bp_tel) {
			$("#add").click();
		}else{
			swal("錯誤","資料未填齊","error")
			console.log(bp_bname);
			console.log(bp_add);
			console.log(bp_tel);
			
		}
		
        
    })
	
	
	
	
	
}
window.onload = init;