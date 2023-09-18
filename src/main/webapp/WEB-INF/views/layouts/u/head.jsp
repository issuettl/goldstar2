<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LG 금성전파사 새로고침센터</title>
    
    <meta property="og:type" content="website">
	<meta property="og:url" content="https://goldstar.lge.co.kr/u/page/index.do">
	<meta property="og:title" content="금성전파사 새로고침센터">
	<meta property="og:image" content="https://goldstar.lge.co.kr/u/images/mata_img_1200x630.png">
	<meta property="og:description" content="스타일부터 마음까지 무엇이든 새로고침">
	<meta property="og:site_name" content="금성전파사 새로고침센터">
	<meta property="og:locale" content="ko_KR">
	<meta property="og:image:width" content="1200">
	<meta property="og:image:height" content="630">
	
    <!-- LG닷컴 css,js -->
    <!-- 210224 파비콘 추가 -->
    <link rel="shortcut icon" href="https://www.lge.co.kr/lg5-common/images/favicon.ico">
    <!-- //210224 파비콘 추가 -->
    <link rel="stylesheet" href="https://www.lge.co.kr/lg5-common/css/reset.min.css" />
    <link rel="stylesheet" href="https://www.lge.co.kr/lg5-common/css/app.min.css" />
    
    
    <script src="https://www.lge.co.kr/lg5-common/js/libs/jquery-2.2.4.min.js"></script>
    <script src="https://www.lge.co.kr/lg5-common/js/libs/jquery.transit.min.js"></script>
    <script src="https://www.lge.co.kr/lg5-common/js/libs/jquery.event.move.min.js"></script>
    <script src="https://www.lge.co.kr/lg5-common/js/libs/jquery.twentytwenty.min.js"></script>
    <!-- CS에서 사용 -->
    <script src="https://www.lge.co.kr/lg5-common/js/libs/jquery.mCustomScrollbar.min.js"></script>
    <!-- // CS에서 사용 -->
    <script src="https://www.lge.co.kr/lg5-common/js/vcui.min.js"></script>
    <script src="https://www.lge.co.kr/lg5-common/js/vcui.common-ui.min.js"></script>
    <script src="https://www.lge.co.kr/lg5-common/js/lg.common.min.js"></script>
    <script>
        var digitalData = digitalData || {};
    </script>
    <script src="https://www.lge.co.kr/lg5-common/js/app.common.min.js"></script>
    <!-- 금성전파사 내부 사용 css,js -->
    
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/common.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/utility.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/slick.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/layout.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/unit.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/main.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/popup.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/contents.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/u/css/renewal.css?v=${versionHtml}"/>" />
    
    <script src="<c:url value="/u/js/slick.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/u/js/script.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/u/js/popup.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/u/js/jquery.cookie.js?v=${versionHtml}"/>"></script>
    
    <script>
    var contextPath = '<c:url value="/"/>';

    $("#dim_loading").hide();
    $(document).ajaxStart(function() {
        $("#dim_loading").show();
    });
    $(document).ajaxStop(function() {
        $("#dim_loading").hide();
    });
    
    $(document).bind("contextmenu", function (e) {
        //e.preventDefault();
        //alert("금성전파사 새로고침 관리자\n\n오른쪽 마우스 버튼은 사용할 수 없습니다.");
    });
    </script>
    
    <c:if test="${profile eq 'prod'}">
<script async src="https://www.googletagmanager.com/gtag/js?id=G-TM6DN5LD71"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-TM6DN5LD71');
</script>
    </c:if>