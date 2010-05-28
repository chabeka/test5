package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

public class PopulateCollectionTest extends
		PopulateContext<PopulateCollectionTest.TestFormulaire> {

	protected static final Logger log = Logger
			.getLogger(PopulateCollectionTest.class);

	private static final String FIELD_DATES_1 = "dates1";

	private static final String FIELD_DATES_2 = "dates2";

	public PopulateCollectionTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void populateListSuccess() {

		map.put(FIELD_DATES_1, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2,
				DATE_SUCCESS_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getDates1().size());

		assertEquals(DATE_SUCCESS_1, formulaire.getDates1().get(0));
		assertEquals(DATE_SUCCESS_2, formulaire.getDates1().get(1));
		assertEquals(DATE_SUCCESS_3, formulaire.getDates1().get(2));
		
		this.assertNullException(FIELD_DATES_1);
	}

	@Test
	public void populateListDateSuccess() {

		map.put(FIELD_DATES_2, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2,
				DATE_SUCCESS_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getDates2().size());

		assertDate(formulaire.getDates2().get(0), DATE_SUCCESS_1);
		assertDate(formulaire.getDates2().get(1), DATE_SUCCESS_2);
		assertDate(formulaire.getDates2().get(2), DATE_SUCCESS_3);
		
		this.assertNullException(FIELD_DATES_2);

	}

	@Test
	public void populateListDateFailure() {

		map.put(FIELD_DATES_2, new String[] { DATE_FAILURE_1, DATE_SUCCESS_1,
				DATE_FAILURE_2 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(1, formulaire.getDates2().size());

		assertDate(formulaire.getDates2().get(0), DATE_SUCCESS_1);
		
		this.assertCollectionExceptionSize(FIELD_DATES_2,2);

		this.assertExceptionDateCode(FIELD_DATES_2, 0);
		this.assertExceptionDateCode(FIELD_DATES_2, 1);

		this.assertExceptionDateValue(FIELD_DATES_2, DATE_FAILURE_1, 0);
		this.assertExceptionDateValue(FIELD_DATES_2, DATE_FAILURE_2, 1);

	}

	@Test
	public void populateListDateFailureAll() {

		map.put(FIELD_DATES_2, new String[] { DATE_FAILURE_1, DATE_FAILURE_2,
				DATE_FAILURE_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(0, formulaire.getDates2().size());
		
		this.assertCollectionExceptionSize(FIELD_DATES_2,3);

		this.assertExceptionDateCode(FIELD_DATES_2, 0);
		this.assertExceptionDateCode(FIELD_DATES_2, 1);
		this.assertExceptionDateCode(FIELD_DATES_2, 2);

		this.assertExceptionDateValue(FIELD_DATES_2, DATE_FAILURE_1, 0);
		this.assertExceptionDateValue(FIELD_DATES_2, DATE_FAILURE_2, 1);
		this.assertExceptionDateValue(FIELD_DATES_2, DATE_FAILURE_3, 2);

	}

	public static class TestFormulaire extends MyFormulaire {

		@SuppressWarnings("unchecked")
		private List dates1;

		@SuppressWarnings("unchecked")
		public List getDates1() {
			return this.dates1;
		}

		@SuppressWarnings("unchecked")
		public void setDates1(List dates1) {
			this.dates1 = dates1;
		}

		private List<Date> dates2;

		public List<Date> getDates2() {
			return this.dates2;
		}

		public void setDates2(List<Date> dates2) {
			this.dates2 = dates2;
		}

	}

}
