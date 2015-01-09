<%@ tag body-content="empty" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/form" %>

<template:formInput path="${path}" mandatory="${mandatory}" labelKey="${labelKey}">
	<form:input path="${path}"/>
</template:formInput>