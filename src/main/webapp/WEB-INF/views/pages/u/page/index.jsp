<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="index_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <main class="">
        <!-- slider_section 슬라이더 -->
        <section class="slider_section">
            <div class="container_full">
                <div class="main_slider">
                    <!-- 슬라이드 1장 -->
                    <div class="slide01 slide">
                        <div class="slide_img">
                            <div class="container">
                                <img src="<c:url value="/u/images/main/img_visual_person_pc.png"/>" class="img_center t_center" alt="">
                                <img src="<c:url value="/u/images/main/img_visual_text_pc.png"/>" class="img_text" alt="">
                            </div>
                        </div>
                        <!-- 2022-12-02 수정 : 영상 파일 수정 -->
                        <div class="slide_video">
                            <video width="100%" height="100%" src="<c:url value="/u/videos/pc_promotion.mp4"/>"
                            type="video/webm" type="video/mp4" playsinline="">
                            </video>
                        </div>
                        <div class="video_button_wrap">
                            <div class="container">
                                <button class="video_button slider_play" tabindex=""></button>
                            </div>
                        </div>
                    </div>
                    <!-- 슬라이드 1장 end -->
                </div>
            </div>
        </section>
        <!-- //slider_section end -->
         <section class="mobileTop_section">
            <div class="container">
                <div class="wrapped_flex">
                    <div class="top">
                        <img src="<c:url value="/u/images/common/img_logo.svg"/>" alt="금성전파사 새로고침센터" class="img_logo">
                        <!-- 2022-12-06 수정 : 디자인 변경으로 퍼블 변경 -->
                        <img src="<c:url value="/u/images/main/img_visual_text_mo.png"/>" alt="">
                    </div>
                    <div class="bottom">
                        <!-- 2022-12-02 수정 : 유튜브링크 추가 -->
                        <a class="mobile_youtube" href="javascript:$('#pop_mainyoutube').show();"><img src="<c:url value="/u/images/main/slide_brand_flim.svg"/>" alt=""></a>
                        <h2 class="mainTitle tac">간단한 고민 접수로 <br class="mo_br" />맞춤형 체험을 추천 받으세요</h2>
                        <p class="mainTitle_sub tac">*해당 체험은 경동시장에서 오프라인 참여만 가능합니다.</p>
                        <div class="btn_wrap tac">
                            <a href="<c:url value="/u/exp/index.do"/>" class="btn btn_round_l btn_line">고민접수 하러 가기</a>
                        </div>
                        <a href="javascript:javascript:popTuto();" class="mainTitle_sub_link tac">금성전파사 새로고침센터가 처음이시라면?</a>
                    </div>
                </div>
            </div>
        </section>
        <!-- mainMenu_section -->
        <section class="mainMenu_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
                <ul class="icon_menu">
                    <li class="icon01">
                        <a href="javascript:popupOpen('pop_store1');">
                            <figure></figure>
                            <span>마음고침 코-너</span>
                        </a>
                    </li>
                    
                    <li class="icon02">
                        <a href="javascript:popupOpen('pop_store2');">
                            <figure></figure>
                            <span>스타일고침 코-너</span>
                        </a>
                    </li>
                    
                    <li class="icon03">
                        <a href="javascript:popupOpen('pop_store3');">
                            <figure></figure>
                            <span>개성고침 코-너</span>
                        </a>
                    </li>
                    <li class="icon04">
                        <a href="javascript:popupOpen('pop_store4');">
                            <figure></figure>
                            <span>기분고침 코-너<br />(금성오락실)</span>
                        </a>
                    </li>
                    <li class="icon05">
                        <a href="javascript:popupOpen('pop_store5');">
                            <figure></figure>
                            <span>고민탈출 코-너<br />(ThinQ 방탈출)</span>
                        </a>
                    </li>
                    <li class="icon06">
                        <a href="javascript:popupOpen('pop_store6');">
                            <figure></figure>
                            <span>새로고침 코-너</span>
                        </a>
                    </li>
                </ul>
            </div>
        </section>
        <!-- //mainMenu_section end -->
        <!-- main_bg (메인 배경을 위한 div) -->
        <div class="main_bg">
            <!-- mainEx_section -->
            <section class="mainEx_section">
                <div class="container">
                    <h2 class="mainTitle tac">간단한 고민 접수로 <br class="mo_br" />
                        맞춤형 체험을 추천 받으세요</h2>
                        
                    <p class="mainTitle_sub tac">* 해당 체험은 경동시장에서 오프라인 참여만 가능합니다.</p>
                    <div class="btn_wrap tac">
                        <a href="<c:url value="/u/exp/index.do"/>" class="btn btn_round_l btn_line">고민접수 하러 가기</a>
                    </div>
                    <a href="javascript:popTuto();" class="mainTitle_sub_link tac">금성전파사 새로고침센터가 처음이시라면?</a>
                </div>
            </section>
            <!-- //mainEx_section end -->
            <!-- mainEvent_section -->
            <section class="mainEvent_section">
                <div class="container">
                    <div class="mainTitle_wrap">
                        <h2 class="mainTitle">이벤트</h2>
                        <p class="mainTitle_sub">금성전파사 새로고침센터의 별난 이벤트를 만나보세요</p>
                        <button onclick="location.href='<c:url value="/u/event/list.do"/>'" class="title_more">더보기</button>
                    </div>
                    <ul class="mainEvent_list">
                        <!-- 2022-12-06 수정 : 이벤트 기본 내용 변경 -->
                        <c:forEach items="${events}" var="item">
                        <li>
                            <a href="<c:url value="/u/event/view/${item.sn}.do"/>" class="mainEvent_item">
                                <figure style="background-image: url('<c:url value="/u/event/file/thumb/${item.sn}.do"/>');"></figure>
                                <div class="mainEvent_content">
                                    <h3><c:out value="${item.subject}"/></h3>
                                    <fmt:parseDate var="startDate" value="${item.startDate}" pattern="yyyyMMdd"/>
                                    <fmt:parseDate var="endDate" value="${item.endDate}" pattern="yyyyMMdd"/>
                                    <p><span class="date"><fmt:formatDate value="${startDate}" pattern="yyyy.MM.dd"/></span><span class="dateline">~</span><span class="date"><fmt:formatDate value="${endDate}" pattern="yyyy.MM.dd"/></span></p>
                                </div>
                                
                            </a>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
            </section>
            <!-- //mainEvent_section end -->
            <!-- mainEscape_section -->
            <section class="mainEscape_section">
                <div class="container wrapped_flex">
                    <div class="left">
                        <div class="mainTitle_wrap">
                            <h2 class="mainTitle">고민탈출 코-너 <br class="mo_br" />(powered by ThinQ 방탈출)</h2>
                            <p class="mainTitle_sub">금성전파사 새로고침센터에서 <br class="mo_br" />ThinQ를 활용한 이색 방탈출을 즐겨보세요. </p>
                        </div>
                        <!-- pc 노출 배너 -->
                        <a href="<c:url value="/u/life/about.do"/>" class="pc_view w100per"><img src="<c:url value="/u/images/main/imgtxt_escape_left.png"/>" alt="금성전파사 새로고침센터 방탈출 예약하기" class=""></a>
                        <!-- mobile 노출 배너 -->
                        <a href="<c:url value="/u/life/about.do"/>" class="mo_view"><img src="<c:url value="/u/images/main/imgtxt_escape_left_mo1.png"/>" alt="금성전파사 새로고침센터 방탈출 예약하기" class=""></a>
                    </div>
                    <div class="right mo_view">
                        <img src="<c:url value="/u/images/main/img_escape_right.png"/>" alt="" class="">
                    </div>
                </div>
            </section>
            <!-- //mainEscape_section end -->
            <!-- mainsns_section -->
            <!-- section class="mainsns_section">
                <div class="container">
                    <div class="mainTitle_wrap">
                        <h2 class="mainTitle">금성전파사 새로고침센터 후기</h2>
                        <div class="mainTitle_sub">
                            <div class="hashtag_wrap">
                                <span class="hashtag">#금성전파사새로고침센터</span><span class="hashtag">#경동시장</span><span class="hashtag">#제기동핫플</span><span class="hashtag">#고민고침</span><span class="hashtag">#LG전자</span>
                            </div>
                            <button class="btn btn_round_s btn_line btn_hashtag" onclick="hashtag();">해시태그 복사하기</button>
                            <input type="text" style="display:none;" id="clipboardCopy" value="#금성전파사새로고침센터 #경동시장 #제기동핫플 #고민고침 #LG전자"/>
                        </div>
                    </div>
                    <ul class="sns_list">
                        <li>
                            <figure style="background-image: url('<c:url value="/u/images/main/img_sns_01.png"/>');"></figure>
                            <h3 class="hiddenText">인스타그램 게시물</h3>
                        </li>
                        <li>
                            <figure style="background-image: url('<c:url value="/u/images/main/img_sns_02.png"/>');"></figure>
                            <h3 class="hiddenText">인스타그램 게시물</h3>
                        </li>
                        <li>
                            <figure style="background-image: url('<c:url value="/u/images/main/img_sns_03.png"/>');"></figure>
                            <h3 class="hiddenText">인스타그램 게시물</h3>
                        </li>
                        <li>
                            <figure style="background-image: url('<c:url value="/u/images/main/img_sns_04.png"/>');"></figure>
                            <h3 class="hiddenText">인스타그램 게시물</h3>
                        </li>
                    </ul>
                    <div id="popupToast1" class="popupToast_wrap">
                        <div class="popupToast">
                            <p class="popupToast_text">해시태그가 복사되었습니다.</p>
                            <button onClick="document.getElementById('popupToast1').remove()" class="popupToast_close"></button>
                        </div>
                    </div>
                </div>
            </section -->
        </div>
        <!-- //main_bg end -->
    </main>
    <!--  div class="bottom_btns"><button onclick="location.href=''" class="insta_btn" alt="인스타그램으로 바로가기"><img src="<c:url value="/u/images/common/btn_instagram.svg"/>" alt=""></button></div -->

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
    <div id="pop_store05">
        <!-- pop_store5 popup start -->
<section id="pop_store5" class="pop_store modal">
    <div class="popBg">
        <div class="popContent">
            <div class="popTitle_wrap">
                <!-- 2022-12-02 수정 : 오타 수정 -->
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
                <button onclick="location.href='<c:url value="/u/life/about.do"/>'" class="btn_pop_s btn_primary btn pc_view">ThinQ 방탈출 예약하기</button>
                <button onclick="location.href='<c:url value="/u/life/about.do"/>'" class="btn_pop_full btn_primary btn mo_view">ThinQ 방탈출 예약하기</button>
            </div>
        </div>
    </div>
    <div class="dim"></div>
</section>
<!-- popup end -->
    </div>
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
            <!-- 2022-12-14 수정 : end -->
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
    
    <!-- pop_tutorial popup start -->
    <section id="pop_tutorial" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>금성전파사 새로고침센터 튜토리얼</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <div class="con_store">
                    <div class="tuto_slide">
                        <div class="slide1 slide">
                            <div class="con_img"><img src="<c:url value="/u/images/popup/pop_tutorial_pc1.png"/>" alt=""></div>
                            <div class="mo_view">
                                <img src="<c:url value="/u/images/main/img_tutorial1_1_mo.png"/>" alt="" class="mo_img">
                                <p class="tuto_subtext">자네를 위해 내가 금성전파사 새로고침센터를 <br class="mo_br" />간단하게 설명해도 괜찮겠나?</p>
                            </div>
                        </div>
                        <div class="slide2 slide">
                            <!-- 새로고침 공통 타이틀 변형 -->
                            <div class="tal exper_chatTitle">
                                <i class="exper_icn"></i>
                                <p class="exper_icnText">우선, 체험은 금성전파사 새로고침센터에서만 참여할 수 있다네!<br />
                                    나를 만나고 싶다면, 아래의 장소로 오시게나</p>
                            </div>
                            <!-- 2022-12-26 con_img 전면 삭제, 구조 변경 -->
                            <div class="slide2_con">
                                <h2 class="tuto_title">금성전파사 새로고침센터</h2>
                                <div class="slide2_map">
                                    <img src="<c:url value="/u/images/main/img_tutorial2_1_mo.png"/>" alt="" class="">
                                </div>
                                <p class="tuto_subtext">위치: 서울특별시 동대문구 고산자로36길 3 <br class="mo_br" />경동시장 본관 3, 4층<br />
                                [지번] 서울특별시 동대문구 제기동 1019</p>
                            </div>
                            <!-- 2022-12-26 con_img 전면 삭제, 구조 변경 end -->
                        </div>
                        <div class="slide3 slide">
                            <!-- 새로고침 공통 타이틀 변형 -->
                            <div class="tal exper_chatTitle">
                                <i class="exper_icn"></i>
                                <p class="exper_icnText">금성전파사는 고민유형에 따라 <br class="mo_br" />다양한 코-너의 체험을 제공한다네.
                                    <br />
                                    자네의 고민은 무엇일지 궁금하군..</p>
                            </div>
                            <div class="con_img pc_view"><img src="<c:url value="/u/images/popup/pop_tutorial_pc3.png"/>" alt=""></div>
                            <div class="con_img mo_view"><img src="<c:url value="/u/images/popup/pop_tutorial_mo3.png"/>" alt=""></div>
                        </div>
                        <div class="slide4 slide">
                            <div class="tal exper_chatTitle">
                                <i class="exper_icn"></i>
                                <p class="exper_icnText">청춘들의 마음 치유를 위해 <br class="mo_br" />특별한 선물도 함께 준비했다네.
                                    <br />
                                    잊지 말고 챙겨가시게나!</p>
                            </div>
                            <div class="con_img pc_view"><img src="<c:url value="/u/images/popup/pop_tutorial_pc4.png"/>" alt=""></div>
                            <div class="con_img mo_view"><img src="<c:url value="/u/images/popup/pop_tutorial_mo4.png"/>" alt=""></div>
                        </div>
                    </div>
                    <div class="tuto_slide_btns">
                        <button id="tuto_prev">이전</button>
                        <button id="tuto_next">다음</button>
                    </div>
                    <div class="blank_pop_bottom"></div>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <!-- 2022-12-05 수정 : youtube 팝업 추가 -->
    <div id="pop_mainyoutube" class="pop_video modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <button onClick="popCloseYoutube()" class="pop_close_w"></button>
                </div>
                <div class="pop_video">
                    <iframe src="https://www.youtube.com/embed/NaS2Av1GGKE?enablejsapi=1&version=3&playerapiid=ytplayer" title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowFullScreen></iframe>
                </div>
            </div>
        </div>
        <div class="dim" onclick="popCloseYoutube()"></div>
    </div>
    <!-- pop_mainyoutube end -->
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    
    <script type="text/javascript">
        $(document).ready(function(){
            //영상 버튼 클릭
            $(".video_button").on("click", function() {
                $(this).hasClass("slider_stop")
                    ?($(this).removeClass("slider_stop"), $(this).parent().parent().parent().find("video").css("display","none")[0].pause(),
                    $(this).parent().parent().parent().parent().find(".slide_img").css("display","block"))
                    :($(this).addClass("slider_stop"), 
                    $(this).parent().parent().parent().find("video").css("display","block")[0].play(),$(this).parent().parent().parent().parent().find(".slide_img").css("display","none")
                );
            });
            //영상 재생 종료 세팅
            $("video").on("ended", function() {
                $(this).parent().parent().find(".video_button").removeClass("slider_stop");
            });

            let $status = $('.slide-page');
            let $slickElement = $('.main_slider');

            $slickElement.on('init reInit afterChange', function(event, slick, currentSlide, nextSlide){
                //페이지 인포 세팅
                let i = (currentSlide ? currentSlide : 0) + 1;
                $status.html('<strong class="current">' + i + '</strong>' + '<em class="count">' + slick.slideCount + '</em>');
            });

            $('.main_slider').slick({
                slidesToShow: 1,
                slidesToScroll: 1,
                arrows: true,
                fade: true,
                autoplay: true,
                infinite: true,
                prevArrow: $("#prev"),
                nextArrow: $("#next")
            });


            $('.tuto_slide').slick({
                slidesToShow: 1,
                slidesToScroll: 1,
                dots: true,
                arrows: true,
                fade: false,
                prevArrow: $("#tuto_prev"),
                nextArrow: $("#tuto_next")
            });
            let $popSlickElement = $('.tuto_slide');

            $popSlickElement.on('init reInit afterChange', function(event, slick, currentSlide, nextSlide){
                //페이지 위치별 버튼 세팅
                // console.log(currentSlide)
                if(currentSlide == 0 || currentSlide == undefined){
                    $("#tuto_prev").css("display", "none");
                    $("#tuto_next").removeAttr("onclick");
                    $("#tuto_next").text("다음");
                    $("#tuto_next").removeClass("half");
                    $("#tuto_next").addClass("full");
                }else if(currentSlide == 3){
                    $("#tuto_next").removeClass("full");
                    $("#tuto_next").addClass("half");
                    // 2022-11-28 수정
                    $("#tuto_prev").css("display", "inline-block");
                    $("#tuto_next").text("닫기");
                    $("#tuto_next").attr("onclick", "popClose()");
                }else{
                    $("#tuto_next").removeClass("full");
                    $("#tuto_next").addClass("half");
                    $("#tuto_prev").css("display", "inline-block");
                    $("#tuto_next").text("다음");
                    $("#tuto_next").removeAttr("onclick");
                }
            });
        });
        //비디오, 슬라이더 영역 종료

        // 코너 팝업 불러오기 
        
        // importPage('pop_store01', 'popup');
        // importPage('pop_store02', 'popup');
        // importPage('pop_store03', 'popup');
        // importPage('pop_store04', 'popup');
        // importPage('pop_store05', 'popup');
        // importPage('pop_store06', 'popup');
        
        /* 2022-12-15 수정 : 슬라이드 팝업 스크립트에 17px 추가 */
        /* 튜토리얼 슬라이드 */
        function popTuto() {
            $('html').css({'overflow':'hidden'});
            // $('html').css({'position':'fixed'});
            $('body').css('padding-right','17px');
            $('#pop_tutorial').css("display", "block");
            $('.tuto_slide').slick('unslick').slick('reinit').slick({
                slidesToShow: 1,
                slidesToScroll: 1,
                dots: true,
                arrows: true,
                fade: false,
                prevArrow: $("#tuto_prev"),
                nextArrow: $("#tuto_next")
            });
        };
        

        /* 해시태그 복사 팝업 노출 */
        function popupToastOpen() {
            $("#popupToast1").fadeIn(200).delay(2000).fadeOut(200);
            return false;
        }
        
        function hashtag(){
            
            // Get the text field
              var copyText = document.getElementById("clipboardCopy");

              // Select the text field
              copyText.select();
              copyText.setSelectionRange(0, 99999); // For mobile devices

               // Copy the text inside the text field
              navigator.clipboard.writeText(copyText.value);
            
              popupToastOpen();
        }
    </script>
</body>
</html>