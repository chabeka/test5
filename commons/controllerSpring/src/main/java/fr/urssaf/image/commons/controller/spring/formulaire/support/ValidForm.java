package fr.urssaf.image.commons.controller.spring.formulaire.support;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ValidFieldForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ValidRuleForm;

public final class ValidForm<F extends MyFormulaire> {

	private ValidRuleForm<F> validRuleForm;
	private ValidFieldForm<F> validFieldForm;

	public ValidForm(ClassForm<F> classe) {
		this.validRuleForm = new ValidRuleForm<F>(classe);
		this.validFieldForm = new ValidFieldForm<F>(classe);
	}

	public void validFormulaire(F formulaire) {
		this.validRuleForm.valid(formulaire);
		this.validFieldForm.valid(formulaire);
	}

	public boolean validRule(F formulaire) {

		return this.validRuleForm.valid(formulaire);

	}

	public boolean isValidRule(F formulaire, String ruleName) {

		return this.validRuleForm.isValid(formulaire, ruleName);

	}
	
	public Object validRule(F formulaire, String ruleName) {

		return this.validRuleForm.valid(formulaire, ruleName);

	}

	public void validField(F formulaire) {

		this.validFieldForm.valid(formulaire);
	}

	public void validField(F formulaire, String field) {

		this.validFieldForm.valid(formulaire, field);
	}

}
