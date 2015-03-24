<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>

<template:master>
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg">${msg}</div>
	</c:if>
	<utils:globalMessages />

	<c:url value="j_spring_security_check" var="loginUrl"/>
	<formUtil:form action="${loginUrl}" modelAttribute="loginForm" method="POST">
		<formUtil:input labelKey="login.nick" path="nick" mandatory="true"/>
		<formUtil:input labelKey="login.password" path="password" mandatory="true" password="true"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<button>ok</button>
	</formUtil:form>
	<a href="<c:url value="/notesmanager/show" />">
		<spring:message code="login.contunueAsAnonymous" />
	</a>
</template:master>