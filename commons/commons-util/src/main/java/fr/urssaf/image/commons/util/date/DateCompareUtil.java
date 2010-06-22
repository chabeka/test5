package fr.urssaf.image.commons.util.date;

import java.util.Date;

import fr.urssaf.image.commons.util.comparable.CompareUtil.ObjectComparator;

public final class DateCompareUtil {

	private final static ObjectComparator<Date> comparator = new ObjectComparator<Date>();

	private DateCompareUtil(){
		
	}
	
	public static boolean sup(Date date1, Date date2) {
		return comparator.sup(date1, date2);
	}

	/**
	 * Compare deux dates
	 * 
	 * @param date1 date à comparer
	 * @param date2 date à comparer
	 * @return true si date1 < date2, false sinon
	 */
	public static boolean inf(Date date1, Date date2) {
		return comparator.inf(date1, date2);
	}
	
	public static boolean futur(Date date) {
		return sup(date, DateUtil.today());
	}

	public static boolean past(Date date) {
		return inf(date, DateUtil.today());
	}
}
