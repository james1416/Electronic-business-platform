let start = 0;
let rows = 16;

show();
$(window).scroll(function(){
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        show();
    }

})


function show(){
$.ajax({
    url:"/CFA101G4/bid.do",
    dataType : "json",
    data:{
        "start" : start,
        "rows" : rows,
        action : "getmybid"
    },
    success:function(data){
        console.log(data.length)
        if(data.length != rows){
        	$("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
        }
        html = "";
        for(let i = 0 ; i < data.length ; i++){
        	let btn=""
            	if(data[i].bp_tprice == null ||data[i].bp_tprice == ""){
            		btn =`<button type="button"  class="btn btn-primary" id="delete1" value="${data[i].bp_id}">刪除</button>`
            	}else{
            		btn = `<button type="button"  class="btn btn-warning" disabled>無法刪除</button>`
            	}
        	if(data[i].bp_sta == 0){
        		data[i].bp_sta = "未開始";
        	}else if(data[i].bp_sta == 1){
        		data[i].bp_sta = "競標進行中";
        	}else if(data[i].bp_sta == 2){
        		data[i].bp_sta = "競標結束";
        	}
        	
        	
        	
        	
        	
        	if(data[i].bp_shsta == 1){
        		data[i].bp_shsta = "收件資訊尚未填寫"
        	}else if(data[i].bp_shsta == 2){
        		data[i].bp_shsta = "訂單尚未出貨"
        	}else if(data[i].bp_shsta == 3){
        		data[i].bp_shsta = "訂單已出貨"
        	}else if(data[i].bp_shsta == 4){
        		data[i].bp_shsta = "訂單已完成"
        	}else if(data[i].bp_shsta == 5){
        		data[i].bp_shsta = "買家申請退貨"
        	}else if(data[i].bp_shsta == 6){
        		data[i].bp_shsta = "賣家接受退貨"
        	}else if(data[i].bp_shsta == 7){
        		data[i].bp_shsta = "賣家拒絕退貨"
        	}else{
        		data[i].bp_shsta = "訂單狀態遺失"
        	}
        	
        	
        	
        	
        	
        	
        	
        	
            html +=`
            <div class="row justify-content-center border-bottom border-$gray-500 p-3">
            <div class="col-1 text-center">
            ${data[i].bp_id }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_name }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_stime }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_etime }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_sprice }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_inc }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_tprice }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_sta }
            </div>
            <div class="col-1 text-center" >
            `+btn+`
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_shsta}
            </div>
            </div>
            `
        };
        $("#moremsg").before(html);
        start += rows;
    }
})
}

$("#PDbody").on("click",function(e){
	console.log(e.target);
	let i = $(e.target).val();
	console.log($("#delete1").val());
	if($(e.target).attr("id") == "delete1"){
	var formData = new FormData();		

    formData.append("bp_id",i)
    formData.append("action","delete")
	swal({
        title: '確定要刪除商品嗎',
        text: '確定刪除後商品將被刪除！',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: '確定！',
        cancelButtonText: "取消",
        
    }).then(function(value) {
        if (value.value) {
			$.ajax({
				url: "/CFA101G4/bid.do",
				type:"post",
				async: true,
			    contentType: false,
			    processData: false,
				data:formData,
				success:function(){
					swal(
							"商品刪除",
							"商品已刪除成功！",
							"success"
									)
				window.setTimeout(window.location="/CFA101G4/front-end/BidProduct/get_my_bidproduct.jsp",3000);
				}
			})
        } else {
            swal('已取消操作！', '', 'error')
        }
    
	})
}
})