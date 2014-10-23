<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="tooltip.dateFormat" var="dateFormatTooltip" text="properties err"/>
<form:form method="get" modelAttribute="dateForm" class="dateForm">
	<table class="dateFilter">
		<colgroup>
			<col class="narrow" span="2"/>
		</colgroup>
		<tr>
			<td/>
			<td/>
			<td>
				<div id="inputholder">
					<span id="labelspan">
						<form:label path="date"><spring:message code="label.dateFrom"/></form:label>
					</span>
					<span id="inputspan">
						<c:if test="${not empty notesPaginationData.fromDate}">
							<c:set var="dateFormatTooltip" value="${notesPaginationData.fromDate}"/>
						</c:if>
						<form:input path="date" title="${dateFormatTooltip}" placeholder="${dateFormatTooltip}" />
					</span>
				</div>
			</td>
			<td>
<%-- 						<input type="submit" value="<spring:message code="button.ok"/>" /> --%>
				<div class="submitBbutton" class="divbutton" onClick="document.forms['dateForm'].submit();">
		        	<span><spring:message code="button.go"/></span>
		            <img id="goimg" src="${pageContext.request.contextPath}/themes/<spring:theme code="img.nav.arrow.right"/>"
		            	 width="25" height="25"/>
		        </div>
			</td>
			<td/>
			<td/>
		</tr>
		<tr>
			<td />
			<td />
			<td><form:errors path="date" cssClass="jsr303ErrorBlock" /></td>
			<td />
		</tr>
	</table>	
</form:form>