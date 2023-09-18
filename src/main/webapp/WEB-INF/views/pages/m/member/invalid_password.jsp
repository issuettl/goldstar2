<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
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
    </main>
<form id="searchForm" action="<c:url value="/m/member/list.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
    <script>
    alert('비밀번호를 확인해주세요.');
    $("#searchForm").submit();
    </script>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>