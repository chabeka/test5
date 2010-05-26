package fr.urssaf.image.commons.controller.spring.aspect;

import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.ValidatorFormulaireException;

@Aspect
public class LogErrorFormulaire {

	protected static final Logger log = Logger
			.getLogger(LogErrorFormulaire.class);

	private static final String EXCEPTION_FORM = AspectUtil.TARGET
			+ "("
			+ "fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateExceptionForm"
			+ ")";

	private static final String EXCEPTION_RULE = AspectUtil.TARGET
			+ "("
			+ "fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateExceptionRuleForm"
			+ ")";

	private static final String TYPE = " type:";

	private static final String TYPEEXCEPTION = "fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException";

	private static final String PUT_TYPEEXCEPTION = EXCEPTION_FORM
			+ AspectUtil.AND + AspectUtil.EXCECUTION
			+ "(protected * putException(..)) " + AspectUtil.AND
			+ AspectUtil.ARGS + "(" + AspectUtil.STRING + "," + TYPEEXCEPTION
			+ ")";

	@Before(PUT_TYPEEXCEPTION)
	public void logTypeFormulaireException(JoinPoint joinPoint)
			throws Throwable {

		logTypeException(joinPoint);

	}

	private static final String PUT_TYPE_COL = EXCEPTION_FORM + AspectUtil.AND
			+ AspectUtil.EXCECUTION
			+ "(protected * putCollectionException(..)) " + AspectUtil.AND
			+ AspectUtil.ARGS + "(" + AspectUtil.STRING + "," + TYPEEXCEPTION
			+ ")";

	@Before(PUT_TYPE_COL)
	public void logTypeFormulaireExceptionCollection(JoinPoint joinPoint)
			throws Throwable {

		logTypeException(joinPoint);

	}

	private void logTypeException(JoinPoint joinPoint) {

		String field = (String) joinPoint.getArgs()[0];
		TypeFormulaireException exception = (TypeFormulaireException) joinPoint
				.getArgs()[1];

		log.debug("error:" + field + " value:" + exception.getValue() + TYPE
				+ exception.getException().getCode());

	}

	private static final String VALIDEXCEPTION = "fr.urssaf.image.commons.controller.spring.formulaire.support.exception.ValidatorFormulaireException";

	private static final String PUT_VALID = EXCEPTION_FORM + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(protected * putException(..)) "
			+ AspectUtil.AND + AspectUtil.ARGS + "(" + AspectUtil.STRING + ","
			+ VALIDEXCEPTION + ")";

	@Before(PUT_VALID)
	public void logValidatorFormulaireException(JoinPoint joinPoint)
			throws Throwable {

		logValidException(joinPoint);

	}

	private static final String PUT_VALID_COL = EXCEPTION_FORM + AspectUtil.AND
			+ AspectUtil.EXCECUTION
			+ "(protected * putCollectionException(..)) " + AspectUtil.AND
			+ AspectUtil.ARGS + "(" + AspectUtil.STRING + "," + VALIDEXCEPTION
			+ ")";

	@Before(PUT_VALID_COL)
	public void logValidatorFormulaireExceptionCollection(JoinPoint joinPoint)
			throws Throwable {

		logValidException(joinPoint);

	}

	private void logValidException(JoinPoint joinPoint) {

		String field = (String) joinPoint.getArgs()[0];
		ValidatorFormulaireException exception = (ValidatorFormulaireException) joinPoint
				.getArgs()[1];

		log.debug("error:" + field + TYPE
				+ exception.getValidator().getAnnotation().getSimpleName()
				+ TYPE + exception.getException().getCode());

	}

	private static final String PUT_RULEEXCEPTION = EXCEPTION_RULE
			+ AspectUtil.AND + AspectUtil.EXCECUTION
			+ "(protected * putException(..)) " + AspectUtil.AND
			+ AspectUtil.ARGS + "(" + AspectUtil.STRING + "," + AspectUtil.LIST
			+ ")";

	@SuppressWarnings("unchecked")
	@Before(PUT_RULEEXCEPTION)
	public void logRuleFormulaireException(JoinPoint joinPoint)
			throws Throwable {

		String rule = (String) joinPoint.getArgs()[0];
		List<RuleFormulaireException> exceptions = (List<RuleFormulaireException>) joinPoint
				.getArgs()[1];

		log.debug("rule_error:" + rule + TYPE
				+ exceptions.get(0).getException().getCode());

	}

}
