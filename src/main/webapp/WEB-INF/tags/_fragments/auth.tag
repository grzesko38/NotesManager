<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="navigation" uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<security:authorize ifNotGranted="ROLE_ANONYMOUS">
	<div id="welcome">
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form id="topBarLogoutForm" action="${logoutUrl}" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
		<spring:message code="global.welcome"/>&#44;&nbsp;${userName}&nbsp;
		&#40;
		<span id="logoutAction"><spring:message code="global.logout"/></span>
		&#41;	
	</div>
</security:authorize>
	
<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
	<c:url value="/j_spring_security_check" var="loginUrl"/>
	<div id="topBarLogin">
		<div class="fieldsRow">
			<form id="topBarLoginForm" action="${loginUrl}" method="POST">
				<input type="text" name="nick"/>
				<input type="password" name="password"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
		<div id="loginAction">
			<div>
				<spring:message code="global.login" />
			</div>
			<img src="<c:url value="/themes/common/images/icons/arrow-right.png" />" height="26" width="26"/>
		</div>
	</div>
</security:authorize>