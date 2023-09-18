<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:out value="${lgeFooter}" escapeXml="false"/>
    <script>
    $(function(){
	    // 앱 하단메뉴 스크롤 기능 사용 여부
	    lgkorUI.setEnableAppScrollBottomMenu(false);
	    // 앱 하단메뉴 노출 여부 
	    lgkorUI.showAppBottomMenu(false);
    });
    </script>
    <c:import url="/WEB-INF/views/layouts/u/loading.jsp" />