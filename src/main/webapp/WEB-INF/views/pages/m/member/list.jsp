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
            <!-- topsch_area : 어드민 상단 검색 -->
            <div class="topsch_area wrapped_flex">
                <div class="left">
                    <h3 class="adm_title">회원가입 고객<span class="adm_title_after"><c:out value="${paging.totalElements}"/>명</span></h3>
                </div>
                <div class="inputBox sch_wrap"><input type="text" id="nameSearch" placeholder="이름 검색" value="<c:out value="${param.name}"/>"></div>
                <div class="inputBox sch_wrap"><input type="text" id="phoneSearch" placeholder="휴대폰 번호 검색" value="<c:out value="${param.phone}"/>"></div>
                <div class="date_sch_wrap">
                    <input type="datetime" id="dateStartSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateStartOrg}"/>">
                    <span class="date_during">~</span>
                    <input type="datetime" id="dateEndSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateEndOrg}"/>">
                    <button class="btn_date btn_gray btn" id="click-search">검색</button>
                </div>
            </div>
            <!-- topsch_area end -->
            <div class="adm_con_page roundbox">
                <ul class="adm_list">
                    <li class="adm_list_title">
                        <div class="list_con01">No.</div>
                        <div class="list_con02">이름</div>
                        <div class="list_con03">휴대폰 번호</div>
                        <div class="list_con08">성별</div>
                        <div class="list_con09">연령</div>
                        <div class="list_con04">최초 방문 일자</div>
                        <div class="list_con05">방문 횟수</div>
                        <div class="list_con06">체험 횟수</div>
                        <div class="list_con07 list_con_btn">기능</div>
                    </li>
                    <c:set var="num" value="${paging.numberOfPages}"/>
                    <c:forEach items="${page.content}" var="item">
                    <li class="adm_list_con">
                        <div class="list_con01"><c:out value="${num}" /></div>
                        <div class="list_con02">
                            <c:out value="${item.nameDec}"/>
                            <c:if test="${empty item.nameDec }">
                                <c:out value="${item.name}"/>
                            </c:if>
                        </div>
                        <div class="list_con03">
                            <c:out value="${item.phoneDec}"/>
                            <c:if test="${empty item.phoneDec }">
                                <c:out value="${item.phone}"/>
                            </c:if>
                        </div>
                        <div class="list_con08">
                            <c:if test="${not empty item.gender}">
                                <c:out value="${item.gender.title}"/>
                            </c:if>
                        </div>
                        <div class="list_con09">
                            <c:if test="${not empty item.age}">
                                <c:out value="${item.age.title}"/>
                            </c:if>
                        </div>
                        <div class="list_con04">
                            <fmt:parseDate var="created" value="${item.created}" pattern="yyyyMMddHHmmss"/>
                            <fmt:formatDate value="${created}" pattern="yyyy-MM-dd"/>
                        </div>
                        <div class="list_con05"><c:out value="${item.visit}"/></div>
                        <div class="list_con06"><c:out value="${item.part}"/></div>
                        <div class="list_con07 list_con_btn">
                            <button data-sn="<c:out value="${item.sn}"/>" class="goView btn_m btn_primary btn">보기</button>
                        </div>
                        <div style="display:none;">
                            <c:out value="${item.uid}"/>
                            <c:out value="${item.name}"/>
                            <c:out value="${item.phone}"/>
                        </div>
                    </li>
                    <c:set var="num" value="${num-1}"/>
                    </c:forEach>
                </ul>
                <!-- 페이지 네비게이션 Start : 게시판과 동일한 태그를 사용하고 css속성만 변경했습니다. -->
                <r9:pagingHelperManager paging="${paging}" groups="5" request="${request}"/>
                <!-- 페이지 네비게이션 End -->
            </div>
        </section>
    </main>
    <section id="pop_password" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <h2 class="pop_mini_title">2차 인증</h2>
                    <p>비밀번호를 입력해주세요</p>
                    <div class="inputBox">
                        <input type="password" id="managerPassword" placeholder="비밀번호 확인" class="input_pw border_input">
                        <span class="pw_eye"></span>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button class="btn_pop_s btn_primary btn" id="goView">확인</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
<form id="searchForm" action="<c:url value="/m/member/list.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
<form id="viewForm" action="<c:url value="/m/member/view.do"/>" method="post">
    <input type="hidden" name="sn" value=""/>
    <input type="hidden" name="password" value=""/>
    <input type="hidden" name="total" value="<c:out value="${paging.totalElements}"/>"/>
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>