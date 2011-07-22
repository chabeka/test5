package fr.urssaf.image.sae.ecde.service.validation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.ecde.util.MessageRessourcesUtils;

/**
 * 
 * Classe de validation des arguments en entrée des implémentations du service
 * SommaireXmlService
 * 
 */

@SuppressWarnings("PMD.PreserveStackTrace")
@Aspect
public class SommaireXmlServiceValidation {

   private static final String ECDESOMSERVICE = "fr.urssaf.image.sae.ecde.service.SommaireXmlService.";
   
   // verification param entrée methode readSommaireXml#1
   private static final String READSOMXMLINPUT = "execution(fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType "+ECDESOMSERVICE+"readSommaireXml(*))" +
                                                 "&& args(input)";
   
   // verification param entrée methode readSommaireXml#2
   private static final String READSOMXMLFILE = "execution(fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType "+ECDESOMSERVICE+"readSommaireXml(*))" +
                                                "&& args(input)";
   
   
   /**
    * Methode permettant de venir verifier si les paramétres d'entree de la methode readSommaireXml de l'interface
    * service.SommaireXmlService sont bien correct.
    * 
    * @param input flux representant un fichier sommaire.xml
    * 
    */
   @Before(READSOMXMLINPUT)
   public final void readSommaireXml(InputStream input) {
      
      try {
         if ( input == null || input.read() == -1 ) { // =-1 si fin de flux
            throw new IllegalArgumentException(MessageRessourcesUtils.recupererMessage("input.nonRenseigne", null));
         }
      } catch (IOException e) {
            throw new IllegalArgumentException(MessageRessourcesUtils.recupererMessage("input.nonRenseigne", null));
      }
      
   }
   
   /**
    * Methode permettant de venir verifier si les paramétres d'entree de la methode readSommaireXml de l'interface
    * service.SommaireXmlService sont bien correct.
    * 
    * @param input fichier sommaire.xml
    * 
    */
   @Before(READSOMXMLFILE)
   public final void readSommaireXml(File input) {
      
      if (input == null) { // le fichier est null
            throw new IllegalArgumentException(MessageRessourcesUtils.recupererMessage("input.nonRenseigne", null));
      }
   } 
   
}
