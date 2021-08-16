$(document).on('click','#delcol',function(){
	let atid = $(this).val();
	$.ajax({
		type : "GET",
		url : "/CFA101G4/ArticlecollectionServlet",
		dataType : 'json',
		data : {
			'action' : 'COLLECTION',
			'atid' : atid
		},
		cache : false,
		success : function(data) {
			if (data==0) {
				Swal.fire({
					  icon: 'success',
					  text: '刪除成功',
				});
				reflash();
			} else {
				alert("fail")
			}
		},
	})
});

function reflash() {
	   setTimeout(function(){
			  location.reload(false);
			}, 1000);
	}
