<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/sae_integration.tld" prefix="sae"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SAE - Intégration - Référentiel des cas de tests</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<table width="100%">
	<tr>
		<td style="width: 75%">
		<p class="titre1">SAE - Intégration - Référentiel des cas de tests</p>
		</td>
		<td style="width: 25%" align="right"><a href="index.do">&lt;&lt;&nbsp;Retour
		à l&apos;accueil </a></td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<a href="listeTests.do">Liste des cas de test&nbsp;&gt;&gt; </a>
		</td>
	</tr>
</table>
<p><br />
</p>


<c:forEach var="categorie" items="${listeTests.categorie}">

	<table border="1" cellpadding="4" cellspacing="0" style="width: 100%;">

		<tr style="font-weight: bold;; background-color: lightgray;">
			<td style="width: 15%">Catégorie</td>
			<td style="width: 15%">Code</td>
			<td style="width: 60%">Description</td>
		</tr>


		<c:forEach var="casTest" items="${categorie.casTests.casTest}">

			<tr>
				<td>${categorie.nom}</td>
				<td>${casTest.code}</td>
				<td>${casTest.description}</td>
			</tr>

		</c:forEach>

	</table>

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
