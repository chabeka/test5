<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="fr.urssaf.image.commons.util.base64.*"%>
<%@page import="org.apache.commons.lang.*"%>
<head>
<title>Test de redirection </title>
</head>

<div style="word-wrap: break-word"><span><b><u>Jeton d'authentification: </u></b><%= request.getParameter("SAMLResponse")%></span></div>
<br>
<div><span><b><u>Service demandé :</u></b>&nbsp;&nbsp;<%=request.getParameter("RelayState")%></span>
</div>
<p><%= StringEscapeUtils.escapeJava(StringEscapeUtils.escapeHtml(Base64Decode.decode(request.getParameter("SAMLResponse"))))%></p>


