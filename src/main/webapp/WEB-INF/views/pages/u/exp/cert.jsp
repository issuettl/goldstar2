<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <c:import url="/WEB-INF/views/layouts/u/head.jsp" />
    <script type="text/javascript" src="<c:url value="/u/js/html2canvas.min.js?v=${versionHtml}"/>"></script>
</head>
<body class="experience_certificate_20221224">
    <c:import url="/WEB-INF/views/layouts/u/header.jsp" />
    <div class="sub">
        <section class="subGnb_section">
            <div class="container">
                <c:import url="/WEB-INF/views/layouts/u/gnb.jsp" />
            </div>
        </section>
        <section class="subTop_section">
        </section>
        <section class="regi2023 exper_page sub_section">
            <figure></figure>
            <div class="container">
                <div class="exper_chatTitle">
                    <p class="tac">‘<c:out value="${sign.worryType.desc}"/> 고민’을 가진<br />
                        <c:out value="${sign.nickDec}"/>의 새로고침 솔루션</p>
                </div>
                <div class="exper_person"></div>
                <div class="mypage_cert">
                    <!-- ul class="sub_text_nav">
                        <li class="left"><a href="<c:url value="/u/exp/corner.do"/>" class="text_navlink_left">나의 고민 관리</a></li>
                    </ul -->
                    <div class="subTitle_wrap">
                        <h1 class="subTitle subTitle_mypage">새로고침 보증서</h1>
                    </div>
                    <!-- test 이미지 -->
                    <div class="cert_wrap" id="downloadImage">
                        <!-- 폰트 임베딩 라이센스가 안되는 폰트를 사용해서 
                            각 상황별 이미지를 따로 제작해서 적용했습니다. 
                            이미지 네이밍 규칙 
                            관계 : cert_relation_
                            일상 : cert_life_
                            건강 : cert_health_
                            미래 : cert_future_
                            자신감 : cert_confidence_
                            도전 : cert_challenge_
                            lv1 : 1~2개만 했을때 
                            lv2 : 3~5개만 했을때
                            lv3 : 6개 전부 했을때
                        -->
                        <!-- 
                        <img src="../../images/cert/sample.png" alt="" class="cert_bg"> -->
                        
                        <img src="<c:url value="/u/images/cert/${sign.worryType.cert}lv${level}.png"/>" alt="" class="cert_bg">
                        <div class="cert_name cert_text_s"><span class="name"><c:out value="${sign.nickDec}"/></span>님</div>
                        <div class="cert_worry cert_text_s"><c:out value="${sign.worryType.certText}"/></div>
                        <div class="cert_corner cert_text_s">
                            <c:set var="text" value=""/>
                            <c:if test="${not empty mind}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>마음</span>"/>
                            </c:if>
                            <c:if test="${not empty style}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>스타일</span>"/>
                            </c:if>
                            <c:if test="${not empty indiv}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>개성</span>"/>
                            </c:if>
                            <c:if test="${not empty mood}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>기분</span>"/>
                            </c:if>
                            <c:if test="${not empty life}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>고민</span>"/>
                            </c:if>
                            <c:if test="${not empty refresh}">
                                <c:if test="${not empty text}"><c:set var="text" value="${text}, "/></c:if>
                                <c:set var="text" value="${text}<span>새로</span>"/>
                            </c:if>
                            <c:out value="${text}" escapeXml="false"/>
                        </div>
                        <div class="cert_text_m">
                            <p><span class="name"><c:out value="${sign.nickDec}"/></span>님이 새로 고쳐질 수 있는 한마디</p>
                        </div>
                    </div>
                </div>
                <div class="btn_full btn_wrap">
                    <!-- 이 부분은 임시로 배경이미지만 다운로드 받게 작성했습니다. -->
                    <a class="large_btn_black btn_ex btn downloadImage">다운로드</a>
                </div>
            </div>
        </section>
    </div>
    
    <c:import url="/WEB-INF/views/layouts/u/footer.jsp" />
    <script>
    function printDiv(){
    	$("#dim_loading").show();
    	var div = $("#downloadImage")[0];
    	html2canvas(div).then(function(canvas){
    		var myImage = canvas.toDataURL();
    		downloadURI(myImage, "goldstar.png");
    	});
    }
    
    function downloadURI(uri, name) {
    	var link = document.createElement("a");
    	link.download = name;
    	link.href = uri;
    	document.body.appendChild(link);
    	link.click();
    	$("#dim_loading").hide();
    }
    
    $(".downloadImage").on({
    	"click" : printDiv
    });
    
    </script>
</body>
</html>
