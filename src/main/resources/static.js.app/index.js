/*
브라우저의 스코프는 공용공간. 따라서 같은 이름의 함수라면 나중에 로딩된 함수가 덮어쓰게 됨
해결 --> 해당 파일만의 유효범위 만들기
var main 란 객체를 만들어 해당 객체에서 필요한 모든 function 을 선언
이렇게 하면 main 객체 안에서만 function 이 유효하기 때문에 다른 js와 겹칠 위험이 사라짐
*/
var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
    },
    save : function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
main.init();