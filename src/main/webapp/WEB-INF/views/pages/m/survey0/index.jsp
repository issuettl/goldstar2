<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/survey/index.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>사전문답 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="survey_section adm_section">
            <div class="adm_con_page roundbox">
                <div class="survey_write adm_part">
                    <h3 class="adm_title">사전문답 등록</h3>
                    <div class="adm_write">
                        <div class="row checkbox_corner">
                            <label for="">코-너</label>
                            <label for="check_corner1" class="customCheck customCheck_round">
                                <input type="radio" name="check_corner" id="check_corner1" value="mind" checked="checked">
                                <span class="checkmark"></span>
                                <span class="checkText">마음고침 코-너</span>
                            </label>
                            <label for="check_corner3" class="customCheck customCheck_round">
                                <input type="radio" name="check_corner"id="check_corner3" value="indiv">
                                <span class="checkmark"></span>
                                <span class="checkText">개성고침 코-너</span>
                            </label>
                            <label for="check_corner2" class="customCheck customCheck_round">
                                <input type="radio" name="check_corner" id="check_corner2" value="style">
                                <span class="checkmark"></span>
                                <span class="checkText">스타일고침 코-너</span>
                            </label>
                            <label for="check_corner4" class="customCheck customCheck_round">
                                <input type="radio" name="check_corner" id="check_corner4" value="mood">
                                <span class="checkmark"></span>
                                <span class="checkText">기분고침 코-너(금성오락실)</span>
                            </label>
                        </div>
                        <div class="row inputBox">
                            <label for="adm_faq_title">질문</label><input type="text" name="" id="saveQuestion" placeholder="질문을 입력해 주세요">
                        </div>
                        <div class="row_answer row inputBox">
                            <label for="adm_faq_cont">답변</label>
                            <div class="inputs saveAnswers" id="mindSaveAnswers">
                                <c:forEach items="${mindTypes}" var="item" varStatus="aIndex">
                                    <input type="text" name="mindAnswers" id="<c:out value="mindSaveAnswers${aIndex.count}"/>" data-type="<c:out value="${item.name}"/>" placeholder="(<c:out value="${item.title}"/>) 답변을 입력해 주세요" />
                                </c:forEach>
                            </div>
                            <div class="inputs saveAnswers" id="indivSaveAnswers" style="display:none;">
                                <c:forEach items="${indivTypes}" var="item" varStatus="aIndex">
                                    <input type="text" name="indivAnswers" id="<c:out value="indivSaveAnswers${aIndex.count}"/>" data-type="<c:out value="${item.name}"/>" placeholder="(<c:out value="${item.title}"/>) 답변을 입력해 주세요" />
                                </c:forEach>
                            </div>
                            <div class="inputs saveAnswers" id="styleSaveAnswers" style="display:none;">
                                <c:forEach items="${styleTypes}" var="item" varStatus="aIndex">
                                    <input type="text" name="styleAnswers" id="<c:out value="styleSaveAnswers${aIndex.count}"/>" data-type="<c:out value="${item.name}"/>" placeholder="(<c:out value="${item.title}"/>) 답변을 입력해 주세요" />
                                </c:forEach>
                            </div>
                            <div class="inputs saveAnswers" id="moodSaveAnswers" style="display:none;">
                                <c:forEach items="${moodTypes}" var="item" varStatus="aIndex">
                                    <input type="text" name="moodAnswers" id="<c:out value="moodSaveAnswers${aIndex.count}"/>" data-type="<c:out value="${item.name}"/>" placeholder="답변을 입력해 주세요" />
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="btn_wrap t_center"><button class="btn_m btn_gray btn" id="saveAnswers">등록하기</button></div>
                </div>
                
                <!-- survey_list -->
                <div class="survey_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">[마음고침 코-너] 사전문답 관리</h3>
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
                        <c:forEach items="${mindList}" var="question" varStatus="qIndex">
                        <li class="adm_list_con list_line sortQuestion mind" data-sn="<c:out value="${question.sn}"/>">
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
                                    <c:out value="${answer.type.title}"/>
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
                                <button class="btn_gray btn_s btn doRemove" data-type="mind" data-sn="<c:out value="${question.sn}"/>">삭제</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- survey_list end -->

                <!-- survey_list -->
                <div class="survey_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">[개성고침 코-너] 사전문답 관리</h3>
                        <button class="btn_bbsfx btn_m btn_gray btn sortQuestions" data-type="indiv">순서 변경</button>
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
                        <c:forEach items="${indivList}" var="question" varStatus="qIndex">
                        <li class="adm_list_con list_line sortQuestion indiv" data-sn="<c:out value="${question.sn}"/>">
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
                                    <c:out value="${answer.type.title}"/>
                                    <c:if test="${not aIndex.last}"><br></c:if>
                                </c:forEach>
                            </div>
                            <div class="list_con04 list_con_select" style="width:10%">
                                <select name="questionStatus" data-sn="<c:out value="${question.sn}"/>" data-type="indiv">
                                    <option value="Y" <c:if test="${question.status.name eq 'Y'}">selected="selected"</c:if>>ON</option>
                                    <option value="N" <c:if test="${question.status.name eq 'N'}">selected="selected"</c:if>>OFF</option>
                                </select>
                            </div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">
                                <button class="btn_primary btn_s btn doUpdate" data-type="indiv" data-sn="<c:out value="${question.sn}"/>">수정</button>
                                <button class="btn_gray btn_s btn doRemove" data-type="indiv" data-sn="<c:out value="${question.sn}"/>">삭제</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- survey_list end -->

                <!-- survey_list -->
                <div class="survey_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">[스타일고침 코-너] 사전문답 관리</h3>
                        <button class="btn_bbsfx btn_m btn_gray btn sortQuestions" data-type="style">순서 변경</button>
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
                        <c:forEach items="${styleList}" var="question" varStatus="qIndex">
                        <li class="adm_list_con list_line sortQuestion style" data-sn="<c:out value="${question.sn}"/>">
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
                                    <c:out value="${answer.type.title}"/>
                                    <c:if test="${not aIndex.last}"><br></c:if>
                                </c:forEach>
                            </div>
                            <div class="list_con04 list_con_select" style="width:10%">
                                <select name="questionStatus" data-sn="<c:out value="${question.sn}"/>" data-type="style">
                                    <option value="Y" <c:if test="${question.status.name eq 'Y'}">selected="selected"</c:if>>ON</option>
                                    <option value="N" <c:if test="${question.status.name eq 'N'}">selected="selected"</c:if>>OFF</option>
                                </select>
                            </div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">
                                <button class="btn_primary btn_s btn doUpdate" data-type="style" data-sn="<c:out value="${question.sn}"/>">수정</button>
                                <button class="btn_gray btn_s btn doRemove" data-type="style" data-sn="<c:out value="${question.sn}"/>">삭제</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- survey_list end -->

                <!-- survey_list -->
                <div class="survey_list adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">[기분고침 코-너] 사전문답 관리</h3>
                        <button class="btn_bbsfx btn_m btn_gray btn sortQuestions" data-type="mood">순서 변경</button>
                    </div>
                    <ul class="list_num5 adm_sortable adm_list">
                        <li class="adm_list_title ui-state-disabled">
                            <div class="list_con01" style="width:5%">NO.</div>
                            <div class="list_con02" style="width:30%">질문</div>
                            <div class="list_con03" style="width:30%">답변</div>
                            <div class="list_con04 list_con_select" style="width:15%">상태</div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">기능</div>
                        </li>
                        <c:forEach items="${moodList}" var="question" varStatus="qIndex">
                        <li class="adm_list_con list_line sortQuestion mood" data-sn="<c:out value="${question.sn}"/>">
                            <div class="list_con01" style="width:5%"><c:out value="${qIndex.count}"/></div>
                            <div class="list_con02 list_bbs_view" style="width:30%"><c:out value="${question.question}"/></div>
                            <div class="list_con03 list_bbs_view" style="width:30%">
                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                    <c:out value="${answer.name}"/>
                                    <c:if test="${not aIndex.last}"><br></c:if>
                                </c:forEach>
                            </div>
                            <div class="list_con04 list_con_select" style="width:15%">
                                <select name="questionStatus" data-sn="<c:out value="${question.sn}"/>" data-type="mood">
                                    <option value="Y" <c:if test="${question.status.name eq 'Y'}">selected="selected"</c:if>>ON</option>
                                    <option value="N" <c:if test="${question.status.name eq 'N'}">selected="selected"</c:if>>OFF</option>
                                </select>
                            </div>
                            <div class="list_con05 list_btn_wrap" style="width:20%">
                                <button class="btn_primary btn_s btn doUpdate" data-type="mood" data-sn="<c:out value="${question.sn}"/>">수정</button>
                                <button class="btn_gray btn_s btn doRemove" data-type="mood" data-sn="<c:out value="${question.sn}"/>">삭제</button>
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
                    <h2 id="updateTitle">[마음고침 코-너] 사전문답 수정</h2>
                    <button onClick="javascript:$('#pop_survey_mod1').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3 class="d_inline_block">코너</h3>
                    <span class="pop_span" id="updateCorner">마음고침 코-너</span>
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

    <!-- pop_survey_del1 (문답 삭제 팝업) popup -->
    <section id="pop_survey_del1" class="pop_bbs_del modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2 id="removeTitle">[마음고침 코-너] 사전문답 삭제</h2>
                    <button onClick="javascript:$('#pop_survey_del1').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3 class="d_inline_block">코너</h3>
                    <span class="pop_span" id="removeCorner">마음고침 코-너</span>
                </div>
                <div class="pop_delete_con">
                    <h3 class="d_inline_block">제목</h3>
                    <span class="pop_span" id="removeQuestion"></span>
                </div>
                <div class="pop_delete_con" id="removeAnswers">
                    <h3 class="d_inline_block">답변</h3>
                    <span class="pop_span">기능</span>
                    <span class="pop_div">디자인</span>
                    <span class="pop_div">새로움</span>
                    <span class="pop_div">답변</span>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button id="removeButton" class="btn_pop_s btn_primary btn">삭제</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    
    <!-- 삭제 커스텀 다이알로그 -->
    <section id="dialog_delete" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>삭제하시겠습니까?</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">취소</button>
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">삭제</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
<form id="searchForm" action="<c:url value="/m/survey/index.do"/>" method="post">
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
</html>