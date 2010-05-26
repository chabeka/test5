package fr.urssaf.image.commons.controller.spring.servlet.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validation {

	public String[] fields() default {};

	public String[] rules() default {};

	public String action() default "";

}
