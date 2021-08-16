let start = 0;
let rows = 16;

show();
$(window).scroll(function() {
	if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
		show();
	}

})


function show() {
	$.ajax({
		url: "/CFA101G4/PdSelfServlet",
		dataType: "json",
		async: false,
		data: {
			"start": start,
			"rows": rows,
			action: "getSelf"
		},
		success: function(data) {
			for (let i = 0; i < data.length; i++) {
				$.ajax({
					url: "/CFA101G4/PoServlet",
					dataType: "json",
					async: false,
					data: {

						action: "getOnePdSum",
						pdid: data[i].pd_id,
					},
					success: function(sum) {
						let btn=""
						if (data[i].pd_sta == 0) {
						
						btn =`<button type="button" id="off" value="${data[i].pd_id}" class="btn btn-primary m-0">下架</button>`
						
							data[i].pd_sta = "尚未開賣"
						}
						else if (data[i].pd_sta == 1) {
						btn =`<button type="button" id="off" value="${data[i].pd_id}" class="btn btn-primary m-0">下架</button>`
							data[i].pd_sta = "熱賣中"
						}
						else if (data[i].pd_sta == 2) {
						btn =`<button type="button" id="off" value="${data[i].pd_id}" class="btn btn-primary m-0"　disabled>下架</button>`
							data[i].pd_sta = "結束拍賣"
						}
						else {
						btn = `<button type="button" id="off" value="${data[i].pd_id}" class="btn btn-primary m-0" disabled>已下架</button>`
							data[i].pd_sta = "已下架"
						}
						if (data.length != rows) {
							$("#moremsg").html(`<div class="col-12 text-center">沒有更多資料囉！</div>`)
						}
						html = "";
						html += `
            <div class="row justify-content-center border-bottom border-$gray-500 p-3">
            <div class="col-1 text-center">
            ${data[i].pd_id}
            </div>
            <div class="col-2 text-center">
            ${data[i].pd_name}
            </div>
            <div class="col-2 text-center">
            ${data[i].pd_sdate}
            </div>
            <div class="col-2 text-center">
            ${data[i].pd_edate}
            </div>
            <div class="col-1 text-center">
            ${data[i].pd_price}
            </div>
            <div class="col-1 text-center">
            ${data[i].pd_no}
            </div>
            <div class="col-1 text-center">
            ${sum}
            </div>
            <div class="col-1 text-center">
            
            ${data[i].pd_sta}
            </div>
            
            
            <div class="col-1 text-center">
				`+btn+`
				
            </div>
        </div>
            `

						$("#moremsg").before(html);


					}
				})



			};
			start += rows;
		}
	})
}


$("button").on("click", function(e) {
	let i = $(this).parent().parent();


	if ($(this.id).selector == "off") {
		let id = $(this).val()
		swal({
			title: '確定要下架商品嗎？',
			text: '如果沒有人購買才可以下架商品！',
			type: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#d33',
			cancelButtonColor: '#3085d6',
			confirmButtonText: '確定！',
			cancelButtonText: "取消",
		}).then(function(value) {
			if (value.value) {
				console.log("123")
				$.ajax({
					url: "/CFA101G4/PdSelfServlet",
					data: {
						PD_ID: id,
						PD_STA: "3",
						action: "offPd"
					},
					success: function(data) {
						if (data == "true") {
						
							swal(
								"成功下架商品",
								"success"
							)
						}
						else{
							swal(
							"下架失敗",
							"已經有人購買"
							)
						}
						
						
					}
				})
			} else {
				swal('已取消操作！', '', 'error')
			}
		})
	}
})
