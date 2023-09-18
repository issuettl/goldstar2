$(function(){
});

function popupOpenCookie(targetId, url) {
    if($.cookie("GOLDSTAR_A") == "Y"){
        document.location.href = url;
        return;
    }
    popupOpen(targetId);
};

function popCloseCookie(url) {
    var date = new Date();
    var expires = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), 23, 59, 59));
    $.cookie("GOLDSTAR_A", "Y", {"expires" : expires});
    
    document.location.href = url;
};