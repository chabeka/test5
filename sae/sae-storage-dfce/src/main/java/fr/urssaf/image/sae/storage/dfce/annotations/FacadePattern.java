/**
 * 
 */
package fr.urssaf.image.sae.storage.dfce.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Marque un classe qui implémente le design pattern façade <br/>
 * <li>Attribut participants : permet de lister toutes les classes participant à
 * cette façade</li> <li>Attribut comment : permet d'ajouter un commentaire</li>
 * 
 * @author akenore
 */
@Retention(RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Documented
public @interface FacadePattern {
	/**
	 * 
	 *  Les participants du pattern
	 */
	Class<?>[] participants() default {};

	/**
	 * 
	 *  Le commentaire
	 */
	String comment() default "";
}
