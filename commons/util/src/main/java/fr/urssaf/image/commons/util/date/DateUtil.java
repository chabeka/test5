package fr.urssaf.image.commons.util.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateUtil {

	
	private final static SimpleDateFormat formatFr = new SimpleDateFormat(
			"dd/MM/yyyy", Locale.getDefault());

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

	public static String date(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale
				.getDefault());
		return date(date, format);
	}

	public static String date(Date date, SimpleDateFormat format) {
		if (date != null) {
			return format.format(date);
		} else {
			return null;
		}
	}

	public static String dateFr(Date date) {
		return date(date, formatFr);
	}

	public static String todayFr() {
		return formatFr.format(today());
	}

	public static List<String> date(Collection<Date> dates, String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale
				.getDefault());
		List<String> results = new ArrayList<String>();

		if (dates != null) {
			for (Date date : dates) {
				results.add(date(date, format));
			}

		}
		return results;
	}

}
