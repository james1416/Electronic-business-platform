 	$("#rpmain").on("click" , function(e){
 		
 		console.log(e.target.id)
 				if(e.target.id == "reportOK"){
 					let id = e.target.value;
 					$.ajax({
 	 					url:"/CFA101G4/TXReportServlet",
 	 					type:"POST",
 	 					data:{
 	 						action:"audit",
 	 						trid:id,
 	 						trsta: 1
 	 					},
 	 					success:function(){
 	 						swal(
 	 								"審核成功！",
 	 								"",
 	 								"success"
 	 						)
 	 						$("#close").click();
 	 						$("#trsta"+id).html(`<button type="button" class="btn btn-success" disabled>審核已通過</button>`)
 	 					}
 	 				})
 				}
 				
 				if(e.target.id == "reportFail"){
 					let id = e.target.value;
 					$.ajax({
 	 					url:"/CFA101G4/TXReportServlet",
 	 					type:"POST",
 	 					data:{
 	 						action:"audit",
 	 						trid:id,
 	 						trsta: 2,
 	 					},
 	 					success:function(){
 	 						swal(
 	 								"審核成功！",
 	 								"",
 	 								"success"
 	 						)
 	 						$("#close").click();
 	 						$("#trsta"+id).html(`<button type="button" class="btn btn-danger" disabled>審核未通過</button>`)
 	 					}
 	 				})
 				}
 				
 				
 				if(e.target.id == "refund"){
 					let value = e.target.value;
 					$(e.target).parent().prev().prev().prev().prev().prev().prev().prev().html("");
 	 				$.ajax({
 	 					url:"/CFA101G4/OrdersServlet",
 	 					type:"POST",
 	 					data:{
 	 						"action":"refund",
 	 						"od_id":value
 	 					},
 	 					success:function(data){
 	 						$(e.target).prop("disabled", "true");
 	 						$(e.target).text("已退款");
 	 						
 	 					}
 	 				})
 					
 				}
 				
 				
 				if(e.target.id == "openRp"){
 					
 					$.ajax({
 						url:"/CFA101G4/TXReportServlet",
 						dataType:"json",
 						data :{ 
 							trid :e.target.value,
 							action :"getone"
 						},
 						success:function(data){
 								console.log(data)
 								
 								buton = "";
 								bunton = data.tr_sta == 0 ? `
 								 <button type="button" value="${data.tr_id }" id="reportOK" class="btn btn-success" >通過</button>
							     <button type="button" value="${data.tr_id }" id="reportFail" class="btn btn-danger">不通過</button>
 								 ` :
 								`<button type="button" value="${data.tr_id }" id="reportFail" class="btn btn-primary" disabled>已審核</button>
`
 								html ="";
 								html =`
 								<div class="modal fade" id="showtrBody" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							  <div class="modal-dialog modal-lg">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="staticBackdropLabel">檢舉表單</h5>
							        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							      </div>
							      <div class="modal-body">
							      	<div class="container-fluid">
							      	<div class="row">
							      		<div class="col m-auto">
							        	<div class="input-group input-group-lg m-auto">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉訂單編號</span>
		                                <input type="text" value="${data.tr_odid }" class="form-control" aria-label="Sizing example input"
		                                    aria-describedby="inputGroup-sizing-lg" readonly>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row">
							        	<div class="col m-auto">
							        	<div class="input-group input-group-lg mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉人編號</span>
		                                <input type="text" value="${data.tr_reporter }" class="form-control" aria-label="Sizing example input"
		                                    aria-describedby="inputGroup-sizing-lg" readonly>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row">
							        	<div class="col m-auto">
							        	<div class="input-group input-group-lg mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">被檢舉人編號</span>
		                                <input type="text" value="${data.tr_reported }" class="form-control" aria-label="Sizing example input"
		                                    aria-describedby="inputGroup-sizing-lg" readonly>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row h-50">
							        	<div class="col h-50 m-auto">
							        	<div class="input-group input-group-lg h-50 mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉內容</span>
		                                <textarea class="form-control h-50" aria-label="With textarea" readonly>${data.tr_content == null ?  "" : data.tr_content}</textarea>
		                            	</div>
							        	</div>
							        </div>
							        </div>
							      </div>
							      <div class="modal-footer">`+
							      bunton
							        +`<input id="close" type="button" class="btn btn-secondary" value="取消" data-bs-dismiss="modal">
							      </div>
							    </div>
							  </div>
							</div>
 								`
 								
 	 							$("#moremsg").before(html);
 								showtrModal();
 							}
 						})
 					
 					
 					
 					
 				}
 				
 				
 		
 		
 				
 				if(e.target.id == "showOD"){
 					console.log("近來")
 					console.log($(this).val())
 					$.ajax({
 						url:"/CFA101G4/OrdersServlet",
 						dataType:"json",
 						data :{ 
 							odid :e.target.value,
 							action :"getoneOD"
 						},
 						success:function(data){
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
 							$("#moremsg").before(html);
 							showModal();
 						}
 					})
 				}
 			})
 			
 			
function showtrModal(){
 		var myModal = new bootstrap.Modal(document.getElementById("showtrBody"), {});
 		myModal.show();
 		var myModalEl = document.getElementById('showtrBody')
 		myModalEl.addEventListener('hidden.bs.modal', function (event) {
 			$("#showtrBody").remove();
 		})
 	}

function hidetrModal(){
 		var myModal = new bootstrap.Modal(document.getElementById("showtrBody"), {});
 		myModal.hide();
}

function showModal(){
 		var myModal = new bootstrap.Modal(document.getElementById("showodBody"), {});
 		myModal.show();
 		var myModalEl = document.getElementById('showodBody')
 		myModalEl.addEventListener('hidden.bs.modal', function (event) {
 			$("#showodBody").remove();
 		})
 	}		



window.onload = function(){
	let start = 0;
	let rows = 16;
	let sta = $("#selectSTA").val();
	frist(start,rows);
	start += rows
	scroll(start ,rows ,sta);
}	







$("#selectSTA").on("change",function(){
	$(window).off();
	$(".rpbody").remove();
	let start = 0;
	let rows = 16;
	let sta = $(this).val();
	if($(this).val() == ""){
		frist(start,rows);
		start += rows;
		scroll(start , rows ,sta);
	}
	
	if($(this).val() != ""){
		zero(start,rows,sta);
		start += rows;
		scroll(start , rows ,sta);
		
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
	    		zero(start,rows,sta);
	    		start +=rows
	    	}
	    	
	    }
	})

}



function frist(start,rows){
 			$.ajax({
 				url:"/CFA101G4/TXReportServlet",
 				type:"POST",
 				data:{
 					action:"getAll",
 					start : start,
 					rows : rows,
 					},
 				dataType:"json",
 				success:function(data){
 					console.log(data);
 					for(i=0;i<data.length;i++){
	 					if(data.length != rows){
	 					     $("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
	 					}
	 					button = ``;
	 					checkbox =``;
	 					sta= ``;
	 					
	 					if(data[i].tr_sta == 0){
	 						sta = `<button type="button" class="btn btn-secondary" disabled>未審核</button>`
	 					}else if(data[i].tr_sta == 1){
	 						sta = `<button type="button" class="btn btn-success" disabled>審核已通過</button>`;
	 					}else{
	 						sta = `<button type="button" class="btn btn-danger" disabled>審核未通過</button>`;
	 					}
	 					
	 					
	 					let tr = `
	 					<div class="rpbody">
	 						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
					              <div class="col-1 text-center align-self-center">${data[i].tr_id}</div>
					              <div class="col-1 text-center align-self-center">${data[i].tr_odid}</div>
					              <div class="col-1 text-center align-self-center">${data[i].tr_reporter}</div>
					              <div class="col-1 text-center align-self-center">${data[i].tr_reported}</div>
					              <div class="col-2 text-center align-self-center" >${data[i].tr_date} </div>
					              <div class="col-2 text-center align-self-center" id="trsta${data[i].tr_id}">
					              `+sta+`
					              </div>
					              <div class="col-4 text-center align-self-center">
					              	<button type="button" id="openRp" value="${data[i].tr_id}" class="btn btn-primary">查看檢舉</button>
					              	<button type="button" id="showOD" value="${data[i].tr_odid}" class="btn btn-primary">訂單詳情</button>
					              </div>
					        </div>
	 					</div>
		 			          `
 						$("#moremsg").before(tr);
 					}
 				}
 			})
}

function zero(start,rows,sta){
		$.ajax({
			url:"/CFA101G4/TXReportServlet",
			type:"POST",
			data:{
				action:"getAllbySTA",
				start : start,
				rows : rows,
				trsta : sta
				},
			dataType:"json",
			success:function(data){
				console.log(data);
				for(i=0;i<data.length;i++){
					if(data.length != rows){
					     $("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
					}
					button = ``;
					checkbox =``;
					sta= ``;
					
					if(data[i].tr_sta == 0){
						sta = `<button type="button" class="btn btn-secondary" disabled>未審核</button>`
					}else if(data[i].tr_sta == 1){
						sta = `<button type="button" class="btn btn-success" disabled>審核已通過</button>`;
					}else{
						sta = `<button type="button" class="btn btn-danger" disabled>審核未通過</button>`;
					}
					
					
					let tr = `
					<div class="rpbody">
						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
			              <div class="col-1 text-center align-self-center">${data[i].tr_id}</div>
			              <div class="col-1 text-center align-self-center">${data[i].tr_odid}</div>
			              <div class="col-1 text-center align-self-center">${data[i].tr_reporter}</div>
			              <div class="col-1 text-center align-self-center">${data[i].tr_reported}</div>
			              <div class="col-2 text-center align-self-center" >${data[i].tr_date} </div>
			              <div class="col-2 text-center align-self-center" id="trsta${data[i].tr_id}">
			              `+sta+`
			              </div>
			              <div class="col-4 text-center align-self-center">
			              	<button type="button" id="openRp" value="${data[i].tr_id}" class="btn btn-primary">查看檢舉</button>
			              	<button type="button" id="showOD" value="${data[i].tr_odid}" class="btn btn-primary">訂單詳情</button>
			              </div>
			        </div>
					</div>
 			          `
					$("#moremsg").before(tr);
				}
			}
		})
}
