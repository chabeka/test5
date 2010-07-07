package fr.urssaf.image.commons.util.collection;

import java.util.Collection;

public class CollectionUtils {

	public static class CollectionComparator<O> {

		public boolean equals(Collection<O> arg1, Collection<O> arg2) {

			boolean isEquals;

			if (arg1 == null && arg2 == null) {
				isEquals = true;
			}

			else if (arg1 == null || arg2 == null) {
				isEquals = false;
			}

			else if (arg1.size() == arg2.size()) {

				isEquals = equalsCollection(arg1, arg2);

			}

			else {
				isEquals = false;
			}

			return isEquals;
		}

		private boolean equalsCollection(Collection<O> arg1, Collection<O> arg2) {

			boolean isEquals = true;

			for (O obj1 : arg1) {

				if (!arg2.contains(obj1)) {
					isEquals = false;
					break;
				}

			}

			if (isEquals) {
				for (O obj2 : arg2) {

					if (!arg1.contains(obj2)) {
						isEquals = false;
						break;
					}

				}
			}

			return isEquals;
		}

	}
}
