var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
        $('#btn-update').on('click', function() {
            _this.update();
        })
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
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    }
}

main.init();