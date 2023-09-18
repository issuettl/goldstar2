<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/life/weekend.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <!-- 2022-11-22 수정 : 방탈출 서브 메뉴 추가 -->
                    <li class="prev_nav">
                        <a href="<c:url value="/m/life/weekday.do"/>">방탈출 관리</a>
                    </li>
                    <li class="current_nav">
                        <h2>주말 응모 리스트</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <div class="escape_section adm_section">
            <!-- topsch_area : 어드민 상단 검색 -->
            <div class="topsch_area wrapped_flex">
                <div class="inputBox sch_wrap"><input type="text" id="nameSearch" placeholder="이름 검색" value="<c:out value="${param.name}"/>"></div>
                <div class="inputBox sch_wrap"><input type="text" id="phoneSearch" placeholder="휴대폰 번호 검색" value="<c:out value="${param.phone}"/>"></div>
                <div class="date_sch_wrap">
                    <input type="datetime" id="dateStartSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateStartOrg}"/>">
                    <span class="date_during">~</span>
                    <input type="datetime" id="dateEndSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateEndOrg}"/>">
                </div>
            </div>
            <div class="mb10 topsch_area topsch2 wrapped_flex">
                <h3 class="topsch2_title">시간 선택</h3>
                <div class="roundbox">
                    <label for="timeAll" class="customCheck customCheck_round">
                        <input type="checkbox" id="timeAll"
                        <c:if test="${param.timeAM11 eq 'Y' and param.timePM12 eq 'Y' and param.timePM13 eq 'Y' and param.timePM14 eq 'Y' and param.timePM15 eq 'Y' and param.timePM16 eq 'Y' and param.timePM17 eq 'Y' and param.timePM18 eq 'Y'}">checked="checked"</c:if>
                        >
                        <span class="checkmark"></span>
                        <span class="checkText">전체</span>
                    </label>
                    <label for="timeAM11Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timeAM11Search"/>" class="timeAll" value="Y" <c:if test="${param.timeAM11 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">11:00</span>
                    </label>
                    <label for="timePM12Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM12Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM12 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">12:00</span>
                    </label>
                    <label for="timePM13Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM13Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM13 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">13:00</span>
                    </label>
                    <label for="timePM14Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM14Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM14 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">14:00</span>
                    </label>
                    <label for="timePM15Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM15Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM15 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">15:00</span>
                    </label>
                    <label for="timePM16Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM16Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM16 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">16:00</span>
                    </label>
                    <label for="timePM17Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM17Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM17 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">17:00</span>
                    </label>
                    <label for="timePM18Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="<c:out value="timePM18Search"/>" class="timeAll" value="Y" <c:if test="${param.timePM18 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">18:00</span>
                    </label>
                </div>
            </div>
            <div class="topsch_area topsch2 wrapped_flex">
                <h3 class="topsch2_title">상태 선택</h3>
                <div class="roundbox">
                    <!-- 2022-11-25 수정 : radio가 아니라 checkbox로 중복 선택 가능 -->
                    <label for="statusAll" class="customCheck customCheck_round">
                        <input type="checkbox" id="statusAll" <c:if test="${param.status1 eq 'Y' and param.status2 eq 'Y' and param.status3 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">전체</span>
                    </label>
                    <label for="status1Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status1Search" class="statusAll" value="Y" <c:if test="${param.status1 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">응모중</span>
                    </label>
                    <label for="status2Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status2Search" class="statusAll" value="Y" <c:if test="${param.status2 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">당첨</span>
                    </label>
                    <label for="status3Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status3Search" class="statusAll" value="Y" <c:if test="${param.status3 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">미당첨</span>
                    </label>
                </div>
                <button class="btn_date btn_gray btn" id="click-search">검색</button>
            </div>
            <!-- topsch_area end -->
            <div class="adm_con_page roundbox">
               <div class="escape_draw_list escape_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">주말 응모 리스트</h3>
                        <button class="btn_bbsfx btn_s btn_primary btn doConfirms">당첨</button>
                    </div>
                    <ul class="list_num10 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01 list_con_chk">
                            </div>
                            <div class="list_con01">No.</div>
                            <div class="list_con02">예약일</div>
                            <div class="list_con03">시간</div>
                            <div class="list_con04">이름</div>
                            <div class="list_con05">휴대폰 번호</div>
                            <div class="list_con06">체험인원</div>
                            <div class="list_con07">상태</div>
                            <div class="list_con10 list_con_btn">기능</div>
                        </li>
                        <c:set var="num" value="${paging.numberOfPages}"/>
                        <c:forEach items="${page.content}" var="item">
                        <li class="adm_list_con">
                            <div class="list_con01 list_con_chk">
                                <label for="snList<c:out value="${item.sn}"/>" class="customCheck">
                                    <input type="checkbox" class="snListAll" id="snList<c:out value="${item.sn}"/>" value="<c:out value="${item.sn}"/>">
                                    <span class="checkmark"></span>
                                </label>
                            </div>
                            <div class="list_con01"><c:out value="${num}" /></div>
                            <div class="list_con02" id="<c:out value="date${item.sn}"/>">
                                <fmt:parseDate var="date" value="${item.date}" pattern="yyyyMMdd"/>
                                <fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="list_con03" id="<c:out value="time${item.sn}"/>"><c:out value="${item.time.fmt}"/></div>
                            <div class="list_con04" id="<c:out value="name${item.sn}"/>"><c:out value="${item.member.nameDec}"/></div>
                            <div class="list_con05" id="<c:out value="phone${item.sn}"/>"><c:out value="${item.member.phoneDec}"/></div>
                            <div class="list_con06" id="<c:out value="memberCount${item.sn}"/>"><c:out value="${item.memberCount}"/>명</div>
                            <div class="list_con07" id="<c:out value="statusName${item.sn}"/>"><c:out value="${item.statusName}"/></div>
                            <div class="list_con10 list_con_btn">
                                <c:if test="${item.status.name eq 'status1'}">
                                <button class="btn_primary btn_m btn doConfirm" data-sn="<c:out value="${item.sn}"/>">당첨</button>
                                </c:if>
                            </div>
                        </li>
                        <c:set var="num" value="${num-1}"/>
                        </c:forEach>
                    </ul>
                </div>
                <!-- 페이지 네비게이션 Start : 게시판과 동일한 태그를 사용하고 css속성만 변경했습니다. -->
                <r9:pagingHelperManager paging="${paging}" groups="5" request="${request}"/>
                <!-- 페이지 네비게이션 End -->
            </div>
        </div>
    </main>
    
<form id="searchForm" action="<c:url value="/m/life/weekend.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
    <input type="hidden" name="status1" value="<c:out value="${param.status1}"/>"/>
    <input type="hidden" name="status2" value="<c:out value="${param.status2}"/>"/>
    <input type="hidden" name="status3" value="<c:out value="${param.status3}"/>"/>
    <input type="hidden" name="timeAM11" value="<c:out value="${param.timeAM11}"/>"/>
    <input type="hidden" name="timePM12" value="<c:out value="${param.timePM12}"/>"/>
    <input type="hidden" name="timePM13" value="<c:out value="${param.timePM13}"/>"/>
    <input type="hidden" name="timePM14" value="<c:out value="${param.timePM14}"/>"/>
    <input type="hidden" name="timePM15" value="<c:out value="${param.timePM15}"/>"/>
    <input type="hidden" name="timePM16" value="<c:out value="${param.timePM16}"/>"/>
    <input type="hidden" name="timePM17" value="<c:out value="${param.timePM17}"/>"/>
    <input type="hidden" name="timePM18" value="<c:out value="${param.timePM18}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>