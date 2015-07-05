<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" 		 	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="spring"	 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="formElement"	tagdir="/WEB-INF/tags/formElements" %>

<%@ attribute name="action" required="true" %>

<formElement:form method="post" action="${action}" modelAttribute="noteForm">
	<form:hidden path="id" />
	<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
		<formElement:input path="author" mandatory="true" labelKey="notes.addNew.label.nick" />
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ANONYMOUS">
		<form:hidden path="author" value="${userName}" />
	</security:authorize>
	<formElement:input path="title" mandatory="true" labelKey="notes.addNew.label.title" />
	<c:set var="themeName"><spring:theme code="theme.name"/></c:set>
	<c:url var="iconUrl" value="/themes/${themeName}/images/icons/calendar.png"/>
	<formElement:input path="deadline" mandatory="true" labelKey="notes.addNew.label.deadline"
					   iconUrl="${iconUrl}" showCalendar="true" placeholderKey="notes.addNew.deadline.tooltip.dateFormat"/>
	<formElement:textArea path="content" mandatory="true" labelKey="notes.addNew.label.content" maxChars="4000" />
	<form:hidden path="longitude" />
	<form:hidden path="latitude" />
	<div class="buttonHolder">
		<input type="submit" class="buttonPositive"	value="<spring:message code="notes.addNew.button.save"/>" />
	</div>
</formElement:form>