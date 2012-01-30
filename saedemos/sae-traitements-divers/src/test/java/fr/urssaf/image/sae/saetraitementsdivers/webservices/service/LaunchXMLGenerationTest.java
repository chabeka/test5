/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.webservices.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.modele.BeanRNDTypeDocument;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.service.DataManagementInterface;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.DuplicationInterface;

/**
 * Attention. Ceci n'est pas une interface de test. Cette interface sert à
 * générer les fichier de compatibilité avec CNA
 * 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-saeTraitementsDivers-test.xml" })
public class LaunchXMLGenerationTest {

   private static final Logger LOG = LoggerFactory
         .getLogger(LaunchXMLGenerationTest.class);

   @Autowired
   private DuplicationInterface duplicationI;

   @Autowired
   private DataManagementInterface managementI;

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   @Ignore
   public void runFromConfigurationFile() throws AdrnToRcndException {

      String version = duplicationI.getVersionFromConfigFile();

      LOG.debug("numéro de version = " + version);

      List<BeanRNDTypeDocument> typeDoc = duplicationI
            .getDocumentTypesFromConfigFile();
      managementI.saveDocuments(typeDoc, version);

   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   @Ignore
   public void runFromWSCall() throws AdrnToRcndException {

      String version = duplicationI.getVersionFromWS();

      List<BeanRNDTypeDocument> typeDoc = duplicationI.getDocumentTypesFromWS();
      managementI.saveDocuments(typeDoc, version);

   }

}
