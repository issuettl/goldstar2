<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="about_corner_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-about.jsp" />
        <section class="sub_section">
            <div class="container">
                <h2 class="hiddenText">금성전파사 내 코너</h2>
                <ul class="about_corner_list">
                    <li>
                        <a href="javascript:popupOpen('pop_store1');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_01.png"/>" alt="">
                            <span class="corner_text">
                                <h3 class="corner_title">마음고침 코-너</h3>
                                <p>이곳은 지친 마음이 <br class="mo_br" />
                                    활기 넘치게 고쳐지는 곳입니다. <br />
                                    이곳에선 나만의 반려식물을 선물처럼 <br class="mo_br" />
                                    데려갈 수 있습니다.</p>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:popupOpen('pop_store2');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_02.png"/>" alt="">
                            <span class="corner_text">
                                <h3 class="corner_title">스타일고침 코-너</h3>
                                <p>이곳은 나의 스타일을 원하는 대로 <br class="mo_br" />새롭게 고칠 수 있는 곳입니다. <br />
                                    이곳에서 신발부터 겉옷까지 상상하던 <br class="mo_br" />스타일로 완성해보실 수 있습니다.</p>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:popupOpen('pop_store3');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_03.png"/>" alt="">
                            <span class="corner_text">
                                <h3 class="corner_title">개성고침 코-너</h3>
                                <p>이곳은 노트북을 <br class="mo_br" />자신의 취향대로 리폼할 수 있는 곳입니다. <br />
                                    늘 들고 다니던 평범한 노트북은 이곳에서 <br class="mo_br" />나만의 개성 넘치는 노트북으로 변신합니다.</p>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:popupOpen('pop_store4');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_04.png"/>" alt="">
                            <span class="corner_text">
                                <h3 class="corner_title">기분고침 코-너 (금성오락실)</h3>
                                <p>이곳은 평범한 하루가 새롭게 느껴지도록 <br class="mo_br" />
                                    기분을 고쳐주는 곳입니다. <br />
                                    이곳에서는 누구나 주인공이 되어 게임을 하며 <br class="mo_br" />
                                    특별한 하루를 체험할 수 있습니다.<br />
                                    </p>
                            </span>
                        </a>
                    </li>
                    <li>
                        <!-- 2022-11-30 수정 : 퍼블 파일안에서 잘못 작성되어서 수정했습니다 -->
                        <a href="javascript:popupOpen('pop_store5');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_05.png"/>" alt="">
                            <span class="corner_text">
                                <!-- 2022-11-11 수정 : 방탈출 코너 이름 일상고침 코-너 -> 고민탈출 코-너 -->
                                <h3 class="corner_title">고민탈출 코-너 (ThinQ 방탈출)</h3>
                                <p>이곳은 지루한 일상 속 <br class="mo_br" />
                                    우울한 기분을 고쳐주는 곳으로,<br />
                                    ThinQ 방탈출을 통해 스트레스를 <br class="mo_br" />
                                    날려버리실 수 있습니다.</p>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:popupOpen('pop_store6');" class="cornerlink wrapped_flex">
                            <img src="<c:url value="/u/images/sub/img_corner_06.png"/>" alt="">
                            <span class="corner_text">
                                <h3 class="corner_title">새로고침 코-너</h3>
                                <p>이곳은 새로 고침이 끝나는 곳입니다.<br />
                                    이곳에서는 당신의 고민을 새로 고쳐줄 맞춤 <br class="mo_br" />
                                    영상을 시청하고 마음까지 힐링할 수 있습니다.<br />
                                    </p>
                            </span>
                        </a>
                    </li>
                </ul>
                <div class="btn_full tac btn_wrap"><button onclick="location.href='<c:url value="/u/exp/index.do"/>'" class="btn_round_ml btn_black btn">체험하러 가기</button></div>
                
            </div>
        </section>
        <div id="pop_store01">
            <!-- pop_store1 popup start -->
    <section id="pop_store1" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>마음고침 코-너</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                <div class="con_img">
                    <img src="<c:url value="/u/images/popup/corenr_popup_pc1.png"/>" alt="">
                </div>
                <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                <div class="con_store">
                    <div class="con_store_top">
                        <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                        <img src="<c:url value="/u/images/popup/img_pop_strore1_1.png"/>" alt="">
                        <h4 class="con_store_title">소개</h4>
                        
                        <p>예쁜 식물들을 보며 여유롭게 힐링하는 공간<br />
                            내 마음을 치유할 수 있는 나만의 식물을 추천받고, <br class="mo_br" />
                            직접 심어볼 수도 있답니다
                        </p>
                    </div>
                    <div class="con_store_bottom">
                        <h4 class="con_store_title">위치</h4>
                        <div class="con_store_imgs">
                            
                            <h5 class="store_floor">3F</h5>
                            <img src="<c:url value="/u/images/popup/img_pop_strore1_2.png"/>" alt="">
                        </div>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn pc_view">닫기</button>
                    <button onclick="popClose()" class="btn_pop_full btn_primary btn mo_view">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
        </div>
        <div id="pop_store02">
            <!-- pop_store2 popup start -->
    <section id="pop_store2" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>스타일고침 코-너</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                <div class="con_img">
                    <img src="<c:url value="/u/images/popup/corenr_popup_pc2.png"/>" alt="">
                </div>
                <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                <div class="con_store">
                    <div class="con_store_top">
                        <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                        <img src="<c:url value="/u/images/popup/img_pop_strore3_1.png"/>" alt="">
                        <h4 class="con_store_title">소개</h4>
                        <p>나만의 스타일로 새롭게 창조하는 공간<br />
                            다양한 소품으로 패션을 리폼하고, <br class="mo_br" />
                            리사이클링 팬던트로 DIY 팔찌도 만들 수 있답니다 
                            </p>
                    </div>
                    <div class="con_store_bottom">
                        <h4 class="con_store_title">위치</h4>
                        <div class="con_store_imgs">
                            <h5 class="store_floor">3F</h5>
                            <img src="<c:url value="/u/images/popup/img_pop_strore3_2.png"/>" alt="">
                        </div>
                    </div>
                </div>
                <!-- 2022-10-24 버튼 변경 -> 모바일에서만 닫기버튼으로 노출 -->
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn pc_view">닫기</button>
                    <button onclick="popClose()" class="btn_pop_full btn_primary btn mo_view">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
        </div>
        <div id="pop_store03">
            <!-- pop_store3 popup start -->
    <section id="pop_store3" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>개성고침 코-너</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                <div class="con_img">
                    <img src="<c:url value="/u/images/popup/corenr_popup_pc3.png"/>" alt="">
                </div>
                <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                <div class="con_store">
                    <div class="con_store_top">
                        <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                        <img src="<c:url value="/u/images/popup/img_pop_strore2_1.png"/>" alt="">
                        <h4 class="con_store_title">소개</h4>
                        <p>힙한 스티커로 나만의 개성을 뽐내는 공간 <br />
                            내 노트북을 멋지게 꾸며도 보고, <br class="mo_br" />
                            리사이클링 굿즈도 만날 볼 수 있답니다 
                            </p>
                    </div>
                    <div class="con_store_bottom">
                        <h4 class="con_store_title">위치</h4>
                        <div class="con_store_imgs">
                            <h5 class="store_floor">3F</h5>
                            <img src="<c:url value="/u/images/popup/img_pop_strore2_2.png"/>" alt="">
                        </div>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn pc_view">닫기</button>
                    <button onclick="popClose()" class="btn_pop_full btn_primary btn mo_view">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
        </div>
        <div id="pop_store04">
            <!-- pop_store4 popup start -->
    <section id="pop_store4" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>기분고침 코-너 (금성오락실)</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                <div class="con_img">
                <img src="<c:url value="/u/images/popup/corenr_popup_pc4.png"/>" alt="">
                </div>
                <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                <div class="con_store">
                    <div class="con_store_top">
                        <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                        <img src="<c:url value="/u/images/popup/img_pop_strore4_1.png"/>" alt="">
                        <h4 class="con_store_title">소개</h4>
                        
                        <p>오락실 게임을 즐기며 기분전환하는 공간<br />
                            어릴 적 추억의 게임을 LG OLED의 크고 <br class="mo_br" />
                            생생한 화면으로 즐길 수 있답니다
                            </p>
                    </div>
                    <div class="con_store_bottom">
                        <h4 class="con_store_title">위치</h4>
                        <div class="con_store_imgs">
                            <h5 class="store_floor">3F</h5>
                            <img src="<c:url value="/u/images/popup/img_pop_strore4_2.png"/>" alt="">
                        </div>
                    </div>
                </div>
                <!-- 2022-10-24 버튼 변경 -> 모바일에서만 닫기버튼으로 노출 -->
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn pc_view">닫기</button>
                    <button onclick="popClose()" class="btn_pop_full btn_primary btn mo_view">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
        </div>
        <!-- pop_store5 popup start -->
        <div id="pop_store05">
            <section id="pop_store5" class="pop_store modal">
                <div class="popBg">
                    <div class="popContent">
                        <div class="popTitle_wrap">
                            <!-- 2022-11-11 수정 : 방탈출 코너 이름 일상고침 코-너 -> 고민탈출 코-너 -->
                            <h2>고민탈출 코-너 (ThinQ 방탈출)</h2>
                            <button onClick="popClose()" class="popup_close"></button>
                        </div>
                        <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                        <div class="con_img">
                            <img src="<c:url value="/u/images/popup/corenr_popup_pc5.png"/>" alt="">
                        </div>
                        <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                        <div class="con_store">
                            <div class="con_store_top">
                                <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                                <img src="<c:url value="/u/images/popup/img_pop_strore5_1.png"/>" alt="">
                                
                                <p>방탈출 게임으로 지루한 일상을 벗어나는 공간<br />
                                    사전예약 후 ThinQ앱을 활용하여 <br class="mo_br" />
                                    흥미진진한 방탈출 게임을 즐길 수 있답니다
                                    </p>
                            </div>
                            <div class="con_store_bottom">
                                <h4 class="con_store_title">위치</h4>
                                <div class="con_store_imgs">
                                    <h5 class="store_floor">3F</h5>
                                    <img src="<c:url value="/u/images/popup/img_pop_strore5_2.png"/>" alt="">
                                </div>
                            </div>
                        </div>
                        <div class="btn_wrap w100per tac">
                            <!-- 2022-11-21 수정 : 방탈출 앞단계 페이지 추가 -->
                            <button onclick="location.href='<c:url value="/u/life/about.do"/>'" class="btn_pop_s btn_primary btn pc_view">ThinQ 방탈출 예약하기</button>
                            <button onclick="location.href='<c:url value="/u/life/about.do"/>'" class="btn_pop_full btn_primary btn mo_view">ThinQ 방탈출 예약하기</button>
                        </div>
                    </div>
                </div>
                <div class="dim"></div>
            </section>
        </div>
        
        <!-- popup end -->
        <div id="pop_store06">
            <!-- pop_store6 popup start -->
    <section id="pop_store6" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>새로고침 코-너</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 2022-12-14 수정 : 팝업 구조 변경 -->
                <div class="con_img">
                    <img src="<c:url value="/u/images/popup/corenr_popup_pc6.png"/>" alt="">
                </div>
                <!-- 2022-12-14 수정 : con_store에 mo_view class 추가 -->
                <div class="con_store">
                    <div class="con_store_top">
                        <!-- 2022-12-14 수정 : 준비중 이미지 -> 실제 사이트 이미지로 변경 -->
                        <img src="<c:url value="/u/images/popup/img_pop_strore6_1.png"/>" alt="">
                        <h4 class="con_store_title">소개</h4>
                        
                        <p>나만의 맞춤 솔루션 영상을 확인하는 공간<br />
                            나의 고민을 들어준 금성아저씨의 <br class="mo_br" />
                            특별한 선물을 만날 수 있답니다
                        </p>
                    </div>
                    <div class="con_store_bottom">
                        <h4 class="con_store_title">위치</h4>
                        <div class="con_store_imgs">
                            
                            <h5 class="store_floor">3F</h5>
                            <img src="<c:url value="/u/images/popup/img_pop_strore6_2.png"/>" alt="">
                        </div>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn pc_view">닫기</button>
                    <button onclick="popClose()" class="btn_pop_full btn_primary btn mo_view">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
        </div>
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
