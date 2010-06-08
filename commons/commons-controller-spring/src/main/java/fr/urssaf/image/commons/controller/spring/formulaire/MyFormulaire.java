package fr.urssaf.image.commons.controller.spring.formulaire;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.ValidForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FieldException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateExceptionRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ResetForm;

public class MyFormulaire {

	private Map<String, MyFormulaire> formulaires;

	private MyExceptionFormulaire exceptionForm;

	private PopulateExceptionRuleForm exceptionRuleForm;

	@SuppressWarnings("unchecked")
	private PopulateForm populateForm;

	@SuppressWarnings("unchecked")
	private ResetForm resetForm;

	@SuppressWarnings("unchecked")
	private ValidForm validForm;

	@SuppressWarnings("unchecked")
	private ClassForm classForm;

	@SuppressWarnings("unchecked")
	public final void initClassForm(ClassForm classForm) {
		this.classForm = classForm;
		this.populateForm = new PopulateForm(classForm);
		this.resetForm = new ResetForm(classForm);
		this.validForm = new ValidForm(classForm);

		this.exceptionForm = new MyExceptionFormulaire();
		this.exceptionRuleForm = new PopulateExceptionRuleForm();

		this.formulaires = classForm.init(this);

	}

	public BeanFormulaire getBeanFormulaire(String field) {

		if (!UtilForm.containsPoint(field)) {

			return this.classForm.getMethode(field);
		} else {

			String form = UtilForm.getForm(field);
			return this.formulaires.get(form).getBeanFormulaire(
					UtilForm.getElement(field));
		}

	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getMethodeNames() {
		return this.classForm.getMethodeNames();
	}

	public MyExceptionFormulaire getException() {
		return this.exceptionForm;
	}

	public PopulateExceptionRuleForm getExceptionRuleForm() {
		return this.exceptionRuleForm;
	}

	public Set<String> getFormulaires() {
		return this.formulaires.keySet();
	}

	public MyFormulaire getFormulaire(String name) {
		return this.formulaires.get(name);
	}

	public List<RuleFormulaireException> getRuleExceptions(String rule) {

		if (!UtilForm.containsPoint(rule)) {

			return this.exceptionRuleForm.getExceptions(rule);
		} else {

			String form = UtilForm.getForm(rule);
			return this.formulaires.get(form).getRuleExceptions(
					UtilForm.getElement(rule));
		}
	}

	public FieldException getException(String field) {

		if (!UtilForm.containsPoint(field)) {

			return this.exceptionForm.getException(field);
		} else {

			String form = UtilForm.getForm(field);
			return this.formulaires.get(form).getException(
					UtilForm.getElement(field));
		}

	}

	@SuppressWarnings("unchecked")
	public void populate(Map map) {

		for (Object field : map.keySet()) {

			populate((String) field, (String[]) map.get((String) field));

		}
	}

	@SuppressWarnings("unchecked")
	public void populate(String field, String[] values) {

		if (!UtilForm.containsPoint(field) || (UtilForm.containsHook(field))) {
			populateForm.populate(field, values, this);
		} else {
			String form = UtilForm.getForm(field);
			this.formulaires.get(form).populate(UtilForm.getElement(field),
					values);
		}

	}

	public void reset(String[] liste) {

		for (String field : liste) {
			reset(field);
		}
	}

	@SuppressWarnings("unchecked")
	public void reset(String field) {

		if (!UtilForm.containsPoint(field) || (UtilForm.containsHook(field))) {
			resetForm.reset(field, this);
		} else {
			String form = UtilForm.getForm(field);
			this.formulaires.get(form).reset(UtilForm.getElement(field));
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isValid() {
		validForm.validFormulaire(this);
		boolean valid = this.exceptionForm.isEmpty()
				&& this.exceptionRuleForm.isEmpty();

		for (MyFormulaire form : formulaires.values()) {

			valid = valid & form.isValid();
		}

		return valid;
	}

	@SuppressWarnings("unchecked")
	public boolean isValid(String field) {

		if (!UtilForm.containsPoint(field)) {

			validForm.validField(this, field);
			return this.exceptionForm.isEmpty(field);
		} else {

			String form = UtilForm.getForm(field);
			return this.formulaires.get(form).isValid(
					UtilForm.getElement(field));

		}

	}

	@SuppressWarnings("unchecked")
	public boolean isValidRule(String rule) {

		if (!UtilForm.containsPoint(rule)) {

			return validForm.isValidRule(this, rule);
		} else {

			String form = UtilForm.getForm(rule);
			return this.formulaires.get(form).isValidRule(
					UtilForm.getElement(rule));

		}

	}

	@SuppressWarnings("unchecked")
	public Object validRule(String rule) {

		if (!UtilForm.containsPoint(rule)) {

			return validForm.validRule(this, rule);
		} else {

			String form = UtilForm.getForm(rule);
			return this.formulaires.get(form).validRule(
					UtilForm.getElement(rule));

		}

	}

}
