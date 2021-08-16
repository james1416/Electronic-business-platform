	
		document.getElementById("submit").addEventListener("click",function() {
			window.opener.location.reload();
		})
		document.getElementById("cancel").addEventListener("click",function() {
			window.opener.location.reload();
			window.close();
		})
