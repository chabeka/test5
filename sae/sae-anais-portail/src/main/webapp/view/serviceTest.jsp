
<head>
<title>Test de redirection </title>
</head>

<div style="word-wrap: break-word"><span><b><u>Jeton d'authentification: </u></b><%= request.getParameter("SAMLResponse")%></span></div>
<br>
<div><span><b><u>Service demandé :</u></b>&nbsp;&nbsp;<%=request.getParameter("RelayState")%></span>
</div>
