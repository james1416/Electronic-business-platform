$("button").on("click",function(e){
	console.log($(e.target).attr("id"))
	let odid = $(e.target).val()
	if( $(e.target).attr("id") == "accept"){
		swal({
	        title: '確定接受退貨嗎？',
	        text: '接受後將無法更換囉！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
				$.ajax({
					url:"/CFA101G4/OrdersServlet",
					data:{
						od_id : odid,
						od_sta : "8",
						action : "returned"
					},
						success:function(){
							location.href = "/CFA101G4/front-end/Orders/selectReturnOD.jsp"
						}
				  })
	        } else {
	            swal('已取消操作！', '訂單還在～', 'error')
	        }
		})
		
	}
	if( $(e.target).attr("id") == "refuse"){
		swal({
	        title: '確定不接受退貨嗎？',
	        text: '接受後將無法更換囉！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
				$.ajax({
					url:"/CFA101G4/OrdersServlet",
					data:{
						od_id : odid,
						od_sta : "3",
						action : "returned"
					},
						success:function(){
							location.href = "/CFA101G4/front-end/Orders/selectReturnOD.jsp"
						}
				  })
	        } else {
	            swal('已取消操作！', '訂單還在～', 'error')
	        }
		})
	}
	
})