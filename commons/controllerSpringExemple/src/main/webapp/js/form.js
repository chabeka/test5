function populateCheck(controller,checkBox, hidden, checked, nochecked) {

	if (checkBox.checked) {
		hidden.value = checked;
	} else {
		hidden.value = nochecked;
	}

	populateInput(controller, hidden);
}

function populateSelectMultiple(controller, select) {
	var values = "";
	for ( var i = 0; i < select.options.length; i++) {
		if (select.options[i].selected) {
			
			values = values + encodeURIComponent(select.name) + "="
					+ encodeURIComponent(select.options[i].value)+"&";
		}
	}

	return values;

}

function populateInput(controller, field) {

	if (field.type == 'select-multiple') {
		var valeur = populateSelectMultiple(controller, field);
	} else {

		var valeur = encodeURIComponent(field.name) + "="
				+ encodeURIComponent(field.value)+"&";
	}

	if(valeur == '&'){
		return;
	}
	
	var parametres = 'action=populateField&' + valeur + 'field=' + field.name;

	new Ajax.Request(controller, {
		parameters : parametres,
		evalScripts : true,
		onSuccess : function(transport) {
			var json = transport.responseText.evalJSON();
			for ( var field in json) {
				exception(json, field);
			}

		},
		onException : function(request, exception) {
			alert('updater exception' + request + '\n' + exception);

		},
		onFailure : function(transport) {
			alert("failure:"+transport.responseText);
		}

	});
}

function exception(json, field) {

	error(field, "");
	var tableau = json[field];
	if (tableau != null) {
		if (tableau.length == 1) {
			error(field, tableau[0]);
		} else if (tableau.length > 1) {

			text = '<ul style="top">';

			for ( var i = 0; i < tableau.length; i++) {
				text = text.concat('<li type="disc" >');
				text = text.concat(tableau[i]);
				text = text.concat('</li>');

			}
			text = text.concat('</ul>');

			error(field, text);

		}
	}
}

function error(field, text) {

	var errorId = $('error_'.concat(field));
	if (errorId != null) {
		errorId.innerHTML = text;
	}
}
