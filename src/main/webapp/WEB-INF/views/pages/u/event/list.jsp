<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="event_event_list_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <div class="container_full">
            <div class="subTop_event_bg subTop_bg">
                <div class="container wrapped_flex">
                    <div class="t_center pc_view"><img src="<c:url value="/u/images/sub/sub_top_event_left.png"/>" alt=""></div>
                    <div class="mo_view"><img src="<c:url value="/u/images/sub/sub_top_event_left_mo.png"/>" alt=""></div>
                </div>
            </div>
        </div>
        <!-- 실제 나올 페이지 -->
        <section class="event_page sub_section">
            <div class="container">
                <h2 class="hiddenText">이벤트 목록</h2>
            </div>
            <form action="">
                <div class="bbs_wrap container">
                    <!-- bbs_list 게시판 리스트형 내용 -->
                    <ul class="subEvent_list">
                        <c:if test="${empty events}">
                        <li>
                            <div class="bbs_flex tac"><p class="bbs_title mt50 mb50 tac">게시물이 없습니다.</p></div>
                        </li>
                        </c:if>
                        <c:forEach items="${events}" var="item">
                        <li>
                            <a href="<c:url value="/u/event/view/${item.sn}.do"/>" class="subEvent_disable subEvent_item roundShadow_box">
                                <figure style="background-image: url('<c:url value="/u/event/file/thumb/${item.sn}.do"/>');"></figure>
                                <div class="subEvent_content">
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
            </form>
        </section>
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
