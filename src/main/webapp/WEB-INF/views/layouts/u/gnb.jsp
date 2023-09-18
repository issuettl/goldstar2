
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <ul class="gnb">
        <li class=""><a href="<c:url value="/u/page/index.do"/>" class="gnb_link mo_view<c:if test="${fn:startsWith(currentURI, '/u/page/index')}"> active</c:if>">홈</a></li>
        <li class="mo_hidden"><a href="<c:url value="/u/page/index.do"/>" class="gnb_link pc_view<c:if test="${fn:startsWith(currentURI, '/u/page/index')}"> active</c:if>">금성전파사 새로고침센터</a></li>
        <li class=""><a href="<c:url value="/u/page/about/world.do"/>" class="gnb_link<c:if test="${fn:startsWith(currentURI, '/u/page/about/')}"> active</c:if>">소개</a></li>
        <li class=""><a href="<c:url value="/u/exp/index.do"/>" class="gnb_link<c:if test="${fn:startsWith(currentURI, '/u/sign/') or fn:startsWith(currentURI, '/u/exp/')}"> active</c:if>">체험하기</a></li>
        <li class=""><a href="<c:url value="/u/event/list.do"/>" class="gnb_link<c:if test="${fn:startsWith(currentURI, '/u/event')}"> active</c:if>">이벤트</a></li>
        <li class=""><a href="<c:url value="/u/board/1/list.do"/>" class="gnb_link<c:if test="${fn:startsWith(currentURI, '/u/board/')}"> active</c:if>">FAQ/공지사항</a></li>
    </ul>