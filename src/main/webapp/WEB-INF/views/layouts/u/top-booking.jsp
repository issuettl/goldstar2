<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

    <section class="subTop_section escape_top">
        <div class="container">
            <div class="subTitle_wrap">
                <h1 class="subTitle">ThinQ 방탈출 예약</h1>
            </div>
            <ul class="subTab_menu">
                <!-- 2022-11-21 수정 : 방탈출 앞단계 페이지 추가 -->
                <li><a class="<c:if test="${fn:startsWith(currentURI, '/u/life/about') or fn:startsWith(currentURI, '/u/life/week')}">active</c:if>" href="<c:url value="/u/life/about.do"/>">예약하기</a></li>
                <li><a class="<c:if test="${fn:startsWith(currentURI, '/u/life/list')}">active</c:if>" href="<c:url value="/u/life/list.do"/>">예약확인/취소</a></li>
            </ul>
            <ul class="sub_text_nav">
            <!-- 
                <li class="left"><a href="<c:url value="/u/page/index.do"/>" class="text_navlink_left"></a></li> -->
            </ul>
        </div>
    </section>