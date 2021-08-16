$("button").on("click",function(e){
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
					url:"/CFA101G4/OrdersServlet",
					data:{
						odid : id,
						odsta : "8",
						odgoldflow :"2",
						action : "updataOD"
					},
						success:function(){
							window.location.reload();
						}
				  })
	        } else {
	            swal('已取消操作！', '訂單還在～', 'error')
	        }
		})
	}
})