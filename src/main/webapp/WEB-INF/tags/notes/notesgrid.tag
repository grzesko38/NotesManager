<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"      		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigation"	uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>

<script src="${pageContext.request.contextPath}/js/notes/notesgrid.js"></script>

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

<form:form method="post" action="${pageContext.request.contextPath}/notesmanager/show" modelAttribute="selectedCheckboxesForm">
	<table class="data">
		<colgroup>
			<col class="narrowCheckbox" span="1"/>
			<col class="narrow" span="1"/>
		</colgroup>
		<thead>
			<tr>
				<th class="corner">
					<div class="checkbox">
						<input id="selectAll" class="chceckbox" type="checkbox">
						<label for="selectAll"></label>
					</div>
				</th>
				<th class="corner"/>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="author" imgSize="16"
							ascImgUrl="${sortCol eq 'author' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'author' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="label.author"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="email" imgSize="16"
							ascImgUrl="${sortCol eq 'email' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'email' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="label.email"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="dateCreated" imgSize="16"
							ascImgUrl="${sortCol eq 'dateCreated' && isSortAsc ? ascActiveImgUrl : ascImgUrl}"
							descImgUrl="${sortCol eq 'dateCreated' && !isSortAsc ? descActiveImgUrl : descImgUrl}" >
						<span><spring:message code="label.createdon"/></span>
					</navigation:sortHeader>
				</th>
				<th><spring:message code="label.actions"/></th>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${notesPaginationData.page.content}" var="note" varStatus="loopStatus">
				<tr>
					<td class="left">
						<div class="checkbox">
							<form:checkbox path="selections" value="${note.id}" />
							<label for="selections${loopStatus.index + 1}"><spring:message text=""/></label>
						</div>
					</td>
<!-- 							onClick="document.forms['selectedNotes'].submit();" -->
					<td class="left"><spring:message text="${notesPaginationData.page.number * notesPaginationData.page.size + loopStatus.index + 1}."/></td>
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
</form:form>