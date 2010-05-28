package fr.urssaf.image.commons.util.number;

import fr.urssaf.image.commons.util.comparable.CompareUtil.ObjectComparator;

public final class IntegerUtil{

	private static ObjectComparator<Integer> comparator = new ObjectComparator<Integer>();

	private IntegerUtil(){
		
	}
	
	public static boolean sup(Integer num1,Integer num2) {
		return comparator.sup(num1, num2);
	}
	
	public static boolean inf(Integer num1,Integer num2) {
		return comparator.inf(num1, num2);
	}
	
	public static boolean positif(Integer num){
		return comparator.sup(num,0);
	}
	
	public static boolean negatif(Integer num){
		return comparator.inf(num,0);
	}
}
