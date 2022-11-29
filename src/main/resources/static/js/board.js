let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },

    save: function() {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            if(resp.status === 500){
                alert("글쓰기에 실패하였습니다.");
            }else{
                alert("글쓰기가 완료되었습니다.");
                location.href = "/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
}

index.init();