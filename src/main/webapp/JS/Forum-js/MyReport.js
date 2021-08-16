$("#hideall").click(function() {
	$(".collapse").each(function() {
		$(this).collapse('hide')
	})
})
$(function() {
	$(window).scroll(function() {
		if ($(this).scrollTop() > 400) {
			$('#GOTOP').fadeIn();
		} else {
			$('#GOTOP').fadeOut();
		}
	});
	$('#GOTOP').click(function() {
		$('html,body').animate({
			scrollTop : 0
		}); 
		return false;
	});
})