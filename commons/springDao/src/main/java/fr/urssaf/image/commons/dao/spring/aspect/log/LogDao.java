package fr.urssaf.image.commons.dao.spring.aspect.log;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import fr.urssaf.image.commons.dao.spring.aspect.AspectUtil;

@Aspect
public class LogDao {

	protected static final Logger log = Logger.getLogger(LogDao.class);

	private static final String DAO = "com.spring.dao.support.MyHibernateDaoSupport";

	private final String TIME = AspectUtil.TARGET + "(" + DAO + ") && "
			+ AspectUtil.EXCECUTION + "(public * *(..))";

	@Around(TIME)
	public Object logControllerTime(ProceedingJoinPoint pjp) throws Throwable {

		Calendar calendar1 = Calendar.getInstance();
		
		Object retVal = pjp.proceed();
		Calendar calendar2 = Calendar.getInstance();

		log.debug(pjp.getThis().getClass().getSimpleName() + " time:"
				+ (calendar2.getTimeInMillis() - calendar1.getTimeInMillis())
				/ 1000.0);
		
		return retVal;
	}

	
}
