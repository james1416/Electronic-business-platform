//我的文章總數
$(function() {
	$("div#allmyart").each(function(i,e) {
			$("#total").html("共有"+(i+1)+"篇文章")
	})
})
// 頁面載入區END

	//一秒後刷新
	function reflash() {
	   setTimeout(function(){
			  location.reload(false);
			}, 1000);
	}
	//我的文章刪除
	$("button[id='del']").click(function(){
	let atid =$(this).val()
		Swal.fire({
			  title: '確定刪除?',
			  text: "文章移除後無法復原",
			  icon: 'warning',
			  showCancelButton: true ,
			  cancelButtonText:'取消',
			  confirmButtonColor: '#d33',
			  cancelButtonColor: '#3085d6',
			  confirmButtonText: '刪除'
			}).then((result) => {
			  if (result.isConfirmed) {
					$.ajax({
						type:"post",
						url:"/CFA101G4/article/ArticleServlet.do",
						dataType:'json',
						data:{
							'action':'delete_fot_At',
							'atid': atid
						},
						success:function(data){
				           if(data=="1"){
				        	   Swal.fire(
				     			      'Deleted!',
				     			      '刪除成功',
				     			      'success'
				     			    )
				     			    reflash();
				                 }else{
						         Swal.fire(
							     	'刪除失敗',
							     	'error'
							   )
				            }
				         },
					})
			  	}
			})
		})
