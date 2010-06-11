package fr.urssaf.image.commons.ioc.spring.exemple.service;

import org.apache.log4j.Logger;

public class InjectionServiceImpl implements InjectionService {

	protected static final Logger log = Logger
			.getLogger(InjectionServiceImpl.class);

	@Override
	public String methode(String libelle) {
		log.debug(libelle);
		return libelle;

	}

}
