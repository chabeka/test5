package fr.urssaf.image.sae.services.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.services.SAEServiceProvider;

/**
 * Classe de base pour les tests unitaires.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/appliContext-sae-services-test.xml" })
@SuppressWarnings( { "PMD.ExcessiveImports", "PMD.LongVariable",
      "AbstractClassWithoutAbstractMethod" })
public class CommonsServices {
   @Autowired
   @Qualifier("saeServiceProvider")
   private SAEServiceProvider sAEServiceProvider;
   
   /**
    * @return Un service.
    */
   public final SAEServiceProvider getSaeServiceProvider() {
      return sAEServiceProvider;
   }

   /**
    * @param sAEServiceProvider  : Service provider. 
    */
   public final void setSaeServiceProvider(SAEServiceProvider sAEServiceProvider) {
      this.sAEServiceProvider = sAEServiceProvider;
   }
}
