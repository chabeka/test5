package fr.urssaf.image.sae.webservices.skeleton;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.stream.XMLStreamReader;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.webservices.exception.CaptureAxisFault;
import fr.urssaf.image.sae.webservices.util.XMLStreamUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class ArchivageUnitaireTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final String FAIL_SOAPFAULT = "SOAP FAULT non attendu";

   @Autowired
   private SaeServiceSkeleton skeleton;

   @Autowired
   private SAECaptureService captureService;

   @After
   public void after() {
      EasyMock.reset(captureService);
   }

   private ArchivageUnitaire createArchivageMasseResponse(String filePath) {

      try {

         XMLStreamReader reader = XMLStreamUtils
               .createXMLStreamReader(filePath);
         return ArchivageUnitaire.Factory.parse(reader);

      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

   }

   private void mockReturn(URI ecdeURL, Map<String, String> metadatas) {

      try {
         EasyMock.expect(captureService.capture(metadatas, ecdeURL)).andReturn(
               UUID.fromString("110E8400-E29B-11D4-A716-446655440000"));
      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

      EasyMock.replay(captureService);
   }

   @Test
   public void archivageUnitaire_success() throws CaptureAxisFault {

      URI ecdeURL = URI
            .create("ecde://cer69-ecde.cer69.recouv/DCL001/19991231/3/documents/attestation.pdf");
      Map<String, String> metadatas = new HashMap<String, String>();

      metadatas.put("code_test_1", "value_test_1");
      metadatas.put("code_test_2", "value_test_2");

      mockReturn(ecdeURL, metadatas);

      ArchivageUnitaire request = createArchivageMasseResponse("src/test/resources/request/archivageUnitaire_success.xml");

      ArchivageUnitaireResponseType response = skeleton
            .archivageUnitaireSecure(request).getArchivageUnitaireResponse();

      Assert.assertEquals("Test de l'archivage unitaire",
            "110E8400-E29B-11D4-A716-446655440000", response.getIdArchive()
                  .getUuidType());
   }

   @Test
   public void archivageUnitaire_failure_metadonnees_vide() {

      try {

         ArchivageUnitaire request = createArchivageMasseResponse("src/test/resources/request/archivageUnitaire_failure_metadonnees_vide.xml");

         skeleton.archivageUnitaireSecure(request)
               .getArchivageUnitaireResponse();

         Assert.fail(FAIL_MSG);

      } catch (AxisFault axisFault) {

         Assert.assertEquals(FAIL_SOAPFAULT,
               "Archivage impossible. La liste des métadonnées est vide.",
               axisFault.getMessage());
         Assert.assertEquals(FAIL_SOAPFAULT, "CaptureMetaDonneesVide",
               axisFault.getFaultCode().getLocalPart());
         Assert.assertEquals(FAIL_SOAPFAULT, "sae", axisFault.getFaultCode()
               .getPrefix());

         Assert.assertEquals(FAIL_SOAPFAULT, "urn:sae:faultcodes", axisFault
               .getFaultCode().getNamespaceURI());
      }
   }

}
