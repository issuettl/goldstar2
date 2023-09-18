<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/corner.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_gift_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <section class="subTop_section">
        </section>
        <section class="exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    <!-- 2022-11-03 수정 -->
                    <p class="tac">‘<c:out value="${sign.worryType.desc}"/> 고민’을 가진 <br />
                        <c:out value="${sign.nickDec}"/>의 새로고침 솔루션
                    </p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <div class="mypage_history_wrap">
                    <!-- ul class="sub_text_nav">
                        <li class="left"><a href="<c:url value="/u/exp/worry.do"/>" class="text_navlink_left">나의 고민 관리</a></li>
                    </ul -->
                    <div class="subTitle_wrap">
                        <h1 class="subTitle subTitle_mypage">선물함</h1>
                    </div>
                    <ul class="round_tablist">
                        <li><button class="changeLi active" data-type="jca">전체</button></li>
                        <li><button class="changeLi" data-type="jammy">Jammy</button></li>
                        <li><button class="changeLi" data-type="corner">코-너</button></li>
                    </ul>
                    <div class="gift_histroy_wrap">
                        <!-- Jammy에만 해당 roundShadow_box start -->
                        <div class="roundShadow_box jc jammy jca">
                            <div class="gift_text_area">
                                <h3>개성고침 코-너</h3>
                                <p>방문 인증 Jammy 포인트 2000g</p> 
                                <%--
                                <c:if test="${not empty member.jammy}">
                                <fmt:parseDate var="issued" value="${member.jammy.issued}" pattern="yyyyMMddHHmmss"/>
                                <p class="date">발급일 : <fmt:formatDate value="${issued}" pattern="yyyy.MM.dd"/></p>
                                </c:if> --%>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <div class="left">
                                    <a href="https://jammy.lge.co.kr/" target="_blank" class="medium_btn_black jammy btn_black btn">JAMMY 바로가기</a>
                                </div>
                                <c:if test="${not empty member.jammy}">
                                <div class="right">
                                    <button class="medium_btn_gray jammy btn_lightgray btn" disabled="disabled"><c:out value="${member.jammy.code}"/></button>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        
                        <c:if test="${mind.staffCheck.name eq 'notyet'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty mind}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>마음고침 코-너</h3>
                                <p>맞춤 모종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${mind.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty mind}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${mind.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${style.staffCheck.name eq 'notyet'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty style}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>스타일고침 코-너</h3>
                                <p>금성전파사 열전사 스티커 1종, 자수 스티커 1종 / 리사이클링 DIY 팔찌 키트</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${style.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty style}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${style.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${indiv.staffCheck.name eq 'notyet'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty indiv}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>개성고침 코-너</h3>
                                <p>금성전파사 스티커 1종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${indiv.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty indiv}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${indiv.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        
                        <c:if test="${empty mind}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty mind}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>마음고침 코-너</h3>
                                <p>맞춤 모종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${mind.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty mind}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${mind.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${empty style}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty style}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>스타일고침 코-너</h3>
                                <p>금성전파사 열전사 스티커 1종, 자수 스티커 1종 / 리사이클링 DIY 팔찌 키트</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${style.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty style}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${style.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${empty indiv}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty indiv}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>개성고침 코-너</h3>
                                <p>금성전파사 스티커 1종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${indiv.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty indiv}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${indiv.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        
                        <c:if test="${mind.staffCheck.name eq 'present'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty mind}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${mind.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>마음고침 코-너</h3>
                                <p>맞춤 모종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${mind.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty mind}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${mind.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${style.staffCheck.name eq 'present'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty style}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${style.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>스타일고침 코-너</h3>
                                <p>금성전파사 열전사 스티커 1종, 자수 스티커 1종 / 리사이클링 DIY 팔찌 키트</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${style.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty style}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${style.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                        <c:if test="${indiv.staffCheck.name eq 'present'}">
                        <!-- 각 코너 roundShadow_box start -->
                        <div class="roundShadow_box jc corner jca">
                            <div class="gift_text_area">
                                <c:choose>
                                    <c:when test="${empty indiv}"><span class="booking">체험 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'notyet'}"><span class="experience">증정 가능</span></c:when>
                                    <c:when test="${indiv.staffCheck.name eq 'present'}"><span class="disable">증정 완료</span></c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <h3>개성고침 코-너</h3>
                                <p>금성전파사 스티커 1종</p>
                            </div>
                            <div class="gift_btns wrapped_flex btn_wrap">
                                <c:if test="${indiv.staffCheck.name ne 'present' }">
                                <div class="right">
                                    <c:choose>
                                        <c:when test="${empty indiv}"><button class="medium_btn_lg booking btn_primary btn goExp">시작하기</button></c:when>
                                        <c:when test="${indiv.staffCheck.name eq 'notyet'}"><button class="medium_btn_black experience btn_black btn goQr">QR코드 보기</button></c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- roundShadow_box end -->
                        </c:if>
                    </div>
                </div>
            </div>
        </section>
    </div>
    
    <div id="pop_qr"></div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <script>
    importPage3('pop_qr', 'exp/qr/page');
    
    $(function(){
    	$(".goExp").on({
    		"click" : function(){
    			document.location.href=contextPath + "u/exp/corner.do";
    		}
    	});
    	
    	$(".goQr").on({
            "click" : function(){
            	popupOpen('pop_qr_page');
            }
        });
    	
    	$(".changeLi").on({
    		"click" : function(){
    			$(".changeLi").removeClass("active");
    			$(this).addClass("active");
    			var type = $(this).data("type");
    			
    			$(".jc").hide();
    			$("." + type).show();
    		}
    	});
    });
    
    function goExp(){
    	
    }
    </script>
</body>
</html>
