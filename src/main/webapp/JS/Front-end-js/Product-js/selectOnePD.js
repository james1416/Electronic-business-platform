console.log($("div[id='imgbox']").length)
let y = $("div[id='imgbox']").length;
for(let i = 0 ; i < y ; i ++){
	if( i == 0)
		$("div[id='imgbox']:eq("+ i +")").addClass("carousel-item active")
	else
		$("div[id='imgbox']:eq("+ i +")").addClass("carousel-item")
}





$("button").on("click",function(e){
	let del = `<i class="bi bi-cart-dash" id="delCL"></i>移除`;
	let add = `<i class="bi bi-cart-plus" id="addCL"></i>加入`;
		
	let tdel = `<i class="bi bi-suit-heart-fill"></i>追蹤中`;
	let tadd = `<i class="bi bi-suit-heart"></i>追蹤`;
		
	if($(this).text() == "加入"){
		let sum = parseInt($("#cart-sum").text())
		$("#cart-sum").html(sum+1)
		$(this).html(del)
		$.ajax({
			url:"/CFA101G4/CartListServlet",
			data:{ 
				pid : $(this).val(),
				action : "add"
			},
			success:function(data){
				console.log(data)
				if(data == "true"){
					swal(
						'加入購物車！',
						'繼續購物吧！',
						'success'
					)
				}else{
					window.location.href = "/CFA101G4/front-end/login.jsp"
				}
			}

		})
		return
}
	if($(this).text() == "移除"){
		let sum = parseInt($("#cart-sum").html())
		$("#cart-sum").html(sum-1)
		console.log($(this).text())
		$(this).html(add)
		$.ajax({
			url:"/CFA101G4/CartListServlet",
			data:{ 
				pid : $(this).val(),
				action : "del"
			},
			success:function(data){
				swal(
						'從購物車移除囉！',
						'繼續購物吧！',
						'success'
					)
			}
		})
		return
	}
	
	if($(this).text() == "追蹤"){
		$(this).html(tdel)
		$.ajax({
			url:"/CFA101G4/TrackingServlet",
			data:{ 
				pid : $(this).val(),
				action : "add"
			},
			success:function(data){
				if(data == "true"){
				swal(
						'加入追蹤囉！',
						'繼續逛逛吧！',
						'success'
					)
				}else{
					window.location.href = "/CFA101G4/front-end/login.jsp"
				}
			}
		})
		return
	}
	
	if($(this).text() == "追蹤中"){
		$(this).html(tadd)
		$.ajax({
			url:"/CFA101G4/TrackingServlet",
			data:{ 
				pid : $(this).val(),
				action : "del"
			},
			success:function(data){
				swal(
						'移除追蹤囉！',
						'繼續逛逛吧！',
						'success'
					)
			}
		})
		return
	}
	
})
