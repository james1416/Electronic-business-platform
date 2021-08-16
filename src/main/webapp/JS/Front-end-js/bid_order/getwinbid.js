
$("#confirm").on("click",function(){
	var formData = new FormData();		

    formData.append("bp_id",$("#bp_id").val())
    formData.append("bp_shsta",$("#bp_shsta").val())
    formData.append("action","updateshsta")
	swal({
        title: '確定收貨',
        text: '確定收貨後將撥款給賣家！',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: '確定！',
        cancelButtonText: "取消",
        
    }).then(function(value) {
        if (value.value) {
			$.ajax({
				url: "/CFA101G4/bid.do",
				type:"post",
				async: true,
			    contentType: false,
			    processData: false,
				data:formData,
				success:function(){
					swal(
							"訂單收貨成功",
							"訂單收貨成功！",
							"success"
									)
					window.location.href = "/CFA101G4/bid.do?action=get_win_bid"
				}
			})
        } else {
            swal('已取消操作！', '', 'error')
        }
    
	})
})
		

$("#tab").on("click",function(e){
	console.log(e.target);
	if($(e.target).attr("id") == "return"){
	var formData = new FormData();		

    formData.append("bp_id",$("#bp_id").val())
    formData.append("bp_shsta",5)
    formData.append("action","updateshsta")
	swal({
        title: '確定要退貨嗎',
        text: '確定退貨後將由賣家判定是否允許！',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: '確定！',
        cancelButtonText: "取消",
        
    }).then(function(value) {
        if (value.value) {
			$.ajax({
				url: "/CFA101G4/bid.do",
				type:"post",
				async: true,
			    contentType: false,
			    processData: false,
				data:formData,
				success:function(){
					swal(
							"退貨申請",
							"發送成功！",
							"success"
									)
					window.location.href = "/CFA101G4/bid.do?action=get_win_bid"
				}
			})
        } else {
            swal('已取消操作！', '', 'error')
        }
    
	})
}
})		
