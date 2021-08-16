    $(function() {
    	
    	//文章檢舉帶入
    	$.ajax({
			type : "GET",
			url : "/CFA101G4/AdminReportList",
			dataType : 'json',
			data : {
				'action' : 'admingetAll'
			},
			async: false,
			cache : false,
			success : function(data) {
				let html = "";
				for(i=0;i<data.length;i++){
					html +=	
						`<tr>
				          <td class="pt-2">${data[i].memname}</td>
				          <td class="pt-2" value1="${data[i].attitle}" value2="${data[i].attext}" value="${data[i].repid}" id="dd">${data[i].text}</td>
				          <td class="pt-2">${data[i].reptime}</td>
				          <td class="pt-2" id="c">${data[i].status}</td>
				          <td id="cck" class="p-1">
				            <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				              <button type="button" class="btn btn-outline-success" value="${data[i].repid}" id="pass">PASS</button>
				              <button type="button" class="btn btn-outline-danger" value="${data[i].repid}" id="fail">FAIL</button>
				            </div>
				          </td>
				        </tr>`}
				$("#tbody").append(html)
				checkSTA();
			},
		})
		//留言檢舉帶入
			$.ajax({
			type : "POST",
			url : "/CFA101G4/MessageReportServlet",
			dataType : 'json',
			data : {
				'action' : 'admingetAll'
			},
			async: false,
			cache : false,
			success : function(data) {
				var sd = data.length
				let msghtml = "";
				for(g=0;g<data.length;g++){
					msghtml +=	
						`<tr>
				          <td class="pt-2">${data[g].memname}</td>
				          <td class="pt-2" value1="${data[g].msgtext}" value="${data[g].msrid}" id="ss">${data[g].msrtext}</td>
				          <td class="pt-2">${data[g].msrtime}</td>
				          <td class="pt-2" id="c">${data[g].msrsta}</td>
				          <td id="cck" class="p-1">
				            <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				              <button type="button" name="msg" class="btn btn-outline-success" value="${data[g].msrid}" id="pass">PASS</button>
				              <button type="button" name="msg" class="btn btn-outline-danger" value="${data[g].msrid}" id="fail">FAIL</button>
				            </div>
				          </td>
				        </tr>`}
				$("#tbody").append(msghtml)
				checkSTA();	
			},
		})
    	pages();
		totalpag();
		
})// 載入END
	//載入文章檢舉資訊
		$(document).on('click','#dd',function () {
			let rep = $(this).text()
			let title =	$(this).attr("value1")
			let text =$(this).attr("value2")
			let cont=`<span>被檢舉內容--文章</span><hr class="mb-1 mt-1"><p class="mb-1">文章標題：</p><p class="mb-1" style="text-indent:1em">${title}</p><hr class="mb-1 mt-1"><p style="text-indent:1em">${text}</p>`
			$("#reptext").html(rep)
			$("#repattext").html(cont)
			 $("#detailed").modal('show')
		})
    	//載入留言檢舉資訊
		$(document).on('click','#ss',function () {
			let rep = $(this).text()
			let msg =	$(this).attr("value1")
			let cont=`<span>被檢舉內容--留言</span><hr class="mb-1 mt-1"><p style="text-indent:1em">${msg}</p>`
			$("#reptext").html(rep)
			$("#repattext").html(cont)
			 $("#detailed").modal('show')
		})
    //總筆數顯示
    function totalpag() {
        var tot = $('tbody tr').length
  		let total = `<button type="button" class="btn btn-secondary" disabled>總共${tot}筆資料</button>`
        $("#total").html(total)
	}
    //關鍵字篩選
    $(document).ready(function(){
    	  $("#searchUSR").on("keyup", function() {
    	    let value = $(this).val().trim().toLowerCase();
    	    $("tbody tr").filter(function() {
    	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    	    }); 
    	    $("#checkfn").prop('selectedIndex', 0);
    	    if($(this).val().trim()==""){
    	    	$("#searchtotal").html("")
    	    	pages()
    	    }else{
    	    	changes();
    	    }
    	  });
    	});
  
    //清除分頁
    function changes() {
    	let a =0;
		$('tbody tr').each(function() {			
			if($(this).css("display")!="none"){
				a++;
			}
		})
		$("#pagination").html('');
		let total = `<button type="button" class="btn btn-secondary" disabled>搜尋${a}筆資料</button>`
        $("#searchtotal").html(total)
        
	}
    // 審核轉換
    function checkSTA() {
		$("td[id='c']").each(function() {
		let finish = `<button type="button" class="btn btn-light w-100" disabled>檢舉審核完畢</button>`
			let sta = $(this).text().trim();
		    if(sta==0){
		    	$(this).text("待審核")
		    }
		    if(sta==1){
		    	$(this).text("審核已通過").css('color','green')
		    	$(this).next().html(finish)
		    }
		    if(sta==2){
		    	$(this).text("審核未通過").css('color','red')
		    	$(this).next().html(finish)
		    }
		})	
    }

    // 審核通過按鈕
    $(document).on('click','#pass',function () {
      let check = $(this).parent().parent().parent().find('td#c');
      let pass =  $(this).parent();
      let repid = $(this).val();
      let msgrep =$(this).attr("name")
   		Swal.fire({
		  title: '確定審核?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: '審核送出',
		  cancelButtonText:'取消'
		}).then((result) => {
		  if (result.isConfirmed) {
			 //判斷是留言檢舉或是文章檢舉
			  if(msgrep=="msg"){			  
					$.ajax({
						type : "post",
						url : "/CFA101G4/MessageReportServlet",
						dataType : 'json',
						data : {
							'action' : 'PASS',
							'repid' : repid
						},
						cache : false,
						success : function(data) {
							if (data == true) {
							      check.text("審核已通過").css('color','green')
							      let finish = `<button type="button" class="btn btn-light w-100" disabled>檢舉審核完畢</button>`
							      pass.html(finish)	
							}else{
								alert("審核失敗")
							}
						},
					})
		    	 }else{	    	 
				$.ajax({
					type : "GET",
					url : "/CFA101G4/AdminReportList",
					dataType : 'json',
					data : {
						'action' : 'PASS',
						'repid' : repid
					},
					cache : false,
					success : function(data) {
						if (data == true) {
						      check.text("審核已通過").css('color','green')
						      let finish = `<button type="button" class="btn btn-light w-100" disabled>檢舉審核完畢</button>`
						      pass.html(finish)
				   			
						}else{
							alert("審核失敗")
						}
					},
				})
		      } 
		  	}
		})
    })
    // 審核未通過按鈕
   $(document).on('click','#fail',function () {
	   let check = $(this).parent().parent().parent().find('td#c');
	   let fail = $(this).parent();
	   let repid = $(this).val();
	   let msgrep =$(this).attr("name")
	     	Swal.fire({
    		  title: '確定審核?',
    		  icon: 'warning',
    		  showCancelButton: true,
    		  confirmButtonColor: '#3085d6',
    		  cancelButtonColor: '#d33',
    		  confirmButtonText: '審核送出',
    		  cancelButtonText:'取消'
    		}).then((result) => {
    		  if (result.isConfirmed) {
    			  if(msgrep=="msg"){
  					$.ajax({
						type : "post",
						url : "/CFA101G4/MessageReportServlet",
						dataType : 'json',
						data : {
							'action' : 'REJECT',
							'repid' : repid
						},
						cache : false,
						success : function(data) {
							if (data == true) {
								check.text("審核未通過").css('color','red')
					   			let finish = `<button type="button" class="btn btn-light w-100" disabled>檢舉審核完畢</button>`
					   			fail.html(finish)
							}else{
								alert("審核失敗")
							}
						},
					})
    			  }else{
					$.ajax({
						type : "GET",
						url : "/CFA101G4/AdminReportList",
						dataType : 'json',
						data : {
							'action' : 'REJECT',
							'repid' : repid
						},
						cache : false,
						success : function(data) {
							if (data == true) {
								check.text("審核未通過").css('color','red')
					   			let finish = `<button type="button" class="btn btn-light w-100" disabled>檢舉審核完畢</button>`
					   			fail.html(finish)
					   			
							}else{
								alert("審核失敗")
							}
						},
					})
    			  }
    		  	}
    		})
   		})
      
    //下拉選單
    $("#checkfn").change(function () {
      let sta =$(this).val()
      $("tbody tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(sta) > -1)
	    }); 
      $("#searchUSR").val('')
      if(sta==0){
    	  $("#searchtotal").html("")
    	  pages()
    	  }else{
    	  changes()
    	  }
    })
    // 簡易分頁 待更改
    	function pages() {
      $("#pagination").html('');
      var trnum =0;
      var maxRows = 10;
      var totalRows = $('tbody tr').length
      $('tr:gt(0)').each(function(){
        trnum++;
        if(trnum > maxRows){
          $(this).hide()
        }
        if(trnum <= maxRows){
          $(this).show()
        }
      })
      if(totalRows > maxRows){
    	let pagenum = Math.ceil(totalRows/maxRows)
        for(let i=1; i<=pagenum;){
          $("#pagination").append(
          '<li class="page-item" data-page="'+i+'">\<button type="button" class="btn btn-secondary ms-2 mb-0"><span>'+ i++ +'</span></button>\<li>').show()
        }
      }
      $('#pagination li:first-child').addClass('active')
      $('#pagination li').on('click', function(){
        let nowpage = $(this).text()
        let nowhtml = `第${nowpage}頁`
        $("#nowpage").html(nowhtml)
        let pageNum = $(this).attr('data-page')
        let trIndex = 0;
        $('#pagination li').removeClass('active')
        $(this).addClass('active')
        $('tr:gt(0)').each(function(){
          trIndex++;
          if(trIndex > (maxRows*pageNum) || trIndex <= ((maxRows*pageNum)-maxRows)){
            $(this).hide()
          }else{
            $(this).show()
          }
        })
      })
    	}
