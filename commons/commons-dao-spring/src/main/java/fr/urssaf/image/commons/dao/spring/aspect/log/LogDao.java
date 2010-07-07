package fr.urssaf.image.commons.dao.spring.aspect.log;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogDao {

	protected static final Logger LOGGER = Logger.getLogger(LogDao.class);

	private final static String TIME = "target(fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport) && execution(public * *(..))";
	
	@Around(TIME)
	public Object logControllerTime(ProceedingJoinPoint pjp) throws Throwable {

		Calendar calendar1 = Calendar.getInstance();

		Object retVal = pjp.proceed();
		Calendar calendar2 = Calendar.getInstance();

		LOGGER.debug(pjp.getThis().getClass().getSimpleName() + " time:"
				+ (calendar2.getTimeInMillis() - calendar1.getTimeInMillis())
				/ 1000.0);

		return retVal;
	}

}
