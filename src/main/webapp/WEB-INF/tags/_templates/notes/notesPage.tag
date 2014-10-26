<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="banner" fragment="true" %>
<%@ attribute name="topText" fragment="false" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>

<template:master>
	<div class="notes">
		<jsp:invoke fragment="banner" />
		<div class=top>
			<span>${topText}</span>
		</div>
		<jsp:doBody/>
		<div class="bottom"></div>
	</div>
</template:master>