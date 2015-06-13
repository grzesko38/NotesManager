<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${pageContext.request.contextPath}/js/notes/datefilter.js"></script>

<spring:message code="notes.listing.dateFilter.tooltip.dateFormat" var="dateFormatTooltip"/>
<form:form action="${pageContext.request.contextPath}/notesmanager/show" method="get" modelAttribute="dateForm" class="dateForm">
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
						<form:label path="date"><spring:message code="notes.listing.dateFilter.label.from"/>:</form:label>
					</span>
					<span id="inputspan">
						<form:input path="date" title="${dateFormatTooltip}" placeholder="${dateFormatTooltip}" />
						<c:if test="${not empty notesPaginationData.fromDate}">
							<c:url value="/notesmanager/show" var="clrAction">
								<c:param name="clrDateFilter"/>
							</c:url>
							<em id="cancelDateFilter" data-clraction="${clrAction}"/>
						</c:if>
					</span>
				</div>
			</td>
			<td>
				<div id="dateFilterFormSubmitButton" class="submitBbutton">
		        	<span><spring:message code="notes.listing.dateFilter.button.go"/></span>
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