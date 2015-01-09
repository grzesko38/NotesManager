<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="utils" uri="http://arczynskiadam.pl/jsp/tlds/utils" %>
<%@ taglib prefix="navigation" uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>

<spring:theme code="theme.name" var="themeName"/>
<c:set var="isBlueTheme" value="${themeName eq 'blue'}" />
<c:set var="isRedTheme" value="${themeName eq 'red'}" />
<c:set var="isGreenTheme" value="${themeName eq 'green'}" />	
<c:set var="isYellowTheme" value="${themeName eq 'yellow'}" />

<!-- workaround for Eclipse validation bug -->
<c:set var="locale" value="${pageContext.response.locale}"/>
<!--end of workarround -->

<c:set var="isUKLocale" value="${locale eq 'en_EN'}" />
<c:set var="isPLLocale" value="${locale eq 'pl_PL'}" />
		
<header class="top">
	<div class="topbar">
		<div class="dateholder">		
			<utils:date separator="/" />
		</div>
	</div>
	<div class="menu">
		<nav class="buttonsBar">
			<c:url value="" var="themeUrl">
				<c:param name="theme" value="blue"/>
			</c:url>
			<a href="${themeUrl}">
				<span class="blue buttonBorder${isBlueTheme?" active":""}">
					<span class="blue themeholder${isBlueTheme?" active":""}"></span>
				</span>
			</a>
			
			<c:url value="" var="themeUrl">
				<c:param name="theme" value="yellow"/>
			</c:url>
			<a href="${themeUrl}">
				<span class="yellow buttonBorder${isYellowTheme?" active":""}">
					<span class="yellow themeholder${isYellowTheme?" active":""}"></span>
				</span>
			</a>
			
			<c:url value="" var="themeUrl">
				<c:param name="theme" value="green"/>
			</c:url>
			<a href="${themeUrl}">
				<span class="green buttonBorder${isGreenTheme?" active":""}">
					<span class="green themeholder${isGreenTheme?" active":""}"></span>
				</span>
			</a>
			
			<c:url value="" var="themeUrl">
				<c:param name="theme" value="red"/>
			</c:url>
			<a href="${themeUrl}">
				<span class="red buttonBorder${isRedTheme?" active":""}">
					<span class="red themeholder${isRedTheme?" active":""}"></span>
				</span>
			</a>
		</nav>
		<nav class="buttonsBar">
			<c:url value="" var="langUrl">
				<c:param name="lang" value="en_EN"/>
			</c:url>
			<a href="${langUrl}">
				<span class="uk buttonBorder${isUKLocale?" active":""}">
					<span class="uk flagholder dark"></span>
					<span class="uk flagholder${isUKLocale?" active":""}"></span>
				</span>
			</a>
			
			<c:url value="" var="langUrl">
				<c:param name="lang" value="pl_PL"/>
			</c:url>
			<a href="${langUrl}">
				<span class="pl buttonBorder${isPLLocale?" active":""}">
					<span class="pl flagholder dark"></span>
					<span class="pl flagholder${isPLLocale?" active":""}"></span>
				</span>
			</a>
		</nav>

	</div>
	<div class="clockHolder">
		<canvas class="clockBar" id="clock" width="75" height="75"></canvas>
	</div>
	<c:if test="${not empty breadcrumbs}">
		<div class="breadcrumbs">
			<navigation:breadcrumbs items="${breadcrumbs}" />
			<div class="breadcrumbs_corner">
				<img src="${pageContext.request.contextPath}/themes/<spring:theme code="img.header.breadcrumbs.corner"/>"
				width="33" height="37"/>
			</div>
		</div>
	</c:if>	
</header>