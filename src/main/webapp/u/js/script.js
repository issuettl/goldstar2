
/* */
function preventDefault(e) {
    e = e || window.event;
    if (e.preventDefault)
        e.preventDefault();
    e.returnValue = false;
  }
  
  function theMouseWheel(e) {
    preventDefault(e);
  }
  
  function disable_scroll() {
    if (window.addEventListener) {
        window.addEventListener('DOMMouseScroll', theMouseWheel, false);
    }
    window.onmousewheel = document.onmousewheel = theMouseWheel;
  }
  
  function enable_scroll() {
      if (window.removeEventListener) {
          window.removeEventListener('DOMMouseScroll', theMouseWheel, false);
      }
      window.onmousewheel = document.onmousewheel = null;
  }
  
  $(function () {
    $(".modal").on('shown.bs.modal', function () {disable_scroll();});
    $(".modal").on('hidden.bs.modal', function () {enable_scroll();});
  });
/* 라지 팝업 클릭 end */
/*$(document).ready(function() {
	$('.dim').click(function() {
		$('.modal').css("display", "none");
	});
	return false;
});팝업 딤 함수*/

/* 모바일 full 화면 */
function setScreenSize() {
	let vh = window.innerHeight * 0.01;
	document.documentElement.style.setProperty('--vh', `${vh}px`);
}
setScreenSize();
window.addEventListener('resize', setScreenSize);


/* 2022-11-03 스크롤 메뉴 임시 닫음
$(document).ready(function() {
	// Hide Header on on scroll down
var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('.mo_header').outerHeight();

$(window).scroll(function(event){
    didScroll = true;
});

setInterval(function() {
    if (didScroll) {
        hasScrolled();
        didScroll = false;
    }
}, 250);

function hasScrolled() {
    var st = $(this).scrollTop();
    
    if(Math.abs(lastScrollTop - st) <= delta)
        return;
    
    if (st > lastScrollTop && st > navbarHeight){
        // Scroll Down
		$('.mo_header').removeClass('active');
    } else {
        if (st > 770) {
             // Scroll Up
		    $('.mo_header').addClass('active');
        } else {
            $('.mo_header').removeClass('active');
        }
       
    }
    lastScrollTop = st;
}
    
    

});
 */
/* 2022-11-28 수정 체크박스 전체 동의 임시 팝업 -> 동의 팝업 btn에 class 추가 및 class 속성 변경 */
function checkagreeall() {
    if ($("#agreeCheck0").prop('checked')) {
        $("#agreeCheck1").prop('checked',true);
        $("#agreeCheck2").prop('checked',true);
        $('.pop_agree .btn_wrap .btn_agree_ok').removeClass('btn_lightgray');
        $('.pop_agree .btn_wrap .btn_agree_ok').addClass('btn_primary');
    } else {
        $("#agreeCheck1").prop('checked',false);
        $("#agreeCheck2").prop('checked',false);
        $('.pop_agree .btn_wrap .btn_agree_ok').removeClass('btn_primary');
        $('.pop_agree .btn_wrap .btn_agree_ok').addClass('btn_lightgray');
    }
    
}
/* 2022-12-07 LG GNB 일괄 반영 스크립트 
각 div에 파일명과 동일한 id값을 가진 div      를 생성시켜 그 안에 기존의 LG소스를 넣어 일괄적용했습니다.
S
async function fetchHtmlAsText(url) {
    return await (await fetch(url)).text();
}
async function importHeadPage(target) {
    $('#'+ target).html( await fetchHtmlAsText('../gnb/html/components/' + target + '.html'))
}
async function importHeadPage(target) {
    document.getElementById(target).innerHTML('https://www.lge.co.kr/gnb');
}
// 푸터
async function importFootPage(target) {
    $('#'+ target).html( await fetchHtmlAsText('../gnb/html/components/' + target + '.html'))
}
*/
/* 메인페이지 상단 youtube 팝업 닫기 재생정지 */
function popCloseYoutube(){
    popClose();
    //document.getElementsByClassName("ytp-play-button")[0].click();
    $("iframe")[0].contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
}

/* 질문 다음, 이전 이동 2023년도 통일 */
function toggleSlide(current, next) {
    if($(next).css('display') == 'block') {
        $(next).hide();
        $(current).fadeIn();
    } else {
        $(current).hide();
        $(next).fadeIn();
    }
}