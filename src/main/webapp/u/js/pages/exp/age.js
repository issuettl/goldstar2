$(function(){
    $("input[name='worry_radio1']").on({
        "click" : function(){
            $("input[name='worry_radio1']").prop("checked", false);
            $(this).prop("checked", true);
        }
    });
    
    //나이 저장
    $(".btn_round_m").on({
        "click" : function(){
            var type = $("input[name='worry_radio_age']:checked").val();
            
            if(!type){
                alert("나이를 선택해주세요.");
                return;
            }
            
            var data = {age:type};
            $.ajax({
                url: contextPath + "u/member/age/action.do",
                data: JSON.stringify(data),
                method: "POST",
                contentType: "application/json",
                dataType: "json"
            })
            .done(function(json) {
                console.log(json);
                if(json["result"] == "SUCCESS"){
                    document.location.href = contextPath + "u/exp/worry.do";
                }
                else {
                    alert(json["reason"]);
                }
            })
            .fail(function(xhr, status, errorThrown) {
                alert(status);
            });
        }
    });
});
