package fr.urssaf.image.commons.util.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateFormatUtil {

	private DateFormatUtil() {

	}

	public static SimpleDateFormat getFormatFR() {
		synchronized (new Object()) {
			return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		}
	}

	public static String date(Date date, SimpleDateFormat format) {

		String libelle = null;

		if (date != null) {
			libelle = format.format(date);
		}

		return libelle;
	}

	public static String dateFr(Date date) {
		return date(date, getFormatFR());
	}

	public static String todayFr() {
		return getFormatFR().format(new Date());
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
