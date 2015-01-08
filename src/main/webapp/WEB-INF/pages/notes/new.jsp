<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="notes" tagdir="/WEB-INF/tags/notes" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/notes" %>

<template:notesPage>
   <jsp:attribute name="banner">
		<template:banner bannerClass="listing">
			<spring:message code="notes.addNew.label.title" />
		</template:banner>
    </jsp:attribute>
        
    <jsp:body>
		<form:form method="post" action="${pageContext.request.contextPath}/notesmanager/add.do" modelAttribute="noteForm">
			<table>
				<tr>
					<td><form:label path="author"><spring:message code="notes.addNew.label.nick"/>:</form:label></td>
					<td><form:input path="author" /></td> 
					<td><form:errors path="author" cssClass="jsr303error" /></td>
				</tr>
				<tr>
					<td><form:label path="email"><spring:message code="notes.addNew.label.email"/>:</form:label></td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" cssClass="jsr303error" /></td>
				</tr>
				<tr>
					<td><form:label path="emailConfirmation"><spring:message code="notes.addNew.label.email.confirm"/>:</form:label></td>
					<td><form:input path="emailConfirmation" /></td>
					<td><form:errors path="emailConfirmation" cssClass="jsr303error" /></td>
				</tr>
				<tr>
					<td><form:label path="content"><spring:message code="notes.addNew.label.content"/>:</form:label></td>
					<td><form:textarea path="content" rows="5" cols="30" /></td>
					<td><form:errors path="content" cssClass="jsr303error" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="<spring:message code="notes.addNew.button.save"/>" />
					</td>
				</tr>
			</table>	
		</form:form>
		<a href="${pageContext.request.contextPath}/notesmanager/show/">BACK</a>
    </jsp:body>
</template:notesPage>
