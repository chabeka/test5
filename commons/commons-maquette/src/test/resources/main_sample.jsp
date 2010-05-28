<%@ page import="fr.urssaf.image.commons.maquette.tool.MaquetteConstant"%>
<%@ page import="fr.urssaf.image.commons.maquette.template.MaquetteConfig"%>
<% MaquetteConfig myConfig = (MaquetteConfig) request.getSession().getAttribute( MaquetteConstant.SESSION_MAQUETTECONFIG ) ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="fr.urssaf.image.commons.maquette.tool.MaquetteConstant"%><html lang="fr" xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="auteur" content="Benjamin RICHARD" />
  <meta name="language" content="fr" />
  <meta name="description" content="maquette des applications web du pôle technocom, script d'exemple pour les tests unitaires" />
  
  <title>Archivage Electronique de Documents</title>

  <link href="css/form.css" rel="stylesheet" type="text/css" />
  <link href="css/form-font.css" rel="stylesheet" type="text/css" />
  <link href="css/menu.css" rel="stylesheet" type="text/css" />
  <link href="css/menu-font.css" rel="stylesheet" type="text/css" />
  <link href="css/main.css" rel="stylesheet" type="text/css" />
  <link href="css/main-font.css" rel="stylesheet" type="text/css" />
  
  <% if( myConfig.getOverloadColor() == Boolean.TRUE ) {%>
  <!-- Surcharge des couleurs et images par défaut -->
  <link href="css/menu-color.css" rel="stylesheet" type="text/css" />
  <link href="css/menu-font-color.css" rel="stylesheet" type="text/css" />
  <link href="css/main-color.css" rel="stylesheet" type="text/css" />
  <link href="css/main-font-color.css" rel="stylesheet" type="text/css" />
  <% } %>
  
  <% if( myConfig.getCssList().size() > 0 ) { %>
  <!-- Ajout de css supplementaire pour l'application et ou la page courante -->
  	<% for( String css : myConfig.getCssList() ) { %>
  <link href="css/<%= css %>" rel="stylesheet" type="text/css" />
  	<% } %> 
  <% } %>
  
  <!--[if IE 6]>
  <style type="text/css">
  #leftcol
  {
      /* figer une hauteur minimum pour IE6 */
      height: 500px; 
  }
  /* simuler un min-width et max-width pour IE6 */
  #container {
      width: 750px;
      width: expression( document.body.clientWidth < 750? "750px" : document.body.clientWidth > 1200? "1200" : "auto" );
  }
  </style>
  <![endif]-->
  
  <script>
  var maVariableQuiSertARien = null ;
  </script>
  
  <script>
  var uneAutreVariableQuiSertAPasGrandChose = null ;
  </script>

</head>

<body>

<!--  container : conteneur principal permettant d'organiser l'affichage de la page -->
<div id="container">

<!--  header : bandeau disposant des logos, du titre de l'application et du menu de navigation principale -->
<div id="header">
   <img id="logoimage" src="img/logo_image.png" alt="logo-image" title="logo-image" /> 
   <h1 id="title-app" title="Titre de l'application de mon attribut title">Titre de l'application</h1>
   <img id="logoappli" src="img/logo_aed.png" alt="logo-aed" title="logo-aed" /> 

<!--  menu : zone affichant le menu de navigation de type liste non ordonnee (ul/li) -->
   <div id="menu" title="Menu de navigation de type liste non ordonn&egrave;e">

   </div>
<!--  menu : fin -->
</div>
<!--  header : fin -->

<!--  leftcol : colonne de gauche pour disposer les informations contextuels propres à chaque application -->
<div id="leftcol">

<h3 id="app-title">Application</h3>
<p id="application" title="Informations relatives à l'application courante">
<span id="app-name" title="Nom de l'application">SAEL ORGANISME</span><br />
<span id="app-version" title="Version de l'application">91250</span></p>

<h3 id="user-title">Utilisateur</h3>
<p id="user" title="Informations relatives à l'utilisateur identifié">
<span id="user-name" title="Prénom Nom de l'utilisateur">Benjamin RICHARD</span><br />
<span id="user-rights" title="Droits affectés à l'utilisateur">Administration Fonctionnelle</span></p>

<h3 id="logout-title">Déconnexion</h3>
<p id="logout" title="Boite de déconnexion">
<input id="user-logout" type="button" value="Déconnexion" onclick="" tabindex="0" /></p>

</div>
<!-- leftcol : fin -->

<!-- content : zone propre à chaque page -->
<div id="content">

<!--  identification de la zone courante, non cliquable car seul le dernier élément correspond à une page -->
<div id="pagereminder" title="Rappel de la page courante en utilisant le cheminement du menu">Administration &gt; Paramétrage &gt; Fonctionnel</div>

<!--  content-application : zone générée par l'action courante -->
<div id="content-application">

<h3>Information</h3>
<p>Cette page a pour but de présenter la hauteur minimum de l'application sous Internet Explorer, Firefox ou tout autre navigateur. </p>

<!-- noscript : zone toujours présente sur chaque page de l'application -->
<noscript>
<h4>Information utilisateur : javascript</h4>
<p>Javascript n'est pas activé sur votre navigateur. Pour plus de confort dans l'utilisation de cette application, il 
est recommandé de l'activer via les préférences de votre navigateur. Toutefois, sans Javascript le site reste 
fonctionnel.
</p>
</noscript>

</div>
<!-- content-application : fin -->

</div>
<!-- content : fin -->

<!-- footer : pied de page -->
<div id="footer">
    <div id="providedby"><a href="providedby.html" title="Accès à la page Provided by affichant la liste des normes et standard respectés par
    cette application, ainsi que les principales technologies utilisées">Provided by</a></div>
    <div id="copyright">Copyright CIRTIL 2006-2010</div>
</div>
<!-- footer : fin -->

</div>
<!-- container : fin -->


<!--  pour ne pas empêcher le téléchargement parallèle des images/css/... il faut mettre le javascript en fin de page -->
<!-- script type="text/javascript" src="js/mootools-1.2.4-core-nc.js"></script  -->
<script type="text/javascript" src="js/mootools-1.2.4-core-fullButNoFx.js"/>mootools core framework</script>
<script type="text/javascript" src="js/mootools-1.2.4.4-more-fullNoDragNoFx.js">mootools more framework</script>
<script type="text/javascript" src="js/mootools-1.2.4.4-more-Asset.js">mootools more framework : asset functiunalities</script>

<script type="text/javascript" src="js/class/mootools-forge-Array.Math.js">Array mathematics functiunalities</script>
<script type="text/javascript" src="js/class/DebugToolBox.js">DebugToolBox class</script>
<script type="text/javascript" src="js/class/Menu.js">Menu class</script>

<script type="text/javascript" src="js/domreadyscript.js">domready script</script>

</body>
  
</html>