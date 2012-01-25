<%@ tag body-content="empty"%>
<%@ attribute name="objetFormulaire" required="true"
	type="java.lang.String"%>
<%@ attribute name="pathFormulaire" required="true"
	type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<p style="font-weight: bold; text-decoration: underline;">Lien de
monitoring</p>


<table border=0 cellspacing=3 cellpadding=3 style="width: 100%;">
	<tr style="vertical-align: top;">
		<td><c:choose>
			<c:when test="${objetFormulaire == null}">
         		Traitement non démarré
         	</c:when>
			<c:otherwise>
				<a href="${objetFormulaire}">Lien monitoring</a>
				<input type="hidden" value="${objetFormulaire}"
					name="${pathFormulaire}" />
			</c:otherwise>
		</c:choose></td>
	</tr>
</table>
<hr />