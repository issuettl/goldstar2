<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/life.js?v=${versionHtml}"/>"></script>
</head>
<body class="<c:choose><c:when test="${not empty parts}">about_booking_list_20221224</c:when><c:otherwise>about_booking_list_none_20221224</c:otherwise></c:choose>">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/top-booking.jsp" />
        <section class="sub_section">
            <div class="container">
                <div class="booking_wrap">
                    <c:choose>
                        <c:when test="${empty list}">
		                    <div class="gift_history_none">
		                        <p>예약 내역이 없습니다.</p>
		                    </div>
                        </c:when>
                        <c:otherwise>
		                    <div class="booking_list gift_histroy_wrap">
		                        <!-- roundShadow_box start -->
		                        <c:forEach items="${list}" var="item">
		                        <div class="roundShadow_box">
		                            <div class="gift_text_area">
		                                <h3>ThinQ 방탈출</h3>
		                                <table class="booking_table">
		                                    <tr>
		                                        <th>날짜</th>
		                                        <td>
		                                            <fmt:parseDate var="date" value="${item.date}" pattern="yyyyMMdd"/>
		                                            <fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/>
		                                        </td>
		                                    </tr>
		                                    <tr>
		                                        <th>시간</th>
		                                        <td><c:out value="${item.time.fmt}"/></td>
		                                    </tr>
		                                    <tr>
		                                        <th>상태</th>
		                                        <td><c:out value="${item.statusName}"/></td>
		                                    </tr>
		                                </table>
		                            </div>
		                            <div class="gift_btns btn_wrap tar">
		                                <div class="right">
		                                    <c:if test="${item.type.name eq 'weekday'}">
		                                        <c:if test="${item.statusName ne '기간만료' and item.status.name eq 'status2'}">
		                                        <button class="btn_black btn doCancel" data-sn="<c:out value="${item.sn}"/>">예약 취소하기</button>
		                                        </c:if>
		                                    </c:if>
		                                    <c:if test="${item.type.name eq 'weekend'}">
		                                        <c:if test="${item.status.name eq 'status1'}">
		                                        <button class="btn_black btn doCancel" data-sn="<c:out value="${item.sn}"/>">예약 취소하기</button>
		                                        </c:if>
		                                    </c:if>
		                                </div>
		                            </div>
		                        </div>
		                        </c:forEach>
		                    </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
