<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/formElements"%>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes"%>
<%@ taglib prefix="banner" tagdir="/WEB-INF/tags/_templates"%>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils"%>

<template:notesPage>
	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/googlemap.css"/>" rel="stylesheet" type="text/css" />
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		<script	src="https://maps.googleapis.com/maps/api/js?libraries=places&sensor=false&v=3"></script>
		<script src="${pageContext.request.contextPath}/js/googlemaps.js"></script>
		<script src="${pageContext.request.contextPath}/js/notes/noteTabs.js"></script>
	</jsp:attribute>

	<jsp:attribute name="banner">
		<banner:banner bannerClass="listing">
			<spring:message code="notes.addNew.label.title" />
		</banner:banner>
    </jsp:attribute>

	<jsp:body>
		<div id="tabs">
			<ul>
		    	<li><a href="#tabs-1"><spring:message code="notes.tab.label.details"/></a></li>
		    	<li id="map-li"><a href="#tabs-2"><spring:message code="notes.tab.label.map"/></a></li>
			</ul>
			<div id="tabs-1">
				<utils:globalMessages />
				<c:url value="/notesmanager/edit/..." var="action"/>
				<forms:noteForm action="${action}" />
			</div>
			<div id="tabs-2" class="googleMapTab">
				<utils:googlemap />
			</div>
		</div>
    </jsp:body>
</template:notesPage>