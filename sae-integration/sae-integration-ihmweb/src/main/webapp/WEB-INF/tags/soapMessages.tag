<%@ tag body-content="empty"%>
<%@ attribute name="objetFormulaire" required="true"
	type="fr.urssaf.image.sae.integration.ihmweb.formulaire.SoapFormulaire"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<p style="font-weight: bold; text-decoration: underline;">Echanges
Web-Services</p>


<table border="0" cellspacing="3" cellpadding="3" style="width: 100%;">
	<tr style="vertical-align: top;">
		<td style="width: 50%;" align="right">Message Envoyé : <br />
		 <textarea rows="15" cols="50" readonly="readonly" style="text-align:left;"><c:out value="${objetFormulaire.messageOut}" /></textarea>
		</td>
		<td
			style="width: 50%; border-left-width: 2px; border-left-color: black; border-left-style: solid;">
		Message reçu :<br />
		<textarea rows="15" cols="50" readonly="readonly"><c:out value="${objetFormulaire.messageIn}" /></textarea>
      </td>
	</tr>
</table>

<hr />