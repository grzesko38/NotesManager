<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tg"	   tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="notes"  tagdir="/WEB-INF/tags/notes" %>

<div class=top>
	<span><spring:message code="label.notes" /></span>
</div>
	<c:set value="/notesmanager/show/" var="linkCore" />
	<notes:datefilter />
	<notes:notesgrid />
	<tg:paging pagedListHolder="${notesPaginationData.pagedListHolder}" linkCore="${linkCore}" />
<div class="bottom"></div>
