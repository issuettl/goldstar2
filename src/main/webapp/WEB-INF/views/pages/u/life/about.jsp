<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/life.js?v=${versionHtml}"/>"></script>
</head>
<body class="about_booking_about_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/top-booking.jsp" />
        <section class="sub_escape_about sub_section">
            <div class="container">
                <h2 class="hiddenText">방탈출 예약 하기</h2>
                <!-- 2022-12-09 수정 : 내용 추가 -->
                <div class="booking_about booking_about_center">
                    <div class="booking_poster"><img src="<c:url value="/u/images/sub/img_escape_poster2.png"/>" alt=""></div>
                    <div class="t_center roundLine_box">
                        <p>어서 오시게. 금성 전파사에 온 걸 환영하네.<br />
                            이곳은 사람들의 고민이 모여드는 내 작업실이지.<br />
                            사람들의 고민을 담은 편지가 이곳으로 도착하면,<br />
                            나는 그들에게 해결책을 제시해 주곤 한다네.<br />
                            오늘도 편지가 많이 도착했구먼.<br />
                            오늘은 자네가 나를 도와 사람들의 고민을 <br class="mo_br" />해결해 주지 않겠는가?
                        </p>
                    </div>
                </div>
                <div class="booking_center_subtxt m0">
                    <!-- 2022-12-06 수정 : 내용 추가 -->
                    <h3 class="booking_center_title">LG ThinQ란?</h3>
                    <p class="t_left">ThinQ로 LG 가전을 언제 어디서나 한 번에 제어하고 상태를 확인할 수 있을 뿐 아니라, <br class="pc_br" />
                        사용자의 환경이나 사용 습관에 맞춰 내 가전 생활을 업그레이드 해줍니다. 또한 IoT기기를 연결한 다양한 사용씬을 통해 나만의 스마트한 일상을 경험할 수 있습니다.</p>
                </div>
                <div class="booking_center_subtxt m0">
                    <h3 class="booking_center_title">ThinQ 방탈출이란?</h3>
                    <p class="t_left">ThinQ 앱을 통해 테마에 설치된 가전과 IoT 기기를 원격으로 제어하고, 앱에서만 제공하는 다양한 서비스와 컨텐츠를 활용한 단서를 통해 문제를 해결하여 제한시간 내 밀실을 탈출하는 새로운 방식의 체험/경험 공간입니다.</p>
                </div>
                <div class="booking_recommend wrapped_flex">
                    <div class="recommend_row">
                        <!-- 위에 2개는 앞페이지 worry_select에서 추천 클릭한 두개의 컨텐츠가 윗줄에 2개 나타나고, wrapped_ab가 나타납니다. -->
                        <figure class="w50per recommend roundShadow_box">
                            <img src="<c:url value="/u/images/sub/icn_escape_01.png"/>" alt="">
                            <span class="texts">
                                <h3>평일 선착순 예약</h3>
                                <p>월 / 화 / 수 / 목 / 금</p>
                            </span>
                            
                            <c:choose>
	                            <c:when test="${isMemberIn}">
                            <div class="btn_wrap t_center"><button onClick="javascript:popupOpen('pop_booking2'); return false;" class="btn_black btn">예약하기</button></div>
	                            </c:when>
	                            <c:otherwise>
                            <div class="btn_wrap t_center"><button onClick="document.location.href='<c:url value="/sso/account/in.do?state=/u/life/about.do"/>';" class="btn_black btn">예약하기</button></div>
	                            </c:otherwise>
                            </c:choose>
                            
                        </figure>
                        <figure class="w50per recommend roundShadow_box">
                            <img src="<c:url value="/u/images/sub/icn_escape_02.png"/>" alt="">
                            <span class="texts">
                                <h3>주말 추첨 응모</h3>
                                <p>토 / 일</p>
                            </span>
                            <c:choose>
                                <c:when test="${isMemberIn}">
                            <div class="btn_wrap t_center"><button onClick="javascript:popupOpen('pop_booking3'); return false;" class="btn_primary btn">응모하기</button></div>
                                </c:when>
                                <c:otherwise>
                            <div class="btn_wrap t_center"><button onClick="document.location.href='<c:url value="/sso/account/in.do?state=/u/life/about.do"/>';" class="btn_primary btn">응모하기</button></div>
                                </c:otherwise>
                            </c:choose>
                        </figure>
                    </div>
                </div>
                <div class="booking_about_row">
                    <h3 class="booking_center_title">ThinQ 방탈출 참여하기</h3>
                    <div class="booking_flow wrapped_flex">
                        <div class="w30per">
                            <div class="circle_num">01</div>
                            <h4 class="flow_title">선착순, 응모</h4>
                            <p>방식에 따른 예약 진행</p>
                            <img src="<c:url value="/u/images/sub/icn_escapeflow_01.png"/>" alt="첫번째 선착순, 응모 및 예약">
                        </div>
                        <div class="w30per">
                            <div class="circle_num">02</div>
                            <h4 class="flow_title">예약 시간 15분 전</h4>
                            <p>도착 후 방탈출 체험 진행</p>
                            <img src="<c:url value="/u/images/sub/icn_escapeflow_02.png"/>" alt="두번째, 예약시간 15분전 도착">
                        </div>
                        <div class="w30per">
                            <div class="circle_num">03</div>
                            <h4 class="flow_title">금성전파사 새로고침센터</h4>
                            <p>다른 체험 코-너도 참여</p>
                            <img src="<c:url value="/u/images/sub/icn_escapeflow_03.png"/>" alt="세번째, 방탈출 후 다른 체험코너도 체험">
                        </div>
                    </div>
                </div>
                <div class="booking_about_row">
                    <h3 class="booking_center_title">예약 안내</h3>
                    <p class="booking_center_subtxt t_center">
                        <strong>매주 월요일 오전 9시</strong> 신규 예약이 오픈 됩니다.<br />
                        <strong>매주 수요일</strong> 주말 추첨 응모 당첨자가 공개 됩니다.<br />
                        (운영사무국: <a href="tel:02-3295-4947">02-3295-4947</a>)
                    </p>
                    <div class="booking_ticket_row wrapped_flex">
                        <div class="black col">
                            <figure class="ticket_figure"><strong>평일</strong><span>ThinQ 방탈출 예약</span></figure>
                            <p class="text_gray">매주 월요일 오전 9시부터<br /> <strong>신규 선착순 예약</strong>이 가능합니다.</p>
                            <!-- 2022-12-09 수정 : 내용 삭제  -->
                            <p class="text_warning">*1일 1회 예약 가능</p>
                        </div>
                        <div class="primary col">
                            <figure class="ticket_figure"><strong>주말</strong><span>ThinQ 방탈출 추첨</span></figure>
                            <p class="text_gray">매주 월~일요일까지<br /> <strong>차주 주말 추첨 응모</strong>가 가능합니다.</p>
                            <p class="text_warning">*주 1회 응모 가능/당첨시 개별 연락</p>
                        </div>
                    </div>
                    <div class="booking_center_subtxt t_center">
                        <img src="<c:url value="/u/images/sub/img_weekly_pc.png"/>" alt="" class="pc_view m0">
                        <img src="<c:url value="/u/images/sub/img_weekly_mo.png"/>" alt="" class="mo_view">
                    </div>
                    <div class="warning_1211 roundLine_box">
	                    <p class="pb10">※ 유의사항</p>
	                    <p>- LG ThinQ 앱 내에 가전제품 등록을 위해서는 가정 내 무선인터넷 환경이 필요합니다. <br />
	                        - ThinQ 기능 사용을 위해서는 스마트폰의 Google Play 스토어 또는 Apple App 스토어에서 'LG ThinQ' 앱을 설치하여 Wi-Fi에 연결해야 합니다. 자세한 사용 방법은 애플리케이션의 도움말을 참고해 주세요.<br />
	                        - LG ThinQ 앱은 일부 스마트폰에서는 기능 사용에 제약이 발생하거나 작동하지 않을 수 있으니, 최소 사양을 확인 후 사용하세요. (Android OS 7.0 이상, iOS 14.0 이상)<br />
	                        - LG ThinQ 앱의 실제 기능은 보유 제품, 모델별로 차이가 있을 수 있습니다.</p>
	                </div>
                </div>

            </div>
        </section>
    </div>
    <!-- 2022-12-09 수정 : 팝업구조변경 -->
    <!-- pop_booking2 popup start -->
    <section id="pop_booking2" class="pop_booking pop_agree modal">
		<div class="popBg">
			<div class="popContent">
				<div class="popTitle_wrap">
					<h2>개인정보 수집·이용 및 제공에 대한 안내</h2>
					<button onClick="popClose()" class="popup_close"></button>
				</div>
				<div class="con_store">
					<div class="pop_agree_title">
						<p>LG전자(이 하 “회사”)는 이벤트 참가와 관련하여 아래와 같이 개인정보의 수집·이용하고자 합니다. <br />
							내용을 자세히 읽으신 후 동의 여부를 결정하여 주시기 바랍니다.</p>
					</div>
					<div class="booking_wrap">
						<div class="booking_agree">
							<div class="agree_wrap">
								<h3>개인정보의 수집 · 이용 동의서(필수)</h3>
								<table class="pop_agree_table2 mo_hidden">
									<thead>
										<tr>
											<th>수집항목</th>
											<th>수집·이용 목적</th>
											<th>보유·이용기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>이름, 휴대전화번호, 예약일,<br class="pc_br" />
												예약시간, 동반인원 수</td>
											<td>ThinQ 방탈출 참여 및 본인확인</td>
											<td>이벤트 종료일로부터<br />
												3개월</td>
										</tr>
									</tbody>
								</table>
								<table class="pop_agree_table2 pc_hidden">
									<tr>
										<th>수집항목</th>
									</tr>
									<tr>
										<td>이름, 휴대전화번호, 예약일,<br />
											예약시간, 동반인원 수</td>
									</tr>
									<tr>
										<th>수집·이용 목적</th>
									</tr>
									<tr>
										<td>ThinQ 방탈출 참여 및 본인확인</td>
									</tr>
									<tr>
										<th>보유·이용기간</th>
									</tr>
									<tr>
										<td>이벤트 종료일로부터<br />
											3개월</td>
									</tr>
								</table>
								<p class="agree_bottom_txt">* 위의 개인정보 수집·이용에 대한 동의를 거부할 권리가 있습니다. <br class="pc_br" />
									그러나 동의를 거부할 경우 이벤트 참가에 제약사항이 발생할 수 있습니다.</p>
							</div>
						</div>
					</div>
					<div class="agree_chkbtn_wrap pc_view">
						<button onclick="location.href='./weekday.do'" class="btn_check"><span class="checkmark"></span><span>동의</span></button>
						<button onclick="popClose()" class="btn_check"><span class="checkmark"></span><span>미동의</span></button>
					</div>
					<div class="booking_wrap">
						<div class="booking_agree">
							<div class="agree_wrap">
								<h3>개인정보 취급업무 위탁 내역(고지사항)</h3>
								<table class="pop_agree_table2">
									<tr>
										<th class="w50per">위탁받는 자(수탁업체)</th>
										<th class="w50per">위탁 업무 내용</th>
									</tr>
									<tr>
										<td class="w50per">㈜LG CNS</td>
										<td class="w50per">LGE.COM 서버 운영 및 관리</td>
									</tr>
									<tr>
										<td class="w50per">㈜에이치에스애드</td>
										<td class="w50per">ThinQ 방탈출 예약 인원 확인 및 관리, 리워드 증정</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="mo_view btn_wrap w100per tac">
					<button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
					<button onclick="location.href='./weekday.do'" class="btn_pop_s btn_primary btn">동의합니다</button>
				</div>
				
			</div>
		</div>
		<div class="dim"></div>
	</section>
	<!-- popup end -->
	<!-- 2022-12-09 수정 : 팝업구조변경 -->
	<section id="pop_booking3" class="pop_booking pop_agree modal">
		<div class="popBg">
			<div class="popContent">
				<div class="popTitle_wrap">
					<h2>개인정보 수집·이용 및 제공에 대한 안내</h2>
					<button onClick="popClose()" class="popup_close"></button>
				</div>
				<div class="con_store">
					<div class="pop_agree_title">
						<p>LG전자(이 하 “회사”)는 이벤트 참가와 관련하여 아래와 같이 개인정보의 수집·이용하고자 합니다. <br />
							내용을 자세히 읽으신 후 동의 여부를 결정하여 주시기 </p>
					</div>
					<div class="booking_wrap">
						<div class="booking_agree">
							<div class="agree_wrap">
								<h3>개인정보의 수집 · 이용 동의서(필수)</h3>
								<table class="pop_agree_table2 mo_hidden">
									<thead>
										<tr>
											<th>수집항목</th>
											<th>수집·이용 목적</th>
											<th>보유·이용기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>이름, 휴대전화번호, 예약일,<br class="pc_br" />
												예약시간, 동반인원 수</td>
											<td>ThinQ 방탈출 참여 및 본인확인</td>
											<td>이벤트 종료일로부터<br />
												3개월</td>
										</tr>
									</tbody>
								</table>
								<table class="pop_agree_table2 pc_hidden">
									<tr>
										<th>수집항목</th>
									</tr>
									<tr>
										<td>이름, 휴대전화번호, 예약일,<br />
											예약시간, 동반인원 수</td>
									</tr>
									<tr>
										<th>수집·이용 목적</th>
									</tr>
									<tr>
										<td>ThinQ 방탈출 참여 및 본인확인</td>
									</tr>
									<tr>
										<th>보유·이용기간</th>
									</tr>
									<tr>
										<td>이벤트 종료일로부터<br />
											3개월</td>
									</tr>
								</table>
								<p class="agree_bottom_txt">* 위의 개인정보 수집·이용에 대한 동의를 거부할 권리가 있습니다. <br class="pc_br" />
									그러나 동의를 거부할 경우 이벤트 참가에 제약사항이 발생할 수 있습니다.</p>
							</div>
						</div>
					</div>
					<div class="agree_chkbtn_wrap pc_view">
						<button onclick="location.href='./weekend.do'" class="btn_check"><span class="checkmark"></span><span>동의</span></button>
						<button onclick="popClose()" class="btn_check"><span class="checkmark"></span><span>미동의</span></button>
					</div>
					<div class="booking_wrap">
						<div class="booking_agree">
							<div class="agree_wrap">
								<h3>개인정보 취급업무 위탁 내역(고지사항)</h3>
								<table class="pop_agree_table2">
									<tr>
										<th class="w50per">위탁받는 자(수탁업체)</th>
										<th class="w50per">위탁 업무 내용</th>
									</tr>
									<tr>
										<td class="w50per">㈜LG CNS</td>
										<td class="w50per">LGE.COM 서버 운영 및 관리</td>
									</tr>
									<tr>
										<td class="w50per">㈜에이치에스애드</td>
										<td class="w50per">ThinQ 방탈출 예약 인원 확인 및 관리, 리워드 증정</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="mo_view btn_wrap w100per tac">
					<button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
					<button onclick="location.href='./weekend.do'" class="btn_pop_s btn_primary btn">동의합니다</button>
				</div>
				
			</div>
		</div>
		<div class="dim"></div>
	</section>
    <!-- popup end -->
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>