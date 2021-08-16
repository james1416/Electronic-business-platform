
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
	
	
	if($(this.id).selector == "modelshipment"){
		let id = $(this).val()
		swal({
	        title: '確定出貨此筆訂單嗎？',
	        text: '更改狀態後若賣家沒收到會被檢舉喔！',
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
						bp_shsta : "3",
						action : "updateshsta"
					},
					success:function(){
						window.location.href = "/CFA101G4/front-end/BidOrders/shiporder.jsp"
					}
				})
	        } else {
	            swal('已取消操作！', '', 'error')
	        }
		})
	}
		
		
	if($(this.id).selector == "modelcancel"){
		let id = $(this).val()
		swal({
	        title: '確定取消訂單嗎？',
	        text: '惡意取消訂單將會被檢舉喔！',
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
						bp_shsta : "5",
						action : "updateshsta"
					},
					success:function(){
						window.location.href = "/CFA101G4/front-end/BidOrders/shiporder.jsp"
					}
				})
	        } else {
	            swal('已取消操作！', '訂單還在～', 'error')
	        }
		})
	}
	
	
	
	if($(this.id).selector == "shipment"){
		let id = $(this).val()
		swal({
	        title: '確定出貨此筆訂單嗎？',
	        text: '更改狀態後若賣家沒收到會被檢舉喔！',
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
						bp_shsta : "3",
						action : "updateshsta"
					},
					success:function(){
						swal(
							"訂單成功出貨",
							"訂單出貨囉！",
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
	        title: '確定取消訂單嗎？',
	        text: '惡意取消訂單將會被檢舉喔！',
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
						bp_shsta : "5",
						action : "updateshsta"
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