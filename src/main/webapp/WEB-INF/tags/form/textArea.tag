<%@ tag body-content="empty" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="maxChars" required="false" type="java.lang.Integer" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/form" %>

<template:formInput path="${path}" mandatory="${mandatory}" labelKey="${labelKey}">
	<c:choose>
		<c:when test="${not empty maxChars}">
			<form:textarea path="${path}" data-maxchars="${maxChars}" maxLength="${maxChars}"/>
			<div class="charCounter">
				<spring:message code="global.charactersLeft" />:&nbsp;<span>${maxChars}</span>
			</div>
		</c:when>
		<c:otherwise>
			<form:textarea path="${path}"/>
		</c:otherwise>
	</c:choose>
</template:formInput>
