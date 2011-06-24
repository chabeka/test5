package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationResponseType;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class ConsultationTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   @Test
   public void consultation_success() throws IOException {

      Axis2Utils.initMessageContext(ctx,
            "src/test/resources/request/consultation_success.xml");

      Consultation request = new Consultation();

      ConsultationResponseType response = skeleton.consultationSecure(request)
            .getConsultationResponse();

      MetadonneeType[] metadonnees = response.getMetadonnees().getMetadonnee();

      assertEquals("nombre de metadonnees inattendu", 5, metadonnees.length);

      assertMetadonnee(metadonnees[0], "NumeroCotisant", "719900");
      assertMetadonnee(metadonnees[1], "CodeRND", "1.2.3.3.1");
      // assertMetadonnee(metadonnees[2], "UUID",
      // "48758200-A29B-18C4-B616-455677840120");
      assertMetadonnee(metadonnees[2], "Siret", "07412723410007");
      assertMetadonnee(metadonnees[3], "CodeOrganisme", "UR030");
      assertMetadonnee(metadonnees[4], "DenominationCompte",
            "COUTURIER GINETTE");

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

   private static void assertMetadonnee(MetadonneeType metadonnee,
         String expectedCode, String expectedValeur) {

      assertEquals("mauvais code", expectedCode, metadonnee.getCode()
            .getMetadonneeCodeType());
      assertEquals("mauvaise valeur", expectedValeur, metadonnee.getValeur()
            .getMetadonneeValeurType());
   }

}
