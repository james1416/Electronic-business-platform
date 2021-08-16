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
        action : "completebids"
    },
    success:function(data){
        console.log(data.length)
        if(data.length != rows){
        	$("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
        }
        html = "";
        for(let i = 0 ; i < data.length ; i++){
        	if(data[i].bp_shsta == 4){
        		data[i].bp_shsta = "競標訂單完成";
        	}
            html +=`
            <div class="row justify-content-center border-bottom border-$gray-500 p-3">
            <div class="col-1 text-center">
            ${data[i].bp_id }
            </div>
            <div class="col-1 text-center">
            ${data[i].bp_name }
            </div>
            <div class="col-2 text-center">
            ${data[i].bp_to }
            </div>
            <div class="col-2 text-center">
            ${data[i].bp_desc }
            </div>
            <div class="col-4 text-center">
            ${data[i].bp_shsta }
            </div> 
            <div class="col-2 text-center">
            <a class="btn btn-primary" href="#" role="button">查看評價</a>
            </div>
            
        </div>
            `
        };
        $("#moremsg").before(html);
        start += rows;
    }
})
}