package fr.urssaf.image.sae.anais.portail.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Contrainte de validation sur les chaines de caractères<br>
 * Une exception est levé si la chaine est vide ou si elle ne comporte que des blancs<br>
 * <br>
 * ex:
 * 
 *
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEmptyValidator.class})
@Documented
public @interface NotEmpty {

   /**
    * Message par défaut
    */
	String message() default "{org.hibernate.validator.constraints.NotEmpty.message}";
	
	/**
	 * Non prise en compte
	 */
	Class<?>[] groups() default {};

	/**
	 * Non prise en compte
	 */
	Class<? extends Payload>[] payload() default {};
}