let start = 0;
let rows = 16;

window.onload = function () {
    frist();
}


$(window).scroll(function () {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        frist();
    }
})

function frist() {
    $.ajax({
        url: "/CFA101G4/bid.do",
        type: "POST",
        data: {
            "action": "getSelfBuyOrdersAll",
            "start": start,
            "rows": rows
        },
        dataType: "json",
        success: function (data) {
            console.log(data)

            var obj = data;

            for (let i = 0; i < data.length; i++) {

                let html = ` <div class="col shadow-sm p-3 mb-5 bg-white rounded" style="background-color: white; margin-bottom: 15px!important;">
                <div class="col border border-$gray-500">
                    <div class="row">
                        <div class="col text-start">
                            <div class="row">
                                <div class="col-3 align-self-center text-center">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-award" viewBox="0 0 16 16">
                                        <path d="M9.669.864 8 0 6.331.864l-1.858.282-.842 1.68-1.337 1.32L2.6 6l-.306 1.854 1.337 1.32.842 1.68 1.858.282L8 12l1.669-.864 1.858-.282.842-1.68 1.337-1.32L13.4 6l.306-1.854-1.337-1.32-.842-1.68L9.669.864zm1.196 1.193.684 1.365 1.086 1.072L12.387 6l.248 1.506-1.086 1.072-.684 1.365-1.51.229L8 10.874l-1.355-.702-1.51-.229-.684-1.365-1.086-1.072L3.614 6l-.25-1.506 1.087-1.072.684-1.365 1.51-.229L8 1.126l1.356.702 1.509.229z"/>
                                        <path d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1 4 11.794z"/>
                                      </svg>${data[i].bp_bmemid}
                                </div>
                                <div class="col-2 text-center align-items-center p-0">
                                    
                                      <button type="button" class="btn btn-danger align-self-center" style="font-size: 12px;"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-chat-dots-fill" viewBox="0 0 16 16">
                                        <path d="M16 8c0 3.866-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.584.296-1.925.864-4.181 1.234-.2.032-.352-.176-.273-.362.354-.836.674-1.95.77-2.966C.744 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7zM5 8a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                                      </svg> 聊聊</button>
                                </div>
                                <div class="col-2 text-center align-items-center p-0">
                                    <button type="button" class="btn btn-light" style="font-size: 12px;padding-right: 0px; padding-left: 0px;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-fill" viewBox="0 0 16 16">
                                        <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5z"/>
                                      </svg>查看賣場</button>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col text-end">
                            
                        </div>
                    </div>
                    
                </div>
                <div id="goods-${data[i].bp_id}">
                   
                </div>   
                        
                    <div class="row align-items-center">
                        <div class="col-6 text-center align-self-end" id="sum-${data[i].bp_id}">
                                	
                        </div>
                        <div class="col-6 text-end">
                            <button type="button" class="btn btn-primary">再買一次</button>
                            <button type="button" class="btn btn-secondary">聯絡賣家</button>
                            <button type="button" class="btn btn-success"data-bs-toggle="modal" data-bs-target="#exampleModal-${data[i].bp_id}" data-comment="${data[i].bp_id}" id="comment-${data[i].bp_id}">評論賣家</button>
                        </div>
                    </div>
                    <div class="modal fade" id="exampleModal-${data[i].bp_id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  
						<div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLabel">評價</h5>
						        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="關閉"></button>
						      </div>
						      <div class="modal-body">
						        
                                    <div class="mb-3">
                                        <label for="customRange2" class="form-label">評價分數</label><span class="rating" id="rating-${data[i].bp_id}">(預設為3分)</span>
                                        <input type="range" name="foo" class="form-range" min="1" max="5" data-range="${data[i].bp_id}" id="customRange-${data[i].bp_id}">
                                        
                                    </div>
                                    <div class="mb-3">
                                        <label for="message-text" class="col-form-label">請留下您的評論:</label>
                                        <textarea class="form-control" data-message="msg-${data[i].bp_id}" id="message-${data[i].bp_id}"></textarea>
                                    </div>
						        
						      </div>
						      <div class="modal-footer">
						        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
						        <button type="button" class="btn btn-primary" data-sendmsg="${data[i].bp_id}">送出評價</button>
						      </div>
						    </div>                
						  </div>
			        </div>
						                    
            </div>`
                $("#ajaxchange").append(html);
                let sumofprice = 0;

                if (data[i].bp_shsta == 1) {
                    data[i].bp_shsta = '訂單尚未填寫收貨資訊'
                } else if (data[i].bp_shsta == 2) {
                    data[i].bp_shsta = '訂單尚未出貨'
                } else if (data[i].bp_shsta == 3) {
                    data[i].bp_shsta = '訂單已出貨'
                } else if (data[i].bp_shsta == 4) {
                    data[i].bp_shsta = '訂單已完成'
                } else {
                    data[i].bp_shsta = '訂單取消'
                }



                let html2 = ` <div class="col">
						<div class="row border border-$gray-500" style="height: 100px;">
							<div class="col-2 text-center align-self-center" style="margin-top: 5px; data-pic="${data[i].bp_id}" id="pic${data[i].bp_id}-${data[i].bp_id}">
								${getonepic(data[i].bp_id)}
							</div>
							<div class="col-8">
								 ${data[i].bp_name}<br>
								 ${data[i].bp_desc}<br>
                                 ${data[i].bp_to}
							</div>
                            <div class="col-2 align-self-center" style="padding-left: 0px; padding-right: 0px; font-size: 15px;text-align: center;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-truck" viewBox="0 0 16 16">
                                <path d="M0 3.5A1.5 1.5 0 0 1 1.5 2h9A1.5 1.5 0 0 1 12 3.5V5h1.02a1.5 1.5 0 0 1 1.17.563l1.481 1.85a1.5 1.5 0 0 1 .329.938V10.5a1.5 1.5 0 0 1-1.5 1.5H14a2 2 0 1 1-4 0H5a2 2 0 1 1-3.998-.085A1.5 1.5 0 0 1 0 10.5v-7zm1.294 7.456A1.999 1.999 0 0 1 4.732 11h5.536a2.01 2.01 0 0 1 .732-.732V3.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5v7a.5.5 0 0 0 .294.456zM12 10a2 2 0 0 1 1.732 1h.768a.5.5 0 0 0 .5-.5V8.35a.5.5 0 0 0-.11-.312l-1.48-1.85A.5.5 0 0 0 13.02 6H12v4zm-9 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm9 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                            </svg>${data[i].bp_shsta}
                            </div>
                              
						</div>
					</div>`
                $(`#goods-${data[i].bp_id}`).append(html2);


                $(`#sum-${data[i].bp_id}`).html("合計金額：" + data[i].bp_to + "元");

                function getonepic(bp_id) {
                    let pic64 = "";
                    $.ajax({
                        url: "/CFA101G4/BpImageServlet",
                        type: "GET",
                        async: false,
                        data: {
                            "action": "getimg",
                            "bpid": bp_id
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data)
                            pic64 = `<img src="data:image/gif;base64,${data}" alt="">`
                        }
                    })
                    return pic64;
                }



            }

            start += rows
        },
        error: function (data) {
            alert("有誤！")
        }
    })
}

$(function () {
    $("#ajaxchange").on("click", '[data-comment]', function (e) {
        let od_id = e.target.dataset.comment
        console.log(od_id)


        $("#exampleModal-" + od_id).on("click", '[data-sendmsg]', function (e) {
            let msg = $("#message-" + od_id).val();
            console.log(od_id);
            console.log(msg);
            let rating = $("#customRange-" + od_id).val()
            console.log(rating);
            $.ajax({
                url: "/CFA101G4/OrdersServlet",
                type: "POST",
                data: {
                    "action": "submitComment",
                    "msg": msg,
                    "rating": rating,
                    "od_id": od_id,
                    "od_sta": 10
                },
                success: function (data) {
                    console.log(data)

                    $("#exampleModal-" + od_id).modal('hide');
                    window.location.reload();
                },
                error: function () {
                    alert("提交評價失敗！")
                }
            })

        })
        $("#exampleModal-" + od_id).on("input", '[data-range]', function (e) {
            let val = $("#customRange-" + od_id).val()
            console.log(val)
            $("#rating-" + od_id).html(val + "分");

        })
    })

})

