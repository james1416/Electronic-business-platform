$("#allAccept").on("click",function(){
	let point = false;
	let formData = new FormData();
	let i = $("input[id='odidcheckbox']").length;
	console.log("?")
	formData.append('action','allupdataOD')
	formData.append('odsta','2')
	for(let y = 0 ; y < i ; y++){
	if($("input[id='odidcheckbox']:eq("+y+")").prop('checked')){
			let odid = $("input[id='odidcheckbox']:eq("+y+")").val();
			formData.append('odid', odid)
			point = true;
		}
	}
	console.log("?")
	if(!point){
		swal("請勾選訂單")
		return;
	}else{
		swal({
	        title: '確定接受所選的訂單嗎？',
	        text: '確認後無故取消將會被檢舉唷！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
	        	$.ajax({
	    			url:"/CFA101G4/PoServlet",
	    		    contentType: false,
	    		    processData: false,
	    			type:"post",
	    			data: formData,
	                success: function() {
	                    	window.location.href = "/CFA101G4/front-end/PreProduct/sellPreOrder.jsp"
	                }
	 
	            })
	        } else {
	            swal('已取消操作！', '', 'error')
	        }
		})
	}
})





$("#checkbox").on("change",function(){
	if($("#checkbox").prop('checked')){
		let i = $("input[id='odidcheckbox']").length;
		for(let y = 0 ; y < i ; y++){
			$("input[id='odidcheckbox']:eq("+y+")").prop("checked" , true)
		}

	}
	if(!$("#checkbox").prop('checked')){
		let i = $("input[id='odidcheckbox']").length;
		for(let y = 0 ; y < i ; y++){
			$("input[id='odidcheckbox']:eq("+y+")").prop("checked" , false)
		}
	}
})




$("button").on("click",function(e){
	let i = $(this).parent().parent();
	
		
	if($(this.id).selector == "accept"){
		let id = $(this).val()
		swal({
	        title: '確定接受訂單嗎？',
	        text: '接受後無故取消將會被檢舉喔！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
	        	$.ajax({
	    			url:"/CFA101G4/PoServlet",
	    			data:{
	    				PO_ID : id ,
	    				PO_STA : "3",
	    				PO_GOLDFLOW: "1",
	    				action : "updateBOrderSta"
	    					
	    			},
	    			success:function(){
						swal(
							"成功接受訂單",
							"趕快接著出貨囉！",
							"success"
									)
						i.remove();
					}
	 
	            })
	        } else {
	            swal('已取消操作！', '', 'error')
	        }
	    })
	}
	
	
	if($(this.id).selector == "cancel"){
		let id = $(this).val()
		swal({
	        title: '確定取消嗎？',
	        text: '取消後就無法復原囉！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
	        	$.ajax({
	    			url:"/CFA101G4/PoServlet",
	    			data:{
	    				PO_ID : id ,
	    				PO_STA : "9",
	    				PO_GOLDFLOW: "2",
	    				action : "updateBOrderSta"
	    			},
	    			success:function(){
						swal(
							"成功取消訂單",
							"訂單已被您取消囉！",
							"success"
									)
						i.remove();
					}
	 
	            })
	        } else {
	            swal('已取消操作！', '訂單還在～', 'error')
	        }
	 
	    })
	}
		
})