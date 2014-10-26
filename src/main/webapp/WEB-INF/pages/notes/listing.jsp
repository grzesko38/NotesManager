<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>

<template:master>
    <jsp:attribute name="banner">
<%--     	//TODO HERE --%>
    </jsp:attribute>
    <jsp:body>
        <notes:noteslisting />
        <a href="${pageContext.request.contextPath}/notesmanager/add">new</a>
    </jsp:body>
</template:master>
