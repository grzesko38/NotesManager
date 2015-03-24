<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="utils" uri="http://arczynskiadam.pl/jsp/tlds/utils" %>
<%@ taglib prefix="navigation" uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifNotGranted="ROLE_ANONYMOUS">
	<div id="welcome">
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	 
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		<spring:message code="global.welcome"/>&#44;&nbsp;${userName}&nbsp;
		&#40;
		<a href="javascript:formSubmit()"><spring:message code="global.logout"/></a>
		&#41;	
	</div>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
	<c:url value="/j_spring_security_check" var="loginUrl"/>
	<form action="${loginUrl}" method="POST">
		<input type="text" name="nick"/>
		<input type="password" name="password"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<button>ok</button>
	</form>
</security:authorize>

<div class="dateHolder">
	<utils:date separator="/" />
</div>