<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/manager/in.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <section class="login_section">
        <div class="login_wrap">
            <h1 class="logo"><img src="<c:url value="/m/images/logo_primary.svg"/>" alt="금성전파사"></h1> 
            <div class="inputBox">
                <!-- 2022-11-25 수정 -->
                <label for="adminID" class="label_s">아이디</label>
                <input type="text" id="adminID" class="border_input" placeholder="아이디를 입력해주세요." value="">
            </div>
            <div class="inputBox">
                <label for="adminPW" class="label_s">비밀번호</label>
                <input type="password" id="adminPW" placeholder="비밀번호를 입력해주세요." class="input_pw border_input" value="">
                <span class="pw_eye"></span>
            </div>
            <div class="w100per btnWrap"><button id="signin-action" class="btn_arrow btn_primary btn">로그인</button></div>
        </div>
    </section>
    <input type="hidden" id="returnURI" value="<c:out value="${param.returnURI}"/>"/>
    <!-- Master admin : Hsad, SPLAB, KPR -->
    <script>
    
    </script>
    <!-- login popup start -->
    <section id="pop_login" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p class="resultTxt">아이디 또는 비밀번호가 잘못 입력되었습니다.</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button id="pop_click" onclick="popClose()" class="btn_pop_s btn_primary btn">닫기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>