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
        action : "geteffbid"
    },
    success:function(data){
        console.log(data.length)
        if(data.length != rows){
        	$("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
        }
        html = "";
        for(let i = 0 ; i < data.length ; i++){
        	if(data[i].bp_sta == 0){
        		data[i].bp_sta = "未開始";
        	}else if(data[i].bp_sta == 1){
        		data[i].bp_sta = "競標進行中";
        	}else if(data[i].bp_sta == 2){
        		data[i].bp_sta = "競標結束";
        	}
        	if(data[i].bp_shsta == 1){
        		
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
            ${data[i].bp_etime }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_sprice }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_inc }
            </div>
             <div class="col-1 text-center">
            ${data[i].bp_bmemid }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_to }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_sta }
            </div>
            <div class="col-2 text-center">
            ${data[i].bp_shsta }
            </div>
            <div class="col-1 text-center">
            <a class="btn btn-primary" href="#" role="button">出貨</a>
            </div>
            <div class="col-1 text-center">
            <a class="btn btn-primary" href="#" role="button">取消訂單</a>
            </div>
        </div>
            `
        };
        $("#moremsg").before(html);
        start += rows;
    }
})
}