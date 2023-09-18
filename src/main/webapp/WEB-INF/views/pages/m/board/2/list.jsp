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
                        <h2>공지사항 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="faq_section adm_section">
            <div class="adm_con_page roundbox">
                <div class="faq_write adm_part">
                    <h3 class="adm_title">공지사항 등록</h3>
                    <div class="adm_write">
                        <div class="row inputBox">
                            <label for="adm_notice_title">제목</label><input type="text" id="saveSubject" placeholder="제목을 입력해 주세요">
                        </div>
                        <div class="row inputBox">
                            <label for="adm_notice_cont">내용</label><textarea id="saveContents" cols="20" rows="4" placeholder="답변 내용을 입력해 주세요"></textarea>
                        </div>
                    </div>
                    <div class="btn_wrap t_center"><button class="btn_m btn_gray btn" id="doSave">등록하기</button></div>
                </div>
                
                <!-- notice list -->
                <div class="notice_list page_pb0 adm_part">
                    <div class="adm_title_btns">
                        <h3 class="adm_title">공지사항 리스트</h3>
                    </div>
                    <ul class="list_num4 adm_list">
                        <li class="adm_list_title ui-state-disabled">
                            <div class="list_con01">NO.</div>
                            <!-- 2022-11-18 : 수정 - 네이밍 변경 -->
                            <div class="list_con02 list_bbs_view">공지사항</div>
                            <div class="list_con03 list_bbs_num">조회수</div>
                            <div class="list_con04 list_btn_wrap">기능</div>
                        </li>
                        <c:set var="num" value="${paging.numberOfPages}"/>
                        <c:forEach items="${page.content}" var="item">
                        <li class="adm_list_con list_line">
                            <div class="list_con01"><c:out value="${num}"/></div>
                            <div class="list_con02 list_bbs_view">
                                <div class="list_bbs_top">
                                    <span class="bbs_label">제목</span>
                                    <h4 class="bbs_cont"><c:out value="${item.subject}"/></h4>
                                </div>
                                <div class="list_bbs_bottom">
                                    <span class="bbs_label">내용</span>
                                    <div class="bbs_cont"><c:out value="${item.contents}" escapeXml="false"/></div>
                                </div>
                            </div>
                            <div class="list_con03 list_bbs_num"><c:out value="${item.read}"/></div>
                            <div class="list_con04 list_btn_wrap">
                                <button class="btn_primary btn_s btn doUpdate" data-sn="<c:out value="${item.sn}"/>">수정</button><button class="btn_gray btn_s btn doDelete" data-sn="<c:out value="${item.sn}"/>">삭제</button>
                            </div>
                        </li>
                        <c:set var="num" value="${num-1}"/>
                        </c:forEach>
                    </ul>
                </div>
                <!-- notice end -->
                <!-- 페이지 네비게이션 Start -->
                <r9:pagingHelperHome paging="${paging}" groups="5" request="${request}"/>
                <!-- 페이지 네비게이션 End -->
            </div>
        </section>
    </main>
    <!-- pop_faq_mod popup -->
    <section id="pop_update" class="pop_bbs_mod modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>공지사항 수정</h2>
                    <button onClick="javascript:$('#pop_update').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>제목</h3>
                    <div class="row inputBox">
                        <input type="text" id="updateSubject" class="w100per line_input" placeholder="" value="" />
                    </div>
                </div>
                <div class="pop_survey_con">
                    <h3>내용</h3>
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
                    <h2>공지사항 삭제</h2>
                    <button onClick="javascript:$('#pop_delete').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="">제목</span>
                        </li>
                        <li class="content">
                            <span class="" id="deleteSubject"></span>
                        </li>
                    </ul>
                </div>
                <div class="pop_delete_con">
                    <ul class="pop_delete_table">
                        <li class="title">
                            <span class="">내용</span>
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
<form id="searchForm" action="<c:url value="/m/board/2/list.do"/>" method="post">
    <input type="hidden" id="bbsSn" value="2"/>
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>