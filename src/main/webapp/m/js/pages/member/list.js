$(function(){
    
    //검색
    $("#click-search").on({
        "click" : doSearch
    });
    
    //페이징
    $(".pagination").find("button").on({
        "click" : function(){
            var page = $(this).data("page");
            $("#searchForm > input[name='page']").val(page);
            $("#searchForm").submit();
        }
    });
    
    $(".goView").on({
        "click" : function(){
            $("#goView").data("sn", $(this).data("sn"));
            $('#pop_password').show();
        }
    });
    $("#goView").on({
        "click" : function(){
            if($("#managerPassword").val().password(8)){
                alert("비밀번호는 영문, 숫자, 특수문자로 구성된 8자리 이상입니다.");
                return;
            }
            var f = $("#viewForm");
            f.find("[name='sn']").val($(this).data("sn"));
            f.find("[name='password']").val($("#managerPassword").val());
            f.submit();
        }
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
    f.find("[name='name']").val($("#nameSearch").val());
    f.find("[name='phone']").val($("#phoneSearch").val());
    
    f.find("[name='dateStartOrg']").val($("#dateStartSearch").val());
    f.find("[name='dateEndOrg']").val($("#dateEndSearch").val());
    f.find("[name='dateStart']").val($("#dateStartSearch").val().replaceAll("-",""));
    f.find("[name='dateEnd']").val($("#dateEndSearch").val().replaceAll("-",""));
    
    f.submit();
};