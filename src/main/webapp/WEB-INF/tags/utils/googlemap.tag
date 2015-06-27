<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ attribute name="showPacInput" type="java.lang.Boolean" required="true"%>

<c:if test="${showPacInput}">
	<input id="pac-input" class="controls" type="text">
</c:if>
<div id="map-canvas" class="centered"></div>