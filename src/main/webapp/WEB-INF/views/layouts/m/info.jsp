<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

            <div class="right">
                <p class="top_profile_text"><strong><c:out value="${signManager.name}"/> 님</strong> 안녕하세요.</p>
                <button onClick="location.href='<c:url value="/m/manager/out.do"/>'" class="btn_top btn_primary btn">로그아웃</button>
            </div>