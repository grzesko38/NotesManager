<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="bannerClass" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="tmp">
	<spring:theme code="img.banner.notes.listing"/>
</c:set>
<c:url var="bannerUrl" value="/themes/${tmp}"/>
<c:set var="style" value="url('${bannerUrl}');"/>

<section class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<jsp:doBody />
	<div class="imgHolder" style="background-image: ${style}"></div>
</section>

