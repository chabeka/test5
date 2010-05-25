<%
HttpSession hs = request.getSession() ;
hs.setAttribute("PageInjectionLeftColAutre", "1") ;
%>
<h1>Titre de ma JSP : injectionLeftColAutre.jsp</h1>
<div>
Cette page ne contient pas de balise &lt;html&gt; &lt;head&gt; ou &lt;body&gt;<br /> 
Par contre vous devriez avoir une boîte contextuelle spécifique sur la gauche. Son titre est "Autres".
</div>