package fr.urssaf.image.commons.util.comparable;

import java.util.Comparator;

public abstract class AbstractComparator<T> implements Comparator<T> {

	private boolean inverse;

	protected void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	protected boolean isInverse() {
		return this.inverse;
	}

	public int compare(T arg1, T arg2) {

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

	public boolean sup(T arg1, T arg2) {
	   return compare(arg1, arg2) > 0; 
	}

	public boolean inf(T arg1, T arg2) {
		return compare(arg1, arg2) < 0 ;
	}

	public boolean range(T arg, T min, T max, boolean minClose, boolean maxClose) {

		return (minClose ? !inf(arg, min) : sup(arg, min))
				&& (maxClose ? !sup(arg, max) : inf(arg, max));
	}

	protected abstract int compareImpl(T arg1, T arg2);
}
