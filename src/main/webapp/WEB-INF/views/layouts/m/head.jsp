<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LG 금성전파사 관리자</title>
    <!-- 홈페이지와 공통공유 css -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/common.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/utility.css?v=${versionHtml}"/>" />
    <!-- admin에서만 사용 css -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/jquery-ui.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/layout.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/slick.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/unit.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/admin.css?v=${versionHtml}"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/m/css/popup.css?v=${versionHtml}"/>" />
    <script src="<c:url value="/m/js/jquery-3.5.1.min.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/m/js/jquery-ui.min.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/m/js/slick.min.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/m/js/admin.js?v=${versionHtml}"/>"></script>
    <script src="<c:url value="/m/js/string.js?v=${versionHtml}"/>"></script>
    
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