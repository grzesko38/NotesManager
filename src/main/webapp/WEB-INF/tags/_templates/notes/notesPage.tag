<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="banner" fragment="true" %>
<%@ attribute name="topText" fragment="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>

<template:master>

	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/notes.css"/>" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes"/>" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes.errors"/>" rel="stylesheet" type="text/css"/>
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		
	</jsp:attribute>
	
	<jsp:body>
		<div class="notes">
			<div class="content">
				<jsp:invoke fragment="banner" />
				<div class=top>
					<span>${topText}</span>			
				</div>
				<jsp:doBody/>
			</div>
			<div class="bottom"></div>
		</div>
	</jsp:body>
	
</template:master>