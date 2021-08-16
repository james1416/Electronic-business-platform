let y = $("div[id='imgbox']").length;
for (let i = 0; i < y; i++) {
	if (i == 0)
		$("div[id='imgbox']:eq(" + i + ")").addClass("carousel-item active")
	else
		$("div[id='imgbox']:eq(" + i + ")").addClass("carousel-item")
}


$("button").on("click", function(e) {


	let tdel = `<i class="bi bi-suit-heart-fill"></i>追蹤中`;
	let tadd = `<i class="bi bi-suit-heart"></i>追蹤`;


	if ($(this).text() == "追蹤") {
		$(this).html(tdel)
		$.ajax({
			url: "/CFA101G4/TrackingServlet",
			data: {
				pid: $(this).val(),
				action: "add"
			},
			success: function(data) {
				swal(
					'加入追蹤囉！',
					'繼續逛逛吧！',
					'success'
				)
			}
		})
		return
	}

	if ($(this).text() == "追蹤中") {
		$(this).html(tadd)
		$.ajax({
			url: "/CFA101G4/TrackingServlet",
			data: {
				pid: $(this).val(),
				action: "del"
			},
			success: function(data) {
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