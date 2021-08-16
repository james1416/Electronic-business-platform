 	$("#rpmain").on("click" , function(e){
 		
 		console.log(e.target.id)
 				if(e.target.id == "reportOK"){
 					let id = e.target.value;
 					$.ajax({
 	 					url:"/CFA101G4/ProductReportServlet",
 	 					type:"POST",
 	 					data:{
 	 						action:"updata",
 	 						prid:id,
 	 						prsta: 1
 	 					},
 	 					success:function(){
 	 						swal(
 	 								"審核成功！",
 	 								"",
 	 								"success"
 	 						)
 	 						$("#close").click();
 	 						$("#prsta"+id).html(`<button type="button" class="btn btn-success" disabled>審核已通過</button>`)
 	 					}
 	 				})
 				}
 				
 				if(e.target.id == "reportFail"){
 					let id = e.target.value;
 					$.ajax({
 	 					url:"/CFA101G4/ProductReportServlet",
 	 					type:"POST",
 	 					data:{
 	 						action:"updata",
 	 						prid:id,
 	 						prsta: 2,
 	 					},
 	 					success:function(){
 	 						swal(
 	 								"審核成功！",
 	 								"",
 	 								"success"
 	 						)
 	 						$("#close").click();
 	 						$("#prsta"+id).html(`<button type="button" class="btn btn-danger" disabled>審核未通過</button>`)
 	 					}
 	 				})
 				}
 				
 				
 				if(e.target.id == "openRp"){
 					
 					$.ajax({
 						url:"/CFA101G4/ProductReportServlet",
 						dataType:"json",
 						data :{ 
 							prid :e.target.value,
 							action :"getone"
 						},
 						success:function(data){
 								console.log(data)
 								
 								buton = "";
 								bunton = data.pr_sta == 0 ? `
 								 <button type="button" value="${data.pr_id }" id="reportOK" class="btn btn-success" >通過</button>
							     <button type="button" value="${data.pr_id }" id="reportFail" class="btn btn-danger">不通過</button>
 								 ` :
 								`<button type="button" class="btn btn-primary" disabled>已審核</button>
`
 								html ="";
 								html =`
 								<div class="modal fade" id="showPRBody" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
							        	<div class="input-group input-group-lg mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉人編號</span>
		                                <input type="text" value="${data.pr_memid }" class="form-control" aria-label="Sizing example input"
		                                    aria-describedby="inputGroup-sizing-lg" readonly>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row">
							        	<div class="col m-auto">
							        	<div class="input-group input-group-lg mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">被檢舉商品編號</span>
		                                <input type="text" value="${data.pr_pid }" class="form-control" aria-label="Sizing example input"
		                                    aria-describedby="inputGroup-sizing-lg" readonly>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row h-50">
							        	<div class="col h-50 m-auto">
							        	<div class="input-group input-group-lg h-50 mt-3">
		                                <span class="input-group-text" id="inputGroup-sizing-lg">檢舉內容</span>
		                                <textarea class="form-control h-50" aria-label="With textarea" readonly>${data.pr_content == null ?  "" : data.pr_content}</textarea>
		                            	</div>
							        	</div>
							        </div>
							        <div class="row">
							        	<div class="col mt-3 text-center">
							        		<a href="/CFA101G4/ProductServlet?action=selectOnePD&pid=${data.pr_pid }" target="_blank">
							        		<button type="button" class="btn btn-primary">查看此商品詳情</button>
							        		</a>
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
 				
 			})
 			
 			
function showtrModal(){
 		var myModal = new bootstrap.Modal(document.getElementById("showPRBody"), {});
 		myModal.show();
 		var myModalEl = document.getElementById('showPRBody')
 		myModalEl.addEventListener('hidden.bs.modal', function (event) {
 			$("#showPRBody").remove();
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
	console.log($(this).val())
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
 				url:"/CFA101G4/ProductReportServlet",
 				type:"POST",
 				data:{
 					action:"getallbyADMIN",
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
	 					
	 					if(data[i].pr_sta == 0){
	 						sta = `<button type="button" class="btn btn-secondary" disabled>未審核</button>`
	 					}else if(data[i].pr_sta == 1){
	 						sta = `<button type="button" class="btn btn-success" disabled>審核已通過</button>`;
	 					}else{
	 						sta = `<button type="button" class="btn btn-danger" disabled>審核未通過</button>`;
	 					}
	 					
	 					
	 					let tr = `
	 					<div class="rpbody">
	 						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
					              <div class="col-1 text-center align-self-center">${data[i].pr_id}</div>
					              <div class="col-1 text-center align-self-center">${data[i].pr_pid}</div>
					              <div class="col-1 text-center align-self-center">${data[i].pr_memid}</div>
					              <div class="col-3 text-center align-self-center" >${data[i].pr_date} </div>
					              <div class="col-2 text-center align-self-center" id="prsta${data[i].pr_id}">
					              `+sta+`
					              </div>
					              <div class="col-4 text-center align-self-center">
					              	<button type="button" id="openRp" value="${data[i].pr_id}" class="btn btn-primary">查看檢舉</button>
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
			url:"/CFA101G4/ProductReportServlet",
			type:"POST",
			data:{
				action:"getallbySTA",
				start : start,
				rows : rows,
				prsta : sta
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
 					
 					if(data[i].pr_sta == 0){
 						sta = `<button type="button" class="btn btn-secondary" disabled>未審核</button>`
 					}else if(data[i].pr_sta == 1){
 						sta = `<button type="button" class="btn btn-success" disabled>審核已通過</button>`;
 					}else{
 						sta = `<button type="button" class="btn btn-danger" disabled>審核未通過</button>`;
 					}
 					
 					
 					let tr = `
 					<div class="rpbody">
 						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
				              <div class="col-1 text-center align-self-center">${data[i].pr_id}</div>
				              <div class="col-1 text-center align-self-center">${data[i].pr_pid}</div>
				              <div class="col-1 text-center align-self-center">${data[i].pr_memid}</div>
				              <div class="col-3 text-center align-self-center" >${data[i].pr_date} </div>
				              <div class="col-2 text-center align-self-center" id="prsta${data[i].pr_id}">
				              `+sta+`
				              </div>
				              <div class="col-4 text-center align-self-center">
				              	<button type="button" id="openRp" value="${data[i].pr_id}" class="btn btn-primary">查看檢舉</button>
				              </div>
				        </div>
 					</div>
	 			          `
						$("#moremsg").before(tr);
					}
			}
		})
}
