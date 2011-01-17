<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<div>

<form action="j_spring_security_check" method="post">

	<div>
	<label id="login" class="field">
		<fmt:message key="login" />
	</label>:
	<input type="text" name='j_username' id='login' class="value" value="${SPRING_SECURITY_LAST_USERNAME}" />
		
	</div>

	<div>
	<label id="password" class="field">
		<fmt:message key='password' />
	</label>:
	
	<input type="password" name='j_password' id='password' class="value" />
	</div>


	<div class="center">

	<input type='submit' id='boutton_login' class="button"
		value='<fmt:message key="connection" />' />
		
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
		<p class="error">
			${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
	</c:if>

	
		
	</div>
	
	
</form>


<div>
Comptes utilisateurs:
<ul>
<li> admin : adminpassword</li>
<li> user  : userpassword</li>
</ul>

</div>

</div>


</body>
</html>
