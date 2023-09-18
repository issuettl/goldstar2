$(function(){
	//페이징
    $(".btn_ex").on({
        "click" : function(){
            var nickname = $("#nickname").val();
            
            if(nickname.length == 0){
                alert("닉네임을 입력해 주세요(한글 최대 5자)");
                $("#nickname").focus();
                return;
            }
            if(nickname.length > 5){
                alert("닉네임은 최대 5자입니다.");
                $("#nickname").focus();
                return;
            }
            
            if(!nickname.kor()){
                alert("닉네임은 표준 한글만 입력 가능합니다.");
                $("#nickname").focus();
                return;
            }
            
            var data = {nick:nickname};
            $.ajax({
                url: contextPath + "u/sign/name/action.do",
                data: JSON.stringify(data),
                method: "POST",
                contentType: "application/json",
                dataType: "json" // 서버에서 보내줄 데이터의 타입
            })
            .done(function(json) {
                console.log(json);
                if(json["result"] == "SUCCESS"){
                    document.location.href = contextPath + "u/exp/worry.do";
                }
                else {
                    alert(json["reason"]);
                    if(json["code"] == 1001){
                        document.location.href = contextPath + "u/exp/worry.do";
                    }
                }
            })
            .fail(function(xhr, status, errorThrown) {
                alert(status);
            });
        }
    });
});
