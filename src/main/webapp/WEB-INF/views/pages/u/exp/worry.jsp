<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/worry.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_worry_select_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-exp.jsp" />
        
        <section class="regi2023 exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    <!-- 2023-09-12 수정 : 텍스트 변경 -->
                    <p class="tac">자네가 어떤 성향의 사람인지 궁금하지 않은가?
                        자네의 솔직한 생각을 아래에서 한번 골라 보시게!</p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <c:forEach items="${questions}" var="q" varStatus="qIndex">
                <!-- <c:out value="${qIndex.count}"/>번 질문 -->
                <div class="exper_Q<c:out value="${qIndex.count}"/> exper_step exper_worry"
                    <c:choose>
                        <c:when test="${qIndex.first}">
                             style="display: block;"
                        </c:when>
                        <c:otherwise>
                             style="display: none;"
                        </c:otherwise>
                    </c:choose>
                >
                    <h3 class="worry_step_title"><c:out value="${qIndex.count}"/>. <c:out value="${q.question}"/></h3>
                    <ul class="worry_list worry_list_2023" data-name="worry_step<c:out value="${qIndex.count}"/>">
                        <c:forEach items="${q.answers}" var="a" varStatus="aIndex">
                        <li>
                            <!-- 1개밖에 선택 불능해서 라디오로 넣었습니다 -->
                            <label for="worry_radio_step<c:out value="${qIndex.count}_${aIndex.count}"/>" class="worry_radio">
                                <input type="radio" name="worry_step<c:out value="${qIndex.count}"/>" id="worry_radio_step<c:out value="${qIndex.count}_${aIndex.count}"/>" value="<c:out value="${a.sn}"/>">
                                <span class="worry_option">
                                    <h4><c:out value="${a.name}"/></h4>
                                </span>
                            </label>
                        </li>
                        </c:forEach>
                    </ul>
                    <div class="tac btn_wrap">
                        <c:if test="${not qIndex.first}">
                        <button onClick="pursuePrev(<c:out value="${qIndex.count - 1}"/>);" class="large_btn_line btn_round_m btn">이전</button>
                        </c:if>
                        <c:if test="${not qIndex.last}">
                        <button onClick="pursueNext(<c:out value="${qIndex.count}"/>);" class="large_btn_black btn_round_m btn">다음</button>
                        </c:if>
                        <c:if test="${qIndex.last}">
                        <button onClick="pursueSave();" class="large_btn_black btn_round_m btn">설문 완료</button>
                        </c:if>
                    </div>
                </div>
                <!-- <c:out value="${qIndex.count}"/>번 질문 end -->
                </c:forEach>
                
                <!-- 코너 추천 리스트 end -->
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
