package fr.urssaf.image.commons.ioc.spring.exemple.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("injection")
public class InjectionAnnotationServiceImpl implements InjectionService {

	protected static final Logger log = Logger
			.getLogger(InjectionAnnotationServiceImpl.class);

	@Override
	public String methode(String libelle) {
		log.debug(libelle);
		return libelle;

	}

}
