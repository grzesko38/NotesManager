<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="notes"  tagdir="/WEB-INF/tags/notes" %>

<c:set value="/notesmanager/show/" var="linkCore" />
<notes:datefilter />
<c:if test="${fn:length(notesPaginationData.page.content) gt 0}">
	<notes:notesgrid />
</c:if>
