function init() {
	$("#updata").on("click",function(){
		var form = $('form')[0];
        var formData = new FormData(form);
        for (var i = 0, len = fileList.length; i < len; i++) {
            formData.append('piimage', fileList[i]);
        }
        formData.append("bp_bpcid", $("#bp_bpcid").val())
        formData.append("bp_id", $("#bp_id").val())
        formData.append("bp_name", $("#bp_name").val())
        formData.append("bp_stime" , $("#bp_stime").val())
        formData.append("bp_etime" , $("#bp_etime").val())
        formData.append("bp_sprice" , $("#bp_sprice").val())
        formData.append("bp_inc" , $("#bp_inc").val())
        formData.append("bp_imdt" , $("#bp_imdt").val())
        formData.append("bp_ship" , $("#bp_ship").val())
        formData.append("bp_desc" , $("#bp_desc").val())
        formData.append("action" , "updata")
		alert(formData)
		$.ajax({
			url: "/CFA101G4/bid.do",
			type:"post",
			async: true,
		    contentType: false,
		    processData: false,
			data:formData
		})
	})
	
	
	
	
	$("#upimg").on("click",function () {
        $("#i1").click();
    })
	
    let img = document.getElementById("i1")
    let d1 = document.getElementById("d1")
    let fileList = [];
    let curFile;

    // 當 input有改變時開始動作
    img.addEventListener("change", function (e) {
    	 //這個元素
        console.log(this)
        //
        curFile = this.files
        //
        console.log(curFile)
        //將類陣列或可迭代物件轉換成陣列
        console.log(Array.from(curFile))
        //concat 是用於串接兩個陣列，串接的結果會透過回傳值取得，並不會套用至原有的陣列
        fileList =fileList.concat(Array.from(curFile))
        console.log("變數組:"+fileList);
    	
    	
//     	document.getElementById("d1").innerHTML = "";
        // e = img 可由下方程式碼看出
        // console.log(e)
        //由上方程式法可以看出，下方只是將img.files傳入一個變數而已(為了方便)
        //不然其實都可以直接使用img.files;
        let files = img.files;
        for (let i = 0 ; i < img.files.length ; i++){
        if (files !== null) {
            //檔案回傳的都是一個 陣列[]
            let file = files[i];
            // 判斷file.type的型別是否包含'image'，若不是會回傳-1
            if (file.type.indexOf("image") > -1) {
                //此處為固定語法，代表加載FileReader()物件，瀏覽器才可以開始讀取
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    // e = reader 可由下方程式碼看出
                    //抓取讀到之檔案的result放入一個變數 (就是img src="這裡面要填的路徑")
                    //就等於下方可以簡略成img.src= e.target.result;
                    let result = e.target.result;
                    d1.innerHTML += `
                        <li class="imgbox"><img src="`+result +`"><sapn class="del">x</sapn></li>
                        `
                })
                //此動作是開始讀取的意思(一定要加，為固定語法)
                //Data URL 是一種特殊的 URL 格式，可直接將文件容編成一組 URL 字串
             reader.readAsDataURL(file) 
            
            } else {
                alert("不要亂傳");
            }
        }
        }
    })
     $("#d1").on("click", ".del" , function () {
                //
                console.log($(this))
                //拿到點擊的這個標籤的父元素
                console.log($(this).parent())
                test = $(this).parent()
                //可以看到這個元素的引索直 ，也就是說第幾個li ，引索值從0開始
                console.log(test.index())
                //詭異語法 splice， 第一個值為他的起點 ， 第二個值為要刪除的元素
                //這邊的話，因為li剛好跟我們的fileList[]陣列位置互相呼應到
                //所以就可以使用 splice 可以刪除我們不要的照片(Object)
                fileList.splice(test.index() , 1)
                //這邊就是將預覽圖片刪掉
                test.remove();
                // var filess = document.getElementById('file');
                // filess.value = '';
                console.log($(this))
                console.log($(this).parent())
                //可以看到被刪除過後filelist內剩下幾個
                console.log("變數組:"+fileList);
                filess = document.getElementById('i1');
                filess.value = '';
            })

}



window.onload = init;