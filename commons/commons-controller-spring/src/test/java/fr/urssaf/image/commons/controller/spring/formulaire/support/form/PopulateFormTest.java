package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Formulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.AnnotationFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.AbstractRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotNull;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;

public class PopulateFormTest extends
		PopulateContext<PopulateFormTest.TestFormulaire> {

	private static final String DATE_FUTURE = "12/12/2024";

	private static final String FIELD = "date";

	private static final String EXCEPTION = "date.before";

	private static final String RULE = "getValidDate";

	public PopulateFormTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void validDate() {

		String FORM = "";
		assertValidDate(FORM, formulaire);
		assertDate(formulaire.getDate(), DATE_SUCCESS_1);
	}

	@Test
	public void validFormulaire1Date() {

		String FORM = "testFormulaire1.";
		assertValidDate(FORM, formulaire.getTestFormulaire1());
		assertDate(formulaire.getTestFormulaire1().getDate(), DATE_SUCCESS_1);
	}

	@Test
	public void validFormulaire2Date() {

		String FORM = "testFormulaire2.";
		assertValidDate(FORM, formulaire.getTestFormulaire2());
		assertDate(formulaire.getTestFormulaire2().getDate(), DATE_SUCCESS_1);

	}

	@Test
	public void validFormulaireFormulaireDate() {

		String FORM = "testFormulaire2.testFormulaire.";
		assertValidDate(FORM, formulaire.getTestFormulaire2()
				.getTestFormulaire());
		assertDate(formulaire.getTestFormulaire2().getTestFormulaire()
				.getDate(), DATE_SUCCESS_1);

	}

	@Test
	public void validDateFailure() {

		String FORM = "";
		assertValidDateFailure(FORM, formulaire);
		assertDate(formulaire.getDate(), DATE_SUCCESS_1);
	}

	@Test
	public void validFormulaire1DateFailure() {

		String FORM = "testFormulaire1.";
		assertValidDateFailure(FORM, formulaire.getTestFormulaire1());
		assertDate(formulaire.getTestFormulaire1().getDate(), DATE_SUCCESS_1);
	}

	@Test
	public void validFormulaire2DateFailure() {

		String FORM = "testFormulaire2.";
		assertValidDateFailure(FORM, formulaire.getTestFormulaire2());
		assertDate(formulaire.getTestFormulaire2().getDate(), DATE_SUCCESS_1);

	}

	@Test
	public void validFormulaireFormulaireDateFailure() {

		String FORM = "testFormulaire2.testFormulaire.";
		assertValidDateFailure(FORM, formulaire.getTestFormulaire2()
				.getTestFormulaire());
		assertDate(formulaire.getTestFormulaire2().getTestFormulaire()
				.getDate(), DATE_SUCCESS_1);

	}

	private void assertExceptionCode(String field, String code,
			MyFormulaire formulaire) {

		assertEquals(code, ((AnnotationFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireException().get(0)
				.getException().getCode());
	}

	private void assertNullException(String field, MyFormulaire formulaire) {

		assertNull(formulaire.getException(field));
	}

	private void assertValidatorExceptionSize(String field, int size,
			MyFormulaire formulaire) {

		assertEquals(size, ((AnnotationFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireException().size());
	}

	private void assertExceptionDateValue(String field, String value,
			MyFormulaire formulaire) {

		assertEquals(value, ((TypeFormulaireException) formulaire
				.getException(field)).getValue());
	}

	private void assertExceptionDateFormatCode(String field,
			MyFormulaire formulaire) {

		assertEquals(EXPT_DATEFORMAT, ((TypeFormulaireException) formulaire
				.getException(field)).getException().getCode());
	}

	private void assertRuleExceptionCode(MyFormulaire formulaire) {

		assertEquals(EXCEPTION, formulaire.getRuleExceptions(RULE).get(0)
				.getException().getCode());
	}
	
	private void assertRuleExceptionSize(MyFormulaire formulaire) {

		assertEquals(1, formulaire.getRuleExceptions(RULE).size());
	}

	private void assertValidDate(String FORM, MyFormulaire exceptForm) {

		map.put(FORM + FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD, exceptForm);

		// date incorrecte
		map.put(FORM + FIELD, new String[] { DATE_FUTURE });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1, exceptForm);
		assertExceptionCode(FIELD, EXPT_PAST, exceptForm);

		// correction de la date
		map.put(FORM + FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD, exceptForm);

	}

	private void assertValidDateFailure(String FORM,
			MyFormulaire exceptForm) {

		// date incorrecte
		String FIELD = "date";

		map.put(FORM + FIELD, new String[] { DATE_FAILURE_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertExceptionDateFormatCode(FIELD, exceptForm);
		assertExceptionDateValue(FIELD, DATE_FAILURE_1, exceptForm);

		// date vide
		map.put(FORM + FIELD, new String[] { "" });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1, exceptForm);
		assertExceptionCode(FIELD, EXPT_NULL, exceptForm);

		assertRuleExceptionCode(exceptForm);
		assertRuleExceptionSize(exceptForm);

		// correction de la date
		map.put(FORM + FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD, exceptForm);

	}

	private static class ValidDateRule extends AbstractRuleForm {

		private Date date;

		public ValidDateRule(Date date) {
			this.date = date;
		}

		@Override
		protected boolean isValid() {
			return date != null;
		}

	};

	public static class TestFormulaire extends MyFormulaire {

		private Date date;

		@Past
		@NotNull
		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		private TestIntoFormulaire testFormulaire1 = new TestIntoFormulaire();

		@Rule(exception = EXCEPTION)
		public RuleException getValidDate() {

			ValidDateRule validDateRule = new ValidDateRule(this.date);

			return validDateRule.getRuleException();
		}

		@Formulaire
		public TestIntoFormulaire getTestFormulaire1() {
			return this.testFormulaire1;
		}

		private TestIntoFormulaire testFormulaire2 = new TestIntoFormulaire();

		@Formulaire
		public TestIntoFormulaire getTestFormulaire2() {
			return this.testFormulaire2;
		}
	}

	public static class TestIntoFormulaire extends MyFormulaire {

		private Date date;

		@Past
		@NotNull
		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		@Rule(exception = EXCEPTION)
		public RuleException getValidDate() {

			ValidDateRule validDateRule = new ValidDateRule(this.date);

			return validDateRule.getRuleException();
		}

		private TestIntoIntoFormulaire testFormulaire = new TestIntoIntoFormulaire();

		@Formulaire
		public TestIntoIntoFormulaire getTestFormulaire() {
			return this.testFormulaire;
		}
	}

	public static class TestIntoIntoFormulaire extends MyFormulaire {

		private Date date;

		@Past
		@NotNull
		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		@Rule(exception = EXCEPTION)
		public RuleException getValidDate() {

			ValidDateRule validDateRule = new ValidDateRule(this.date);

			return validDateRule.getRuleException();
		}
	}

}
