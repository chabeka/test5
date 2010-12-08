<head>
<title>Portail d'authentification au SAE</title>

</head>
<body onload="document.connection.submit();">

<form action="${action}" method="post" id="connection "
	name="connection"><input type="hidden" name="SAMLResponse"
	id="SAMLResponse" value="${SAMLResponse}" /> <input type="hidden"
	name="RelayState" id="RelayState" value="${RelayState}" />


<noscript>
<div style="padding-top: 0.5em;"><input type="submit"
	value='redirection vers "${action}"' /></div>
</noscript>


</form>

<script>
   var o = document.createElement("div");
   o.innerHTML = '<span>vous allez être redirigé vers "${action}"</span>';
   document.connection.appendChild(o);
</script>

</body>