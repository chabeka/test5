package fr.urssaf.image.commons.controller.spring.exemple.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.servlet.ModelAndView;

import fr.urssaf.image.commons.util.logger.Log;

@Aspect
public class ExceptionController {

	private static final Logger log = Logger
			.getLogger(ExceptionController.class);

	private static final String EXCEPTION = "target(org.springframework.web.servlet.mvc.Controller) && execution(public * handleRequest(..))";

	@Around(EXCEPTION)
	public Object exceptionController(ProceedingJoinPoint pjp) throws Throwable {

		try {
			return pjp.proceed();
		} catch (Exception e) {
			Log.exception(log, e);

			HttpServletRequest request = (HttpServletRequest) pjp.getArgs()[0];
			request.setAttribute("exceptionCode", e);

			return new ModelAndView("exceptionView");

		}

	}

}
