/**
 * 
 */
package fr.urssaf.image.sae.ecde.managertest;

import java.io.IOException;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;

/**
 * Classe Factory pour les Tests Unitaires.
 * 
 */
public interface EcdeSourceManagerTest {
   
   /**
    * Methode permettant de charger des EcdesSources pour les TU.
    * @return lsite des ECDESources
    * @throws EcdeBadFileException
    * @throws IOException 
    */
   EcdeSources load() throws EcdeBadFileException, IOException;

}
