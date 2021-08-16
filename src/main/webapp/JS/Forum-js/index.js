//全局調用NavTopbar.js chcekmember()跟 remindmember() 方法
// 頁面載入
$(function() {
	myCol();
	mylike();
	// 熱門文章總覽
	$("li[id='allHotAt']").click(function() {
		$.ajax({
			beforeSend: function () {
	            $('#loading').css("display", "block");
	         },
			type : "GET",
			url : "/CFA101G4/article/ArticleServlet.do",
			dataType : 'json',
			data : {
				'action' : 'allHotArticle'
			},
			cache : false,
			success : function(data) {
				let view ="";
				 for(let i=0;i<data.length;i++){
				view +=
					`<div class="card mb-3" id="articletext">
					<div class="row g-0">
						<div class="col-md-4 align-self-center" id="USRPHOTO">
							<img src="data:image/png;base64,${data[i].mempic}"
								class="img-fluid" alt="..." style="height:120px;border-radius: 5px;">
							<div class="USRID"><p>${data[i].memname}</p>
							</div>
							<div><p id="posttime">發佈於${data[i].postime}</p></div>
						</div>
						<div class="col-md-8 shadow p-2 mb-5 bg-body rounded" id="textbox">
							<div class="card-body ">
								<h4 class="card-title">${data[i].title}</h4>
								<p class="card-text">${data[i].text}</p>
							</div>
							<div class="row mt-1" >
							<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
							<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
								收藏
							</button></div>
							<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart" >${data[i].like}</i></button></div>
							</div>
						</div>
					</div>
				</div>`;
				}
					$("#art").html(view)
						myCol();
						mylike();
				}, complete: function () {
					loading();}
			})
		});
	
	// 最新文章總覽
	$("li[id='allAt']").click(function() {
		$.ajax({
			beforeSend: function () {
	             $('#loading').css("display", "block");
	          },
			type : "GET",
			url : "/CFA101G4/article/ArticleServlet.do",
			dataType : 'json',
			data : {
				'action' : 'allArticle'
			},
			cache : false,
			success : function(data) {
				let view ="";
				 for(let i=0;i<data.length;i++){
				view +=
					`<div class="card mb-3" id="articletext">
					<div class="row g-0">
						<div class="col-md-4 align-self-center" id="USRPHOTO">
							<img src="data:image/png;base64,${data[i].mempic}"
								class="img-fluid" alt="..." style="height:120px;border-radius: 5px;">
							<div class="USRID"><p>${data[i].memname}</p>
							</div>
							<div><p id="posttime">發佈於${data[i].postime}</p></div>
						</div>
						<div class="col-md-8 shadow p-2 mb-5 bg-body rounded" id="textbox">
							<div class="card-body ">
								<h4 class="card-title">${data[i].title}</h4>
								<p class="card-text">${data[i].text}</p>
							</div>
							<div class="row mt-1" >
							<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
							<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
								收藏
							</button></div>
							<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart" >${data[i].like}</i></button></div>
							</div>
						</div>
					</div>
				</div>`; }
				$("#art").html(view)
				myCol();
				mylike(); }, 
				complete: function () {
				loading();}
				})
			});
	// 收藏比對
	function myCol() {
		if (chcekmember() == false) {
		}else{
			$.ajax({
				type : "post",
				url : "/CFA101G4/MyCollectionServlet",
				dataType : 'json',
				data : {
					'action' : 'coll'
				},
				success : function(data) {
					let a ="";
					for(let g=0;g<data.length;g++){
						 a = data[g].atid
				$('button#colbtn').each(function(i, e){
					if($(this).val()==a){
						$(this).text("已收藏")  }
						})
					}
				},
			})
		}
	}
	// 按讚比對
	function mylike() {
		if (chcekmember() == false) {
		}else{
			$.ajax({
				type : "post",
				url : "/CFA101G4/ArticleLikeServlet",
				dataType : 'json',
				data : {
					'action' : 'comparemyLike'
				},
				success : function(data) {
					let a ="";
					for(let g=0;g<data.length;g++){
						 a = data[g].atid
				$('button#likeart').each(function(i, e){
					if($(this).val()==a){
						let atlike = $(this).text()
						let hert = `<i class="bi-heart-fill" >${atlike}</i>`
						$(this).html(hert)  }
						})
					}
				},
			})
		}
	}
	// 以下為上方搜尋列
	$("#secbtn").click(function() {
		let title=$("#artname").val().trim();
		if(title.length<1){
			Keywordinput();
		}else{
			$.ajax({
			type : "GET",
			url : "/CFA101G4/article/ArticleServlet.do",
			dataType : 'json',
			data : {
				'action' : 'getSomeAt',
				'title' : title
			},
			cache : false,
			success : function(data) {
				if(data==""){
					noArticle();
				}else{
					let view ="";
					 for(let i=0;i<data.length;i++){
					view +=
						`<div class="card mb-3" id="articletext">
						<div class="row g-0">
							<div class="col-md-4 align-self-center" id="USRPHOTO">
							<img src="data:image/png;base64,${data[i].mempic}"
								class="img-fluid" alt="..." style="height:120px;">
								<div class="USRID"><p>${data[i].memname}</p>
								</div>
								<div><p id="posttime">發佈於${data[i].postime}</p></div>
							</div>
							<div class="col-md-8 shadow p-2 mb-5 bg-body rounded" id="textbox">
								<div class="card-body ">
									<h4 class="card-title">${data[i].title}</h4>
									<p class="card-text">${data[i].text}</p>
								</div>
								<div class="row mt-1" >
								<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
								<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
									收藏
								</button></div>
								<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart" >${data[i].like}</i></button></div>
								</div>
							</div>
						</div>
					</div>`;}
					$("#art").html(view) 
					myCol(); mylike(); 
					}
					},
				  })
				}
			})
				// 文章搜尋
					$("#artname").keypress(function() {
						let title=$("#artname").val().trim();
						if(event.which == 13 ){
							if(title.length<1){
								Keywordinput();
							}else{
							$.ajax({
							type : "GET",
							url : "/CFA101G4/article/ArticleServlet.do",
							dataType : 'json',
							data : {'action' : 'getSomeAt','title' : title },
							cache : false,
							success : function(data) {
								if(data==""){
									noArticle();
								}else{
									let view ="";
									 for(let i=0;i<data.length;i++){
									view +=
										`<div class="card mb-3" id="articletext">
										<div class="row g-0">
											<div class="col-md-4 align-self-center" id="USRPHOTO">
												<img src="data:image/png;base64,${data[i].mempic}"
													class="img-fluid" alt="..." style="height:120px;">
												<div class="USRID"><p>${data[i].memname}</p>
												</div>
												<div><p id="posttime">發佈於${data[i].postime}</p></div>
											</div>
											<div class="col-md-8 shadow p-2 mb-5 bg-body rounded" id="textbox">
												<div class="card-body ">
													<h4 class="card-title">${data[i].title}</h4>
													<p class="card-text">${data[i].text}</p>
												</div>
												<div class="row mt-1" >
												<div class="col" style="padding:0; margin-left: 10px"><a class="btn btn-secondary" href="/CFA101G4/ViewmoreArticleServlet?content=${data[i].atid}" target="_blank">查看更多</a></div>
												<div class="col" ><button  class="btn btn-secondary" id="colbtn" value="${data[i].atid}">
													收藏
												</button></div>
												<div class="col" ><button class="btn" id="likeart" value="${data[i].atid}"><i class="bi-heart" >${data[i].like}</i></button></div>
												</div>
											</div>
										</div>
									</div>`;}
									$("#art").html(view)
									myCol(); mylike();
									 }
										},
									})
								}
							}
						})

})
// 頁面載入執行END
//				討論區Q&A
				$("#qa").click(function() {
					let Reg = `<div class="container" style="background-color: white; opacity: 0.88; border-radius: 10px;">
								        <div class="row-cols-1 mt-2">
								            <div class="col position-static border-bottom">
								                <h4 class="pt-2">討論區注意事項</h4>
								            </div>
								            <div class="col p-3">
								                使用者須對自己所張貼之每一篇文章負責，本討論區無需對討論區內的言論負擔起任何的責任，責任的歸屬權屬
								                於各位發表人，在本討論區內發表的言論，智慧財產權均屬於原發表人，非經原發表人同意，包括討論區均嚴格禁止任
								                何未經授權的侵權行為。
								            </div>
								            <div class="col border-bottom">
								                <h4>發文應注意事項</h4>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    尊重他人意見，注意用字遣詞與口氣，避免引起爭吵 。
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    禁止重複刊登相同內容或相同意義之留言
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    不適當的廣告、宣傳活動或商業性留言
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    禁止發表謾罵、脅迫、挑釁、猥褻或不雅之文字
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    避免在公眾區域，討論私人事務
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    避免發表文章於非相關區域，文章標題及內容不符合討論區之討論主題
								                </p>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" style="text-decoration:underline">
								                    禁止發表個人測試用文章或散播不實消息之文章，張貼文章，應自負相關法律責任
								                </p>
								            </div>
								            <div class="col border-bottom">
								                <h4>違規處理</h4>
								            </div>
								            <div class="col p-3 ">
								                <p class="mb-0" >
								                    違反上述規定會員，將刪除該文章或禁止該會員發文。
								                </p>
								            </div>
								        </div>
								    </div>`
					$("#art").html(Reg)   
				})
								 


				// 讀取條動畫
				function loading() {
					setTimeout(function () { $('#loading').css("display", "none"); }, 1000);
				}
					
				// 按讚
				$(document).on('click','#likeart',function(e) {
					let like =$(this)
					let id = ($(this).val())
					if (chcekmember() == false) {
						remindmember();
					} else {
						$.ajax({
							type : "GET",
							url : "/CFA101G4/ArticleLikeServlet",
							dataType : 'json',
							data : {
								'action' : 'LIKE',
								'atid' : id
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
				// 收藏按鈕
				$(document).on('click','#colbtn',function(e) {
					let i =$(this)
					let id = ($(this).val())
					if (chcekmember() == false) {
						remindmember();
					} else {
						$.ajax({
							type : "GET",
							url : "/CFA101G4/ArticlecollectionServlet",
							dataType : 'json',
							data : {
								'action' : 'COLLECTION',
								'atid' : id
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
					// 關鍵字輸入卡控
					function Keywordinput() {
						Swal.fire({
							  icon: 'warning',
							  text: '請輸入關鍵字',
						});
					}
					// 無相關文章提示
					function noArticle() {
						Swal.fire({
							  icon: 'question',
							  text: '查無相關文章',
						});
					}

			
				
