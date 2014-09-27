<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<form:form method="post" action="${pageContext.request.contextPath}/notesmanager/show" modelAttribute="notesPaginationData">
	<table class="data">
		<colgroup>
			<col class="narrowCheckbox" span="1"/>
			<col class="narrow" span="1"/>
		</colgroup>
		<thead>
			<tr>
				<th class="corner"/>
				<th class="corner"/>
				<th><spring:message code="label.author"/></th>
				<th><spring:message code="label.email"/></th>
				<th><spring:message code="label.createdon"/></th>
				<th><spring:message code="label.actions"/></th>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${notesPaginationData.pagedListHolder.pageList}" var="note" varStatus="loopStatus">
				<tr>
					<td class="left">
						<div class="squaredThree">
							<form:checkbox path="selectedNotesIds" value="${note.id}" />
							<label for="selectedNotesIds${loopStatus.index + 1}"><spring:message text=""/></label>
						</div>
					</td>
<!-- 							onClick="document.forms['selectedNotes'].submit();" -->
					<td class="left"><spring:message text="${notesPaginationData.pagedListHolder.page * notesPaginationData.pagedListHolder.pageSize + loopStatus.index + 1}."/></td>
					<td>${note.author}</td>
					<td>${note.email}</td>
					<td>${note.dateCreated}</td>
					<td>
						<a href="${pageContext.request.contextPath}/notesmanager/details/${note.id}">[details]</a> |
						<a href="${pageContext.request.contextPath}/notesmanager/edit/${note.id}">[edit]</a> |
						<span>
							<a href="${pageContext.request.contextPath}/notesmanager/delete/${note.id}">[delete]</a>
						</span>
<%-- 								<div onClick="document.forms['selectedNotes'].submit();">xxx ${note.id}</div>	 --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 	<input type="submit" value="del" name="delete" title="aaaa"/> -->
	<button type="submit" value="del" name="delete" title="aaaa">delete</button>
	<div><form:errors path="selectedNotesIds" cssClass="jsr303ErrorBlock" /></div>
</form:form>