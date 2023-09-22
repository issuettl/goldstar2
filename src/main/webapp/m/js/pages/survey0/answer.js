$(function(){
    //검색
    $("#click-search").on({
        "click" : doSearch
    });
});
var doSearch = function(){
    var sDate = parseInt($("#dateStartSearch").val().replaceAll("-",""), 10);
    var eDate = parseInt($("#dateEndSearch").val().replaceAll("-",""), 10);
    if(sDate > eDate){
        alert("시작일은 종료일보다 늦을 수 없습니다.");
        return;
    }
    
    var f = $("#searchForm");
    f.find("[name='dateStartOrg']").val($("#dateStartSearch").val());
    f.find("[name='dateEndOrg']").val($("#dateEndSearch").val());
    f.find("[name='dateStart']").val($("#dateStartSearch").val().replaceAll("-",""));
    f.find("[name='dateEnd']").val($("#dateEndSearch").val().replaceAll("-",""));
    
    f.submit();
};