$(function(){
    $("input[name='worry_radio1']").on({
        "click" : function(){
            $("input[name='worry_radio1']").prop("checked", false);
            $(this).prop("checked", true);
        }
    });
    
    //코너 추천받기
    $(".btn_round_m").on({
        "click" : function(){
            var type = $("input[name='worry_radio1']:checked").val();
            
            if(!type){
                alert("코너를 선택해주세요.");
                return;
            }
            
            var data = {worryType:type};
            $.ajax({
                url: contextPath + "u/sign/worry/action.do",
                data: JSON.stringify(data),
                method: "POST",
                contentType: "application/json",
                dataType: "json"
            })
            .done(function(json) {
                console.log(json);
                if(json["result"] == "SUCCESS"){
                    document.location.href = contextPath + "u/exp/corner.do";
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
