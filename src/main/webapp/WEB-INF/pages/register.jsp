<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>
<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/_fragments" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/formElements" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>

<%@ taglib prefix="banner" tagdir="/WEB-INF/tags/_templates"%>

<template:notesPage>
	<jsp:attribute name="topBarContent">
		<fragment:auth showRegisterLink="false" />
	</jsp:attribute>
	
	<jsp:attribute name="banner">
		<banner:banner bannerClass="details">
			<spring:message code="global.label.registration" />
		</banner:banner>
    </jsp:attribute>
	
	<jsp:attribute name="additionalJS">
		<script src="${pageContext.request.contextPath}/js/login.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<utils:globalMessages />
	
		<c:url value="/register" var="registerUrl"/>
		<formElement:form action="${registerUrl}" modelAttribute="registerForm" method="post">
			<formElement:input labelKey="register.nick" path="nick" mandatory="true"/>
			<formElement:input password="true" labelKey="register.password" path="password" mandatory="true"/>
			<formElement:input password="true" labelKey="register.password.confirm" path="passwordConfirm" mandatory="true"/>
			<formElement:input labelKey="register.email" path="email" mandatory="true"/>
			<formElement:input labelKey="register.email.confirm" path="emailConfirm" mandatory="true"/>
			<div class="buttonHolder">
				<input type="submit" class="buttonPositive" value="<spring:message code="register.register"/>" />
			</div>
		</formElement:form>
	</jsp:body>
	
</template:notesPage>