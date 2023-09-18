<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/survey/answer.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>사전문답 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="survey_section adm_section">
            <!-- topsch_area : 어드민 상단 검색 -->
            <div class="topsch_area wrapped_flex">
                <h3 class="adm_title mb15">통계 기간</h3>
                <div class="date_sch_wrap">
                    <input type="datetime" id="dateStartSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateStartOrg}"/>">
                    <span class="date_during">~</span>
                    <input type="datetime" id="dateEndSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateEndOrg}"/>">
                    <button class="btn_date btn_gray btn" id="click-search">검색</button>
                </div>
            </div>
            <div class="adm_con_page roundbox">
                <!-- 마음고침 코너 사전문답 데이터 -->
                <div class="refresh_data data_wrap">
                    <ul class="data_list">
                        <!-- data_list li -->
                        <li>
                            <!-- 닫힌 dropdown 컨텐츠 -->
                            <div class="adm_dropdown">
                                <button onclick="return dropdown_open(this);" class="dropdownLink more_open">
                                    <div class="admdata_title wrapped_flex">
                                        <div class="left"><h3 class="qna_title">[마음고침 코-너] 사전문답 데이터</h3></div>
                                        <div class="right">
                                            <!-- 전체 데이터 수 -->
                                            <c:set var="total" value="0"/>
                                            <c:forEach items="${mindList}" var="question" varStatus="qIndex">
                                                <c:set var="total" value="${total + question.total}"/>
                                            </c:forEach>
                                            <span class="total_num"><c:out value="${total}"/></span>
                                            <img src="<c:url value="/m/images/icn_surveydrop.svg"/>" alt="">
                                        </div>
                                    </div>
                                </button>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                                <div class="dropdownFull">
                                    <div class="admdata_contents">
                                        <c:forEach items="${mindList}" var="question" varStatus="qIndex">
                                            <ul class="survey_refresh<c:out value="${qIndex.count}"/> survey_list">
                                                <li class="title"><b class="q_mark">Q<c:out value="${qIndex.count}"/>.</b><c:out value="${question.question}"/></li>
                                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                                <li class="content wrapped_flex"><span class="left"><c:out value="${answer.name}"/></span><span class="right"><c:out value="${answer.count}"/></span></li>
                                                </c:forEach>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                            </div>
                            <!-- 닫힌 dropdown 컨텐츠 end -->
                        </li>
                        <!-- // data_list li end -->
                    </ul>
                </div>
                <!-- 마음고침 코너 사전문답 데이터 end -->
                <!-- 개성고침 코너 사전문답 데이터 -->
                <div class="personality_data data_wrap">
                    <ul class="data_list">
                        <!-- data_list li -->
                        <li>
                            <!-- 닫힌 dropdown 컨텐츠 -->
                            <div class="adm_dropdown">
                                <button onclick="return dropdown_open(this);" class="dropdownLink more_open">
                                    <div class="admdata_title wrapped_flex">
                                        <div class="left"><h3 class="qna_title">[개성고침 코-너] 사전문답 데이터</h3></div>
                                        <div class="right">
                                            <!-- 전체 데이터 수 -->
                                            <c:set var="total" value="0"/>
                                            <c:forEach items="${indivList}" var="question" varStatus="qIndex">
                                                <c:set var="total" value="${total + question.total}"/>
                                            </c:forEach>
                                            <span class="total_num"><c:out value="${total}"/></span>
                                            <img src="<c:url value="/m/images/icn_surveydrop.svg"/>" alt="">
                                        </div>
                                    </div>
                                </button>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                                <div class="dropdownFull">
                                    <div class="admdata_contents">
                                        <c:forEach items="${indivList}" var="question" varStatus="qIndex">
                                            <ul class="survey_personality<c:out value="${qIndex.count}"/> survey_list">
                                                <li class="title"><b class="q_mark">Q<c:out value="${qIndex.count}"/>.</b><c:out value="${question.question}"/></li>
                                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                                <li class="content wrapped_flex"><span class="left"><c:out value="${answer.name}"/></span><span class="right"><c:out value="${answer.count}"/></span></li>
                                                </c:forEach>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                            </div>
                            <!-- 닫힌 dropdown 컨텐츠 end -->
                        </li>
                        <!-- // data_list li end -->
                    </ul>
                </div>
                <!-- 개성고침 코너 사전문답 데이터 end -->
                <!-- 스타일고침 코너 사전문답 데이터 -->
                <div class="style_data data_wrap">
                    <ul class="data_list">
                        <!-- data_list li -->
                        <li>
                            <!-- 닫힌 dropdown 컨텐츠 -->
                            <div class="adm_dropdown">
                                <button onclick="return dropdown_open(this);" class="dropdownLink more_open">
                                    <div class="admdata_title wrapped_flex">
                                        <div class="left"><h3 class="qna_title">[스타일고침 코-너] 사전문답 데이터</h3></div>
                                        <div class="right">
                                            <!-- 전체 데이터 수 -->
                                            <c:set var="total" value="0"/>
                                            <c:forEach items="${styleList}" var="question" varStatus="qIndex">
                                                <c:set var="total" value="${total + question.total}"/>
                                            </c:forEach>
                                            <span class="total_num"><c:out value="${total}"/></span>
                                            <img src="<c:url value="/m/images/icn_surveydrop.svg"/>" alt="">
                                        </div>
                                    </div>
                                </button>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                                <div class="dropdownFull">
                                    <div class="admdata_contents">
                                        <!-- Q 설문지 ul -->
                                        <c:forEach items="${styleList}" var="question" varStatus="qIndex">
                                            <ul class="survey_style<c:out value="${qIndex.count}"/> survey_list">
                                                <li class="title"><b class="q_mark">Q<c:out value="${qIndex.count}"/>.</b><c:out value="${question.question}"/></li>
                                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                                <li class="content wrapped_flex"><span class="left"><c:out value="${answer.name}"/></span><span class="right"><c:out value="${answer.count}"/></span></li>
                                                </c:forEach>
                                            </ul>
                                        </c:forEach>
                                        <!-- Q 설문지 ul end -->
                                    </div>
                                </div>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                            </div>
                            <!-- 닫힌 dropdown 컨텐츠 end -->
                        </li>
                        <!-- // data_list li end -->
                    </ul>
                </div>
                <!-- 스타일고침 코너 사전문답 데이터 end -->
                <!-- 기분고침 코너 사전문답 데이터 -->
                <div class="mind_data data_wrap">
                    <ul class="data_list">
                        <!-- data_list li -->
                        <li>
                            <!-- 닫힌 dropdown 컨텐츠 -->
                            <div class="adm_dropdown">
                                <button onclick="return dropdown_open(this);" class="dropdownLink more_open">
                                    <div class="admdata_title wrapped_flex">
                                        <div class="left"><h3 class="qna_title">[기분고침 코-너] 사전문답 데이터</h3></div>
                                        <div class="right">
                                            <!-- 전체 데이터 수 -->
                                            <c:set var="total" value="0"/>
                                            <c:forEach items="${moodList}" var="question" varStatus="qIndex">
                                                <c:set var="total" value="${total + question.total}"/>
                                            </c:forEach>
                                            <span class="total_num"><c:out value="${total}"/></span>
                                            <img src="<c:url value="/m/images/icn_surveydrop.svg"/>" alt="">
                                        </div>
                                    </div>
                                </button>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                                <div class="dropdownFull">
                                    <div class="admdata_contents">
                                        <!-- Q 설문지 ul -->
                                        <c:forEach items="${moodList}" var="question" varStatus="qIndex">
                                            <ul class="survey_mind<c:out value="${qIndex.count}"/> survey_list">
                                                <li class="title"><b class="q_mark">Q<c:out value="${qIndex.count}"/>.</b><c:out value="${question.question}"/></li>
                                                <c:forEach items="${question.answers}" var="answer" varStatus="aIndex">
                                                <li class="content wrapped_flex"><span class="left"><c:out value="${answer.name}"/></span><span class="right"><c:out value="${answer.count}"/></span></li>
                                                </c:forEach>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- dropdownFull 펼쳐지는 내용 -->
                            </div>
                            <!-- 닫힌 dropdown 컨텐츠 end -->
                        </li>
                        <!-- // data_list li end -->
                    </ul>
                </div>
                <!-- 기분고침 코너 사전문답 데이터 end -->
                <style>
                    .data_wrap {    padding: 0 20px 0px 20px;}
                    .data_wrap .dropdownLink {
                        width: 100%;
                    }
                    .adm_dropdown {}
                    .admdata_title {
                        display: flex;
                        padding-bottom: 20px;
                        justify-content: space-between;
                    }
                    .admdata_title .left {
                        width: 80%;
                        text-align: left;
                    }
                    .admdata_title .qna_title {
                        font-size: 22px;
                        color: var(--lg-primary);
                    }
                    .admdata_title .total_num {
                        font-size: 20px;
                        color: var(--lg-primary);
                        text-align: right;
                        margin-right: 20px;
                        font-weight: 700;
                    }
                    .admdata_title .right {width: 20%; display: inline-flex; align-items: center; justify-content: flex-end;}
                    .admdata_title .right img {display: block; width: 24px; height: 24px; transform: rotate(180deg);}
                    .dropdownLink.more_open .admdata_title .right img {transform: rotate(0deg);}
                    .admdata_contents {}
                    .dropdownFull .survey_list {padding-bottom: 40px;}
                    .survey_list .title {
                        display: flex;
                        padding: 12px 20px;
                        gap: 10px;
                        background: var(--bg);
                        border: 1 solid #ddd;
                        border-radius: 8px;
                        font-size: 20px; 
                        font-weight: 500;
                        color: var(--black);
                        line-height: 30px;
                    }
                    .survey_list .title .q_mark {
                        font-weight: 800;
                    }
                    .survey_list .content {
                        border-bottom: 1px solid #ddd;
                        padding: 25px 20px;
                    }
                    .survey_list .content .left {
                        font-weight: 700;
                        font-size: 20px;
                        line-height: 30px;
                        text-align: left;
                    }
                    .survey_list .content .right {
                        font-weight: 500;
                        font-size: 20px;
                        line-height: 30px;
                        text-align: right;
                        color: #232323;
                    }
                </style>
            </div>
        </section>
        
    </main>
    <script>
        /* 드롭다운 추가 */
        function dropdown_open(el){
            var moreBtn = $(el).closest(".adm_dropdown").find(".dropdownLink");
            var fullCon = $(el).closest(".adm_dropdown").find(".dropdownFull");
            
            if(moreBtn.hasClass("more_open")) {
                $(el).closest(".adm_dropdown").parent().find(".dropdownLink").removeClass("more_open");
                $(el).closest(".adm_dropdown").parent().find(".dropdownFull").slideUp();
            } else {
                $(el).closest(".adm_dropdown").parent().find(".dropdownLink").addClass("more_open");
                $(el).closest(".adm_dropdown").parent().find(".dropdownFull").slideDown();
            }
            return false;
        };
    </script>
    <c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
<form id="searchForm" action="<c:url value="/m/survey/answer.do"/>" method="post">
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
</html>