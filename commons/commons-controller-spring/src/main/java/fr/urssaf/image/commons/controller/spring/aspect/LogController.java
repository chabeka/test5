package fr.urssaf.image.commons.controller.spring.aspect;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.servlet.ModelAndView;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

@Aspect
public class LogController {

	protected static final Logger log = Logger.getLogger(LogController.class);

	private static final String SERVLET = "fr.urssaf.image.commons.controller.spring.servlet";

	
	private static final String CONTROLLER = "("+SERVLET+".AbstractMyController)";
	
	private static final String ACTION_CONTROLLER = "("+SERVLET+".ActionController)";

	
	private final String TIME = AspectUtil.TARGET + CONTROLLER + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(public * handleRequest(..))";

	@Around(TIME)
	public Object logControllerTime(ProceedingJoinPoint pjp) throws Throwable {

		Calendar calendar1 = Calendar.getInstance();

		Object retVal = pjp.proceed();
		Calendar calendar2 = Calendar.getInstance();

		log.debug(pjp.getThis().getClass().getSimpleName() + " time:"
				+ (calendar2.getTimeInMillis() - calendar1.getTimeInMillis())
				/ 100.0);

		return retVal;
	}

	private final String VIEW = AspectUtil.TARGET + ACTION_CONTROLLER + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(protected * view(..))";

	@Around(VIEW)
	public Object logView(ProceedingJoinPoint pjp) throws Throwable {

		ModelAndView view = (ModelAndView) pjp.proceed();
		log.debug("vue:" + view.getViewName());

		return view;

	}

	private final String ACTION = AspectUtil.TARGET + ACTION_CONTROLLER
			+ AspectUtil.AND + AspectUtil.EXCECUTION + "(private * action(..))";

	@Before(ACTION)
	public void logPopulateArray(JoinPoint joinPoint) throws Throwable {

		String action = (String) joinPoint.getArgs()[2];
		log.debug("action:" + action);

	}

	private final String INIT = AspectUtil.TARGET + CONTROLLER + AspectUtil.AND
			+ AspectUtil.EXCECUTION + "(private void init("+AspectUtil.CLASS+"))";

	@SuppressWarnings("unchecked")
	@Before(INIT)
	public void logInit(JoinPoint joinPoint) throws Throwable {

		Class<MyFormulaire> classe = (Class<MyFormulaire>) joinPoint.getArgs()[0];
		log.debug("controller:" + joinPoint.getThis().getClass().getSimpleName());
		log.debug("formulaire:" + classe.getSimpleName());

	}

}
