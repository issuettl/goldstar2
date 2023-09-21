<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/pursue/index.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>설문조사 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="survey_section adm_section">
            <div class="adm_con_page roundbox">
                
                <!-- survey_list -->
                <div class="survey_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">고객 유형 문답 관리</h3>
                        <button class="btn_bbsfx btn_m btn_gray btn sortQuestions" data-type="mind">순서 변경</button>
                    </div>
                    <ul class="list_num5 adm_sortable adm_list">
                        <li class="adm_list_title ui-state-disabled">
                            <div class="list_con01" style="width:5%">NO.</div>
                            <div class="list_con02" style="width:30%">질문</div>
                            <div class="list_con03" style="width:25%">답변</div>
                            <div class="list_con03" style="width:10%">답변타입</div>
                            <div class="list_con04 list_con_select" style="width:10%">상태</div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">기능</div>
                        </li>
                        <c:forEach items="${pursueList}" var="question" varStatus="qIndex">
                        <li class="adm_list_con list_line sortQuestion" data-sn="<c:out value="${question.sn}"/>">
                            <div class="list_con01" style="width:5%"><c:out value="${qIndex.count}"/></div>
                            <div class="list_con02 list_bbs_view" style="width:30%"><c:out value="${question.question}"/></div>
                            <div class="list_con03 list_bbs_view" style="width:25%">
                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                    <c:out value="${answer.name}"/>
                                    <c:if test="${not aIndex.last}"><br></c:if>
                                </c:forEach>
                            </div>
                            <div class="list_con03 list_bbs_view" style="width:10%">
                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                    <c:out value="${answer.type1.title}"/>
                                    <c:out value="${answer.type2.title}"/>
                                    <c:if test="${not aIndex.last}"><br></c:if>
                                </c:forEach>
                            </div>
                            <div class="list_con04 list_con_select" style="width:10%">
                                <select name="questionStatus" data-sn="<c:out value="${question.sn}"/>" data-type="mind">
                                    <option value="Y" <c:if test="${question.status.name eq 'Y'}">selected="selected"</c:if>>ON</option>
                                    <option value="N" <c:if test="${question.status.name eq 'N'}">selected="selected"</c:if>>OFF</option>
                                </select>
                            </div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">
                                <button class="btn_primary btn_s btn doUpdate" data-type="mind" data-sn="<c:out value="${question.sn}"/>">수정</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- survey_list end -->
            </div>
        </section>
    </main>
    <!-- pop_survey_mod1 popup -->
    <section id="pop_survey_mod1" class="pop_survey modal">
        <div class="popBg">
            <!-- 2022-12-20 수정 : 디자인 자체가 바뀌어서 퍼블 다 수정했습니다 다 뜯어보셔야합니다.ㅠ -->
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2 id="updateTitle">고객 유형 문답 수정</h2>
                    <button onClick="javascript:$('#pop_survey_mod1').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>질문</h3>
                    <div class="row inputBox">
                        <input type="text" name="" id="updateQuestion" class="w100per line_input" placeholder="" value=""/>
                    </div>
                </div>
                <div class="pop_survey_con" id="updateAnswers">
                    <h3>답변</h3>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button id="updateButton" class="btn_pop_s btn_primary btn">완료</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->

    <c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>