$(function(){
    $(".border_input").on({
        "keypress" : function(e) {
            if (e.which == 13) {
            	signin();
            }
        }
    });
    $("#adminID").focus();
	$("#signin-action").on({
        "click" : signin,
        "keypress" : function(e) {
            if (e.which == 13) {
            	r9.archive.signin();
            }
        }
    });
});

var signin = function(){
	
	var data = {};
    data["uid"] = $("#adminID").val();
    data["password"] = $("#adminPW1").val();
    data["passwordChange"] = $("#adminPW2").val();
    data["passwordChangeRe"] = $("#adminPW3").val();
    
    if(data["password"].password(8)){
        $("#pop_login").find(".resultTxt").html("비밀번호는 영문, 숫자, 특수문자로 구성된 8자리 이상입니다.");
        $('#pop_login').show();
        return;
    }
    
    if(data["passwordChange"].password(8)){
        $("#pop_login").find(".resultTxt").html("변경 할 비밀번호는 영문, 숫자, 특수문자로 구성된 8자리 이상입니다.");
        $('#pop_login').show();
        return;
    }
    
    if(data["password"] == data["passwordChange"]){
        $("#pop_login").find(".resultTxt").html("동일한 비밀번호는 설정할 수 없습니다.");
        $('#pop_login').show();
        return;
    }
    
    if(data["passwordChange"].indexOf(data["uid"]) > -1){
        $("#pop_login").find(".resultTxt").html("변경 할 비밀번호는 아이디를 포함할 수 없습니다.");
        $('#pop_login').show();
        return;
    }
    
    if(data["passwordChange"] != data["passwordChangeRe"]){
        $("#pop_login").find(".resultTxt").html("변경할 비밀번호를 확인해주세요.");
        $('#pop_login').show();
        return;
    }
    
    $.ajax({
        url: contextPath + "m/manager/password/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            
        }
        else {
            switch(json["result"]){
                case "EMPTY_USERNAME":
                    $("#pop_login").find(".resultTxt").html("아이디를 입력해주세요.");
                    break;
                case "EMPTY_PASSWORD":
                    $("#pop_login").find(".resultTxt").html("비밀번호를 입력해주세요.");
                    break;
                case "NOT_FOUND":
                    $("#pop_login").find(".resultTxt").html("아이디 또는 비밀번호가 잘못 입력되었습니다.");
                    break;
                case "INVALID_PASSWORD":
                    $("#pop_login").find(".resultTxt").html("비밀번호가 잘못되었습니다.[" + json["count"] + " / 5]");
                    break;
                case "INVALID_PASSWORD_MAX":
                    $("#pop_login").find(".resultTxt").html("비밀번호 오류횟수가 5회 초과되어 접속이 제한됩니다.");
                    break;
                case "CHANGE_PASSWORD":
                    $("#pop_login").find(".resultTxt").html("비밀번호가 변경된지 6개월 이상 경과되어 접속이 제한됩니다.");
                    break;
                default:
                    $("#pop_login").find(".resultTxt").html(json["result"]);
                    break;
            }
            $('#pop_login').show();
        }
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};
	