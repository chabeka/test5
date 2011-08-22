package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.axis2.AxisFault;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.Consultation;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ConsultationResponseType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.MetadonneeType;
import fr.urssaf.image.sae.webservices.service.RequestServiceFactory;
import fr.urssaf.image.sae.webservices.util.ADBBeanUtils;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class ConsultationSecureTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger
         .getLogger(ConsultationSecureTest.class);

   @Before
   public final void before() {

      service = SecurityConfiguration.before();

      AuthenticateUtils.authenticate("ROLE_TOUS");
   }

   @After
   public final void after() {

      SecurityConfiguration.after();
   }

   private static void assertMetadata(MetadonneeType metadata,
         Map<String, Object> expectedMetadatas) {

      assertTrue("la metadonnée '" + metadata.getCode().getMetadonneeCodeType()
            + "' est inattendue", expectedMetadatas.containsKey(metadata
            .getCode().getMetadonneeCodeType()));

      assertEquals("la valeur de la metadonnée est inattendue",
            expectedMetadatas.get(metadata.getCode().getMetadonneeCodeType()),
            metadata.getValeur().getMetadonneeValeurType());

      expectedMetadatas.remove(metadata.getCode().getMetadonneeCodeType());
   }

   private static final String AXIS_FAULT = "AxisFault non attendue";

   private static void assertAxisFault(AxisFault axisFault, String expectedMsg,
         String expectedType, String expectedPrefix) {

      assertEquals(AXIS_FAULT, expectedMsg, axisFault.getMessage());
      assertEquals(AXIS_FAULT, expectedType, axisFault.getFaultCode()
            .getLocalPart());
      assertEquals(AXIS_FAULT, expectedPrefix, axisFault.getFaultCode()
            .getPrefix());
   }

   @Test
   public void consultation_success() throws IOException {

      Consultation request = RequestServiceFactory.createConsultation(
            "1261362f-c87c-4e48-a06a-bc6b69f514e4", false);

      ConsultationResponseType response = service.consultation(request)
            .getConsultationResponse();

      String xml = ADBBeanUtils.print(response);
      LOG.debug(xml);

      MetadonneeType[] metadatas = response.getMetadonnees().getMetadonnee();

      assertNotNull("la liste des metadonnées doit être renseignée", metadatas);
      assertEquals("nombre de metadatas inattendu", 10, metadatas.length);

      Map<String, Object> expectedMetadatas = new HashMap<String, Object>();

      expectedMetadatas.put("ASO", "GED");
      expectedMetadatas.put("ACT", "2");
      expectedMetadatas.put("OTY", "autonome");
      expectedMetadatas.put("CSE", "CS1");
      expectedMetadatas.put("DCO", "12");
      expectedMetadatas.put("DFC", "2015/12/01");
      expectedMetadatas.put("COP", "UR030");
      expectedMetadatas.put("DOM", "2");
      expectedMetadatas.put("RND", "2.2.3.2.2");
      expectedMetadatas.put("FFI", "fmt/18");

      assertMetadata(metadatas[0], expectedMetadatas);
      assertMetadata(metadatas[1], expectedMetadatas);
      assertMetadata(metadatas[2], expectedMetadatas);
      assertMetadata(metadatas[3], expectedMetadatas);
      assertMetadata(metadatas[4], expectedMetadatas);
      assertMetadata(metadatas[5], expectedMetadatas);
      assertMetadata(metadatas[6], expectedMetadatas);
      assertMetadata(metadatas[7], expectedMetadatas);
      assertMetadata(metadatas[8], expectedMetadatas);
      assertMetadata(metadatas[9], expectedMetadatas);

      File expectedContent = new File(
            "src/test/resources/storage/attestation.pdf");

      DataHandler actualContent = response.getObjetNumerique()
            .getObjetNumeriqueConsultationTypeChoice_type0().getContenu();

      assertEquals("le contenu n'est pas attendu en base64", Base64Utils
            .encode(FileUtils.readFileToByteArray(expectedContent)),
            Base64Utils.encode(actualContent));

      assertTrue("le contenu n'est pas attendu", IOUtils.contentEquals(
            FileUtils.openInputStream(expectedContent), actualContent
                  .getInputStream()));

      assertNull(
            "Test de l'archivage unitaire : doit avoir aucune url directe de consultation",
            response.getObjetNumerique()
                  .getObjetNumeriqueConsultationTypeChoice_type0().getUrl());

   }

   @Test
   public void consultation_failure_urldirecte() throws RemoteException {
      try {
         Consultation request = RequestServiceFactory.createConsultation(
               "a08addbb-f948-4489-a8a4-70fcb19feb9f", true);

         service.consultation(request).getConsultationResponse();

         fail("le test doit échouer car l'url de consultation directe ne peut être à true");

      } catch (AxisFault fault) {

         assertAxisFault(
               fault,
               "La fonctionnalité URL de consultation directe n'est pas implémentée",
               "FonctionNonImplementee", "sae");

      }
   }

}
