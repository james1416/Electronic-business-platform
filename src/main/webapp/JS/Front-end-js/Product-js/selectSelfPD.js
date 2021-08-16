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
    url:"/CFA101G4/ProductServlet",
    dataType : "json",
    data:{
        "start" : start,
        "rows" : rows,
        action : "getSelfPDbyPage"
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
            <div class="col-1 text-center">
            ${data[i].p_id }
            </div>
            <div class="col-2 text-center">
            <div class="carddesc">
            ${data[i].p_name }
            </div>
            </div>
            <div class="col-1 text-center">
            ${data[i].p_cgid }
            </div>
            <div class="col-3 text-center">
            <div class="carddesc">
            ${data[i].p_desc }
            </div>
            </div>
            <div class="col-1 text-center">
            ${data[i].p_price }
            </div>
            <div class="col-1 text-center">
            ${data[i].p_stock }
            </div>
            <div class="col-1 text-center">
            ${data[i].p_sl }
            </div>
            <div class="col-1 text-center">
            ${data[i].p_sta == 0 ? "下架中"  : "上架中"}
            </div>
            <div class="col-1 text-center">
            <a href="/CFA101G4/ProductServlet?action=findPD&pid=${data[i].p_id}">修改</a>
            </div>
        </div>
            `
        };
        $("#moremsg").before(html);
        start += rows;
        let len = 70;
        $(".carddesc").each(function(i){
              if($(this).text().length>len){
              $(this).attr("title", $(this).text());
              let text  = $(this).text().substring(0, len-1)+"...";
              $(this).text(text);
       }
    })
    	}
	})
}




