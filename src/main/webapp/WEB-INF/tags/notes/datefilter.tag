<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="formUtil"	tagdir="/WEB-INF/tags/form" %>

<script src="${pageContext.request.contextPath}/js/notes/datefilter.js"></script>

<spring:message code="notes.listing.dateFilter.tooltip.dateFormat" var="dateFormatTooltip"/>
<form:form id="dateFilterForm" action="${pageContext.request.contextPath}/notesmanager/show" method="get" modelAttribute="dateFilterForm"
		   class="${userName eq 'anonymous' ? 'indent22' : 'indent62'}">
	<div class="dateFilterWrapper">
		<c:set var="themeName"><spring:theme code="theme.name"/></c:set>
		<c:url var="iconUrl" value="/themes/${themeName}/images/icons/calendar.png"/>
		<formUtil:dateFilterInput path="from" clearMode="from" labelKey="notes.listing.dateFilter.label.from"
								  showCalendarIcon="true" dateFormatTooltip="${dateFormatTooltip}"/>
		<formUtil:dateFilterInput path="to" clearMode="to" labelKey="notes.listing.dateFilter.label.to"
								  showCalendarIcon="true" dateFormatTooltip="${dateFormatTooltip}"/>
	</div>
	<button id="submitDateFilterButton">
       	<span><spring:message code="notes.listing.dateFilter.button.go"/></span>
        <img id="goimg" src="${pageContext.request.contextPath}/themes/<spring:theme code="img.nav.arrow.right"/>" width="25" height="25"/>
    </button>
</form:form>