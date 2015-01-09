<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>

<template:notesPage>
	<jsp:attribute name="additionalCSS">
		<link href="<c:url value="/themes/common/css/globalMessages.css"/>" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/themes/<spring:theme code="css.globalMessages"/>" rel="stylesheet" type="text/css"/>
	</jsp:attribute>

	<jsp:attribute name="additionalJS">
		
	</jsp:attribute>

    <jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="notes.listing.label.title" />
		</template:banner>
    </jsp:attribute>
    
    <jsp:body>
    	<utils:globalMessages />
		<notes:noteslisting />
		<a href="${pageContext.request.contextPath}/notesmanager/add">new</a>
    </jsp:body>
</template:notesPage>
