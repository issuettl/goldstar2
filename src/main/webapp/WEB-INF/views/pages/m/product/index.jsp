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
        <section class="products_section adm_section">
			<div class="adm_con_page roundbox">
				<!-- type_list start -->
				<div class="type_list adm_part">
					<div class="adm_flex adm_title_btns">
						<h3 class="adm_title">추천 제품 관리</h3><!-- 
						<div class="adm_right"><button class="btn_excel btn_m btn_gray btn">엑셀 다운로드</button></div> -->
					</div>
					<ul class="adm_typeproduct adm_list">
						<li class="adm_list_title">
							<div class="list_con01">고객 성향</div>
							<div class="list_typeall">
								<div class="list_con02">NO.</div>
								<div class="list_con03">이미지</div>
								<div class="list_con04">제품명</div>
								<div class="list_con05">제품설명</div>
								<div class="list_con06 list_btn_wrap">기능</div>
							</div>
							
						</li>
						<!-- 소박함 -->
						<c:forEach items="${productList}" var="types" varStatus="typeIndex">
						<li class="adm_list_con list_line">
							<div class="list_con01"><c:out value="${types.type.title}"/></div>
							<!-- list_typeall start -->
							<div class="list_typeall">
								<ul class="adm_sortable adm_list">
									<c:forEach items="${types.list}" var="products" varStatus="proIndex">
									<li class="adm_list_con list_line">
										<div class="list_con02"><c:out value="0${proIndex.count}"/></div>
										<div class="list_con03"><img src="<c:url value="/u${products.imageList}"/>" alt=""></div>
										<div class="list_con04"><c:out value="${products.name}"/></div>
										<div class="list_con05"><c:out value="${products.subject}"/></div>
										<div class="list_con06 list_btn_wrap">
											<button onClick="javascript:$('#pop_survey_mod1').show();" class="btn_primary btn_s btn">수정</button>
											<button onClick="javascript:$('#pop_survey_del1').show();" class="btn_gray btn_s btn">삭제</button>
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
							<!-- list_typeall end -->
						</li>
						</c:forEach>
						<!-- 소박함 end -->
						
					</ul>
				</div>
				<!-- type_list end-->
			</div>
		</section>
    </main>
    
    <!-- pop_survey_add popup -->
    <section id="pop_survey_add" class="pop_survey modal">
		<div class="popBg">
			<div class="popContent">
				<div class="popTitle_wrap">
					<h2>추천 제품 등록</h2>
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
				<div class="pop_survey_con">
					<h3>상세 설명</h3>
					<div class="row inputBox">
						<input type="text" name="" id="adm_event_title_del" class="w100per line_input" placeholder="제품에 대한 설명을 입력합니다." value="" />
					</div>
				</div>
				<div class="pop_delete_con">
					<h3>URL</h3>
					<div class="row inputBox">
						<textarea name="" id="escape_del_textarea" cols="20" rows="4" placeholder="제품 링크를 이곳에 넣습니다." value=""></textarea>
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
    <section id="pop_survey_mod1" class="pop_survey modal">
        <div class="popBg">
            <!-- 2022-12-20 수정 : 디자인 자체가 바뀌어서 퍼블 다 수정했습니다 다 뜯어보셔야합니다.ㅠ -->
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2 id="updateTitle">추천 제품 수정</h2>
                    <button onClick="javascript:$('#pop_survey_mod1').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3>질문</h3>
                    <div class="row inputBox">
                        <input type="text" name="" id="updateQuestion" class="w100per line_input" placeholder="" value=""/>
                    </div>
                </div>
                <div class="pop_survey_con" id="updateAnswers">
                    <h3>답변</h3>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                    <div class="row inputBox">
                        <input type="text" name="" class="w100per line_input" placeholder="" value="" />
                    </div>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button id="updateButton" class="btn_pop_s btn_primary btn">완료</button>
                </div>
            </div>
        </div>
        <div class="dim"></div>
    </section>
    <!-- popup end -->

    <!-- pop_survey_del1 (문답 삭제 팝업) popup -->
    <section id="pop_survey_del1" class="pop_bbs_del modal">
        <div class="popBg">
            <div class="popContent">
                <div class="popTitle_wrap">
                    <h2 id="removeTitle">추천 제품 삭제</h2>
                    <button onClick="javascript:$('#pop_survey_del1').hide();" class="popup_close"></button>
                </div>
                <div class="pop_delete_con">
                    <h3 class="d_inline_block">제목</h3>
                    <span class="pop_span" id="removeQuestion"></span>
                </div>
                <div class="pop_delete_con" id="removeAnswers">
                    <h3 class="d_inline_block">답변</h3>
                    <span class="pop_span">기능</span>
                    <span class="pop_div">디자인</span>
                    <span class="pop_div">새로움</span>
                    <span class="pop_div">답변</span>
                </div>
                <div class="bottom btn_wrap w100per tac">
                    <button id="removeButton" class="btn_pop_s btn_primary btn">삭제</button>
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