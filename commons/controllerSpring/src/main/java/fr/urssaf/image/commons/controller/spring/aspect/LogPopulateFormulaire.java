package fr.urssaf.image.commons.controller.spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LogPopulateFormulaire {

	protected static final Logger log = Logger
			.getLogger(LogPopulateFormulaire.class);

	private static final String POPULATE = AspectUtil.TARGET+"(fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateForm)";
	
	private static final String ARGS_COLLECTION = AspectUtil.ARGS+"(" + AspectUtil.CLASS + ","
			+ AspectUtil.CLASS + "," + AspectUtil.TAB_STRING + ","
			+ AspectUtil.STRING + "," + AspectUtil.STRING + ","
			+ AspectUtil.FORMULAIRE + ")";
	private static final String POP_COLLECTION = POPULATE + AspectUtil.AND + AspectUtil.EXCECUTION
			+ "(private * getCollection(..)) && " + ARGS_COLLECTION;

	@SuppressWarnings("unchecked")
	@Before(POP_COLLECTION)
	public void logPopulateCollection(JoinPoint joinPoint) throws Throwable {

		Class type = (Class) joinPoint.getArgs()[1];
		String[] values = (String[]) joinPoint.getArgs()[2];
		String field = (String) joinPoint.getArgs()[4];
		logCollection(field, type, values);

	}

	private static final String ARGS_ARRAY = AspectUtil.ARGS+"(" + AspectUtil.CLASS + ","
			+ AspectUtil.TAB_STRING + "," + AspectUtil.STRING + "," + AspectUtil.STRING + ","
			+ AspectUtil.FORMULAIRE + ")";
	private static final String POP_ARRAY = POPULATE + " && " + AspectUtil.EXCECUTION
			+ "(private * getArray(..)) && " + ARGS_ARRAY;

	@SuppressWarnings("unchecked")
	@Before(POP_ARRAY)
	public void logPopulateArray(JoinPoint joinPoint) throws Throwable {

		Class type = (Class) joinPoint.getArgs()[0];
		String[] values = (String[]) joinPoint.getArgs()[1];
		String field = (String) joinPoint.getArgs()[2];
		logCollection(field, type, values);

	}

	@SuppressWarnings("unchecked")
	private void logCollection(String field, Class type, String[] values) {

		log.debug(field + " type:" + type.getSimpleName());
		for (String value : values) {
			log.debug("\t\t value:" + value);
		}
	}

	private static final String ARGS_DATA = AspectUtil.ARGS+"(" + AspectUtil.CLASS + ","
			+ AspectUtil.TAB_STRING + "," + AspectUtil.STRING + "," + AspectUtil.STRING + ","
			+ AspectUtil.FORMULAIRE + ")";
	private static final String POP_DATA = POPULATE + " && " + AspectUtil.EXCECUTION
			+ "(private * getData(..)) && " + ARGS_DATA;

	@SuppressWarnings("unchecked")
	@Before(POP_DATA)
	public void logData(JoinPoint joinPoint) throws Throwable {

		Class type = (Class) joinPoint.getArgs()[0];
		String[] values = (String[]) joinPoint.getArgs()[1];
		String field = (String) joinPoint.getArgs()[3];
		
		log.debug(field + " type:" + type.getSimpleName() + " value:"
				+ values[0]);

	}

}
