$(function(){
    
    $("#saveEvent").on({
        "click" : doSave
    });
    
    $(".doRemove").on({
        "click" : doRemove
    });
    
    $(".doUpdate").on({
        "click" : doUpdate
    });
});

var doUpdate = function(){
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/event/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#updateSubject").val(json["event"]["subject"]);
            
            $("#updateStartDate").val(json["event"]["startDate"].substring(0,4)+"-"+json["event"]["startDate"].substring(4,6)+"-"+json["event"]["startDate"].substring(6,8));
            $("#updateEndDate").val(json["event"]["endDate"].substring(0,4)+"-"+json["event"]["endDate"].substring(4,6)+"-"+json["event"]["endDate"].substring(6,8));
            $("#updateContents").val(json["event"]["contents"]);
            
            $("#updateButton").unbind().on({
                "click" : function(){
                    doUpdateAction(json["event"]["sn"]);
                }
            });
            
            $("#pop_event_mod").show();
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
    data["subject"] = $("#updateSubject").val();
    data["startDate"] = $("#updateStartDate").val().replaceAll("-","");
    data["endDate"] = $("#updateEndDate").val().replaceAll("-","");
    data["contents"] = $("#updateContents").val();
    
    if(!data["subject"]){alert("제목을 입력해주세요.");$("#updateSubject").focus();return;}
    if(data["subject"].length > 50){alert("제목은 50글자 이하입니다.");$("#updateSubject").focus();return;}
    
    if(!data["startDate"]){alert("시작일을 입력해주세요.");$("#updateStartDate").focus();return;}
    if(!data["endDate"]){alert("종료일을 입력해주세요.");$("#updateEndDate").focus();return;}
    
    var sDate = parseInt(data["startDate"], 10);
    var eDate = parseInt(data["endDate"], 10);
    if(sDate > eDate){
        alert("시작일은 종료일보다 늦을 수 없습니다.");
        return;
    }
    
    var thumbFile = $("#updateThubmFile")[0].files[0];
    var pcViewFile = $("#updatePcViewFile")[0].files[0];
    var moViewFile = $("#updateMoViewFile")[0].files[0];
    
    var formdata = new FormData();
    
    if(thumbFile !== undefined){
        formdata.append("thumbFile", thumbFile);
    }
    if(pcViewFile === undefined){
        formdata.append("pcViewFile", pcViewFile);
    }
    if(moViewFile === undefined){
        formdata.append("moViewFile", moViewFile);
    }
    
    formdata.append("sn", sn);
    formdata.append("subject", data["subject"]);
    formdata.append("startDate", data["startDate"]);
    formdata.append("endDate", data["endDate"]);
    formdata.append("contents", data["contents"]);
    
    $.ajax({
        url: contextPath + "m/event/update/action.do",
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
    var data = {};
    data["sn"] = $(this).data("sn");

    $.ajax({
        url: contextPath + "m/event/get.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        console.log(json);
        if(json["result"] == "SUCCESS"){
            $("#removeSubject").val(json["event"]["subject"]);
            $("#removeStartDate").val(json["event"]["startDate"].substring(0,4)+"-"+json["event"]["startDate"].substring(4,6)+"-"+json["event"]["startDate"].substring(6,8));
            $("#removeEndDate").val(json["event"]["endDate"].substring(0,4)+"-"+json["event"]["endDate"].substring(4,6)+"-"+json["event"]["endDate"].substring(6,8));
            $("#removeContents").val(json["event"]["contents"]);
            
            $("#removeButton").unbind().on({
                "click" : function(){
                    doRemoveAction(json["event"]["sn"]);
                }
            });
            
            $("#pop_event_del").show();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doRemoveAction = function(sn){

    var data = {};
    data["sn"] = sn;

    $.ajax({
        url: contextPath + "m/event/remove/action.do",
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

var doSave = function(){

    var data = {};
    data["subject"] = $("#saveSubject").val();
    data["startDate"] = $("#saveStartDate").val().replaceAll("-","");
    data["endDate"] = $("#saveEndDate").val().replaceAll("-","");
    data["contents"] = $("#saveContents").val();
    
    if(!data["subject"]){alert("제목을 입력해주세요.");$("#saveSubject").focus();return;}
    if(data["subject"].length > 50){alert("제목은 50글자 이하입니다.");$("#saveSubject").focus();return;}
    
    if(!data["startDate"]){alert("시작일을 입력해주세요.");$("#saveStartDate").focus();return;}
    if(!data["endDate"]){alert("종료일을 입력해주세요.");$("#saveEndDate").focus();return;}
    
    var sDate = parseInt(data["startDate"], 10);
    var eDate = parseInt(data["endDate"], 10);
    if(sDate > eDate){
        alert("시작일은 종료일보다 늦을 수 없습니다.");
        return;
    }
    
    var thumbFile = $("#saveThubmFile")[0].files[0];
    var pcViewFile = $("#savePcViewFile")[0].files[0];
    var moViewFile = $("#saveMoViewFile")[0].files[0];
    
    if(thumbFile === undefined){alert("썸네일을 선택해주세요.");return;}
    if(pcViewFile === undefined){alert("PC상세페이지를 선택해주세요.");return;}
    if(moViewFile === undefined){alert("MO상세페이지를 선택해주세요.");return;}
    
    var formdata = new FormData();
    formdata.append("subject", data["subject"]);
    formdata.append("startDate", data["startDate"]);
    formdata.append("endDate", data["endDate"]);
    formdata.append("contents", data["contents"]);
    formdata.append("thumbFile", thumbFile);
    formdata.append("pcViewFile", pcViewFile);
    formdata.append("moViewFile", moViewFile);
    
    $.ajax({
        url: contextPath + "m/event/save/action.do",
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