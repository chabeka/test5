package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

public class PopulateArrayTest extends
		PopulateContext<PopulateArrayTest.TestFormulaire> {

	protected static final Logger log = Logger
			.getLogger(PopulateCollectionTest.class);

	private static final String FIELD_DATES = "dates";

	public PopulateArrayTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void populateArraySuccess() {

		map.put(FIELD_DATES, new String[] { DATE_SUCCESS_1,
				DATE_SUCCESS_2, DATE_SUCCESS_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getDates().length);

		assertDate(formulaire.getDates()[0], DATE_SUCCESS_1);
		assertDate(formulaire.getDates()[1], DATE_SUCCESS_2);
		assertDate(formulaire.getDates()[2], DATE_SUCCESS_3);
		
		this.assertNullException(FIELD_DATES);

	}

	@Test
	public void populateArrayFailure() {

		map.put(FIELD_DATES, new String[] { DATE_FAILURE_1,
				DATE_SUCCESS_1, DATE_FAILURE_2 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getDates().length);

		assertNull(formulaire.getDates()[0]);
		assertDate(formulaire.getDates()[1], DATE_SUCCESS_1);
		assertNull(formulaire.getDates()[2]);
		
		this.assertCollectionExceptionSize(FIELD_DATES,2);

		this.assertExceptionDateCode(FIELD_DATES, 0);
		this.assertExceptionDateCode(FIELD_DATES, 1);

		this.assertExceptionDateValue(FIELD_DATES, DATE_FAILURE_1, 0);
		this.assertExceptionDateValue(FIELD_DATES, DATE_FAILURE_2, 1);

	}

	@Test
	public void populateArrayFailureAll() {

		map.put(FIELD_DATES, new String[] { DATE_FAILURE_1,
				DATE_FAILURE_2, DATE_FAILURE_3 });

		formulaire.populate(map);

		assertEquals(3, formulaire.getDates().length);

		assertNull(formulaire.getDates()[0]);
		assertNull(formulaire.getDates()[1]);
		assertNull(formulaire.getDates()[2]);
		
		this.assertCollectionExceptionSize(FIELD_DATES,3);

		this.assertExceptionDateCode(FIELD_DATES, 0);
		this.assertExceptionDateCode(FIELD_DATES, 1);
		this.assertExceptionDateCode(FIELD_DATES, 2);

		this.assertExceptionDateValue(FIELD_DATES, DATE_FAILURE_1, 0);
		this.assertExceptionDateValue(FIELD_DATES, DATE_FAILURE_2, 1);
		this.assertExceptionDateValue(FIELD_DATES, DATE_FAILURE_3, 2);
	}

	public static class TestFormulaire extends MyFormulaire {

		private Date[] dates;

		public Date[] getDates() {
			return this.dates;
		}

		public void setDates(Date[] dates) {
			this.dates = dates;
		}

	}

}
