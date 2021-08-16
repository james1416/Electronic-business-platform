$.ajax({
    url:"/CFA101G4/notice",
    type:"GET",
    data:{"action":"checkAllNotices"},
    dataType:"json",
    success:function(data){
        console.log(data)
		
        var obj = data;
        
        for(var key in data){
			
            if(data[key].nt_view==0){
                data[key].nt_view='未讀';
            }else{
                data[key].nt_view='已讀';
            }
            

            
            
            
            
				let html = ` <div class="col shadow-sm p-3 mb-5 bg-white rounded" style="background-color: white; margin-bottom: 15px!important;">
                <div class="col border border-$gray-500">
                    <div class="row">
                        <div class="col text-start">
                            <div class="row">
                                <div class="col-3 align-self-center text-center">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-award" viewBox="0 0 16 16">
                                        <path d="M9.669.864 8 0 6.331.864l-1.858.282-.842 1.68-1.337 1.32L2.6 6l-.306 1.854 1.337 1.32.842 1.68 1.858.282L8 12l1.669-.864 1.858-.282.842-1.68 1.337-1.32L13.4 6l.306-1.854-1.337-1.32-.842-1.68L9.669.864zm1.196 1.193.684 1.365 1.086 1.072L12.387 6l.248 1.506-1.086 1.072-.684 1.365-1.51.229L8 10.874l-1.355-.702-1.51-.229-.684-1.365-1.086-1.072L3.614 6l-.25-1.506 1.087-1.072.684-1.365 1.51-.229L8 1.126l1.356.702 1.509.229z"/>
                                        <path d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1 4 11.794z"/>
                                      </svg>${obj[key].nt_id}
                                </div>
                                <div class="col-2 text-center align-items-center p-0">
                                    
                                      <button type="button" class="btn btn-danger align-self-center" style="font-size: 12px;"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-chat-dots-fill" viewBox="0 0 16 16">
                                        <path d="M16 8c0 3.866-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.584.296-1.925.864-4.181 1.234-.2.032-.352-.176-.273-.362.354-.836.674-1.95.77-2.966C.744 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7zM5 8a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                                      </svg> 聊聊</button>
                                </div>
                                <div class="col-2 text-center align-items-center p-0">
                                    <button type="button" class="btn btn-light" style="font-size: 12px;padding-right: 0px; padding-left: 0px;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-fill" viewBox="0 0 16 16">
                                        <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5z"/>
                                      </svg>查看賣場</button>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col text-end">
                            
                        </div>
                    </div>
                    
                </div>
                <div id="goods-${key}">
                    <div class="col">
                            <div class="row border border-$gray-500" style="height: 100px;">
                                <div class="col-2 text-center align-self-center" style="margin-top: 5px; data-pic="" id="pic">
                                    
                                </div>
                                <div class="col-8">
                                    <br>
                                    ${data[key].nt_text}<br><br>
                                    ${data[key].nt_time}
                                </div>
                                    <div class="col-2 align-self-center" style="padding-left: 0px; padding-right: 0px; font-size: 15px;text-align: center;" name="ntid" data-ntid="${data[key].nt_id}" data-view="${data[key].nt_view}">`
                                    if(data[key].nt_view=='未讀'){
                                         html+=`<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-dash-fill" viewBox="0 0 16 16">
                                         <path fill-rule="evenodd" d="M11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
                                         <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                                        </svg>&nbsp${data[key].nt_view}`
                                     }else{
                                         html+=`<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-check-fill" viewBox="0 0 16 16">
                                         <path fill-rule="evenodd" d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                                         <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                                         </svg>&nbsp${data[key].nt_view}`
                                     }
                                

                                 html+=`</div>
                                    
                            </div>
                        </div>
                </div>   
                        
                    <div class="row align-items-center">
                        <div class="col-6 text-center align-self-end" id="sum-${key}">
                                	
                        </div>
                        <div class="col-6 text-end">
                                    
                        </div>
                    </div>	                    
            </div>`
			$("#ajaxchange").append(html);
	}

    $("#allRead").on("click", function(e){

        let obj = document.getElementsByName("ntid");
        for(var key in obj){
            console.log(obj[key].dataset.ntid)
            
            $.ajax({
                url:"/CFA101G4/notice",
                data:{
                    "action":"changeViewTo1",
                    "nt_id":obj[key].dataset.ntid
                },
                type:"GET",
                success:function(data){
                    console.log(data)
                    window.location.reload();
                }
            })
        }
      
    })
    let read=0
    let unread=0;
    let array = document.getElementsByName("ntid");
    for(var i=0;i<array.length;i++){

        if(array[i].dataset.view=="未讀"){
           unread+=1;
        }else{
           read+=1;
        }
    }	
    console.log(read);
    console.log(unread);

    if(unread>0){
        console.log("未讀的筆數大於1")
    }else if(unread<=0){
        console.log("全部皆已讀")
        $("#allRead").prop("disabled", "true");
    }
},
    error:function(data){
        alert("有誤！")
    }
})


