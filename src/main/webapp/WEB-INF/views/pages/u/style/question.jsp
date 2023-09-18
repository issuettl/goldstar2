<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 본 페이지는 사전 문답 팝업에 들어가는 내용만 정리한 페이지입니다.
인터랙션 스크립트는 팝업 내부의 이동 스크립트밖에 없습니다. -->
<!-- popup start -->
<section id="styleQuestion" class="pop_mindTest modal">
    <div class="popBg">
        <div class="popContent">
            <div class="popTitle_wrap">
                <h2><c:out value="${corner.title}"/> 코-너</h2>
                <button onClick="popClose()" class="popup_close"></button>
            </div>
            <!-- 질문 start -->
            <div id="styleQuestionZone" class="con_mindTest questionZone">
                <div class="mindTest_about">
                    <figure><img src="<c:url value="/u/images/sub/img_character_03.png"/>" alt=""></figure>
                    <div class="test_about_right">
                        <h3><c:out value="${corner.title}"/> 코-너에 온 것을 환영합니다!</h3>
                        <p>다음의 <c:out value="${fn:length(questions)}"/>가지 문답을 진행해주겠나?</p>
                    </div>
                </div>
                <c:forEach items="${questions}" var="q" varStatus="qIndex">
                    <div id="<c:out value="styleQuestions${qIndex.count}"/>" class="mind_Q<c:out value="${qIndex.count}"/> mindTest_q styleQuestions" data-answers="<c:out value="styleAnswers-${q.sn}"/>"
                    <c:choose><c:when test="${qIndex.count == 1}">style="display:block;" </c:when><c:otherwise>style="display:none;" </c:otherwise></c:choose>
                    >
	                    <h4><b>Q<c:out value="${qIndex.count}"/></b><c:out value="${q.question}"/></h4>
	                    <ul class="mindTest_list number<c:out value="${fn:length(q.answers)}"/>">
	                       <c:forEach items="${q.answers}" var="a" varStatus="aIndex">
	                        <li>
	                            <label for="<c:out value="styleAnswer-${a.sn}"/>" class="test_radio">
	                                <input type="radio" name="<c:out value="styleAnswers-${q.sn}"/>" id="<c:out value="styleAnswer-${a.sn}"/>" value="<c:out value="${a.sn}"/>">
	                                <span class="mindTest_option"><c:out value="${a.name}"/></span>
	                            </label>
	                        </li>
	                        </c:forEach>
	                    </ul>
	                    <div class="btn_wrap">
                            <c:if test="${qIndex.count == 1}">
                            <button onClick="showQuestion('<c:out value="styleQuestions${qIndex.count + 1}"/>', 'style');" class="full btn_primary btn_pop_s">다음</button>
                            </c:if>
                            <c:if test="${qIndex.count > 1}">
                            <button onClick="showQuestion('<c:out value="styleQuestions${qIndex.count - 1}"/>', 'style');" class="half btn_gray btn_pop_s">뒤로가기</button>
                            <button onClick="showQuestion('<c:out value="styleQuestions${qIndex.count + 1}"/>', 'style');" class="half btn_primary btn_pop_s">다음</button>
                            </c:if>
	                    </div>
	                </div>
                </c:forEach>
            </div>
            <!-- 질문 end -->
            <!-- 결과 A1 start -->
            <div id="styleResult" class="con_mindResult questionResult">
                <div class="mindReslut_about tac">
                    <figure class="mind01_result1"><img id="styleResultImage" src="<c:url value="/u/images/popup/mind_result_11.png"/>" alt=""></figure>
                    <div class="styleReslut_text">
                        <h3 id="styleResultTitle"></h3>
                        <p id="styleResultRecommend"></p>
                    </div>
                </div>
                <!-- 답변지 qr -->
                <div class="qr_wrap">
                    <figure class="qr_figure">
                        <img src="<c:url value="/u/exp/qr.do"/>" alt="">
                    </figure>
                    <p id="styleResultTodo"></p>
                    <!-- 2022-11-03 수정 -->
                    <!-- QR 완료시 노출
                    <div class="qr_completion">
                        <p>이미 0000.00.00 리워드를 수령했습니다. <br />
                            증정 30일 이후에 추가 수령이 가능합니다.</p>
                    </div>
                    QR 완료시 노출 end -->
                </div>
                <div class="btn_wrap tac">
                    <button onClick="document.location.reload();" class="btn_primary btn_pop_s">종료</button>
                </div>
            </div>
            <!-- 결과 A1 end -->
        </div>
    </div>
    <div class="dim"></div>
</section>

<!-- //popup end -->