package fr.urssaf.image.commons.util.comparable;

import java.util.Comparator;

public abstract class AbstractComparator<T> implements Comparator<T> {

	private boolean inverse;

	protected final void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	protected final boolean isInverse() {
		return this.inverse;
	}

	public final int compare(T arg1, T arg2) {

		int compare = 0;

		if (arg1 != null && arg2 == null) {
			compare = 1;
		} else if (arg1 == null && arg2 == null) {
			compare = 0;
		} else if (arg1 == null && arg2 != null) {
			compare = -1;
		} else {

			compare = compareImpl(arg1, arg2);
		}
		
		if (inverse) {
			compare = -1 * compare;
		}

		return compare;
		
	}

	public final boolean sup(T arg1, T arg2) {
	   return compare(arg1, arg2) > 0; 
	}

	public final boolean inf(T arg1, T arg2) {
		return compare(arg1, arg2) < 0 ;
	}

	public final boolean range(T arg, T min, T max, boolean minClose, boolean maxClose) {

		return (minClose ? !inf(arg, min) : sup(arg, min))
				&& (maxClose ? !sup(arg, max) : inf(arg, max));
	}

	protected abstract int compareImpl(T arg1, T arg2);
}
