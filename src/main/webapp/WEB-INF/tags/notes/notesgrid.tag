<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"      	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigation"	uri="http://arczynskiadam.pl/jsp/tlds/navigation" %>

<script src="${pageContext.request.contextPath}/js/notes/notesgrid.js"></script>

<c:set var="asc"><spring:theme code="img.sort.asc"/></c:set>
<c:set var="desc"><spring:theme code="img.sort.desc"/></c:set>
<c:url var="ascImgUrl" value="/themes/${asc}"/>
<c:url var="descImgUrl" value="/themes/${desc}"/>

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
					<navigation:sortHeader divClass="sort" sortColumn="author" ascImgUrl="${ascImgUrl}" descImgUrl="${descImgUrl}" imgSize="16">
						<span><spring:message code="label.author"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="email" ascImgUrl="${ascImgUrl}" descImgUrl="${descImgUrl}" imgSize="16">
						<span><spring:message code="label.email"/></span>
					</navigation:sortHeader>
				</th>
				<th>
					<navigation:sortHeader divClass="sort" sortColumn="dateCreated" ascImgUrl="${ascImgUrl}" descImgUrl="${descImgUrl}" imgSize="16">
						<span><spring:message code="label.createdon"/></span>
					</navigation:sortHeader>
				</th>
				<th><spring:message code="label.actions"/></th>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${notesPaginationData.pagedListHolder.pageList}" var="note" varStatus="loopStatus">
				<tr>
					<td class="left">
						<div class="checkbox">
							<form:checkbox path="selections" value="${note.id}" />
							<label for="selections${loopStatus.index + 1}"><spring:message text=""/></label>
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
	<div><form:errors path="selections" cssClass="jsr303ErrorBlock" /></div>
</form:form>