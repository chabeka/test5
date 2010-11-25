package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;

@SuppressWarnings("PMD")
public class SaeAnaisServiceTest {

   private SaeAnaisService service;

   private static final String NOTEMPTY = "not empty";

   @Before
   public void initService() {
      service = new SaeAnaisService();
   }

   @Test
   public void environnementEmpty() {

      try {
         service.authentifierPourSaeParLoginPassword(null,
               new SaeAnaisAdresseServeur(), NOTEMPTY, NOTEMPTY, NOTEMPTY,
               NOTEMPTY);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals(
               "L’environnement (Développement / Validation  / Production) doit être renseigné",
               e.getMessage());
      }

   }

}
