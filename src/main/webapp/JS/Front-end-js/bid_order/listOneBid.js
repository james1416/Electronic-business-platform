
let id = $('#bpd_bpid').val();
console.log(id);

window.onload =function(){
    init();
}
function init(){
     setTimeout(init,1*1000);
     $.ajax({
            url: "/CFA101G4/bid.do",
            type:"post",
            datatype:"json",
            async: true,
            data : {
                "action":"tprice",
                bp_id : $("#bpd_bpid").val(),
            },
            success:function(e){
                console.log(e);
                $('#toprice').html(e)
            }

        })

}


let y = $("div[id='imgbox']").length;
for(let i = 0 ; i < y ; i ++){
    if( i == 0)
        $("div[id='imgbox']:eq("+ i +")").addClass("carousel-item active")
    else
        $("div[id='imgbox']:eq("+ i +")").addClass("carousel-item")
}
$('#send').on("click",function(){
    console.log("asadsad");
})