<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/board/list.js?v=${versionHtml}"/>"></script>
</head>
<body class="notice_list_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <section class="subGnb_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
            </div>
        </section>
        <section class="subTop_section">
            <div class="container">
                <ul class="subTab_menu subTab_menu_bbs">
                    <li><a href="<c:url value="/u/board/1/list.do"/>">FAQ</a></li>
                    <li><a class="active" href="<c:url value="/u/board/2/list.do"/>">공지사항</a></li>
                </ul>
            </div>
            
        </section>
        <section class="faq_page sub_section">
            <h2 class="hiddenText">공지사항 목록</h2>
            <div class="bbs_wrap container">
                <div class="bbs_top">
                    <p class="bbs_list_all">전체<strong class="text_primary"><c:out value="${page.totalElements}"/></strong>건</p>
                    <!-- div class="selectBox">
                        <select name="" id="" class="select">
                            <option value="최신순">최신순</option>
                            <option value="등록순">등록순</option>
                        </select>
                    </div -->
                </div>
                <!-- bbs_list 게시판 리스트형 -->
                <ul class="bbs_list">
                    <!-- bbs_list_top li -->
                    <li class="bbs_list_top">
                        <div class="bbs_flex"><span class="bbs_title">제목</span><span class="date">등록일</span><span class="views">조회수</span></div>
                    </li>
                    <!-- //li end -->
                    <!-- li -->
                    <c:forEach items="${page.content}" var="item">
                        <li>
                         <a class="bbs_list_link bbs_flex" href="<c:url value="/u/board/2/view/${item.sn}.do"/>">
                             <h3 class="bbs_title"><c:if test="${item.newIcon}"><b>NEW</b></c:if><c:out value="${item.subject}"/></h3>
                             <fmt:parseDate var="created" value="${item.created}" pattern="yyyyMMddHHmmss"/>
                             <span class="date"><fmt:formatDate value="${created}" pattern="yyyy.MM.dd"/></span><span class="views"><c:out value="${item.read}"/></span>
                         </a>
                     </li>
                    </c:forEach>
                    <c:if test="${empty page.content}">
                    <li>
                        <div class="bbs_flex tac"><p class="bbs_title mt50 mb50 tac">게시물이 없습니다.</p></div>
                    </li>
                    </c:if>
                </ul>
                <!-- bbs_list end -->
                <!-- 페이지 네비게이션 Start -->
                <r9:pagingHelperHome paging="${paging}" groups="5" request="${request}"/>
                <!-- 페이지 네비게이션 End -->
            </div>
        </section>
    </div>
<form id="searchForm" action="<c:url value="/u/board/${bbs.sn}/list.do"/>" method="get">
    <input type="hidden" name="page" value="<c:out value="${params.page}"/>"/>
</form>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />

</body>
</html>
