<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form" %>

<template:master>
	<c:url value="/login.do" var="loginUrl"/>
	<formUtil:form action="${loginUrl}" modelAttribute="loginForm" method="POST">
		<formUtil:input labelKey="login.nick" path="nick" mandatory="true"/>
		<formUtil:input labelKey="login.password" path="password" mandatory="true" password="true"/>
	</formUtil:form>
</template:master>