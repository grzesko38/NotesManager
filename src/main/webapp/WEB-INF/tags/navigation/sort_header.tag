<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="colLabel" required="true" type="java.lang.String" %>
<%@ attribute name="colName" required="true" type="java.lang.String" %>
<%@ attribute name="imgSize" required="true" type="java.lang.Integer" %>

<div class="sort">
	<span>${colLabel}</span>
	<a href="?col=${colName}&sort=asc">
		<img src="${pageContext.request.contextPath}/themes/<spring:theme code="img.sort.asc"/>"
             	width="${imgSize}" height="${imgSize}"/>
	</a>
	<a href="?col=${colName}&sort=desc">
		<img src="${pageContext.request.contextPath}/themes/<spring:theme code="img.sort.desc"/>"
             	width="${imgSize}" height="${imgSize}"/>
	</a>
</div>