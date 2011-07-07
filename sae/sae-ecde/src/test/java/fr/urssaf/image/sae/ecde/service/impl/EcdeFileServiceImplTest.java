package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
/**
 * Classe Test pour l'implementation du Service EcdeFile
 */
public class EcdeFileServiceImplTest {

   private URI uri;
   
   @Autowired
   private EcdeFileService ecdeFileService;
   
   private static final String ECDECER69 = "ecde.cer69.recouv";
   private static final String ECDE = "ecde";
   private static final String ATTESTATION = "/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
   
   // utilisation pour la convertion
   private final EcdeSource ecde1 = new EcdeSource("ecde.hoth.recouv", new File("/ecde/ecde_host/"));
   private final EcdeSource ecde2 = new EcdeSource(ECDECER69, new File("/ecde/ecde_lyon/"));
   private final EcdeSource ecde3 = new EcdeSource("ecde.tatoine.recouv", new File("/ecde/ecde_tatoine/"));
   
   
   // pour la vérification du scheme qui est toujours egale à ecde
   @Test
   public void convertUrlToFileSchemeTest ()  {
      try {
         uri = new URI("ecd", ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri, new EcdeSource(ECDE, new File(ATTESTATION)));
         fail("Une exception était attendue! L'exception du scheme");
      } catch (EcdeBadURLFormatException e){
         assertEquals("Message1 non attendu","L'URL ECDE ecd://"+ECDECER69+ATTESTATION+"# est incorrecte." , e.getMessage());
      } catch (URISyntaxException e){
         assertEquals("Message2 non attendu","L'URL ECDE ecd://"+ECDECER69+ATTESTATION+"# est incorrecte." , e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals("Message3 non attendu","L'URL ECDE ecd://"+ECDECER69+ATTESTATION+"# est incorrecte." , e.getMessage());
      }
   }
   
   // pour la vérification que l'uri est bien dans la liste des ecdesource fournit en param
   // donc génération du chemin absolu du fichier
   @Test
   public void convertUrlToFileUriExistTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
   
      uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
      
      File resultatObtenu = ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      File resultatAttendu = new File("/ecde/ecde_lyon/"+ATTESTATION);
      
      assertEquals("Conversion Ok mais valeur mauvaise ! ", resultatObtenu.getAbsolutePath(), resultatAttendu.getAbsolutePath());
   }
   
   // pour la vérification que l'uri est bien absente de la liste des ecdesource fournit en param
   // donc génération d'une exception
   @Test
   public void convertUrlToFileUriNotExistTest()  {
  
      try {
         uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde3);
         fail("Une exception était attendue! L'exception EcdeBadURLException");
      }catch (EcdeBadURLFormatException e){
         assertEquals("Message4 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());
      }catch (URISyntaxException e){
         assertEquals("Message5 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());// rien à faire, c'est normal.
      }catch (EcdeBadURLException e){
         assertEquals("Message6 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());
      }
   }
   
   //verification que l'url entre est correct
//   @Test
//   public void convertUrlToFileFormatTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
//      uri = new URI(ECDE, "ecde.cer69.recouv", ATTESTATION, "");
//      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
//   }
   
   
   
}
