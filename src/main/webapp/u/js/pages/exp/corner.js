$(function(){
});

async function importPage2(target, position) {
    $('#'+ position+'_'+target).html( await fetchHtmlAsText(contextPath + 'u/' + position + '/' + target + '.do'));
    $('.dim').click(function() {
        $(this).parent('.modal').css('display','none');
        $('html').css({'overflow':'unset'});
        $('html').css({'overflow-y':'scroll'});
        $('html').css({'position':'relative'});
        $('body').css({'padding-right':'0px'});
    });
    
    //답안 라디오
    $("." + position + "Questions").each(function(e){
        $("input[name='" + $(this).data("answers") + "']").on({
            "click" : function(){
                $("input[name='" + $(this).data("answers") + "']").prop("checked", false);
                $(this).prop("checked", true);
            }
        });
    });
}

async function importPage3(target, position) {
    $('#'+ target).html( await fetchHtmlAsText(contextPath + 'u/' + position + '.do'));
    $('.dim').click(function() {
        $(this).parent('.modal').css('display','none');
        $('html').css({'overflow':'unset'});
        $('html').css({'overflow-y':'scroll'});
        $('html').css({'position':'relative'});
        $('body').css({'padding-right':'0px'});
    });
}

function agreement(targetId, obj) {

    if($(obj).hasClass("disabled_complete") || $(obj).hasClass("q_complete") || $(obj).hasClass("disabled_booking")){
        return;
    }

    if($("#agreement_show").val() == "Y"){
    
        $("#agreeCheck0").prop("checked", false);
        $("#agreeCheck1").prop("checked", false);
        $("#agreeCheck2").prop("checked", false);
    
        popupOpen2("pop_booking2");
        
        $("#btn_agree_ok").attr("onclick", "javascript:agreementcheck('" + targetId + "');");
    }
    
    else {
        popupOpen2(targetId);
    }
}

function agreementcheck(targetId) {

    if($("#agreeCheck1").prop("checked") && $("#agreeCheck2").prop("checked")){
        $('.pop_store').fadeOut('.pop_store');
        popupOpen2(targetId);
    }
}

/* 팝업 */
function popupOpen2(targetId) {
    
    $('#'+targetId).find($(".questionZone")).show();
    $('#'+targetId).find($("." + targetId + "s")).hide();
    $('#'+targetId).find($(".questionResult")).hide();
    $('#'+targetId).find($("." + targetId + "s")).first().show();


    $('html').css({'overflow':'hidden'});
    $('html').css({'position':'fixed'}); // 2022-11-07 모바일 팝업 고정
    $('#'+targetId).show();
};

var isSendData = true;
var showQuestion = function(target, position){
    if($("#" + target).length){
        $("." + position + "Questions").hide();
        $("#" + target).show();
        return;
    }
    
    var isSuccessAnswers = true;
    var data = new Array();
    $("." + position + "Questions").each(function(e, i){
    
        if($("input[name='" + $(this).data("answers") + "']:checked").length == 0){
            isSuccessAnswers = false;
        }
        
        //data.push({"id":{"answerSn":$("input[name='" + $(this).data("answers") + "']:checked").val()}});
        data.push($("input[name='" + $(this).data("answers") + "']:checked").val());
        
    });
    
    if(!isSuccessAnswers){
        alert("모든 문답을 진행해주세요.");
        $("." + position + "Questions").hide();
        $("#" + position + "Questions1").show();
        return;
    }
    
    if(!isSendData){
        return;
    }
    
    isSendData = false;
    $.ajax({
        url: contextPath + "u/" + position + "/answer/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("." + position + "Questions").hide();
            $("#" + position + "ResultTitle").html(json["title"]);
            $("#" + position + "ResultRecommend").html(json["recommend"]);
            $("#" + position + "ResultTodo").html(json["todo"]);
            if($("#" + position + "ResultImage").length){
                $("#" + position + "ResultImage").attr("src", $("#" + position + "ResultImage").attr("src").replace("mind_result_11.png", json["image"]));
            }
            $("#" + position + "QuestionZone").hide();
            $("#" + position + "Result").show();
        }
        else {
            alert(json["reason"]);
            popMindClose();
            if(json["code"] && json.code == 9999){
                document.location.href = contextPath + "sso/sign/in.do?state=/u/exp/corner.do";
            }
        }
        
        isSendData = true;
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
        
        isSendData = true;
    });
};
