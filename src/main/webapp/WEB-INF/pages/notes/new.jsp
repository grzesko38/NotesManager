<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes"%>
<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/_fragments"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils"%>

<template:notesPage>
	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/googlemap.css"/>"
			rel="stylesheet" type="text/css" />
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		<script	src="https://maps.googleapis.com/maps/api/js?libraries=places&sensor=false&v=3"></script>
		<script src="${pageContext.request.contextPath}/js/notes/addnote.js"></script>
	</jsp:attribute>

	<jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="notes.addNew.label.title" />
		</template:banner>
    </jsp:attribute>

	<jsp:body>
		<div id="tabs">
			<ul>
		    	<li><a href="#tabs-1"><spring:message code="notes.tab.label.details"/></a></li>
		    	<li id="map-li"><a href="#tabs-2"><spring:message code="notes.tab.label.map"/></a></li>
			</ul>
			<div id="tabs-1">
				<utils:globalMessages />
				<formUtil:form method="post" action="${pageContext.request.contextPath}/notesmanager/add" modelAttribute="noteForm">
					<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
						<formUtil:input path="author" mandatory="true" labelKey="notes.addNew.label.nick" />
					</security:authorize>
		 			<security:authorize ifNotGranted="ROLE_ANONYMOUS">
						<form:hidden path="author" value="${userName}" />
					</security:authorize>
					<formUtil:input path="title" mandatory="true" labelKey="notes.addNew.label.title" />
					<c:set var="themeName"><spring:theme code="theme.name"/></c:set>
					<c:url var="iconUrl" value="/themes/${themeName}/images/icons/calendar.png"/>
					<formUtil:input path="deadline" mandatory="true" labelKey="notes.addNew.label.deadline" iconUrl="${iconUrl}"/>
					<formUtil:textArea path="content" mandatory="true" labelKey="notes.addNew.label.content" maxChars="4000" />
					<form:hidden path="longitude" />
					<form:hidden path="latitude" />
					<div class="buttonHolder">
						<input type="submit" class="buttonPositive"	value="<spring:message code="notes.addNew.button.save"/>" />
					</div>
				</formUtil:form>
			</div>
			<div id="tabs-2">
				<fragment:googlemap />
			</div>
		</div>
    </jsp:body>
</template:notesPage>
