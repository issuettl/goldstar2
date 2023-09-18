<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/life/weekday.js?v=${versionHtml}"/>"></script>
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
                        <h2>예약 확정 리스트</h2>
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
                <div class="topsch_area_check">
                    <label for="todaySearch" class="customCheck">
                        <input type="checkbox" id="todaySearch" value="Y" <c:if test="${param.today eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">당일</span>
                    </label>
                    <label for="tomorrowSearch" class="customCheck">
                        <input type="checkbox" id="tomorrowSearch" value="Y" <c:if test="${param.tomorrow eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">익일</span>
                    </label>
                </div>
            </div>
            <div class="topsch_area topsch2 wrapped_flex">
                <h3 class="topsch2_title">상태</h3>
                <div class="roundbox">
                    <!-- 2022-11-25 수정 : radio가 아니라 checkbox로 중복 선택 가능 -->
                    <label for="statusAll" class="customCheck customCheck_round">
                        <input type="checkbox" id="statusAll" <c:if test="${param.status2 eq 'Y' and param.status4 eq 'Y' and param.status0 eq 'Y' and param.status6 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">전체</span>
                    </label>
                    <label for="status2Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status2Search" class="statusAll" value="Y" <c:if test="${param.status2 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">예약</span>
                    </label>
                    <label for="status4Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status4Search" class="statusAll" value="Y" <c:if test="${param.status4 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">체험완료</span>
                    </label>
                    <label for="status0Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status0Search" class="statusAll" value="Y" <c:if test="${param.status0 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">기간만료</span>
                    </label>
                    <label for="status6Search" class="customCheck customCheck_round">
                        <input type="checkbox" id="status6Search" class="statusAll" value="Y" <c:if test="${param.status6 eq 'Y'}">checked="checked"</c:if>>
                        <span class="checkmark"></span>
                        <span class="checkText">권한삭제</span>
                    </label>
                </div>
                <button class="btn_date btn_gray btn" id="click-search">검색</button>
            </div>
            <!-- topsch_area end -->
            <div class="adm_con_page roundbox">
                <div class="escape_list page_pb0 adm_part">
                    <h3 class="adm_title">예약 확정 리스트</h3>
                    <ul class="list_num10 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">No.</div>
                            <div class="list_con02">예약일</div>
                            <div class="list_con03">시간</div>
                            <div class="list_con04">이름</div>
                            <div class="list_con05">휴대폰 번호</div>
                            <div class="list_con06">체험인원</div>
                            <div class="list_con07">상태</div>
                            <div class="list_con08 list_con_select">스텝체크</div>
                            <div class="list_con09 list_con_memo">메모</div>
                            <div class="list_con10 list_con_btn">기능</div>
                        </li>
                        <c:set var="num" value="${paging.numberOfPages}"/>
                        <c:forEach items="${page.content}" var="item">
                        <li class="adm_list_con">
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
                            <div class="list_con08 list_con_select">
                                <select class="doStaffCheck" <c:if test="${(item.status.name ne 'status2' and item.status.name ne 'status4') or item.statusName eq '기간만료'}">disabled="disabled"</c:if> data-sn="<c:out value="${item.sn}"/>">
                                    <c:forEach items="${staffCheck}" var="check">
                                        <option value="<c:out value="${check.name}"/>" <c:if test="${item.staffCheck.name eq check.name}">selected="selected"</c:if>><c:out value="${check.life}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="list_con09 list_con_memo">
                                <c:if test="${empty item.memo}"><span>-</span></c:if>
                                <c:if test="${not empty item.memo}">
                                <span><c:out value="${item.memo}"/></span>
                                <div class="adm_memo_detail" id="<c:out value="memo${item.sn}"/>"><c:out value="${item.memo}"/></div>
                                </c:if>
                            </div>
                            <div class="list_con10 list_con_btn">
                                <c:if test="${item.status.name eq 'status2' and item.statusName ne '기간만료'}">
                                <button class="btn_delete btn_gray btn_m btn doCancel" data-sn="<c:out value="${item.sn}"/>">삭제</button>
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
            <div class="adm_escape_disable">
                <h3 class="adm_title">예약 불가일 설정</h3>
                <div class="date_sch_wrap">
                    <input type="datetime" id="notDatesAdd" readonly="readonly" class="input_date" placeholder="날짜 선택">
                </div>
                <!-- 날짜 선택 데이트 피커로 선택시 하단에 선택한 날짜가 적힌 태그가 추가됩니다. -->
                <div class="w100per adm_tag_wrap roundbox">
                    <c:forEach items="${notDates}" var="item">
                        <div class="tag" id="notDatesRemove<c:out value="${item.sn}"/>"><span class="disable_number">
                            <fmt:parseDate var="date" value="${item.date}" pattern="yyyyMMdd"/>
                            <fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>
                        </span><button class="tag_close notDatesRemove" data-sn="<c:out value="${item.sn}"/>"></button></div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
    <!-- pop_escape_del popup -->
    <section id="pop_escape_del" class="pop_delete pop_mindTest modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>ThinQ 방탈출 예약 권한 삭제</h2>
                    <button onClick="javascript:$('#pop_escape_del').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>예약 내역</h3>
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="agree01">예약일</span>
                            <span class="agree02">시간</span>
                            <span class="agree03">이름</span>
                            <span class="agree04">휴대폰</span>
                            <span class="agree05">동반인원</span>
                        </li>
                        <li class="content">
                            <span class="agree01" id="date0"></span>
                            <span class="agree02" id="time0"></span>
                            <span class="agree03" id="name0"></span>
                            <span class="agree04" id="phone0"></span>
                            <span class="agree05" id="memberCount0"></span>
                        </li>
                    </ul>
                </div>
                <div class="pop_delete_con">
                    <h3>삭제 사유 입력</h3>
                    <div class="row inputBox">
                        <textarea id="escape_del_textarea" cols="20" rows="4" placeholder="내용을 입력해주세요"></textarea>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="javascript:$('#pop_escape_del').hide();" class="btn_pop_s btn_lightgray btn">취소</button>
                    <button class="btn_pop_s btn_primary btn doCancelAction">삭제</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
<form id="searchForm" action="<c:url value="/m/life/weekday.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
    <input type="hidden" name="today" value="<c:out value="${param.today}"/>"/>
    <input type="hidden" name="tomorrow" value="<c:out value="${param.tomorrow}"/>"/>
    <input type="hidden" name="status0" value="<c:out value="${param.status0}"/>"/>
    <input type="hidden" name="status2" value="<c:out value="${param.status2}"/>"/>
    <input type="hidden" name="status4" value="<c:out value="${param.status4}"/>"/>
    <input type="hidden" name="status6" value="<c:out value="${param.status6}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>