package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.AnnotationFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.CollectionFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;

public class PopulateContext<F extends MyFormulaire> {

	protected static final Logger log = Logger.getLogger(PopulateContext.class);

	protected F formulaire;

	protected ClassForm<F> classForm;

	protected Map<String, String[]> map;

	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",
			Locale.getDefault());

	public PopulateContext(Class<? extends F> classe, F formulaire) {
		this.classForm = new ClassForm<F>(classe);
		this.formulaire = formulaire;

		formulaire.initClassForm(classForm);
	}

	@Before
	public void setUp() {

		map = new HashMap<String, String[]>();
		this.formulaire.getException().clearException();
	}

	public static void assertDate(Date date, String formatDate) {

		assertEquals("date incorrecte:" + date, format.format(date), formatDate);
	}

	protected void assertExceptionDateFormatCode(String field) {

		assertEquals(EXPT_DATEFORMAT, ((TypeFormulaireException) formulaire
				.getException(field)).getException().getCode());
	}
	
	protected void assertTypeExceptionCode(String field,String typeException) {

		assertEquals(typeException, ((TypeFormulaireException) formulaire
				.getException(field)).getException().getCode());
	}

	protected void assertValidatorExceptionSize(String field, int size) {

		assertEquals(size, ((AnnotationFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireException().size());
	}

	protected void assertCollectionValidatorExceptionSize(String field, int size) {

		assertEquals(size, ((CollectionFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireExceptions().size());
	}

	protected void assertExceptionCode(String field,String code) {

		assertEquals(code, ((AnnotationFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireException().get(0)
				.getException().getCode());
	}
	

	protected void assertExceptionDateCode(String field, int index) {

		assertEquals(
				EXPT_DATEFORMAT,
				((CollectionFormulaireException) formulaire.getException(field))
						.getTypeFormulaireExceptions().get(index)
						.getException().getCode());
	}

	protected void assertExceptionPastCode(String field, int index) {

		assertEquals(EXPT_PAST, ((CollectionFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireExceptions().get(
				index).getException().getCode());
	}
	
	protected void assertExceptionCode(String field,String code, int index) {

		assertEquals(code, ((CollectionFormulaireException) formulaire
				.getException(field)).getValidatorFormulaireExceptions().get(
				index).getException().getCode());
	}

	protected void assertCollectionExceptionSize(String field, int size) {

		assertEquals(size, ((CollectionFormulaireException) formulaire
				.getException(field)).getTypeFormulaireExceptions().size());
	}

	protected void assertNullException(String field) {

		assertNull(formulaire.getException(field));
	}
	
	protected void assertTypeExceptionValue(String field, String value) {

		assertEquals(value, ((TypeFormulaireException) formulaire
				.getException(field)).getValue());
	}

	protected void assertExceptionDateValue(String field, String value,
			int index) {

		assertEquals(value, ((CollectionFormulaireException) formulaire
				.getException(field)).getTypeFormulaireExceptions().get(index)
				.getValue());
	}

	protected static final String DATE_SUCCESS_1 = "12/02/1922";
	protected static final String DATE_SUCCESS_2 = "12/02/1923";
	protected static final String DATE_SUCCESS_3 = "12/02/1924";
	protected static final String DATE_FAILURE_1 = "12/02/aaaa";
	protected static final String DATE_FAILURE_2 = "12/02/bbbb";
	protected static final String DATE_FAILURE_3 = "12/02/cccc";

	protected static final String EXPT_DATEFORMAT = "exception.date.format";
	protected static final String EXPT_PAST = "exception.past";
	protected static final String EXPT_NULL = "exception.notnull";

}
