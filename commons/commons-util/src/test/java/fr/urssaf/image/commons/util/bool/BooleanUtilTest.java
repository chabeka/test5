package fr.urssaf.image.commons.util.bool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.urssaf.image.commons.util.bool.BooleanUtil;

public class BooleanUtilTest {

	@Test
	public void getBool() {

		assertTrue(BooleanUtil.getBool(true));
		assertFalse(BooleanUtil.getBool(false));
		assertFalse(BooleanUtil.getBool(null));

	}

	
}
