//檢舉內容監聽
$("textarea[id='reportTextarea']").on('input', function() {
	let repAt = $(this).val().trim().length;
	$('#repTextwarn').text("");
	if (repAt <= 20) {
		$('#repTextwarn').text("回報內容須超過20個字");
		$('#repTextwarn').css('color','red');
		$("button[id='RepSubmit']").attr("disabled", "disabled")
	} else {
		$("button[id='RepSubmit']").removeAttr("disabled")
	}
})
//提交後延遲轉址
function returns() {
	   setTimeout(function(){
		   window.close();
			}, 1500);
}

//檢舉送出
$("button[id='RepSubmit']").click(function() {
	let repText = $("textarea[id='reportTextarea']").val();
	let atid = $(this).val();
	$.ajax({
		type:"post",
		url:"/CFA101G4/ArticleReportList",
		dataType:'json',
		data:{
			'action':'ATReporSubmit',
			'atid': atid,
			'text': repText
		},
		success:function(data){
           if(data=="1"){
        	 $("textarea[id='reportTextarea']").val("");
        	  Swal.fire({
    		   	  icon: 'info',
    		   	  title: '檢舉表單提交成功',
				  text: '感謝您的檢舉，我們將盡速審閱',
				  timer:1000
    	   		})
    	   		returns()
                 }else{
                	 Swal.fire({
              		   	  icon: 'error',
              		   	  title: '檢舉表單提交失敗',
          				  text: '看看是不是哪裡寫錯了',
          				  timer:1000
              	   		})
             }
           },
	})
	
})
//取消鍵資源釋放
$("#cancel").click(function() {
	$("textarea[id='reportTextarea']").val("");
	$('#repTextwarn').empty();
	 window.close();
})