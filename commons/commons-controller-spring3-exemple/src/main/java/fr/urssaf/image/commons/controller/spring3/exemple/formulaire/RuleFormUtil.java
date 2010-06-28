package fr.urssaf.image.commons.controller.spring3.exemple.formulaire;

import java.util.Date;

import fr.urssaf.image.commons.util.date.DateCompareUtil;

public final class RuleFormUtil {

	public static class DateRule {

		private Date date1;
		private Date date2;

		public DateRule(Date date1, Date date2) {
			this.date1 = date1;
			this.date2 = date2;
		}

		protected boolean isValid() {
			if (this.date1 != null && this.date2 != null) {
				return DateCompareUtil.sup(this.date1, this.date2);
			}
			return true;
		}

	}

}
