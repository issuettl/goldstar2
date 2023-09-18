<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tags.rebel9.co.kr/tags" prefix="r9"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/m/head.jsp" />
    <script type="text/javascript" src="<c:url value="/m/js/pages/member/view.js?v=${versionHtml}"/>"></script>
</head>
<body>
    <c:import url="/WEB-INF/views/layouts/m/gnb.jsp" />
    <main>
        <!-- top Menu -->
        <section class="adm_top_section wrapped_flex">
            <div class="left">
                <ul class="top_nav_list">
                    <li class="prev_nav">
                        <a href="<c:url value="/m/member/list.do"/>">회원리스트</a>
                    </li>
                    <li class="current_nav">
                        <h2>회원상세페이지</h2>
                    </li>
                </ul>
            </div>
            <c:import url="/WEB-INF/views/layouts/m/info.jsp" />
        </section>
        <!-- top Menu end -->
        <!-- contents start -->
        <section class="member_section adm_section">
            <!-- topsch_area : 어드민 상단 검색 : 1줄일때 -->
            <div class="topsch_area topsch1 wrapped_flex">
                <div class="left">
                    <h3 class="adm_title">회원가입 고객<span class="adm_title_after"><c:out value="${param.total}"/>명</span></h3>
                </div>
                <div class="inputBox sch_wrap"><input type="text" id="nameSearch" placeholder="이름 검색" value="<c:out value="${param.name}"/>"></div>
                <div class="inputBox sch_wrap"><input type="text" id="phoneSearch" placeholder="휴대폰 번호 검색" value="<c:out value="${param.phone}"/>"></div>
                <div class="date_sch_wrap">
                    <input type="datetime" id="dateStartSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateStartOrg}"/>">
                    <span class="date_during">~</span>
                    <input type="datetime" id="dateEndSearch" maxlength="10" class="input_date" placeholder="날짜 선택" value="<c:out value="${param.dateEndOrg}"/>">
                    <button class="btn_date btn_gray btn click-search">검색</button>
                </div>
            </div>
            <!-- topsch_area end -->
            <div class="adm_con_full roundbox">
                <div class="adm_con_back left"><button class="btn_back btn_line btn click-search">돌아가기</button></div>
                <!-- member_profile -->
                <div class="member_profile adm_part">
                    <h3 class="adm_title">고객정보</h3>
                    <ul class="list_num5 adm_list">
                        <li class="adm_list_title">
                            <!-- 2022-11-30 수정 : 기획서에 반영되지 않은 데이터 수정 -->
                            <div class="list_con01">닉네임</div>
                            <div class="list_con02">이름</div>
                            <div class="list_con03">휴대폰 번호</div>
	                        <div class="list_con08">성별</div>
	                        <div class="list_con09">연령</div>
                            <div class="list_con04">최초 방문일자</div>
                            <div class="list_con05">방문 횟수</div>
                            <div class="list_con06">체험 횟수</div>
                        </li>
                        <li class="adm_list_con list_line">
                            <div class="list_con01"><c:out value="${sign.nickDec}"/></div>
                            <div class="list_con02">
                                <c:out value="${member.nameDec}"/>
                                <c:if test="${empty member.nameDec }">
	                                <c:out value="${member.name}"/>
	                            </c:if>
                            </div>
                            <div class="list_con08">
	                            <c:if test="${not empty member.gender}">
	                                <c:out value="${member.gender.title}"/>
	                            </c:if>
	                        </div>
                            <div class="list_con04">
                                <fmt:parseDate var="created" value="${member.created}" pattern="yyyyMMddHHmmss"/>
                                <fmt:formatDate value="${created}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="list_con09">
                                <c:if test="${not empty member.age}">
                                    <c:out value="${member.age.title}"/>
                                </c:if>
                            </div>
                            <div class="list_con03">
                                <c:out value="${member.phoneDec}"/>
                                <c:if test="${empty member.phoneDec }">
                                    <c:out value="${member.phone}"/>
                                </c:if>
                            </div>
                            <div class="list_con05"><c:out value="${member.visit}"/></div>
                            <div class="list_con06"><c:out value="${member.part}"/></div>
                        </li>
                    </ul>
                </div>
                <!-- member_profile end -->
                <!-- member_worry -->
                <div class="member_worry adm_part">
                    <h3 class="adm_title">고민</h3>
                    <ul class="worry_list">
                        <!-- 체크박스 형태로만 만들고 동작하지 않게 만들었습니다. 각 회원 데이터에 따라 선택된 고민이 있는 경우 체크박스가 checked 됩니다. -->
                        <c:forEach items="${worryTypes}" var="item" varStatus="index">
                            <li><label for="worry<c:out value="${index.count}"/>" class="worrylabel customCheck"><input type="checkbox" id="worry<c:out value="${index.count}"/>" class="worrycheck" disabled <c:if test="${item.name eq sign.worryType.name}">checked="checked"</c:if> /><span class="worry_img"></span></label></li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- member_worry end -->
                <!-- member_solution -->
                <div class="member_solution adm_part">
                    <h3 class="adm_title">새로고침 솔루션</h3>
                    <!-- adm_list -->
                    <ul class="list_num5 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">체험존</div>
                            <div class="list_con02">사전 문답</div>
                            <div class="list_con03">추천 리워드</div>
                            <div class="list_con04">스텝 체크</div>
                            <div class="list_con05">체험 상태</div>
                        </li>
                        <c:forEach items="${cornerTypes}" var="item">
                            <li class="adm_list_con list_line">
                                <div class="list_con01"><c:out value="${item.zone}" escapeXml="false"/></div>
                                <div class="list_con02">
                                   <c:choose>
                                       <c:when test="${item.name eq 'mind'}">
                                           <c:choose>
                                               <c:when test="${not empty mindPart}">종료</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'indiv'}">
                                           <c:choose>
                                               <c:when test="${not empty indivPart}">종료</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'style'}">
                                           <c:choose>
                                               <c:when test="${not empty stylePart}">종료</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'mood'}">
                                           <c:choose>
                                               <c:when test="${not empty moodPart}">종료</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:otherwise>-</c:otherwise>
                                   </c:choose>
                                </div>
                                <div class="list_con03">
                                    <c:choose>
                                       <c:when test="${item.name eq 'mind'}">
                                           <c:choose>
                                               <c:when test="${not empty mindPart}"><c:out value="${mindPart.type.title}"/></c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'indiv'}">
                                           <c:choose>
                                               <c:when test="${not empty indivPart}"><c:out value="${indivPart.type.title}"/></c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'style'}">
                                           <c:choose>
                                               <c:when test="${not empty stylePart}"><c:out value="${stylePart.type.title}"/></c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:otherwise>-</c:otherwise>
                                   </c:choose>
                                </div>
                                <div class="list_con04 list_con_select">
                                    <c:choose>
                                       <c:when test="${item.name eq 'mind'}">
                                           <c:choose>
                                               <c:when test="${not empty mindPart}">
                                                   <select class="staffCheck" data-type="mind" data-sn="<c:out value="${mindPart.sn}"/>">
                                                        <c:forEach items="${staffChecks}" var="staff">
                                                            <option value="<c:out value="${staff.name}"/>" <c:if test="${mindPart.staffCheck.name eq staff.name}">selected="selected"</c:if>><c:out value="${staff.title}"/></option>
                                                        </c:forEach>
                                                    </select>
                                               </c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'indiv'}">
                                           <c:choose>
                                               <c:when test="${not empty indivPart}">
                                                   <select class="staffCheck" data-type="indiv" data-sn="<c:out value="${indivPart.sn}"/>">
                                                        <c:forEach items="${staffChecks}" var="staff">
                                                            <option value="<c:out value="${staff.name}"/>" <c:if test="${indivPart.staffCheck.name eq staff.name}">selected="selected"</c:if>><c:out value="${staff.title}"/></option>
                                                        </c:forEach>
                                                    </select>
                                               </c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'style'}">
                                           <c:choose>
                                               <c:when test="${not empty stylePart}">
                                                   <select class="staffCheck" data-type="style" data-sn="<c:out value="${stylePart.sn}"/>">
                                                        <c:forEach items="${staffChecks}" var="staff">
                                                            <option value="<c:out value="${staff.name}"/>" <c:if test="${stylePart.staffCheck.name eq staff.name}">selected="selected"</c:if>><c:out value="${staff.title}"/></option>
                                                        </c:forEach>
                                                    </select>
                                               </c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'life'}">
                                           <c:choose>
                                               <c:when test="${not empty lifePart}">
                                                   <select class="staffCheck" data-type="life" data-sn="<c:out value="${lifePart.sn}"/>">
                                                        <c:forEach items="${staffChecks}" var="staff">
                                                            <option value="<c:out value="${staff.name}"/>" <c:if test="${lifePart.staffCheck.name eq staff.name}">selected="selected"</c:if>><c:out value="${staff.life}"/></option>
                                                        </c:forEach>
                                                    </select>
                                               </c:when>
                                               <c:otherwise>-</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:otherwise>-</c:otherwise>
                                   </c:choose>
                                </div>
                                <div class="list_con05">
                                    <c:choose>
                                       <c:when test="${item.name eq 'mind'}">
                                           <c:choose>
                                               <c:when test="${mindPart.staffCheck.name eq 'present'}">완료</c:when>
                                               <c:when test="${mindPart.staffCheck.name eq 'notyet'}">사전 문답</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'indiv'}">
                                           <c:choose>
                                               <c:when test="${indivPart.staffCheck.name eq 'present'}">완료</c:when>
                                               <c:when test="${indivPart.staffCheck.name eq 'notyet'}">사전 문답</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'style'}">
                                           <c:choose>
                                               <c:when test="${stylePart.staffCheck.name eq 'present'}">완료</c:when>
                                               <c:when test="${stylePart.staffCheck.name eq 'notyet'}">사전 문답</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'mood'}">
                                           <c:choose>
                                               <c:when test="${moodPart.staffCheck.name eq 'present'}">완료</c:when>
                                               <c:when test="${moodPart.staffCheck.name eq 'notyet'}">사전 문답</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'life'}">
                                           <c:choose>
                                               <c:when test="${not empty lifePart}">
		                                           <c:choose>
                                                       <c:when test="${lifePart.status.name eq 'status2'}">예약</c:when>
                                                       <c:when test="${lifePart.status.name eq 'status4'}">완료</c:when>
		                                               <c:otherwise>미체험</c:otherwise>
		                                           </c:choose>
		                                        </c:when>
                                                <c:otherwise>미체험</c:otherwise>
                                            </c:choose>
                                       </c:when>
                                       <c:when test="${item.name eq 'refresh'}">
                                           <c:choose>
                                               <c:when test="${refreshPart.staffCheck.name eq 'present'}">완료</c:when>
                                               <c:when test="${refreshPart.staffCheck.name eq 'notyet'}">미체험</c:when>
                                               <c:otherwise>미체험</c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:otherwise>미체험</c:otherwise>
                                   </c:choose>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- member_solution end -->
                <!-- member_jammy -->
                <div class="member_jammy adm_part">
                    <h3 class="adm_title"><span class="font-mon">JAMMY</span> 포인트 지급 (개성고침 코-너)</h3>
                    <!-- adm_list -->
                    <ul class="list_num1 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">개성고침 코-너 방문</div>
                        </li>
                        <li class="adm_list_con list_line">
                            <div class="list_con01" id="jammyZone">
                            <c:choose>
                                <c:when test="${empty jammy}">
		                            <button class="btn_m btn_primary btn doJammy" data-sn="<c:out value="${member.sn}"/>">2000g 발급하기</button>
                                </c:when>
                                <c:otherwise>
	                                <p>발급 완료</p>
	                                <p>(<c:out value="${jammy.code}"/>)</p>
                                </c:otherwise>
                            </c:choose>
                            </div>
                        </li>
                    </ul>
                    <!-- adm_list end -->
                </div>
                <!-- member_jammy end -->
                <!-- member_history -->
                <div class="member_history adm_part">
                    <h3 class="adm_title">지난 체험 이력</h3>
                    <!-- adm_list -->
                    <ul class="list_num5 adm_list">
                        <li class="adm_list_title">
                            <div class="list_con01">방문 일자</div>
                            <div class="list_con02">닉네임</div>
                            <div class="list_con03">고민</div>
                            <!-- 2022-11-25 수정 -->
                            <div class="list_con04">체험 코-너</div>
                            <div class="list_con05">체험 여부</div>
                        </li>
                        <c:forEach items="${parts}" var="item">
                        <li class="adm_list_con list_line">
                            <!-- 2022-11-25 수정 : 날짜 구조 통일 2022-11-22 이런 형태로 전부 통일 부탁드립니다. -->
                            <div class="list_con01">
                                <fmt:parseDate var="created" value="${item.sign.id.created}" pattern="yyyyMMdd"/>
                                <fmt:formatDate value="${created}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="list_con02"><c:out value="${item.sign.nickDec}"/></div>
                            <div class="list_con03"><c:out value="${item.sign.worryType.desc}"/></div>
                            <!-- 2022-11-25 수정 -->
                            <div class="list_con04 list_parts">
                                <c:forEach items="${cornerTypes}" var="corner">
                                <span class="parts"><c:out value="${corner.zoneLine}"/></span>
                                </c:forEach>
                            </div>
                            <div class="list_con05 list_parts">
                                <c:forEach items="${cornerTypes}" var="corner">
                                    <c:choose>
                                       <c:when test="${corner.name eq 'mind'}">
                                           <c:choose>
                                               <c:when test="${item.mind.staffCheck.name eq 'present'}"><span class="complete parts">완료</span></c:when>
                                               <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${corner.name eq 'indiv'}">
                                           <c:choose>
                                               <c:when test="${item.indiv.staffCheck.name eq 'present'}"><span class="complete parts">완료</span></c:when>
                                               <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${corner.name eq 'style'}">
                                           <c:choose>
                                               <c:when test="${item.style.staffCheck.name eq 'present'}"><span class="complete parts">완료</span></c:when>
                                               <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${corner.name eq 'mood'}">
                                           <c:choose>
                                               <c:when test="${item.mood.staffCheck.name eq 'present'}"><span class="complete parts">완료</span></c:when>
                                               <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:when test="${corner.name eq 'life'}">
                                           <c:choose>
                                               <c:when test="${item.life.status.name eq 'status4'}">
                                                   <span class="complete parts">완료</span>
                                                </c:when>
                                                <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                            </c:choose>
                                       </c:when>
                                       <c:when test="${corner.name eq 'refresh'}">
                                           <c:choose>
                                               <c:when test="${item.refresh.staffCheck.name eq 'present'}"><span class="complete parts">완료</span></c:when>
                                               <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                           </c:choose>
                                       </c:when>
                                       <c:otherwise><span class="parts">미체험</span></c:otherwise>
                                   </c:choose>
                                </c:forEach>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                    <!-- adm_list end -->
                </div>
                <!-- member_history end -->
            </div>
        </section>
    </main>
<form id="searchForm" action="<c:url value="/m/member/list.do"/>" method="post">
    <input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
    <input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
    <input type="hidden" name="phone" value="<c:out value="${param.phone}"/>"/>
    <input type="hidden" name="dateStartOrg" value="<c:out value="${param.dateStartOrg}"/>"/>
    <input type="hidden" name="dateEndOrg" value="<c:out value="${param.dateEndOrg}"/>"/>
    <input type="hidden" name="dateStart" value="<c:out value="${param.dateStart}"/>"/>
    <input type="hidden" name="dateEnd" value="<c:out value="${param.dateEnd}"/>"/>
</form>
<c:import url="/WEB-INF/views/layouts/m/loading.jsp" />
</body>
</html>