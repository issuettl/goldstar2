<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/event/list.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>이벤트 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <div class="event_section adm_section">
            <div class="adm_con_page roundbox">
                <div class="event_write adm_part" id="saveForm">
                    <h3 class="adm_title">이벤트 등록</h3>
                    <div class="adm_write">
                        <div class="row inputBox">
                            <label for="adm_event_title">제목</label><input type="text" id="saveSubject" placeholder="이벤트 제목을 입력해 주세요" />
                        </div>
                        <!-- 2022-12-19 수정 위치변경 -->
                        <div class="row inputBox">
                            <label for="event_date1">이벤트 기간</label>
                            <input type="datetime" id="saveStartDate" class="w20per input_date" placeholder="날짜 선택" readonly>
                            <span class="date_during">~</span>
                            <input type="datetime" id="saveEndDate" class="w20per input_date" placeholder="날짜 선택" readonly>
                        </div>
                        <!-- 2022-12-19 수정 배너 삭제 -->
                        <div class="row inputBox">
                            <div class="col_first col">
                                <label>썸네일</label>
                                <label for="saveThubmFile" class="upload">
                                    <input class="customFile" id="saveThubmFile" type="file" accept="image/png, image/gif, image/jpeg">
                                    <div class="customfile_text">
                                        <p class="">파일 선택</p>
                                        <button class="file_close"></button>
                                    </div>
                                </label>
                            </div>
                        </div>
                        <div class="row inputBox">
                            <div class="col_first col">
                                <label>PC 상세페이지</label>
                                <label for="savePcViewFile" class="upload">
                                    <input class="customFile" id="savePcViewFile" type="file" accept="image/png, image/gif, image/jpeg">
                                    <div class="customfile_text">
                                        <p class="">파일 선택</p>
                                        <button class="file_close"></button>
                                    </div>
                                </label>
                            </div>
                            <div class="col">
                                <label>MO 상세페이지</label>
                                <label for="saveMoViewFile" class="upload">
                                    <input class="customFile" id="saveMoViewFile" type="file" accept="image/png, image/gif, image/jpeg">
                                    <div class="customfile_text">
                                        <p class="">파일 선택</p>
                                        <button class="file_close"></button>
                                    </div>
                                </label>
                            </div>
                        </div>
                        
                        <div class="row inputBox">
                            <label for="adm_event_cont">내용</label><textarea id="saveContents" cols="20" rows="4" placeholder="이벤트 내용을 입력해 주세요"></textarea>
                        </div>
                    </div>
                    <div class="btn_wrap t_center"><button id="saveEvent" class="btn_m btn_gray btn">등록하기</button></div>
                </div>
                <!-- event_list -->
                <div class="event_list adm_part">
                    <!-- 2022-11-25 수정 : 구조 변경(순서변경 기능 삭제) -->
                    <h3 class="adm_title">이벤트 리스트</h3>
                    <ul class="list_num4 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">NO.</div>
                            <div class="list_con02">제목</div>
                            <div class="list_con03">기간</div>
                            <div class="list_con04 list_btn_wrap">기능</div>
                        </li>
                        <c:forEach items="${page.content}" var="item" varStatus="index">
                        <li class="adm_list_con list_line">
                            <div class="list_con01"><c:out value="${index.count}" /></div>
                            <div class="list_con02"><c:out value="${item.subject}"/></div>
                            <div class="list_con03">
                                <fmt:parseDate var="startDate" value="${item.startDate}" pattern="yyyyMMdd"/>
                                <fmt:parseDate var="endDate" value="${item.endDate}" pattern="yyyyMMdd"/>
                                <fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="list_con04 list_btn_wrap">
                                <button class="btn_primary btn_s btn doUpdate" data-sn="<c:out value="${item.sn}"/>">수정</button>
                                <button class="btn_gray btn_s btn doRemove" data-sn="<c:out value="${item.sn}"/>">삭제</button>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- event_list end -->
            </div>
        </div>
    </main>
    <!-- pop_faq_mod popup -->
    <section id="pop_event_mod" class="pop_event modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>이벤트 수정 하기</h2>
                    <button onClick="javascript:$('#pop_event_mod').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>제목</h3>
                    <div class="row inputBox">
                        <input type="text" id="updateSubject" class="w100per line_input" placeholder="이벤트 제목을 입력해 주세요" />
                    </div>
                </div>
                <!-- 2022-12-20 수정 : 위치변경 -->
                <div class="pop_delete_con">
                    <h3>날짜 선택</h3>
                    <div class="row inputBox">
                        <input type="datetime" id="updateStartDate" class="input_date line_input w30per" placeholder="날짜 선택" readonly />
                        <span class="date_during">~</span>
                        <input type="datetime" id="updateEndDate" class="input_date line_input w30per" placeholder="날짜 선택" readonly />
                    </div>
                </div>
                <!-- 2022-12-19 수정 배너 삭제 -->
                <div class="pop_delete_con row">
                    <div class="col w40per">
                        <h3>썸네일</h3>
                        <label for="updateThubmFile" class="upload">
                            <input class="customFile" id="updateThubmFile" type="file" accept="image/png, image/gif, image/jpeg">
                            <div class="customfile_text">
                                <p class="">파일 선택</p>
                                <button class="file_close"></button>
                            </div>
                        </label>
                    </div>
                </div>
                <div class="pop_delete_con row">
                    <div class="col w40per">
                        <h3>PC 상세페이지</h3>
                        <!-- <input type="file" name="" id="adm_event_file" placeholder="파일 선택" /> -->
                        <label for="updatePcViewFile" class="upload">
                            <input class="customFile" id="updatePcViewFile" type="file" accept="image/png, image/gif, image/jpeg">
                            <div class="customfile_text">
                                <p class="">파일 선택</p>
                                <button class="file_close"></button>
                            </div>
                        </label>
                    </div>
                    <div class="col w40per">
                        <h3>MO 상세페이지</h3>
                        <label for="updateMoViewFile" class="upload">
                            <input class="customFile" id="updateMoViewFile" type="file" accept="image/png, image/gif, image/jpeg">
                            <div class="customfile_text">
                                <p class="">파일 선택</p>
                                <button class="file_close"></button>
                            </div>
                        </label>
                    </div>
                </div>
                <div class="pop_delete_con">
                    <h3>내용</h3>
                    <div class="row inputBox">
                        <textarea id="updateContents" cols="20" rows="4" placeholder="내용을 입력해주세요"></textarea>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="javascript:$('#pop_event_mod').hide();" class="btn_pop_s btn_lightgray btn">취소하기</button>
                    <button id="updateButton" class="btn_pop_s btn_primary btn">수정하기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <!-- pop_event_del popup -->
    <section id="pop_event_del" class="pop_event modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2>이벤트 삭제 하기</h2>
                    <button onClick="javascript:$('#pop_event_del').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>제목</h3>
                    <div class="row inputBox">
                        <input type="text" id="removeSubject" class="w100per line_input" placeholder="이벤트 제목을 입력해 주세요" readonly/>
                    </div>
                </div>
                <!-- 2022-12-20 수정 : 위치변경 -->
                <div class="pop_delete_con">
                    <h3>날짜 선택</h3>
                    <div class="row inputBox">
                        <input type="datetime" id="removeStartDate" class="input_date line_input w30per" placeholder="날짜 선택" readonly />
                        <span class="date_during">~</span>
                        <input type="datetime" id="removeEndDate" class="input_date line_input w30per" placeholder="날짜 선택" readonly />
                    </div>
                </div>
                <div class="pop_delete_con">
                    <h3>내용</h3>
                    <div class="row inputBox">
                        <textarea id="removeContents" cols="20" rows="4" placeholder="내용을 입력해주세요" readonly></textarea>
                    </div>
                </div>
                <div class="btn_wrap w100per tac">
                    <button class="btn_pop_s btn_lightgray btn">취소하기</button>
                    <button id="removeButton" class="btn_pop_s btn_primary btn">삭제하기</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
<form id="searchForm" action="<c:url value="/m/event/list.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>