$(function(){
    
    //검색
    $(".click-search").on({
        "click" : doSearch
    });
    
    $(".staffCheck").on({
        "change" : doStaff
    });
    
    $(".doJammy").on({
        "click" : doJammy
    });
});

var doStaff = function(){
    var data = {};
    data["sn"] = $(this).data("sn");
    data["corner"] = $(this).data("type");
    data["staff"] = $(this).val();

    $.ajax({
        url: contextPath + "m/member/staff/check.do",
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

var doJammy = function(){
    var data = {};
    data["sn"] = $(this).data("sn");
    data["corner"] = $(this).data("type");
    data["staff"] = $(this).val();

    $.ajax({
        url: contextPath + "m/member/jammy/issue.do",
        data: data,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            
            $("#jammyZone").html('<p>발급 완료</p><p>(' + json["saved"].code + ')</p>');
            
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
    
    f.submit();
};