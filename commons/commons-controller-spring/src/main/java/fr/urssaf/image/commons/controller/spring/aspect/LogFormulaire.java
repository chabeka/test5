package fr.urssaf.image.commons.controller.spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

@Aspect
public class LogFormulaire {

	protected static final Logger log = Logger
			.getLogger(LogFormulaire.class);

	private static final String FORMULAIRE = AspectUtil.TARGET + "("
			+ AspectUtil.FORMULAIRE + ")";

	@Pointcut(VALID)
	public void validPointcut() {
	}

	private static final String VALID = FORMULAIRE + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(public * isValid()) ";

	@AfterReturning(pointcut = "fr.urssaf.image.commons.controller.spring.aspect.LogFormulaire.validPointcut()", returning = "result")
	public void logValidFormulaire(JoinPoint joinPoint, boolean result)
			throws Throwable {

		MyFormulaire formulaire = (MyFormulaire) joinPoint.getTarget();

		log.debug(formulaire.getClass().getSimpleName() + " valid:" + result);

	}
	
	private static final String RESET = FORMULAIRE + " && "
	+ AspectUtil.EXCECUTION + "(public void reset("+AspectUtil.STRING+")) ";
	
	@Before(RESET)
	public void logResetFormulaire(JoinPoint joinPoint)
			throws Throwable {

		String field = (String) joinPoint.getArgs()[0];

		log.debug("reset:"+field);

	}
}
