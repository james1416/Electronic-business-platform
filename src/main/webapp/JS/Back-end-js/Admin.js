 	$.ajax({
			url:"/CFA101G4/AdminServlet",
			dataType:"json",
			data:{action:"getall"},
			success: function (data) {
            let html = "";
            for (let i = 0; i < data.length; i++) {
                html += `
                    <tr>
                		<th>${data[i].admin_id}</th>
                		<th>${data[i].admin_acc}</th>
                		<th>${data[i].admin_pw}</th>
                		<th>${data[i].admin_log}</th>
                		<th>${data[i].admin_sta === 0 ? "禁用" : "啟用"}</th>
                		<th id="more">修改</th>
                	</tr>
                    `;
            }
            $("#table").append(html);
// ajax寫法，利用冒泡事件抓取動態生成的元素 此處為抓取"修改"的按鈕
        	$("#main").on("click",function(e){
        		if (e.target.getAttribute("id") === "more"){
        			$.ajax({
        				url:"/CFA101G4/AdminAuthorityServlet",
        				dataType:"json",
        				data:{adminid : e.target.parentElement.children[0].textContent,
        					action : "getone"},
        				success:function(aut){
        			let num = e.target.parentElement.children[0].textContent - 1;
        			html = `<div class="upbody" id="upbody"><div class="updata" id="updata"></div></div>`;
		            document.getElementById("main").innerHTML += html
		            console.log(aut) // 3 有值
		            console.log(aut.length) // 3 有值
		            let y ;
		            let autcon = "";
		            if(aut.length === 0){
		            	autcon = "無任何權限";
		            }else{
		            	for(y = 0 ; y<aut.length ; y++){
		            		autcon += `${aut[y].aa_autid} : ${aut[y].authorityVO.aut_name}  `;
		            	}
		            	
		            	///測試使用 以後做功能
		            	autcon += `<button id="hello">按我</button>`
		            }
		            more = `
		                <table id="moretable">
		                <caption>管理員資料</caption>
		                <tr><th>管理員編號:</th><td id="updataid">${data[num].admin_id}</td></tr>
		                <tr><th>管理員帳號</th><td><input type="text" id="updataacc" value="${data[num].admin_acc}"></td></tr></td></tr>
		                <tr><th>管理員密碼</th><td><input type="text" id="updatapw" value="${data[num].admin_pw}"></td></tr></td></tr>
		                <tr><th>管理員登入時間</th><td>${data[num].admin_log}</td></tr>
		                <tr><th>管理員狀態</th><td><select id="updatasta"><option value="0">0:禁用</option><option value="1">1:啟用</option></select></td></tr>
		                <tr><th>擁有的權限</th><td>
		                `+
		                autcon
		                +`</td></tr>
		                </table>
		                <button type="submit" id="sub">送出</button><button type="submit" id="cansel">取消</button>
		                `;
//		            <select id="" value=""><option>${data[num].admin_sta}</option><option>1</option></select>
		           let i =  JSON.parse(data[num].admin_sta)
		            $(".updata").html(more);
		            (i === 0 ) ? $("option:eq(0)").attr("selected",true) : $("option:eq(1)").attr("selected",true)
        		
		            
        				}
        			})
        		}
        		
        		
        		///之後做功能
        		if(e.target.getAttribute("id") === "hello"){
        			alert("00")
        				}
        		
        		
        		
        		
        		if(e.target.getAttribute("id") === "sub"){
        			if($("#updataacc").val() === "" || $("#updatapw").val() === ""){
        				alert("請輸入正確的格式");
        			}
        			else{
        				$.ajax({
        					url:"/CFA101G4/AdminServlet",
        					data:{
        						action:"updata",
        						admin_id:$("#updataid").text(),
        						admin_acc:$("#updataacc").val(),
        						admin_pw:$("#updatapw").val(),
        						admin_sta:$("#updatasta").val()
        					},
        					success:function(data){
        						alert("修改成功囉！");
          	    			   window.location.href = "/CFA101G4/back-end/Admin/MainAdmin.html"
        					}
        				})
        			}
        		}
        		if(e.target.getAttribute("id") === "cansel" || e.target.getAttribute("id") === "upbody"){
                    $(".upbody").remove()
            	}
        		if(e.target.getAttribute("id") === "add"){
        	 		html = `<div class="upbody" id="upbody"><div class="updata" id="updata"></div></div>`;
        	        document.getElementById("main").innerHTML += html
        	        more = `
        	        <table id="moretable">
        	        	<caption>新增管理員</caption>
        	        	<tr><th>管理員帳號</th><td><input type="text" id="acc"></td></tr>
        	        	<tr><th>管理員密碼</th><td><input type="text" id="pw"></td></tr>
        	        </table>
        	        <button type="submit" id="addsub">送出</button><button type="submit" id="cansel">取消</button>
        	        `;
        	        $(".updata").html(more);
        		}
        		if(e.target.getAttribute("id") === "addsub"){
        			if( $("#pw").val() == "" || $("#acc").val() == "" ){
        				alert("請填入資料")
        			}else{
        				$.ajax({
     	    		   url:"/CFA101G4/AdminServlet",
     	    		   data:{
     	    			   	action:"add",
     	    			   	admin_acc:$("#acc").val(),
     	    			   	admin_pw:$("#pw").val()
     	    		   },
     	    		   success:function(data){
     	    			   alert("成功新增囉");
     	    			   window.location.href = "/CFA101G4/back-end/Admin/MainAdmin.html"
     	    		   		}
        				})
        			}
     	       	}
        	})
        }
    });
 	
