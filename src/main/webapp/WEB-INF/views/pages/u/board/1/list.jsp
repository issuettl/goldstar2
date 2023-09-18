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
<body class="faq_20221224">
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
                    <li><a class="active" href="<c:url value="/u/board/1/list.do"/>">FAQ</a></li>
                    <li><a href="<c:url value="/u/board/2/list.do"/>">공지사항</a></li>
                </ul>
            </div>
            
        </section>
        <section class="faq_page sub_section">
            <h2 class="hiddenText">FAQ 리스트</h2>
            <div class="bbs_wrap container">
                <div class="bbs_top">
                    <p class="bbs_list_all">전체<strong class="text_primary"><c:out value="${page.totalElements}"/></strong>건</p>
                    <!-- <div class="selectBox">
                        <select name="" id="" class="select">
                            <option value="최신순">최신순</option>
                            <option value="등록순">등록순</option>
                        </select>
                    </div> -->
                </div>
                <!-- bbs_list 게시판 리스트형 -->
                <ul class="bbs_list">
                    <c:forEach items="${page.content}" var="item">
                    <!-- li -->
                    <li>
                        <!-- 닫힌 dropdown 컨텐츠 -->
                        <div class="bbs_dropdown">
                            <button onclick="return dropdown_open(this);" class="dropdownLink">
                                <div class="faq_title">
                                    <b class="q_mark">Q</b>
                                    <h3 class="qna_title"><c:out value="${item.subject}"/></h3>
                                </div>
                            </button>
                            <div class="dropdownFull">
                                <div class="faq_contents"><c:out value="${item.contents}" escapeXml="false"/></div>
                            </div>
                        </div>
                        <!-- 닫힌 dropdown 컨텐츠 end -->
                    </li>
                    <!-- //li end -->
                    </c:forEach>
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
    <!-- script start -->
    <script>
        /* 드롭다운 오픈
        $(".dropdownLink").on("click", function () {
            var moreBtn = $(el).closest(".bbs_dropdown").find(".dropdownLink");
            var fullCon = $(el).closest(".bbs_dropdown").find(".dropdownFull");
            if($(".dropdownFull").css('display','block')) {
                $(".dropdownLink").removeClass("more_open");
                $(".dropdownFull").slideUp();
            } else {
                $(this).addClass("more_open");
                $(this).closest(".bbs_dropdown").find(".dropdownFull").slideDown();
            }
            return false;
        });
         */
        function dropdown_open(el){
            var moreBtn = $(el).closest(".bbs_dropdown").find(".dropdownLink");
            var fullCon = $(el).closest(".bbs_dropdown").find(".dropdownFull");
            
            if(moreBtn.hasClass("more_open")) {
                $(".bbs_dropdown").find(".dropdownLink").removeClass("more_open");
                $(".bbs_dropdown").find(".dropdownFull").slideUp();
            } else {
                $(".bbs_dropdown").find(".dropdownLink").removeClass("more_open");
                $(".bbs_dropdown").find(".dropdownFull").hide();
                moreBtn.addClass("more_open");
                fullCon.slideDown();
            }
            return false;
        };
    </script>
</body>
</html>
