<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="pagedListHolder" required="true" type="org.springframework.beans.support.PagedListHolder" %>
<%@ attribute name="linkCore" required="true" type="java.lang.String" %>

<div class="paginationRow">
	<div class="margin"></div>
	<div class="center">
		<div class="paginationHolder">
			<c:if test="${!pagedListHolder.firstPage}">
				<c:url value="${linkCore}" var="pagedLink">
					<c:param name="p" value="${pagedListHolder.page - 1}"/>
				</c:url>
				<a href="${pagedLink}">
					<span class="pagingItem">&lt;</span>
				</a>
			</c:if>
			<c:if test="${pagedListHolder.firstLinkedPage > 0}">
				<c:url value="${linkCore}" var="pagedLink">
					<c:param name="p" value="0"/>
				</c:url>
				<a href="${pagedLink}">
					<span class="pagingItem">1</span>
				</a>
			</c:if>
			<c:if test="${pagedListHolder.firstLinkedPage > 1}">
				<span class="pagingDots">...</span>
			</c:if>
			<c:forEach begin="${pagedListHolder.firstLinkedPage}" end="${pagedListHolder.lastLinkedPage}" var="i">
				<c:choose>
					<c:when test="${pagedListHolder.page == i}">
						<span class="pagingItem active">${i + 1}</span>
					</c:when>
					<c:otherwise>
						<c:url value="${linkCore}" var="pagedLink">
							<c:param name="p" value="${i}"/>
						</c:url>
						<a href="${pagedLink}">
							<span class="pagingItem">${i + 1}</span>
						</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 2}">
				<span class="pagingDots">...</span>
			</c:if>
			<c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 1}">
				<c:url value="${linkCore}" var="pagedLink">
					<c:param name="p" value="${pagedListHolder.pageCount - 1}"/>
				</c:url>
				<a href="${pagedLink}">
					<span class="pagingItem">${pagedListHolder.pageCount}</span>
				</a>
			</c:if>
			<c:if test="${!pagedListHolder.lastPage}">
				<c:url value="${linkCore}" var="pagedLink">
					<c:param name="p" value="${pagedListHolder.page + 1}"/>
				</c:url>
				<a href="${pagedLink}">
					<span class="pagingItem">&gt;</span>
				</a>
			</c:if>
			<span style="clear:both;"></span>
		</div>
	</div>
	<div class="margin">
		<form:form id="entriesPerPageForm" commandName="entriesPerPageForm" method="GET">
				<form:select  id="entriesPerPage" path="size" items="${entriesPerPageForm.pageSizes}" itemLabel="name" itemValue="value" multiple="false"/>
		</form:form>
	</div>
</div>