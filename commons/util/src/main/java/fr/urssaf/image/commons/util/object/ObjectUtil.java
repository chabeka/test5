package fr.urssaf.image.commons.util.object;

public final class ObjectUtil {

	private ObjectUtil() {

	}

	private static int intDefault;
	private static long longDefault;
	private static double doubleDefault;
	private static float floatDefault;
	private static char charDefault;
	private static boolean booleanDefault;
	private static byte byteDefault;
	private static short shortDefault;

	@SuppressWarnings("unchecked")
	public static Object getDefaultValue(Class classe) {

		if (classe.isPrimitive()) {
			if ("int".equals(classe.getCanonicalName())) {

				return intDefault;
			}
			if ("boolean".equals(classe.getCanonicalName())) {
				return booleanDefault;
			}
			if ("float".equals(classe.getCanonicalName())) {
				return floatDefault;
			}
			if ("double".equals(classe.getCanonicalName())) {
				return doubleDefault;
			}
			if ("byte".equals(classe.getCanonicalName())) {
				return byteDefault;
			}
			if ("long".equals(classe.getCanonicalName())) {
				return longDefault;
			}
			if ("short".equals(classe.getCanonicalName())) {
				return shortDefault;
			}
			if ("char".equals(classe.getCanonicalName())) {
				return charDefault;
			}

		}

		return null;
	}
}
