<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        
        <section class="regi2023 exper_page sub_section">
            <figure></figure>
            <div class="container">
	            <div class="exper_chatTitle">
	                <p class="tac">자네는 <strong class="exper_type1"><c:out value="${sign.pursueType.title}"/></strong>을 추구하는 사람이로군!<br />
	                    <strong class="exper_type2"><c:out value="${sign.pursueType.recommend}"/></strong>이라네.<br />
	                    자네에게 필요할 만한 제품을 추천해 주겠네!
	                </p>
	            </div>
	            <div class="exper_person"></div>
	            <!-- 코너 추천 리스트 -->
                <div class="exper_recommend">
                    <ul class="sub_text_nav">
                        <li class="right"><a href="<c:url value="/u/exp/history.do"/>" class="text_navlink_right">지난 체험 이력</a></li>
                    </ul>
                    <!-- 제품 추천 -->
                    <div class="recommend_row">
                        <!-- 전시제품 슬라이더 start -->
                        <div class="products_slide">
                            <c:forEach items="${products}" var="item" varStatus="proIndex">
                            <!-- 전시제품 -->
                            <div class="slide">
                                <figure class="products" onClick="javascript:popupOpen('pop_products_<c:out value="${proIndex.count}"/>');">
                                    <c:choose>
                                        <c:when test="${fn:startsWith(item.imageList, '/images')}">
                                            <img src="<c:url value="/u${item.imageList}"/>" alt="">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value="/u/product/file/list/${item.sn}.do"/>" alt="">
                                        </c:otherwise>
                                    </c:choose>
                                    <h3><c:out value="${item.name}"/></h3>
                                    <button onClick="javascript:popupOpen('pop_products_<c:out value="${proIndex.count}"/>');" class="large_btn_black btn">상세보기</button>
                                </figure>
                            </div>
                            <!-- 전시제품 end -->
                            </c:forEach>
                        </div>
                        <!-- 전시제품 슬라이더 end -->
                    </div>
                    <!-- 제품 추천 end -->
                    <!-- QR 인증 -->
                    <div class="recommend_row">
                        <button onClick="javascript:popupOpen('pop_qr_2023');" class="qr_recommend recommend roundShadow_box">
                            <figure class="qr_figure">
                                <img src="<c:url value="/u/exp/qr.do"/>" alt="">
                            </figure>
                            <span class="texts">
                                <!-- 2023-09-12 수정 : 문구 수정 -->
                                <h3>체험을 위한 QR코드</h3>
                                <p>체험을 원하시면 운영스탭에게 <br class="mo_br">QR코드를 보여주세요.</p>
                            </span>
                        </button>
                    </div>
                    <!-- QR 인증 end -->
                    <div class="recommend_row">
                        <button class="<c:if test="${not empty refresh and refresh.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty refresh and refresh.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
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
                        <button class="<c:if test="${not empty mind and mind.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mind and mind.staffCheck.name ne 'present'}">q_complete </c:if>w30per recommend roundShadow_box">
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
                    </div>
                    <div class="recommend_row">
                        <button class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                            <figure class="<c:if test="${not empty style and style.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty style and style.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_02.png"/>" alt=""></figure>
                            <span class="texts">
                                <h3>스타일고침 코-너</h3>
                                <p>LG 스타일러로 깨끗하게 가꾸고<br />
                                    나만의 아이템도 만드는 공간</p>
                            </span>
                        </button>
                        <button class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                            <figure class="<c:if test="${not empty indiv and indiv.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty indiv and indiv.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_03.png"/>" alt=""></figure>
                            <span class="texts">
                                <h3>개성고침 코-너</h3>
                                <p>LG 그램을 내 개성대로<br class="" />
                                리폼하는 공간</p>
                            </span>
                        </button>
                        <button class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>w20per recommend roundShadow_box">
                            <figure class="<c:if test="${not empty mood and mood.staffCheck.name eq 'present'}">disabled_complete </c:if><c:if test="${not empty mood and mood.staffCheck.name ne 'present'}">q_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_04.png"/>" alt=""></figure>
                            <span class="texts">
                                <h3>기분고침 코-너</h3>
                                <p>게임으로 스트레스를<br class="" />
                                날려버리는 공간</p>
                            </span>
                        </button>
                        <button onClick="javascript:agreement('pop_store5', this);" class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>w20per recommend roundShadow_box">
                            <figure class="<c:if test="${not empty life and life.status.name eq 'status2'}">disabled_booking </c:if><c:if test="${not empty life and life.status.name eq 'status4'}">disabled_complete </c:if>icon_64"><img src="<c:url value="/u/images/sub/img_corner_05.png"/>" alt=""></figure>
                            <span class="texts">
                                <h3>고민탈출 코-너</h3>
                                <p>ThinQ와 함께 즐기는 <br />방탈출 액티비티가 제공되는 공간</p>
                            </span>
                        </button>
                    </div>
                    <div class="w50per tac btn_wrap">
                        <button onclick="location.href='<c:url value="/u/exp/gift.do"/>'" class="large_btn_black btn_round_m btn">선물함</button>
                        
                        <c:choose>
                            <c:when test="${not empty survey}">
                                <button onClick="javascript:popupOpen('pop_survey_comp');" class="large_btn_gray btn_round_m btn">설문 참여 완료</button>
                            </c:when>
                            <c:when test="${isPart}">
                                <button onClick="javascript:popupOpen('pop_survey');" class="large_btn_line btn_round_m btn_line btn">설문 참여</button>
                            </c:when>
                            <c:when test="${empty questions or fn:length(questions) == 0}">
                                <button onClick="javascript:popupOpen('pop_survey_empty');" class="large_btn_line btn_round_m btn_line btn">설문 참여</button>
                            </c:when>
                            <c:otherwise>
                                <button onClick="javascript:popupOpen('pop_survey_not');" class="large_btn_line btn_round_m btn_line btn">설문 참여</button>
                            </c:otherwise>
                        </c:choose>
                        <button onclick="location.href='<c:url value="/u/exp/history.do"/>'" class="mo_view btn_ex btn_pass btn">지난 체험 이력</button>
                    </div>
                    <!-- 2022-12-23 QR버튼 위치 조정(위치 전) -->
                    <br><br>
                </div>
                <!-- 코너 추천 리스트 end -->
             </div>
        </section>
        <!-- popup end -->
    
    <!-- 2023년 QR팝업 -->
    <section id="pop_qr_2023" class="pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <!-- 2023-09-12 수정 : 문구 변경 -->
                    <h2>체험을 위한 QR코드</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- qr -->
                <div class="qr_wrap">
                    <figure class="qr_figure">
                        <img src="<c:url value="/u/exp/qr.do"/>" alt="">
                    </figure>
                    <!-- 2023-09-12 수정 : 문구 변경 -->
                    <p>체험을 원하시면 <br class="mo_br">운영스탭에게 QR코드를 보여주세요.</p>
                    <div class="w100per tac btn_wrap">
                        <button onClick="popClose()" class="btn_primary btn_pop_s">확인</button>
                    </div>
                </div>
                <!-- qr end -->
            </div>
        </div>
        <div class="dim"></div>
        <!-- popup end -->
    </section>
    <!-- 2023년 QR팝업 end -->
    
    <!-- 2023년 제품 팝업 -->
    <c:forEach items="${products}" var="item" varStatus="proIndex">
    <section id="pop_products_<c:out value="${proIndex.count}"/>" class="pop_products pop_store modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <!-- 제품명 (제품번호) -->
                    <h2><c:out value="${item.name}"/></h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <div class="con_store">
                    <!-- pop_product_img -->
                    <div class="pop_product_img">
                        <!-- 쇼핑몰이미지 -->
                        <figure onclick="window.open('<c:out value="${item.url}"/>')" style="background-image: url('<c:url value="/u${item.imageView}"/>');">
                            <div class="blur"></div>
                            <img src="<c:url value="/u${item.imageView}"/>" alt="">
                            
                        </figure>
                        <p class="pop_product_subtxt">※ 구매를 원하시면 이미지를 클릭 해주세요</p>
                        
                    </div>
                    <div class="mindTest_about">
                        <div class="test_about_right">
                            <h3><c:out value="${item.subject}"/></h3>
                            <p><pre><c:out value="${item.contents}"/></pre></p>
                        </div>
                    </div>
                </div>
                <div class="w100per tac btn_wrap">
                    <button onClick="popClose()" class="btn_primary btn_pop_s">종료</button>
                </div>
                <!-- pop_product_img end -->
            </div>
        </div>
        <div class="dim"></div>
        <!-- popup end -->
    </section>
    </c:forEach>
    
    <!-- popup start -->
    <section id="pop_survey" class="pop_mindTest modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>설문 참여</h2>
                    <button onClick="popClose()" class="popup_close"></button>
                </div>
                <!-- 설문참여 start -->
                <div id="survey_full" class="con_survey">
                    <div class="mindTest_about">
                        <figure><img src="<c:url value="/u/images/sub/img_character_03.png"/>" alt=""></figure>
                        <div class="test_about_right">
                            <h3>설문에 참여해 주셔서 감사합니다!</h3>
                            <p>아래의 문답을 참여해 주겠나?</p>
                        </div>
                        
                    </div>
                    <c:forEach items="${questions}" var="q" varStatus="qIndex">
                    <!-- 질문지 Q1 -->
                    <div class="survey_Q<c:out value="${qIndex.count}"/> survey_q" data-name="survey_radio<c:out value="${qIndex.count}"/>"
	                    <c:choose>
	                        <c:when test="${qIndex.first}">
	                             style="display: block;"
	                        </c:when>
	                        <c:otherwise>
	                             style="display: none;"
	                        </c:otherwise>
	                    </c:choose>
	                >
                        <h4><b>Q<c:out value="${qIndex.count}"/></b><c:out value="${q.question}"/></h4>
                        <c:set var="isImage" value="false"/>
                        <c:forEach items="${q.answers}" var="a" varStatus="aIndex">
                            <c:if test="${not empty a.image}">
                                <c:set var="isImage" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:choose>
	                        <c:when test="${isImage}">
		                        <div class="survey_img_list survey_list">
                                    <c:forEach items="${q.answers}" var="a" varStatus="aIndex">
                                    <div class="slide">
                                        <label for="survey_radio<c:out value="${qIndex.count}_${aIndex.count}"/>" class="test_radio">
                                            <input type="radio" name="survey_radio<c:out value="${qIndex.count}"/>" id="survey_radio<c:out value="${qIndex.count}_${aIndex.count}"/>" value="<c:out value="${a.sn}"/>">
                                            <img src="<c:url value="/u${a.image}"/>" alt="">
                                            <c:choose>
		                                        <c:when test="${fn:startsWith(a.image, '/images')}">
		                                            <img src="<c:url value="/u${a.image}"/>" alt="">
		                                        </c:when>
		                                        <c:otherwise>
		                                            <img src="<c:url value="/u/survey/file/image/${item.sn}.do"/>" alt="">
		                                        </c:otherwise>
		                                    </c:choose>
                                            <span class="mindTest_option"><c:out value="${a.name}"/></span>
                                        </label>
                                    </div>
                                    </c:forEach>
		                        </div>
	                        </c:when>
	                        <c:otherwise>
		                        <ul class="survey_list">
		                            <c:forEach items="${q.answers}" var="a" varStatus="aIndex">
		                            <li>
		                                <label for="survey_radio<c:out value="${qIndex.count}_${aIndex.count}"/>" class="test_radio">
		                                    <input type="radio" name="survey_radio<c:out value="${qIndex.count}"/>" id="survey_radio<c:out value="${qIndex.count}_${aIndex.count}"/>" value="<c:out value="${a.sn}"/>">
		                                    <span class="mindTest_option"><c:out value="${a.name}"/></span>
		                                </label>
		                            </li>
		                            </c:forEach>
		                        </ul>
	                        </c:otherwise>
                        </c:choose>
                        <div class="btn_wrap">
                            <c:if test="${not qIndex.first}">
	                        <button onClick="surveyPrev(<c:out value="${qIndex.count - 1}"/>);" class="half btn_gray btn_pop_s">이전</button>
	                        </c:if>
	                        <c:if test="${not qIndex.last}">
	                        <button onClick="surveyNext(<c:out value="${qIndex.count}"/>);" class="half btn_primary btn_pop_s">다음</button>
	                        </c:if>
	                        <c:if test="${qIndex.last}">
	                        <button onClick="surveySave();" class="half btn_primary btn_pop_s">설문 완료</button>
	                        </c:if>
                        </div>
                    </div>
                    <!-- 질문지 Q<c:out value="${qIndex.count}"/> end -->
                    </c:forEach>
                </div>
                <!-- 설문참여 end -->
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- //popup end -->

    <!-- popmini -->
    <section id="pop_survey_not" class="pop_mini_2023 pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <!-- 코너 체험전에 아래 문구가 나타납니다. -->
                    <p>코-너 체험 완료 후, 참여 가능합니다.</p>
                    <!-- 코너 참여기간 아닐 경우 아래 문구가 나타납니다. 
                    <p>코-너 체험 완료 후, 참여 가능합니다.</p> -->
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- //popup end -->
    <!-- popmini -->
    <section id="pop_survey_comp" class="pop_mini_2023 pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>설문 참여를 완료하였습니다. <br />
                        감사합니다.</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <section id="pop_survey_empty" class="pop_mini_2023 pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>지금은 설문 참여 <br />
                        기간이 아닙니다.</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <section id="pop_survey_end" class="pop_mini_2023 pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>설문 참여를 완료하였습니다. <br />
                        감사합니다.</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="document.location.href=contextPath + 'u/exp/corner.do'" class="btn_pop_s btn_primary btn">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- //popup end -->
    
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
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <!-- script start -->
    <script>
        //제품 슬라이더
        $(document).ready(function(){
            $('.products_slide').slick({
                infinite: true,
                slidesToShow: 3,
                slidesToScroll: 1,
                dots: false,
                arrows: false,
                fade: false,
                responsive: [
                    {
                    breakpoint: 1000,
                    settings: {
                        slidesToShow: 3
                    }
                    },
                    {
                    breakpoint: 767,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1,
                        centerMode: true,
                        variableWidth: true,
                        arrows: false,
                        dots: false
                    }
                    }
                ]
            });
            
        });
        

        function initSlick() {
            $('.survey_img_list').slick({
                infinite: true,
                slidesToShow: 3,
                slidesToScroll: 1,
                dots: false
            });
        }

        function resizeSlick() {
        if (window.innerWidth > 767) {
                // 화면이 768px보다 큰 경우 Slick을 활성화
                if (!$('.survey_img_list').hasClass('slick-initialized')) {
                initSlick();
            }
        } else {
                // 화면이 768px보다 작은 경우 Slick을 비활성화
                if ($('.survey_img_list').hasClass('slick-initialized')) {
                $('.survey_img_list').slick('unslick');
                }
            }
        }

        
        // 창 크기가 변경될 때 Slick 상태 변경
        $(window).on('resize', resizeSlick);

        /* 설문 이전, 다음 질문으로 넘어가는 스크립트 */
        function next_step1() { toggleSlide('.survey_Q1', '.survey_Q2'); }
        function next_step2() { toggleSlide('.survey_Q2', '.survey_Q3'); }
        function next_step3() { toggleSlide('.survey_Q3', '.survey_Q4'); }
    </script>
</body>
</html>
