<%@ tag description="Banner template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="bannerClass" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<img src="<c:url value="/themes/common/images/banners/quillpen.png" />" width="200" height="200" />
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