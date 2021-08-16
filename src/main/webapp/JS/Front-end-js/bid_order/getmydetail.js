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
    url:"/CFA101G4/BpDetail",
    dataType : "json",
    data:{
        "start" : start,
        "rows" : rows,
        action : "get_my_detail"
    },
    success:function(data){
        console.log(data.length)
        if(data.length != rows){
        	$("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
        }
        html = "";
        for(let i = 0 ; i < data.length ; i++){
            html +=`
        <div class="row justify-content-center border-bottom border-$gray-500 p-3">
            <div class="col-3 text-center">
            ${data[i].bpd_bpid }
            </div>
            <div class="col-3 text-center">
            ${data[i].bpd_bpprice }
            </div>
            <div class="col-3 text-center">
            ${data[i].bpd_bpdate }
            </div>
            <div class="col-2 text-center">
            <a class="btn btn-primary" href="/CFA101G4/bid.do?action=getOne_For_Display&bp_id=${data[i].bpd_bpid}" role="button">查看商品</a>
            </div>
        </div>
            `
        };
        $("#moremsg").before(html);
        start += rows;
    }
})
}