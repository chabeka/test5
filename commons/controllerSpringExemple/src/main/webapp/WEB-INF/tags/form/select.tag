<%@ tag language="java" pageEncoding="utf8"
	body-content="empty"
	dynamic-attributes="attributes"
	
%>
<%@ taglib uri="http://fr.urssaf.image.commons/taglib/ctl" prefix="ctl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="name"
	required="true"
	rtexprvalue="true"
%>
<%@ attribute name="options"
	required="true"
	rtexprvalue="true"
	type="java.util.Collection"
%>

<%@ attribute name="value"
	required="true"
	rtexprvalue="true"
	type="java.lang.Object"
%>

<%@ attribute name="multiple"
	required="false"
	rtexprvalue="true"
%>

<%@ attribute name="size"
	required="false"
	rtexprvalue="true"
%>

<c:if test="${empty size}">
<c:set var="size" value= "1" />
</c:if>

<c:if test="${empty multiple}">
<c:set var="multiple" value= "false" />
</c:if>
<c:choose>
<c:when test="${size > 1}">
<ctl:select name="${name}" value="${value}" options="${options}"  
	    multiple="${multiple}" 
	    size="${size}"
	  

/>
  </c:when>
  <c:otherwise>
  <ctl:select name="${name}" value="${value}" options="${options}" 
	/>
  </c:otherwise>
  </c:choose>
  
 <script type="text/javascript">
  
	Event.observe('${name}', "change", onChange, false);

	function onChange(event) {
		populateInput('${controller}', $('${name}'));
	}

</script> 
	     