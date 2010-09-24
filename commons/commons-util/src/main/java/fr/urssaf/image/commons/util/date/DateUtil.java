package fr.urssaf.image.commons.util.date;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	private DateUtil() {

	}

	public static Date today() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	public static Date tomorrow() {

		return today(1);
	}

	public static Date yesterday() {

		return today(-1);
	}

	public static Date today(int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today());
		calendar.add(Calendar.DATE, day);

		return calendar.getTime();
	}

}
