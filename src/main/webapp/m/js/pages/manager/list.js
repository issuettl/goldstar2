$(function(){
    $("#saveAuthAll").on({
        "click" : function(){
            $("." + $(this).attr("id")).prop("checked", $(this).prop("checked"));
        }
    });
    
    $("#doSave").on({
        "click" : doSave
    });
    
    $(".doDelete").on({
        "click" : doDelete
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

var doSave = function(){
    
    var data = {};
    data["uid"] = $("#saveUid").val();
    data["password"] = $("#savePassword").val();
    data["company"] = $("#saveCompany").val();
    data["name"] = $("#saveName").val();
    
    if(!data["uid"]){alert("아이디를 입력해주세요.");$("#saveUid").focus();return;}
    if(data["uid"].length < 8 || data["uid"].length > 12){alert("아이디는 8자리 이상 12자리 이하입니다.");$("#saveUid").focus();return;}
    if(!data["uid"].engnum()){alert("아이디는 영문 또는 숫자로 구성되어야 합니다.");$("#saveUid").focus();return;}
    
    if(!data["password"]){alert("비밀번호를 입력해주세요.");$("#savePassword").focus();return;}
    if(data["password"].password(8) ||  data["password"].length > 12){alert("비밀번호는 영문, 숫자, 특수문자로 구성된 8자리 이상, 12자리 이하입니다.");$("#savePassword").focus();return;}
    
    if(!data["company"]){alert("업체명을 입력해주세요.");$("#saveCompany").focus();return;}
    if(data["company"].length > 100){alert("업체명은 100자리 이하입니다.");$("#saveCompany").focus();return;}
    
    if(!data["name"]){alert("담당자를 입력해주세요.");$("#saveName").focus();return;}
    if(data["name"].length > 100){alert("담당자는 100자리 이하입니다.");$("#saveName").focus();return;}
    
    data["authList"] = new Array();
    $(".saveAuthAll").each(function(){
        if($(this).prop("checked")) {
            var auth = {};
            auth["id"] = {};
            auth["id"]["auth"] = $(this).val();
            data["authList"].push(auth);
        }
    });
    
    $.ajax({
        url: contextPath + "m/manager/save/action.do",
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
    if(!confirm("삭제 하시겠습니까?")){return;}
    var data = {};
    data["sn"] = $(this).data("sn");
    
    $.ajax({
        url: contextPath + "m/manager/remove/action.do",
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