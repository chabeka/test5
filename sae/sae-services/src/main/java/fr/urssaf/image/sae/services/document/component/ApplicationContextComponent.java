package fr.urssaf.image.sae.services.document.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Classe permet d'accéder à l'instance {@link ApplicationContext} de
 * l'application dans toute l'application en particulier dans les méthodes
 * statiques
 * 
 * 
 */
@Component
public final class ApplicationContextComponent {

   private static ApplicationContext ctx;

   @Autowired
   protected void setApplicationContext(ApplicationContext context) {
      setContext(context);
   }

   private static void setContext(ApplicationContext context) {
      ctx = context;
   }

   /**
    * 
    * @return contexte de l'application
    */
   public static ApplicationContext getApplicationContext() {
      return ctx;
   }

}
