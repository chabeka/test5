<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table border="0" cellpadding="0" style="width: 100%;">
	<tr style="width: 100%;">
		<td style="width: 75%;">
		<p class="titreTest">SAE - Intégration / <c:out
			value="${casTest.categorie}" /> / <c:out value="${casTest.code}" /></p>
		</td>
		<td style="width: 25%; text-align: right;"><a
			href='listeTests.do?action=detail&id=<c:out value="${requestScope['id']}"/>'>Retour
		à la liste des tests</a><input type="hidden" name="id"
			value='<c:out value="${requestScope['id']}" />' /></td>
	</tr>
</table>

<hr />

<p class="descCasTest"><c:out value="${casTest.description}" /></p>

<hr />
