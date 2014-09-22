<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="navigation" tagdir="/WEB-INF/tags/navigation" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<title>Spring 3 MVC Series - Contact Manager</title>
	
	<link href="<c:url value="/themes/common/css/default.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/themes/common/css/navigation.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/themes/common/css/notes.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/themes/common/css/widgets.css"/>" rel="stylesheet" type="text/css"/>
	
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.navigation"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.notes.errors"/>" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.widgets"/>" rel="stylesheet" type="text/css"/>

	<link href="${pageContext.request.contextPath}/js/clock.css" rel="stylesheet" type="text/css"/>
	
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="${pageContext.request.contextPath}/js/clock.js"></script>
	
</head>
<body>
	<navigation:menu_bar />
	<notes:noteslisting />
	<a href="${pageContext.request.contextPath}/notesmanager/add">new</a>
</body>
</html>
