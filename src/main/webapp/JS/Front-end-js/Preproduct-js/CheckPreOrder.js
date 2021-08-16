function init() {
	
	AddressSeleclList.Initialize("city", "dist");
	
	
	let	PO_TEL	= false;
	let	PO_BNAME = false;
	let PO_PAY = false;
	let city =false;
	let addr = false;
	let DO = false;
	
	$("#PO_TEL").css("border-color" , "red");
	$("#PO_BNAME").css("border-color" , "red");
	$("#PO_PAY").css("border-color" , "red");
	$("#city").css("border-color" , "red");
	$("#addr").css("border-color" , "red");
	$("#do").css("border-color" , "red");
	
	
	$("#PO_TEL").on("input",function(){
		if($("#PO_TEL").val() != ""){
			$("#PO_TEL").css("border-color" , "green");
			PO_TEL = true ; 
		}else{
			$("#PO_TEL").css("border-color" , "red");
			PO_TEL = false
		}
	})
	
	$("#PO_BNAME").on("input",function(){
		if($("#PO_BNAME").val() != ""){
			$("#PO_BNAME").css("border-color" , "green");
			PO_BNAME = true ; 
		}else{
			$("#PO_BNAME").css("border-color" , "red");
			PO_BNAME = false
		}
	})
	
	
	$("#PO_PAY").on("change",function(){
		if($("#PO_PAY").val() != ""){
			$("#PO_PAY").css("border-color" , "green");
			PO_PAY = true ; 
		}else{
			$("#PO_PAY").css("border-color" , "red");
			PO_PAY = false
		}
	})
	
	
	$("#city").on("change",function(){
		if($("#city").val() != ""){
			$("#city").css("border-color" , "green");
			city = true ; 
		}else{
			$("#city").css("border-color" , "red");
			city = false
		}
	})
	
	
	
		$("#addr").on("input",function(){
		if($("#addr").val() != ""){
			$("#addr").css("border-color" , "green");
			addr = true ; 
		}else{
			$("#addr").css("border-color" , "red");
			addr = false
		}
	})
	
		$("#do").on("change",function(){
		if($("#do").val() != ""){
			$("#do").css("border-color" , "green");
			DO = true ; 
		}else{
			$("#do").css("border-color" , "red");
			DO = false
		}
	})
	
	
	$("#add").on("click",function(){
		var formData = new FormData();
		
        
        
        formData.append("PO_TEL", $("#PO_TEL").val())
        formData.append("PO_BNAME", $("#PO_BNAME").val())
        formData.append("PO_PAY" , $("#PO_PAY").val())
        formData.append("city" , $("#city").val())
        formData.append("addr" , $("#addr").val())
        formData.append("do", $("#do").val())
        formData.append("PD_ID", $("#PD_ID").val())
        formData.append("PD_SMEMID", $("#PD_SMEMID").val())
        formData.append("PO_QTY", $("#PO_QTY").val())
        formData.append("PO_PRICE", $("#PO_PRICE").val())
        formData.append("action" , "addOrder")
		
		$.ajax({
			url: "/CFA101G4/PoServlet",
			type:"post",
		    contentType: false,
		    processData: false,
			data:formData,
			success: function(data){
				console.log(data)
				location.assign("/CFA101G4/front-end/FrontMain.jsp");
			}
							
		})
		
	})
	

	
	$("#submit").on("click",function () {
		let ele = false;
		let price= $("#PO_PRICE").val();
		 $.ajax({
		    	url:"/CFA101G4/MemberServlet",
		    	data:{
		    		"action" : "checkEle"
		    	},
		    	success:function(data){
		    		ele = parseInt(data);
		    		if(ele < price){
		    	    	swal({
		    	    		  title: '錢包餘額不足！',
		    	    		  type: 'warning',
		    	    		  html:
		    	    		    "<a target='_blank' href='/CFA101G4/front-end/Member/storeMoney.html'>" +
		    	    		    "<button type='button' class='btn btn-primary'>儲值去！</button>" +
		    	    		    "</a>",
		    	    		  showCloseButton: true,
		    	    		  confirmButtonText:
		    	    		    '確認！',
		    	    		})
		    	    	return;
		    	    }else{
		    	    	ele = true;
		    	    	if(PO_TEL && PO_BNAME && PO_PAY && city && addr && DO && ele){
		    				$("#add").click();
		    			}else if(ele==false){
		    				
		    			}else{
		    				swal("錯誤","商品資訊未填齊","error")
		    			}
		    	    }
		    	}
		    })
    })
}



window.onload = init;