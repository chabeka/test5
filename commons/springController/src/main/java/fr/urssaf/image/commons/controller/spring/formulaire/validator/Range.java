package fr.urssaf.image.commons.controller.spring.formulaire.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAnnotation;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.support.RangeValidator;

@ValidatorAnnotation(RangeValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Range {
	double max();

	double min();

	boolean maxClose() default true;

	boolean minClose() default true;
	
	String format() default "#.#";

}
