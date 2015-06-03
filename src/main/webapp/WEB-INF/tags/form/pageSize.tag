<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ attribute name="action" required="false" type="java.lang.String" %>
<%@ attribute name="sizes" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedSize" required="true" type="java.lang.Integer" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" %>

<select ${not empty cssClass?'class=':''}${not empty cssClass?cssClass:''} data-action="${action}">
	<c:forEach items="${sizes}" var="size">
		<option value="${size}"${size eq selectedSize?'selected="selected"':''}>${size}</option>
	</c:forEach>
</select>