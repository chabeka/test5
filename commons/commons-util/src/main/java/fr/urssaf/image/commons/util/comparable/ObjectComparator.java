package fr.urssaf.image.commons.util.comparable;

public class ObjectComparator<O extends Comparable<O>> extends
		AbstractComparator<O> {

	@Override
	protected int compareImpl(O arg1, O arg2) {
		return arg1.compareTo(arg2);
	}
}
