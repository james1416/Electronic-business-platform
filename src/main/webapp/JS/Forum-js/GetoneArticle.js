$(function() {
	$.ajax({
		type : "post",
		url : "/CFA101G4/article/ArticleServlet.do",
		dataType : 'json',
		data : {
			'action' : 'getonehot'
		},
		cache : false,
		success : function(data) {
			let hot =`<div class="text-truncate">${data.text}</div>
			<div class="text-end"><a href="/CFA101G4/ViewmoreArticleServlet?content=${data.atid}" target="_self"">點此觀看更多</a></div>`
		$("#HOT").html(hot)
		},
	})
	$.ajax({
		type : "post",
		url : "/CFA101G4/article/ArticleServlet.do",
		dataType : 'json',
		data : {
			'action' : 'getonenew'
		},
		cache : false,
		success : function(data) {
			let news =`<div class="text-truncate">${data.text}</div>
				<div class="text-end"><a href="/CFA101G4/ViewmoreArticleServlet?content=${data.atid}" target="_self"">點此觀看更多</a></div>`
		$("#NEW").html(news)
		},
	})
})
$("#HOTAT").click(function() {
	$("#NEWAT").css('background-color','transparent')
	$(this).css('background-color','white')
	$("#NEW").hide()
	$("#HOT").fadeIn("slow")
})
$("#NEWAT").click(function() {
	$("#HOTAT").css('background-color','transparent')
	$(this).css('background-color','white')
	$("#HOT").hide()
	$("#NEW").fadeIn("slow")
})