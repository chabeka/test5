package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import java.util.List;

import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;

public class RuleFormulaireException{
	
	private FormulaireException exception;

	public RuleFormulaireException(List<Object> values,
			Rule rule) {
		exception = new FormulaireException(values, rule.exception());
	}
	
	public FormulaireException getException(){
		return this.exception;
	}


	

}
