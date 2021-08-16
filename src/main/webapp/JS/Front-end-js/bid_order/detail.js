


$("#bpd_bpid").on("click", function(){
	let id = $(this).val();
	$.ajax({
		url:"/CFA101G4/BpDetail",
		datatype:"json",
		type:"post",
		data : {
			action:"insert",
			bpd_bpprice : $("#bpd_bpprice").val(),
			bpd_bpid : $(this).val()
		},
		 success:function(data){
			 console.log(data)
			 if(data === "login"){
				 swal(
						 "請先登入會員",
						 data,
						 "error"
				 ).then(function(value) {
				        if (value.value) {
				        	location.href = "/CFA101G4/front-end/login.jsp"
								 return 
				        }
					})
			 } 
			 else if(data != "OK" ){
				 swal(
						 "出價失敗請重新嘗試",
						 data,
						 "error"
				 )	 
			 }else{
				 swal({
				        title: '成功出價',
				        text: '您已成功出價！',
				        type: 'success',
				        confirmButtonColor: '#5895BF',
				        confirmButtonText: '確定！',
				    }).then(function(value) {
				        if (value.value) {
							 location.href = "/CFA101G4/bid.do?action=getOne_For_Display&bp_id="+id
				        }
					})
			 }
		 }
	})
})














$("#getbp_detail").on("click",function(){
		var formData = new FormData();
		formData.append("action" , "getbp_detail")
		formData.append("bp_id", $(bpd_bpid).val())
		console.log(formData)
		$.ajax({
			url:"/CFA101G4/BpDetail" ,
			type:"post",
			async:true,
			contentType:false,
			processData:false,
			data:formData,
			dataType:"json",
			success:function(data){
				html=`<thead>
						    <tr>
						      <th scope="col">競標商品編號</th>
						      <th scope="col">競標者ID</th>
						      <th scope="col">競標者出價金額</th>
						      <th scope="col">出價時間</th>
						    </tr>
						  </thead>
						  `
                for(let i = 0;i < data.length;i++){
                     html += `
                    
                    <tbody>
                    	<tr>
                    	<td>${data[i].bpd_bpid}</td>
                        <td>${data[i].bpd_bmemid}</td>
                        <td>${data[i].bpd_bpprice}</td>
                        <td>${data[i].bpd_bpdate}</td>
					    </tr>
                    </tbody>
                    `;
                }
                $("#view_detail").html(html);    
			}
		})
	})
