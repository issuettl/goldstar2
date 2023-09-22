<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/index.js?v=${versionHtml}"/>"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>대시보드</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="dashboard_section adm_section">
            <div class="topsch_area wrapped_flex">
                <h3 class="adm_title mb15">통계 기간</h3>
                <div class="date_sch_wrap">
                    <input type="datetime" id="dateStartSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateStartOrg}"/>">
                    <span class="date_during">~</span>
                    <input type="datetime" id="dateEndSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateEndOrg}"/>">
                    <button class="btn_date btn_gray btn" id="click-search">검색</button>
                </div>
            </div>
            <div class="dash_today_row">
                <ul class="dash_today_list2">
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_sad.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="today">TODAY</span><span>고민 등록 인원</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${todayWorryCount}" /></strong></div>
                        </div>
                    </li>
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_sad.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="total">TOTAL</span><span>고민 등록 인원</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${totalWorryCount}"/></strong></div>
                        </div>
                    </li>
                    
                </ul>
                <ul class="dash_today_list2">
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_happy.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="today">TODAY</span><span>체험 인원</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${todayMemberCount}"/></strong></div>
                        </div>
                    </li>
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_happy.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="total">TOTAL</span><span>체험 인원</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${totalMemberCount}"/></strong></div>
                        </div>
                    </li>
                </ul>
                <ul class="dash_today_list2">
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_happy.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="today">TODAY</span><span>체험 완료 횟수</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${todayPartCount}" /></strong></div>
                        </div>
                    </li>
                    <li>
                        <div class="today_total roundbox">
                            <figure><img src="<c:url value="/m/images/img_idx_happy.svg"/>" alt=""></figure>
                            <div class="total_info"><span class="total">TOTAL</span><span>체험 완료 횟수</span><strong><fmt:formatNumber type="number" maxFractionDigits="3" value="${totalPartCount}" /></strong></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="dash_ratio">
                <div class="worry_ratio roundbox">
                    <h3 class="adm_title tac">고객 고민 유형 비율</h3>
                    <div class="dash_ratio_wrap" style="width:100%; height:85%;">
                        <!-- 해당 영역은 우선 기존 디자인과 유사한 chart.js 플러그인으로 사용했습니다.
                        그러나 이 플러그인으로는 100% 플러그인 구현되지 않아 이 부분 그래프영역 디자인 및 퍼블리싱을 새로 잡을 예정입니다. -->
                        <canvas id="worryRador" style="width: 100%; height: 100%;"></canvas>
                        <div id="rationTarget" style="position: absolute; width: 100%; height: 100%; top: 0; left: 0;"> </div>
                    </div>
                </div>
                <div class="exper_ratio roundbox">
                    <h3 class="adm_title tac">체험존 체험 현황</h3>
                    <div class="dash_ratio_wrap">
                        <!-- 각 체험존 그래프는 단순 막대그래프 형태여서 직접 만들었습니다. 
                            corner_per라는 span태그에 width값이 인원 백분율에 따라 다라집니다. -->
                        <ul class="ratio_corner_list">
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_mind.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">마음고침 코-너</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${mindPercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${mind}" /></b>명</span>
                            </li>
                            <!-- li end -->
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_style.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">스타일고침 코-너</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${stylePercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${style}" /></b>명</span>
                            </li>
                            <!-- li end -->
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_personality.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">개성고침 코-너</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${indivPercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${indiv}" /></b>명</span>
                            </li>
                            <!-- li end -->
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_star.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">기분고침 코-너 (금성오락실)</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${moodPercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${mood}" /></b>명</span>
                            </li>
                            <!-- li end -->
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_key.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">고민탈출 코-너 (ThinQ 방탈출)</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${lifePercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${life}" /></b>명</span>
                            </li>
                            <!-- li end -->
                            <!-- li : 각 체험존 라인 -->
                            <li>
                                <img src="<c:url value="/m/images/icn_idx_refresh.svg"/>" alt="">
                                <div class="corner_graph">
                                    <h4 class="corner_title">새로고침 코-너</h4>
                                    <div class="corner_bar">
                                        <span class="corner_per" style="<c:out value="width:${refreshPercent}%;"/>"></span>
                                    </div>
                                </div>
                                <span class="number"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${refresh}" /></b>명</span>
                            </li>
                            <!-- li end -->
                        </ul>
                    </div>
                </div>
            </div>
        </section>
        
    </main>
    <script>

        const labels = [
        	<c:forEach items="${worryLabels}" var="item" varStatus="index">
            ['<c:out value="${item}"/>']<c:if test="${not index.last}">,</c:if> 
        	</c:forEach>
        ];

        const defaultData = [
        	<c:forEach items="${worriesPercent}" var="item">
        	<c:out value="${item}"/><c:if test="${not index.last}">,</c:if> 
            </c:forEach>
        ]

        let realLable =[]

        // labels.forEach((element,idx) => {
        //  realLable.push([element,defaultData[idx]+"%"]);
        // });

        const data = {
            labels: [[''],[''],[''],[''],[''],[''],['']],
            datasets: [{
                backgroundColor: 'rgba(218, 15, 71, .5)',
                borderColor: 'rgba(218, 15, 71, 1)',
                borderWidth: 1,
                pointBorderWidth: 0,
                pointBackgroundColor: 'transparent',
                data: defaultData,
                spanGaps: null,
                fontColor: '#fff',
            }]
        };

        const config = {
            type: 'radar',
            data: data,
            options: {
                responsive: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: false,
                        text: '고객 고민 유형'
                    },
                    labels: {
                        fontColor: '#fff'
                    }
                
                },
                scales: {
                    r: {
                        pointLabels: { 
	                        font: {
	                            size: 14,
	                            color:'#fff',
	                        }
                        }
                    }
                },
                layout: {
                    padding: 30
                }
            }
        };
        const worryRador = new Chart(
            document.getElementById('worryRador'),
            config
        );

        defaultData.forEach((e, i) => {
            document.getElementById("rationTarget").innerHTML += '<p class="ratio'+(i+1)+' ratioper"><span class="num">'+e+'</span><span class="per">%</span></p>';
        })
        labels.forEach((e, i) => {
            document.getElementById("rationTarget").innerHTML += '<p class="text'+(i+1)+' textlabel">'+e+'</p>';
        })
    </script>
    <c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
<form id="searchForm" action="<c:url value="/m/dash.do"/>" method="post">
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
</html>