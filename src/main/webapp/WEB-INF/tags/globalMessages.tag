<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Error messages --%>
<c:if test="${not empty errorMsgs}">
	<div class="globalMessages err">
		<img src="<c:url value="/themes/common/images/icons/err.png"/>" width="40" height="40"/>
		<ul>
			<c:forEach items="${errorMsgs}" var="msg">
				<li>
					<spring:message code="${msg.code}" arguments="${msg.params}"/>
				</li>
			</c:forEach>
		</ul>
	</div>	
</c:if>

<%-- Warning messages --%>
<c:if test="${not empty warningMsgs}">
	<div class="globalMessages warn">
		<img src="<c:url value="/themes/common/images/icons/warn.png"/>" width="40" height="40"/>
		<ul>
			<c:forEach items="${warningMsgs}" var="msg">
				<li>
					<spring:message code="${msg.code}" arguments="${msg.params}"/>
				</li>
			</c:forEach>
		</ul>
	</div>	
</c:if>

<%-- Info messages --%>
<c:if test="${not empty infoMsgs}">
	<div class="globalMessages info">
		<img src="<c:url value="/themes/common/images/icons/info.png"/>" width="40" height="40"/>
		<ul>
			<c:forEach items="${infoMsgs}" var="msg">
				<li>
					<spring:message code="${msg.code}" arguments="${msg.params}"/>
				</li>
			</c:forEach>
		</ul>
	</div>	
</c:if>