<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="about_uncle_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-about.jsp" />
        <section class="sub_section">
            <div class="bg_uncle container_full">
                <!-- 2022-12-06 수정 : 디자인 변경으로 구조 변경 -->
                <div class="uncle_img_pc pc_view tac">
                    <img src="<c:url value="/u/images/sub/bg_uncle2.jpg"/>" alt="">
                </div>
                <div class="container">
                    <div class="uncle_img_mo mo_view">
                        <img src="<c:url value="/u/images/sub/img_uncle_mo.png"/>" alt="" class="">
                    </div>
                </div>
            </div>
        </section>
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
