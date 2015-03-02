<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form" %>

<template:master>
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg">${msg}</div>
	</c:if>

	<c:url value='j_spring_security_check' var="loginUrl"/>

<!-- 	<form name='loginForm' -->
<%-- 		  action="<c:url value='/j_spring_security_check' />" method='POST'> --%>
 
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<td>User:</td> -->
<!-- 				<td><input type='text' name='username'></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>Password:</td> -->
<!-- 				<td><input type='password' name='password' /></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan='2'><input name="submit" type="submit" -->
<!-- 				  value="submit" /></td> -->
<!-- 			</tr> -->
<!-- 		  </table> -->
 
<%-- 		  <input type="hidden" name="${_csrf.parameterName}" --%>
<%-- 			value="${_csrf.token}" /> --%>
 
<!-- 		</form> -->

	<formUtil:form action="${loginUrl}" modelAttribute="loginForm" method="POST">
		<formUtil:input labelKey="login.nick" path="nick" mandatory="true"/>
		<formUtil:input labelKey="login.password" path="password" mandatory="true" password="true"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<button>ok</button>
	</formUtil:form>
</template:master>