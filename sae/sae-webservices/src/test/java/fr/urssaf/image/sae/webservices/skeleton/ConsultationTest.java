package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.axis2.AxisFault;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationResponseType;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.webservices.exception.ConsultationAxisFault;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;
import fr.urssaf.image.sae.webservices.util.XMLStreamUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-consultation-test.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions", "PMD.TooManyStaticImports" })
public class ConsultationTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   @Before
   public final void before() {

      Axis2Utils.initMessageContextSecurity();

   }

   private Consultation createConsultationResponseType(String filePath) {

      try {

         XMLStreamReader reader = XMLStreamUtils
               .createXMLStreamReader(filePath);
         return Consultation.Factory.parse(reader);

      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

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

      Consultation request = createConsultationResponseType("src/test/resources/request/consultation_success.xml");

      ConsultationResponseType response = skeleton.consultationSecure(request)
            .getConsultationResponse();

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
   public void consultation_failure_urldirecte() {
      try {

         Consultation request = createConsultationResponseType("src/test/resources/request/consultation_failure_urldirecte.xml");

         skeleton.consultationSecure(request).getConsultationResponse();

         fail("le test doit échouer car l'url de consultation directe ne peut être à true");

      } catch (ConsultationAxisFault fault) {

         assertAxisFault(
               fault,
               "la fonctionnalité URL de consultation directe n'est pas implémentée",
               "FonctionNonImplementee", "sae");

      }
   }

   @Test
   public void consultation_failure_uuidNotFound() {
      try {
         Consultation request = createConsultationResponseType("src/test/resources/request/consultation_failure_uuidNotFound.xml");

         skeleton.consultationSecure(request).getConsultationResponse();

         fail("le test doit échouer car l'uuid n'existe pas dans le SAE");

      } catch (ConsultationAxisFault fault) {

         assertAxisFault(
               fault,
               "il n'existe aucun document pour l'identifiant d'archivage 'cc26f62e-fd52-42ff-ad83-afc26f96ea91'",
               "ArchiveNonTrouvee", "sae");

      }
   }

}
