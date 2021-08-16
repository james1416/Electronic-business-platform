$("#checkbox").on("change",function(){
	if($("#checkbox").prop('checked')){
		let i = $("input[id='bidcheckbox']").length;
		for(let y = 0 ; y < i ; y++){
			$("input[id='bidcheckbox']:eq("+y+")").prop("checked" , true)
		}

	}
	if(!$("#checkbox").prop('checked')){
		let i = $("input[id='bidcheckbox']").length;
		for(let y = 0 ; y < i ; y++){
			$("input[id='bidcheckbox']:eq("+y+")").prop("checked" , false)
		}
	}
})



$("button").on("click",function(e){
	let i = $(this).parent().parent();
	
	
	if($(this.id).selector == "modelaccept"){
		let id = $(this).val()
		swal({
	        title: '確定接受此退貨需求嗎？',
	        text: '確定後將撥款給買家！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
				$.ajax({
					url:"/CFA101G4/bid.do",
					data:{
						bp_id : id,
						bp_shsta : "6",
						action : "updateshsta"
					},
					success:function(){
						window.location.href = "/CFA101G4/front-end/BidProduct/Cancelbid.jsp"
					}
				})
	        } else {
	            swal('已取消操作！', '尚未接受', 'error')
	        }
		})
	}
		
		
	if($(this.id).selector == "modelcancel"){
		let id = $(this).val()
		swal({
	        title: '確定拒絕退貨需求？',
	        text: '因商品瑕疵原因拒絕將會被檢舉喔！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
	        	$.ajax({
					url:"/CFA101G4/bid.do",
					data:{
						bp_id : id,
						bp_shsta : "7",
						action : "updateshsta"
					},
					success:function(){
						window.location.href = "/CFA101G4/front-end/BidProduct/Cancelbid.jsp"
					}
				})
	        } else {
	            swal('已取消操作！', '尚未拒絕', 'error')
	        }
		})
	}
	
	
	
	if($(this.id).selector == "accept"){
		let id = $(this).val()
		swal({
	        title: '確定接受此退貨需求？',
	        text: '確定後將撥款給買家！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
				$.ajax({
					url:"/CFA101G4/bid.do",
					data:{
						bp_id : id,
						bp_shsta : "6",
						action : "updateshsta"
					},
					success:function(){
						swal(
							"訂單接受退貨",
							"接收退貨成功！",
							"success"
									)
									window.location.href = "/CFA101G4/front-end/BidProduct/Cancelbid.jsp"
					}
				   })
	        } else {
	            swal('已取消操作！', '尚未接受', 'error')
	        }
		})
	}
	
	
	if($(this.id).selector == "cancel"){
		let id = $(this).val()
		swal({
	        title: '確定拒絕退貨需求？',
	        text: '因商品瑕疵原因拒絕將會被檢舉喔！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
				$.ajax({
					url:"/CFA101G4/bid.do",
					data:{
						bp_id : id,
						bp_shsta : "7",
						action : "updateshsta"
					},
						success:function(){
							swal(
								"成功拒絕退貨",
								"已成功拒絕退貨！",
								"success"
										)
						window.location.href = "/CFA101G4/front-end/BidProduct/Cancelbid.jsp"
						}
				  })
	        } else {
	            swal('已取消操作！', '尚未退貨', 'error')
	        }
		})
	}
})