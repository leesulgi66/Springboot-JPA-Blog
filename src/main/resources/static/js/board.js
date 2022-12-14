let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
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
            if(resp.status === 500 || data.title === '' || data.content === '') {
                alert("글쓰기에 실패하였습니다.");
            }else{
                alert("글쓰기가 완료되었습니다.");
                location.href = "/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteById: function() {
        var id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json"
        }).done(function(resp){
            if(resp.status === 500){
                alert("삭제가 실패하였습니다.");
            }else{
                alert("삭제가 완료되었습니다.");
                location.href = "/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update: function() {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            if(resp.status === 500 || data.title === '' || data.content === ''){
                alert("글 수정이 실패하였습니다.");
            }else{
                alert("글 수정이 완료되었습니다.");
                location.href = "/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replySave: function() {
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        };

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            if(resp.status === 500 || data.content === ''){
                alert("댓글작성이 실패하였습니다.");
            }else{
                alert("댓글작성이 완료되었습니다.");
                location.href = `/board/${data.boardId}`;
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replyDelete: function(boardId, replyId) {

        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType: "json"
        }).done(function(resp){
            if(resp.status === 500){
                alert("댓글 삭제가 실패하였습니다.");
            }else{
                alert("댓글 삭제가 완료되었습니다.");
                location.href = `/board/${boardId}`;
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
}

index.init();