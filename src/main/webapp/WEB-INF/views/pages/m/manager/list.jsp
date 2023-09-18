<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/manager/list.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>관리자 설정</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <div class="admpeople_section adm_section">
            <!-- topsch_area : 어드민 상단 검색 레이아웃만 동일하고, 이 페이지에서는 검색이 아닌 등록 기능이 구현되어야합니다. -->
            <div class="topsch_area wrapped_flex">
                <h3 class="adm_title mb15">아이디</h3><div class="inputBox sch_wrap"><input type="text" id="saveUid" placeholder="아이디를 입력해주세요" value=""></div>
                <h3 class="adm_title mb15">비밀번호</h3><div class="inputBox sch_wrap"><input type="password" id="savePassword" placeholder="비밀번호를 입력해주세요" value=""></div>
                <h3 class="adm_title mb15">업체명</h3><div class="inputBox sch_wrap"><input type="text" id="saveCompany" placeholder="업체명을 입력해주세요" value=""></div>
                <h3 class="adm_title mb15">담당자</h3><div class="inputBox sch_wrap"><input type="text" id="saveName" placeholder="담당자를 입력해주세요" value=""></div>
            </div>
            <div class="topsch_area topsch2 wrapped_flex">
                <h3 class="adm_title mb15">열람 메뉴</h3>
                <div class="roundbox">
                    <!-- 2022-11-25 수정 : 죄송해요ㅠ 이거 radio가 아니라 checkbox로 중복 선택 가능하고, 전체 클릭하면 전체선택 되야해요ㅠ
                    제가 확인 용도로 임시 스크립트 넣어놓았습니다 -->
                    <label for="saveAuthAll" class="customCheck customCheck_round">
                        <input type="checkbox" id="saveAuthAll">
                        <span class="checkmark"></span>
                        <span class="checkText">전체</span>
                    </label>
                    <c:forEach items="${managerAuth}" var="auth">
                    <label for="save<c:out value="${auth.name}"/>" class="customCheck customCheck_round">
                        <input type="checkbox" class="saveAuthAll" id="save<c:out value="${auth.name}"/>" value="<c:out value="${auth.name}"/>">
                        <span class="checkmark"></span>
                        <span class="checkText"><c:out value="${auth.title}"/></span>
                    </label>
                    </c:forEach>
                </div>
                <button class="btn_date btn_gray btn" id="doSave">등록하기</button>
            </div>
            <!-- topsch_area end -->
            <div class="adm_con_page roundbox">
                <div class="admpeople_list adm_part">
                    <h3 class="adm_title">관리자 리스트</h3>
                    <ul class="list_num10 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">No.</div>
                            <div class="list_con02">ID</div>
                            <div class="list_con03">업체명</div>
                            <div class="list_con04">담당자</div>
                            <div class="list_con05">열람메뉴</div>
                            <div class="list_con06">등록일</div>
                            <div class="list_con07 list_con_btn">기능</div>
                        </li>
	                    <c:set var="num" value="${paging.numberOfPages}"/>
	                    <c:forEach items="${page.content}" var="item">
                        <li class="adm_list_con">
                            <div class="list_con01"><c:out value="${num}" /></div>
                            <div class="list_con02"><c:out value="${item.uid}"/></div>
                            <div class="list_con03"><c:out value="${item.company}"/></div>
                            <div class="list_con04"><c:out value="${item.name}"/></div>
                            <div class="list_con05">
                                <c:choose>
                                    <c:when test="${fn:length(item.authList) == 7}">전체</c:when>
                                    <c:otherwise>
		                                <c:forEach items="${item.authList}" var="auth" varStatus="index">
		                                    <c:out value="${auth.id.auth.title}"/><c:if test="${not index.last}">, </c:if>
		                                </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="list_con06">
                                <fmt:parseDate var="created" value="${item.created}" pattern="yyyyMMddHHmmss"/>
                                <fmt:formatDate value="${created}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="list_con07 list_con_btn">
                                <!-- 삭제 버튼을 누르면 각 예약자에 대한 정보가 담긴 팝업이 뜬 후에 삭제 이유를 넣고 팝업 내 삭제하기 버튼 누를 시 정보가 삭제됩니다. -->
                                <button class="btn_delete btn_gray btn_m btn doDelete" data-sn="<c:out value="${item.sn}"/>">삭제</button>
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
    <!-- pop_admpeople_del popup -->
    <section id="pop_admpeople_del" class="pop_delete pop_people modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>관리자 삭제</h2>
                    <button onClick="javascript:$('#pop_admpeople_del').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="agree01">ID</span>
                            <span class="agree02">업체명</span>
                            <span class="agree03">담당자</span>
                            <span class="agree04">열람메뉴</span>
                            <span class="agree05">등록일</span>
                        </li>
                        <li class="content">
                            <span class="agree01">kprl</span>
                            <span class="agree02">kprl</span>
                            <span class="agree03">brightbell</span>
                            <span class="agree04">전체</span>
                            <!-- 2022-11-25 수정 : 날짜 구조 통일 2022-11-22 이런 형태로 전부 통일 부탁드립니다. -->
                            <span class="agree05">2022-11-16</span>
                        </li>
                    </ul>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="javascript:$('#pop_admpeople_del').hide();" class="btn_pop_s btn_lightgray btn">취소하기</button>
                    <button onclick="javascript:$('#pop_admpeople_del').hide();" class="btn_pop_s btn_primary btn">삭제하기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <script>
        /* 전체 선택 임시 제작 */
        $(function () {
            $("#admgrade_check2_1").change(function () {
                if ($("#admgrade_check2_1").prop('checked',true)) {
                    $("#admgrade_check2_2").prop('checked',true);
                    $("#admgrade_check2_3").prop('checked',true);
                    $("#admgrade_check2_4").prop('checked',true);
                    $("#admgrade_check2_5").prop('checked',true);
                    $("#admgrade_check2_6").prop('checked',true);
                    $("#admgrade_check2_7").prop('checked',true);
                } else {
                    $("#admgrade_check2_2").prop('checked',false);
                    $("#admgrade_check2_3").prop('checked',false);
                    $("#admgrade_check2_4").prop('checked',false);
                    $("#admgrade_check2_5").prop('checked',false);
                    $("#admgrade_check2_6").prop('checked',false);
                    $("#admgrade_check2_7").prop('checked',false);
                    $("#admgrade_check2_1").prop('checked',false);
                }
                return false;
            });
        });
    </script>
</body>
<form id="searchForm" action="<c:url value="/m/manager/list.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</html>