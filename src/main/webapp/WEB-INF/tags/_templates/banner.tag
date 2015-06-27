<%@ tag description="Banner template" body-content="scriptless" pageEncoding="UTF-8"%>

<%@ attribute name="bannerClass" %>
<%@ attribute name="bannerImgPath" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<c:if test="${not empty bannerImgPath}">
		<img src="<c:url value="${bannerImgPath}" />" />
	</c:if>
	<div class="imgHolder">
		<c:set var="path">
			<spring:theme code="img.banner.notes"/>
		</c:set>
		<c:url var="url" value="/themes/${path}"/>
		<img src="${url}" />
	</div>
	<div class="top">
		<span>
			<jsp:doBody />
		</span>			
	</div>
</div>