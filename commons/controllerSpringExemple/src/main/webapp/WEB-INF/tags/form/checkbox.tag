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
<%@ attribute name="checked"
	required="true"
	rtexprvalue="true"
%>
<%@ attribute name="nochecked"
	required="true"
	rtexprvalue="true"
%>
<ctl:checkbox name="${name}" value="${value}" checked="${checked}" nochecked="${nochecked}"
				/>

<script type="text/javascript">
  
	Event.observe('_${name}', "click", onChange, false);

	function onChange(event) {
		populateCheck('${controller}',$('_${name}'),$('${name}'),'${checked}','${nochecked}');
	}

</script>
 


