<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="center"><form:form method="post"
	modelAttribute="connectionForm" id="form_cirti" name='form_cirti'>

	<div><form:label path="userLogin" cssClass="field">
		<fmt:message key="user.login" />
	</form:label>:<form:input path='userLogin' id='login' cssClass="value" /><form:errors
		path='userLogin' cssClass="error" /></div>

	<div><form:label path="userPassword" cssClass="field">
		<fmt:message key='user.password' />
	</form:label>:<form:password path='userPassword' id='password' cssClass="value" /><form:errors
		path='userPassword' cssClass="error" /></div>


	<div style="padding-top: 1em;" >
	
		<label class="field">&nbsp;</label>
		<input type='submit' id='boutton_login' class="button"
		value='<fmt:message key="button.connection" />' />
	
	</div>


</form:form> <p class="error"><c:out value="${failure}" /></p></div>

<script type="text/javascript">

	var form_cirti = document.getElementById('form_cirti');

	if(form_cirti.login.value == ''){
	   form_cirti.login.focus();
	}

	else if(form_cirti.password.value == ''){
	  form_cirti.password.focus();
	}

</script>

