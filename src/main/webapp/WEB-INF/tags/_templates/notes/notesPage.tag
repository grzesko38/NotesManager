<%@ tag description="Notes Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="additionalCSS" fragment="true" %>
<%@ attribute name="additionalJS" fragment="true" %>
<%@ attribute name="banner" fragment="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>
<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/_fragments" %>

<template:master>

	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/notes.css"/>" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes"/>" rel="stylesheet" type="text/css"/>
		<jsp:invoke fragment="additionalCSS" />
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		<script src="${pageContext.request.contextPath}/js/login.js"></script>
		<jsp:invoke fragment="additionalJS" />
	</jsp:attribute>
	
	<jsp:attribute name="topBarContent">
		<fragment:auth />
	</jsp:attribute>
	
	<jsp:body>
		<div class="notes">
			<div class="content">
				<jsp:invoke fragment="banner" />
				<jsp:doBody/>
			</div>
		</div>
	</jsp:body>
	
</template:master>