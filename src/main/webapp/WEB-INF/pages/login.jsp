<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/formElements" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>

<%@ taglib prefix="banner" tagdir="/WEB-INF/tags/_templates"%>

<template:notesPage>

	<jsp:attribute name="additionalCSS">
	
	</jsp:attribute>

	<jsp:attribute name="additionalJS">

	</jsp:attribute>
	
	<jsp:attribute name="banner">
		<banner:banner bannerClass="details">
			<spring:message code="banner.header.login" />
		</banner:banner>
    </jsp:attribute>
	
	<jsp:body>
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<utils:globalMessages />
	
		<div class="padding-h100 padding-v40">
			<c:url var="listingUrl" value="/notesmanager/show"/>
			<c:url var="registerUrl" value="/register"/>
			<spring:message code="global.introduction" arguments="${listingUrl};${registerUrl}" argumentSeparator=";"/>.
		</div>
		<c:url value="j_spring_security_check" var="loginUrl"/>
		<hr/>
		<formElement:form action="${loginUrl}" modelAttribute="loginForm" method="POST">
			<formElement:input labelKey="login.nick" path="nick" mandatory="true"/>
			<formElement:input labelKey="login.password" path="password" mandatory="true" password="true"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="buttonHolder">
				<input type="submit" class="buttonPositive" value="<spring:message code="login.login"/>" />
			</div>
		</formElement:form>
	</jsp:body>
</template:notesPage>