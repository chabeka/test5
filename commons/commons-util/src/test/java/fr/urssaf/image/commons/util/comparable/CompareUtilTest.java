package fr.urssaf.image.commons.util.comparable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.urssaf.image.commons.util.comparable.CompareUtil.CollectionComparator;

public class CompareUtilTest {

	private CollectionComparator<String> comparator = new CollectionComparator<String>();

	@Test
	public void compareListFalse() {

		List<String> liste1 = new ArrayList<String>();

		liste1.add("toto");
		liste1.add("tata");
		liste1.add("titi");

		List<String> liste2 = new ArrayList<String>();
		liste2.add("toto");
		liste2.add("tutu");
		liste2.add("titi");

		assertFalse(comparator.equals(liste1, liste2));
		assertFalse(comparator.equals(liste2, liste1));

	}

	@Test
	public void compareListFalse2() {

		List<String> liste1 = new ArrayList<String>();

		liste1.add("toto");
		liste1.add("toto");
		liste1.add("toto");

		List<String> liste2 = new ArrayList<String>();
		liste2.add("toto");
		liste2.add("tata");
		liste2.add("titi");

		assertFalse(comparator.equals(liste1, liste2));
		assertFalse(comparator.equals(liste2, liste1));

	}

	@Test
	public void compareListSuccess() {

		List<String> liste1 = new ArrayList<String>();

		liste1.add("toto");
		liste1.add("tata");
		liste1.add("titi");

		List<String> liste2 = new ArrayList<String>();
		liste2.add("titi");
		liste2.add("toto");
		liste2.add("tata");

		assertTrue(comparator.equals(liste1, liste2));
		assertTrue(comparator.equals(liste2, liste1));

	}

}
