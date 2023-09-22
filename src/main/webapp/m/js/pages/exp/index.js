$(function(){
    
    $(".btn_input_add").on({
        "click" : doSave
    });
    
    $(".btn_sort").on({
        "click" : doSort
    });
    
    $(".doRemove").on({
        "click" : doRemove
    });
    
    $(".doUpdate").on({
        "click" : doUpdate
    });
    
    $("#saveButton").on({
        "click" : doSaveAction
    });

    $("#switch").on({
        "change" : doActive
    });
});

var doActive = function(){
    var data = {};

    var isChecked = $("#switch").prop("checked");

    data["product"] = isChecked ? "Y" : "N";
    
    if(!confirm((isChecked ? "활성화" : "비활성화") + " 하시겠습니까?")){
        $("#switch").prop("checked", !isChecked);
        return;
    }
    $.ajax({
        url: contextPath + "m/exp/system.do",
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

var doUpdate = function(){
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/exp/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#updateName").val(json["entity"]["name"]);
            
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
    data["name"] = $("#updateName").val();
    
    if(!data["name"]){alert("제품명을 입력해주세요.");$("#updateName").focus();return;}
    if(data["name"].length > 50){alert("제품명은 50글자 이하입니다.");$("#updateName").focus();return;}
    
    var imageFile = $("#updateImage")[0].files[0];
    
    var formdata = new FormData();
    formdata.append("sn", sn);
    formdata.append("name", data["name"]);
    formdata.append("imageFile", imageFile);
    
    if(imageFile !== undefined){
        formdata.append("imageFile", imageFile);
    }
    $.ajax({
        url: contextPath + "m/exp/update.do",
        data: formdata,
        method: "POST",
        contentType: false,
        processData: false,
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
    var sn = $(this).data("sn");
    $("#removeButton").unbind().on({
        "click" : function(){
            doRemoveAction(sn);
        }
    });
    
    $("#dialog_delete").show();
};
var doRemoveAction = function(sn){

    var data = {};
    data["sn"] = sn;
    
    $.ajax({
        url: contextPath + "m/exp/remove.do",
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
    $(".sortAnswer").each(function(){
        data["snList"].push($(this).data("sn"));
    });
    
    if(!confirm("순서변경 하시겠습니까?")){return;}
    $.ajax({
        url: contextPath + "m/exp/sort.do",
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

var doSave = function(){
    $('#pop_survey_add').show().data("type", $(this).data("type"));
};
var doSaveAction = function(){
    
    var data = {};
    data["name"] = $("#saveName").val();
    
    if(!data["name"]){alert("제품명을 입력해주세요.");$("#saveName").focus();return;}
    if(data["name"].length > 50){alert("제품명은 50글자 이하입니다.");$("#saveName").focus();return;}
    
    var imageFile = $("#saveImage")[0].files[0];
    
    if(imageFile === undefined){alert("이미지를 선택해주세요.");return;}
    
    var formdata = new FormData();
    formdata.append("name", data["name"]);
    formdata.append("imageFile", imageFile);
    
    $.ajax({
        url: contextPath + "m/exp/save.do",
        data: formdata,
        method: "POST",
        contentType: false,
        processData: false,
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