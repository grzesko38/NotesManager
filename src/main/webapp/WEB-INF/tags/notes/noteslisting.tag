<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tg"	   tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="notes"  tagdir="/WEB-INF/tags/notes" %>


<c:set value="/notesmanager/show/" var="linkCore" />
<notes:datefilter />
<c:if test="${notesPaginationData.page.size > 10}">
	<tg:paging paginationData="${notesPaginationData}" linkCore="${linkCore}" />
</c:if>
<notes:notesgrid />
<tg:paging paginationData="${notesPaginationData}" linkCore="${linkCore}" />

