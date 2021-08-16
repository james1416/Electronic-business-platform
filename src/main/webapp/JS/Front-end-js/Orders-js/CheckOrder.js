let len = 50;
    $(".carddesc").each(function(i){
          if($(this).text().length>len){
          $(this).attr("title", $(this).text());
          let text  = $(this).text().substring(0, len-1)+"...";
          $(this).text(text);
   }
})

$("#fastbody").on("click",function(){
    AddressSeleclList.Initialize("fastcity", "fastdist");
    $("#fastadd").css("border-color" , "red");
	$("#fastname").css("border-color" , "red");
	$("#fasttel").css("border-color" , "red");
    $("#fastcity").css("border-color" , "red");
    $("#fastdist").css("border-color" , "red");
    $("#fastadd").css("border-color" , "red");
    $("#fastdo").css("border-color" , "red");
    
    $("#fasttel").on("input",function(){
    	let reg_tel = /^09\d{8}$/;
    	let tel = $("#fasttel").val()
        if(reg_tel.test(tel)){
            $("#fasttel").css("border-color" , "green")
        }else{
            $("#fasttel").css("border-color" , "red")
        }
    })

    $("#fastname").on("input",function(){
    	let name =  $("#fastname").val()
    	let reg_name = /^[\u4e00-\u9fa5\w]{2,15}$/;
    	if(reg_name.test(name)){
    		$("#fastname").css("border-color" , "green")
        }else{
        	$("#fastname").css("border-color" , "red")
        }

    })
    
    $("#fastadd").on("input",function(){
    	let add =  $("#fastadd").val()
    	let reg_add = /^[\u4e00-\u9fa5\w]{5,100}$/;;
    	if(reg_add.test(add)){
    		$("#fastadd").css("border-color" , "green")
        }else{
        	$("#fastadd").css("border-color" , "red")
        }

    })


    $("#fastcity").on("change",function(a){
    	let city =  $("#fastcity").val()
    	if( city != ""){
    		$("#fastcity").css("border-color" , "green")
        }else{
        	$("#fastcity").css("border-color" , "red")
        }

    })

    $("#fastdist").on("change",function(b){
    	let dist =  $("#fastdist").val()
    	if( dist != ""){
    		$("#fastdist").css("border-color" , "green")
        }else{
        	$("#fastdist").css("border-color" , "red")
        }

    })

    $("#fastdo").on("change",function(){
    	let DO =  $("#fastdo").val()
    	if( DO != "" ){
    		$("#fastdo").css("border-color" , "green")
        }else{
        	$("#fastdo").css("border-color" , "red")
        }

    })
    
    
    $("#okfast").on("click",function(){
	console.log("hi")
    let telCheck = false;
    let nameCheck = false;
    let addCheck = false;
    let cityCheck = false;
    let distCheck = false;
    let doCheck = false;

    let reg_tel = /^09\d{8}$/;
    let tel = $("#fasttel").val()
    if(reg_tel.test(tel)){
        telCheck = true;
    }
    let name =  $("#fastname").val()
    let reg_name = /^[\u4e00-\u9fa5\w]{2,15}$/;
    if(reg_name.test(name)){
        nameCheck = true ;
    }
    let add =  $("#fastadd").val()
    let reg_add = /^[\u4e00-\u9fa5\w]{5,100}$/;
    if(reg_add.test(add)){
        addCheck = true ;
    }
    let city =  $("#fastcity").val()
    if( city != ""){
        cityCheck = true ;
    }
    let dist =  $("#fastdist").val()
    if( dist != ""){
        distCheck = true ;
    }
    let DO =  $("#fastdo").val()
    if( DO != "" ){
        doCheck = true ;
    }
    if(telCheck && nameCheck && addCheck && cityCheck && distCheck && doCheck){
    	let y = $("input[id='foradd']").length
    	for(let i = 0 ; i < y ; i ++){
            let id = $("input[id='foradd']:eq("+ i +")").val();
            let AreaSelect = document.getElementById("dist"+id)
            $("select[id='city"+id+"']").val($("#fastcity").val());
            $("select[id='city"+id+"']").on("change",function(e){
            	AddressSeleclList.AppendArea(e , AreaSelect)
            })
            $("select[id='city"+id+"']").change();
            $("input[id='tel"+id+"']").val($("#fasttel").val())
            $("input[id='add"+id+"']").val($("#fastadd").val())
            $("input[id='name"+id+"']").val($("#fastname").val())
            $("select[id='do"+id+"']").val($("#fastdo").val())
            $("#msg"+id).val($("#fastmsg").val())
            $("select[id='dist"+id+"']").val($("#fastdist").val())
            
            $("input[id='add"+id+"']").css("border-color" , "green");
        	$("input[id='name"+id+"']").css("border-color" , "green");
        	$("input[id='tel"+id+"']").css("border-color" , "green");
            $("select[id='city"+id+"']").css("border-color" , "green");
            $("select[id='dist"+id+"']").css("border-color" , "green");
            $("select[id='add"+id+"']").css("border-color" , "green");
            $("select[id='do"+id+"']").css("border-color" , "green");
            
            
    	}
    	$("#fastclose").click();
    }else{
    	swal(
    		"哦哦！錯誤囉",
    		"有資訊未填齊！",
    		"warning"
    	)
    }
})
    
})










function check(i){
	
    
	$("input[id='add"+i+"']").css("border-color" , "red");
	$("input[id='name"+i+"']").css("border-color" , "red");
	$("input[id='tel"+i+"']").css("border-color" , "red");
    $("select[id='city"+i+"']").css("border-color" , "red");
    $("select[id='dist"+i+"']").css("border-color" , "red");
    $("select[id='add"+i+"']").css("border-color" , "red");
    $("select[id='do"+i+"']").css("border-color" , "red");
    
    
    $("input[id='tel"+i+"']").on("input",function(){
    	let reg_tel = /^09\d{8}$/;
    	let tel = $("input[id='tel"+i+"']").val()
        if(reg_tel.test(tel)){
            $("input[id='tel"+i+"']").css("border-color" , "green")
        }else{
            $("input[id='tel"+i+"']").css("border-color" , "red")
        }
    })

    $("input[id='name"+i+"']").on("input",function(){
    	let name =  $("input[id='name"+i+"']").val()
    	let reg_name = /^[\u4e00-\u9fa5\w]{2,15}$/;
    	if(reg_name.test(name)){
    		$("input[id='name"+i+"']").css("border-color" , "green")
        }else{
        	$("input[id='name"+i+"']").css("border-color" , "red")
        }

    })
    
    $("input[id='add"+i+"']").on("input",function(){
    	let add =  $("input[id='add"+i+"']").val()
    	let reg_add = /^[\u4e00-\u9fa5\w]{5,100}$/;;
    	if(reg_add.test(add)){
    		$("input[id='add"+i+"']").css("border-color" , "green")
        }else{
        	$("input[id='add"+i+"']").css("border-color" , "red")
        }

    })


    $("select[id='city"+i+"']").on("change",function(){
    	let city =  $("select[id='city"+i+"']").val()
    	if( city != ""){
    		$("select[id='city"+i+"']").css("border-color" , "green")
        }else{
        	$("select[id='city"+i+"']").css("border-color" , "red")
        }

    })

    $("select[id='dist"+i+"']").on("change",function(){
    	let dist =  $("select[id='dist"+i+"']").val()
    	if( dist != ""){
    		$("select[id='dist"+i+"']").css("border-color" , "green")
        }else{
        	$("select[id='dist"+i+"']").css("border-color" , "red")
        }

    })

    $("select[id='do"+i+"']").on("change",function(){
    	let DO =  $("select[id='do"+i+"']").val()
    	if( DO != "" ){
    		$("select[id='do"+i+"']").css("border-color" , "green")
        }else{
        	$("select[id='do"+i+"']").css("border-color" , "red")
        }

    })
    
}


function checksubmit(i){
    let point = false;
    let telCheck = false;
    let nameCheck = false;
    let addCheck = false;
    let cityCheck = false;
    let distCheck = false;
    let doCheck = false;

    let reg_tel = /^09\d{8}$/;
    let tel = $("input[id='tel"+i+"']").val()
    if(reg_tel.test(tel)){
        telCheck = true;
    }
    let name =  $("input[id='name"+i+"']").val()
    let reg_name = /^[\u4e00-\u9fa5\w]{2,15}$/;
    if(reg_name.test(name)){
        nameCheck = true ;
    }
    let add =  $("input[id='add"+i+"']").val()
    let reg_add = /^[\u4e00-\u9fa5\w]{5,100}$/;
    if(reg_add.test(add)){
        addCheck = true ;
    }
    let city =  $("select[id='city"+i+"']").val()
    if( city != ""){
        cityCheck = true ;
    }
    let dist =  $("select[id='dist"+i+"']").val()
    if( dist != ""){
        distCheck = true ;
    }
    let DO =  $("select[id='do"+i+"']").val()
    if( DO != "" ){
        doCheck = true ;
    }
    if(telCheck && nameCheck && addCheck && cityCheck && distCheck && doCheck){
        point = true;
    }
    return point;
}






function checkall(){
	let ele ;
	let total = parseInt($("#total").val());
	
    let sum = 0;
    let msg = "";
    let pay = $("#paymhtod").val();
    let i = $("input[id='foradd']").length
        for(let y = 0 ; y < i ; y ++){
            let id = $("input[id='foradd']:eq("+ y +")").val();
            if(checksubmit(id)){
                sum++;
            }else{
                msg +="'商家"+ id +"'"
            }
        }
    $.ajax({
    	url:"/CFA101G4/MemberServlet",
    	data:{
    		"action" : "checkEle"
    	},
    	success:function(data){
    		ele = parseInt(data);
    		
    		if( pay == 1 && ele<total){
    	    	swal({
    	    		  title: '錢包餘額不足！',
    	    		  type: 'warning',
    	    		  html:
    	    		    "<a target='_blank' href='/CFA101G4/front-end/Member/storeMoney.html'>" +
    	    		    "<button type='button' class='btn btn-primary'>儲值去！</button>" +
    	    		    "</a>",
    	    		  showCloseButton: true,
    	    		  confirmButtonText:
    	    		    '確認！',
    	    		})
    	    	return;
    	    }
    	    
    	    else if(sum == i && pay != ""){
            	allCheck = true
    	        $("#submit").click();
            	swal("即將回到首頁")
            	
    	    }else{
    	        if(pay ==""){
    	            msg += "'付款方式'";
    	        }
    	        swal(msg+"訂單資訊未填齊")
    	    }
    	}
    })
    
    
    
    
    
}


window.onload = function () {
    	



        $("#paymhtod").css("border-color" , "red")

    	console.log($("input[id='foradd']").length)
    	let i = $("input[id='foradd']").length

        for(let y = 0 ; y < i ; y ++){
            let id = $("input[id='foradd']:eq("+ y +")").val();
            AddressSeleclList.Initialize("city"+id, "dist"+id);
            check(id);
        }


    	$("#paymhtod").on("change",function(){
            if($("#paymhtod").val() != ""){
                $("#paymhtod").css("border-color" , "green")
            }else{
            	$("#paymhtod").css("border-color" , "red")
            }
        })


        $("#push").on("click" , function(){
            checkall()
        })

   }