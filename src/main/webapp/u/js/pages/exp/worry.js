$(function(){

});

function pursueNext(index){
    var prev = index;
    var next = index + 1;
    toggleSlide('.exper_Q' + prev, '.exper_Q' + next);
};
function pursuePrev(index){
    var prev = index;
    var next = index + 1;
    toggleSlide('.exper_Q' + next, '.exper_Q' + prev);
};
function pursueSave(){

    var names = $("ul.worry_list_2023");
    var isSuccessAnswers = true;
    var data = new Array();
    names.each(function(){
        var name = $(this).data("name");
        
        if(isSuccessAnswers && $("input[name='" + name + "']:checked").length == 0){
            alert("모든 문답을 진행해주세요.");
            isSuccessAnswers = false;
        }
        
        data.push($("input[name='" + name + "']:checked").val());
    });
    
    if(!isSuccessAnswers){
        return;
    }
    
    $.ajax({
        url: contextPath + "u/pursue/answer/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            document.location.href = contextPath + "u/exp/corner.do";
        }
        else {
            alert(json["reason"]);
            if(json["code"] && json.code == 9999){
                document.location.href = contextPath + "sso/sign/in.do?state=/u/exp/worry.do";
            }
        }
        
        isSendData = true;
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
        
        isSendData = true;
    });
};
