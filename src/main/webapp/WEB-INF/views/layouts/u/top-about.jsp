<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

    <section class="subTop_section">
        <div class="subTop_bg">
            <div class="container">
                <img src="<c:url value="/u/images/sub/sub_about_toptxt.png"/>" alt="금성전파사 새로고침센터" class="pc_view" />
            </div>
        </div>
        <div class="container">
            <div class="subTitle_wrap">
                <h1 class="subTitle">소개</h1>
            </div>
            <ul class="subTab_menu mo_left">
                <li class="ml0"><a class="<c:if test="${fn:startsWith(currentURI, '/u/page/about/world')}">active</c:if>" href="<c:url value="/u/page/about/world.do"/>">금성전파사 새로고침센터</a></li>
                <li><a class="<c:if test="${fn:startsWith(currentURI, '/u/page/about/uncle')}">active</c:if>" href="<c:url value="/u/page/about/uncle.do"/>">금성아저씨</a></li>
                <li><a class="<c:if test="${fn:startsWith(currentURI, '/u/page/about/corner')}">active</c:if>" href="<c:url value="/u/page/about/corner.do"/>">체험 코-너</a></li>
                <li><a class="<c:if test="${fn:startsWith(currentURI, '/u/page/about/product')}">active</c:if>" href="<c:url value="/u/page/about/products.do"/>">전시 제품 안내</a></li>
            </ul>
        </div>
    </section>