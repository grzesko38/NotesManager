<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>
<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/_fragments" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>

<template:notesPage>
	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/googlemap.css"/>" rel="stylesheet" type="text/css"/>
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		<script src="https://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
		<script src="${pageContext.request.contextPath}/js/notes/googlemap.js"></script>
	</jsp:attribute>
	
	<jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="notes.addNew.label.title" />
		</template:banner>
    </jsp:attribute>
        
    <jsp:body>
    	<utils:globalMessages />
		<formUtil:form method="post" action="${pageContext.request.contextPath}/notesmanager/add" modelAttribute="noteForm">
			<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
				<formUtil:input path="author" mandatory="true" labelKey="notes.addNew.label.nick" />
			</security:authorize>
 			<security:authorize ifNotGranted="ROLE_ANONYMOUS">
				<form:hidden path="author" value="${userName}" />
			</security:authorize>
			<formUtil:input path="title" mandatory="true" labelKey="notes.addNew.label.title" />
			<formUtil:textArea path="content" mandatory="true" labelKey="notes.addNew.label.content" maxChars="4000" />
			<form:hidden path="longitude"/>
			<form:hidden path="latitude"/>
			<div class="buttonHolder">
				<input type="submit" class="buttonPositive" value="<spring:message code="notes.addNew.button.save"/>" />
			</div>
		</formUtil:form>
		
		<fragment:googlemap />
		
    </jsp:body>
</template:notesPage>
