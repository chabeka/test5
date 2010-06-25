<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.util.*"%>
<%@page import="fr.urssaf.image.commons.controller.spring3.exemple.modele.*"%>

<html>
<head>
<title>Exemple d'un tableau</title>
<c:import url="/view/header.jsp" />
</head>
<body>
<form:form method="post" modelAttribute="formulaire">
<div id="table">
<table>
	<thead>
		<tr>
			<th><fmt:message key="document.titre" /></th>
			<th><fmt:message key="document.etat" /></th>
			<th><fmt:message key="document.openDate" /></th>
			<th><fmt:message key="document.closeDate" /></th>
			<th><fmt:message key="document.level" /></th>
			<th><fmt:message key="document.flag" /></th>
			<th><fmt:message key="document.etats" /></th>
			<th><fmt:message key="document.comment" /></th>
		</tr>
		<%
			List<Etat> etats = new ArrayList<Etat>();
			etats.addAll(Arrays.asList(Etat.values()));
		%>
		<c:set var="etatOptions" value= "<%= etats %>" />
	</thead>
	<c:forEach var="document" items="${formulaire.documents}"
		varStatus="status">

		<c:set var="titre" value="${formulaire.titres[document.id]}" />
		<c:set var="etat" value="${formulaire.etats[document.id]}" />
		<c:set var="etats" value="${formulaire.etatss[document.id]}" />
		<fmt:formatDate var="openDate" value="${formulaire.openDates[document.id]}" pattern="dd/MM/yyyy" />
		<fmt:formatDate var="closeDate" value="${formulaire.closeDates[document.id]}" pattern="yyyy-MM-dd" />
		<c:set var="level" value="${formulaire.levels[document.id]}" />
		<c:set var="flag" value="${formulaire.flags[document.id]}" />
		<c:set var="comment" value="${formulaire.comments[document.id]}" />
		
		<tr>
			<td>
				<form:input path='titres[${document.id}]'/> <form:errors path='titres[${document.id}]' />
			</td>

			<td>
				<form:select path='etats[${document.id}]'>
					<form:options items='${etatOptions}'/>
				</form:select>
			</td>

			<td>
				<form:input path='openDates[${document.id}]' /> <form:errors path='openDates[${document.id}]'/>
			</td>

			<td>
				<form:input path='closeDates[${document.id}]' /> <form:errors path='closeDates[${document.id}]'/>
			</td>

			<td>
				<form:radiobutton path='levels[${document.id}]' value='${level}'/>
				<form:radiobutton path='levels[${document.id}]' value='${level}'/>
				<form:radiobutton path='levels[${document.id}]' value='${level}'/>
			</td>

			<td align="center">
				<form:checkbox path='flags[${document.id}]' value='${flag}'/>
			</td>
			
			<td>
				<form:select path='etatss[${document.id}]' multiple='true' size='4' >
					<form:options items='${etatOptions}'/>
				</form:select>
			</td>
			
			<td>
				<form:textarea path='comments[${document.id}]'/>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<input type="submit" />
</form:form>

</body>
</html>