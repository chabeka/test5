<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Accès refusé</title>
<c:import url="/view/header.jsp" />
</head>
<body>

<h6 class="erreur">&nbsp;l'accès de la page est refusé&nbsp;</h6>

<button type="button" onclick="javascript:location.href='view/index.jsp';">index</button>

<button type="button" onclick="javascript:location.href='j_spring_security_logout';">deconnexion</button>

</body>

</html>