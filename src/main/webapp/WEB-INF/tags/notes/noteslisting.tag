<%@ taglib prefix="spring" 	 uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	  	 uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="notes"  	 tagdir="/WEB-INF/tags/notes" %>

<c:set value="/notesmanager/show/" var="linkCore" />

<c:choose>
	<c:when test="${fn:length(notesPaginationData.page.content) gt 0}">
		<notes:datefilter />
		<notes:notesgrid />
	</c:when>
	<c:otherwise>
		<c:if test="${not empty notesPaginationData.fromDate}">
			<notes:datefilter />
		</c:if>
	</c:otherwise>
</c:choose>

<div class="buttonsRow">
	<a href="<c:url value="/notesmanager/add" />">
		<span class="buttonPositive">
			<spring:message code="notes.listing.addNew" />	
		</span>
	</a>
	<security:authorize ifNotGranted="ROLE_ANONYMOUS">
		<c:if test="${fn:length(notesPaginationData.page.content) gt 0}">
			<span id="deleteSelectedNotes" class="buttonPositive">
				<spring:message code="notes.listing.delete.selected" />
			</span>
			<span id="deleteAllNotes" class="buttonPositive">
				<spring:message code="notes.listing.delete.all" />
			</span>
		</c:if>
	</security:authorize>
	
	<spring:message code="global.delete.all.popup" arguments="${notesPaginationData.page.totalElements}" var="askDeleteAll" />
	<div id="popupI18NData"
		 data-askdeleteall="${askDeleteAll}"
		 data-askheader='<spring:message code="global.areYouSure" />'
		 data-yes='<spring:message code="misc.yes" />'
		 data-no='<spring:message code="misc.no" />'
		 data-close='<spring:message code="misc.close" />' >
	</div>
		 
	<div id="dialog-deleteSelected" title='<spring:message code="global.areYouSure" />'>
		<spring:message code="global.delete.popup.begin" />
		<span>${fn:length(notesPaginationData.selectedNotesIds)}</span>
		<spring:message code="global.delete.popup.end" />
	</div>
</div>