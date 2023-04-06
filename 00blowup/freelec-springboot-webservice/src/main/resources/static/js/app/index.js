var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',   // POST 요청을
            url: '/api/v1/posts',    // 이 URL로 보낸다
            dataType: 'json',   // 데이터 타입은 JSON
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function() {    // 성공적으로 완료되었을 경우
            alert('글이 등록되었습니다.');   // 화면에 완료 알림을 띄우고
            window.location.href = '/'; // 시작 화면으로 이동
        }).fail(function(error) {   // 오류가 발생했을 경우
            alert(JSON.stringify(error));   //화면에 오류 알림을 띄움
        });
    }
}

main.init();