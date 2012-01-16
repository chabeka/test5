<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<td style="width: 25%" align="right"><a href="listeTests.do">&lt;&lt;&nbsp;Retour
		à la liste des tests </a></td>
	</tr>
</table>
<p><br />
</p>


<c:forEach var="categorie" items="${listeTests.categorie}">
	
	<c:set var="lon" value="${fn:length(categorie.casTests.casTest)}" />
	
	<p class="titre2"><c:out value="${categorie.nom}" /></p>

	<table border=0 cellpadding=3>


		<tr>

			<c:forEach var="casTest" items="${categorie.casTests.casTest}"
				varStatus="status">
				
				<c:if test="${not status.first and status.index % 2 == 0}">
					</tr><tr>
				</c:if>
				
				<td><c:choose>

					<c:when test="${casTest.implemente==true}">
						<a class="lienCas"
							href="test<c:out value="${casTest.id}" />.do?id=<c:out value='${categorie.id}' />">
						<c:out value="${casTest.code}" /> </a>
					</c:when>

					<c:otherwise>
						<c:out value="${casTest.code}" />
					</c:otherwise>

				</c:choose></td>
			</c:forEach>
			<c:if test="${lon %2 == 1}">
				<td>&nbsp;</td>
			</c:if>
		</tr>
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
