/* datepicker */
$(function () {
	$('.input_date').datepicker({
		dateFormat: 'yy-mm-dd', //달력 날짜 형태
		dayNames: ['SUN','MON','TUE','WED','THU','FRI','SAT'],
		dayNamesMin: ['SUN','MON','TUE','WED','THU','FRI','SAT'],
		buttonImageOnly: true
	});
});
/* 사전문답, faq, 공지사항 삭제 */
$(function () {
	$('.list_btn_wrap .btn_modify').click(function () {
		if ($(this).hasClass('btn_modify_comp')) {
			$(this).parent().parent().find(".list_bbs_input").hide();
			$(this).parent().parent().find(".list_bbs_view").show();
			$(this).parent().parent().addClass(".ui-state-disabled");
			$(this).text("수정");
			$(this).removeClass("btn_modify_comp");
		} else {
			$(this).parent().parent().find(".list_bbs_view").hide();
			$(this).parent().parent().find(".list_bbs_input").show();
			$(this).text("완료");
			$(this).addClass("btn_modify_comp");
			$(this).parent().parent().parent().removeClass(".ui-state-disabled");
		}
	});
	$('.list_btn_wrap .btn_modify_comp').click(function () {
		return false;
	});
	$('.list_btn_wrap .btn_delete').click(function () {
		$(this).parent().parent().hide();
	});
});
/* 순서변경 sortable */
$(function(){
	$( ".adm_sortable" ).sortable({
		items: "li:not(.adm_list_title)",
		placeholder: "ui-state-highlight",
		cancel: ".ui-state-disabled, input, textarea, select, button"
	});
	$( ".adm_sortable" ).disableSelection();
})

/* 눈모양 노출 script */
$(function(){
	$('.pw_eye').on('click',function(){
		$('.input_pw').toggleClass('active');
		if($('.input_pw').hasClass('active')){
			$(this).attr('class',"pw_eye pw_eye_on").prev('input').attr('type',"text");
		}else {
			$(this).attr('class',"pw_eye").prev('input').attr('type','password');
			$('.input_pw').removeClass('active');
		}
	});
});

/* 프론트페이지에 있던 팝업 닫기 복제 */
function popClose() {
    $('.modal').css("display", "none");
    $('html').css({'overflow':'unset'});
    $('html').css({'position':'relative'});
	return false;
};
$(function(){
	$('.dim').click(function() {
		$(this).parent('.modal').css('display','none');
		$('html').css({'overflow':'unset'});
		$('html').css({'position':'relative'});
		return false;
	});
});/*팝업 딤 함수*/

/* 파일 업로드 */
$(function () {
	document.querySelectorAll(".customFile").forEach(ee => {
		ee.onchange = function(e) { 
			ee.parentNode.childNodes[3].childNodes[1].innerText = ee.files[0].name;
			ee.parentNode.childNodes[3].childNodes[1].className += 'uploaded';
		};
	})
})
/* 2022-12-21 현장예약고객 추가 스크립트 */
// 직접입력하기 눌렀을때 input type이 text에서 search로 변경하는 식으로 바꿨습니다.
$(function () {
	$("#excape_addcustom").change(function () {
		if ($("#excape_addcustom").is(':checked')) {
			$('#pop_addpeople_name1').prop("type", "text");
			$('#pop_addpeople_phone1').prop("type", "text");
		} else {
			$('#pop_addpeople_name1').prop("type", "search");
			$('#pop_addpeople_phone1').prop("type", "search");
		}
		return false;
	});
})