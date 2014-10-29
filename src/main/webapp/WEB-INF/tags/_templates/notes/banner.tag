<%@ tag description="Master Page template" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ attribute name="bannerClass" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="banner ${bannerClass}">
	<div class="aboveBanner"></div>
	<jsp:doBody />
	<div class="imgHolder"></div>
</section>

