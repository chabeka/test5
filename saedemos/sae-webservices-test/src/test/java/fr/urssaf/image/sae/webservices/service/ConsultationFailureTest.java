package fr.urssaf.image.sae.webservices.service;

import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;
import fr.urssaf.image.sae.webservices.util.SoapTestUtils;

/**
 * Tests de l'opération "consultation" pour lesquels on attend une SoapFault<br>
 * <br>
 * Il faut penser à modifier la variable privée UUID_EXISTANT, après avoir
 * archivé un document grâce au TU ConsultationUtilsTest.prepareData()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-webservices.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class ConsultationFailureTest {

   /**
    * TODO : remplacer ici l'UUID par un UUID existant
    * 
    * Pour archiver un document, utiliser le TU
    * ConsultationUtilsTest.prepareData()
    */
   private static final String UUID_EXISTANT = "448b8c83-c2e1-4c2b-8574-8c0255086208";

   private static final String[] NOT_EXISTS_META = new String[] { "TypeHash",
         "NbPages", "metadatainexistante" };

   private static final String[] NO_CONSULT_META = new String[] { "TypeHash",
         "NbPages", "StartPage" };

   @Autowired
   private ConsultationService service;

   @Before
   public void before() {
      AuthenticateUtils.authenticate("ROLE_TOUS");
   }

   @After
   public final void after() {
      SecurityConfiguration.cleanSecurityContext();
   }

   @Test
   public void consultation_failure_ArchiveNonTrouve() throws RemoteException {
      try {

         service.consultation("a08addbb-f948-4489-a8a4-70fcb19feb9f");

         fail("le test doit échouer car l'archive n'existe dans le SAE");

      } catch (AxisFault fault) {

         SoapTestUtils
               .assertAxisFault(
                     fault,
                     "Il n'existe aucun document pour l'identifiant d'archivage 'a08addbb-f948-4489-a8a4-70fcb19feb9f'",
                     "ArchiveNonTrouvee", SoapTestUtils.SAE_NAMESPACE,
                     SoapTestUtils.SAE_PREFIX);

      }
   }

   /**
    * Test en succes avec uid not null et metadata inexistant dans referentiel
    * 
    * @throws RemoteException
    *            remoteException
    */
   @Test
   @Ignore
   public final void consultation_failure_ConsultationMetadonneesInexistante()
         throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      List<String> listMdString = Arrays.asList(NOT_EXISTS_META);

      try {
         service.consultation(UUID_EXISTANT, listMdString);

         fail("une exception devrait être levée");

      } catch (AxisFault axisFault) {
         SoapTestUtils
               .assertAxisFault(
                     axisFault,
                     "La ou les métadonnées suivantes, demandées dans les critères de consultation, n'existent pas dans le référentiel des métadonnées : metadatainexistante",
                     "ConsultationMetadonneesInexistante",
                     SoapTestUtils.SAE_NAMESPACE, SoapTestUtils.SAE_PREFIX);
      }

      SecurityConfiguration.cleanSecurityContext();
   }

   /**
    * Test failure avec metadata non consultables
    * 
    * @throws RemoteException
    *            remoteException
    */
   @Test
   @Ignore
   public final void consultation_failure_ConsultationMetadonneesNonAutorisees()
         throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      List<String> listMdString = Arrays.asList(NO_CONSULT_META);

      try {
         service.consultation(UUID_EXISTANT, listMdString);

         fail("une exception devrait être levée");

      } catch (AxisFault axisFault) {
         SoapTestUtils
               .assertAxisFault(
                     axisFault,
                     "La ou les métadonnées suivantes, demandées dans les critères de consultation, ne sont pas consultables : StartPage",
                     "ConsultationMetadonneesNonAutorisees",
                     SoapTestUtils.SAE_NAMESPACE, SoapTestUtils.SAE_PREFIX);
      }

      SecurityConfiguration.cleanSecurityContext();
   }

}
