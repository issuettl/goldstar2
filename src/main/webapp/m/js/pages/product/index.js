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
});

var doUpdate = function(){
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/product/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#updateName").val(json["entity"]["name"]);
            $("#updateSubject").val(json["entity"]["subject"]);
            $("#updateContents").val(json["entity"]["contents"]);
            $("#updateUrl").val(json["entity"]["url"]);
            
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
    data["subject"] = $("#updateSubject").val();
    data["contents"] = $("#updateContents").val();
    data["url"] = $("#updateUrl").val();
    
    if(!data["name"]){alert("제품명을 입력해주세요.");$("#updateName").focus();return;}
    if(!data["subject"]){alert("제품 상세명을 입력해주세요.");$("#updateSubject").focus();return;}
    if(data["name"].length > 50){alert("제품명은 50글자 이하입니다.");$("#updateName").focus();return;}
    if(data["subject"].length > 50){alert("제품 상세명은 50글자 이하입니다.");$("#updateSubject").focus();return;}
    
    if(!data["contents"]){alert("상세 설명을 입력해주세요.");$("#updateContents").focus();return;}
    if(!data["url"]){alert("URL을 입력해주세요.");$("#updateUrl").focus();return;}
    
    var listFile = $("#updateImageList")[0].files[0];
    var viewFile = $("#updateImageView")[0].files[0];
    
    var formdata = new FormData();
    formdata.append("sn", sn);
    formdata.append("name", data["name"]);
    formdata.append("subject", data["subject"]);
    formdata.append("contents", data["contents"]);
    formdata.append("url", data["url"]);
    formdata.append("listFile", listFile);
    formdata.append("viewFile", viewFile);
    
    if(listFile !== undefined){
        formdata.append("listFile", listFile);
    }
    if(viewFile === undefined){
        formdata.append("viewFile", viewFile);
    }
    $.ajax({
        url: contextPath + "m/product/update.do",
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
    
    alert(sn);

    $.ajax({
        url: contextPath + "m/product/remove.do",
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

    var type = $(this).data("type");

    var data = {};
    data["snList"] = new Array();
    $(".sortProduct." + type).each(function(){
        data["snList"].push($(this).data("sn"));
    });
    
    if(!confirm("순서변경 하시겠습니까?")){return;}
    $.ajax({
        url: contextPath + "m/product/sort.do",
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
    data["subject"] = $("#saveSubject").val();
    data["contents"] = $("#saveContents").val();
    data["url"] = $("#saveUrl").val();
    
    if(!data["name"]){alert("제품명을 입력해주세요.");$("#saveName").focus();return;}
    if(!data["subject"]){alert("제품 상세명을 입력해주세요.");$("#saveSubject").focus();return;}
    if(data["name"].length > 50){alert("제품명은 50글자 이하입니다.");$("#saveSubject").focus();return;}
    if(data["subject"].length > 50){alert("제품 상세명은 50글자 이하입니다.");$("#saveSubject").focus();return;}
    
    if(!data["contents"]){alert("상세 설명을 입력해주세요.");$("#saveContents").focus();return;}
    if(!data["url"]){alert("URL을 입력해주세요.");$("#saveUrl").focus();return;}
    
    var listFile = $("#saveImageList")[0].files[0];
    var viewFile = $("#saveImageView")[0].files[0];
    
    if(listFile === undefined){alert("목록 이미지를 선택해주세요.");return;}
    if(viewFile === undefined){alert("상세 이미지를 선택해주세요.");return;}
    
    var formdata = new FormData();
    formdata.append("name", data["name"]);
    formdata.append("subject", data["subject"]);
    formdata.append("contents", data["contents"]);
    formdata.append("url", data["url"]);
    formdata.append("type", $('#pop_survey_add').data("type"));
    formdata.append("listFile", listFile);
    formdata.append("viewFile", viewFile);
    
    $.ajax({
        url: contextPath + "m/product/save.do",
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