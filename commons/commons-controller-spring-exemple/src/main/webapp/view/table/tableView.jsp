<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://fr.urssaf.image.commons/taglib/ctl" prefix="ctl"%>
<%@ taglib tagdir="/WEB-INF/tags/form" prefix="form" %>
<%@ taglib prefix="table" tagdir="/WEB-INF/tags/table"%>
<%@page import="java.util.*"%>
<%@page import="fr.urssaf.image.commons.controller.spring.exemple.modele.*"%>

<html>
<head>
<title>Exemple d'un tableau</title>
<c:import url="/view/header.jsp" />
</head>
<body>
<form method="get" action="${controller}">
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

		<%
			List<Etat> etats = new ArrayList<Etat>();
			etats.addAll(Arrays.asList(Etat.values()));
		%>
		<c:set var="etatOptions" value= "<%= etats %>" />

		<tr>
			<table:td field="titres[${document.id}]">
				<form:input name="titres[${document.id}]" value="${titre}" />
			</table:td>

			<table:td field="etats[${document.id}]">
				<form:select name="etats[${document.id}]" options="${etatOptions}" value="${etat}" />
			</table:td>

			<table:td field="openDates[${document.id}]">
				<form:input name="openDates[${document.id}]" value="${openDate}" />
			</table:td>

			<table:td field="closeDates[${document.id}]">
				<form:input name="closeDates[${document.id}]" value="${closeDate}" />
				<h6 class="erreur"><ctl:ruleException rule="validDate[${document.id}]" /></h6>
			</table:td>

			<table:td field="levels[${document.id}]">
				<form:radio name="levels[${document.id}]" value="${level}" checked="1" />
				<form:radio name="levels[${document.id}]" value="${level}" checked="2" />
				<form:radio name="levels[${document.id}]" value="${level}" checked="3" />
			</table:td>

			<table:td field="flags[${document.id}]" align="center">
				<form:checkbox name="flags[${document.id}]" value="${flag}" nochecked="false" checked="true"/>
			</table:td>
			
			<table:td field="etatss[${document.id}]">
				<form:select name="etatss[${document.id}]" options="${etatOptions}" value="${etats}" multiple="true" size="4" />
			</table:td>
			
			<table:td field="comments[${document.id}]">
				<form:textarea name="comments[${document.id}]" value="${comment}"/>
			</table:td>
		</tr>
	</c:forEach>
</table>
</div>

<ctl:button class="button" action="add" title="button.document.add"/>
<ctl:button class="button" action="update" title="button.save"/>
</form>

</body>
</html>