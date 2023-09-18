$(function(){
    $("#statusAll").on({
        "click" : function(){
            $("." + $(this).attr("id")).prop("checked", $(this).prop("checked"));
        }
    });
    $("#timeAll").on({
        "click" : function(){
            $("." + $(this).attr("id")).prop("checked", $(this).prop("checked"));
        }
    });
    $(".snListAll").on({
        "click" : function(){
            var checked = $(this).prop("checked");
            if(checked){
                $(".snListAll").prop("checked", false);
                $(this).prop("checked", true);
            }
        }
    });
    
    //검색
    $("#click-search").on({
        "click" : doSearch
    });
    
    $(".doConfirm").on({
        "click" : doConfirm
    });
    
    $(".doConfirms").on({
        "click" : doConfirms
    });
    
    //페이징
    $(".pagination").find("button").on({
        "click" : function(){
            var page = $(this).data("page");
            $("#searchForm > input[name='page']").val(page);
            $("#searchForm").submit();
        }
    });
});
var doConfirms = function(){
    var sn = $(".snListAll:checked").val();
    if(!sn){
        alert("당첨자를 선택해주세요.");
        return;
    }
    
    if(!confirm("당첨 시키겠습니까?")){return;}
    var data = {};
    data["sn"] = sn;
    
    $.ajax({
        url: contextPath + "m/life/admin/confirm.do",
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
var doConfirm = function(){
    if(!confirm("당첨 시키겠습니까?")){return;}
    var data = {};
    data["sn"] = $(this).data("sn");
    
    $.ajax({
        url: contextPath + "m/life/admin/confirm.do",
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
    
    f.find("[name='status1']").val($("#status1Search").prop("checked")?"Y":"");
    f.find("[name='status2']").val($("#status2Search").prop("checked")?"Y":"");
    f.find("[name='status3']").val($("#status3Search").prop("checked")?"Y":"");
    f.find("[name='status4']").val($("#status4Search").prop("checked")?"Y":"");
    
    f.find("[name='timeAM11']").val($("#timeAM11Search").prop("checked")?"Y":"");
    f.find("[name='timePM12']").val($("#timePM12Search").prop("checked")?"Y":"");
    f.find("[name='timePM13']").val($("#timePM13Search").prop("checked")?"Y":"");
    f.find("[name='timePM14']").val($("#timePM14Search").prop("checked")?"Y":"");
    f.find("[name='timePM15']").val($("#timePM15Search").prop("checked")?"Y":"");
    f.find("[name='timePM16']").val($("#timePM16Search").prop("checked")?"Y":"");
    f.find("[name='timePM17']").val($("#timePM17Search").prop("checked")?"Y":"");
    f.find("[name='timePM18']").val($("#timePM18Search").prop("checked")?"Y":"");
    
    f.submit();
};
    