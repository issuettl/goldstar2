<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="notice_view_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <section class="subGnb_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
            </div>
        </section>
        <section class="subTop_section">
            <div class="container">
                <!-- <div class="subTitle_wrap">
                    <h1 class="subTitle">안내</h1>
                </div> -->
                <ul class="subTab_menu subTab_menu_bbs">
                    <li><a href="<c:url value="/u/board/1/list.do"/>">FAQ</a></li>
                    <li><a class="active" href="<c:url value="/u/board/1/list.do"/>">공지사항</a></li>
                </ul>
            </div>
            
        </section>
        <section class="faq_page sub_section">
            <h2 class="hiddenText">공지사항</h2>
            <form action="">
                <div class="bbs_wrap container">
                    <div class="bbs_top">
                        <div class="bbs_top_view">
                            <c:if test="${board.newIcon}">
                            <span class="new">NEW</span>
                            </c:if>
                            <h3 class="bbs_view_title"><c:out value="${board.subject}"/></h3>
                            <div class="bbs_view_info">
                                <fmt:parseDate var="created" value="${board.created}" pattern="yyyyMMddHHmmss"/>
                                <span class="date"><fmt:formatDate value="${created}" pattern="yyyy.MM.dd"/></span>
                                <span class="views">조회 <c:out value="${board.read}"/></span>
                            </div>
                        </div>
                        <div class="line_share">
                            <button class="bbs_view_share">공유하기</button>
                        </div>
                    </div>
                    <!-- bbs_view : 게시글 본문 -->
                    <div class="bbs_view">
                        <p><c:out value="${board.contents}" escapeXml="false"/></p>
                    </div>
                    <!-- bbs_view end -->
                    <!-- 이전글 다음글 bbs_view_list -->
                    <ul class="bbs_view_list">
                        <!-- 이전 -->
                        <li class="prev">
                            <c:choose>
                                <c:when test="${not empty prev}">
                            <a href="<c:url value="/u/board/2/view/${prev.sn}.do"/>"><span class="next_title">이전 글</span><span class="list_subject"><c:out value="${prev.subject}"/></span></a>
                                </c:when>
                                <c:otherwise>
                            <a href="#"><span class="prev_title">이전 글</span><span class="list_subject"></span></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <!-- 다음글 end -->
                        <!-- 다음글 -->
                        <li class="next">
                            <c:choose>
                                <c:when test="${not empty next}">
                            <a href="<c:url value="/u/board/2/view/${next.sn}.do"/>"><span class="next_title">다음 글</span><span class="list_subject"><c:out value="${next.subject}"/></span></a>
                                </c:when>
                                <c:otherwise>
                            <a href="#"><span class="next_title">다음 글</span><span class="list_subject"></span></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <!-- 다음글 end -->
                    </ul>
                    <!-- bbs_view_list end -->
                    <!-- 2022-11-01 태그 수정 -->
                    <div class="tac w100per btn_wrap"><a href="<c:url value="/u/board/2/list.do"/>" class="btn_primary btn">목록</a></div>
                </div>
            </form>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />

</body>
</html>
