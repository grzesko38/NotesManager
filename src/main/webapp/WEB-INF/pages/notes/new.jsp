<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>

<template:notesPage>
	<jsp:attribute name="additionalCSS">

	</jsp:attribute>
	
	<jsp:attribute name="additionalJS">
		
	</jsp:attribute>
	
	<jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="notes.addNew.label.title" />
		</template:banner>
    </jsp:attribute>
        
    <jsp:body>
    	<utils:globalMessages />
		<formUtil:form method="post" action="${pageContext.request.contextPath}/notesmanager/add.do" modelAttribute="noteForm">
			<formUtil:input path="author" mandatory="true" labelKey="notes.addNew.label.nick" />
			<formUtil:textArea path="content"  mandatory="true" labelKey="notes.addNew.label.content" />
			<div class="buttonHolder">
				<input type="submit" class="buttonPositive" value="<spring:message code="notes.addNew.button.save"/>" />
			</div>
		</formUtil:form>
    </jsp:body>
</template:notesPage>
