package fr.urssaf.image.sae.webservices.security.igc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.webservices.component.IgcConfigFactory;
import fr.urssaf.image.sae.webservices.component.SecurityFactory;
import fr.urssaf.image.sae.webservices.component.TempDirectoryFactory;
import fr.urssaf.image.sae.webservices.security.igc.exception.LoadCertifsAndCrlException;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcServiceTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final String CERTIFICAT = "pseudo_IGCA.crt";

   private static final Logger LOG = Logger.getLogger(IgcServiceTest.class);

   @Before
   public void before() {

      // création des répertoires pour le dépot des CRL et du
      // fichier de configuration

      TempDirectoryFactory.createDirectory();
      TempDirectoryFactory.createACDirectory();
      TempDirectoryFactory.createCRLDirectory();

   }

   @After
   public void after() {

      TempDirectoryFactory.cleanDirectory();
   }

   @Test
   public void IgcService_success() {

      // téléchargement d'un certificat dans le répertoires temporaire
      SecurityFactory.downloadCertificat(CERTIFICAT);

      IgcConfig igcConfig = IgcConfigFactory
            .createIgcConfig("igcConfig_success.xml");

      assertNotNull("exception dans le constructeur", new IgcService(igcConfig));

   }

   @Test
   public void IgcService_failure_certificateException() {

      SecurityFactory.downloadCertificat("Pseudo_IGC_A.pem", CERTIFICAT);

      IgcConfig igcConfig = IgcConfigFactory
            .createIgcConfig("igcConfig_failure_certificateException.xml");

      try {
         new IgcService(igcConfig);
         fail(FAIL_MSG);
      } catch (IllegalArgumentException e) {

         LOG.debug(e.getCause().getMessage());
         assertTrue(
               "Exception non attendue de type " + e.getCause().getClass(),
               CertificateException.class.isAssignableFrom(e.getCause()
                     .getClass()));
      }
   }

   @Test
   public void IgcService_failure_ac_racine_empty() throws IOException {

      IgcConfig igcConfig = IgcConfigFactory
            .createIgcConfig("igcConfig_failure_ac_racine_empty.xml");

      try {
         new IgcService(igcConfig);
         fail(FAIL_MSG);
      } catch (IllegalArgumentException e) {

         LOG.debug(e.getMessage());
         assertTrue(
               "Message d'exception non attendue de type " + e.getMessage(),
               e
                     .getMessage()
                     .startsWith(
                           "Aucun certificat d'AC racine de confiance trouvé dans le répertoire"));
      }
   }

   @Test
   public void getInstanceCertifsAndCrl_success()
         throws LoadCertifsAndCrlException {

      // téléchargement d'un certificat dans le répertoires temporaire
      SecurityFactory.downloadCertificat(CERTIFICAT);

      IgcConfig igcConfig = IgcConfigFactory
            .createIgcConfig("igcConfig_success.xml");

      SecurityFactory.downloadCRLs(igcConfig);

      IgcService igcService = new IgcService(igcConfig);

      assertNotNull("une instance de CertifsAndCrl est attendue", igcService
            .getInstanceCertifsAndCrl());
   }

   @Test
   public void getInstanceCertifsAndCrl_failure() {

      // téléchargement d'un certificat dans le répertoires temporaire
      SecurityFactory.downloadCertificat(CERTIFICAT);

      IgcConfig igcConfig = IgcConfigFactory
            .createIgcConfig("igcConfig_failure.xml");

      SecurityFactory.downloadCRLs(igcConfig);
      SecurityFactory.downloadCRL(CERTIFICAT);

      IgcService igcService = new IgcService(igcConfig);

      try {
         igcService.getInstanceCertifsAndCrl();
         fail(FAIL_MSG);
      } catch (LoadCertifsAndCrlException e) {
         LOG.debug(e.getCause().getMessage());
         assertTrue(
               "Exception non attendue de type " + e.getCause().getClass(),
               CRLException.class.isAssignableFrom(e.getCause().getClass()));
      }
   }
}
