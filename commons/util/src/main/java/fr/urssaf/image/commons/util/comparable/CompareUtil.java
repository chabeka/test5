package fr.urssaf.image.commons.util.comparable;

import java.util.Collection;

public final class CompareUtil {

	private CompareUtil() {

	}

	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 != null && obj2 == null) {
			return false;
		}
		if (obj1 == null && obj2 != null) {
			return false;
		}

		return obj1.equals(obj2);
	}

	public static class ObjectComparator<O extends Comparable<O>> extends
			AbstractComparator<O> {

		@Override
		protected int compareImpl(O arg1, O arg2) {
			return arg1.compareTo(arg2);
		}
	}

	public static class CollectionComparator<O> {

		public boolean equals(Collection<O> arg1, Collection<O> arg2) {

			if (arg1 == null && arg2 == null) {
				return true;
			}

			if (arg1 == null || arg2 == null) {
				return false;
			}

			if (arg1.size() != arg2.size()) {
				return false;
			}

			for (O obj1 : arg1) {

				if (!arg2.contains(obj1)) {
					return false;
				}

			}
			
			for (O obj2 : arg2) {

				if (!arg1.contains(obj2)) {
					return false;
				}

			}

			return true;
		}

	}
}
