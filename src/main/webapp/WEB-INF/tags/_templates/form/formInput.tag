<%@ tag body-content="scriptless" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="${path}">
	<div class="formRow${not empty status.errorMessages ? ' error' : ''}">
		<form:label path="${path}">
			<spring:message code="${labelKey}"/>:
			<c:if test="${mandatory}">
				<span class="mandatory">*</span>
			</c:if>
		</form:label>
		<jsp:doBody />
		<c:if test="${not empty status.errorMessages}">
			<div>
				<form:errors path="${path}" cssClass="jsr303error" />
			</div>
		</c:if>
	</div>
</spring:bind>