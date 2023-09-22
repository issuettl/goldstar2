<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/survey/index.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="current_nav">
                        <h2>설문 조사 관리</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="survey_section adm_section">
			<div class="adm_con_page roundbox">
				
				<!-- survey_list -->
				<div class="survey_list adm_part">
					<h3 class="adm_title">체험 제품 관리</h3>
					<div class="adm_flex adm_title_btns">
						<div class="adm_onoff">
                            <input type="checkbox" id="switch" class="onoffCheck"/><label class="onoff_indicator" for="switch"></label>
							<span class="onoff_text">체험 제품 활성화</span>
                        </div>
                        <div class="adm_right"><!-- 
                            <button class="btn_excel btn_m btn_gray btn">엑셀 다운로드</button> -->
                        </div>
					</div>
					<ul class="adm_typeproduct2 adm_sortable adm_list">
						<li class="adm_list_title ui-state-disabled">
							<div class="list_con01">순서</div>
							<div class="list_con02">이미지</div>
							<div class="list_con03">제품명</div>
							<div class="list_con04 list_btn_wrap">기능</div>
						</li>
						<c:forEach items="${entity.answers}" var="products" varStatus="proIndex">
						<li class="adm_list_con list_line">
							<div class="list_con01">03</div>
							<div class="list_con02 list_bbs_view"><img src="<c:url value="/u${products.image}"/>" alt=""></div>
							<div class="list_con03 list_bbs_view"><c:out value="${products.name}"/></div>
							<div class="list_con04 list_btn_wrap">
								<button onClick="javascript:$('#pop_survey_mod1').show();" class="btn_primary btn_s btn">수정</button><button onClick="javascript:$('#dialog_delete').show();" class="btn_gray btn_s btn">삭제</button>
							</div>
						</li>
						</c:forEach>
						<li class="adm_plus_con adm_list_con list_line ui-state-disabled">
							<div class="left">
								<button onClick="javascript:$('#pop_survey_add').show();" class="btn_input_add">제품 추가하기</button>
							</div>
							<div class="right">
								<button class="btn_m btn_gray btn">순서 변경</button>
							</div>
						</li>
					</ul>
				</div>
				<!-- survey_list end -->

			</div>
		</section>
    </main>
    
    <section id="pop_survey_add" class="pop_survey2 pop_survey modal">
		<div class="popBg">
			<div class="popContent">
				<div class="popTitle_wrap">
					<h2>체험 제품 등록</h2>
					<button onClick="javascript:$('#pop_survey_add').hide();" class="popup_close"></button>
				</div>
				<div class="pop_delete_con">
					<h3>제품명</h3>
					<div class="row inputBox">
						<input type="text" name="" id="adm_event_title_del" class="w100per line_input" placeholder="제품명을 입력합니다." value=""/>
					</div>
				</div>
				<div class="pop_survey_con row">
					<h3>이미지</h3>
					<div class="file_btn_col col">
						<label for="fileInput1" class="upload">
							<input class="customFile2 customFile" id="fileInput1" type="file" accept="image/png, image/gif, image/jpeg">
							<p class="uploaded">파일 선택</p>
						</label>
						<button id="" class="btn_file btn_m btn_gray btn">파일 찾기</button>
					</div>
				</div>
				<div class="bottom btn_wrap w100per tac">
					<button onclick="javascript:$('#pop_survey_add').hide();" class="btn_pop_s btn_primary btn">완료</button>
				</div>
			</div>
		</div>
		<div class="dim"></div>
	</section>
	<!-- popup end -->

	<!-- pop_survey_mod1 popup -->
	<section id="pop_survey_mod1" class="pop_survey2 pop_survey modal">
		<div class="popBg">
			<div class="popContent">
				<div class="popTitle_wrap">
					<h2>체험 제품 수정</h2>
					<button onClick="javascript:$('#pop_survey_mod1').hide();" class="popup_close"></button>
				</div>
				<div class="pop_delete_con">
					<h3>제품명</h3>
					<div class="row inputBox">
						<input type="text" name="" id="adm_event_title_del" class="w100per line_input" placeholder="" value="LG 스탠바이미"/>
					</div>
				</div>
				<div class="pop_survey_con row">
					<h3>이미지</h3>
					<div class="file_btn_col col">
						<label for="fileInput2" class="upload">
							<input class="customFile2 customFile" id="fileInput2" type="file" accept="image/png, image/gif, image/jpeg">
							<p class="uploaded">파일 선택</p>
						</label>
						<button id="" class="btn_file btn_m btn_gray btn">파일 찾기</button>
					</div>
				</div>
				<div class="bottom btn_wrap w100per tac">
					<button onclick="javascript:$('#pop_survey_mod1').hide();" class="btn_pop_s btn_primary btn">완료</button>
				</div>
			</div>
		</div>
		<div class="dim"></div>
	</section>
	<!-- popup end -->

	<!-- 삭제 커스텀 다이알로그 -->
	<section id="dialog_delete" class="pop_mini modal">
		<div class="popBg">
			<div class="popContent">
				<div class="centerCon">
					<p>체험 제품을 삭제하시겠습니까?</p>
				</div>
				<div class="btn_wrap w100per tac">
					<button onclick="popClose()" class="btn_pop_s btn_lightgray btn">아니오</button>
					<button onclick="popClose()" class="btn_pop_s btn_primary btn">예</button>
				</div>
			</div>
		</div>
		<div class="dim"></div>
	</section>
	<!-- popup end -->
    <!-- 내부활용문답관리 활성화 dialog_on popup -->
	<section id="dialog_on" class="pop_mini modal">
		<div class="popBg">
			<div class="popContent">
				<div class="centerCon">
					<p>체험 제품을 활성화 하시겠습니까?</p>
				</div>
				<div class="btn_wrap w100per tac">
					<button onclick="popClose()" class="btn_pop_s btn_lightgray btn">아니오</button>
					<button onclick="popClose()" class="btn_pop_s btn_primary btn">예</button>
				</div>
			</div>
		</div>
		<div class="dim"></div>
	</section>
	<!-- popup end -->
	<!-- 내부활용문답관리 비활성화 dialog_off popup -->
	<section id="dialog_off" class="pop_mini modal">
		<div class="popBg">
			<div class="popContent">
				<div class="centerCon">
					<p>체험 제품을 비활성화 하시겠습니까?</p>
				</div>
				<div class="btn_wrap w100per tac">
					<button onclick="popClose()" class="btn_pop_s btn_lightgray btn">아니오</button>
					<button onclick="popClose()" class="btn_pop_s btn_primary btn">예</button>
				</div>
			</div>
		</div>
		<div class="dim"></div>
	</section>
    
    <!-- 삭제 커스텀 다이알로그 -->
    <section id="dialog_delete" class="pop_mini modal">
        <div class="popBg">
            <div class="popContent">
                <div class="centerCon">
                    <p>삭제하시겠습니까?</p>
                </div>
                <div class="btn_wrap w100per tac">
                    <button onclick="popClose()" class="btn_pop_s btn_lightgray btn">취소</button>
                    <button onclick="popClose()" class="btn_pop_s btn_primary btn">삭제</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->
    <c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>