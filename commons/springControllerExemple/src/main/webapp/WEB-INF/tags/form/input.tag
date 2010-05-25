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
	required="false"
	rtexprvalue="true"
%>

<ctl:input  name="${name}" value="${value}" readonly="false"/>


<script type="text/javascript">
  
	Event.observe('${name}', "change", onChange, false);

	function onChange(event) {
		populateInput('${controller}', $('${name}'));
	}

</script>


