<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="backgroundImgClass" %>

<section class="banner ${backgroundImgClass}">
	<div class="aboveBanner"></div>
	<jsp:doBody />
	<div class="imgHolder"></div>
</section>