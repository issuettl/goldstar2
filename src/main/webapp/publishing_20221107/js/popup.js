/* 팝업페이지로 노출 */
async function fetchHtmlAsText(url) {
    return await (await fetch(url)).text();
}
async function importPage(target) {
    $('#'+ target).html( await fetchHtmlAsText('../../popup/' + target + '.html'))
    $('.dim').click(function() {
		$(this).parent('.modal').css('display','none');
        $('html').css({'overflow':'unset'});
		$('html').css({'overflow-y':'scroll'});
        $('html').css({'position':'relative'});
		$('body').css({'padding-right':'0px'});
	});
}
/* 팝업 */
function popupOpen(targetId) {
    $('html').css({'overflow':'hidden'});
    // $('html').css({'position':'fixed'});/
    /* $('html').on('scroll touchmove mousewheel', function(event) {
        event.preventDefault();
        event.stopPropagation();
        return false;
    }); */
    $('#'+targetId).fadeIn(100);
	$('body').css('padding-right','17px');
	if(targetId === "pop_survey" && window.innerWidth > 767){
		initSlick();
	}
};
function popupSmallOpen() {
    $('.popupSmall').css("display", "flex");
	$('.sectionPopup .dim').css("display", "block");
	$('body').css('padding-right','17px');
	return false;
};
function popClose() {
    $('.modal').css("display", "none");
    $('html').css({'overflow':'unset'});
	$('html').css({'overflow-y':'scroll'});
    $('html').css({'position':'relative'}); 
	$('body').css({'padding-right':'0px'});
	return false;
};
/* 팝업 노출 */
function popMindTest() {
	$('.pop_mindTest').css("display", "block");
};
function popMindClose() {
	$('.modal').css("display", "none");
	$('html').css({'overflow':'unset'});
	$('html').css({'overflow-y':'scroll'});
	$('html').css({'position':'relative'});
	$('.con_mindResult').hide();
	$('.con_mindTest').show();
	if($('.mind_Q4').css('display') == 'block') {
		$('.mind_Q4').hide();
		$('.mind_Q1').show();
	} else if($('.mind_Q3').css('display') == 'block') {
		$('.mind_Q3').hide();
		$('.mind_Q1').show();
	}else if($('.mind_Q1').css('display') == 'none') {
		$('.mind_Q1').show();
	}
	return false;
};
$(document).ready(function() {
	$('.dim').click(function() {
		$(this).parent('.modal').css('display','none');
		$('html').css({'overflow':'unset'});
		$('html').css({'overflow-y':'scroll'});
		$('html').css({'position':'relative'});
		$('body').css({'padding-right':'0px'});
	});
});/*팝업 딤 함수*/

/* 팝업 질문지 이동 2번 */
function next_q1() {
	if($('.mind_Q2').css('display') == 'block') {
		$('.mind_Q2').hide();
		$('.mind_Q1').fadeIn();
	} else {
		$('.mind_Q1').hide();
		$('.mind_Q2').fadeIn();
	}
}
/* 팝업 질문지 이동 3번 */
function next_q2() {
	if($('.mind_Q3').css('display') == 'block') {
		$('.mind_Q3').hide();
		$('.mind_Q2').fadeIn();
	} else {
		$('.mind_Q2').hide();
		$('.mind_Q3').fadeIn();
	}
}
/* 팝업 질문지 이동 4번 */
function next_q3() {
	if($('.mind_Q4').css('display') == 'block') {
		$('.mind_Q4').hide();
		$('.mind_Q3').fadeIn();
	} else {
		$('.mind_Q3').hide();
		$('.mind_Q4').fadeIn();
	}
}
/* 팝업 질문지 이동 5번 */
function next_q4() {
	if($('.mind_Q5').css('display') == 'block') {
		$('.mind_Q5').hide();
		$('.mind_Q4').fadeIn();
	} else {
		$('.mind_Q4').hide();
		$('.mind_Q5').fadeIn();
	}
}
/* 팝업 질문지 이동 6번 */
function next_q5() {
	if($('.mind_Q6').css('display') == 'block') {
		$('.mind_Q6').hide();
		$('.mind_Q5').fadeIn();
	} else {
		$('.mind_Q5').hide();
		$('.mind_Q6').fadeIn();
	}
}
/* 팝업 답안지로 이동 */
function next_a1() {
	if($('.con_mindResult').css('display') == 'block') {
		$('.con_mindTest').show();
		$('.con_mindResult').hide();
	} else {
		$('.con_mindTest').hide();
		$('.con_mindResult').fadeIn();
	}
}