<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<h1>connection réussie!</h1>
<font color="#FFFFFF">Anais::LoginOK</font>
<br>
<p><u>SAMLResponse</u> :<c:out value="${SAMLResponse}" /></p>
<p><u>RelayState</u> : <c:out value="${RelayState}" /></p>
</html>