<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
</head>
<body class="about_products_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <c:import url="/WEB-INF/views/layouts/u/gnb-sub.jsp" />
        <c:import url="/WEB-INF/views/layouts/u/top-about.jsp" />
        <section class="sub_section">
            <div class="container">
                <h2 class="hiddenText">금성전파사 내 전시제품</h2>
                <!-- 전시제품 -->
                <div class="products_wrap">
                    <!-- 전시제품 슬라이더 start -->
                    <div class="products_slide">
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onclick="coverMap(['01','02','03','04','06'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_01.png"/>" alt="">
                                <h3>LG 스탠바이미</h3>
                                <button onclick="window.open('https://www.lge.co.kr/tvs/27art10akpl')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['01'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_02.png"/>" alt="">
                                <h3>LG 틔운 미니</h3>
                                <button onclick="window.open('https://www.lge.co.kr/lg-tiiun/l012e1')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['01'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_03.png"/>" alt="">
                                <h3>LG 디오스 오브제컬렉션 무드업 (노크온)</h3>
                                <button onclick="window.open('https://www.lge.co.kr/refrigerators/m623gnn392')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 (이미지 미도착) -->
                        <div class="slide">
                            <figure onClick="coverMap(['01'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_04.png"/>" alt="">
                                <h3>LG 디오스 오브제컬렉션 김치톡톡 무드업</h3>
                                <button onclick="window.open('https://www.lge.co.kr/kimchi-refrigerators/z331gnn152')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['01','05'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_05.png"/>" alt="">
                                <h3>LG Ultra Tab</h3>
                                <button onclick="window.open('https://www.lge.co.kr/notebook/10a30q-lq14k')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['04'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_06.png"/>" alt="">
                                <h3>LG 올레드 TV (벽걸이형)</h3>
                                <button onclick="window.open('https://www.lge.co.kr/tvs/oled55a2ena-wall')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['03'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_07.png"/>" alt="">
                                <h3>그램 16인치</h3>
                                <button onclick="window.open('https://www.lge.co.kr/notebook/16z90q-ea5wk')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['03'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_08.png"/>" alt="">
                                <h3>LG 그램 +view</h3>
                                <button onclick="window.open('https://www.lge.co.kr/notebook/16mq70')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['03'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_09.png"/>" alt="">
                                <h3>LG 그램 360</h3>
                                <button onclick="window.open('https://www.lge.co.kr/notebook/16t90q-ga7bk')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['02'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_10.png"/>" alt="">
                                <h3>LG 스타일러 오브제컬렉션</h3>
                                <button onclick="window.open('https://www.lge.co.kr/lg-styler/s5bbpu')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->



                        <!-- 4층 -->




                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_11.png"/>" alt="">
                                <h3>LG 울트라 HD TV (벽걸이형)</h3>
                                <button onclick="window.open('https://www.lge.co.kr/tvs/43uq9300kna-wall')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_12.png"/>" alt="">
                                <h3>LG 울트라 PC</h3>
                                <button onclick="window.open('https://www.lge.co.kr/notebook/15u40q-gr3dk')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_13.png"/>" alt="">
                                <h3>LG 엑스붐 360</h3>
                                <button onclick="window.open('https://www.lge.co.kr/home-audio/xo3qbe')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_14.png"/>" alt="">
                                <h3>LG 디오스 오브제컬렉션 무드업 (베이직)</h3>
                                <button onclick="window.open('https://www.lge.co.kr/refrigerators/m623gnn092')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_15.png"/>" alt="">
                                <h3>LG 퓨리케어 오브제컬렉션 에어로타워</h3>
                                <button onclick="window.open('https://www.lge.co.kr/aerotower/fs061psgc-c')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_16.png"/>" alt="">
                                <h3>LG휘센 오브제컬렉션 엣지</h3>
                                <button onclick="window.open('https://www.lge.co.kr/air-conditioners/wq06dcba45')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_17.png"/>" alt="">
                                <h3>LG 틔운 오브제 컬렉션</h3>
                                <button onclick="window.open('https://www.lge.co.kr/lg-tiiun/l061g1pn')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->
                        <!-- 전시제품 -->
                        <div class="slide">
                            <figure onClick="coverMap(['07'])" class="products">
                                <img src="<c:url value="/u/images/sub/img_product_18.png"/>" alt="">
                                <h3>LG 트롬 오브제컬렉션 워시타워</h3>
                                <button onclick="window.open('https://www.lge.co.kr/wash-tower/w20egzm')" class="btn_black btn">상세 보기</button>
                            </figure>
                        </div>
                        <!-- 전시제품 end -->

                    </div>
                    <!-- 전시제품 슬라이더 end -->
                    <div class="products_map">
                        <h3 class="products_map_title">전시제품 위치</h3>
                        <div class="products_graymap">
                            <p class="graymap_title tac"><span class="spot spot06">고민 접수처</span><span class="spot spot01">마음고침 코-너</span><span class="spot spot02">스타일고침 코-너</span><span class="spot spot03">개성고침 코-너</span><span class="spot spot04">기분고침 코-너</span><span class="spot spot05">새로고침 코-너</span><span class="spot spot07">고민탈출 코-너
                                (ThinQ 방탈출)</span></p>
                            <figure class="map_all">
                                <!-- figure bg로 전체 맵 깔고 밑에 애들은 display none 상태 -->
                                <img src="<c:url value="/u/images/sub/img_store_full.png"/>" alt="" class="map_all_img pc_view">
                                <img src="<c:url value="/u/images/sub/img_store_full_mo.png"/>" alt="" class="map_all_img mo_view">
                                <img src="<c:url value="/u/images/sub/corner_refresh.svg"/>" alt="마음고침 코너 위치" class="spot spot01">
                                <img src="<c:url value="/u/images/sub/corner_style.svg"/>" alt="스타일고침  코너 위치" class="spot spot02">
                                <img src="<c:url value="/u/images/sub/corner_personality.svg"/>" alt="개성 고침 코너 위치" class="spot spot03">
                                <img src="<c:url value="/u/images/sub/corner_mind.svg"/>" alt="기분 고침 코너 위치" class="spot spot04">
                                <img src="<c:url value="/u/images/sub/corner_star.svg"/>" alt="새로고침 코너 위치" class="spot spot05">
                                <img src="<c:url value="/u/images/sub/corner_info.svg"/>" alt="고민접수처 위치" class="spot spot06">
                                <img src="<c:url value="/u/images/sub/img_escape_poster2.png"/>" alt="포스터" class="spot spot07 pc_view">
                                <img src="<c:url value="/u/images/sub/img_escape_poster2_products.png"/>" alt="포스터" class="spot spot07 mo_view">
                                <img src="<c:url value="/u/images/sub/img_store_full_text.png"/>" alt="" class="map_all_img map_all_img3 pc_view">
                                <img src="<c:url value="/u/images/sub/img_store_full_mo_text.png"/>" alt="" class="map_all_img map_all_img3 mo_view">
                            </figure>
                        </div>
                        <div class="btn_full tac btn_wrap">
                            <button onclick="window.open('https://www.lge.co.kr/bestshop/visit-store-reservation')" class="btn_black btn">매장 상담 예약</button>
                        </div>
                        <div class="products_info_gray">
                            <h4>[유의사항]</h4>
                            <ul class="sub_dot_list">
                                <li>금성전파사 새로고침센터는 별도로 LG전자 제품을 판매하지 않습니다. </li>
                                <li>제품 구매를 희망하시는 경우, 매장 상담 예약 후 방문하시면 전문 컨설팅을 받으실 수 있습니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 전시제품 end -->
            </div>
        </section>
    </div>
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <!-- script start -->
    <script>
        $(document).ready(function(){
            $('.products_slide').slick({
                infinite: true,
                slidesToShow: 5,
                slidesToScroll: 1,
                dots: true,
                arrows: true,
                fade: false,
                responsive: [
                    {
                    breakpoint: 1200,
                    settings: {
                        slidesToShow: 4
                    }
                    },
                    {
                    breakpoint: 840,
                    settings: {
                        slidesToShow: 3,
                        slidesToScroll: 2,
                        variableWidth: true,
                        arrows: false,
                        dots: false
                    }
                    }
                ]
            });
        });

        /* 전시제품 위치
        01 마음고침 코너 위치
        02 스타일고침  코너 위치
        03 개성 고침 코너 위치
        04 기분 고침 코너 위치
        05 새로고침 코너 위치
        06 고민접수처 위치
         */
        /* 스탠바이미 */
        function removeMap() {
            $('.spot').removeClass('active');
            $('.products').removeClass('active');
        }
        function coverMap(arr) {
            removeMap();
            event.target.className += " active"; 
            arr.forEach((element) => {
                if (element === '07') {
                    $('.spot'+element).addClass('active');
                    $('.map_all_img').hide();
                    $('.products_graymap .text_caption').hide();
                } else {
                    $('.spot'+element).addClass('active');
                    $('.map_all_img').show();
                    $('.products_graymap .text_caption').show();
                }
                
                
            });
            
        }
    </script>
</body>
</html>
