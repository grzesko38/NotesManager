<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="banner" fragment="true" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/_templates" %>

<template:master>
	<div class="notes">
		<jsp:invoke fragment="banner" />
		<jsp:doBody/>
	</div>
</template:master>