$(function() {
	let a = false;
	let b = false;
	function check() {
		if(a==true&&b==true){
			$("button[id='Submit']").removeAttr("disabled")
		}else{
			$("button[id='Submit']").attr("disabled", "disabled")
		}
	}
	
	//文章標題監聽
	$("input[id='atTitle']").on('input', function() {
		let Title = $(this).val().trim().length;
		$('#Titlewarn').text("");
		if (Title < 5 || Title > 35 ) {
			$('#Titlewarn').text("標題長度為5到35個字之間");
			$('#Titlewarn').css({"color":"red","font-size":"14px"});
			a = false;
		} 
		else {
			a = true;
		}
		check();
	})
	
	//內容監聽
	tinymce.init({
		branding : false,
		resize : false,
		height: "550",
		forced_root_block : false,
		statusbar: false,
		selector : '#mytext',
		language : 'zh_TW',
		plugins : 'emoticons',
		toolbar : 'undo redo | styleselect | forecolor | emoticons |code',
		setup : function(box) {
			box.on('input', function() {
				let repText = tinyMCE.get('mytext').getContent({ format: "text" }).trim().length;
				$('#Textwarn').text("");
				if (repText <= 30) {
					$('#Textwarn').text("內容須超過30個字");
					$('#Textwarn').css({"color":"red","font-size":"14px"});
					b = false;
				} else {
					b = true;
				}
				check();
			});
		}
	});
})

	//修改後關閉視窗
function Redirect() {
   setTimeout(function(){
	   window.location.href="/CFA101G4/front-end/index.jsp"
		   self.opener.location.reload();
		}, 1500);
}
//文章修改送出
$("button[id='Submit']").click(function() {
	let Text = tinyMCE.get('mytext').getContent().trim();
	let title = $("#atTitle").val();
	$.ajax({
		type : "post",
		url : "/CFA101G4/article/ArticleServlet.do",
		dataType : 'json',
		data : {
			'action' : 'insert',
			'text' :  Text,
			'title' : title
		},
		success : function(data) {
			if (data == "1") {
				Swal.fire({
					  icon: 'success',
					  text: '發文成功',
				});
				Redirect()
			} else {
				alert("發文失敗,格式不正確")
			}
		},
		error : function() {
			alert("發生錯誤!")
		}
	})
})
// 取消鍵&清空內容
$("#cancel").click(function() {
	tinyMCE.get('mytext').setContent('')
	$("input[id='atTitle']").val('')
	 self.location = document.referrer;
})