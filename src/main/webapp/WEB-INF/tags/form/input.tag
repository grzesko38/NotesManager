<%@ tag body-content="empty" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="password" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:formInput path="${path}" mandatory="${mandatory}" labelKey="${labelKey}">
	<c:choose>
		<c:when test="${password}">
			<form:password path="${path}"/>
		</c:when>
		<c:otherwise>
			<form:input path="${path}"/>
		</c:otherwise>
	</c:choose>
</template:formInput>