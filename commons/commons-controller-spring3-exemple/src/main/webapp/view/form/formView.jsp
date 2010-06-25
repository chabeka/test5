<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.util.*"%>
<%@page import="fr.urssaf.image.commons.controller.spring3.exemple.modele.*"%>

<html>
<head>
<title>Exemple d'un formulaire</title>
<c:import url="/view/header.jsp" />
</head>
<body>
<div id="form">
<form:form method="post" modelAttribute="formFormulaire">


<table>
	<tr>
		<td><fmt:message key="document.titre" /></td>
		<td><form:input path='titre' /></td>
		<td class="erreur"><form:errors path='titre' /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.etat" /></td>
		<td>
			<%
				List<Etat> etats = new ArrayList<Etat>();
				etats.addAll(Arrays.asList(Etat.values()));
			%> 
			<c:set var="etatOptions" value= "<%= etats %>" />
			<form:select path="etat">
				<form:options items="${etatOptions}"/>
			</form:select>
		
		</td>
		<td class="erreur"><form:errors path="etat" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.openDate" /></td>
		<td><form:input path="openDate"/></td>
		<td class="erreur"><form:errors path="openDate" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.closeDate" /></td>
		<td><form:input path="closeDate" /></td>
		<td class="erreur"><form:errors path="closeDate" />
		</td>
	</tr>
	<tr>
		<td><fmt:message key="document.level" /></td>
		<td>
				
			<form:radiobutton path="level" value="0"/>
			<form:radiobutton path="level" value="1"/>
			<form:radiobutton path="level" value="2"/>
			
			
		</td>
		<td class="erreur"><form:errors path="level" /></td>
	</tr>

	<tr>
		<td><fmt:message key="document.flag" /></td>
		<td><form:checkbox path="flag"/></td>
		<td class="erreur"><form:errors path="flag" /></td>
	</tr>
	<tr>
		<td><fmt:message key="document.etats" /></td>
		<td>
			<form:select path="etats" multiple="true" size="4">
				<form:options items="${etatOptions}"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="document.comment" /></td>
		<td>
			<form:textarea path="interneFormulaire.comment"/>
		</td>
		<td class="erreur"><form:errors path="interneFormulaire.comment" /></td>
	</tr>
</table>
<input type="submit" />
</form:form>
</div>
</body>
</html>



