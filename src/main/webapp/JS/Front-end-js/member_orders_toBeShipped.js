


$.ajax({
    url:"/CFA101G4/OrdersServlet",
    type:"POST",
    data:{"action":"checkOrdersByOd_sta",
    	  "od_sta":2
            },
    dataType:"json",
    success:function(data){
        console.log(data)
		
        var obj = data;
        
        for(var key in obj){
			console.log(obj[key][0].memberVO.mem_acc)

				let html = ` <div class="col shadow-sm p-3 mb-5 bg-white rounded" style="background-color: white; margin-bottom: 15px!important;">
                <div class="col border border-$gray-500">
                    <div class="row">
                        <div class="col text-start">
                            <div class="row">
                                <div class="col-3 align-self-center text-center">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-award" viewBox="0 0 16 16">
                                        <path d="M9.669.864 8 0 6.331.864l-1.858.282-.842 1.68-1.337 1.32L2.6 6l-.306 1.854 1.337 1.32.842 1.68 1.858.282L8 12l1.669-.864 1.858-.282.842-1.68 1.337-1.32L13.4 6l.306-1.854-1.337-1.32-.842-1.68L9.669.864zm1.196 1.193.684 1.365 1.086 1.072L12.387 6l.248 1.506-1.086 1.072-.684 1.365-1.51.229L8 10.874l-1.355-.702-1.51-.229-.684-1.365-1.086-1.072L3.614 6l-.25-1.506 1.087-1.072.684-1.365 1.51-.229L8 1.126l1.356.702 1.509.229z"/>
                                        <path d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1 4 11.794z"/>
                                      </svg>${obj[key][0].memberVO.mem_acc}
                                </div>
                            </div>
                            
                        </div>
                        <div class="col text-end">
                            
                        </div>
                    </div>
                    
                </div>
                <div id="goods-${key}">
                   
                </div>   
                        
                    <div class="row align-items-center">
                        <div class="col-6 text-center align-self-end" id="sum-${key}">
                                	
                        </div>
                        <div class="col-6 text-end">
                            <div class="col-3 align-items-center text-center" style="margin-left:330px;">  
                                <button type="button" class="btn btn-danger" data-odid="${obj[key][0].ordersVO.od_id}" id="refund-${key}">取消訂單</button>
                            </div>  
                        </div>
                    </div>
            </div>`
			$("#ajaxchange").append(html);
			let sumofprice= 0;
			
		for(const iterator of data[key]){
                if(iterator.ordersVO.od_sta==0){
                    iterator.ordersVO.od_sta='訂單未成立' 
                }else if(iterator.ordersVO.od_sta==2){
                    iterator.ordersVO.od_sta='訂單已成立'      
                }else if(iterator.ordersVO.od_sta==3){
                    iterator.ordersVO.od_sta='訂單已出貨'
                }else if(iterator.ordersVO.od_sta==4){
                    iterator.ordersVO.od_sta='訂單售後處理中'
                }else if(iterator.ordersVO.od_sta==5){
                    iterator.ordersVO.od_sta='訂單買家未收貨'
                }else if(iterator.ordersVO.od_sta==6){
                    iterator.ordersVO.od_sta='訂單退款完成'
                }else if(iterator.ordersVO.od_sta==7){
                    iterator.ordersVO.od_sta='訂單完成'
                }else if(iterator.ordersVO.od_sta==8){
                    iterator.ordersVO.od_sta='訂單取消'
                }else if(iterator.ordersVO.od_sta==9){
                    iterator.ordersVO.od_sta='訂單已撥款'
                }else if(iterator.ordersVO.od_sta==10){
                    iterator.ordersVO.od_sta='訂單已評價'
                }
                
               
				
				let html2 =` <div class="col">
						<div class="row border border-$gray-500" style="height: 100px;">
							<div class="col-2 text-center align-self-center" style="margin-top: 5px; data-pic="${iterator.productVO.p_id}" id="pic${key}-${iterator.productVO.p_id}">
								${getonepic(iterator.productVO.p_id)}
							</div>
							<div class="col-8">
								 ${iterator.productVO.p_name}*${iterator.ordersListVO.ol_pq}<br>
								 <div class="carddesc">${iterator.productVO.p_desc}</div>
                                 $${iterator.ordersListVO.ol_price}
							</div>
                            <div class="col-2 align-self-center" style="padding-left: 0px; padding-right: 0px; font-size: 15px;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-truck" viewBox="0 0 16 16">
                                <path d="M0 3.5A1.5 1.5 0 0 1 1.5 2h9A1.5 1.5 0 0 1 12 3.5V5h1.02a1.5 1.5 0 0 1 1.17.563l1.481 1.85a1.5 1.5 0 0 1 .329.938V10.5a1.5 1.5 0 0 1-1.5 1.5H14a2 2 0 1 1-4 0H5a2 2 0 1 1-3.998-.085A1.5 1.5 0 0 1 0 10.5v-7zm1.294 7.456A1.999 1.999 0 0 1 4.732 11h5.536a2.01 2.01 0 0 1 .732-.732V3.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5v7a.5.5 0 0 0 .294.456zM12 10a2 2 0 0 1 1.732 1h.768a.5.5 0 0 0 .5-.5V8.35a.5.5 0 0 0-.11-.312l-1.48-1.85A.5.5 0 0 0 13.02 6H12v4zm-9 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm9 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                            </svg>${iterator.ordersVO.od_sta}
                            </div>
                                
						</div>
					</div>`
					$(`#goods-${key}`).append(html2);
                
                
                 $(`#sum-${key}`).html("合計金額："+iterator.ordersVO.od_price+"元");	

                function getonepic(pi_pid){
                    let pic64="";
                    $.ajax({
                        url:"/CFA101G4/ProductImgServlet",
                        type:"GET",
                        async: false,
                        data:{
                            "action":"getonepic",
                            "pi_pid":pi_pid
                        },
                        dataType:"json",
                        success:function(data){
                            pic64=`<img src="data:image/gif;base64,${data}" alt="">`
                        }
                    })
                    return pic64;
                }
    
			}
			
		
	}

        let len = 100;
        $(".carddesc").each(function(i){
              if($(this).text().length>len){
              $(this).attr("title", $(this).text());
              let text  = $(this).text().substring(0, len-1)+"...";
              $(this).text(text);
       }
    })
		
	
    },
    error:function(data){
        alert("有誤！")
    }
})

            //取消訂單 將狀態改成8
            $("#ajaxchange").on("click", "[data-odid]",function(e){
                swal({
                    title: `確定要取消訂單${e.target.dataset.odid}嗎？`,
                    text: '此舉有可能影響您的信用評分！',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: '確定！',
                    cancelButtonText: '取消',
                }).then(function(value) {
                    if (value.value) {
                        console.log(e.target.dataset.odid)
                        let odid = e.target.dataset.odid;
                        $.ajax({
                            url:"/CFA101G4/OrdersServlet",
                            type:"GET",
                            data:{
                                "action":"ChangeOrderSta",
                                "od_id":odid,
                                "od_sta":8
                            },
                            success:function(data){
                                console.log(data)
                                swal({
                                    title: '雖然感到很遺憾',
                                    text: '但我們期待您的再度光臨！',
                                    type: 'success',
                                    confirmButtonColor: '#d3d3d3',
                                    
                                    confirmButtonText: '確定！',
                                }).then(function(value) {
                                    if (value.value) {
                                        window.location.reload()
                                    }
                                })
                            }
                        })
                    
                    } 
            })
        })	