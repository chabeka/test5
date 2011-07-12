package fr.urssaf.image.sae.ecde.exception;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe pour tester les methodes de EcdeBadURLException
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")

public class EcdeBadURLExceptionTest {

   @Autowired 
   private MessageSource messageSource; 
    
   /**
    * Test afin de verifier que l'exception s'affiche bien 
    */
   @Test
   public void ecdeBadURLException() {
      String message2 = messageSource.getMessage("ecdeBadUrlException.message", new Object[]{"toto"}, Locale.FRENCH);
      EcdeBadURLException exception = new EcdeBadURLException(message2);
      String resultatObtenu = exception.getMessage();
      String resultatAttendu = "L'URL ECDE toto n'appartient à aucun ECDE transmis en paramètre du service.";

      assertEquals("Message non attendu", resultatObtenu, resultatAttendu);   
   }

}
