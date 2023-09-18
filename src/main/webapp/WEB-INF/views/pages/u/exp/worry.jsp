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
        
        <section class="exper_page sub_section">
            <figure></figure>
            <div class="container">
                <!-- 새로고침 공통 타이틀 변형 -->
                <div class="tal exper_chatTitle">
                    <p class="tac">자네의 솔직한 고민을 <br class="mo_br" />아래에서 한번 골라 보시게!<br />
                        고칠 수 있는 방법을 알려주겠네!
                    </p>
                </div>
                <div class="exper_person"></div>
                <!--// 새로고침 공통 타이틀 End -->
                <!-- 코너 추천 리스트 -->
                <div class="exper_worry">
                    <ul class="worry_list">
                        <li>
                            <!-- 우선 input을 checkbox로 구현했으나, 실제로는 여기서 2개밖에 선택할 수 없습니다. -->
                            <label for="worry_radio1_1" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_1" value="type1">
                                <span class="worry_option">
                                    <h4>믿음과 신뢰의 고민</h4>
                                    <p>마음 놓고 속마음을 <br class="pc_br" />털어놓고 싶어요</p>
                                </span>
                            </label>
                        </li>
                        <li>
                            <label for="worry_radio1_2" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_2" value="type2">
                                <span class="worry_option">
                                    <h4>치열한 일상의 고민</h4>
                                    <p>답답한 일상을 벗어나 해방감을 느끼고 싶어요</p>
                                </span>
                            </label>
                        </li>
                        <li>
                            <label for="worry_radio1_3" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_3" value="type3">
                                <span class="worry_option">
                                    <h4>건강한 삶에 대한 고민</h4>
                                    <p>맑은 공기를 마시며 <br class="pc_br" />
                                        재충전을 하고 싶어요</p>
                                </span>
                            </label>
                        </li>
                        <li>
                            <label for="worry_radio1_4" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_4" value="type4">
                                <span class="worry_option">
                                    <h4>미래에 대한 고민</h4>
                                    <p>막막한 오늘을 벗어나 <br class="pc_br" />찬란한 미래를 꿈꾸고 싶어요</p>
                                </span>
                            </label>
                        </li>
                        <li>
                            <label for="worry_radio1_5" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_5" value="type5">
                                <span class="worry_option">
                                    <h4>도전과 용기에 대한 고민</h4>
                                    <p>나에게 닥친 고난과 역경을 이겨내고 싶어요</p>
                                </span>
                            </label>
                        </li>
                        <li>
                            <label for="worry_radio1_6" class="worry_radio">
                                <input type="checkbox" name="worry_radio1" id="worry_radio1_6" value="type6">
                                <span class="worry_option">
                                    <h4>잃어버린 꿈에 대한 고민</h4>
                                    <p>잃어버린 꿈과 희망을 <br class="pc_br" />다시 한번 떠올리고 싶어요</p>
                                </span>
                            </label>
                        </li>
                    </ul>
                    <!-- 2022-11-03 수정 -->
                    <div class="tac btn_wrap">
                        <button class="large_btn_black btn_round_m btn">코-너 추천 받기</button>
                    </div>
                </div>
                <!-- 코너 추천 리스트 end -->
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
</body>
</html>
