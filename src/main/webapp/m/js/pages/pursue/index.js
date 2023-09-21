$(function(){
    
    $("select[name='questionStatus']").on({
        "change" : doStatus
    });
    
    $(".sortQuestions").on({
        "click" : doSort
    });
    
    $(".doUpdate").on({
        "click" : doUpdate
    });
});

var doUpdate = function(){
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/pursue/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#updateQuestion").val(json["entity"]["question"]);
            $("#updateAnswers").html('<h3>답변</h3>');
            if(json["entity"]["answers"]){
                json["entity"]["answers"].forEach(function(e, i){
                    $("#updateAnswers").append('<div class="row inputBox">');
                    $("#updateAnswers").append('<input type="text" name="updateAnswers" data-sn="' + e.sn + '" class="w100per line_input" placeholder="" value="' + e.name + '"/>');
                    $("#updateAnswers").append('<h4 style="font-weight:400;font-size:13px;">' + e.type1Title + '/' + e.type2Title + '</h4>');
                    $("#updateAnswers").append('</div><br>');
                });
            }
            $("#updateButton").unbind().on({
                "click" : function(){
                    doUpdateAction(json["entity"]["sn"]);
                }
            });
            
            $("#pop_survey_mod1").show();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doUpdateAction = function(sn){

    var data = {};
    data["sn"] = sn;
    var question = $("#updateQuestion");
    var answers = $("input[name='updateAnswers']");
    
    data["question"] = question.val();
    data["answers"] = new Array();
    
    var invalidObj = null;
    var invalidText = null;
    answers.each(function(i, e){
        if(!$(this).val()){invalidText="답변을 입력해주세요."; invalidObj = $(this);return false;}
        if($(this).val().length > 30){invalidText="답변은 30글자 이하입니다."; invalidObj = $(this);return false;}
        
        data["answers"][i] = {};
        data["answers"][i]["sn"] = $(this).data("sn");
        data["answers"][i]["name"] = $(this).val();
    });
    
    if(!data["question"]){alert("질문을 입력해주세요.");$("#updateQuestion").focus();return;}
    if(data["question"].length > 100){alert("질문은 100글자 이하입니다.");$("#updateQuestion").focus();return;}
    
    if(invalidObj){
        alert(invalidText);
        invalidObj.focus();
        return;
    }
    
    $.ajax({
        url: contextPath + "m/pursue/update.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            document.location.reload();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doSort = function(){
    var data = {};
    data["snList"] = new Array();
    $(".sortQuestion").each(function(){
        data["snList"].push($(this).data("sn"));
    });
    
    if(!confirm("순서변경 하시겠습니까?")){return;}
    $.ajax({
        url: contextPath + "m/pursue/sort.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            document.location.reload();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doStatus = function(){
    var data = {};
    data["sn"] = $(this).data("sn");
    data["status"] = $(this).val();

    $.ajax({
        url: contextPath + "m/pursue/status.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            alert("변경되었습니다.");
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};