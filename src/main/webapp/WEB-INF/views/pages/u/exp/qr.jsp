<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 2022-10-26 QR popup start -->
<section id="pop_qr_page" class="pop_mini modal">
	<div class="popBg">
	    <div class="popContent">
	        <div class="popTitle_wrap">
	            <h2><c:out value="${sign.nickDec}"/>의 체험 인증 QR</h2>
	            <button onClick="popClose()" class="popup_close mo_view"></button>
	        </div>
	        <!-- qr -->
	        <div class="qr_wrap">
	            <figure class="qr_figure">
	                <img src="<c:url value="/u/exp/qr.do"/>" alt="">
	            </figure>
	            <p>기분고침 코-너, 새로고침 코-너,<br />
                        리워드 증정을 위해 사용됩니다.</p>
                <div class="w100per tac btn_wrap">
                    <button onClick="popClose()" class="btn_primary btn_pop_s">확인</button>
                </div>
	        </div>
	        <!-- qr end -->
	    </div>
	</div>
	<div class="dim"></div>
</section>
<!-- popup end -->