package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;

import org.apache.axiom.util.base64.Base64Utils;
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
   }

   @After
   public final void after() {

      SecurityConfiguration.after();
   }

   @Test
   public void consultation_success() throws IOException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      Consultation request = RequestServiceFactory.createConsultation(
            "48758200-A29B-18C4-B616-455677840120", false);

      ConsultationResponseType response = service.consultation(request)
            .getConsultationResponse();

      String xml = ADBBeanUtils.print(response);
      LOG.debug(xml);

      MetadonneeType[] metadonnees = response.getMetadonnees().getMetadonnee();

      assertEquals("nombre de metadonnees inattendu", 6, metadonnees.length);

      assertMetadonnee(metadonnees[0], "NumeroCotisant", "719900");
      assertMetadonnee(metadonnees[1], "CodeRND", "1.2.3.3.1");
      assertMetadonnee(metadonnees[2], "UUID",
            "48758200-A29B-18C4-B616-455677840120");
      assertMetadonnee(metadonnees[3], "Siret", "07412723410007");
      assertMetadonnee(metadonnees[4], "CodeOrganisme", "UR030");
      assertMetadonnee(metadonnees[5], "DenominationCompte",
            "COUTURIER GINETTE");

      File expectedContent = new File(
            "src/test/resources/storage/attestation.pdf");

      DataHandler actualContent = response.getObjetNumerique()
            .getObjetNumeriqueTypeChoice_type0().getContenu();

      assertEquals("le contenu n'est pas attendu en base64", Base64Utils
            .encode(FileUtils.readFileToByteArray(expectedContent)),
            Base64Utils.encode(actualContent));

      assertTrue("le contenu n'est pas attendu", IOUtils.contentEquals(
            FileUtils.openInputStream(expectedContent), actualContent
                  .getInputStream()));

      assertNull("Test de l'archivage unitaire : doit avoir aucune urlEcde",
            response.getObjetNumerique().getObjetNumeriqueTypeChoice_type0()
                  .getUrl());

   }

   private static void assertMetadonnee(MetadonneeType metadonnee,
         String expectedCode, String expectedValeur) {

      assertEquals("mauvais code", expectedCode, metadonnee.getCode()
            .getMetadonneeCodeType());
      assertEquals("mauvaise valeur", expectedValeur, metadonnee.getValeur()
            .getMetadonneeValeurType());
   }

}
