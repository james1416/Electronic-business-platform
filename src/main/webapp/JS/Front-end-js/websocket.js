   //0718聊天室JS

   var self = "";
   var MyPoint = "";
   var endPointURL="";
   var host = window.location.host;
   var messagesArea = document.getElementById("messagesArea");
   var webSocket;
   
 //時間格式化
   Date.prototype.format = function (format) {
       var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S": this.getMilliseconds()
       };
       if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
       }
       for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
               format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
       }
       return format;
   };
   

  $("#chatroomOpen").on("click", function(){
   $.ajax({
       url: "/CFA101G4/MemberServlet",
       type:"POST",
       data:{action:"findSingleMem"},
       dataType:"json",
       success:function(data){
           console.log(data)
           MyPoint = `/websocketDemo/${data.mem_acc}`;
           self=data.mem_acc;
           endPointURL = "ws://"+window.location.host+"/CFA101G4"+MyPoint;
           console.log(MyPoint);	
           console.log(endPointURL);	
           console.log(self); 
           connect();
           $(".area").html("");
           $(".chatroomHeaderLeft").html("");
       } 
   })
})

//$("#connect").on("click", function(){
//   connect();
//})
$("#sendMessage").on("click", function(){
   sendMessage();
})

$("#textbox").on("keydown", function(event){
   if(event.which==13){
       sendMessage();
   }
})

function sendMessage(){
   var inputMessage = document.getElementById("textbox");
   var friend = $(".chatroomHeaderLeft").text();
   var message = $(".textbox").val().trim();
   console.log(friend)
   if(message===""){
	   swal({
      	 title: '錯誤！',
      	 text: '請輸入訊息！',
      	 type: 'error',
      	 confirmButtonText: '重試'
      		})
   }else if(friend===""){
	   console.log(friend)
	   swal({
      	 title: '錯誤！',
      	 text: '請選擇一位好友！',
      	 type: 'error',
      	 confirmButtonText: '重試'
      		})

   }else{
	   var now = new Date();
	   let time = now.format("yyyy-MM-dd hh:mm:ss");
	   console.log(time);
       var jsonObj={
               "type":"chat",
               "sender":self,
               "receiver":friend,
               "message":message,
               "time":time
                   };
       webSocket.send(JSON.stringify(jsonObj));
       $("#textbox").val("");
       $("#textbox").focus();  
   }
}



function connect(){
   webSocket = new WebSocket(endPointURL);

   webSocket.onopen = function(e){
       console.log("connect success!")
       $("#sendMessage").prop("disabled", false);
   }
   webSocket.onmessage = function(e){
	   console.log(e.data);
       var jsonObj = JSON.parse(e.data); //e.date是拿到傳回來的chatMessage物件(JSON格式) 再把他轉成JS物件
       console.log(jsonObj)
       if("open"===jsonObj.type){
           console.log("open")
           refreshFriendList(jsonObj);
       }
       if("history"===jsonObj.type){
           
            $(".area").html("");
           var messages =  JSON.parse(jsonObj.message);
           
           for(var i=0; i<messages.length;i++){
               var historyData = JSON.parse(messages[i]);
               
               var showMsg = historyData.message; 
               var showTime = historyData.time;
               let myself = `<li class="me"><span class="now">${showTime}</span>${showMsg}</li>`
               let myfriend = `<li class="friend">${showMsg}<span class="now">${showTime}</span></li>`
               historyData.sender===self? $(".area").append(myself) : $(".area").append(myfriend);
           }
           document.getElementById("messageArea").scrollTop = document.getElementById("messageArea").scrollHeight;
       }else if("chat"===jsonObj.type){
    	   console.log(jsonObj.type)
    	   
           let selfmsg = `<li class="me"><span class="now">${jsonObj.time}</span>${jsonObj.message}</li>`
           let friendmsg = `<li class="friend">${jsonObj.message}<span class="now">${jsonObj.time}</span></li>` 
           
           jsonObj.sender===self ? $(".area").append(selfmsg) : $(".area").append(friendmsg);

    	  document.getElementById("messageArea").scrollTop = document.getElementById("messageArea").scrollHeight; 
       }else if("close" === jsonObj.type){
    	   refreshFriendList(jsonObj);
    	   console.log("close");
       }
   }
   webSocket.onclose=function(e){
       console.log("Disconnected")
   }
}

$("#chatroomHide").on("click", disconnect);


function disconnect(){
   webSocket.close();
   console.log("close");
}


  // 註冊列表點擊事件並抓取好友名字以取得歷史訊息 沒送訊息過去 主要是為了要把歷史資料拉回來
function addListener(){
  $(".memberRow").on("click", 'h5', function(e){
	  console.log("hello");
       console.log(e.target.outerText);
       $(".chatroomHeaderLeft").html(e.target.outerText);
       var friend = e.target.outerText;
       var jsonObj = {
           "type":"history",
           "sender": self,
           "receiver": friend,
           "message" : "",
              "time" : ""
            }
           webSocket.send(JSON.stringify(jsonObj));
       })
   }
//更新好友名單
function refreshFriendList(jsonObj){
	let friendRow = "";
   var friends = jsonObj.users;
   for(i=0; i<friends.length;i++){
       if(friends[i]===self){
    	   console.log(friends[i]);
           continue;
       }
       console.log(friends[i]);
       $(".memberRow").html("");
        friendRow += `<div class="oneMember">
                           <h5>${friends[i]}</h5>
                       </div>`
    	   console.log(friendRow)
   }
   $(".memberRow").html(friendRow);
   addListener();
}
