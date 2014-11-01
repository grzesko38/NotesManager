<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="bannerClass" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<jsp:doBody />
	<div class="imgHolder">
		<div class="colorizer"></div>
		<div>
			<c:set var="path">
				<spring:theme code="img.banner.notes"/>
			</c:set>
			<c:url var="url" value="/themes/${path}"/>
			<img src="${url}" />
		</div>
	</div>
</div>

