package fr.urssaf.image.commons.util.number;

import fr.urssaf.image.commons.util.comparable.ObjectComparator;

public final class LongUtil{

	private static ObjectComparator<Long> comparator = new ObjectComparator<Long>();

	private LongUtil(){
		
	}
	
	public static boolean sup(Long num1,Long num2) {
		return comparator.sup(num1, num2);
	}
	
	public static boolean inf(Long num1,Long num2) {
		return comparator.inf(num1, num2);
	}
	
	
}
