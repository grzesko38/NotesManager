<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="banner">
	<div class="aboveBanner">
		<jsp:doBody />
	</div>
	<img src="<c:url value="/themes/common/images/banners/quillpen.png" />" width="200" height="200" />
	<div class="imgHolder"></div>
</section>