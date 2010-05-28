<%@ tag language="java" pageEncoding="utf8" dynamic-attributes="attributes"%>
<%@ attribute name="field"
	required="true"
	rtexprvalue="true"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://fr.urssaf.image.commons/taglib/ctl" prefix="ctl"%>

<td
<c:forEach var="attribute" items="${attributes}">${attribute.key} = "${attribute.value}"</c:forEach>
>
<jsp:doBody/>
<h6 class="erreur"><ctl:exception field="${field}" /></h6>
</td>
