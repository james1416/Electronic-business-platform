
window.onload = function(){
	let start = 0;
	let rows = 16;
	let sta = $("#selectSTA").val();
	frist(start,rows,sta);
	start += rows
	scroll(start ,rows ,sta);
}	


$("#allsubmit").on("click",function(){
	let point = false;
	let formData = new FormData();
	let i = $(".submitcheck").length
	formData.append('action','submitAll')
	for(let y = 0 ; y < i ; y++){
	if($(".submitcheck:eq("+y+")").prop('checked')){
			let odid = $(".submitcheck:eq("+y+")").val();
			formData.append('submitodid', odid)
			point = true;
		}
	}
	let z = $(".refundcheck").length;
	for(let y = 0 ; y < z ; y++){
		if($(".refundcheck:eq("+y+")").prop('checked')){
				let odid = $(".refundcheck:eq("+y+")").val();
				formData.append('refundodid', odid)
				point = true;
			}
		}
	
	if(!point){
		swal("請勾選訂單")
		return;
	}else{
		swal({
	        title: '確定撥/退款至選定的訂單嗎？',
	        text: '確認後將無法復原！',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#d33',
	        cancelButtonColor: '#3085d6',
	        confirmButtonText: '確定！',
	        cancelButtonText: "取消",
	    }).then(function(value) {
	        if (value.value) {
	        	$.ajax({
	    			url:"/CFA101G4/PoServlet",
	    		    contentType: false,
	    		    processData: false,
	    			type:"post",
	    			data: formData,
	                success: function() {
	                    	window.location.href = "/CFA101G4/back-end/Orders/selectPO.jsp"
	                }
	            })
	        } else {
	            swal('已取消操作！', '', 'error')
	        }
		})
	}
})






$("#selectSTA").on("change",function(){
	$(window).off();
	$(".listbody").remove();
	let start = 0;
	let rows = 16;
	let sta = $(this).val();
	if($(this).val() == ""){
		frist(start,rows);
		start += rows;
		scroll(start , rows ,sta);
	}
	
	if($(this).val() != ""){
		one(start,rows,sta);
		start += rows;
		scroll(start , rows ,sta);
		
	}
	
})


$("#checkbox").on("change",function(){
	if($("#checkbox").prop('checked')){
		let i = $(".submitcheck").length;
		for(let y = 0 ; y < i ; y++){
			$(".submitcheck:eq("+y+")").prop("checked" , true)
		}
		let z = $(".refundcheck").length;
		for(let y = 0 ; y < z ; y++){
			$(".refundcheck:eq("+y+")").prop("checked" , true)
		}

	}
	if(!$("#checkbox").prop('checked')){
		let i = $(".submitcheck").length
		for(let y = 0 ; y < i ; y++){
			$(".submitcheck:eq("+y+")").prop("checked" , false)
		}
		let z = $(".refundcheck").length
		for(let y = 0 ; y < z ; y++){
			$(".refundcheck:eq("+y+")").prop("checked" , false)
		}
	}
})

function scroll(start,rows,sta){
	$(window).scroll(function(){
	    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
	    	if(sta == ""){
	    		frist(start,rows);
	    		start +=rows
	    	}
	    	
	    	if(sta != ""){
	    		one(start,rows,sta);
	    		start +=rows
	    	}
	    }
	})

}



$("#delModal").on("click",function(){
	$("#showodBody").remove();
});
		


function frist(start,rows,sta){
 			$.ajax({
 				url:"/CFA101G4/PoServlet",
 				type:"POST",
 				data:{
 					action:"adminGetOD",
 					start : start,
 					rows : rows,
 					posta : sta
 					},
 				dataType:"json",
 				success:function(data){
 					console.log(data);
 					if(data.length != rows){
					     $("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
					}
 					for(i=0;i<data.length;i++){
 					data[i].po_pay==0?data[i].po_pay='超商取貨':data[i].po_pay='宅配配送';
 					
 					button = ``;
 					checkbox =``;
 					sta=``;
 					
 					if(data[i].po_sta == 5 && data[i].po_goldflow == 1 ){
 						button = `<button type="button" id="submit" class="btn btn-primary m-0" value="${data[i].po_id}">撥款</button>
 						`;
 						checkbox=`<input class="submitcheck" type="checkbox" name="submitodid" value="${data[i].po_id}" >`
 						sta=`<button type="button" class="btn btn-secondary" disabled>待撥款</button>`;
 					}else if(data[i].po_sta == 9 && data[i].po_goldflow == 2 || data[i].po_sta == 6 && data[i].po_goldflow == 2){
 						button = `<button type="button" id="refund" class="btn btn-danger m-0" value="${data[i].po_id}">退款</button>
 						`;
 						checkbox=`<input  class="refundcheck" type="checkbox" name="refundodid" value="${data[i].po_id}" >`;
 						sta=`<button type="button" class="btn btn-secondary" disabled>待退款</button>`;
 					}else if(data[i].po_sta == 9 && data[i].po_goldflow == 4 || data[i].po_sta == 6 && data[i].po_goldflow == 4){
 						button = `<button type="button" id="refund" class="btn btn-danger m-0" value="${data[i].po_id}" disabled>已退款</button>
 	 						`;
 						sta=`<button type="button" class="btn btn-success" disabled>已退款</button>`;
 					}else if( data[i].po_sta == 5 && data[i].po_goldflow == 3 ){
 						button = `<button type="button" id="refund" class="btn btn-primary m-0" value="${data[i].po_id}" disabled>已撥款</button>
 						`;
 						sta=`<button type="button" class="btn btn-success" disabled>已撥款</button>`;
 					}else{
 						continue;
 					}
 					
 					
 					if(data[i].po_pay==0){
 						data[i].po_pay='信用卡'
 					}else{
 						data[i].po_pay='錢包付款'
 					}
 					let tr = `
 					<div class="listbody">
 						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
				            <div class="col-1 text-center align-self-center">`+
				            	checkbox
				              +`</div>
				              <div class="col-1 text-center align-self-center">${data[i].po_id}</div>
				              <div class="col-1 text-center align-self-center">${data[i].po_price}</div>
				              <div class="col-2 text-center align-self-center">${data[i].po_date}</div>
				              <div class="col-1 text-center align-self-center">${data[i].po_pay}</div>
				              <div class="col-2 text-center align-self-center" id="odsta${data[i].po_id}">`+sta+`</div>
				              <div class="col-1 text-center align-self-center">${data[i].po_point}</div>
				              <div class="col-3 text-center align-self-center">`+
				              	button
				               +`<button type="button" id="showOD" value="${data[i].po_id}" class="btn btn-secondary m-0">查看訂單</button>
				              </div>
				        </div>
 					</div>
		 			          `
 						$("#moremsg").before(tr);
 					}
 				}
 			})
}

function one(start,rows,sta){
		$.ajax({
			url:"/CFA101G4/PoServlet",
			type:"POST",
			data:{
				action:"adminGetOD",
				start : start,
				rows : rows,
				posta : sta
			},
			dataType:"json",
			success:function(data){
				console.log(data);
				for(i=0;i<data.length;i++){
				if(data.length != rows){
				     $("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
				}
				data[i].od_shipping==0?data[i].od_shipping='超商取貨':data[i].od_shipping='宅配配送';
				
				button = ``;
				checkbox =``;
				sta=``;
					
					if(data[i].po_sta == 5 && data[i].po_goldflow == 1){
						button = `<button type="button" id="submit" class="btn btn-primary m-0" value="${data[i].po_id}">撥款</button>
						`;
						checkbox=`<input class="submitcheck" type="checkbox" name="submitodid" value="${data[i].po_id}" >`
						sta=`<button type="button" class="btn btn-secondary" disabled>待撥款</button>`;
					}else if(data[i].po_sta == 9 && data[i].po_goldflow == 2 ){
						button = `<button type="button" id="refund" class="btn btn-danger m-0" value="${data[i].po_id}">退款</button>
						`;
						checkbox=`<input  class="refundcheck" type="checkbox" name="refundodid" value="${data[i].po_id}" >`;
						sta=`<button type="button" class="btn btn-secondary" disabled>待退款</button>`;
					}else if(data[i].po_sta == 9 && data[i].po_goldflow == 4 ){
						button = `<button type="button" id="refund" class="btn btn-danger m-0" value="${data[i].po_id}" disabled>已退款</button>
	 						`;
						sta=`<button type="button" class="btn btn-success" disabled>已退款</button>`;
					}else if(data[i].po_sta == 5 && data[i].po_goldflow == 3){
						button = `<button type="button" id="refund" class="btn btn-primary m-0" value="${data[i].po_id}" disabled>已撥款</button>
						`;
						sta=`<button type="button" class="btn btn-success" disabled>已撥款</button>`;
					}else{
						continue;
					}
				
				
				if(data[i].po_pay==0){
					data[i].po_pay='信用卡'
				}else{
					data[i].po_pay='錢包付款'
				}
				let tr = `
				<div class="listbody">
					<div class="row justify-content-center border-bottom border-$gray-500 p-3">
		            <div class="col-1 text-center align-self-center">`+
		            	checkbox
		              +`</div>
		              <div class="col-1 text-center align-self-center">${data[i].po_id}</div>
		              <div class="col-1 text-center align-self-center">${data[i].po_price}</div>
		              <div class="col-2 text-center align-self-center">${data[i].po_date}</div>
		              <div class="col-1 text-center align-self-center">${data[i].po_pay}</div>
		              <div class="col-2 text-center align-self-center" id="odsta${data[i].po_id}">`+sta+`</div>
		              <div class="col-1 text-center align-self-center">${data[i].po_point}</div>
		              <div class="col-3 text-center align-self-center">`+
		              	button
		               +`<button type="button" id="showOD" value="${data[i].po_id}" class="btn btn-secondary m-0">查看訂單</button>
		              </div>
		        </div>
				</div>
 			          `
					$("#moremsg").before(tr);
				}
			}
		})
}




 	$("#orderBody").on("click" , function(e){
 		
 		console.log(e.target.id)
 				if(e.target.id == "submit"){
 					let value = e.target.value;
 					$(e.target).parent().prev().prev().prev().prev().prev().prev().prev().html("");
 	 				$.ajax({
 	 					url:"/CFA101G4/PoServlet",
 	 					type:"POST",
 	 					data:{
 	 						"action":"POdone",
 	 						"poid":value
 	 					},
 	 					success:function(data){
 	 						$(e.target).prop("disabled", "true");
 	 						$(e.target).text("已撥款");
 	 						$("#odsta"+value).html(`<button type="button" class="btn btn-success" disabled>已撥款</button>`);
 	 					}
 	 				})
 					
 				}
 				
 				if(e.target.id == "refund"){
 					let value = e.target.value;
 					$(e.target).parent().prev().prev().prev().prev().prev().prev().prev().html("");
 	 				$.ajax({
 	 					url:"/CFA101G4/PoServlet",
 	 					type:"POST",
 	 					data:{
 	 						"action":"refund",
 	 						"poid":value
 	 					},
 	 					success:function(data){
 	 						$(e.target).prop("disabled", "true");
 	 						$(e.target).text("已退款");
 	 						$("#odsta"+value).html(`<button type="button" class="btn btn-success" disabled>已退款</button>`);
 	 					}
 	 				})
 					
 				}
 		
 		
 				
 				if(e.target.id == "showOD"){
 					console.log("近來")
 					console.log($(this).val())
 					$.ajax({
 						url:"/CFA101G4/",
 						dataType:"json",
 						data :{ 
 							odid :e.target.value,
 							action :"getone"
 						},
 						success:function(data){
 							console.log(data)
 							html="";
 							
 							pdbody="";
 							
 							for(let i = 0 ; i < data.plist.length ; i++){
 								pdbody+=`
 								<!-- 訂單內明細商品的起始點 -->
                                                <div class="row">
                                                    <div class="col-2 text-center align-self-center">
                                                    	${data.plist[i].p_id}
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                    <img src="data:image/gif;base64, ${data.imgList[i].pdimg}" class="img-thumbnail" alt="...">
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                    ${data.plist[i].p_name}
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                    ${data.olist[i].ol_price}
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                    ${data.olist[i].ol_pq}
                                                    </div>
                                                    <div class="col-2 text-center align-self-center">
                                                    ${data.olist[i].ol_price * data.olist[i].ol_pq}
                                                    </div>
                                                </div>
 								
 								
 								`
 							}
 							
 							
 							
 							html=`
 							
 							<div class="modal fade" id="showodBody" data-bs-backdrop="static"
                                data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
                                aria-hidden="true">
                                <div class="modal-dialog modal-xl">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="staticBackdropLabel">${data.ordersVO.od_id} 的訂單明細</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="container-fluid">
                                                
                                                <div class="row">
                                                    <div class="col-2 text-center popover-header">
                                                       	 商品編號
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	商品縮圖
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	商品名稱
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                       	 單價
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                        	下單數量
                                                    </div>
                                                    <div class="col-2 text-center popover-header">
                                                      	  總額
                                                    </div>
                                                </div>
                                                `+
                                                pdbody
 												+`
                                                <div class="row">
                                                	<div class="col align-self-center">
                                                        <p class="h5">配送地址</p>
                                                    </div>
                                                    <div class="col align-self-center">
                                                        <p class="h4">${data.ordersVO.od_shipinfo}</p>
                                                    </div>
                                                    <div class="col text-end">
                                                      	  訂單總金額
                                                        <p class="h2">${data.ordersVO.od_price}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
							        		<button type="button" id="delModal" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
 							`
 							$("#orderBody").append(html);
 							showModal();
 						}
 					})
 				}
 			})

function showModal(){
 		var myModal = new bootstrap.Modal(document.getElementById("showodBody"), {});
 		myModal.show();
 		var myModalEl = document.getElementById('showodBody')
 		myModalEl.addEventListener('hidden.bs.modal', function (event) {
 			$("#showodBody").remove();
 		})
 	}		