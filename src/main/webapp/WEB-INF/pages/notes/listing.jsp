<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>
<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/_fragments" %>

<template:notesPage>
    <jsp:attribute name="banner">
		<fragment:banner />
    </jsp:attribute>
    
    <jsp:body>
		<notes:noteslisting />
		<a href="${pageContext.request.contextPath}/notesmanager/add">new</a>
    </jsp:body>
</template:notesPage>
