$("#delete1").on("click", function(e) {
    alert("OK");


    if ($(this.id).selector == "delete1") {
        let id = $(this).val()
        swal({
            title: '確定要刪除商品嗎？',
            text: '如果沒有人購買才可以刪除商品！',
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: '確定！',
            cancelButtonText: "取消",
        }).then(function(value) {
            if (value.value) {
                console.log("123")
                $.ajax({
                    url: "/CFA101G4/bid.do",
                    data: {
                        BP_ID: id,
                        action: "delete"
                    },
                    success: function(data) {
                        if (data == "true") {

                            swal(
                                "成功刪除商品",
                                "success"
                            )
                        }
                        else{
                            swal(
                            "刪除失敗",
                            "已經有人購買"
                            )
                        }
                    }
                })
            } else {
                swal('已取消操作！', '', 'error')
            }
        })
    }
})