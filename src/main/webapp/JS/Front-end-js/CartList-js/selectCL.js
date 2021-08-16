let len = 50;
    $(".carddesc").each(function(i){
          if($(this).text().length>len){
          $(this).attr("title", $(this).text());
          let text  = $(this).text().substring(0, len-1)+"...";
          $(this).text(text);
   }
})

function PDAllTotal(){
	let alltotal = 0;
	let all = $("div[id='PDprice']").length
	
	for(let i = 0 ; i < all ; i++ ){
		let pdtotal = parseInt($("div[id='PDtotal']:eq("+ i +")").text())
		alltotal = alltotal + pdtotal
	}
	$("#alltotal").text(alltotal)
	
}

function check(){
	let input = $("input[id='clpq']").length
	console.log(input)
	for(let i = 0 ; i < input ; i++){
		if($("input[id='clpq']:eq("+ i +")").val() === "1")
			$("input[id='down']:eq("+ i +")").prop({"disabled" : true})
			
	$.ajax({
        url:"/CFA101G4/ProductServlet",
        dataType:"json",
        data:{
        	pid : $("div[id='clpid']:eq("+i+")").text(),
        	action : "findPDforAJAX",
        },
        success:function(data){
        		if($("input[id='clpq']:eq("+ i +")").val() == data){
        			$("input[id='up']:eq("+ i +")").prop({"disabled" : true})
        		}
        		if($("input[id='clpq']:eq("+ i +")").val() > data){
        			console.log("大於了")
        			$("input[id='clpq']:eq("+ i +")").val("1")
        			pdprice = parseInt($("div[id='PDprice']:eq("+ i +")").text())
		        	let total = parseInt(1)
		        	$("div[id='PDtotal']:eq("+ i +")").text(pdprice*total) 
        			PDAllTotal();
        		}
        	}
    	})
	}
	
}

function checkStock(e,i){
	console.log("去看數量的"+ $("div[id='clpid']:eq("+i+")").text())
	$.ajax({
        url:"/CFA101G4/ProductServlet",
        dataType:"json",
        data:{
        	pid : $("div[id='clpid']:eq("+i+")").text(),
        	action : "findPDforAJAX",
        },
        success:function(data){
        		console.log("輸入框多少" + $("input[id='clpq']:eq("+ i +")").val())
        		console.log("data來多少"+data)
        		if($("input[id='clpq']:eq("+ i +")").val() == data){
        			$("input[id='up']:eq("+ i +")").prop({"disabled" : true})
        		}
        	}
    	})
}



function init() {
	
	PDAllTotal();
	check();
	$("#accounts").on("click",function(){
		
		
		let sum = parseInt($("#cart-sum").text())
		let checkPoint = 0 ;
		
		let pqlength = $("input[id='clpq']").length
			for(let i = 0 ; i < pqlength ; i++){
				if($("input[id='clpq']:eq("+ i +")").val() === "0"){
					checkPoint++;
				}
			}
		
		if(sum != 0 && checkPoint == 0){
			$("#submit").click()
		}else if(checkPoint != 0 ){
			swal(
					"您購物車內有商品已完售！",
					"記得把它刪除喔！",
					"warning"
					)
		}
		else{
		swal(
				"您沒有選購任何商品！",
				"去商城挑挑東西吧！",
				"warning"
				)
		}		
		
	})
	
	
	
	$("#CLbody").on("click",function(e){
		
		 
		if(e.target.getAttribute("id") === "del"){
			let i = $(e.target).parent().parent().parent().index();
			let sum = parseInt($("#cart-sum").text())
			$("#cart-sum").html(sum-1)
			$.ajax({
		        url:"/CFA101G4/CartListServlet",
		        dataType:"json",
		        data:{
		        	pid : $("div[id='clpid']:eq("+i+")").text(),
		        	action : "del",
		        },
		        success:function(data){
		        	$(e.target).parent().parent().parent().remove();
		        	PDAllTotal()
		        }
		    })
		}
		
		
		
		if(e.target.getAttribute("id") === "up"){
			let i = $(e.target).parent().parent().parent().parent().index();
			$.ajax({
		        url:"/CFA101G4/CartListServlet",
		        dataType:"json",
		        data:{
		        	pid : $("div[id='clpid']:eq("+i+")").text(),
		        	action : "updata",
		        	up_or_down : "up"
		        },
		        success:function(data){
		        	$("input[id='clpq']:eq("+ i +")").prop("value", data)
		        	pdprice = parseInt($("div[id='PDprice']:eq("+ i +")").text())
		        	let total = parseInt(data)
		        	$("div[id='PDtotal']:eq("+ i +")").text(pdprice*total)
		        	PDAllTotal()
		        	$("input[id='down']:eq("+ i +")").prop({"disabled" : false})
		        	checkStock(e,i)
		        }
		    })
		}
		
		
			
		if(e.target.getAttribute("id") === "down"){
			let i = $(e.target).parent().parent().parent().parent().index();
			$.ajax({
		        url:"/CFA101G4/CartListServlet",
		        dataType:"json",
		        data:{
		        	pid :$("div[id='clpid']:eq("+i+")").text(),
		        	action : "updata",
		        	up_or_down : "down"
		        },
		        success:function(data){
		        	$("input[id='clpq']:eq("+ i +")").prop("value", data)
		        	pdprice = parseInt($("div[id='PDprice']:eq("+ i +")").text())
		        	let total = parseInt(data)
		        	$("div[id='PDtotal']:eq("+ i +")").text(pdprice*total) 
		        	PDAllTotal()
		        	
		        	if(total === 1){
		        		$(e.target).prop({"disabled" : true})
		        	}
		        	$("input[id='up']:eq("+ i +")").prop({"disabled" : false})
		        	
		        }
		    })
		}
	
	})
	
	
	
	
}
window.onload = init;