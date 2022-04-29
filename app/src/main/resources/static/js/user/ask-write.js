var count = 0;
$(document).ready(function () {    //html문서가 다 로드된후에 자바스크립트가 자동으로 실행되는 함수

    init();

});

function init() {

    $("#btnWrite").on("click", function () {

        var askName = $('#askName').val();
        var askContent = $('#askContent').val();

        console.log("askName =" + askName);
        console.log("askContent = " + askContent);
        /*
        var param = new URLSearchParams(); // 파라미터를 가지고 가기위해 객체생성을 해준것

        param.set('memberTypeNo', member_type_no); //meberTypeNo 도메인에 정의되있는 변수명으로 맵핑을해준다 why? 도메인롬북이 읽게하기위해
        param.set('name', name);
        param.set('criticalCheck', critical_check);
        param.set('content', content);
        */
        var fd = new FormData(document.forms.namedItem("frm"));

        fetch("/ask/user-add", { // 컨트롤러고 가기위한 경로
            method: "POST",
            body: fd         // 파라미터 객체를 세팅해준다. 커트롤러로 고고!!
        }).then(function (response) {
            return response.json();
        })
            .then(function (result) { //긴 여행을 거쳐 컨트롤러에서 다시넘어온 결과값이다.
                if (result.status == "success") {
                    location.href = "ask-list.html";
                } else {
                    window.alert("게시글 등록 실패!");
                    console.log(result.data);
                }

            });

    });
    $("#btnCancel").on("click", function () {
        location.href = 'ask-list.html'
    })

}




