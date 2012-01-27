/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.webservices.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.RNDTypeDocument;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.DataManagementInterface;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.DuplicationInterface;

/**
 * Attention. Ceci n'est pas une interface de test. Cette interface sert à
 * générer les fichier de compatibilité avec CNA
 * 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-saeTraitementsDivers-test.xml" })
public class LaunchXMLGeneration {

   @Autowired
   private DuplicationInterface duplicationI;

   @Autowired
   private DataManagementInterface managementI;

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void runFromConfigurationFile() throws AdrnToRcndException {

      String version = duplicationI.getVersionFromConfigFile();

      RNDTypeDocument[] typeDoc = duplicationI.getDocumentTypesFromConfigFile();
      managementI.saveDocuments(typeDoc, version);

   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void runFromWSCall() throws AdrnToRcndException {

      String version = duplicationI.getVersionFromWS();

      RNDTypeDocument[] typeDoc = duplicationI.getDocumentTypesFromWS();
      managementI.saveDocuments(typeDoc, version);

   }

}
