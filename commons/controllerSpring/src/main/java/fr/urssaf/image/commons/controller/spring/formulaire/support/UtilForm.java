package fr.urssaf.image.commons.controller.spring.formulaire.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class UtilForm {

	private UtilForm() {

	}

	public static String getNameForm(Method formulaire) {

		String nameForm = formulaire.getName().substring(3);
		nameForm = nameForm.substring(0, 1).toLowerCase()
				+ nameForm.substring(1);
		return nameForm;

	}

	private static final char point = ".".charAt(0);
	private static final char hook_right = "]".charAt(0);
	private static final char hook_left = "[".charAt(0);

	public static class FieldForm {

		public String field;

		public List<String> keys = new ArrayList<String>();

		public FieldForm(String field) {

			String fieldLower = field.substring(0, 1).toLowerCase()
					+ field.substring(1);

			if (fieldLower.indexOf(hook_left) > -1) {
				this.field = fieldLower.substring(0, fieldLower
						.indexOf(hook_left));

				String keys = "";
				if (fieldLower.charAt(fieldLower.length() - 1) == hook_right) {
					keys = fieldLower.substring(
							fieldLower.indexOf(hook_left) + 1, fieldLower
									.length() - 1);
				}
				Pattern pattern = Pattern.compile(String.valueOf(hook_right)
						.concat(String.valueOf(hook_left)), Pattern.LITERAL);
				String[] tabKeys = pattern.split(keys);

				for (int i = 0; i < tabKeys.length; i++) {
					this.keys.add(tabKeys[i]);
				}

			}

			else {
				this.field = field;
			}

		}
	}

	public static String getForm(String arg) {
		return arg.substring(0, arg.indexOf(point));
	}

	public static boolean containsPoint(String arg) {
		return arg.contains(String.valueOf(point));
	}

	public static boolean containsHook(String arg) {
		return arg.indexOf(hook_left) > 1
				&& arg.indexOf(hook_left) < arg.indexOf(point);
	}

	public static String getElement(String arg) {
		return arg.substring(arg.indexOf(point) + 1);
	}

	public static String getRule(String rule, String index) {

		return rule + hook_left + index + hook_right;
	}

}
