<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/exp/gender.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_gender">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-exp.jsp" />
        
        <section class="regi2023 exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    <p class="tac">자네의 성별은 무엇인가?</p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <!-- 성별 선택 -->
                <!-- 코너 추천 리스트 -->
                <div class="exper_worry">
                    <ul class="worry_list worry_list_gender">
                        <c:forEach items="${genders}" var="item">
                        <li>
                            <label for="<c:out value="worry_radio_gender_${item.name}"/>" class="worry_radio">
                                <input type="radio" name="worry_radio_gender" id="<c:out value="worry_radio_gender_${item.name}"/>" value="<c:out value="${item.name}"/>">
                                <span class="worry_option">
                                    <h4><c:out value="${item.title}"/></h4>
                                </span>
                            </label>
                        </li>
                        </c:forEach>
                    </ul>
                    <div class="tac btn_wrap">
                        <button class="large_btn_black btn_round_m btn">다음</button>
                    </div>
                </div>
                <!-- 코너 추천 리스트 end -->
                <!-- 성별 선택 end -->
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
