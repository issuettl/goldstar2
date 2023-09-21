<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

    <!-- left GNB -->
    <header class="adm_gnb">
        <h1 class="adm_logo"><a href="<c:url value="/m/dash.do"/>"><img src="<c:url value="/m/images/logo_primary.svg"/>" alt="lG 금성전파사"></a></h1>
        <ul class="adm_menulist">
            <li><a href="<c:url value="/m/dash.do"/>" class="menu_link01<c:if test="${fn:startsWith(currentURI, '/m/dash.do')}"> active</c:if>">대시보드</a></li>
            <li><a href="<c:url value="/m/member/list.do"/>" class="menu_link02<c:if test="${fn:startsWith(currentURI, '/m/member/')}"> active</c:if>">회원리스트</a></li>
            <li>
                <a href="<c:url value="/m/survey/index.do"/>" class="menu_link03<c:if test="${fn:startsWith(currentURI, '/m/survey/')}"> active</c:if>">사전문답 관리</a>
                <ul class="adm_menulist_sub">
                    <li><a href="<c:url value="/m/survey/index.do"/>" class="menu_link031<c:if test="${fn:startsWith(currentURI, '/m/survey/index')}"> active</c:if>">사전문답 리스트</a></li>
                    <li><a href="<c:url value="/m/survey/answer.do"/>" class="menu_link032<c:if test="${fn:startsWith(currentURI, '/m/survey/answer')}"> active</c:if>">사전문답 데이터</a></li>
                </ul>
            </li>
            <li>
                <a href="<c:url value="/m/survey/index.do"/>" class="menu_link03<c:if test="${fn:startsWith(currentURI, '/m/pursue/')}"> active</c:if>">설문 조사 관리</a>
                <ul class="adm_menulist_sub">
                    <li><a href="<c:url value="/m/pursue/index.do"/>" class="menu_link031<c:if test="${fn:startsWith(currentURI, '/m/pursue/index')}"> active</c:if>">고객 유형 문답 관리</a></li>
                    <li><a href="<c:url value="/m/survey/answer.do"/>" class="menu_link032<c:if test="${fn:startsWith(currentURI, '/m/survey/answer')}"> active</c:if>">사전문답 데이터</a></li>
                </ul>
            </li>
            <li><a href="<c:url value="/m/event/list.do"/>" class="menu_link04<c:if test="${fn:startsWith(currentURI, '/m/event/')}"> active</c:if>">이벤트 관리</a></li>
            <li><a href="<c:url value="/m/board/1/list.do"/>" class="menu_link05<c:if test="${fn:startsWith(currentURI, '/m/board/1/')}"> active</c:if>">FAQ 관리</a></li>
            <li><a href="<c:url value="/m/board/2/list.do"/>" class="menu_link06<c:if test="${fn:startsWith(currentURI, '/m/board/2/')}"> active</c:if>">공지사항 관리</a></li>
            <li>
                <a href="<c:url value="/m/life/weekday.do"/>" class="menu_link07<c:if test="${fn:startsWith(currentURI, '/m/life')}"> active</c:if>">방탈출 관리</a>
                <ul class="adm_menulist_sub">
                    <li><a href="<c:url value="/m/life/weekday.do"/>" class="menu_link071<c:if test="${fn:startsWith(currentURI, '/m/life/weekday')}"> active</c:if>">예약 확정 리스트</a></li>
                    <li><a href="<c:url value="/m/life/weekend.do"/>" class="menu_link072<c:if test="${fn:startsWith(currentURI, '/m/life/weekend')}"> active</c:if>">주말 응모 리스트</a></li>
                </ul>
            </li>
            <!-- 
            <li><a href="<c:url value="/m/manager/list.do"/>" class="menu_link09<c:if test="${fn:startsWith(currentURI, '/m/manager/list')}"> active</c:if>">관리자 관리</a></li> -->
        </ul>
        <div class="gnb_login_info">
            <figure class="profile_img" style="background-image:'';"></figure>
            <div class="profile_info">
                <h3 class="profile_name"><c:out value="${signManager.name}"/></h3>
            </div>
        </div>
    </header>
    <!-- left GNB end -->