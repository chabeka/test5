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
 * Classe pour tester les methodes de EcdeBadURLFormatException
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")

public class EcdeBadURLFormatExceptionTest {

   @Autowired 
   private MessageSource messageSource; 
    
   /**
    * Test afin de verifier que l'exception affiche bien l'Url ECDE '{0}' est incorrecte.
    */
   @Test
   public void ecdeBadURLException() {
      String message2 = messageSource.getMessage("ecdeBadUrlFormatException.message", new Object[]{"toto"}, Locale.FRENCH);
      EcdeBadURLException exception = new EcdeBadURLException(message2);
      String resultatObtenu = exception.getMessage();
      String resultatAttendu = "L'URL ECDE toto est incorrecte.";

      assertEquals("Message non attendu", resultatObtenu, resultatAttendu);   
   }

}
