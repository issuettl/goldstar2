<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="<c:choose><c:when test="${not empty parts}">experience_history_20221224</c:when><c:otherwise>experience_history_none_20221224</c:otherwise></c:choose>">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <section class="subGnb_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
            </div>
        </section>
        <section class="subTop_section">
        </section>
        <section class="exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    <!-- 2022-11-03 수정 -->
                    <p class="tac">‘<c:out value="${sign.worryType.desc}"/> 고민’을 가진 <br />
                        <c:out value="${sign.nickDec}"/>의 새로고침 솔루션
                    </p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <div class="mypage_history_wrap">
                    <!-- ul class="sub_text_nav">
                        <li class="left"><a href="<c:url value="/u/exp/worry.do"/>" class="text_navlink_left">나의 고민 관리</a></li>
                    </ul -->
                    <div class="subTitle_wrap">
                        <h1 class="subTitle subTitle_mypage">지난 체험 이력</h1>
                    </div>
                    
                    <c:if test="${empty parts}">
                    <div class="gift_history_none">
                        <p>지난 체험 이력이 없습니다.</p>
                    </div>
                    </c:if>
                    
                    <c:if test="${not empty parts}">
                    <form action="">
                        <div class="bbs_wrap container">
                            <div class="bbs_top">
                                <p class="bbs_list_all">지난 체험 이력<strong class="text_primary"><c:out value="${fn:length(parts)}"/></strong>건</p>
                                <!-- <div class="selectBox">
                                    <select name="" id="" class="select">
                                        <option value="최신순">최신순</option>
                                        <option value="등록순">등록순</option>
                                    </select>
                                </div> -->
                            </div>
                            <!-- bbs_list 게시판 리스트형 -->
                            <ul class="bbs_list">
                                <!-- li -->
                                
                                <c:forEach items="${parts}" var="item">
                                <li>
                                    <!-- 닫힌 dropdown 컨텐츠 -->
                                    <div class="bbs_dropdown">
                                        <button onclick="return dropdown_open(this);" class="dropdownLink">
                                            <div class="history_title wrapped_flex">
                                                <div class="left"><h3 class="qna_title"><c:out value="${item.sign.nickDec}"/>의 <c:out value="${item.sign.worryType.desc}"/></h3></div>
                                                <div class="right"><span class="date">
                                                <fmt:parseDate var="created" value="${item.sign.id.created}" pattern="yyyyMMdd"/>
                                                <fmt:formatDate value="${created}" pattern="yyyy-MM-dd"/>
                                                </span></div>
                                            </div>
                                        </button>
                                        <div class="dropdownFull">
                                            <div class="history_contents">
                                                <ul class="history_list">
                                                    <li class="title wrapped_flex">
                                                        <div class="list_corner left" style="max-width:400px;">코-너</div>
                                                        <div class="date right">체험 여부</div>
                                                    </li>
                                                    <c:forEach items="${cornerTypes}" var="corner">
                                                        <li class="wrapped_flex">
                                                            <div class="list_corner left" style="max-width:400px;"><c:out value="${corner.zoneLine}"/></div>
                                                            <div class="date right">
                                                                <c:choose>
                                                                   <c:when test="${corner.name eq 'mind'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.mind.staffCheck.name eq 'present'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                                                       </c:choose>
                                                                   </c:when>
                                                                   <c:when test="${corner.name eq 'indiv'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.indiv.staffCheck.name eq 'present'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span>미체험</span></c:otherwise>
                                                                       </c:choose>
                                                                   </c:when>
                                                                   <c:when test="${corner.name eq 'style'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.style.staffCheck.name eq 'present'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span>미체험</span></c:otherwise>
                                                                       </c:choose>
                                                                   </c:when>
                                                                   <c:when test="${corner.name eq 'mood'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.mood.staffCheck.name eq 'present'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span>미체험</span></c:otherwise>
                                                                       </c:choose>
                                                                   </c:when>
                                                                   <c:when test="${corner.name eq 'life'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.life.status.name eq 'status4'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span>미체험</span></c:otherwise>
                                                                        </c:choose>
                                                                   </c:when>
                                                                   <c:when test="${corner.name eq 'refresh'}">
                                                                       <c:choose>
                                                                           <c:when test="${item.refresh.staffCheck.name eq 'present'}"><span class="complete">완료</span></c:when>
                                                                           <c:otherwise><span>미체험</span></c:otherwise>
                                                                       </c:choose>
                                                                   </c:when>
                                                                   <c:otherwise><span>미체험</span></c:otherwise>
                                                               </c:choose>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 닫힌 dropdown 컨텐츠 end -->
                                </li>
                                </c:forEach>
                                <!-- //li end -->
                            </ul>
                            <!-- bbs_list end -->
                        </div>
                    </form>
                    </c:if>
                </div>
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    
    <!-- script start -->
    <script>
        /* 드롭다운 오픈 */
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
