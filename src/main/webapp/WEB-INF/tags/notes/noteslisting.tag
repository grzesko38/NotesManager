<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" 	 uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	  	 uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="notes"  	 tagdir="/WEB-INF/tags/notes" %>

<c:set value="/notesmanager/show/" var="linkCore" />

<c:if test="${fn:length(notesPaginationData.page.content) gt 0}">
	<notes:datefilter />
	<notes:notesgrid />
</c:if>
<c:if test="${empty notesPaginationData.page.content}">
	<notes:datefilter />
	<a href="<c:url value="/notesmanager/add" />">
		<span class="buttonPositive">
			<spring:message code="notes.listing.addNew" />	
		</span>
	</a>
</c:if>

<spring:message code="global.delete.all.popup.info" arguments="${notesPaginationData.page.totalElements}" var="askDeleteAll" />
<div id="popupI18NData"
	 data-askdeleteall="${askDeleteAll}"
	 data-askheader='<spring:message code="global.areYouSure" />'
	 data-yes='<spring:message code="misc.yes" />'
	 data-no='<spring:message code="misc.no" />'
	 data-close='<spring:message code="misc.close" />' >
</div>
<div id="dialog-deleteSelected" title="<spring:message code="global.areYouSure" />" >
	<spring:message code="global.delete.selected.popup.info" arguments="${fn:length(notesPaginationData.selectedNotesIds)}" />
</div>
<div id="dialog-deleteAll" title="<spring:message code="global.areYouSure" />">
	<spring:message code="global.delete.all.popup.info" arguments="${notesPaginationData.page.totalElements}"/>
</div>