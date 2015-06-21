<%@ tag body-content="empty" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" %>
<%@ attribute name="iconUrl" required="false" type="java.lang.String" %>
<%@ attribute name="password" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:formInput path="${path}" mandatory="${mandatory}" labelKey="${labelKey}">
	<c:choose>
		<c:when test="${password}">
			<form:password path="${path}" cssClass="${cssClass}"/>
		</c:when>
		<c:otherwise>
			<form:input path="${path}" cssClass="${cssClass}"/>
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty iconUrl}">
		<em class="formInputIcon" style="background: url(${iconUrl}) no-repeat scroll right center"></em>
	</c:if>
</template:formInput>