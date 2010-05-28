<%@ tag language="java" pageEncoding="utf8"
	body-content="empty"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="value"
	required="true"
	rtexprvalue="true"
%>

<% request.setAttribute("oldLineChar", "\n");%>
<% request.setAttribute("newLineChar", "<br>");%>
<c:set var="value">
		<c:out value="${value}"/>
</c:set>
${fn:replace(value, oldLineChar, newLineChar)}
