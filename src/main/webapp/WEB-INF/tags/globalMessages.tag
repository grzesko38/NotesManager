<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Warning messages --%>
<c:if test="${not empty warnMsgs}">
	<div class="globalMessages warning">
		<img src="<c:url value="/themes/common/images/marks/warn.png"/>" width="40" height="40"/>
		<ul>
			<c:forEach items="${warnMsgs}" var="msg">
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
		<img src="<c:url value="/themes/common/images/marks/info.png"/>" width="40" height="40"/>
		<ul>
			<c:forEach items="${infoMsgs}" var="msg">
				<li>
					<spring:message code="${msg.code}" arguments="${msg.params}"/>
				</li>
			</c:forEach>
		</ul>
	</div>	
</c:if>