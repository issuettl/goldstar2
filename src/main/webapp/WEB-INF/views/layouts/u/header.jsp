<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <c:out value="${lgeGnb}" escapeXml="false"/>
    <div class="mo_view mo_header">
        <div class="mobile-nav-wrap sub-renew-nav-wrap">
            <a href="javascript:history.back();" class="btn-back"><span class="blind">이전</span></a>
            
            <c:choose>
                <c:when test="${fn:startsWith(currentURI, '/u/life/')}">
            <span class="nav-item">ThinQ 방탈출 예약</span>
                </c:when>
                <c:otherwise>
            <span class="nav-item">금성전파사 새로고침센터</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>