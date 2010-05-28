package fr.urssaf.image.commons.util.bool;

public final class BooleanUtil {

	private BooleanUtil() {

	}

	public static boolean getBool(Boolean bool) {
		if (bool != null) {
			return bool.booleanValue();
		}
		
		return false;
	}
}
