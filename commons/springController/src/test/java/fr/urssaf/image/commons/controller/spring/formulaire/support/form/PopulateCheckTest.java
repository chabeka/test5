package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.annotation.Phone;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.Type;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DateType;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Pattern;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Range;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Sup;

public class PopulateCheckTest extends
		PopulateContext<PopulateCheckTest.TestFormulaire> {

	protected static final Logger log = Logger.getLogger(PopulateMapTest.class);

	private static final String MAIL_SUCCESS = "JohnDoe@mail.com";
	private static final String MAIL_FAILURE = "JohnDoe@mail";

	public PopulateCheckTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void populateDateFormat() {

		String FIELD = "date";

		Type<Date> type = new Type<Date>(FIELD, new DateType("yyyy/MM/dd"));

		this.classForm.getTypeFactory().addTypeFormulaire(type);

		map.put(FIELD, new String[] { "1922/02/12" });

		formulaire.populate(map);
		formulaire.isValid();

		assertDate(formulaire.getDate(), "12/02/1922");
		assertNullException(FIELD);

	}

	@Test
	public void populateEmail() {

		String FIELD = "email";

		map.put(FIELD, new String[] { MAIL_SUCCESS });

		formulaire.populate(map);
		formulaire.isValid();

		assertEquals(formulaire.getEmail(), MAIL_SUCCESS);
		assertNullException(FIELD);

	}

	@Test
	public void populateEmailFailure() {

		String FIELD = "email";

		map.put(FIELD, new String[] { MAIL_FAILURE });

		formulaire.populate(map);
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, "exception.pattern");

	}

	@Test
	public void populatePositif() {

		String FIELD = "positif";

		map.put(FIELD, new String[] { "0" });

		formulaire.populate(map);
		formulaire.isValid();

		assertEquals(Integer.valueOf(0), formulaire.getPositif());
		assertNullException(FIELD);

	}

	@Test
	public void populatePositifFailure() {

		String FIELD = "positif";

		map.put(FIELD, new String[] { "-1" });

		formulaire.populate(map);
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, "exception.supNumber");

	}

	@Test
	public void populateRange() {

		assertPopulateRange(1);
		assertPopulateRange(2);
		assertPopulateRange(3);

	}

	private void assertPopulateRange(int range) {

		String FIELD = "range";

		map.put(FIELD, new String[] { Integer.toString(range)});

		formulaire.populate(map);
		formulaire.isValid();

		assertEquals(Integer.valueOf(range), formulaire.getRange());
		assertNullException(FIELD);
	}

	@Test
	public void populateFailure() {

		assertPopulateRangeFailure(-1);
		assertPopulateRangeFailure(4);

	}

	private void assertPopulateRangeFailure(int range) {

		String FIELD = "range";

		map.put(FIELD, new String[] {Integer.toString(range) });

		formulaire.populate(map);
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, "exception.range");
	}

	@Test
	public void populatePhone() {

		String FIELD = "phone";

		map.put(FIELD, new String[] { "0610154662" });

		formulaire.populate(map);
		formulaire.isValid();

		assertEquals(formulaire.getPhone(), "0610154662");
		assertNullException(FIELD);

	}

	@Test
	public void populatePhoneFailure() {

		String FIELD = "phone";

		map.put(FIELD, new String[] { "123456789" });

		formulaire.populate(map);
		formulaire.isValid();

		assertValidatorExceptionSize(FIELD, 1);
		assertExceptionCode(FIELD, "exception.phone");

	}

	@Test
	public void populateEnum() {

		String FIELD = "cst";

		Type<constante> type = new Type<constante>(FIELD,
				new AbstractType<constante>() {

					public constante getObject(String valeur)
							throws TypeFormulaireException {

						try {
							return constante.valueOf(valeur);
						} catch (IllegalArgumentException e) {

							List<Object> values = new ArrayList<Object>();
							values.add(valeur);

							throw new TypeFormulaireException(valeur,
									constante.class, new FormulaireException(
											values, "exception.cst"));

						}

					}

					public String getValue(constante object) {
						return object.name();
					}

				});

		this.classForm.getTypeFactory().addTypeFormulaire(type);

		map.put(FIELD, new String[] { "paul" });

		formulaire.populate(map);

		assertEquals("constante incorrecte", formulaire.getCst(),
				constante.paul);
		assertNullException(FIELD);

	}

	@Test
	public void populateEnumFailure() {

		String FIELD = "cst";

		Type<constante> type = new Type<constante>(FIELD,
				new AbstractType<constante>() {

					public constante getObject(String valeur)
							throws TypeFormulaireException {

						try {
							return constante.valueOf(valeur);
						} catch (IllegalArgumentException e) {

							List<Object> values = new ArrayList<Object>();
							values.add(valeur);

							throw new TypeFormulaireException(valeur,
									constante.class, new FormulaireException(
											values, "exception.cst"));

						}

					}

					public String getValue(constante object) {
						return object.name();
					}

				});

		this.classForm.getTypeFactory().addTypeFormulaire(type);

		map.put(FIELD, new String[] { "paul1" });

		formulaire.populate(map);

		assertNull(formulaire.getCst());

		assertEquals("paul1", ((TypeFormulaireException) formulaire
				.getException(FIELD)).getValue());

		assertEquals("exception.cst", ((TypeFormulaireException) formulaire
				.getException(FIELD)).getException().getCode());

	}
	
	@Test
	public void primtiveFailure(){
		
		String FIELD = "primitive";
		
		map.put(FIELD, new String[] { "aaa" });

		formulaire.populate(map);
		formulaire.isValid();

		assertEquals(0,formulaire.getPrimitive());
		assertTypeExceptionCode("primitive","exception.integer");
		assertTypeExceptionValue("primitive","aaa");
	}

	public static class TestFormulaire extends MyFormulaire {

		private int primitive = 1;

		public void setPrimitive(int primitive) {
			this.primitive = primitive;
		}

		public int getPrimitive() {
			return primitive;
		}

		private Date date;

		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		private String email;

		@Pattern(regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
		public String getEmail() {
			return this.email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		private Integer positif;

		@Sup(borneSup = -1)
		public Integer getPositif() {
			return this.positif;
		}

		public void setPositif(Integer positif) {
			this.positif = positif;
		}

		private Integer range;

		@Range(min = 1, max = 3)
		public Integer getRange() {
			return range;
		}

		public void setRange(Integer range) {
			this.range = range;
		}

		private String phone;

		@Phone
		public String getPhone() {
			return this.phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		private constante cst;

		public void setCst(constante cst) {
			this.cst = cst;
		}

		public constante getCst() {
			return cst;
		}

	}

	public enum constante {

		pierre("1"), paul("2"), jacques("3");

		private String libelle;

		constante(String libelle) {
			this.libelle = libelle;
		}

		public String getLibelle() {
			return this.libelle;
		}
	}

}
