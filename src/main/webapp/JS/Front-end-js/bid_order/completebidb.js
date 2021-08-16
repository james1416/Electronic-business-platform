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
        action : "completebidb"
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
            <div class="col-2 text-center">
            ${data[i].bp_name }
            </div>
            <div class="col-2 text-center">
            ${data[i].bp_to }
            </div>
            <div class="col-3 text-center">
            ${data[i].bp_desc }
            </div>
            <div class="col-2 text-center">
            ${data[i].bp_shsta }
            </div> 
            <div class="col-2 text-center">
             <button type="button" class="btn btn-success"data-bs-toggle="modal" data-bs-target="#exampleModal${data[i].bp_id}">評論賣家</button>
                <div class="modal fade" id="exampleModal${data[i].bp_id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">評價</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="關閉"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="customRange2" class="form-label">評價分數</label><span class="rating" id="rating">(預設為3分)</span>
                                    <input type="range" name="foo" class="form-range" min="1" max="5" data-range="" id="customRange${data[i].bp_id}">
                                    
                                </div>
                                <div class="mb-3">
                                    <label for="message-text" class="col-form-label">請留下您的評論:</label>
                                    <textarea class="form-control" data-message="msg" id="message${data[i].bp_id}"></textarea>
                                </div>
                            
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
                                <button type="button" class="btn btn-primary" id="sendout${data[i].bp_id}" value="${data[i].bp_id}">送出評價</button>
                            </div>
                        </div>                
                    </div>
                </div>
            </div>
            
        </div>
            `
            	
        };
        $("#moremsg").before(html);
        start += rows;
    }  
})
}

$("#bidbody").on("click",function (e) {
	let bp_id = $(e.target).val();
	if($(e.target).attr("id")=='sendout'+bp_id){
	console.log(bp_id);
    let msg = $("#message"+bp_id).val();
    console.log(msg);
    let rating = $("#customRange"+bp_id).val()
    console.log(rating);
    $.ajax({
        url: "/CFA101G4/bid.do",
        type: "POST",
        data: {
            "action": "submitComment",
            "msg": msg,
            "rating": rating,
            "bp_id": bp_id,
            "bp_comsta": 2
        },
        success: function (data) {
            console.log(data)
            window.location.reload();
        },
        error: function () {
            alert("提交評價失敗！")
        }
    })
	}
})

$("#exampleModal").on("input", '[data-range]', function (e) {
    let val = $("#customRange").val()
    console.log(val)
    $("#rating").html(val + "分");

})









