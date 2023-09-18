$(function(){
    $("#statusAll").on({
        "click" : function(){
            $("." + $(this).attr("id")).prop("checked", $(this).prop("checked"));
        }
    });
    
    //검색
    $("#click-search").on({
        "click" : doSearch
    });
    
    $(".doCancel").on({
        "click" : doCancelPop
    });
    $(".doStaffCheck").on({
        "change" : doStaffCheck
    });
    
    //페이징
    $(".pagination").find("button").on({
        "click" : function(){
            var page = $(this).data("page");
            $("#searchForm > input[name='page']").val(page);
            $("#searchForm").submit();
        }
    });
    $(".notDatesRemove").on({
        "click" : doNotDatesRemove
    });
    $("#notDatesAdd").on({
        "change" : doNotDatesAdd
    });
});

var doNotDatesAdd = function(){
    
    var date = $(this).val();
    var data = {};
    data["date"] = date.replaceAll("-","");
    $.ajax({
        url: contextPath + "m/life/weekday/date/add.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#notDatesAdd").val("");
            
            var html = '<div class="tag" id="notDatesRemove' + json["date"].sn + '"><span class="disable_number">';
                html += date;
                html += '</span><button class="tag_close notDatesRemove" data-sn="' + json["date"].sn + '"></button></div>';
            
            $(".adm_tag_wrap").append(html);
            $(".notDatesRemove").last().on({
                "click" : doNotDatesRemove
            });
            
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doNotDatesRemove = function(){
    var data = {};
    data["sn"] = $(this).data("sn");
    $.ajax({
        url: contextPath + "m/life/weekday/date/remove.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#notDatesRemove" + data["sn"]).remove();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doStaffCheck = function(){
    if(!confirm("변경하시겠습니까?")){
        if($(this).val() == "notyet"){
            $(this).val("present");
        }
        else {
            $(this).val("notyet");
        }
        return;
    }
    var data = {};
    data["sn"] = $(this).data("sn");
    data["staffCheck"] = $(this).val();
    
    $.ajax({
        url: contextPath + "m/life/weekday/staff.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#searchForm").submit();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var doCancelPop = function(){
    
    var sn = $(this).data("sn");
    $("#date0").html($("#date" + sn).html());
    $("#time0").html($("#time" + sn).html());
    $("#name0").html($("#name" + sn).html());
    $("#phone0").html($("#phone" + sn).html());
    $("#memberCount0").html($("#memberCount" + sn).html());

    $("#pop_escape_del").find(".doCancelAction").unbind().on({
        "click" : function(){
            doCancel(sn);
        }
    });

    $("#pop_escape_del").show();
    $("#escape_del_textarea").val($("#memo" + sn).html()).focus();
};
var doCancel = function(sn){
    if(!confirm("삭제하시겠습니까?")){return;}
    var data = {};
    data["sn"] = sn;
    data["memo"] = $("#escape_del_textarea").val();
    
    $.ajax({
        url: contextPath + "m/life/admin/cancel.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            $("#searchForm").submit();
            return;
        }
        alert(json["reason"]);
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
var doSearch = function(){
    var sDate = parseInt($("#dateStartSearch").val().replaceAll("-",""), 10);
    var eDate = parseInt($("#dateEndSearch").val().replaceAll("-",""), 10);
    if(sDate > eDate){
        alert("시작일은 종료일보다 늦을 수 없습니다.");
        return;
    }
    
    var f = $("#searchForm");
    f.find("[name='name']").val($("#nameSearch").val());
    f.find("[name='phone']").val($("#phoneSearch").val());
    
    f.find("[name='dateStartOrg']").val($("#dateStartSearch").val());
    f.find("[name='dateEndOrg']").val($("#dateEndSearch").val());
    f.find("[name='dateStart']").val($("#dateStartSearch").val().replaceAll("-",""));
    f.find("[name='dateEnd']").val($("#dateEndSearch").val().replaceAll("-",""));
    
    f.find("[name='today']").val($("#todaySearch").prop("checked")?"Y":"");
    f.find("[name='tomorrow']").val($("#tomorrowSearch").prop("checked")?"Y":"");
    
    f.find("[name='status0']").val($("#status0Search").prop("checked")?"Y":"");
    f.find("[name='status2']").val($("#status2Search").prop("checked")?"Y":"");
    f.find("[name='status4']").val($("#status4Search").prop("checked")?"Y":"");
    f.find("[name='status6']").val($("#status6Search").prop("checked")?"Y":"");
    
    f.submit();
};