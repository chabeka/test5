<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">


<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@page import="com.opensymphony.module.sitemesh.Page"%>
<%@page import="com.opensymphony.module.sitemesh.RequestConstants"%>
<%
   Page thepage = (Page) request.getAttribute(RequestConstants.PAGE);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<page:applyDecorator name="main" title="<%= thepage.getTitle() %>">
	<body>
	<div id="content">

	<div id="menu">
	<ul>
		<li><a href="page1.do">page 1</a></li>
		<li><a href="page2.do">page 2</a></li>
		<li><a href="page3.do">page 3</a></li>
	</ul>

	<button type="button"
		onclick="javascript: location.href = 'logout';">
		deconnexion
	</button>

	</div>

	<decorator:body />
	</div>
	</body>
</page:applyDecorator>



