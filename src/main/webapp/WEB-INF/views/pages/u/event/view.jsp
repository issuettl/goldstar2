<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="event_event_view_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <!-- 2022-12-15 수정 : 이벤트 상세페이지에서만 삭제 -->
        <section class="event_page sub_section">
            <div class="container">
                <h2 class="hiddenText">이벤트 상세</h2>
            </div>
            <div class="bbs_wrap container">
                    <div class="bbs_top">
                        <div class="left">
                            <div class="bbs_top_view">
                                <!-- 2022-12-15 수정 -->
                                <h3 class="bbs_view_title"><c:out value="${event.subject}"/></h3>
                            </div>
                        </div>
                    </div>
                    <!-- bbs_view : 게시글 본문 -->
                    <div class="bbs_view">
                        <!-- 2022-12-15 수정 -->
                        <img src="<c:url value="/u/event/file/pc/${event.sn}.do"/>" class="pc_view" alt="<c:out value="${event.subject}"/>">
                        <img src="<c:url value="/u/event/file/mobile/${event.sn}.do"/>" class="mo_view" alt="<c:out value="${event.subject}"/>">
                    </div>
                    <!-- bbs_view end -->
                    <h3 class="bbs_slider_title">진행 중인 이벤트</h3>
                    <!-- bbs_contents_slider 이벤트 목록 슬라이더 start -->
                    <div class="bbs_contents_slider">
                        <!-- slide start -->
                        <c:forEach items="${events}" var="item">
                        <div class="slide">
                            <!-- 2022-12-15 수정 -->
                            <a href="<c:url value="/u/event/view/${item.sn}.do"/>" class="subEvent_item roundShadow_box">
                                <figure style="background-image: url('<c:url value="/u/event/file/thumb/${item.sn}.do"/>');"></figure>
                                <div class="subEvent_content">
                                    <h3><c:out value="${item.subject}"/></h3>
                                    <fmt:parseDate var="startDate" value="${item.startDate}" pattern="yyyyMMdd"/>
                                    <fmt:parseDate var="endDate" value="${item.endDate}" pattern="yyyyMMdd"/>
                                    <p class="date_wrap"><span class="date"><fmt:formatDate value="${startDate}" pattern="yyyy.MM.dd"/></span><span class="dateline">~</span><span class="date"><fmt:formatDate value="${endDate}" pattern="yyyy.MM.dd"/></span></p>
                                </div>
                            </a>
                        </div>
                        </c:forEach>
                    </div>
                    <!-- bbs_contents_slider end -->
                    <!-- 2022-12-15 수정 -->
                    <div class="tac w100per btn_wrap"><a href="<c:url value="/u/event/list.do"/>" class="btn_primary btn">목록</a></div>
                </div>
        </section>
    </div>
    <!-- script start -->
    <script>
        $(document).ready(function(){
            $('.bbs_contents_slider').slick({
                infinite: false,
                slidesToShow: 4,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                fade: false,
                responsive: [
                    {
                    breakpoint: 840,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 1,
                        variableWidth: true,
                        arrows: false
                    }
                    }
                ]
            });
        });
    </script>
</body>
</html>
