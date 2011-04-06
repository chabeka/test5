package fr.urssaf.image.sae.webservices.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Classe permet d'accéder aux context de spring pour les élements en dehors du
 * context de spring
 * 
 * 
 */
@Component
public final class SpringConfiguration {

   @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
   private static ApplicationContext context;

   @Autowired
   private SpringConfiguration(ApplicationContext applicationCtx) {
      context = applicationCtx;
   }

   /**
    * 
    * 
    * @param <T>
    *           type du bean
    * @param requiredType
    *           classe du bean
    * @return bean du context de spring
    */
   public static <T> T getService(Class<T> requiredType) {
      return context.getBean(requiredType);
   }

}
