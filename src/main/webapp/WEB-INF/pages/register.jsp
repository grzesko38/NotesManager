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

	<c:url value="/register" var="registerUrl"/>
	<formUtil:form action="${registerUrl}" modelAttribute="registerForm" method="post">
		<formUtil:input labelKey="register.nick" path="nick" mandatory="true"/>
		<formUtil:input password="true" labelKey="register.password" path="password" mandatory="true"/>
		<formUtil:input password="true" labelKey="register.password.confirm" path="passwordConfirm" mandatory="true"/>
		<formUtil:input labelKey="register.email" path="email" mandatory="true"/>
		<formUtil:input labelKey="register.email.confirm" path="emailConfirm" mandatory="true"/>
		<div class="buttonHolder">
			<input type="submit" class="buttonPositive" value="<spring:message code="register.register"/>" />
		</div>
	</formUtil:form>
	
</template:master>