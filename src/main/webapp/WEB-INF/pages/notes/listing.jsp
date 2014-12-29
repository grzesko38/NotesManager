<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>

<template:notesPage>
    <jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="label.notes" />
		</template:banner>
    </jsp:attribute>
    
    <jsp:body>
		<notes:noteslisting />
		<a href="${pageContext.request.contextPath}/notesmanager/add">new</a>
    </jsp:body>
</template:notesPage>
