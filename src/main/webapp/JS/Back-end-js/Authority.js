 	$.ajax({
			url:"/CFA101G4/AuthorityServlet",
			dataType:"json",
			data:{action:"getall"},
			success: function (data) {
            let html = "";
            for (let i = 0; i < data.length; i++) {
                html += `
                    <tr>
                		<th>${data[i].aut_id}</th>
                		<th>${data[i].aut_name}</th>
                		<th>${data[i].aut_con}</th>
                		<th id="more">修改</th>
                	</tr>
                    `;
            }
            $("#table").append(html);
// ajax寫法，利用冒泡事件抓取動態生成的元素 此處為抓取"修改"的按鈕
        	$("#main").on("click",function(e){
        		if (e.target.getAttribute("id") === "more"){
        			let num = e.target.parentElement.children[0].textContent - 1;
        			html = `<div class="upbody" id="upbody"><div class="updata" id="updata"></div></div>`;
		            document.getElementById("main").innerHTML += html
//		            ${data[num].admin_sta}
		            more = `
		                <table id="moretable">
		                <caption>權限資料</caption>
		                <tr><th>管理員編號:</th><td id="updataid">${data[num].aut_id}</td></tr>
		                <tr><th>管理員帳號</th><td><input type="text" id="updataacc" value="${data[num].aut_name}"></td></tr></td></tr>
		                <tr><th>管理員密碼</th><td><input type="text" id="updatapw" value="${data[num].aut_con}"></td></tr></td></tr>
		                <button type="submit" id="sub">送出</button><button type="submit" id="cansel">取消</button>
		                `;
//		            <select id="" value=""><option>${data[num].admin_sta}</option><option>1</option></select>
		           let i =  JSON.parse(data[num].admin_sta)
		            $(".updata").html(more);
		            (i === 0 ) ? $("option:eq(0)").attr("selected",true) : $("option:eq(1)").attr("selected",true)
        		}
        		if(e.target.getAttribute("id") === "sub"){
        			if($("#updataacc").val() === "" || $("#updatapw").val() === ""){
        				alert("請輸入正確的格式");
        			}
        			else{
        				$.ajax({
        					url:"/CFA101G4/AuthorityServlet",
        					data:{
        						action:"updata",
        						admin_id:$("#updataid").text(),
        						admin_acc:$("#updataacc").val(),
        						admin_pw:$("#updatapw").val(),
        						admin_sta:$("#updatasta").val()
        					},
        					success:function(data){
        						alert("修改成功囉！");
          	    			   window.location.href = "/CFA101G4/Back-end/Admin/mainAdmin.html"
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
     	    		   url:"/CFA101G4/AUTHORITY_SERVLET",
     	    		   data:{
     	    			   	action:"add",
     	    			   	admin_acc:$("#acc").val(),
     	    			   	admin_pw:$("#pw").val()
     	    		   },
     	    		   success:function(data){
     	    			   alert("成功新增囉");
     	    			   window.location.href = "/CFA101G4/Back-end/Admin/mainAdmin.html"
     	    		   		}
        				})
        			}
     	       	}
        	})
        }
    });
 	
