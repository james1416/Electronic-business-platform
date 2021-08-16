$(function() {
	let a =window.location.href
	let b =`<div class="fb-share-button" data-href="${a}" data-layout="button_count" data-size="small"><a target="_blank" 
	href="https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fplugins%2F&amp;src=sdkpreparse" class="fb-xfbml-parse-ignore">分享</a></div>`
	$("#tagfb").html(b)
	chcekCol();
	checklike();
	checkpost();
});
// 頁面載入區END
// 確認PO文身分&留言是否是自己
function checkpost() {
	let check =$("#postname").val();
	let USRID = $('li[id="USRUID"]').val();
	let atid = $("#collection").val();
	$("li[id='msgname']").each(function(i, e){
		let memid= $(this).attr("ss")
		if(memid==USRID){
			let msg = $(this).val();
			let remsg=`<li><button type="button" class="dropdown-item" value="${msg}" id="delMsg">刪除留言</button></li>
				   <li><button type="button" class="dropdown-item" value="${msg}" id="updatemsg">修改留言</button></li>`
			let msgfun = $(this).parent().parent().parent().parent().children('.col-10').find("#msgbox")
			msgfun.html(remsg)
		}
		})
	if(check==USRID){
		let renew =`<li><a class="dropdown-item" href="/CFA101G4/article/ArticleServlet.do?action=USR_For_Update&atid=${atid}" target="_blank">編輯文章</a></li>
					<li><button type="button" class="dropdown-item" value="${atid}" id="del">刪除文章</button></li>`
		$("#dropfuntion").html(renew)
	}
  
}
//window.close(); 
//刪除後關閉視窗
function Redirect() {
   setTimeout(function(){
	   window.location.href="/CFA101G4/front-end/index.jsp"
		   self.opener.location.reload();
		}, 1500);
}
//刪除後刷新
function reflsh() {
	   setTimeout(function(){
		   location.reload();
			}, 1500);
}
//修改留言
$(document).on('click', '#updatemsg', function(){
	let msgid =$(this).val()
	let a =$(this).parent().parent().parent().parent().parent().find('textarea#mymsg')
	let c =a.val();
	let funbox=($(this).parent().parent().parent().find("#texs"))
	let btn = `<button type="button" class="btn btn-success m-1" id="submit">確定修改</button>
		   		<button type="button" class="btn btn-danger m-1" id="Cancel">取消</button>
		   		<span id = "hint"></span>`
		$(funbox).html(btn)
	a.removeAttr("readonly")
	let cancelbox=$(this).parent().parent().parent().find("#Cancel")
	let submitbox=$(this).parent().parent().parent().find("#submit")
	let hints=$(this).parent().parent().parent().find("#hint")
	$(cancelbox).click(function() {
		a.val(c)
		funbox.html("")
		a.attr("readonly","readonly")
		
	})
	//留言內容監聽
	$(a).on('input',function(){
		$(hints).text("")
		if($(this).val().trim().length<5){
			$(hints).text("內容不得少於五個字")
			$(hints).css('color','red')
			$(submitbox).attr("disabled", "disabled")
		}else{
			$(submitbox).removeAttr("disabled")
		}
	})
	//留言修改送出
	$(submitbox).click(function() {
		let msgtext = a.val()
		$.ajax({
			type:"get",
			url:"/CFA101G4/ArticlemessageServlet",
			dataType:'json',
			data:{
				'action':'updateMsg',
				'msgid': msgid,
				'msgtext': msgtext
			},
			success:function(data){
			a.attr("readonly","readonly")
			funbox.html("")
	           if(data=="1"){
	        	   Swal.fire({
	        		   	  icon: 'success',
						  text: '修改成功',
						  timer:800
	        	   		})
	                 }else{
			         Swal.fire(
				     	'修改失敗',
				     	'error'
				   )
	            }
	         },
		})
	})
})
//我的留言刪除
$(document).on('click', '#delMsg', function(){
	let funbox=($(this).parent().parent().parent().find("#texs"))
	let a =$(this).parent().parent().parent().parent().parent().find('textarea#mymsg')
	let row =$(this).parents("#das")
	$(a).attr("readonly","readonly")
	funbox.html("")
	let msgid=$(this).val()
		Swal.fire({
		  title: '確定刪除留言?',
		  text: "留言移除後無法復原",
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
					url:"/CFA101G4/ArticlemessageServlet",
					dataType:'json',
					data:{
						'action':'dalete',
						'msgid': msgid
					},
					success:function(data){
			           if(data=="1"){
			        	   Swal.fire(
			     			      'Deleted!',
			     			      '刪除成功',
			     			      'success'
			     			    )
			     			   row.remove();
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
//我的文章刪除
$(document).on('click', '#del', function(){
let atid =$(this).val()
	Swal.fire({
		  title: '確定刪除文章?',
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
			     			    Redirect();
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

// 留言回覆內容監聽
$("textarea[id='RepMsg']").on('input', function() {
	let repMsg = $(this).val().trim().length;
	$('#Repwarn').text("");
	if (repMsg < 5) {
		$('#Repwarn').text("回覆內容不得少於五個字");
		$('#Repwarn').css('color', 'red');
		$("button[id='RepSubmit']").attr("disabled", "disabled")
	} else {
		$("button[id='RepSubmit']").removeAttr("disabled")
	}
})
// 我要回復按鈕
$("button[id='Reply']").click(function() {
	if (chcekmember() == false) {
		remindmember();
	} else {
		let atText = $("div[id='articleText']").text();
		let atTitle = $("div[id='ttes']").text();
		let atpostime = $("p[id='atPosttime']").text();
		
		$("#Reptime").html(atpostime)
		$("#ReplyTitle").html(atTitle)
		$("#articleCard").html(atText)
		ReplyAt();
	}
})
// 開啟回覆視窗
function ReplyAt() {
	$('#ReplyModal').modal('toggle');
}
// 留言送出
$(document).on('click', '#RepSubmit', function() {
	$(this).attr("disabled", "disabled")
	let repMsg = $("textarea[id='RepMsg']").val();
	let articleID = $("button[id='Reply']").val();
	$.ajax({
		type : "get",
		url : "/CFA101G4/ArticlemessageServlet",
		dataType : 'json',
		data : {
			'action' : 'Reply',
			'atid' : articleID,
			'text' : repMsg
		},
		success : function(data) {
			if (data == "1") {
				 Swal.fire({
	        	icon: 'success',
	        	text: '留言成功',
	        	  })
	        	   	reflsh();
			} else {
				alert("失敗")
			}
		},
	})
})
//全局調用NavTopbar.js chcekmember()方法跟 remindmember()

//未登入檢舉文章鎖定
$("#reparticle").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	} 
})
//未登入檢舉留言鎖定
$("a[id='repArticleMsg']").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	} 
})
//未登入收藏鎖定
$("#collection").click(function(e) {
	let i=$(this)
	let atid = $(this).val()
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	}else{
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
				if (data == true) {
					i.text("已收藏")
					Swal.fire({
						  icon: 'success',
						  text: '收藏成功',
						  timer:1000
					});
				} else {
					i.text("收藏")
					Swal.fire({
						  icon: 'success',
						  text: '移除收藏',
						  timer:1000
					});
				}
			},
		})
	}
})
//按讚比對
function checklike(){
	if (chcekmember() == false) {
	}else{
	let atid =$("#collection").val()
			$.ajax({
			type : "GET",
			url : "/CFA101G4/ArticleLikeServlet",
			dataType : 'json',
			data : {
				'action' : 'likecheck',
				'atid' : atid
			},
			cache : false,
			success : function(data) {
				if (data == 1) {
					let atlike = $("#likeArticle").text()
					let hert = `<i class="bi-heart-fill" >${atlike}</i>`
					$("#likeArticle").html(hert)
				}
			},
		})
	  }	
	}
//收藏確認
function chcekCol() {
	if (chcekmember() == false) {
	}else{
	let atid =$("#collection").val()
		$.ajax({
			type : "GET",
			url : "/CFA101G4/MyCollectionServlet",
			dataType : 'json',
			data : {
				'action' : 'colchecck',
				'atid' : atid
			},
			cache : false,
			success : function(data) {
				if (data == 1) {
					$("#collection").text("已收藏")
				} else {
					$("#collection").text("收藏")
				}
			},
		})
	  }
	}
//未登入按讚鎖定
$("#likeArticle").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	}else{
		let like = $(this)
		let atid = $(this).val()
		$.ajax({
			type : "GET",
			url : "/CFA101G4/ArticleLikeServlet",
			dataType : 'json',
			data : {
				'action' : 'LIKE',
				'atid' : atid
			},
			cache : false,
			success : function(data) {
				if (data == true) {
					let total =parseInt(like.text(), 10)+1
					let hert = `<i class="bi-heart-fill" >${total}</i>`
					like.html(hert);
				} else {
					let total =parseInt(like.text(), 10)-1
					let hert = `<i class="bi-heart" >${total}</i>`
						like.html(hert);
				}
			},
		})
	} 
})
