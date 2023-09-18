<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/corner.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_recommend_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-exp.jsp" />
        
        <section class="exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 -->
                <div class="exper_chatTitle">
                    <p class="tac">‘<c:out value="${sign.worryType.desc}"/>’을 가진<br />
                        <c:out value="${sign.nickDec}"/>의 새로고침 솔루션</p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <!-- 코너 추천 리스트 -->
                <div class="exper_recommend">
                    <ul class="sub_text_nav">
                        <li class="right"><a href="<c:url value="/u/exp/history.do"/>" class="text_navlink_right">지난 체험 이력</a></li>
                    </ul>
                    <c:if test="${empty mind and empty indiv and empty style and empty mood}">
                    <input type="hidden" id="agreement_show" value="N"/>
                    </c:if>
                    <div class="recommend_row">
                        <c:forEach items="${sign.worryType.recommend}" var="corner">
                            <c:choose>
                                <c:when test="${corner.name eq 'mind'}">
                                    <button onClick="javascript:agreement('pop_store1', this);" class="<c:if test="${not empty mind and mind.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mind and mind.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty mind and mind.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mind and mind.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_01.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>마음고침 코-너</h3>
                                            <p>LG 틔운을 통해<br class="" />
                                            마음을 치유하는 공간</p>
                                        </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'indiv'}">
                                    <button onClick="javascript:agreement('pop_store3', this);" class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_03.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>개성고침 코-너</h3>
                                            <p>LG 그램을 내 개성대로<br class="" />
                                            리폼하는 공간</p>
                                        </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'style'}">
                                    <button onClick="javascript:agreement('pop_store2', this);" class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_02.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>스타일고침 코-너</h3>
                                            <p>LG 스타일러로 깨끗하게 가꾸고<br />
                                                나만의 아이템도 만드는 공간</p>
                                        </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'mood'}">
                                    <button onClick="javascript:agreement('pop_store4', this);" class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
                                    <!-- 팝업 확인을 위해 botton 기능을 그대로 두었고, 체험완료됬을 경우 button과 figure에 disabled_complete 추가됩니다. -->
                                    <figure class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_04.png"/>" alt=""></figure>
                                    <span class="texts">
                                        <h3>기분고침 코-너</h3>
                                        <p>게임으로 스트레스를<br class="" />
                                        날려버리는 공간</p>
                                    </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'life'}">
                                    <button onClick="javascript:agreement('pop_store5', this);" class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>w30per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_05.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>고민탈출 코-너</h3>
                                            <p>ThinQ와 함께 즐기는 <br />방탈출
                                액티비티가 제공되는 공간</p>
                                        </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'refresh'}">
                                    <button onClick="javascript:agreement('pop_store6', this);" class="<c:if test="${not empty refresh and refresh.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty refresh and refresh.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty refresh and refresh.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty refresh and refresh.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_06.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>새로고침 코-너</h3>
                                            <p>나만의 힐링 장소를<br class="" /> 몰입감 있게
                                            경험하는 공간</p>
                                        </span>
                                        <span class="wrapped_ab">
                                            <span class="rectag tag">추천!</span>
                                        </span>
                                    </button>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <div class="recommend_row">
                        <c:forEach items="${sign.worryType.normal}" var="corner">
                            <c:choose>
                                <c:when test="${corner.name eq 'mind'}">
                                    <button onClick="javascript:agreement('pop_store1', this);" class="<c:if test="${not empty mind and mind.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mind and mind.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty mind and mind.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mind and mind.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_01.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>마음고침 코-너</h3>
                                            <p>LG 틔운을 통해<br class="" />
                                            마음을 치유하는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'indiv'}">
                                    <button onClick="javascript:agreement('pop_store3', this);" class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_03.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>개성고침 코-너</h3>
                                            <p>LG 그램을 내 개성대로<br class="" />
                                            리폼하는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'style'}">
                                    <button onClick="javascript:agreement('pop_store2', this);" class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_02.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>스타일고침 코-너</h3>
                                            <p>LG 스타일러로 깨끗하게 가꾸고<br />
                                                나만의 아이템도 만드는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'mood'}">
                                    <button onClick="javascript:agreement('pop_store4', this);" class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                                        <!-- 팝업 확인을 위해 botton 기능을 그대로 두었고, 체험완료됬을 경우 button과 figure에 disabled_complete 추가됩니다. -->
                                        <figure class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_04.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>기분고침 코-너</h3>
                                            <p>게임으로 스트레스를<br class="" />
                                            날려버리는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'life'}">
                                    <button onClick="javascript:agreement('pop_store5', this);" class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>w20per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_05.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>고민탈출 코-너</h3>
                                            <p>ThinQ와 함께 즐기는 <br />방탈출
                                액티비티가 제공되는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                                <c:when test="${corner.name eq 'refresh'}">
                                    <button onClick="javascript:agreement('pop_store6', this);" class="<c:if test="${not empty refresh and refresh.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty refresh and refresh.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                                        <figure class="<c:if test="${not empty refresh and refresh.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty refresh and refresh.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_06.png"/>" alt=""></figure>
                                        <span class="texts">
                                            <h3>새로고침 코-너</h3>
                                            <p>나만의 힐링 장소를<br class="" /> 몰입감 있게
                                            경험하는 공간</p>
                                        </span>
                                    </button>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <div class="w50per tac btn_wrap">
                        <button onclick="location.href='<c:url value="/u/exp/gift.do"/>'" class="large_btn_black btn_round_m btn">선물함</button>
                        <c:choose>
                            <c:when test="${isPart}">
                            <button onclick="location.href='<c:url value="/u/exp/cert.do"/>'" class="large_btn_line btn_round_m btn_line btn">새로고침 보증서</button>
                            </c:when>
                            <c:otherwise>
                            <button onclick="$('#pop_cert').show();" class="btn_round_m btn_line btn">새로고침 보증서</button>
                            </c:otherwise>
                        </c:choose>
                        
                        <button onclick="location.href='<c:url value="/u/exp/history.do"/>'" class="mo_view btn_ex btn_pass btn">지난 체험 이력</button>
                    </div>
                </div>
                <!-- 코너 추천 리스트 end -->
            </div>
            <button onClick="javascript:popupOpen('pop_qr_page');" class="qr_btn"><img src="<c:url value="/u/images/sub/img_textqr.png"/>" alt=""></button>
        </section>
    </div>
    
    <div id="mind_question"></div>
    <div id="style_question"></div>
    <div id="indiv_question"></div>
    <div id="mood_question"></div>
    <div id="pop_qr"></div>
    
    <section id="pop_cert" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>보증서는 코-너 체험 후 확인하실 수 있습니다.</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">확인</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    
    <div id="pop_store01_rec">
        <!-- 2022-10-26 이미지 경로 문제로 6개 페이지 다시 제작했습니다ㅠ -->
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
                    <!-- 2022-10-26 버튼변경 -->
                    <div class="btn_wrap w100per tac">
                        <button onclick="javascript:$('.pop_store').fadeOut('.pop_store');popupOpen2('mindQuestion');" class="btn_pop_s btn_primary btn">사전 문답</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_store02_rec">
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
                    <!-- 2022-10-26 버튼변경 -->
                    <div class="btn_wrap w100per tac">
                        <button onclick="javascript:$('.pop_store').fadeOut('.pop_store');popupOpen2('styleQuestion');" class="btn_pop_s btn_primary btn">사전 문답</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_store03_rec">
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
                    <!-- 2022-10-26 버튼변경 -->
                    <div class="btn_wrap w100per tac">
                        <button onclick="javascript:$('.pop_store').fadeOut('.pop_store');popupOpen2('indivQuestion');" class="btn_pop_s btn_primary btn">사전 문답</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_store04_rec">
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
                    <!-- 2022-10-26 버튼변경 -->
                    <div class="btn_wrap w100per tac">
                        <button onclick="javascript:$('.pop_store').fadeOut('.pop_store');popupOpen2('moodQuestion');" class="btn_pop_s btn_primary btn">사전 문답</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_store05_rec">
        <!-- pop_store5 popup start -->
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
                            <h4 class="con_store_title">소개</h4>
                            
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
        <!-- popup end -->
    </div>
    <div id="pop_store06_rec">
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
                        <button onclick="javascript:$('.pop_store').fadeOut('.pop_store');popupOpen('pop_qr_page');" class="btn_pop_s btn_primary btn">체험 인증 QR 보기</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <div id="pop_agree">
        <!-- 본 페이지는 사전 문답 팝업에 들어가는 내용만 정리한 페이지입니다.
        인터랙션 스크립트는 팝업 내부의 이동 스크립트밖에 없습니다. -->
        <!-- 2022-11-03 신규 팝업 추가 : 사전문답 최초 참여시 추가되는 약관동의 화면 팝업이 추가되었습니다.
        어느 사전문답이든 최초 참여시에만 나타납니다. -->
        <!-- pop_booking2 popup start -->
        <section id="pop_booking2" class="pop_agree pop_mini modal">
            <div class="popBg">
                <div class="popContent">
                    <div class="popTitle_wrap">
                        <h2>사전문답 참여하기</h2>
                        <button onClick="popClose()" class="popup_close"></button>
                    </div>
                    <div class="con_store">
                        <div class="pop_agree_title">
                            <h3>개인정보 수집 이용 동의</h3>
                            <p>귀하께서는 본 동의를 거절하실 수 있습니다. 단, 거절하신 경우 참여가 제한될 수 있습니다.</p>
                        </div>
                        <div class="booking_wrap">
                            <div class="booking_agree">
                                <ul class="pop_agree_table pc_view">
                                    <li class="title">
                                        <span>목적</span>
                                        <span>항목</span>
                                        <span>보유기간</span>
                                    </li>
                                    <li class="content">
                                        <span>제품관련 문답 <br />
                                            데이터 수집</span>
                                        <span>문답 결과 값</span>
                                        <span>5년</span>
                                    </li>
                                </ul>
                                <ul class="pop_agree_table mo_view">
                                    <li class="title">
                                        <span>목적</span>
                                    </li>
                                    <li class="content">
                                        <span>제품관련 문답 
                                            데이터 수집</span>
                                    </li>
                                    <li class="title">
                                        <span>항목</span>
                                    </li>
                                    <li class="content">
                                        <span>문답 결과 값</span>
                                    </li>
                                    <li class="title">
                                        <span>보유기간</span>
                                    </li>
                                    <li class="content">
                                        <span>5년</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="btn_wrap w100per tac">
                        <!-- 2022-11-28 수정 : 전부 동의하기 전에는 회색 버튼으로 노출 -->
                        <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">동의하지 않습니다</button>
                        <button id="btn_agree_ok" onclick="javascript:$('.pop_store').fadeOut('.pop_store');" class="btn_agree_ok btn_pop_s btn_primary btn">동의합니다</button>
                    </div>
                </div>
            </div>
            <div class="dim"></div>
        </section>
        <!-- popup end -->
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <!-- script start -->
    <script>
        // 코너 팝업 불러오기 
        importPage2('question', 'mind'); //마음고침
        importPage2('question', 'style'); //스타일
        importPage2('question', 'indiv'); //개성고침
        importPage2('question', 'mood'); //기분고침
        importPage3('pop_qr', 'exp/qr/page');
    </script>
</body>
</html>
