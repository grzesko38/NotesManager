<%@ tag body-content="scriptless" %>

<%@ attribute name="method" required="true" %>
<%@ attribute name="modelAttribute" required="true" %>
<%@ attribute name="action" required="true" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="formWrapper">
	<form:form method="${method}" action="${action}" modelAttribute="${modelAttribute}">
		<jsp:doBody />
	</form:form>
</div>