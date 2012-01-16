<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SAE - Intégration - Liste des tests</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<table width="100%">
	<tr>
		<td style="width: 75%">
		<p class="titre1">SAE - Intégration - Liste des tests</p>
		</td>
		<td style="width: 25%" align="right"><a href="index.do">&lt;&lt;&nbsp;Retour
		à l&apos;accueil </a></td>
	</tr>
</table>
<p><br />
</p>

<c:forEach var="categorie" items="${listeTests.categorie}">

	<p class="titre2">
		<a href="listeTests.do?action=detail&id=${categorie.id}">
	<c:out value="${categorie.nom}" /></a></p>
	<p><br />
	</p>

</c:forEach>



<p><br />
<br />
<br />
<br />
</p>
</body>
</html>
