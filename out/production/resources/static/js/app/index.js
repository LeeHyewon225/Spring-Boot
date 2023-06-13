/*
브라우저의 스코프는 공용공간. 따라서 같은 이름의 함수라면 나중에 로딩된 함수가 덮어쓰게 됨
해결 --> 해당 파일만의 유효범위 만들기
var main 란 객체를 만들어 해당 객체에서 필요한 모든 function 을 선언
이렇게 하면 main 객체 안에서만 function 이 유효하기 때문에 다른 js와 겹칠 위험이 사라짐
*/

var main = {
    init : function(){
        var _this = this;

        //btn-save 란 id를 가진 HTML 엘리먼트에 click 이벤트 발생 시 save()를 실행하도록 이벤트 등록
        $('#btn-save').on('click', function(){
            _this.save();
        });

        //btn-update 란 id를 가진 HTML 엘리먼트에 click 이벤트 발생 시 update()를 실행하도록 이벤트 등록
        $('#btn-update').on('click', function(){
            _this.update();
        })

        //btn-delete 란 id를 가진 HTML 엘리먼트에 click 이벤트 발생 시 delete()를 실행하도록 이벤트 등록
        $('#btn-delete').on('click', function(){
            _this.delete();
        })
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
    },
    update : function(){
        var data ={
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            /*
            여러 HTTP Method 중 PUT 메소드 선택
            api 에서 이미 @PutMapping 으로 선언했기 때문에 PUT 사용 필수

            REST 규약
            생성(Create) : POST
            읽기(Read) : Get
            수정(Update) : PUT
            삭제(Delete) : DELETE

            */
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href = "/";
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
    delete : function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};

main.init();