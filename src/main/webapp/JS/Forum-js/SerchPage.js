
//		引用index 暫時沒用



//$("#listbtn").click(function() {
//	$(".side").slideToggle("slow")
//});
//// 頁面載入
//$(function() {
//})
//// 頁面載入區END
//
//// 收藏按鈕
//$(document).on('click','#colbtn',function() {
//	let id = ($(this).val())
//	let USRID = $('li[id="USRUID"]').text().trim();
//	if (USRID == "未登入") {
//		Swal.fire({
//			  icon: 'question',
//			  text: '請先登入會員',
//		});
//	} else {
//		$.ajax({
//			type : "GET",
//			url : "/CFA101G4/ArticlecollectionServlet",
//			dataType : 'json',
//			data : {
//				'action' : 'COLLECTION',
//				'atid' : id
//			},
//			cache : false,
//			success : function(data) {
//				if (data == true) {
//					$(e.target).text("已收藏")
//				} else {
//					$(e.target).text("收藏")
//				}
//			},
//		})
//	}
//})
//	// 按讚
//	$(document).on('click','#likeart',function() {
//		let id = ($(this).val())
//		let USRID = $('li[id="USRUID"]').text().trim();
//		if (USRID == "未登入") {
//			Swal.fire({
//				  icon: 'question',
//				  text: '請先登入會員',
//			});
//		} else {
//			$.ajax({
//				type : "GET",
//				url : "/CFA101G4/ArticleLikeServlet",
//				dataType : 'json',
//				data : {
//					'action' : 'LIKE',
//					'atid' : id
//				},
//				cache : false,
//				success : function(data) {
//					if (data == true) {
//						location.reload();
//					} else {
//						location.reload();
//					}
//				},
//			})
//		}
//	})
//	// 最新文章總覽
//	$("li[id='allAt']").click(function() {
//		$.ajax({
//			type : "GET",
//			url : "/CFA101G4/article/ArticleServlet.do",
//			dataType : 'json',
//			data : {
//				'action' : 'allArticle'
//			},
//			cache : false,
//			success : function(data) {
//				let view ="";
//				 for(let i=0;i<data.length;i++){
//				view +=
//					`<div class="card mb-3" id="articletext">
//					<div class="row g-0">
//						<div class="col-md-4 align-self-center" id="USRPHOTO">
//							<img src="/CFA101G4/Img/logo1.png"
//								class="img-fluid rounded-start" alt="..." style="border:1px solid black;">
//							<div class="USRID"><p>${data[i].memname}</p>
//							</div>
//							<div><p id="posttime">發佈於${data[i].postime}</p></div>
//						</div>
//						<div class="col-md-8 shadow p-3 mb-5 bg-body rounded" id="textbox">
//							<div class="card-body ">
//								<h4 class="card-title">${data[i].title}</h4>
//								<p class="card-text">${data[i].text}</p>
//							</div>
//							<div class="row" >
//							<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
//							<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
//								收藏
//							</button></div>
//							<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart-fill" >${data[i].like}</i></button></div>
//							</div>
//						</div>
//					</div>
//				</div>`;
//				}
//				$("#art").html(view)
//			},
//		})
//	});
//// 熱門文章總覽
//$("li[id='allHotAt']").click(function() {
//	$.ajax({
//		type : "GET",
//		url : "/CFA101G4/article/ArticleServlet.do",
//		dataType : 'json',
//		data : {
//			'action' : 'allHotArticle'
//		},
//		cache : false,
//		success : function(data) {
//			let view ="";
//			 for(let i=0;i<data.length;i++){
//			view +=
//				`<div class="card mb-3" id="articletext">
//				<div class="row g-0">
//					<div class="col-md-4 align-self-center" id="USRPHOTO">
//						<img src="/CFA101G4/Img/logo1.png"
//							class="img-fluid rounded-start" alt="..." style="border:1px solid black;">
//						<div class="USRID"><p>${data[i].memname}</p>
//						</div>
//						<div><p id="posttime">發佈於${data[i].postime}</p></div>
//					</div>
//					<div class="col-md-8 shadow p-3 mb-5 bg-body rounded" id="textbox">
//						<div class="card-body ">
//							<h4 class="card-title">${data[i].title}</h4>
//							<p class="card-text">${data[i].text}</p>
//						</div>
//						<div class="row" >
//						<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
//						<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
//							收藏
//						</button></div>
//						<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart-fill" >${data[i].like}</i></button></div>
//						</div>
//					</div>
//				</div>
//			</div>`;
//			}
//			$("#art").html(view)
//		},
//	})
//});
//// 以下為上方搜尋列
//$("#secbtn").click(function() {
//	let title=$("#artname").val().trim();
//	if(title.length<1){
//		alert("請輸入")
//	}else{
//		$.ajax({
//		type : "GET",
//		url : "/CFA101G4/article/ArticleServlet.do",
//		dataType : 'json',
//		data : {
//			'action' : 'getSomeAt',
//			'title' : title
//		},
//		cache : false,
//		success : function(data) {
//			if(data==""){
//				alert("ff")
//			}else{
//				alert("成功")
//				let view ="";
//				 for(let i=0;i<data.length;i++){
//				view +=
//					`<div class="card mb-3" id="articletext">
//					<div class="row g-0">
//						<div class="col-md-4 align-self-center" id="USRPHOTO">
//							<img src="/CFA101G4/Img/logo1.png"
//								class="img-fluid rounded-start" alt="..." style="border:1px solid black;">
//							<div class="USRID"><p>${data[i].memname}</p>
//							</div>
//							<div><p id="posttime">發佈於${data[i].postime}</p></div>
//						</div>
//						<div class="col-md-8 shadow p-3 mb-5 bg-body rounded" id="textbox">
//							<div class="card-body ">
//								<h4 class="card-title">${data[i].title}</h4>
//								<p class="card-text">${data[i].text}</p>
//							</div>
//							<div class="row" >
//							<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
//							<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
//								收藏
//							</button></div>
//							<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart-fill" >${data[i].like}</i></button></div>
//							</div>
//						</div>
//					</div>
//				</div>`;
//				}
//				$("#art").html(view)
//			}
//		},
//		error: function(){alert("發生錯誤")}
//	})
//	}
//})
//// 文章搜尋
//$("#artname").keydown(function() {
//	let title=$("#artname").val().trim();
//	if(event.which == 13 ){
//		if(title.length<1){
//			alert("請輸入")
//		}else{
//		$.ajax({
//		type : "GET",
//		url : "/CFA101G4/article/ArticleServlet.do",
//		dataType : 'json',
//		data : {
//			'action' : 'getSomeAt',
//			'title' : title
//		},
//		cache : false,
//		success : function(data) {
//			if(data==""){
//				alert("ff")
//			}else{
//				alert("成功")
//				let view ="";
//				 for(let i=0;i<data.length;i++){
//				view +=
//					`<div class="card mb-3" id="articletext">
//					<div class="row g-0">
//						<div class="col-md-4 align-self-center" id="USRPHOTO">
//							<img src="/CFA101G4/Img/logo1.png"
//								class="img-fluid rounded-start" alt="..." style="border:1px solid black;">
//							<div class="USRID"><p>${data[i].memname}</p>
//							</div>
//							<div><p id="posttime">發佈於${data[i].postime}</p></div>
//						</div>
//						<div class="col-md-8 shadow p-3 mb-5 bg-body rounded" id="textbox">
//							<div class="card-body ">
//								<h4 class="card-title">${data[i].title}</h4>
//								<p class="card-text">${data[i].text}</p>
//							</div>
//							<div class="row" >
//							<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
//							<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
//								收藏
//							</button></div>
//							<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart-fill" >${data[i].like}</i></button></div>
//							</div>
//						</div>
//					</div>
//				</div>`;
//				}
//				$("#art").html(view)
//			}
//		},
//		error: function(){alert("發生錯誤")}
//	})
//}
//	}
//
//})
