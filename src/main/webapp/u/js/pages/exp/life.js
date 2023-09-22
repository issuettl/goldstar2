$(function(){
    $(".doCancel").on({
        "click" : doCancel
    });
});
var doCancel = function(){
    if(!confirm("예약을 취소하시겠습니까?")){return;}
    var data = {};
    data["sn"] = $(this).data("sn");
    data["memo"] = $("#escape_del_textarea").val();
    
    $.ajax({
        url: contextPath + "u/life/booking/cancel.do",
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
var getTimes = function(){
    $("input[name='bookingTime']").each(function(){
        $(this).prop("disabled", true);
    });
    var today = $(".day.current.today2");
    if(!today.hasClass("reserve")){
        return;
    }
    var date = today.data("date");
    var data = {};
    data["date"] = date;
    
    $.ajax({
        url: contextPath + "u/life/booking/times.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        if(json["result"] == "SUCCESS"){
            
            $("input[name='bookingTime']").each(function(){
                if(json["times"]){
                    for(var i=0;i<json["times"].length;i++){
                        if($(this).val() == json["times"][i]){
                            $(this).prop("disabled", false);
                        }
                    }
                }
            });
            
            if(!json["times"] || json["times"].length == 0){
                popupToastOpen();
            }
        }
        else {
            alert(json["reason"]);
        }
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });
};

var bookingValid = function(){
    
    var today = $(".day.current.today2");
    
    if(!today.hasClass("reserve")){
        alert("예약 불가능한 날짜입니다.");
        return;
    }
    
    if($(".bookingPeople.mo_hidden").css("display") == "none"){
        if($("input[name='bookingPeople2']:checked").length == 0){
            alert("체험 인원을 선택해주세요.");
            return;
        }
    }
    else {
        if($("input[name='bookingPeople1']:checked").length == 0){
            alert("체험 인원을 선택해주세요.");
            return;
        }
    }
    
    if($(".day.current.today2").length == 0){
        alert("날짜를 선택해주세요.");
        return;
    }
    
    if($("input[name='bookingTime']:checked").length == 0){
        alert("체험 시간을 선택해주세요.");
        return;
    }
    
    popupOpen('pop_booking');
};


var saveBooking = function(){
    
    if($(".bookingPeople.mo_hidden").css("display") == "none"){
        if($("input[name='bookingPeople2']:checked").length == 0){
            alert("체험 인원을 선택해주세요.");
            return;
        }
    }
    else {
        if($("input[name='bookingPeople1']:checked").length == 0){
            alert("체험 인원을 선택해주세요.");
            return;
        }
    }
    
    if($(".day.current.today2").length == 0){
        alert("날짜를 선택해주세요.");
        return;
    }
    
    if($("input[name='bookingTime']:checked").length == 0){
        alert("체험 시간을 선택해주세요.");
        return;
    }
    var today = $(".day.current.today2");
    if(!today.hasClass("reserve")){
        alert("예약 불가능한 날짜입니다.");
        return;
    }
    
    var data = {};
    data["date"] = today.data("date");
    data["time"] = $("input[name='bookingTime']:checked").val();
    data["memberCount"] = $(".bookingPeople.mo_hidden").css("display") == "none" ? $("input[name='bookingPeople2']:checked").val() : $("input[name='bookingPeople1']:checked").val();
    data["type"] = today.data("type");
    
    $.ajax({
        url: contextPath + "u/life/booking/action.do",
        data: JSON.stringify(data),
        method: "POST",
        contentType: "application/json",
        dataType: "json"
    })
    .done(function(json) {
        console.log(json);
        if(json["result"] == "SUCCESS"){
            
            alert('예약이 완료되었습니다.');
            document.location.href='./list.do';
        }
        else {
            alert(json["reason"]);
        }
    })
    .fail(function(xhr, status, errorThrown) {
        alert(status);
    });

}