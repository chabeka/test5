<%@ tag language="java" pageEncoding="utf8"
	body-content="empty"
	dynamic-attributes="attributes"
	
%>
<%@ taglib uri="http://fr.urssaf.image.commons/taglib/ctl" prefix="ctl"%>
<%@ attribute name="name"
	required="true"
	rtexprvalue="true"
%>
<%@ attribute name="value"
	required="true"
	rtexprvalue="true"
%>
<%@ attribute name="checked"
	required="true"
	rtexprvalue="true"
%>

<ctl:radio name="${name}" value="${value}" checked="${checked}"
onClick="populateInput('${controller}', this);"/>

