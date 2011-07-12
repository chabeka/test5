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
 * Classe pour tester les methodes de EcdeBadFileException
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")

public class EcdeBadFileExceptionTest {

   @Autowired 
   private MessageSource messageSource; 
    
   /**
    * Test afin de verifier que l'exception affiche bien 
    * Le chemin du document '{0}' n'appartient à aucun ECDE transmis en paramètre du service.
    */
   @Test
   public void ecdeBadFileException() {
      String message2 = messageSource.getMessage("ecdeBadFileException.message", new Object[]{"toto"}, Locale.FRENCH);
      EcdeBadFileException exception = new EcdeBadFileException(message2);
      String resultatObtenu = exception.getMessage();
      String resultatAttendu = "Le chemin du document 'toto' n'appartient à aucun ECDE transmis en paramètre du service.";

      assertEquals("Message non attendu", resultatObtenu, resultatAttendu);   
   }
}