<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Portail d'authentification au SAE</title>

</head>
<body onload="setTimeout('redirect()',5000)">

<form action="${action}" method="post" id="connection" >
<div>
	<input type="hidden" name="SAMLResponse"
	id="SAMLResponse" value="${SAMLResponse}" /> 
	
	
	<input type="hidden"
	name="RelayState" id="RelayState" value="${RelayState}" />
</div>
<noscript>
<div style="padding-top: 0.5em;"><input type="submit"
	value='redirection vers "${action}"' /></div>
</noscript>


</form>

<script type="text/javascript">

	var connection = document.getElementById('connection');

	function redirect(){
     connection.submit();
   }	

   var o = document.createElement("div");
   o.innerHTML = "<span>Veuillez patienter quelques instants, vous allez &ecirc;tre redirig&eacute; vers '${action}'</span>";
   connection.appendChild(o);

</script>

</body>

</html>