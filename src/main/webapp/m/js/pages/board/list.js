$(function(){
    $("#doSave").on({
        "click" : doSave
    });
    
    $(".doUpdate").on({
        "click" : doUpdate
    });
    
    $("#updateAction").on({
        "click" : doUpdateAction
    });
    
    $(".doDelete").on({
        "click" : doDelete
    });
    
    $("#deleteAction").on({
        "click" : doDeleteAction
    });
    
    //페이징
    $(".pagination").find("button").on({
        "click" : function(){
            var page = $(this).data("page");
            $("#searchForm > input[name='page']").val(page);
            $("#searchForm").submit();
        }
    });
    
    $("#updateCancel").on({
        "click" : function(){
            $("#pop_update").hide();
        }
    });
    
    $("#deleteCancel").on({
        "click" : function(){
            $("#pop_delete").hide();
        }
    });
    
    $("#doOrder").on({
        "click" : doOrder
    });
});

var doOrder = function(){
    
    var data = {};
    data["snList"] = new Array();
    $("li.orderSn").each(function(){
        data["snList"].push($(this).data("sn"));
    });
    
    if(!confirm("순서변경 하시겠습니까?")){return;}
    
    $.ajax({
        url: contextPath + "m/board/order/action.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            doSearch();
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
    data["bbsSn"] = $("#bbsSn").val();
    data["subject"] = $("#saveSubject").val();
    data["contents"] = $("#saveContents").val();
    
    if(!data["subject"]){alert("제목을 입력해주세요.");$("#saveSubject").focus();return;}
    if(data["subject"].length > 100){alert("제목은 100글자 이하입니다.");$("#saveSubject").focus();return;}
    
    if(!data["contents"]){alert("내용을 입력해주세요.");$("#saveContents").focus();return;}
    
    $.ajax({
        url: contextPath + "m/board/save/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            doSearch();
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
    data["bbsSn"] = $("#bbsSn").val();
    data["sn"] = $(this).data("sn");
    
    $.ajax({
        url: contextPath + "m/board/update.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#updateSubject").val(json["board"].subject);
            $("#updateContents").val(json["board"].contents);
            $("#updateAction").data("sn", json["board"].sn);
            
            $("#pop_update").show();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doUpdateAction = function(){
    var data = {};
    data["bbsSn"] = $("#bbsSn").val();
    data["sn"] = $(this).data("sn");
    data["subject"] = $("#updateSubject").val();
    data["contents"] = $("#updateContents").val();
    
    if(!data["subject"]){alert("제목을 입력해주세요.");$("#updateSubject").focus();return;}
    if(data["subject"].length > 100){alert("제목은 100글자 이하입니다.");$("#updateSubject").focus();return;}
    
    if(!data["contents"]){alert("내용을 입력해주세요.");$("#updateContents").focus();return;}
    
    if(!confirm("수정 하시겠습니까?")){return;}
    $.ajax({
        url: contextPath + "m/board/update/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            doSearch();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doDelete = function(){
    var data = {};
    data["bbsSn"] = $("#bbsSn").val();
    data["sn"] = $(this).data("sn");
    
    $.ajax({
        url: contextPath + "m/board/update.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            
            $("#deleteSubject").html(json["board"].subject);
            $("#deleteContents").html(json["board"].contentsBr);
            $("#deleteAction").data("sn", json["board"].sn);
            
            $("#pop_delete").show();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doDeleteAction = function(){
    if(!confirm("삭제 하시겠습니까?")){return;}
    var data = {};
    data["bbsSn"] = $("#bbsSn").val();
    data["sn"] = $(this).data("sn");
    
    $.ajax({
        url: contextPath + "m/board/remove/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            doSearch();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doSearch = function(){
    var f = $("#searchForm");
    f.submit();
};