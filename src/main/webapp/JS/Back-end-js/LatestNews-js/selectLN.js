let start = 0;
let rows = 16;
show();
$(window).scroll(function(){
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        show();
    }
})

function check(){
	 let submit = false
	 
	 let i = $("#newText").val()
	 let reg = /^[\u4e00-\u9fa5\w]{5,100}$/;
	 if(reg.test(i)){
		 submit = true;
	 }else{
		 submit = false
	 }
	 return submit;
}

$("#submit").on("click",function(){
	let point = check()
	if(point){
		$("#go").click();
	}else{
		swal(
				"擋住",
				"請填五至一百個字",
				"error"
				)
	}
		
})


function add(i){
	let submit = false
	
	$("#updata"+i).on("click",function(){
		
		 let a = $("#updatatext"+i).val()
		 console.log(i)
		 let reg = /^[\u4e00-\u9fa5\w]{5,100}$/;
		 if(reg.test(a)){
			 submit = true;
		 }else{
			 submit = false
		 }
		
		if(submit){
			$("#go"+i).click();
		}else{
			swal(
					"擋住",
					"請填五至一百個字",
					"error"
					)
		}
	})
	 
}

function show(){
 			$.ajax({
 				url:"/CFA101G4/Latest_newsServlet",
 				type:"get",
 				data:{
 					action:"getall",
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
 					
 					let tr = `
 					<div class="listbody">
 						<div class="row justify-content-center border-bottom border-$gray-500 p-3">
				             <div class="col-1 text-center align-self-center">${data[i].ln_id}</div>
				             <div class="col-7 text-center align-self-center">${data[i].ln_con}</div>
				             <div class="col-2 text-center align-self-center">${data[i].ln_tsp}</div>
				             <div class="col-1 text-center align-self-center">${data[i].ln_sta == 1 ? "發布" : "隱藏中"}</div>
				             <div class="col-1 text-center align-self-center">
				             <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#nl${data[i].ln_id}">
							 修改
							 </button>
				             </div>
				        </div>
 					</div>
 					<form method="get" action="/CFA101G4/Latest_newsServlet" style="display:flex">
 					<div class="modal fade" id="nl${data[i].ln_id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg modal-dialog-centered">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">修改消息</h5>
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <div class="modal-body">
					        <div class="container">
                                       
                                        <div class="row mb-3">
                                            <div class="col">
                                            	<div class="input-group mb-3">
												  <span class="input-group-text" id="basic-addon1">編號</span>
												  <input type="text" class="form-control" name="lnid" value="${data[i].ln_id}" aria-label="Username" aria-describedby="basic-addon1" readonly>
												</div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col">
                                                <div  class="input-group">
                                                    <span class="input-group-text">內容</span>
                                                    <textarea id="updatatext${data[i].ln_id}" name="lncon" class="form-control"
                                                        aria-label="With textarea">${data[i].ln_con}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                            <div class="col">
                                            <select name="lnsta" class="form-select" aria-label="Default select example">
											  <option value="1" ${data[i].ln_sta == 1 ? "selected" : ""}>發布</option>
											  <option value="0" ${data[i].ln_sta == 0 ? "selected" : ""}>隱藏中</option>
											</select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
								      	<button type="button" value="update" id="updata${data[i].ln_id}" name="action" class="btn btn-primary">修改</button>
								      	<button name="action" id="go${data[i].ln_id}" value="update" class="btn btn-primary" style="display:none">新增</button>
								        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
					      			</div>
					      </div>
					    </div>
					  </div>
					</div>
					<form>
		 			          `
 						$("#moremsg").before(tr);
 					console.log(data[i].ln_id)
 						add(data[i].ln_id);
 					}
 					start += rows
 				}
 			})
}







