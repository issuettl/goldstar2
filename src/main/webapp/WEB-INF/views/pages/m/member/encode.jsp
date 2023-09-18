<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/member/list.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>회원리스트</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="member_section adm_section">
            <!-- topsch_area end -->
            <div class="adm_con_page roundbox">
                <ul class="adm_list">
                    <li class="adm_list_title">
                        <div class="list_con01">No.</div>
                        <div class="list_con02">uid</div>
                        <div class="list_con02">이름</div>
                        <div class="list_con03">휴대폰 번호</div>
                    </li>
                    <c:forEach items="${members}" var="item">
                    <li class="adm_list_con">
                        <div class="list_con01"><c:out value="${item.sn}" /></div>
                        <div class="list_con02"><c:out value="${item.uid}"/></div>
                        <div class="list_con02"><c:out value="${item.name}"/><br><c:out value="${item.nameDec}"/></div>
                        <div class="list_con03"><c:out value="${item.phone}"/><br><c:out value="${item.phoneDec}"/></div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
    </main>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>