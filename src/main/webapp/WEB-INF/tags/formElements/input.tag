<%@ tag body-content="empty" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" %>
<%@ attribute name="iconUrl" required="false" type="java.lang.String" %>
<%@ attribute name="password" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showCalendar" required="false" type="java.lang.Boolean"%>
<%@ attribute name="placeholderKey" required="false" type="java.lang.String"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:formInput path="${path}" mandatory="${mandatory}" labelKey="${labelKey}">
	<c:choose>
		<c:when test="${password}">
			<form:password path="${path}" cssClass="${cssClass}"/>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty placeholderKey}">
				<spring:message code="${placeholderKey}" var="placeholder"/>
			</c:if>
			<form:input path="${path}" cssClass="${cssClass}"
						placeholder="${not empty placeholderKey?placeholder:''}" />
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty iconUrl}">
		<em class="formInputIcon" style="background: url(${iconUrl}) no-repeat scroll right center"></em>
	</c:if>
	<c:if test="${showCalendar}">
		<em class="formInputIcon"></em>
		<script>
		(function() {
			$("input#${path} + .formInputIcon").click(function() {
				$("#${path}").datepicker({
					minDate: 0,
			        changeMonth: true,
			        changeYear: true,
			        dateFormat: 'dd/mm/yy'
				});
				$("#${path}").datepicker('show');
			});
		})();	
		</script>
	</c:if>
</template:formInput>