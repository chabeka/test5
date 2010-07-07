package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.AbstractRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;
import fr.urssaf.image.commons.util.date.DateFormatUtil;

public class PopulateRuleTest extends
		PopulateContext<PopulateRuleTest.TestFormulaire> {

	private static final String DATE_FUTURE_1 = "12/12/2023";
	private static final String DATE_FUTURE_2 = "12/12/2024";

	private static final String EXCEPTION = "date.before";

	private static final String FIELD_D = "dateDebut";

	public PopulateRuleTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	private void assertNullRuleExceptions(String rule) {

		assertNull(formulaire.getRuleExceptions(rule));
	}

	private void assertRuleExceptionCode(String rule) {

		assertRuleExceptionCode(rule, 0);
	}

	private void assertRuleExceptionCode(String rule, int index) {

		assertEquals(EXCEPTION, formulaire.getRuleExceptions(rule).get(index)
				.getException().getCode());
	}

	private void assertRuleExceptionParameter(String rule, String value,
			int index) {

		assertEquals(value, formulaire.getRuleExceptions(rule).get(index)
				.getException().getParameters().get(1));
	}

	private void assertRuleExceptionSize(String rule, int size) {

		assertEquals(size, formulaire.getRuleExceptions(rule).size());
	}

	@Test
	public void dateBefore() throws ParseException {

		String RULE = "getValidDate";
		String FIELD_F = "dateFin";

		map.put(FIELD_D, new String[] { DATE_FUTURE_2 });
		map.put(FIELD_F, new String[] { DATE_FUTURE_1 });
		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertRuleExceptionCode(RULE);
		assertRuleExceptionSize(RULE, 1);

		// correction de la date
		map.put(FIELD_D, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F, new String[] { DATE_FUTURE_2 });
		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE);
	}

	@Test
	public void dateBeforeFailure() throws ParseException {

		String RULE = "getValidDate";
		String FIELD_F = "dateFin";

		map.put(FIELD_D, new String[] { DATE_FUTURE_2 });
		map.put(FIELD_F, new String[] { DATE_FAILURE_1 });
		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE);
	}

	@Test
	public void dateBeforeCollection() throws ParseException {

		String RULE = "getValidListDate";
		String FIELD_F = "listDate";

		map.put(FIELD_D, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F, new String[] { DATE_FUTURE_2, DATE_SUCCESS_1,
				DATE_FUTURE_1, DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertRuleExceptionSize(RULE, 2);

		assertRuleExceptionCode(RULE, 0);
		assertRuleExceptionCode(RULE, 1);

		assertRuleExceptionParameter(RULE, DATE_SUCCESS_1, 0);
		assertRuleExceptionParameter(RULE, DATE_SUCCESS_2, 1);

		// correction de la date
		map.put(FIELD_D, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD_F, new String[] { DATE_FUTURE_2, DATE_FUTURE_1,
				DATE_FUTURE_1, DATE_FUTURE_2 });
		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE);

	}

	@Test
	public void dateBeforeCollectionFailure() throws ParseException {

		String RULE = "getValidListDate";
		String FIELD_F = "listDate";

		map.put(FIELD_D, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F, new String[] { DATE_FAILURE_1, DATE_SUCCESS_1,
				DATE_FAILURE_2, DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertRuleExceptionSize(RULE, 2);

		assertRuleExceptionCode(RULE, 0);
		assertRuleExceptionCode(RULE, 1);

		assertRuleExceptionParameter(RULE, DATE_SUCCESS_1, 0);
		assertRuleExceptionParameter(RULE, DATE_SUCCESS_2, 1);

		assertCollectionExceptionSize(FIELD_F, 2);

		assertExceptionDateCode(FIELD_F, 0);
		assertExceptionDateCode(FIELD_F, 1);

		assertExceptionDateValue(FIELD_F, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD_F, DATE_FAILURE_2, 1);

	}

	@Test
	public void dateBeforeMap() throws ParseException {

		String RULE = "getValidMapDate";
		String FIELD_F = "MapDate";

		String INDEX_0 = "[0]";
		String INDEX_1 = "[1]";
		String INDEX_2 = "[2]";
		String INDEX_3 = "[3]";

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		map.put(FIELD_D, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F + INDEX_0, new String[] { DATE_FUTURE_2 });
		map.put(FIELD_F + INDEX_1, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD_F + INDEX_2, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F + INDEX_3, new String[] { DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE + INDEX_0);
		assertRuleExceptionSize(RULE + INDEX_1, 1);
		assertNullRuleExceptions(RULE + INDEX_2);
		assertRuleExceptionSize(RULE + INDEX_3, 1);

		assertRuleExceptionCode(RULE + INDEX_1, 0);
		assertRuleExceptionCode(RULE + INDEX_3, 0);

		assertRuleExceptionParameter(RULE + INDEX_1, DATE_SUCCESS_1, 0);
		assertRuleExceptionParameter(RULE + INDEX_3, DATE_SUCCESS_2, 0);

		// correction de la date
		map.put(FIELD_D, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD_F + INDEX_0, new String[] { DATE_FUTURE_2 });
		map.put(FIELD_F + INDEX_1, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F + INDEX_2, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F + INDEX_3, new String[] { DATE_FUTURE_1 });
		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE + INDEX_0);
		assertNullRuleExceptions(RULE + INDEX_1);
		assertNullRuleExceptions(RULE + INDEX_2);
		assertNullRuleExceptions(RULE + INDEX_3);
	}

	@Test
	public void dateBeforeMapFailure() throws ParseException {

		String RULE = "getValidMapDate";
		String FIELD_F = "MapDate";

		String INDEX_0 = "[0]";
		String INDEX_1 = "[1]";
		String INDEX_2 = "[2]";
		String INDEX_3 = "[3]";

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		map.put(FIELD_D, new String[] { DATE_FUTURE_1 });
		map.put(FIELD_F + INDEX_0, new String[] { DATE_FAILURE_1 });
		map.put(FIELD_F + INDEX_1, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD_F + INDEX_2, new String[] { DATE_FAILURE_2 });
		map.put(FIELD_F + INDEX_3, new String[] { DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValidRule(RULE);
		formulaire.isValidRule(RULE);

		assertNullRuleExceptions(RULE + INDEX_0);
		assertRuleExceptionSize(RULE + INDEX_1, 1);
		assertNullRuleExceptions(RULE + INDEX_2);
		assertRuleExceptionSize(RULE + INDEX_3, 1);

		assertRuleExceptionCode(RULE + INDEX_1, 0);
		assertRuleExceptionCode(RULE + INDEX_3, 0);

		assertRuleExceptionParameter(RULE + INDEX_1, DATE_SUCCESS_1, 0);
		assertRuleExceptionParameter(RULE + INDEX_3, DATE_SUCCESS_2, 0);

		assertExceptionDateFormatCode(FIELD_F + INDEX_0);
		assertExceptionDateFormatCode(FIELD_F + INDEX_2);
		assertTypeExceptionValue(FIELD_F + INDEX_0, DATE_FAILURE_1);
		assertTypeExceptionValue(FIELD_F + INDEX_2, DATE_FAILURE_2);

	}

	private static class ValidDateRule extends AbstractRuleForm {

		private Date dateDebut;
		private Date dateFin;

		public ValidDateRule(Date dateDebut, Date dateFin) {
			this.dateDebut = dateDebut;
			this.dateFin = dateFin;
		}

		@Override
		protected boolean isValid() {
			return !(dateDebut != null && dateFin != null && dateDebut
					.after(dateFin));
		}

		@Override
		protected List<Object> getValues() {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(DateFormatUtil.dateFr(dateDebut));
			parameters.add(DateFormatUtil.dateFr(dateFin));
			return parameters;
		}

	};

	public static class TestFormulaire extends MyFormulaire {

		private Date dateDebut;

		public void setDateDebut(Date dateDebut) {
			this.dateDebut = dateDebut;
		}

		@Past
		public Date getDateDebut() {
			return this.dateDebut;
		}

		private Date dateFin;

		public void setDateFin(Date dateFin) {
			this.dateFin = dateFin;
		}

		@Past
		public Date getDateFin() {
			return this.dateFin;
		}

		private List<Date> listDate = new ArrayList<Date>();

		@Past
		public List<Date> getListDate() {
			return this.listDate;
		}

		public void setListDate(List<Date> listDate) {
			this.listDate = listDate;
		}

		private Map<String, Date> mapDate = new HashMap<String, Date>();

		public void setMapDate(Map<String, Date> mapDate) {
			this.mapDate = mapDate;
		}

		@Past
		public Map<String, Date> getMapDate() {
			return mapDate;
		}

		@Rule(exception = EXCEPTION)
		public RuleException getValidDate() {

			ValidDateRule validDateRule = new ValidDateRule(this.dateDebut,
					this.dateFin);

			return validDateRule.getRuleException();
		}

		@Rule(exception = EXCEPTION)
		public List<RuleException> getValidListDate() {

			List<RuleException> ruleExceptions = new ArrayList<RuleException>();

			for (Date date : this.listDate) {

				ValidDateRule validDateRule = new ValidDateRule(this.dateDebut,
						date);
				RuleException ruleException = validDateRule.getRuleException();
				if (ruleException != null) {
					ruleExceptions.add(validDateRule.getRuleException());
				}

			}

			return ruleExceptions;

		}

		@Rule(exception = EXCEPTION)
		public Map<String, RuleException> getValidMapDate() {

			Map<String, RuleException> ruleExceptions = new HashMap<String, RuleException>();

			for (String id : this.mapDate.keySet()) {

				Date date = this.mapDate.get(id);

				ValidDateRule validDateRule = new ValidDateRule(this.dateDebut,
						date);

				ruleExceptions.put(id, validDateRule.getRuleException());

			}

			return ruleExceptions;

		}

		@Rule(exception = EXCEPTION)
		public List<String> getValidDateFailureList() {

			return null;
		}

		@Rule(exception = EXCEPTION)
		public void getValidDateFailure() {

		}

		@Rule(exception = EXCEPTION)
		public Map<Integer, RuleException> getValidDateFailureMap1() {

			return null;
		}

		@Rule(exception = EXCEPTION)
		public Map<Integer, String> getValidDateFailureMap2() {

			return null;
		}

	}

}
