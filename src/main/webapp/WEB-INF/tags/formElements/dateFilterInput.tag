<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ attribute name="path" required="true" type="java.lang.String"%>
<%@ attribute name="labelKey" required="true" type="java.lang.String"%>
<%@ attribute name="clearMode" required="true" type="java.lang.String"%>
<%@ attribute name="showCalendarIcon" required="true" type="java.lang.Boolean"%>
<%@ attribute name="dateFormatTooltip" required="false" type="java.lang.String"%>

<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>

<form:label path="from"><spring:message code="${labelKey}"/>:</form:label>
<div class="inputContainer">
	<form:input class="date" path="${path}" title="${dateFormatTooltip}" placeholder="${dateFormatTooltip}" />
	<c:set var="filterVal">
		<spring:eval expression="notesPaginationData.deadlineFilter.${path}" />
	</c:set>
	<c:if test="${not empty filterVal}">
		<c:url value="/notesmanager/show" var="clrAction">
			<c:param name="clrDateFilter" value="${clearMode}"/>
		</c:url>
		<em class="cancelDateFilter" data-clraction="${clrAction}"></em>
	</c:if>
</div>
<c:if test="${showCalendarIcon}">
	<em class="formInputIcon"></em>
	<script>
	(function() {
		$("input#${path}").closest('div.inputContainer').next('.formInputIcon').click(function() {
			$("#${path}").datepicker({
		        changeMonth: true,
		        changeYear: true,
		        dateFormat: 'dd/mm/yy'
			});
			$("#${path}").datepicker('show');
		});
	})();	
	</script>
</c:if>
