<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<sec:authorize access="isAuthenticated()">
	<div id="identity">
		<sec:authentication property="principal.authorities" var="authorities"/>
		<span>id:<sec:authentication property="principal.username"/></span>
		<ul>
		<c:forEach 	var="authority" items="${authorities}">
			<li>${authority.authority}</li>
		</c:forEach>
		</ul>
	</div>
	</sec:authorize>
	
	<ul>
		
		<sec:authorize access="isAuthenticated()">
			<li><a href="page1.do">page 1</a></li>
		</sec:authorize>
		
		<sec:authorize url="/page2.do">
			<li><a href="page2.do">page 2</a></li>
		</sec:authorize>
		
		<sec:authorize url="/page3.do">
			<li><a href="page3.do">page 3</a></li>
		</sec:authorize>
	</ul>

	<sec:authorize access="isAuthenticated()">
		<button type="button"
				onclick="javascript: location.href = 'logout';">deconnexion</button>
	</sec:authorize>
	
	<sec:authorize access="!isAuthenticated()">
		<button type="button" onclick="javascript: location.href = 'login_basic.do';">
			<span>connexion</span>
		</button>
	</sec:authorize>

	

	</div>

	<decorator:body />
	</div>
	</body>
</page:applyDecorator>



