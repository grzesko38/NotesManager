<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form"   		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"      		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	   		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="navigation"	uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>
<%@ taglib prefix="utils" 		tagdir="/WEB-INF/tags/utils" %>

<c:set var="asc"><spring:theme code="img.sort.asc"/></c:set>
<c:set var="desc"><spring:theme code="img.sort.desc"/></c:set>
<c:url var="ascImgUrl" value="/themes/${asc}"/>
<c:url var="descImgUrl" value="/themes/${desc}"/>

<c:set var="ascActive"><spring:theme code="img.sort.asc.active"/></c:set>
<c:set var="descActive"><spring:theme code="img.sort.desc.active"/></c:set>
<c:url var="ascActiveImgUrl" value="/themes/${ascActive}"/>
<c:url var="descActiveImgUrl" value="/themes/${descActive}"/>

<c:set var="sortCol" value="${notesPaginationData.sortCol}"/>
<c:set var="isSortAsc" value="${notesPaginationData.sortAscending}"/>

<c:if test="${fn:length(notesPaginationData.page.content) gt 10}">
	<utils:pagination paginationData="${notesPaginationData}" linkCore="${linkCore}" />
</c:if>
<c:url value="/notesmanager/updateSelections.json" var="checkboxAjaxUrl" />
<form:form id="notesGridForm" method="post" cssClass="clearfix" action="${pageContext.request.contextPath}/notesmanager/show"
		   modelAttribute="selectedCheckboxesForm" data-checkboxajaxaction="${checkboxAjaxUrl}">
	<table class="data">
		<colgroup>
			<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
				<col style="width: 40px" span="1"/>
				<col style="width: 220px" span="1"/>
				<col span="1"/>
				<col style="width: 200px" span="2"/>
				<col style="width: 120px" span="1"/>
			</security:authorize>
			<security:authorize ifNotGranted="ROLE_ANONYMOUS">
				<col style="width: 40px" span="2"/>
				<col span="1"/>
				<col style="width: 200px" span="2"/>
				<col style="width: 220px" span="1"/>
			</security:authorize>
		</colgroup>
		<thead>
			<tr>
				<security:authorize ifNotGranted="ROLE_ANONYMOUS">
					<th class="corner">
						<div class="checkbox">
							<input id="selectAll" class="chceckbox" type="checkbox">
							<label for="selectAll"></label>
						</div>
					</th>
				</security:authorize>
				<th class="corner"/>
				<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
					<th>
						<navigation:sortHeader divClass="sort" sortColumn="author.nick" imgSize="16"
								ascImgUrl="${sortCol eq 'author.nick' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
								descImgUrl="${sortCol eq 'author.nick' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
							<span><spring:message code="notes.listing.label.author"/></span>
						</navigation:sortHeader>
					</th>
				</security:authorize>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="title" imgSize="16"
							ascImgUrl="${sortCol eq 'title' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'title' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="notes.listing.label.title"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="deadline" imgSize="16"
							ascImgUrl="${sortCol eq 'deadline' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'deadline' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="notes.listing.label.deadline"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="dateCreated" imgSize="16"
							ascImgUrl="${sortCol eq 'dateCreated' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'dateCreated' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="notes.listing.label.createdon"/></span>
					</navigation:sortHeader>
				</th>
				<th><spring:message code="notes.listing.label.actions"/></th>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${notesPaginationData.page.content}" var="note" varStatus="loopStatus">
				<tr>
					<security:authorize ifNotGranted="ROLE_ANONYMOUS">
						<td class="left">
							<div class="checkbox">
								<form:checkbox path="selections" value="${note.id}" />
								<label for="selections${loopStatus.index + 1}"><spring:message text=""/></label>
							</div>
						</td>
					</security:authorize>
					<td class="left"><spring:message text="${notesPaginationData.page.number * notesPaginationData.page.size + loopStatus.index + 1}."/></td>
					<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
						<td><c:out value="${note.author.nick}" /></td>
					</security:authorize>
					<td><c:out value="${note.title}" /></td>
					<td>
						<fmt:parseDate value="${note.deadline}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
						<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
					</td>
					<td>
						<fmt:parseDate value="${note.dateCreated}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
						<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/notesmanager/details/${note.id}">
							[<spring:message code="global.details"/>]
						</a>
						<security:authorize ifNotGranted="ROLE_ANONYMOUS">
							|
							<a href="${pageContext.request.contextPath}/notesmanager/edit/${note.id}">
								[<spring:message code="global.edit"/>]
							</a>
							|
							<a class="deleteNoteLink" href="${pageContext.request.contextPath}/notesmanager/delete/${note.id}">
								<span>[<spring:message code="global.delete"/>]</span>
							</a>
						</security:authorize>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<utils:pagination paginationData="${notesPaginationData}" linkCore="${linkCore}" />
	
	<div class="buttonsRow">
		<a href="<c:url value="/notesmanager/add" />">
			<span class="buttonPositive">
				<spring:message code="notes.listing.addNew" />	
			</span>
		</a>
		<security:authorize ifNotGranted="ROLE_ANONYMOUS">
			<form:button id="deleteSelectedNotes" class="buttonPositive" name="delete" value="selected">
				<spring:message code="notes.listing.delete.selected" />
			</form:button>
			<form:button id="deleteAllNotes" class="buttonPositive" name="delete" value="all">
				<spring:message code="notes.listing.delete.all" />
			</form:button>
		</security:authorize>
	</div>
</form:form>
