<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<title>Connection</title>
</head>
<body>
<form:form method="post" modelAttribute="connectionForm" name="form_cirti">

	<table>
		<tr>
			<td><fmt:message key="user.login" /></td>
			<td><form:input path='userLogin' id="login"/></td>
			<td><form:errors path='userLogin' /></td>
		</tr>
		<tr>
			<td><fmt:message key="user.password" /></td>
			<td><form:password path='userPassword' id="password"/></td>
			<td><form:errors path='userPassword' /></td>
		</tr>
	</table>
	<input type="submit" id="boutton_login"/>
</form:form>
<c:out value="${failure}" />
</body>
</html>