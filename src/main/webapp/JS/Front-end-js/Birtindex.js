



$('.etime').each(function(){
	//拿到Class = etime 的 內文 也就是2021-XX-XX XX:XX:XX
    let timer_interval = $(this).text()
    //這邊由於之後要時刻更改這個裡面的時間 所以先存變數以利之後使用
    let thisone = $(this)
    //將上面timer_interval拿到的2021-XX-XX XX:XX:XX 改為 2021/XX/XX XX:XX:XX才能用js方法
    timer_interval = timer_interval.replace(new RegExp("-","gm"),"/");
    //拿到timer_interval的毫秒
    let etime = (new Date(timer_interval)).getTime();
//    拿到這個CLASS = etime 這個div所包含的ID名稱
    let timer_name = $(this).attr('id')
//   一個名為startTimer的方法需要有三個變數 (計時器名稱，截止時間，要更換的DIV是哪個)
    startTimer(timer_name, etime , thisone);
});
function startTimer(timer_name, etime , thisone) {
	//timer_name極為上方傳下來的動態變數
	timer_name = setInterval(function(){
//		拿到現在時間
		let nowTime = new Date().getTime()
//		截止時間-現在時間獲得剩餘時間的毫秒
		let time = etime - nowTime;
		//be方法會回傳看得懂的時間，然後放到thistone的內容裡面
		if( time < 0){
			thisone.text("狀態更改中，請重整頁面")
			return;
		}
		let abc = be(time)
		thisone.text(abc)
	}, 1000);
}
function be(mss) {
	var days = parseInt(mss / (1000 * 60 * 60 * 24));
	var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
	var seconds = Math.round((mss % (1000 * 60)) / 1000);
	return days + " 天 " + hours + " 小時 " + minutes + " 分 " + seconds + " 秒 ";
}
