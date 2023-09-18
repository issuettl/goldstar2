<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/board/list.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>FAQ 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="faq_section adm_section">
            <div class="adm_con_page roundbox">
                <!-- 2022-11-18 : 수정 - form태그 추가(실수했네요ㅠ) -->
                <div class="faq_write adm_part">
                    <h3 class="adm_title">FAQ 등록</h3>
                    <div class="adm_write">
                        <div class="row inputBox">
                            <label for="adm_faq_title">질문</label><input type="text" id="saveSubject" placeholder="질문을 입력해 주세요">
                        </div>
                        <div class="row inputBox">
                            <label for="adm_faq_cont">답변</label><textarea id="saveContents" cols="20" rows="4" placeholder="답변 내용을 입력해 주세요"></textarea>
                        </div>
                    </div>
                    <div class="btn_wrap t_center"><button class="btn_m btn_gray btn" id="doSave">등록하기</button></div>
                </div>
                
                <!-- faq list -->
                <div class="faq_list page_pb0 adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">FAQ 리스트</h3>
                        <!-- 순서변경 버튼은 드래그로 옮긴 게시물 순서를 확정짓고 적용하는 버튼입니다. -->
                        <button class="btn_bbsfx btn_m btn_gray btn" id="doOrder">순서 변경</button>
                    </div>
                    <ul class="list_num3 adm_sortable adm_list">
                        <li class="adm_list_title ui-state-disabled">
                            <div class="list_con01">NO.</div>
                            <!-- 2022-11-18 : 수정 - 네이밍 변경 -->
                            <div class="list_con02 list_bbs_view">FAQ</div>
                            <div class="list_con03">기능</div>
                        </li>
                        <c:forEach items="${page.content}" var="item" varStatus="index">
                        <li class="adm_list_con list_line orderSn" data-sn="<c:out value="${item.sn}"/>">
                            <div class="list_con01"><c:out value="${index.count}" /></div>
                            <div class="list_con02 list_bbs_view">
                                <div class="list_bbs_top">
                                    <span class="bbs_label">질문</span>
                                    <h4 class="bbs_cont"><c:out value="${item.subject}"/></h4>
                                </div>
                                <div class="list_bbs_bottom">
                                    <span class="bbs_label">답변</span>
                                    <div class="bbs_cont"><c:out value="${item.contents}" escapeXml="false"/></div>
                                </div>
                            </div>
                            
                            <!-- 인터랙션 확인용도로 영역 숨기고 보이는 기능을 첫줄에만 구현했습니다 -->
                            <div class="list_con03 list_btn_wrap">
                                <button class="btn_primary btn_s btn doUpdate" data-sn="<c:out value="${item.sn}"/>">수정</button><button class="btn_gray btn_s btn doDelete" data-sn="<c:out value="${item.sn}"/>">삭제</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- faq_list end -->
            </div>
        </section>
    </main>
    <!-- pop_faq_mod popup -->
    <section id="pop_update" class="pop_bbs_mod modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>FAQ 수정</h2>
                    <button onClick="javascript:$('#pop_update').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>질문</h3>
                    <div class="row inputBox">
                        <input type="text" id="updateSubject" class="w100per line_input" placeholder="" value="" />
                    </div>
                </div>
                <div class="pop_survey_con">
                    <h3>답변</h3>
                    <div class="row inputBox">
                        <textarea id="updateContents" cols="20" rows="4" placeholder=""></textarea>
                    </div>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button class="btn_pop_s btn_lightgray btn" id="updateCancel">취소</button>
                    <button class="btn_pop_s btn_primary btn" id="updateAction">수정</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <!-- pop_faq_del popup -->
    <section id="pop_delete" class="pop_bbs_del modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>FAQ 삭제</h2>
                    <button onClick="javascript:$('#pop_delete').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="">질문</span>
                        </li>
                        <li class="content">
                            <span class="" id="deleteSubject"></span>
                        </li>
                    </ul>
                </div>
                <div class="pop_delete_con">
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="">답변</span>
                        </li>
                        <li class="content">
                            <span class="t_left" id="deleteContents"></span>
                        </li>
                    </ul>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button class="btn_pop_s btn_lightgray btn" id="deleteCancel">취소</button>
                    <button class="btn_pop_s btn_primary btn" id="deleteAction">삭제</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
<form id="searchForm" action="<c:url value="/m/board/1/list.do"/>" method="post">
    <input type="hidden" id="bbsSn" value="1"/>
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>