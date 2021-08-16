$(function() {
	ckmem();
})

//左上角展開
$("#listbtn").click(function() {
	$(".side").slideToggle("slow")
});
//右上角人頭展開
$("#Memberfunction").click(function(e) {
	e.preventDefault()
	$("#topfunction").slideToggle()
})
$(function() {
	//判斷顯示登入登出
	if (chcekmember() == false) {
		$("#logoin").text("登入")
	} else {
		$("#logoin").text("登出")
	}
    $(window).scroll(function() {
        if ( $(this).scrollTop() > 400){
            $('#GOTOP').fadeIn();
        } else {
            $('#GOTOP').fadeOut();
        }
    });
    $('#GOTOP').click(function(){
        $('html,body').animate({ scrollTop: 0 });   /* 返回到最頂上 */
        return false;
    });
})

//會員中心轉跳
$("#memcenter").click(function() {
	if (chcekmember() == false) {
		location.href="/CFA101G4/front-end/login.jsp";
	} else {
		location.href="/CFA101G4/front-end/Member/member_center.html";
	}
})

//登入登出轉跳
$("#logoin").click(function() {
	if($(this).text().trim()=="登出"){
    	$.ajax({
    		url:"/CFA101G4/MemberServlet",
    		cache: false,
    		type:"POST",
    		data:{action:"exits"},
    		success:function(data){
            	location.href = "/CFA101G4/front-end/login.jsp";
            }
    	})
		$("#topfunction").slideUp();
	}else{
		location.href="/CFA101G4/front-end/login.jsp";
		$("#topfunction").slideUp();
	}
})
//左邊清單登入
$('li[id="USRUID"]').click(function() {
	if (chcekmember() == false){
		location.href="/CFA101G4/front-end/login.jsp";
	}
})
//會員確認
function chcekmember() {
	let USRID = $('li[id="USRUID"]').text().trim();
	if (USRID == "未登入") {
		return false;
	}
}
//會員比對
function ckmem() {
	let USRID = $('li[id="USRUID"]').text().trim();
	if(USRID==""){
	  	$.ajax({
			url:"/CFA101G4/MemberServlet",
			cache: false,
			type:"POST",
			dataType : 'json',
			data:{action:"findoneMem"},
			success:function(data){
	        	let pic =  `<img src="data:image/png;base64, ${data.mem_pic}" id="USR" class="img-fluid" >`
	        	let name = `<p>${data.mem_name}</p>`
	        $("#USRpic").html(pic)
	        $('li[id="USRUID"]').html(name)
	        }
		})
	}
}
//登入會員提醒
function remindmember() {
	Swal.fire({
		  icon: 'question',
		  text: '請先登入會員',
	});
}
//發文鍵鎖定
$("#postmyarticle").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	}  else {
		window.open("/CFA101G4/front-end/Article/PostArticle.jsp", "_self");
	}
})
//未登入我的文章鎖定
$("#myarticle").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	} 
})
//未登入我的收藏鎖定
$("#mycolarticle").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	} 
})
//未登入我的檢舉鎖定
$("#myrep").click(function(e) {
	if (chcekmember() == false) {
		e.preventDefault()
		remindmember();
	} 
})