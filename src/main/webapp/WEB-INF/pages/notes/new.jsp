<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigation" tagdir="/WEB-INF/tags/navigation" %>

<html>
<head>
	<title>Spring 3 MVC Series - Contact Manager</title>
	
	<link href="${pageContext.request.contextPath}/themes/common/css/default.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/common/css/navigation.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/common/css/notes.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/common/css/widgets.css" rel="stylesheet" type="text/css"/>
	
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.navigation"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes.errors"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.widgets"/>" rel="stylesheet" type="text/css"/>

	<link href="${pageContext.request.contextPath}/js/clock.css" rel="stylesheet" type="text/css"/>
	
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="${pageContext.request.contextPath}/js/clock.js"></script>
	
</head>
<body>

<navigation:menubar />

<h2>Contact Manager</h2>

<div class="notes">
	<form:form method="post" action="${pageContext.request.contextPath}/notesmanager/add.do" modelAttribute="noteForm">
		<table>
			<tr>
				<td><form:label path="author"><spring:message code="addnote.label.nick"/></form:label></td>
				<td><form:input path="author" /></td> 
				<td><form:errors path="author" cssClass="jsr303error" /></td>
			</tr>
			<tr>
				<td><form:label path="email"><spring:message code="addnote.label.email"/></form:label></td>
				<td><form:input path="email" /></td>
				<td><form:errors path="email" cssClass="jsr303error" /></td>
			</tr>
			<tr>
				<td><form:label path="emailConfirmation"><spring:message code="addnote.label.confirmemail"/></form:label></td>
				<td><form:input path="emailConfirmation" /></td>
				<td><form:errors path="emailConfirmation" cssClass="jsr303error" /></td>
			</tr>
			<tr>
				<td><form:label path="content"><spring:message code="addnote.label.content"/></form:label></td>
				<td><form:textarea path="content" rows="5" cols="30" /></td>
				<td><form:errors path="content" cssClass="jsr303error" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="<spring:message code="addnote.button.addnote"/>" />
				</td>
			</tr>
		</table>	
	</form:form>
</div>
<a href="${pageContext.request.contextPath}/notesmanager/show/">BACK</a>
</body>
</html>
