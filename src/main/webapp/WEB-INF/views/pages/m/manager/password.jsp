<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/manager/password.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <section class="login_section">
        <div class="login_wrap">
            <h1 class="logo"><img src="<c:url value="/m/images/logo_primary.svg"/>" alt="금성전파사"></h1> 
            <div class="inputBox">
                <!-- 2022-11-25 수정 -->
                <label for="adminID" class="label_s">아이디</label>
                <input type="text" id="adminID" class="border_input" placeholder="아이디를 입력해주세요.">
            </div>
            <div class="inputBox">
                <label for="adminPW1" class="label_s">현재 비밀번호</label>
                <input type="password" id="adminPW1" placeholder="비밀번호를 입력해주세요." class="input_pw border_input">
                <span class="pw_eye"></span>
            </div>
            <div class="inputBox">
                <label for="adminPW2" class="label_s">변경할 비밀번호</label>
                <input type="password" id="adminPW2" placeholder="비밀번호를 입력해주세요." class="input_pw border_input">
                <span class="pw_eye"></span>
            </div>
            <div class="inputBox">
                <label for="adminPW3" class="label_s">비밀번호 확인</label>
                <input type="password" id="adminPW3" placeholder="비밀번호를 입력해주세요." class="input_pw border_input">
                <span class="pw_eye"></span>
            </div>
            <div class="w100per btnWrap"><button id="signin-action" class="btn_arrow btn_primary btn">비밀번호 변경</button></div>
        </div>
    </section>
    <!-- Master admin : Hsad, SPLAB, KPR -->
    <script>
        
    </script>
    <!-- login popup start -->
    <section id="pop_login" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p class="resultTxt"></p>
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