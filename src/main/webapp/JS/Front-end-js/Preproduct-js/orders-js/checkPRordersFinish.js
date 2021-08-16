let start = 0;
let rows = 16;

window.onload = function() {
	frist();
}


$(window).scroll(function() {
	if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
		frist();
	}
})

function frist() {
	$.ajax({
		url: "/CFA101G4/PoServlet",
		type: "POST",
		data: {
			"action": "getSelfOrders",
			"start": start,
			"rows": rows,
			"posta" : "5"
		},
		dataType: "json",
		success: function(data) {
			console.log(data)

			var obj = data;





			for (let i = 0; i < data.length; i++) {
				btn = ""


				if (data[i].poVO.po_sta == 2) {
					btn = `
                    <button type="button" class="btn btn-secondary" id="cancel" value="${data[i].poVO.po_id}" >取消訂單</button>
               
                    `
				}

				if (data[i].poVO.po_sta == 3) {
					btn = `
                    <button type="button" class="btn btn-secondary" id="cancel" value="${data[i].poVO.po_id}" >取消訂單</button>
                    `
				}

				if (data[i].poVO.po_sta == 4) {
					btn = `
                <button type="button" id="check" value="${data[i].poVO.po_id}" class="btn btn-secondary"  >確認收貨</button>
                <button type="button" id= "return" value="${data[i].poVO.po_id}" class="btn btn-secondary">申請退貨</button>
               
                `
				}

				if (data[i].poVO.po_sta == 5) {
					
					if(data[i].poVO.po_iscom == 0){
					btn = `
                   
                    <button type="button" class="btn btn-success"data-bs-toggle="modal" data-bs-target="#exampleModal-${data[i].poVO.po_id}" data-comment="${data[i].poVO.po_id}" id="comment-${data[i].poVO.po_id}">評論賣家</button>
                <div class="modal fade" id="exampleModal-${data[i].poVO.po_id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">評價</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="關閉"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="customRange2" class="form-label">評價分數</label><span class="rating" id="rating-${data[i].poVO.po_id}">(預設為3分)</span>
                                    <input type="range" name="foo" class="form-range" min="1" max="5" data-range="${data[i].poVO.po_id}" id="customRange-${data[i].poVO.po_id}">
                                    
                                </div>
                                <div class="mb-3">
                                    <label for="message-text" class="col-form-label">請留下您的評論:</label>
                                    <textarea class="form-control" data-message="msg-${data[i].poVO.po_id}" id="message-${data[i].poVO.po_id}"></textarea>
                                </div>
                            
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
                                <button type="button" class="btn btn-primary" data-sendmsg="${data[i].poVO.po_id}">送出評價</button>
                            </div>
                        </div>                
                    </div>
                </div>
                    
                    `
                    }else{
                    btn = `
                    
                    `
                    }
				
				
				}

				
				
				if (data[i].poVO.po_sta == 9) {
					btn = ""
				}




				let html = ` 
            <div class="col shadow-sm p-3 mb-5 bg-white rounded" style="background-color: white; margin-bottom: 15px!important;">
                    <div class="col border border-$gray-500">
                        <div class="row">
                            <div class="col text-start">
                                <div class="row">
                                    <div class="col-3 align-self-center text-center">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-award" viewBox="0 0 16 16">
                                            <path d="M9.669.864 8 0 6.331.864l-1.858.282-.842 1.68-1.337 1.32L2.6 6l-.306 1.854 1.337 1.32.842 1.68 1.858.282L8 12l1.669-.864 1.858-.282.842-1.68 1.337-1.32L13.4 6l.306-1.854-1.337-1.32-.842-1.68L9.669.864zm1.196 1.193.684 1.365 1.086 1.072L12.387 6l.248 1.506-1.086 1.072-.684 1.365-1.51.229L8 10.874l-1.355-.702-1.51-.229-.684-1.365-1.086-1.072L3.614 6l-.25-1.506 1.087-1.072.684-1.365 1.51-.229L8 1.126l1.356.702 1.509.229z"/>
                                            <path d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1 4 11.794z"/>
                                        </svg>${data[i].poVO.po_smemid}
                                    </div>
                                </div>
                            </div>
                            <div class="col text-end"></div>

                        </div>
                    
                    </div>
                    <div id="goods-${data[i].poVO.po_id}">
                   
                    </div>   
                        
                    <div class="row align-items-center">
                        <div class="col-6 text-center align-self-end" id="sum-${data[i].poVO.po_id}">
                        </div>
                        <div class="col-6 text-end">
                        `+ btn + `
                        </div>
                    </div>
            </div>
                    `


				$("#ajaxchange").append(html);
				let sumofprice = 0;


				if (data[i].poVO.po_sta == 0) {
					data[i].poVO.po_sta = '等待預購結束日'
				} else if (data[i].poVO.po_sta == 1) {
					data[i].poVO.po_sta = '預購未達標不出貨'
				} else if (data[i].poVO.po_sta == 2) {
					data[i].poVO.po_sta = '達標等待賣家確認'
				} else if (data[i].poVO.po_sta == 3) {
					data[i].poVO.po_sta = '賣家確認訂單等待出貨'
				} else if (data[i].poVO.po_sta == 4) {
					data[i].poVO.po_sta = '已出貨(買家尚未收貨)'
				} else if (data[i].poVO.po_sta == 5) {
				
					if (data[i].poVO.po_iscom == 0){
					data[i].poVO.po_sta = '訂單買家已取貨未評論'
					}else{
					data[i].poVO.po_sta = '訂單買家已取貨且評論'
					}
				
				} else if (data[i].poVO.po_sta == 6){
					data[i].poVO.po_sta = '訂單已退貨'
				}else {
					data[i].poVO.po_sta = '訂單已取消'
				}



				let html2 = ` <div class="col">
						<div class="row border border-$gray-500" style="height: 100px;">
							<div class="col-2 text-center align-self-center" style="margin-top: 5px; data-pic="${data[i].poVO.po_id}" id="pic${data[i].poVO.po_id}-${data[i].poVO.po_id}">
								${getonepic(data[i].pdVO.pd_id)}
							</div>
							<div class="col-8">
								 ${data[i].pdVO.pd_name}*${data[i].poVO.po_qty}<br>
								 <div class="carddesc">${data[i].pdVO.pd_desc}</div>
                                 ${data[i].pdVO.pd_price}
							</div>
                            <div class="col-2 align-self-center" style="padding-left: 0px; padding-right: 0px; font-size: 15px;text-align: center;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-truck" viewBox="0 0 16 16">
                                <path d="M0 3.5A1.5 1.5 0 0 1 1.5 2h9A1.5 1.5 0 0 1 12 3.5V5h1.02a1.5 1.5 0 0 1 1.17.563l1.481 1.85a1.5 1.5 0 0 1 .329.938V10.5a1.5 1.5 0 0 1-1.5 1.5H14a2 2 0 1 1-4 0H5a2 2 0 1 1-3.998-.085A1.5 1.5 0 0 1 0 10.5v-7zm1.294 7.456A1.999 1.999 0 0 1 4.732 11h5.536a2.01 2.01 0 0 1 .732-.732V3.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5v7a.5.5 0 0 0 .294.456zM12 10a2 2 0 0 1 1.732 1h.768a.5.5 0 0 0 .5-.5V8.35a.5.5 0 0 0-.11-.312l-1.48-1.85A.5.5 0 0 0 13.02 6H12v4zm-9 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm9 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                            </svg>${data[i].poVO.po_sta}
                            </div>
                              
						</div>
					</div>`
				$(`#goods-${data[i].poVO.po_id}`).append(html2);


				$(`#sum-${data[i].poVO.po_id}`).html("合計金額：" + data[i].poVO.po_price + "元");

				function getonepic(pd_id) {
					let pic64 = "";
					$.ajax({
						url: "/CFA101G4/PiServlet",
						type: "GET",
						async: false,
						data: {
							"action": "getimg",
							"pdid": pd_id
						},
						success: function(data) {
							pic64 = `<img src="data:image/gif;base64,${data}" alt="">`
						}
					})
					return pic64;
				}



			}

			start += rows
			let len = 100;
	        $(".carddesc").each(function(i){
	              if($(this).text().length>len){
	              $(this).attr("title", $(this).text());
	              let text  = $(this).text().substring(0, len-1)+"...";
	              $(this).text(text);
	       }
	    })
		},
		error: function(data) {
			alert("有誤！")
		}
	})
}

$(function() {
	$("#ajaxchange").on("click", '[data-comment]', function(e) {
		let po_id = e.target.dataset.comment
		console.log(po_id)


		$("#exampleModal-" + po_id).on("click", '[data-sendmsg]', function(e) {
			let msg = $("#message-" + po_id).val();
			console.log(po_id);
			console.log(msg);
			let rating = $("#customRange-" + po_id).val()
			console.log(rating);
			$.ajax({
				url: "/CFA101G4/PoServlet",
				type: "POST",
				data: {
					"action": "submitComment",
					"msg": msg,
					"rating": rating,
					"po_id": po_id,
					"po_sta": 5,
					"po_iscom" : "1"
				},
				success: function(data) {
					console.log(data)

					$("#exampleModal-" + po_id).modal('hide');
					window.location.reload();
				},
				error: function() {
					alert("提交評價失敗！")
				}
			})

		})



		$("#exampleModal-" + po_id).on("input", '[data-range]', function(e) {
			let val = $("#customRange-" + po_id).val()
			console.log(val)
			$("#rating-" + po_id).html(val + "分");

		})


	})

})


$(function() {
	$("#ajaxchange").on("click",function(e) {
		console.log(e.target)
		if($(e.target).attr("id") == 'check'){
			let po_id = $(e.target).val()
			
			console.log(po_id)
			swal({
				title: '確定收貨嗎？',
				text: '',
				type: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
				confirmButtonText: '確定！',
				cancelButtonText: "取消",
			}).then(function(value) {
				if (value.value) {
					$.ajax({
						url: "/CFA101G4/PoServlet",
						data: {
							PO_ID: po_id,
							PO_STA: "5",
							PO_GOLDFLOW : "1",
							action: "updateBOrderSta"
						},
						success: function() {
							swal(
								"成功收貨",
								"可以去評論訂單囉！",
								"success"
							)
							window.location.reload();
						}
					})
				} else {
					swal('已取消操作！', '尚未收貨', 'error')
				}
			})
			}
		})
	
})




$(function() {
	$("#ajaxchange").on("click",function(e) {
		console.log(e.target)
		if($(e.target).attr("id") == 'cancel'){
			let po_id = $(e.target).val()
			
			console.log(po_id)
			swal({
				title: '確定要取消訂單嗎？',
				text: '無故取消可能會被檢舉哦!',
				type: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
				confirmButtonText: '確定！',
				cancelButtonText: "取消",
			}).then(function(value) {
				if (value.value) {
					$.ajax({
						url: "/CFA101G4/PoServlet",
						data: {
							PO_ID: po_id,
							PO_STA: "9",
							action: "updateBOrderSta",
							PO_GOLDFLOW:"2"
						},
						success: function() {
							swal(
								"成功取消訂單",
								"success"
							)
							window.location.reload();
						}
					})
				} else {
					swal('已取消操作！', '尚未確認訂單', 'error')
				}
			})
			}
		})
	
})


$(function() {
	$("#ajaxchange").on("click",function(e) {
		console.log(e.target)
		if($(e.target).attr("id") == 'return'){
			let po_id = $(e.target).val()
			
			console.log(po_id)
			swal({
				title: '確定要退貨嗎？',
				text: '無故退貨可能會被檢舉哦!',
				type: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
				confirmButtonText: '確定！',
				cancelButtonText: "取消",
			}).then(function(value) {
				if (value.value) {
					$.ajax({
						url: "/CFA101G4/PoServlet",
						data: {
							PO_ID: po_id,
							PO_STA: "6",
							PO_GOLDFLOW : "2",
							action: "updateBOrderSta"
						},
						success: function() {
							swal(
								"成功退貨訂單",
								"success"
							)
							window.location.reload();
						}
					})
				} else {
					swal('已取消操作！', '尚未退貨訂單', 'error')
				}
			})
			}
		})
	
})