<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://fr.urssaf.image.commons/taglib/ctl" prefix="ctl"%>
<%@ taglib tagdir="/WEB-INF/tags/form" prefix="form" %>
<%@page import="java.util.*"%>
<%@page import="fr.urssaf.image.commons.controller.spring.exemple.modele.*"%>

<html>
<head>
<title>Exemple d'un formulaire</title>
<c:import url="/view/header.jsp" />
</head>
<body>
<form method="get" action="${controller}">

<div id="form">
<table>
	<tr>
		<td><fmt:message key="document.titre" /></td>
		<td><form:input name="titre" value="${formulaire.titre}" /></td>
		<td class="erreur"><ctl:exception field="titre" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.etat" /></td>
		<td>
			<%
				List<Etat> etats = new ArrayList<Etat>();
				etats.addAll(Arrays.asList(Etat.values()));
			%> 
			
			<form:select name="etat" options="<%= etats %>"
				value="${formulaire.etat}" />
		
		</td>
		<td class="erreur"><ctl:exception field="etat" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.openDate" /></td>
		<td><fmt:formatDate value="${formulaire.openDate}"
			pattern="dd/MM/yyyy" var="openDate" /> <form:input name="openDate"
			value="${openDate}" /></td>
		<td class="erreur"><ctl:exception field="openDate" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.closeDate" /></td>
		<td><fmt:formatDate value="${formulaire.closeDate}"
			pattern="yyyy-MM-dd" var="closeDate" /> <form:input name="closeDate"
			value="${closeDate}" /></td>
		<td class="erreur"><ctl:exception field="closeDate" />
		<ctl:ruleException rule="validDate" />
		
		</td>
	</tr>
	<tr>
		<td><fmt:message key="document.level" /></td>
		<td>
				
			<form:radio name="level" value="${formulaire.level}" checked="1" />
			<form:radio name="level" value="${formulaire.level}" checked="2" />
			<form:radio name="level" value="${formulaire.level}" checked="3" />
			
			
		</td>
		<td class="erreur"><ctl:exception field="level" /></td>
	</tr>

	<tr>
		<td><fmt:message key="document.flag" /></td>
		<td><form:checkbox name="flag" value="${formulaire.flag}" nochecked="false" checked="true"/></td>
		<td class="erreur"><ctl:exception field="flag" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.etats" /></td>
		<td>
			<form:select name="etats" options="<%= etats %>"
				value="${formulaire.etats}" multiple="true" size="4"/>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="document.comment" /></td>
		<td>
			<form:textarea name="interneFormulaire.comment" value='${formulaire.interneFormulaire.comment}'/>
		</td>
		<td class="erreur"><text:out value="${formulaire.interneFormulaire.comment}"/><ctl:exception field="interneFormulaire.comment" /></td>
	</tr>
</table>

<ctl:button class="button" action="list" title="button.return"/>
<ctl:button class="button" action="save" title="button.save"/>
</div>
</form>

</body>
</html>



