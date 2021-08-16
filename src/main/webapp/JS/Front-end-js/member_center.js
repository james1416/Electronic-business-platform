//會員中心左側登入後自動抓取個人身分資料
let date = new Date().getTime();
console.log(date);
$.ajax({	
	url:"/CFA101G4/MemberServlet?"+date,
    type:"POST",
    data:{action:"findSingleMem"},
    dataType:"json",
    success:function(data){

    	let newTable = `<table id="table">
                                <tr><th>使用者帳號</th><td><input type="hidden" id="account" name="account" value="${data.mem_acc}">${data.mem_acc}</td></tr>
                                <tr><th>姓名</th><td><input type="text" id="name" name="name" value="${data.mem_name}"></td></tr>
                                <tr><th>Email</th><td><input type="text" id="email" name="email" value="${data.mem_email}">
                                </td></tr>
                                <tr><th>手機號碼</th><td><input type="text" id="tel" name="tel" value="${data.mem_tel}"></td></tr>
                                <tr><th>賣場名稱</th><td>${data.mem_acc}</td></tr>
                                <tr><th>性別</th><td>男<input type="radio" name="gender" value="1">&nbsp;女<input type="radio" name="gender" value="0"></td></tr>
                                <tr><th>生日</th><td><input type="date" id="bth" name="bth"></td></tr>
                        </table>
    					<button class="button" type="button" id="savePersonalData">儲存</button>`
    	
    	$("#form").html(newTable);
    	$(".accountName").html(data.mem_acc);  
    		$.ajax({
    			url:"/CFA101G4/MemberServlet",
    		    type:"POST",
    		    data:{
    		    	"action" : "imgto64"
    		    },
    		    success:function(img){
    		    	let userIcon = `<img src="data:image/gif;base64,${img}" alt="">`
    		    	$("#icon").html(userIcon);
    		    }
    		})
    		
    }

})
	let count = 0;
    $.ajax({
    	url:"/CFA101G4/notice?"+date,
    	data:{
    		"action":"checkNoticebyMemid"
    		},
    	dataType:"json",
    	type:"GET",
    	success:function(data){
    		console.log(data);
    		for(var i=0;i<data.length;i++){
    			if(data[i].nt_view==0){
    				count++;
    			}
    		}
    		console.log(count)
    		$("#noticeCount").html(count);
    	}
    })



//會員資料更新正則表達式
    function checkName(){
        let name = $("#name").val();
        let reg_name = /^[\u4e00-\u9fa5\w]{1,15}$/;
        let flag = reg_name.test(name);
        if(flag){
            $("#name").css("border", "3px solid green");
        }else{
            $("#name").css("border", "3px solid red");
        }
        return flag;
    }
    function checkEmail(){
        let email = $("#email").val();
        let reg_email = /^[^\[\]\(\)\\<>:;,@.]+[^\[\]\(\)\\<>:;,@]*@[a-z0-9A-Z]+(([.]?[a-z0-9A-Z]+)*[-]*)*[.]([a-z0-9A-Z]+[-]*)+$/g;
        let flag = reg_email.test(email);
        if(flag){
            $("#email").css("border", "3px solid green");
        }else{
            $("#email").css("border", "3px solid red");
        }
        return flag;
    }
    function checkTel(){
        let tel = $("#tel").val();
        let reg_tel = /^09\d{8}$/;
        let flag = reg_tel.test(tel);
        if(flag){
            $("#tel").css("border", "3px solid green");
        }else{
            $("#tel").css("border", "3px solid red");
        }
        return flag;
    }
    function checkBth(){
        let bth = $("#bth").val();
        let flag = true;
        if(bth!=""){
            $("#bth").css("border", "3px solid green");
            flag = true;
        }else{
            $("#bth").css("border", "3px solid red");
            flag = false;
        }
        return flag;
    }
    function checkGender(){
    	let gender = $('input:radio[name="gender"]').val();
    	flag = true;
    	if(gener == null){
    		alert("你沒選")
    		flag = false;
    		return flag;
    	}else{
    		alert(gender);
    		flag = true;
    		return flag;
    	}
    }
//event delegate一定要做，否則以template新增的版型無法綁定JS事件
    $("#form").on("input", "[name~='name']" ,checkName);
    $("#form").on("input", "[name~='bth']", checkBth);
    $("#form").on("input","[name~='email']", checkEmail);
    $("#form").on("input","[name~='tel']", checkTel);


//入口函式
$(function(){
	//請問each()可以綁event delegation嗎? 我怎麼綁不上去?
//	let len = 50;
//    $(".notice").on("each", 'p',function(){
//        if($(this).text().length>len){
//            $(this).attr("title", $(this).text());
//            let text  = $(this).text().substring(0, len-1)+"...";
//            $(this).text(text);
//        }
//    })
	$(".myAccountInfo li a:eq(1)").on("click", function(){
		let myTransactionHead=`<div class="myDocument">
            <div class="myDocument1">
                更改密碼
            </div>
            <div class="myDocument2">
                為了保護你帳號的安全，請不要分享你的密碼給其他人
            </div>
        </div>
        <div class="inputCollection">
             <div class="updateNewPW">
		            <div class="nowPW">
		                <div>現在的密碼</div>
		                <div><input type="password" id="nowPWD" class="nowPWD" data-type="nowPWD"></div>
		            </div>
		                <div class="checkOriginPW"></div>
		            <div class="newPW">
		                <div class="newPWDText">新的密碼</div>
		                <div><input type="password" id="newPWD" class="newPWD" data-type="newPWD"></div>
		            </div>
		            <div class="confirmPW">
		                <div class="confirmPWText">確認密碼</div>
		                <div><input type="password" id="confirmPWD" class="confirmPWD data-type="confirmPWD"></div>
		            </div>
		            <button type="button" id="changePWButton" data-type="button" class="changePWButton">確認</button>
			</div>
        </div>
    </div>`
	$(".containerRight").html(myTransactionHead);
		
})		

let flagOfcheck = true;
		$(".containerRight").on("blur", "[data-type~='nowPWD']", function(){	
//			alert("hello");
			
			$.ajax({
				url: "/CFA101G4/MemberServlet",
				type:"POST",
				data:{
					action:"checkPWExist",
					mem_pw:$("#nowPWD").val()
				},
				dataType:"json",
				success:function(data){
					console.log(data);
					if(data.userExist){
						$(".checkOriginPW").html(data.msg)
						$(".checkOriginPW").css("color", "green")
						flagOfcheck = true;
					}else{
						$(".checkOriginPW").html(data.msg)
						$(".checkOriginPW").css("color", "red")
						flagOfcheck = false;
					}				
				},
				error:function(data){
					alert("密碼有誤！")
				}
			})
		})
		
		
			
		$(".containerRight").on("input", "[data-type~='newPWD']", checkNewPassword)	
		
		
		
		function checkNewPassword(){
        let password = $("#newPWD").val();
        let reg_password = /^\w{5,15}$/;            
        let flag = reg_password.test(password);
        if(flag){
            $("#newPWD").css("border", "3px solid green");
        }else{
            $("#newPWD").css("border", "3px solid red");
        }
        return flag;
    }
	
		function repeat(){
			var repeatPW = $("#confirmPWD").val()
			var password = $("#newPWD").val();
			console.log(password)
			var flag = true;
			if(repeatPW==password){
				flag=true;	
				return flag;
			}else{
				flag=false;
				return flag;
			}
		}
			
		$(".containerRight").on("click","[data-type~='button']" ,function(){
			
         if(repeat() && checkNewPassword() && flagOfcheck){
        	 console.log(repeat())
        	 console.log(checkNewPassword())
        	 console.log(flagOfcheck)
        	 let newPWD =  $("#newPWD").val();
 			$.ajax({
 				url:"/CFA101G4/MemberServlet",
 				type:"POST",
 				data:{
 					action:"updateMemPW",
 					mem_pw:newPWD	
 				},
 				success:function(data){
 					console.log(data);
 					swal('密碼已更新！',
 						 '成功更新您的密碼！',
 						 'success'
 							)
 				}
 			}) 
         }else{
        	 swal({
        		    title: '錯誤！',
        		    text: '更新密碼失敗！請再次確認密碼是否皆填寫正確',
        		    type: 'error',
        		    confirmButtonText: '再試一次'
        		})
         }
})
			

    
    //會員中心右上角通知總覽之hover效果以及字節數超過顯示上限後顯示...效果
    $(".naviRight a:eq(0)").hover(function(){
        $(".notice").show("fast");
         $(".notice").mouseleave(function(){
        	$(".notice").hide("fast");
        })
        
        $.ajax({
        	url:"/CFA101G4/MemberServlet",
        	data:{action:"getAllNotice"},
        	dataType:"json",
        	success:function(data){
        	
        		console.log(data);
        		let html="";
        		for(let i=0;i<5;i++ ){
        			html += `<tr><th>${(data[i].nt_time)}</th><td><p class="JQellipsis">${data[i].nt_text}</p></td></tr>`
        			$(".noticeTable").html(html);
        		}
        		let len = 40;
        	    $(".JQellipsis").each(function(i){
        	        if($(this).text().length>len){
        	            $(this).attr("title", $(this).text());
        	            let text  = $(this).text().substring(0, len-1)+"...";
        	            $(this).text(text);
        	        }
        	    })
        	}
        })
    })
	
//圖片上傳onchange事件
	
    let data ;
    $("#newIcon").on("change", function(e){   	
        let file = e.target.files[0];  
        if(file.type.indexOf('image')>-1){
        	 $("#buttonIcon").prop("disabled", false);  	
             let reader = new FileReader();
             $("#responsePic").html("");
             reader.readAsDataURL(file);
             reader.onload = function(e){
                 data = e.target.result;
                 console.log(data);
                 let imgsrc = `<img src="${data}" alt="">`
                 $("#pictureUpload").html(imgsrc);
             }
        }else{
        	$("#buttonIcon").prop("disabled", true); 
        	swal('個人圖片更新失敗！',
        			'請確認您上傳的檔案格式正確',
					'error'
							)
        }
       
    })
    
    //圖片上傳點擊上傳
     $("#buttonIcon").on("click", function(){
    	 var form = $('form')[1];
    	 var formData = new FormData(form);
    	 formData.append("action", "uploadPic");
        $.ajax({
            url:"/CFA101G4/MemberServlet",
            type:"POST",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success:function(data){
            	//rollback function中再次呼叫
            	 let mem_id = $("#mem_id").val();
                 $.ajax({
                    url:"/CFA101G4/MemberServlet",
                    type:"POST",
                    data: {mem_id:mem_id, action:"readIcon"},
                    success:function(data){
                    	console.log(data)
                        console.log("左上角icon更新")
                        let newicon = `<img src="data:image/gif;base64,${data}" alt="">`
                        $("#icon").html(newicon);
                    	swal('個人圖片已更新！',
        					'成功更新頭像！',
        					'success'
        							)
                    },
                })
            	console.log(data)
                console.log("更新個人圖片成功")
                $("#responsePic").html("個人圖片更新成功！");
            	$("#responsePic").css("color", "green");
            	
            	
            },
            error: function(data){
            	$("#responsePic").html("個人圖片更新失敗！");
            	$("#responsePic").css("color", "red");
            	
            }
        })
       
        
        //CSS操縱範例
//        let span = $("#s_username");
//        span.css("color", "red");
//		span.html(data.msg);
    })

    //登出按鈕 消滅session
        $("#logout").on("click", function(){
    	$.ajax({
    		url:"/CFA101G4/MemberServlet",
    		cache: false,
    		type:"POST",
    		data:{action:"exit"},
    		success:function(data){
            	location.href = "/CFA101G4/front-end/FrontMain.jsp";
            }
    	})
    })
    
    //會員資料更新前驗證
    	
    $("#form").on("click", "[type=button]", function(){
    	if(checkName() && checkBth() && checkEmail() && checkTel() ){
    	$.ajax({
    		url: "/CFA101G4/MemberServlet?action=updateMem",
    		type: "POST",
    		data: $("#form").serialize(),
    		success:function(data){
    			swal(	  '做得好！',
    					  '你成功更新了個人資料！',
    					  'success'
    					)
//    			alert(data);   			
    		},
    		error:function(data){
    			sweetAlert(
    					  '資料更新失敗！',
    					  '請檢查欄位是否皆填寫無誤！',
    					  'error'
    					)
    			
//    			alert("資料更新失敗！ 請檢查欄位是否皆填寫無誤！")
    		}
    	})
    }else{
    	sweetAlert(
				  '資料更新失敗！',
				  '請檢查欄位是否皆填寫無誤！',
				  'error'
				)
//    	alert("資料更新失敗！ 請檢查欄位是否皆填寫無誤！")
    	return false;
    	
    }
})
 		
		$(".buyListInfo li a:eq(1)").on("click", function(){
		
			let myTransactionHead=`<div class="myDocument">
                <div class="myDocument1">
                    錢包交易紀錄
                </div>
                <div class="myDocument2">
					您目前的錢包餘額為：<span id="mem_ele" style="color:#ff7300"></span>元<br>
                    所有交易歷程如下：
                </div>
            </div>
            <div class="inputCollection">
                  
           
            </div>
        </div>
        <span class="totalCount">總筆數：</span>
            <span class="currentPage">目前頁數：</span>
			<nav aria-label="Page navigation example" id="nav" style="position: absolute; bottom: 0px;">
                <ul id="pageNumRow" class="pagination justify-content-end" style="margin: 0px;">
	                
		                
	               
                </ul>
            </nav>`
			$(".containerRight").html(myTransactionHead);
			
			loadEW();
			console.log("hi");
//
			$.ajax({
				url:"/CFA101G4/MemberServlet",
				type:"POST",
				data:{
					"action":"getMem_ele"
				},
				dataType:"json",
				success:function(data){
					console.log(data)
					$("#mem_ele").html(data)
				}
			})

			 $(".inputCollection").on("click", "[data-eleid]", function(e){
					
					console.log(e.target.dataset.eleid);
					let ele_id = e.target.dataset.eleid;

			    	$.ajax({
						url:"/CFA101G4/ElectronicwalletServlet",
						type:"POST",
						data:{
							ele_id:ele_id,
							"action":"getOneLog"
								},
						dataType:"json",
						success:function(data){
							console.log(data);
							let noticeDetail=`
				            <div class="inputCollection">
				                  <div class="transactionLog" style="pointer-events: none;">
		                          <div class="transactionDetails">
		                                <div class="detailPics"></div>
		                                	<div class="detail">
			                                    <h4>更新時間：${data.ele_time}</h4>
			                                    <b>${data.ele_rec}</b>
		                                	</div>		                             
				                          </div>
				                      </div>
						            </div>
						        </div>`
								$(".inputCollection").html(noticeDetail);


						}
					})
					
					
				})	
	   
	})	
		
			

	
	
	$(".buyListInfo li a:eq(2)").on("click", function(){
		let myTransactionHead=`<div class="myDocument">
            <div class="myDocument1">
                	天堂鳥通知
            </div>
            <div class="myDocument2">
                	您的通知：
            </div>
        </div>
        <div class="inputCollection">
              
       
        </div>
    </div>
			<span class="totalCount">總筆數：</span>
            <span class="currentPage">目前頁數：</span>
			<nav aria-label="Page navigation example" id="nav" style="position: absolute; bottom: 0px;">
                <ul id="pageNumRow" class="pagination justify-content-end" style="margin: 0px;">
	                
		                
	               
                </ul>
            </nav>`
			$(".containerRight").html(myTransactionHead);
//		寫ajax
			load();
			console.log("hi");
			
	   $(".inputCollection").on("click", "[data-lnid]", function(e){
						
						console.log(e.target.dataset.lnid);
						let ln_id = e.target.dataset.lnid;

				    	$.ajax({
							url:"/CFA101G4/MemberServlet",
							data:{
								ln_id:ln_id,
								action:"listOneLN"
									},
							dataType:"json",
							success:function(data){
								$("#buttonIcon").prop("disabled", false);  	
								console.log(data[0].ln_con);
								let noticeDetail=`
					            <div class="inputCollection">
					                  <div class="transactionLog" style="pointer-events: none;">
			                          <div class="transactionDetails">
			                                <div class="detailPics"></div>
			                                	<div class="detail">
				                                    <h4>發布時間：${data[0].ln_tsp}</h4>
				                                    <b>${data[0].ln_con}</b>
			                                	</div>		                             
					                          </div>
					                      </div>
							            </div>
							        </div>`
									$(".inputCollection").html(noticeDetail);
							}
						})
						
						
					})	
					
			})
//=========================================================
    //左側toggle效果
    
    $("#toggle").on("click", function(){
    	$("#toggle2").slideToggle();
    })
    
    $("#toggle3").on("click", function(){
    	$("#toggle4").slideToggle();
    })
    
    //聊天室toggle效果
    $("#chatroomOpen").on("click", function(){
    	$(".chatroom").slideToggle();
    })
    
    $("#chatroomHide").on("click", function(){
    	$(".chatroom").slideToggle();
    })
  
})
//////////////////////////////////////////////////
	function loadEW(currentPage){
//	console.log(currentPage)
	
	$.ajax({
		url:"/CFA101G4/ElectronicwalletServlet",
		data:{"action":"checkMyWalletbyPage",
			"currentPage":currentPage
			},
		type:"POST",
		dataType:"json",
		success:function(data){
			$(".totalCount").html("總筆數："+data.totalCount)
			$(".currentPage").html("目前頁數："+data.currentPage)
			console.log(data);
			let html="";
			
			for(var i=1; i<=data.totalPage;i++){
				let pageNum=`<li class="page-item" data-type="${i}" id="pageNum"><a class="page-link" href="javascript:loadEW(${i})">${i}</a></li>`
				html+=pageNum;		
			}
			
			
			$("#pageNumRow").html(html);
			
				let previousDisable = `<li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">上一頁</a>
                </li>`
				let previousAble = `<li class="page-item">
		        <a class="page-link" href="javascript:loadEW(${data.currentPage-1})" tabindex="-1">上一頁</a>
		        </li>`
				let nextAble=` <li class="page-item">
	                <a class="page-link" href="javascript:loadEW(${data.currentPage+1})">下一頁</a>
	                </li>`
				let nextDisable=` <li class="page-item disabled">
					<a class="page-link" href="javascript:loadEW(${data.currentPage+1})">下一頁</a>
		          	</li>`
					
					if(currentPage===1||currentPage===undefined){
						$("#pageNumRow").prepend(previousDisable)
					}else{
						$("#pageNumRow").prepend(previousAble)
					}
					if(data.currentPage===data.totalPage){
						$("#pageNumRow").append(nextDisable)
					}else{
						$("#pageNumRow").append(nextAble)
					}
					
				
			let html2 = '';
			for(let i=0;i<data.list.length;i++){
				html2+=`<div class="transactionLog">
                          <div class="transactionDetails">
                                <div class="detailPics"></div>
                                	<div class="detail">
	                                    <h4>更新時間：${data.list[i].ele_time}</h4>
	                                    <b>${data.list[i].ele_rec}</b>
	                                   
                                	</div>
                                <div class="watchmoreDetails">  
                                    <button type="button" class="btn btn-outline-secondary buttonWMD data-type="ele" data-eleid="${data.list[i].ele_id}">查看更多</button>
                                </div>
                          </div>
                      </div>`
			}
				$(".inputCollection").html(html2);

				let len = 30;
        	    $(".detail b").each(function(i){
        	        if($(this).text().length>len){
        	            $(this).attr("title", $(this).text());
        	            let text  = $(this).text().substring(0, len-1)+"...";
        	            $(this).text(text);
        	        }
        	    })
		}
		
	})
	
	
}



		function load(currentPage){
//			console.log(currentPage)
			
			$.ajax({
				url:"/CFA101G4/Latest_newsServlet",
				data:{"action":"checkLNbyPage",
					"currentPage":currentPage
					},
				type:"POST",
				dataType:"json",
				success:function(data){
					$(".totalCount").html("總筆數："+data.totalCount)
					$(".currentPage").html("目前頁數："+data.currentPage)
					console.log(data);
					let html="";
					
					for(var i=1; i<=data.totalPage;i++){
						let pageNum=`<li class="page-item" data-type="${i}" id="pageNum"><a class="page-link" href="javascript:load(${i})">${i}</a></li>`
						html+=pageNum;		
					}
					
					
					$("#pageNumRow").html(html);
					
						let previousDisable = `<li class="page-item disabled">
		                <a class="page-link" href="#" tabindex="-1">上一頁</a>
		                </li>`
						let previousAble = `<li class="page-item">
				        <a class="page-link" href="javascript:load(${data.currentPage-1})" tabindex="-1">上一頁</a>
				        </li>`
						let nextAble=` <li class="page-item">
			                <a class="page-link" href="javascript:load(${data.currentPage+1})">下一頁</a>
			                </li>`
						let nextDisable=` <li class="page-item disabled">
							<a class="page-link" href="javascript:load(${data.currentPage+1})">下一頁</a>
				          	</li>`
							
							if(currentPage===1||currentPage===undefined){
								$("#pageNumRow").prepend(previousDisable)
							}else{
								$("#pageNumRow").prepend(previousAble)
							}
							if(data.currentPage===data.totalPage){
								$("#pageNumRow").append(nextDisable)
							}else{
								$("#pageNumRow").append(nextAble)
							}
							
						
					let html2 = '';
					for(let i=0;i<data.list.length;i++){
						html2+=`<div class="transactionLog">
		                          <div class="transactionDetails">
		                                <div class="detailPics"></div>
		                                	<div class="detail">
			                                    <h4>發布時間：${data.list[i].ln_tsp}</h4>
			                                    <b>${data.list[i].ln_con}</b>
			                                   
		                                	</div>
		                                <div class="watchmoreDetails">  
		                                    <button type="button" class="btn btn-outline-secondary buttonWMD data-type="ln" data-lnid="${data.list[i].ln_id}">查看更多</button>
		                                </div>
		                          </div>
		                      </div>`
					}
						$(".inputCollection").html(html2);

						let len = 30;
		        	    $(".detail b").each(function(i){
		        	        if($(this).text().length>len){
		        	            $(this).attr("title", $(this).text());
		        	            let text  = $(this).text().substring(0, len-1)+"...";
		        	            $(this).text(text);
		        	        }
		        	    })
				}
				
			})
			
			
		}