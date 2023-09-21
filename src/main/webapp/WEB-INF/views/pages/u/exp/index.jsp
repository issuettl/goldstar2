<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/index.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_index_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-exp.jsp" />
        
        <section class="regi2023 exper_page sub_section">
            <figure></figure>
            <div class="m_container">
                <!-- 새로고침 공통 타이틀 -->
                <div class="exper_chatTitle">
                    <p class="tac">어서오시게! <br />
                        무엇을 고치려고 왔는가?</p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <div class="exper_indexText">
                    <h3 class="tac">고민을 입력한 후 제공된 QR을 찍으면<br />
                        재미난 솔루션과 다양한 선물을 받을 수 있습니다</h3>
                    <p class="text_caption tac">*단, LG전자 회원가입 후 체험 가능</p>
                </div>
                <div class="btn_full btn_wrap">
                    <c:choose>
                        <c:when test="${isMemberIn}">
                            <c:choose>
                                <c:when test="${isSignIn}">
                            <button onClick="document.location.href='<c:url value="/u/sign/name.do"/>'" class="large_btn_black btn_ex btn">오늘의 고민 접수 시작</button>
                                </c:when>
                                <c:otherwise>
                            <button onClick="popupOpenCookie('pop_booking3', '<c:url value="/u/sign/name.do"/>');" class="large_btn_black btn_ex btn">오늘의 고민 접수 시작</button>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <button onClick="document.location.href='<c:url value="/sso/account/in.do?state=/u/exp/index.do"/>'" class="large_btn_black btn_ex btn">로그인</button>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${isMemberIn}">
                            <c:choose>
                                <c:when test="${isSignIn}">
                            <button onClick="location.href='<c:url value="/u/exp/worry.do"/>'" class="btn_ex btn_pass btn">이미 고민을 선택했어요</button>
                                </c:when>
                                <c:otherwise>
                            <button onClick="popupOpenCookie('pop_booking4', '<c:url value="/u/exp/worry.do"/>');" class="btn_ex btn_pass btn">이미 고민을 선택했어요</button>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
        </section>
    </div>
    <div id="pop_agree">
        <!-- 본 페이지는 사전 문답 팝업에 들어가는 내용만 정리한 페이지입니다.
        인터랙션 스크립트는 팝업 내부의 이동 스크립트밖에 없습니다. -->
        <!-- 2022-11-03 신규 팝업 추가 : 사전문답 최초 참여시 추가되는 약관동의 화면 팝업이 추가되었습니다.
        어느 사전문답이든 최초 참여시에만 나타납니다. -->
        <!-- pop_booking2 popup start -->
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
                                                <td>이름, 휴대전화번호, 닉네임, 성별, 연령, <br class="pc_br" />
                                                현재 고민, 체험 완료 코너, 증정 받은 리워드</td>
                                                <td>금성전파사 새로고침센터 체험</td>
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
                                            <td>이름, 휴대전화번호, 닉네임, 성별, 연령, 현재 고민,<br />
                                            체험 완료 코너, 증정 받은 리워드</td>
                                        </tr>
                                        <tr>
                                            <th>수집·이용 목적</th>
                                        </tr>
                                        <tr>
                                            <td>금성전파사 새로고침센터 체험</td>
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
                            <button onclick="popCloseCookie('<c:url value="/u/sign/name.do"/>')" class="btn_check"><span class="checkmark"></span><span>동의</span></button>
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
                                            <td class="w50per">금성전파사 새로고침센터 플래폼 운영 및 관리, 리워드 증정</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mo_view btn_wrap w100per tac">
                        <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
                        <button onclick="popCloseCookie('<c:url value="/u/sign/name.do"/>')" class="btn_pop_s btn_primary btn">동의합니다</button>
                    </div>
                    
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_agree1">
        <!-- 본 페이지는 사전 문답 팝업에 들어가는 내용만 정리한 페이지입니다.
        인터랙션 스크립트는 팝업 내부의 이동 스크립트밖에 없습니다. -->
        <!-- 2022-11-03 신규 팝업 추가 : 사전문답 최초 참여시 추가되는 약관동의 화면 팝업이 추가되었습니다.
        어느 사전문답이든 최초 참여시에만 나타납니다. -->
        <!-- pop_booking2 popup start -->
        <section id="pop_booking4" class="pop_booking pop_agree modal">
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
                                                <td>이름, 휴대전화번호, 닉네임, 현재 고민,<br class="pc_br" />
                                                    체험 완료 코너, 증정 받은 리워드</td>
                                                <td>금성전파사 새로고침센터 체험</td>
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
                                            <td>이름, 휴대전화번호, 닉네임, 현재 고민,<br />
                                                체험 완료 코너, 증정 받은 리워드</td>
                                        </tr>
                                        <tr>
                                            <th>수집·이용 목적</th>
                                        </tr>
                                        <tr>
                                            <td>금성전파사 새로고침센터 체험</td>
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
                            <button onclick="popCloseCookie('<c:url value="/u/exp/worry.do"/>')" class="btn_check"><span class="checkmark"></span><span>동의</span></button>
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
                                            <td class="w50per">금성전파사 새로고침센터 플래폼 운영 및 관리, 리워드 증정</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mo_view btn_wrap w100per tac">
                        <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
                        <button onclick="popCloseCookie('<c:url value="/u/exp/worry.do"/>')" class="btn_pop_s btn_primary btn">동의합니다</button>
                    </div>
                    
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
