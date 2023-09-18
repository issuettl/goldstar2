<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/pages/string.js?v=${versionHtml}"/>"></script>
    <script type="text/javascript" src="<c:url value="/u/js/pages/sign/name.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_name_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <section class="subGnb_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
            </div>
        </section>
        <section class="subTop_section">
            <div class="container">
                <div class="subTitle_wrap">
                    <h1 class="hiddenText">체험하기</h1>
                </div>
            </div>
        </section>
        <section class="exper_page sub_section">
            <figure></figure>
            <div class="m_container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    
                    <p class="tac">금성전파사에서<br />
                        어떤 이름으로 불리고 싶은가?</p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <div class="exper_indexText">
                    <h3 class="tac">새로고침 코-너에서
                        빛나는 별과 함께 <br />
                        당신의 닉네임을 확인해보세요</h3>
                    <div class="w100per block"><input type="text" length="5" id="nickname" placeholder="닉네임을 입력해 주세요(한글 최대 5자)" class="w100per round_input"></div>
                    <p class="text_caption tac">* 닉네임은 표준 한글만 입력 가능합니다.</p>
                </div>
                <div class="btn_full btn_wrap">
                    <button class="large_btn_black btn_ex btn">다음</button>
                </div>
            </div>
            
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
