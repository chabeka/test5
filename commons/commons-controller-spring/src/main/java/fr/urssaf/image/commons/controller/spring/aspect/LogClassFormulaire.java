package fr.urssaf.image.commons.controller.spring.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanRuleFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassFieldForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.util.logger.Log;

@Aspect
public class LogClassFormulaire {

	protected static final Logger log = Logger
			.getLogger(LogClassFormulaire.class);

	private static final String CLASS_FORM = "fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm";

	private static final String FIELD_FORM = "(fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassFieldForm)";

	private static final String RULE_FORM = "(fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassRuleForm)";

	private static final String TARGET = AspectUtil.TARGET + "(" + CLASS_FORM
			+ ")";

	private static final String INIT_METHOD = TARGET + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(private void init(..))  "
			+ AspectUtil.AND + AspectUtil.ARGS + "(" + AspectUtil.METHOD + ")";

	private List<Method> methodes = new LinkedList<Method>();

	@Before(INIT_METHOD)
	public void logBeforeMethod(JoinPoint joinPoint) {

		Method method = (Method) joinPoint.getArgs()[0];
		methodes.add(method);
	}

	@After(INIT_METHOD)
	public void logAfterMethod(JoinPoint joinPoint) {
		((LinkedList<Method>) methodes).removeLast();

	}

	private static final String TAB = "\t";

	private String afficherMethode() {

		StringBuffer logMethod = new StringBuffer();
		String tabs = "";
		if (!methodes.isEmpty()) {
			tabs = TAB.concat(tabs);
			logMethod.append(tabs + "form:" + methodes.get(0).getName());

			for (int i = 1; i < methodes.size(); i++) {
				logMethod.insert(0, TAB);
				tabs = TAB.concat(tabs);
				logMethod.append("->");
				logMethod.append(methodes.get(i).getName());
			}

		}

		if (logMethod.length() > 0) {
			log.debug(logMethod.toString());
		}

		return tabs;
	}

	private static final String INIT_FIELD = AspectUtil.TARGET + FIELD_FORM
			+ AspectUtil.AND + AspectUtil.EXCECUTION
			+ "(private void init(..)) " + AspectUtil.AND + AspectUtil.ARGS
			+ "(" + AspectUtil.CLASS + ")";

	@SuppressWarnings("unchecked")
	@After(INIT_FIELD)
	public void logFieldForm(JoinPoint joinPoint) {

		// Class form = (Class) joinPoint.getArgs()[0];
		// StringBuffer logForm = new StringBuffer();
		String tabs = TAB.concat(afficherMethode());
		// logForm.append(tabs+"type:" + form.getSimpleName());

		ClassFieldForm classFieldForm = (ClassFieldForm) joinPoint.getThis();
		try {
			Map<String, BeanFormulaire> methodes = (Map<String, BeanFormulaire>) this
					.getElement(classFieldForm, "getMethodes");

			Map<String, List<ValidatorAbstract>> validators = (Map<String, List<ValidatorAbstract>>) this
					.getElement(classFieldForm, "getValidators");

			for (String field : methodes.keySet()) {
				StringBuffer logField = new StringBuffer();

				logField.append(field);

				List<ValidatorAbstract> valids = validators.get(field);
				if (!valids.isEmpty()) {
					logField.append("->"
							+ valids.get(0).getAnnotation().getSimpleName());

					for (int i = 1; i < valids.size(); i++) {
						logField.append(", ");
						logField.append(valids.get(i).getAnnotation()
								.getSimpleName());
					}

				}

				log.debug(tabs + "field:" + logField);
			}

		} catch (Exception e) {
			Log.exception(log, e);
		}
	}

	private static final String INIT_RULE = AspectUtil.TARGET + RULE_FORM
			+ AspectUtil.AND + AspectUtil.EXCECUTION
			+ "(private void init(..)) " + AspectUtil.AND + AspectUtil.ARGS
			+ "(" + AspectUtil.CLASS + ")";

	@SuppressWarnings("unchecked")
	@After(INIT_RULE)
	public void logRuleForm(JoinPoint joinPoint) {

		// Class form = (Class) joinPoint.getArgs()[0];
		// StringBuffer logForm = new StringBuffer();
		String tabs = TAB.concat(afficherMethode());
		// logForm.append(tabs+"type:" + form.getSimpleName());

		ClassRuleForm classRuleForm = (ClassRuleForm) joinPoint.getThis();
		try {
			
			Map<String, BeanRuleFormulaire> rules = (Map<String, BeanRuleFormulaire>) this
					.getElement(classRuleForm, "getRules");
			for (String rule : rules.keySet()) {
				log.debug(tabs + "rule:" + rule + "->"
						+ rules.get(rule).getRule().exception());
			}

		} catch (Exception e) {
			Log.exception(log, e);
		}
	}
	
	private Object getElement(Object classForm, String name)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Method methode = classForm.getClass().getDeclaredMethod(name,
				new Class[0]);
		methode.setAccessible(true);
		return methode.invoke(classForm, new Object[0]);

	}
}
