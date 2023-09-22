$(function(){
    
    $("#saveAnswer").on({
        "click" : doSave
    });
    
    $("select[name='questionStatus']").on({
        "change" : doStatus
    });
    
    $(".sortQuestions").on({
        "click" : doSort
    });
    
    $(".doRemove").on({
        "click" : doRemove
    });
    
    $(".doUpdate").on({
        "click" : doUpdate
    });
    
    $(".addAnswer").on({
        "click" : doAdd
    });

	$("#switch").on({
        "change" : doActive
    });
});

var doActive = function(){
    var data = {};

	var isChecked = $("#switch").prop("checked");

	data["survey"] = isChecked ? "Y" : "N";
    
    if(!confirm((isChecked ? "활성화" : "비활성화") + " 하시겠습니까?")){
        $("#switch").prop("checked", !isChecked);
        return;
    }
    
    $.ajax({
        url: contextPath + "m/survey/system.do",
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

var doAdd = function(){
	$("#saveAnswers > span").append('<input type="text" name="eachAnswers" placeholder="답변을 입력해 주세요" />');
};

var doUpdate = function(){
    var type = $(this).data("type");
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/survey/get.do",
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
                     $("#updateAnswers").append('<div class="row inputBox"><input type="text" name="updateAnswers" data-sn="' + e.sn + '" class="w100per line_input" placeholder="" value="' + e.name + '" /></div>');
                });
            }
            $("#updateButton").unbind().on({
                "click" : function(){
                    doUpdateAction(json["entity"]["sn"], type);
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
var doUpdateAction = function(sn, type){

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
        url: contextPath + "m/survey/update.do",
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
var doRemove = function(){
    var type = $(this).data("type");
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/survey/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#removeQuestion").html(json["entity"]["question"]);
            $("#removeAnswers").html('<h3 class="d_inline_block">답변</h3>')
               if(json["entity"]["answers"]){
                  json["entity"]["answers"].forEach(function(e, i){
                      if(i==0){
                        $("#removeAnswers").append('<span class="pop_span">' + e.name + '</span>');
                      }
                      else {
                        $("#removeAnswers").append('<span class="pop_div" style="line-height: 40px;margin-left: 55px;">' + e.name + '</span>');
                      }
                  });
               }
                $("#removeButton").unbind().on({
                    "click" : function(){
                        doRemoveAction(json["entity"]["sn"], type);
                    }
                });
            
            $("#pop_survey_del1").show();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doRemoveAction = function(sn, type){

    var data = {};
    data["sn"] = sn;

    $.ajax({
        url: contextPath + "m/survey/remove.do",
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
        url: contextPath + "m/survey/sort.do",
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
        url: contextPath + "m/survey/status.do",
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

var doSave = function(){
    
    var question = $("#saveQuestion");
    var answers = $("input[name='eachAnswers']");
    
    var data = {};
    data["question"] = question.val();
    data["answers"] = new Array();
    
    var invalidObj = null;
    var invalidText = null;
    answers.each(function(i, e){
        if(!$(this).val()){invalidText="답변을 입력해주세요."; invalidObj = $(this);return false;}
        if($(this).val().length > 30){invalidText="답변은 30글자 이하입니다."; invalidObj = $(this);return false;}
        
        data["answers"][i] = {};
        data["answers"][i]["name"] = $(this).val();
    });
    
    if(!data["question"]){alert("질문을 입력해주세요.");$("#saveQuestion").focus();return;}
    if(data["question"].length > 100){alert("질문은 100글자 이하입니다.");$("#saveQuestion").focus();return;}
    
    if(invalidObj){
        alert(invalidText);
        invalidObj.focus();
        return;
    }
    
    $.ajax({
        url: contextPath + "m/survey/save.do",
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