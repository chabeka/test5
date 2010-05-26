package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

public class PopulateMapTest extends
		PopulateContext<PopulateMapTest.TestFormulaire> {

	protected static final Logger log = Logger.getLogger(PopulateMapTest.class);

	private static final String INDEX_0 = "[0]";
	private static final String INDEX_1 = "[1]";
	private static final String INDEX_2 = "[2]";

	public PopulateMapTest() {
		super(TestFormulaire.class, new TestFormulaire());
	}

	@Test
	public void populateMap() {

		String FIELD = "map";

		map.put(FIELD + INDEX_0,
				new String[] { DATE_SUCCESS_1, DATE_SUCCESS_3 });
		map.put(FIELD + INDEX_1,
				new String[] { DATE_SUCCESS_2, DATE_SUCCESS_3 });
		map.put(FIELD + INDEX_2,
				new String[] { DATE_SUCCESS_3, DATE_SUCCESS_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMap().size());

		assertEquals(DATE_SUCCESS_1, formulaire.getMap().get("0"));
		assertEquals(DATE_SUCCESS_2, formulaire.getMap().get("1"));
		assertEquals(DATE_SUCCESS_3, formulaire.getMap().get("2"));

	}

	@Test
	public void populateMapDate() {

		String FIELD = "mapDate";

		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_2, new String[] { DATE_SUCCESS_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapDate().size());

		assertDate(formulaire.getMapDate().get(0), DATE_SUCCESS_1);
		assertDate(formulaire.getMapDate().get(1), DATE_SUCCESS_2);
		assertDate(formulaire.getMapDate().get(2), DATE_SUCCESS_3);

	}
	
	@Test
	public void populateMapDateFailure() {

		String FIELD = "mapDate";

		map.put(FIELD + INDEX_0, new String[] { DATE_FAILURE_1 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_2, new String[] { DATE_FAILURE_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapDate().size());

		assertNull(formulaire.getMapDate().get(0));
		assertDate(formulaire.getMapDate().get(1), DATE_SUCCESS_2);
		assertNull(formulaire.getMapDate().get(2));
		
		assertExceptionDateFormatCode(FIELD + INDEX_0);
		assertExceptionDateFormatCode(FIELD + INDEX_2);
		assertTypeExceptionValue(FIELD + INDEX_0, DATE_FAILURE_1);
		assertTypeExceptionValue(FIELD + INDEX_2, DATE_FAILURE_3);

	}

	@Test
	public void populateMapList() {

		String FIELD = "mapList";

		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2,
				DATE_SUCCESS_3 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2, DATE_SUCCESS_3,
				DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_2, new String[] { DATE_SUCCESS_3, DATE_SUCCESS_1,
				DATE_SUCCESS_2 });

		formulaire.populate(map);

		assertEquals(3, formulaire.getMapList().size());

		assertEquals(3, formulaire.getMapList().get(0).size());
		assertEquals(formulaire.getMapList().get(0).get(0), DATE_SUCCESS_1);
		assertEquals(formulaire.getMapList().get(0).get(1), DATE_SUCCESS_2);
		assertEquals(formulaire.getMapList().get(0).get(2), DATE_SUCCESS_3);

		assertEquals(3, formulaire.getMapList().get(1).size());
		assertEquals(formulaire.getMapList().get(1).get(0), DATE_SUCCESS_2);
		assertEquals(formulaire.getMapList().get(1).get(1), DATE_SUCCESS_3);
		assertEquals(formulaire.getMapList().get(1).get(2), DATE_SUCCESS_1);

		assertEquals(3, formulaire.getMapList().get(2).size());
		assertEquals(formulaire.getMapList().get(2).get(0), DATE_SUCCESS_3);
		assertEquals(formulaire.getMapList().get(2).get(1), DATE_SUCCESS_1);
		assertEquals(formulaire.getMapList().get(2).get(2), DATE_SUCCESS_2);
	}

	@Test
	public void populateMapArray() {

		String FIELD = "mapArray";

		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2,
				DATE_SUCCESS_3 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2, DATE_SUCCESS_3,
				DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_2, new String[] { DATE_SUCCESS_3, DATE_SUCCESS_1,
				DATE_SUCCESS_2 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapArray().size());

		assertEquals(3, formulaire.getMapArray().get(0).length);
		assertDate(formulaire.getMapArray().get(0)[0], DATE_SUCCESS_1);
		assertDate(formulaire.getMapArray().get(0)[1], DATE_SUCCESS_2);
		assertDate(formulaire.getMapArray().get(0)[2], DATE_SUCCESS_3);

		assertEquals(3, formulaire.getMapArray().get(1).length);
		assertDate(formulaire.getMapArray().get(1)[0], DATE_SUCCESS_2);
		assertDate(formulaire.getMapArray().get(1)[1], DATE_SUCCESS_3);
		assertDate(formulaire.getMapArray().get(1)[2], DATE_SUCCESS_1);

		assertEquals(3, formulaire.getMapArray().get(2).length);
		assertDate(formulaire.getMapArray().get(2)[0], DATE_SUCCESS_3);
		assertDate(formulaire.getMapArray().get(2)[1], DATE_SUCCESS_1);
		assertDate(formulaire.getMapArray().get(2)[2], DATE_SUCCESS_2);
	}

	@Test
	public void populateMapArrayFailure() {

		String FIELD = "mapArray";

		map.put(FIELD + INDEX_0, new String[] { DATE_FAILURE_1, DATE_SUCCESS_1,
				DATE_FAILURE_2 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2, DATE_SUCCESS_1,
				DATE_FAILURE_1 });
		map.put(FIELD + INDEX_2, new String[] { DATE_FAILURE_1, DATE_FAILURE_2,
				DATE_FAILURE_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapArray().size());

		assertEquals(3, formulaire.getMapArray().get(0).length);
		assertNull(formulaire.getMapArray().get(0)[0]);
		assertDate(formulaire.getMapArray().get(0)[1], DATE_SUCCESS_1);
		assertNull(formulaire.getMapArray().get(0)[2]);

		assertCollectionExceptionSize(FIELD + INDEX_0, 2);

		assertExceptionDateCode(FIELD + INDEX_0, 0);
		assertExceptionDateCode(FIELD + INDEX_0, 1);
		assertExceptionDateValue(FIELD + INDEX_0, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD + INDEX_0, DATE_FAILURE_2, 1);

		assertEquals(3, formulaire.getMapArray().get(1).length);
		assertDate(formulaire.getMapArray().get(1)[0], DATE_SUCCESS_2);
		assertDate(formulaire.getMapArray().get(1)[1], DATE_SUCCESS_1);
		assertNull(formulaire.getMapArray().get(1)[2]);

		assertCollectionExceptionSize(FIELD + INDEX_1, 1);

		assertExceptionDateCode(FIELD + INDEX_1, 0);
		assertExceptionDateValue(FIELD + INDEX_1, DATE_FAILURE_1, 0);

		assertEquals(3, formulaire.getMapArray().get(2).length);

		assertNull(formulaire.getMapArray().get(2)[0]);
		assertNull(formulaire.getMapArray().get(2)[1]);
		assertNull(formulaire.getMapArray().get(2)[2]);

		assertCollectionExceptionSize(FIELD + INDEX_2, 3);

		assertExceptionDateCode(FIELD + INDEX_2, 0);
		assertExceptionDateCode(FIELD + INDEX_2, 1);
		assertExceptionDateCode(FIELD + INDEX_2, 2);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_2, 1);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_3, 2);

	}

	@Test
	public void populateMapListDate() {

		String FIELD = "mapListDate";

		map.put(FIELD + INDEX_0, new String[] { DATE_SUCCESS_1, DATE_SUCCESS_2,
				DATE_SUCCESS_3 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2, DATE_SUCCESS_3,
				DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_2, new String[] { DATE_SUCCESS_3, DATE_SUCCESS_1,
				DATE_SUCCESS_2 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapListDate().size());

		assertEquals(3, formulaire.getMapListDate().get(0).size());
		assertDate(formulaire.getMapListDate().get(0).get(0), DATE_SUCCESS_1);
		assertDate(formulaire.getMapListDate().get(0).get(1), DATE_SUCCESS_2);
		assertDate(formulaire.getMapListDate().get(0).get(2), DATE_SUCCESS_3);

		assertEquals(3, formulaire.getMapListDate().get(1).size());
		assertDate(formulaire.getMapListDate().get(1).get(0), DATE_SUCCESS_2);
		assertDate(formulaire.getMapListDate().get(1).get(1), DATE_SUCCESS_3);
		assertDate(formulaire.getMapListDate().get(1).get(2), DATE_SUCCESS_1);

		assertEquals(3, formulaire.getMapListDate().get(2).size());
		assertDate(formulaire.getMapListDate().get(2).get(0), DATE_SUCCESS_3);
		assertDate(formulaire.getMapListDate().get(2).get(1), DATE_SUCCESS_1);
		assertDate(formulaire.getMapListDate().get(2).get(2), DATE_SUCCESS_2);
	}

	@Test
	public void populateMapListDateFailure() {

		String FIELD = "mapListDate";

		map.put(FIELD + INDEX_0, new String[] { DATE_FAILURE_1, DATE_SUCCESS_1,
				DATE_FAILURE_2 });
		map.put(FIELD + INDEX_1, new String[] { DATE_SUCCESS_2, DATE_SUCCESS_1,
				DATE_FAILURE_1 });
		map.put(FIELD + INDEX_2, new String[] { DATE_FAILURE_1, DATE_FAILURE_2,
				DATE_FAILURE_3 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(3, formulaire.getMapListDate().size());

		assertEquals(1, formulaire.getMapListDate().get(0).size());
		assertDate(formulaire.getMapListDate().get(0).get(0), DATE_SUCCESS_1);

		assertCollectionExceptionSize(FIELD + INDEX_0, 2);

		assertExceptionDateCode(FIELD + INDEX_0, 0);
		assertExceptionDateCode(FIELD + INDEX_0, 1);
		assertExceptionDateValue(FIELD + INDEX_0, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD + INDEX_0, DATE_FAILURE_2, 1);

		assertEquals(2, formulaire.getMapListDate().get(1).size());
		assertDate(formulaire.getMapListDate().get(1).get(0), DATE_SUCCESS_2);
		assertDate(formulaire.getMapListDate().get(1).get(1), DATE_SUCCESS_1);

		assertCollectionExceptionSize(FIELD + INDEX_1, 1);

		assertExceptionDateCode(FIELD + INDEX_1, 0);
		assertExceptionDateValue(FIELD + INDEX_1, DATE_FAILURE_1, 0);

		assertEquals(0, formulaire.getMapListDate().get(2).size());

		assertCollectionExceptionSize(FIELD + INDEX_2, 3);

		assertExceptionDateCode(FIELD + INDEX_2, 0);
		assertExceptionDateCode(FIELD + INDEX_2, 1);
		assertExceptionDateCode(FIELD + INDEX_2, 2);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_1, 0);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_2, 1);
		assertExceptionDateValue(FIELD + INDEX_2, DATE_FAILURE_3, 2);

	}

	@Test
	public void populateMapMapListDate() {

		String FIELD = "mapMapListDate";

		map.put(FIELD + INDEX_0 + INDEX_0, new String[] { DATE_SUCCESS_1,
				DATE_SUCCESS_2 });
		map.put(FIELD + INDEX_0 + INDEX_1, new String[] { DATE_SUCCESS_2,
				DATE_FAILURE_1 });
		map.put(FIELD + INDEX_1 + INDEX_0, new String[] { DATE_SUCCESS_3,
				DATE_SUCCESS_1 });
		map.put(FIELD + INDEX_1 + INDEX_1, new String[] { DATE_FAILURE_2,
				DATE_FAILURE_1 });

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(2, formulaire.getMapMapListDate().size());

		assertEquals(2, formulaire.getMapMapListDate().get(0).size());

		assertEquals(2, formulaire.getMapMapListDate().get(0).get(0).size());
		assertDate(formulaire.getMapMapListDate().get(0).get(0).get(0),
				DATE_SUCCESS_1);
		assertDate(formulaire.getMapMapListDate().get(0).get(0).get(1),
				DATE_SUCCESS_2);
		assertNullException(FIELD + INDEX_0 + INDEX_0);

		assertEquals(1, formulaire.getMapMapListDate().get(0).get(1).size());
		assertDate(formulaire.getMapMapListDate().get(0).get(1).get(0),
				DATE_SUCCESS_2);
		assertCollectionExceptionSize(FIELD + INDEX_0 + INDEX_1, 1);
		assertExceptionDateCode(FIELD + INDEX_0 + INDEX_1, 0);
		assertExceptionDateValue(FIELD + INDEX_0 + INDEX_1,
				DATE_FAILURE_1, 0);

		assertEquals(2, formulaire.getMapMapListDate().get(1).size());

		assertEquals(2, formulaire.getMapMapListDate().get(1).get(0).size());
		assertDate(formulaire.getMapMapListDate().get(1).get(0).get(0),
				DATE_SUCCESS_3);
		assertDate(formulaire.getMapMapListDate().get(1).get(0).get(1),
				DATE_SUCCESS_1);
		assertNullException(FIELD + INDEX_1 + INDEX_0);

		assertEquals(0, formulaire.getMapMapListDate().get(1).get(1).size());
		assertCollectionExceptionSize(FIELD + INDEX_1 + INDEX_1, 2);

		assertExceptionDateCode(FIELD + INDEX_1 + INDEX_1, 0);
		assertExceptionDateCode(FIELD + INDEX_1 + INDEX_1, 1);
		assertExceptionDateValue(FIELD + INDEX_1 + INDEX_1,
				DATE_FAILURE_2, 0);
		assertExceptionDateValue(FIELD + INDEX_1 + INDEX_1,
				DATE_FAILURE_1, 1);

	}

	@Test
	public void populateUnderscore() {

		map.put("_", new String[0]);

		formulaire.populate(map);
		formulaire.populate(map);

		assertEquals(0, formulaire.getException().getFieldExceptions()
				.size());

	}

	public static class TestFormulaire extends MyFormulaire {

		@SuppressWarnings("unchecked")
		private Map map;

		@SuppressWarnings("unchecked")
		public Map getMap() {
			return this.map;
		}

		@SuppressWarnings("unchecked")
		public void setMap(Map map) {
			this.map = map;
		}

		private Map<Integer, Date> mapDate;

		public Map<Integer, Date> getMapDate() {
			return this.mapDate;
		}

		public void setMapDate(Map<Integer, Date> mapDate) {
			this.mapDate = mapDate;
		}

		@SuppressWarnings("unchecked")
		private Map<Integer, List> mapList;

		@SuppressWarnings("unchecked")
		public Map<Integer, List> getMapList() {
			return this.mapList;
		}

		@SuppressWarnings("unchecked")
		public void setMapList(Map<Integer, List> mapList) {
			this.mapList = mapList;
		}

		private Map<Integer, List<Date>> mapListDate;

		public Map<Integer, List<Date>> getMapListDate() {
			return this.mapListDate;
		}

		public void setMapListDate(Map<Integer, List<Date>> mapListDate) {
			this.mapListDate = mapListDate;
		}

		private Map<Integer, Map<Integer, List<Date>>> mapMapListDate;

		public Map<Integer, Map<Integer, List<Date>>> getMapMapListDate() {
			return this.mapMapListDate;
		}

		public void setMapMapListDate(
				Map<Integer, Map<Integer, List<Date>>> mapMapListDate) {
			this.mapMapListDate = mapMapListDate;
		}

		private Map<Integer, Date[]> mapArray;

		public Map<Integer, Date[]> getMapArray() {
			return this.mapArray;
		}

		public void setMapArray(Map<Integer, Date[]> mapArray) {
			this.mapArray = mapArray;
		}

	}

}
