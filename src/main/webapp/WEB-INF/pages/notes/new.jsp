<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>

<template:master>
    <jsp:attribute name="banner">
<!-- 		//TODO here -->
    </jsp:attribute>
    <jsp:body>
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
    </jsp:body>
</template:master>
</html>
