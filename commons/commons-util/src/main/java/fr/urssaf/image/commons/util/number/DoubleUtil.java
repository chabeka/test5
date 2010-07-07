package fr.urssaf.image.commons.util.number;

import fr.urssaf.image.commons.util.comparable.ObjectComparator;

public final class DoubleUtil {

	private static ObjectComparator<Double> comparator = new ObjectComparator<Double>();

	private DoubleUtil() {

	}

	public static boolean sup(Double num1, Double num2) {
		return comparator.sup(num1, num2);
	}

	public static boolean inf(Double num1, Double num2) {
		return comparator.inf(num1, num2);
	}

	public static boolean range(Double num, Double min, Double max,
			boolean minClose, boolean maxClose) {

		return comparator.range(num, min, max, minClose, maxClose);
	}

}
