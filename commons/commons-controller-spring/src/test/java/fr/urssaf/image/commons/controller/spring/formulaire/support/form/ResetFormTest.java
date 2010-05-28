package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;

public class ResetFormTest {

	protected static final Logger log = Logger.getLogger(ResetFormTest.class);

	private TestFormulaire formulaire;

	private Map<String, String[]> map;

	private static final String INDEX_0 = "[0]";
	private static final String INDEX_1 = "[1]";

	public ResetFormTest() {

		ClassForm<TestFormulaire> classForm = new ClassForm<TestFormulaire>(
				TestFormulaire.class);

		formulaire = new TestFormulaire();
		formulaire.initClassForm(classForm);

	}

	@Before
	public void setUp() {
		map = new HashMap<String, String[]>();
	}

	@Test
	public void resetArray() {

		String FIELD = "arrayDate";

		map.put(FIELD, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });
		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getArrayDate()[0],
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getArrayDate()[1],
				PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD });

		assertNull(formulaire.getArrayDate());
	}

	@Test
	public void resetMap() {

		String FIELD = "mapDate";

		map.put(FIELD + INDEX_0,
				new String[] { PopulateContext.DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_1,
				new String[] { PopulateContext.DATE_SUCCESS_2 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getMapDate().get(0),
				PopulateContext.DATE_SUCCESS_1);

		PopulateContext.assertDate(formulaire.getMapDate().get(1),
				PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD + INDEX_0, FIELD + INDEX_1 });

		assertNull(formulaire.getMapDate().get(0));
		assertNull(formulaire.getMapDate().get(1));

	}

	@Test
	public void restList() {

		String FIELD = "listDate";

		map.put(FIELD, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getListDate().get(0),
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getListDate().get(1),
				PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD });

		assertNull(" date non null", formulaire.getListDate());
	}

	@Test
	public void resetMapList() {

		String FIELD = "mapListDate";

		map.put(FIELD + INDEX_0, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getMapListDate().get(0).get(0),
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapListDate().get(0).get(1),
				PopulateContext.DATE_SUCCESS_2);
		PopulateContext.assertDate(formulaire.getMapListDate().get(1).get(0),
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapListDate().get(1).get(1),
				PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD + INDEX_0, FIELD + INDEX_1 });

		assertNull(formulaire.getMapListDate().get(0));
		assertNull(formulaire.getMapListDate().get(1));

	}

	@Test
	public void resetMapArray() {

		String FIELD = "mapArrayDate";

		map.put(FIELD + INDEX_0, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1, new String[] { PopulateContext.DATE_SUCCESS_1,
				PopulateContext.DATE_SUCCESS_2 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getMapArrayDate().get(0)[0],
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapArrayDate().get(0)[1],
				PopulateContext.DATE_SUCCESS_2);
		PopulateContext.assertDate(formulaire.getMapArrayDate().get(1)[0],
				PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapArrayDate().get(1)[1],
				PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD + INDEX_0, FIELD + INDEX_1 });

		assertNull(formulaire.getMapArrayDate().get(0));
		assertNull(formulaire.getMapArrayDate().get(1));
	}

	@Test
	public void resetMapMapList() {

		String FIELD = "mapMapListDate";

		map.put(FIELD + INDEX_0 + INDEX_0,
				new String[] { PopulateContext.DATE_SUCCESS_1,
						PopulateContext.DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_0 + INDEX_1,
				new String[] { PopulateContext.DATE_SUCCESS_1,
						PopulateContext.DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1 + INDEX_0,
				new String[] { PopulateContext.DATE_SUCCESS_1,
						PopulateContext.DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_1 + INDEX_1,
				new String[] { PopulateContext.DATE_SUCCESS_1,
						PopulateContext.DATE_SUCCESS_2 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getMapMapListDate().get(0).get(0)
				.get(0), PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(0).get(0)
				.get(1), PopulateContext.DATE_SUCCESS_2);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(0).get(1)
				.get(0), PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(0).get(1)
				.get(1), PopulateContext.DATE_SUCCESS_2);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(1).get(0)
				.get(0), PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(1).get(0)
				.get(1), PopulateContext.DATE_SUCCESS_2);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(1).get(1)
				.get(0), PopulateContext.DATE_SUCCESS_1);
		PopulateContext.assertDate(formulaire.getMapMapListDate().get(1).get(1)
				.get(1), PopulateContext.DATE_SUCCESS_2);

		formulaire.reset(new String[] { FIELD + INDEX_0 + INDEX_0,
				FIELD + INDEX_0 + INDEX_1, FIELD + INDEX_1 + INDEX_0,
				FIELD + INDEX_1 + INDEX_1 });

		assertNull(formulaire.getMapMapListDate().get(0).get(0));
		assertNull(formulaire.getMapMapListDate().get(0).get(1));
		assertNull(formulaire.getMapMapListDate().get(1).get(0));
		assertNull(formulaire.getMapMapListDate().get(1).get(1));
	}

	@Test
	public void resetDate() {

		String FIELD = "date";

		map.put(FIELD, new String[] { PopulateContext.DATE_SUCCESS_1 });

		formulaire.populate(map);

		PopulateContext.assertDate(formulaire.getDate(),
				PopulateContext.DATE_SUCCESS_1);

		formulaire.reset(new String[] { FIELD });

		assertNull(formulaire.getDate());

	}

	public class TestFormulaire extends MyFormulaire {

		private Map<Integer, List<Date>> mapListDate;

		public Map<Integer, List<Date>> getMapListDate() {
			return this.mapListDate;
		}

		public void setMapListDate(Map<Integer, List<Date>> mapListDate) {
			this.mapListDate = mapListDate;
		}

		private Date[] arrayDate;

		public Date[] getArrayDate() {
			return this.arrayDate;
		}

		public void setArrayDate(Date[] arrayDate) {
			this.arrayDate = arrayDate;
		}

		private List<Date> listDate;

		public List<Date> getListDate() {
			return this.listDate;
		}

		public void setListDate(List<Date> listDate) {
			this.listDate = listDate;
		}

		private Map<Integer, Date> mapDate;

		public Map<Integer, Date> getMapDate() {
			return this.mapDate;
		}

		public void setMapDate(Map<Integer, Date> mapDate) {
			this.mapDate = mapDate;
		}

		private Map<Integer, Map<Integer, List<Date>>> mapMapListDate;

		public Map<Integer, Map<Integer, List<Date>>> getMapMapListDate() {
			return this.mapMapListDate;
		}

		public void setMapMapListDate(
				Map<Integer, Map<Integer, List<Date>>> mapMapListDate) {
			this.mapMapListDate = mapMapListDate;
		}

		private Map<Integer, Date[]> mapArrayDate;

		public Map<Integer, Date[]> getMapArrayDate() {
			return this.mapArrayDate;
		}

		public void setMapArrayDate(Map<Integer, Date[]> mapArrayDate) {
			this.mapArrayDate = mapArrayDate;
		}

		private Date date;

		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}

}
