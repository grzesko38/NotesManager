<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="bannerClass" %>

<div class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<jsp:doBody />
	<div class="imgHolder"></div>
</div>

