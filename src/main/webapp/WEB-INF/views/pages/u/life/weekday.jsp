<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/life.js?v=${versionHtml}"/>"></script>
    
    <style>
        .current.reserve span {
			    background: #000;
			    color: var(--white);
			    border-radius: 50%;
		}
		.current.reserve.today2 span {
		    background: var(--lg-primary) !important;
		    color: var(--white);
		    border-radius: 50%;
		}
    </style>
</head>
<body class="about_booking_calendar_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/top-booking.jsp" />
        <section class="calendar_section sub_section">
            <div class="container">
                <h2 class="hiddenText">방탈출 예약 하기</h2>
                <div class="booking_about wrapped_flex">
                    <!-- 2022-11-21 수정 : 이미지 변경 -->
                    <div class="left booking_poster"><img src="<c:url value="/u/images/sub/img_escape_poster2.png"/>" alt=""></div>
                    <div class="right roundLine_box">
                        <p>어서 오시게. 금성 전파사에 온 걸 환영하네.<br />
                            이곳은 사람들의 고민이 모여드는 내 작업실이지.<br />
                            사람들의 고민을 담은 편지가 이곳으로 도착하면,<br />
                            나는 그들에게 해결책을 제시해 주곤 한다네.<br />
                            오늘도 편지가 많이 도착했구먼.<br />
                            오늘은 자네가 나를 도와 사람들의 고민을 <br class="mo_br" />해결해 주지 않겠는가?
                        </p>
                    </div>
                </div>
                <form action="">
                    <div class="booking_wrap">
                        <!-- 2022-11-21 수정 : 텍스트 수정 -->
                        <h3 class="subTitle_line">고민탈출 코-너 (ThinQ 방탈출)</h3>
                        <div class="subTitle_flex">
                            <h3>예약 일시 선택</h3>
                            <p>희망하시는 예약 날짜와 시간을 선택해주세요.</p>
                        </div>
                        <div class="booking_row wrapped_flex">
                            <div class="booking_place roundLine_box">
                                <div class="placeTitle">
                                    <h4>ThinQ 방탈출</h4>
                                    <!-- 2022-11-21 수정 : 텍스트 수정 <p>경동시장점</p> -->
                                </div>
                                <table class="placeBooking_table">
                                    <!-- 2022-11-15 수정 : 참여 인원 추가로 구조변경 -->
                                    <tbody>
                                        <tr>
                                            <th>주소</th>
                                            <td>서울 동대문구 고산자로36길 3 경동시장 본관 3층, 4층 <br />
                                                (지번) 서울특별시 동대문구 제기동 1019
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="vertical-align: top;">영업시간</th>
                                            <td>
                                                <p><span class="text_gray">기본 운영시간</span><span>09:00~22:00</span></p>
                                                <p><span class="text_gray">체험 운영시간</span><span>11:00~19:00</span></p>
                                                <p><span class="text_gray">전시 관람시간</span><span>09:00~11:00 / 19:00~22:00</span></p>
                                            </td>
                                        </tr>
                                        <tr class="mo_hidden bookingPeople">
                                            <th>체험 인원</th>
                                            <td>
                                                <label for="bookingPeople21" class="calandar_time_check">
                                                    <input type="radio" name="bookingPeople1" value="2" id="bookingPeople21">
                                                    <span class="person_btn time_btn">2명</span>
                                                </label>
                                                <label for="bookingPeople31" class="calandar_time_check">
                                                    <input type="radio" name="bookingPeople1" value="3" id="bookingPeople31">
                                                    <span class="person_btn time_btn">3명</span>
                                                </label>
                                                <label for="bookingPeople41" class="calandar_time_check">
                                                    <input type="radio" name="bookingPeople1" value="4" id="bookingPeople41">
                                                    <span class="person_btn time_btn">4명</span>
                                                </label>
                                                <label for="bookingPeople51" class="calandar_time_check">
                                                    <input type="radio" name="bookingPeople1" value="5" id="bookingPeople51">
                                                    <span class="person_btn time_btn">5명</span>
                                                </label>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <tfoot class="mo_hidden">
                                        <tr>
                                            <!-- 2022-11-21 수정 : 텍스트 수정 -->
                                            <td colspan="2" class="tfoot_text warning_text">※협동과제로 인해 1인 참여는 불가한점 양해 바랍니다.</td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="mo_view calandar_time_list">
                                <div class="mo_view placeTitle">
                                    <h4 class="mo_view">체험 인원</h4>
                                </div>
                                <div class="mo_booking_people mo_flex mo_view">
                                    <label for="bookingPeople22" class="calandar_time_check">
                                        <input type="radio" name="bookingPeople2" id="bookingPeople22" value="2">
                                        <span class="person_btn time_btn">2명</span>
                                    </label>
                                    <label for="bookingPeople32" class="calandar_time_check">
                                        <input type="radio" name="bookingPeople2" id="bookingPeople32" value="3">
                                        <span class="person_btn time_btn">3명</span>
                                    </label>
                                    <label for="bookingPeople42" class="calandar_time_check">
                                        <input type="radio" name="bookingPeople2" id="bookingPeople42" value="4">
                                        <span class="person_btn time_btn">4명</span>
                                    </label>
                                    <label for="bookingPeople52" class="calandar_time_check">
                                        <input type="radio" name="bookingPeople2" id="bookingPeople52" value="5">
                                        <span class="person_btn time_btn">5명</span>
                                    </label>
                                </div>
                                <p class="tfoot_text warning_text">※협동과제로 인해 1인 참여는 불가한점 양해 바랍니다.</p>
                            </div>
                            <div class="calander_wrap roundLine_box">
                                <div class="calendar">
                                    <div class="cal_navbar">
                                        <div></div>
                                        <div class="center">
                                            <a href="javascript:;" class="go-prev"></a>
                                            <div class="year-month">2022 10월</div>
                                            <a href="javascript:;" class="go-next"></a>
                                        </div>
                                    </div>
                                    <div class="cal_wrap">
                                        <div class="days">
                                            <div class="day">일</div>
                                            <div class="day">월</div>
                                            <div class="day">화</div>
                                            <div class="day">수</div>
                                            <div class="day">목</div>
                                            <div class="day">금</div>
                                            <div class="day">토</div>
                                        </div>
                                        <div class="dates"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="mo_booking_time mo_flex right calandar_time_list">
                                <div class="mo_view placeTitle">
                                    <h4 class="mo_view">예약시간 선택</h4>
                                </div>
                                <c:forEach items="${times}" var="time" varStatus="index">
                                    <label for="bookingTime<c:out value="${time.name}"/>" class="calandar_time_check">
	                                    <input type="radio" name="bookingTime" id="bookingTime<c:out value="${time.name}"/>" value="<c:out value="${time.name}"/>" disabled>
	                                    <span class="time_btn"><c:out value="${time.fmt}"/></span>
	                                </label>
                                </c:forEach>
                            </div>

                        </div>
                        <ul class="sub_dot_list w100per">
                            <li><span class="text_primary">체험 시작 10분 전</span>까지 도착하지 않으신 경우, 다음 체험자를 위해 신청 내역이 취소될 수 있습니다.</li>
                        </ul>

                    </div>
                    <div class="mt50 btn_full tac btn_wrap">
                        <!-- 2022-11-30 수정 : 선착순으로 예약한경우 예약하기 버튼, 주말드로우 응모는 응모하기로 노출됩니다.(기획 확인 필요) -->
                        <button onClick="javascript:bookingValid(); return false;" class="btn_round_l btn">예약하기</button>
                    </div>
                </form>

            </div>
        </section>
    </div>
    <!-- pop_booking popup start -->
    <section id="pop_booking" class="pop_agree modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>ThinQ 방탈출 체험 동의서</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <div class="con_store">
                    <div class="mindTest_about">
                        <!-- 2022-12-02 수정 : 줄바꿈 br수정 -->
                        <ul class="sub_dot_list w100per">
                            <li>해당 게임은 한정된 공간에서 진행됩니다. 폐소공포증이 있으신 고객님들께서는 이용을 삼가 부탁드립니다.</li>
                            <li>게임 공간 내 음식 및 음료 반입 및 섭취가 어렵습니다. 양해 부탁드립니다.</li>
                            <li>인화성 물질(라이터, 성냥)은 안전상의 이유로 반입이 어렵습니다. </li>
                            <li>게임 중 큰 힘을 요구하는 단계는 없습니다.
                                가전 및 소품을 조심히 다뤄주세요.(파손 시 파손품에 대한 손해 배상의 책임이 따를 수 있습니다.)</li>
                            <li>안전과 보안 및 힌트 제공을 위하여 CCTV 촬영 중입니다.</li>
                        </ul>
                    </div>
                    <div class="con_store_bottom">
                        <div class="tac pc_view">
                            본 동의서는 설명 의무의 이행 여부를 확인할 목적으로만 사용합니다.<br />고객은 이용에 대한 동의를 거부할 수 있으며,<br />동의를 거부할 경우 서비스 이용이 어려운 점 양해 부탁드립니다.
                        </div>
                        <ul class="sub_dot_list mo_view">
                            <li>본 동의서는 설명 의무의 이행 여부를 확인할 목적으로만 사용합니다.</li>
                            <li>고객은 이용에 대한 동의를 거부할 수 있으며, 동의를 거부할 경우 서비스 이용이 어려운 점 양해 부탁드립니다.</li>
                        </ul>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
                    <button onclick="saveBooking()" class="btn_pop_s btn_primary btn">동의합니다</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <div id="popupToast2" class="popupToast_wrap" style="display: none;">
        <div class="popupToast">
            <p class="popupToast_text">선택하신 날짜는 예약이 모두 완료되었습니다. <br />
                다음 예약 오픈 일은 차주 월요일 오전 9시입니다.</p>
            <button onClick="document.getElementById('popupToast2').remove()" class="popupToast_close"></button>
        </div>
    </div>
    <!-- popup end -->
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <!-- script start -->
    <script>
    $(document).ready(function() {
        calendarInit();
    });
    
    function popupToastOpen() {
        $("#popupToast2").fadeIn(200).delay(2000).fadeOut(200);
        return false;
    }

    function calendarInit() {
    	
    	var weekDates = [<c:forEach items="${weekDates}" var="item" varStatus="index">'<c:out value="${item}"/>'<c:if test="${not index.last}">,</c:if></c:forEach>];

        // 날짜 정보 가져오기
    	var date = new Date();
        var thisMonth = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        
    	if(weekDates.length > 0){
    		var yyyy,mm,dd;
            yyyy = parseInt(weekDates[0].substring(0,4), 10);
            mm = parseInt(weekDates[0].substring(4,6), 10) - 1;
            dd = parseInt(weekDates[0].substring(6,8), 10);
            
            thisMonth = new Date(yyyy, mm, dd);
    	}
        
        // 달력에서 표기하는 날짜 객체
        var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
        var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
        var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

        var __thisMonth = thisMonth;
        var __currentYear = currentYear;
        var __currentMonth = currentMonth;
        var __currentDate = currentDate;
        

        // 캘린더 렌더링
        renderCalender(thisMonth);
        
        function renderCalender(thisMonth) {
        	
            // 렌더링을 위한 데이터 정리
            currentYear = thisMonth.getFullYear();
            currentMonth = thisMonth.getMonth();
            currentDate = thisMonth.getDate();

            // 이전 달의 마지막 날 날짜와 요일 구하기
            var startDay = new Date(currentYear, currentMonth, 0);
            var prevDate = startDay.getDate();
            var prevDay = startDay.getDay();

            // 이번 달의 마지막날 날짜와 요일 구하기
            var endDay = new Date(currentYear, currentMonth + 1, 0);
            var nextDate = endDay.getDate();
            var nextDay = endDay.getDay();

            // 현재 월 표기
            $('.year-month').text(currentYear + "." + (currentMonth + 1));

            // 렌더링 html 요소 생성
            calendar = document.querySelector('.dates')
            calendar.innerHTML = '';

            // 지난달
            for (var i = prevDate - prevDay; i <= prevDate; i++) {
                calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + "<div><span>" + i + "</span></div>" + '</div>';
            }
            // 이번달
            for (var i = 1; i <= nextDate; i++) {
                var text = '<div class="day current';
                var yyyyMMdd = currentYear + "" + (currentMonth+1 > 9 ? (currentMonth+1) : "0" + (currentMonth+1)) + "" + (i > 9 ? i : "0" + i) + "";
                
                var isReserve = false;
                for(var dd=0;dd<weekDates.length;dd++){
                	if(yyyyMMdd == weekDates[dd]){
                		isReserve = true;
                    }
                }
                if(isReserve){
                    text += ' reserve';
                }
                else {
                    text += ' disable';
                }
                text += '" ';
                text += ' data-type="weekday"';
                text += ' data-date="' + yyyyMMdd + '">' + "<span>" + i + "</span>" + '</div>';
                
                calendar.innerHTML = calendar.innerHTML + text;
            }
            // 다음달
            for (var i = 1; i <= (7 - (nextDay+1)); i++) {
                calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + "<span>" + i + "</span>" + '</div>'
            }

            // 오늘 날짜 표기
            if (thisMonth.getMonth() == __thisMonth.getMonth()) {
                todayDate = __thisMonth.getDate();
                var currentMonthDate = document.querySelectorAll('.dates .current');
                currentMonthDate[todayDate -1].classList.add('today2');
                
                for(var i = 0; i < currentMonthDate[todayDate -1].classList.length; i++){
                	if(currentMonthDate[todayDate -1].classList[i] == "reserve"){
                        getTimes();
                	}
                }
            }
            
            $(".day.current").on({
            	"click" : function(){
            		$(".day").removeClass("today2");
            		$(this).addClass("today2");
            		
            		console.log($(this).hasClass("reserve"));
            		if($(this).hasClass("reserve")){
                        getTimes();
            		}
            	}
            });
            
        }

        // 이전달로 이동
        $('.go-prev').on('click', function() {
            thisMonth = new Date(currentYear, currentMonth - 1, 1);
            renderCalender(thisMonth);
        });

        // 다음달로 이동
        $('.go-next').on('click', function() {
            thisMonth = new Date(currentYear, currentMonth + 1, 1);
            renderCalender(thisMonth);
        });
    }
    </script>
</body>
</html>
