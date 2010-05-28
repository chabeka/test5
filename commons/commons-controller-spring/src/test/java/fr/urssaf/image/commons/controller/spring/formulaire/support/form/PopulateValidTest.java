package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotEmpty;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotNull;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;

public class PopulateValidTest extends
		PopulateContext<PopulateValidTest.TestFormulaire> {

	private static final String DATE_FUTURE = "12/12/2024";

	private static final String INDEX_0 = "[0]";
	private static final String INDEX_1 = "[1]";

	public PopulateValidTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void validDate() {

		// date correcte
		String FIELD = "date";

		map.put(FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

		// date incorrecte
		map.put(FIELD, new String[] { DATE_FUTURE });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, EXPT_PAST);

		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validDateFailure() {

		// date incorrecte
		String FIELD = "date";

		map.put(FIELD, new String[] { DATE_FAILURE_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertExceptionDateFormatCode(FIELD);
		assertTypeExceptionValue(FIELD, DATE_FAILURE_1);

		// date vide
		map.put(FIELD, new String[] { "" });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, EXPT_NULL);

		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validArrayDate() {

		// date correcte
		String FIELD = "arrayDate";

		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

		// date incorrecte
		map.put(FIELD, new String[] { DATE_FUTURE, DATE_FUTURE,"" });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD, 3);
		assertExceptionPastCode(FIELD, 0);
		assertExceptionPastCode(FIELD, 1);
		assertExceptionCode(FIELD,EXPT_NULL, 2);

		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}
	
	@Test
	public void validArrayDateFailure() {

		// date correcte
		String FIELD = "arrayDate";

		map.put(FIELD, new String[] { DATE_FAILURE_1, DATE_FUTURE, DATE_FUTURE,
				DATE_FAILURE_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD, 4);
		assertExceptionCode(FIELD,EXPT_NULL, 0);
		assertExceptionPastCode(FIELD, 1);
		assertExceptionPastCode(FIELD, 2);
		assertExceptionCode(FIELD,EXPT_NULL, 3);

		assertCollectionExceptionSize(FIELD, 2);

		assertExceptionDateCode(FIELD, 0);
		assertExceptionDateCode(FIELD, 1);

		assertExceptionDateValue(FIELD, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD, DATE_FAILURE_2, 1);
		
		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validListDate() {

		// date correcte
		String FIELD = "listDate";

		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

		// date incorrecte
		map.put(FIELD, new String[] { DATE_FUTURE, DATE_FUTURE });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD, 2);
		assertExceptionPastCode(FIELD, 0);
		assertExceptionPastCode(FIELD, 1);

		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validListDateFailure() {

		// date incorrecte
		String FIELD = "listDate";

		map.put(FIELD, new String[] { DATE_FAILURE_1, DATE_FUTURE, DATE_FUTURE,
				DATE_FAILURE_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD, 2);
		assertExceptionPastCode(FIELD, 0);
		assertExceptionPastCode(FIELD, 1);

		assertCollectionExceptionSize(FIELD, 2);

		assertExceptionDateCode(FIELD, 0);
		assertExceptionDateCode(FIELD, 1);

		assertExceptionDateValue(FIELD, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD, DATE_FAILURE_2, 1);
		
		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validMapDate() {

		String FIELD = "mapDate";

		// date correcte
		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD + INDEX_0);
		assertNullException(FIELD + INDEX_1);

		// date incorrecte
		map.put(FIELD + INDEX_0, new String[] { DATE_FUTURE });
		map.put(FIELD + INDEX_1, new String[] { DATE_FUTURE });

		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD + INDEX_0, 1);
		assertExceptionCode(FIELD + INDEX_0, EXPT_PAST);

		assertValidatorExceptionSize(FIELD + INDEX_1, 1);
		assertExceptionCode(FIELD + INDEX_1, EXPT_PAST);

		// correction de la date
		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2 });

		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD + INDEX_0);
		assertNullException(FIELD + INDEX_1);

	}
	
	@Test
	public void validMapDateFailure() {

		String FIELD = "mapDate"+ INDEX_0;

		// date correcte
		map.put(FIELD, new String[] { DATE_FAILURE_1 });
		
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertExceptionDateFormatCode(FIELD);
		assertTypeExceptionValue(FIELD, DATE_FAILURE_1);

		// date vide
		map.put(FIELD, new String[] { "" });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, EXPT_NULL);

		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	@Test
	public void validMapListDate() {

		// date correcte
		String FIELD = "mapListDate";

		map.put(FIELD + INDEX_0,
				new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1,
				new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD + INDEX_0);
		assertNullException(FIELD + INDEX_1);

		// date incorrecte
		map.put(FIELD + INDEX_0, new String[] { DATE_FUTURE, DATE_FUTURE });
		map.put(FIELD + INDEX_1, new String[] { DATE_FUTURE, DATE_FUTURE });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD + INDEX_0, 2);
		assertExceptionPastCode(FIELD + INDEX_0, 0);
		assertExceptionPastCode(FIELD + INDEX_0, 1);

		assertCollectionValidatorExceptionSize(FIELD + INDEX_1, 2);
		assertExceptionPastCode(FIELD + INDEX_1, 0);
		assertExceptionPastCode(FIELD + INDEX_1, 1);

		// correction de la date
		map.put(FIELD + INDEX_0,
				new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1,
				new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD + INDEX_0);
		assertNullException(FIELD + INDEX_1);

	}
	
	@Test
	public void validMapListDateFailure() {

		String FIELD = "mapListDate"+ INDEX_0;

		// date correcte
		map.put(FIELD, new String[] { DATE_FAILURE_1, DATE_FUTURE, DATE_FUTURE,
				DATE_FAILURE_2 });
		
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertCollectionValidatorExceptionSize(FIELD, 2);
		assertExceptionPastCode(FIELD, 0);
		assertExceptionPastCode(FIELD, 1);

		assertCollectionExceptionSize(FIELD, 2);

		assertExceptionDateCode(FIELD, 0);
		assertExceptionDateCode(FIELD, 1);

		assertExceptionDateValue(FIELD, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD, DATE_FAILURE_2, 1);
		
		// correction de la date
		map.put(FIELD, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2 });
		formulaire.populate(map);

		formulaire.isValid();
		formulaire.isValid();

		assertNullException(FIELD);

	}

	public static class TestFormulaire extends MyFormulaire {

		private Date date;

		@Past
		@NotNull
		@NotEmpty
		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		private List<Date> listDate;

		@Past
		@NotEmpty
		public List<Date> getListDate() {
			return this.listDate;
		}

		public void setListDate(List<Date> listDate) {
			this.listDate = listDate;
		}

		private Date[] arrayDate;

		@Past
		@NotNull
		@NotEmpty
		public Date[] getArrayDate() {
			return this.arrayDate;
		}

		public void setArrayDate(Date[] arrayDate) {
			this.arrayDate = arrayDate;
		}

		private Map<Integer, Date> mapDate;

		@Past
		@NotNull
		@NotEmpty
		public Map<Integer, Date> getMapDate() {
			return this.mapDate;
		}

		public void setMapDate(Map<Integer, Date> mapDate) {
			this.mapDate = mapDate;
		}

		private Map<Integer, List<Date>> mapListDate;

		public void setMapListDate(Map<Integer, List<Date>> mapListDate) {
			this.mapListDate = mapListDate;
		}

		@Past
		@NotEmpty
		public Map<Integer, List<Date>> getMapListDate() {
			return mapListDate;
		}

	}

}
