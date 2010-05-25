package fr.urssaf.image.commons.controller.spring.formulaire.support.validator;

import java.lang.annotation.Annotation;
import java.util.List;

import org.apache.log4j.Logger;

public interface ValidatorAbstract<O, P extends Annotation> {

	public static final Logger log = Logger.getLogger(ValidatorAbstract.class);

	public boolean isValid(O value);

	public void initialize(P parametres);
	
	public String getLibelleValue(O value);

	public String getValidatorException();
	
	public List<String> getExceptionParameters();
	
	public Class<P> getAnnotation();
	
}
