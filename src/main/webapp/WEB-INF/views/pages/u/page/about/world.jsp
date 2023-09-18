<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="about_world_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-about.jsp" />
        <section class="sub_section">
            <div class="bg_world container_full">
                <div class="container wrapped_flex">
                    <div class="left">
                        <p>어느 날 금성으로부터 <br />
                            유성 하나가 떨어지고<br />
                            <br />
                            별이 떨어진 경동시장에<br />
                            세상에 없던 전파사가 생겼다<br />
                            <br />
                            푸근한 미소의 ‘금성아저씨’가 반겨주는<br />
                            금성전파사에는 신비한 비밀이 있다는데<br />
                            <br />
                            이 곳을 찾은 이들의<br />
                            지쳐있던 마음도, 구겨졌던 스타일도<br />
                            잊고 지낸 개성도, 우울했던 기분도<br />
                            몰라보게 새롭게 고쳐진다고 한다.</p>
                        <p>이렇게 전파사에서 시작된 변화는<br />
                            경동시장 전체로 퍼져<br />
                            곳곳에 활기찬 기운이 넘쳐나게 된다<br />
                            <br />
                            고치고 싶은 게 있다면<br />
                            꼭 한번 방문해 보시길.<br />
                            <br />
                            기분부터 마음까지<br />
                            무엇이든 새로 고쳐지는 이 곳.</p>
                        <p><strong>세상에 없던 전파사</strong></p>
                        <h3>금성전파사 새로고침센터</h3>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="about_place wrapped_flex">
                    <div class="left">
                        <h3 class="place_title">금성전파사 새로고침센터</h3>
                        <ul class="about_place_list">
                            <li>
                                <img src="<c:url value="/u/images/sub/icn_place_map.svg"/>" alt="">
                                <p>위치 : 서울특별시 동대문구 고산자로36길 3 경동시장 본관 3층, 4층<br />
                                    (지번) 서울특별시 동대문구 제기동 1019</p>
                            </li>
                            <li>
                                <img src="<c:url value="/u/images/sub/icn_place_metro.svg"/>" alt="">
                                <p>지하철 제기동역: 2번 출구 도보 6분<br />
                                    지하철 청량리역: 1번 출구 도보 8분</p>
                            </li>
                            <li>
                                <img src="<c:url value="/u/images/sub/icn_place_bus.svg"/>" alt="">
                                <p>경동시장앞 정류소(A)에서 도보 2분</p>
                            </li>
                            <li>
                                <img src="<c:url value="/u/images/sub/icn_place_time.svg"/>" alt="">
                                <p>기본 운영시간: 09:00~22:00<br />
                                    체험 운영시간: 11:00~19:00<br />
                                    전시 관람시간: 09:00 ~11:00 / 19:00~22:00</p>
                            </li>
                            <li>
                                <img src="<c:url value="/u/images/sub/icn_place_tel.png"/>" alt="">
                                <p>운영사무국: <a href="tel:02-3295-4947">02-3295-4947</a></p>
                            </li>
                            <li>
                                <p>* 경동시장 내부 주차가 어려우니 주변 공영 주차장을 이용해주세요.</p>
                            </li>
                        </ul>
                    </div>
                    <div class="right"><img src="<c:url value="/u/images/sub/img_about_place2.png"/>" alt="경동시장 앞 사진"></div>
                </div>
            </div>
        </section>
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <!-- script start -->
    <script>
    </script>
</body>
</html>
